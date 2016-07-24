package com.lordjoe.ui.propertyeditor;

import com.lordjoe.lang.ObjectOps;
import com.lordjoe.lib.xml.ClassProperty;
import com.lordjoe.propertyeditor.IPropertyWrapper;
import com.lordjoe.propertyeditor.ReflectionPropertyWrapper;
import com.lordjoe.ui.general.JSingleLineTextField;
import com.lordjoe.ui.general.JTextFieldArea;
import com.lordjoe.utilities.LimitedString;
import com.lordjoe.utilities.Util;
import com.lordjoe.utilities.ValidityChangeListener;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * com.lordjoe.ui.propertyeditor.StringReflectionPropertyEditor
 *
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public class StringReflectionPropertyEditor extends AbstractReflectionPropertyEditor<String>
        implements ValidityChangeListener {
    public static StringReflectionPropertyEditor[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = StringReflectionPropertyEditor.class;
    public static final int DEFAULT_WIDTH_CHARS = 30;
    public static final int DEFAULT_HEIGHT_CHARS = 4;

    private JTextComponent m_TextField;
    private JScrollPane m_Scroller;

    public StringReflectionPropertyEditor(IPropertyWrapper<String> pw) {
        super(pw);
        int MaxLength = Util.DEFAULT_MAX_STRING_LENGTH;
        boolean singleLine = true;
        if (pw instanceof ReflectionPropertyWrapper) {
            ReflectionPropertyWrapper rw = (ReflectionPropertyWrapper) pw;
            ClassProperty property = rw.getProperty();
            String name = property.getName();
            if("IndividualType".equals(name))
                Util.breakHere();
            Field backingField = property.getBackingField();
            if (backingField != null &&
                    LimitedString.class.isAssignableFrom(backingField.getType())) {
                try {
                    /**
                     * see http://java.sun.com/docs/books/tutorial/reflect/member/methodTrouble.html
                     * this makes the field accessable as long asd the secutity managet allows
                     */
                    boolean acessable = backingField.isAccessible();
                    backingField.setAccessible(true);
                    LimitedString o = (LimitedString) backingField.get(rw.getOwner());
                    MaxLength = o.getMaxLength();
                    backingField.setAccessible(acessable); // set it back
                    singleLine = MaxLength <= 256;
                } catch (IllegalAccessException e) {
                    singleLine = true;
                }
            }
        }
        if (singleLine) {
            JSingleLineTextField tf = new JSingleLineTextField(DEFAULT_WIDTH_CHARS);
            tf.addFocusListener(this);
            tf.addValidityChangeListener(this);
            adjustFieldProperties(m_TextField);
            tf.setRequired(pw.isRequired());
            tf.setMaxCharacters(MaxLength);
            m_TextField = tf;
            if(pw.isReadOnly())
                  m_TextField.setEnabled(false);
        } else {
            JTextFieldArea tf = new JTextFieldArea();
            tf.addFocusListener(this);
            tf.addValidityChangeListener(this);
            adjustFieldProperties(m_TextField);
            tf.setRequired(pw.isRequired());
            tf.setMaxCharacters(MaxLength);
            m_TextField = tf;
            m_Scroller = new JScrollPane(tf);
            JTextFieldArea test = new JTextFieldArea(DEFAULT_HEIGHT_CHARS,DEFAULT_WIDTH_CHARS);
            m_Scroller.setPreferredSize(test.getPreferredSize());
            if(pw.isReadOnly())
                   m_TextField.setEnabled(false);
        }
    }


    public JComponent getMainDisplay() {
        if(m_TextField instanceof JTextFieldArea)
            return m_Scroller;
        return m_TextField;
    }


    public JComponent[] getStylizableComponents() {
        List<JComponent> holder = new ArrayList<JComponent>();
        holder.add(getLabel());
        holder.add(getMainDisplay());
        JComponent[] ret = new JComponent[holder.size()];
        holder.toArray(ret);
        return ret;
    }


    /**
     * return the current value as represented by the editor
     *
     * @return
     */
    public String getValue() {
        if(m_TextField == null)
            return null;
        return m_TextField.getText();
    }

    protected void onBlur() {
        String value = getValue();
        notifyEditedPropertyChangeListeners(null, value);
    }

    public void onValidityChange(Object source, boolean isValid) {
        String value = getValue();
        notifyEditedPropertyChangeListeners(null, value);

    }

    public void setValue(String value) {
        String oldValue = getValue();
        if (ObjectOps.equalsNullSafe(oldValue, value))
            return;
        m_TextField.setText(value);
        adjustTextComponentProperties(m_TextField);
        notifyEditedPropertyChangeListeners(oldValue, value);
    }

}
