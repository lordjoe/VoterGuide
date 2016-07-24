package com.lordjoe.ui.propertyeditor;

import com.lordjoe.propertyeditor.*;
import com.lordjoe.ui.general.*;
import com.lordjoe.ui.timeeditor.*;
import com.lordjoe.ui.*;
import com.lordjoe.utilities.*;
import com.lordjoe.utilities.ResourceString;
import com.toedter.calendar.*;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

import sun.security.util.Resources;


/**
 * com.lordjoe.ui.propertyeditor.AbstractReflectionPropertyEditor
 *
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public abstract class AbstractReflectionPropertyEditor<T> implements IComponentPropertyEditor<T>,
        IStylizableComponent, FocusListener{
    public static AbstractReflectionPropertyEditor[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = AbstractReflectionPropertyEditor.class;
    public static final Color DIASABLED_BACKGROUND = new Color(245, 245, 245);

    private IPropertyWrapper<T> m_Wrapper;
    private T m_OriginalValue;
    private JProjectEditorPanel m_Panel;
    private JLabel m_Label;
    private final List<EditedPropertyChangeListener> m_EditedPropertyChangeListeners;

    protected AbstractReflectionPropertyEditor(IPropertyWrapper<T> pWrapper) {
        m_Wrapper = pWrapper;
        m_OriginalValue = pWrapper.getCurrentValue();
        m_Panel = new JProjectEditorPanel(this);
        m_Label = new JLabel(buildLabelText());
        m_EditedPropertyChangeListeners = new CopyOnWriteArrayList<EditedPropertyChangeListener>();
    }

    // Add to constructor


    /**
     * add a change listener
     *
     * @param added non-null change listener
     */
    public void addEditedPropertyChangeListener(EditedPropertyChangeListener added) {
        if (!m_EditedPropertyChangeListeners.contains(added))
            m_EditedPropertyChangeListeners.add(added);
    }

    /**
     * remove a change listener
     *
     * @param removed non-null change listener
     */
    public void removeEditedPropertyChangeListener(EditedPropertyChangeListener removed) {
        while (m_EditedPropertyChangeListeners.contains(removed))
            m_EditedPropertyChangeListeners.remove(removed);
    }


    /**
     * notify any state change listeners - probably should
     * be protected but is in the interface to form an event cluster
     *
     * @param oldState
     * @param newState
     * @param commanded
     */
    public void notifyEditedPropertyChangeListeners(Object oldValue, Object value) {
        if (m_EditedPropertyChangeListeners.isEmpty())
            return;
        for (EditedPropertyChangeListener listener : m_EditedPropertyChangeListeners) {
            listener.onEditedPropertyChange(oldValue, value);
        }
    }

    protected void adjustFieldProperties(JComponent c) {
        IPropertyWrapper<T> pr = getPropertyWrapper();
        if (c instanceof JTextComponent) {
            adjustTextComponentProperties((JTextComponent) c);
            return;
        }
        if (c instanceof JComboBox) {
            adjustComboBoxProperties((JComboBox) c);
            return;
        }
        if (c instanceof RequiredObjectEditor) {
            adjustComboBoxProperties((RequiredObjectEditor) c);
            return;
        }
        if (c instanceof JDateValueEditor) {
            adjustCalendarProperties((JDateValueEditor) c);
            return;
        }
        if (c instanceof JTimeStampDateEditor) {
            adjustCalendarProperties((JTimeStampDateEditor) c);
            return;
        }
        if (c instanceof JTimeStampChooser) {
            adjustCalendarProperties((JTimeStampChooser) c);
            return;
        }
        if (c instanceof JDateChooser) {
            adjustCalendarProperties((JDateChooser) c);
            return;
        }
//        if (c instanceof ar.com.da.swing.Calendar) {
//            adjustCalendarProperties((ar.com.da.swing.Calendar) c);
//            return;
//        }
    }

    protected void adjustTextComponentProperties(JTextComponent c) {
        IPropertyWrapper<T> pr = getPropertyWrapper();
        if (pr.isReadOnly()) {
            c.setEditable(false);
            c.setForeground(Color.black);
            c.setBackground(DIASABLED_BACKGROUND);
            notifyEditedPropertyChangeListeners(null,getValue());
            return;
        }
        if (pr.isRequired()) {
            String s = c.getText();
            int len = s.length();
            if (len == 0)
                c.setBackground(JSingleLineTextField.REQUIRED_COLOR);
            else {
                c.setBackground(Color.white);

            }
        }
        notifyEditedPropertyChangeListeners(null,getValue());

    }


    protected void adjustComboBoxProperties(JComboBox c) {
        IPropertyWrapper<T> pr = getPropertyWrapper();
        if (pr.isReadOnly()) {
            c.setEditable(false);
            c.setForeground(Color.black);
            c.setBackground(DIASABLED_BACKGROUND);
            return;
        }
        if (pr.isRequired()) {
            if (c.getSelectedItem() == null)
                c.setBackground(JSingleLineTextField.REQUIRED_COLOR);
            else {
                c.setBackground(Color.white);
            }
        }
    }

    protected void adjustComboBoxProperties(RequiredObjectEditor c) {
        IPropertyWrapper<T> pr = getPropertyWrapper();
        if (pr.isReadOnly()) {
            c.setEditable(false);
            return;
        }
        if (pr.isRequired()) {
            if (c.getEditedObject() == null)
                c.setRequiredPresent(false);
            else {
                c.setRequiredPresent(true);
            }
        }
    }

//    protected void adjustCalendarProperties(ar.com.da.swing.Calendar c) {
//        IPropertyWrapper<T> pr = getPropertyWrapper();
//        if (pr.isReadOnly()) {
//            c.setEnabled(false);
//            c.setForeground(Color.black);
//            c.setBackground(DIASABLED_BACKGROUND);
//            return;
//        }
//        if (pr.isRequired()) {
//            Date date = c.getTime();
//            if (date == null || Math.abs(date.getTime() - System.currentTimeMillis()) < 50)
//                c.setBackground(JSingleLineTextField.REQUIRED_COLOR);
//            else {
//                c.setBackground(Color.white);
//            }
//        }
//    }
    protected void adjustCalendarProperties(JDateValueEditor c) {
        IPropertyWrapper<T> pr = getPropertyWrapper();
        if (pr.isReadOnly()) {
            c.setEnabled(false);
            c.setForeground(Color.black);
            c.setBackground(DIASABLED_BACKGROUND);
            return;
        }
        if (pr.isRequired()) {
            Date date = c.getTime();
            if (date == null || Math.abs(date.getTime() - System.currentTimeMillis()) < 50)
                c.setBackground(JSingleLineTextField.REQUIRED_COLOR);
            else {
                c.setBackground(Color.white);
            }
        }
    }
    protected void adjustCalendarProperties(JTimeStampDateEditor c) {
        IPropertyWrapper<T> pr = getPropertyWrapper();
        if (pr.isReadOnly()) {
            c.setEnabled(false);
            c.setForeground(Color.black);
            c.setBackground(DIASABLED_BACKGROUND);
            return;
        }
        if (pr.isRequired()) {
            Date date = c.getDate();
            if (date == null || Math.abs(date.getTime() - System.currentTimeMillis()) < 50)
                c.setBackground(JSingleLineTextField.REQUIRED_COLOR);
            else {
                c.setBackground(Color.white);
            }
        }
    }

    protected void adjustCalendarProperties(JTimeStampChooser c) {
        IPropertyWrapper<T> pr = getPropertyWrapper();
        if (pr.isReadOnly()) {
            c.setEnabled(false);
            c.setForeground(Color.black);
            c.setBackground(DIASABLED_BACKGROUND);
            return;
        }
        if (pr.isRequired()) {
            Date date = c.getDate();
            if (date == null || Math.abs(date.getTime() - System.currentTimeMillis()) < 50)
                c.setBackground(JSingleLineTextField.REQUIRED_COLOR);
            else {
                c.setBackground(Color.white);
            }
        }
    }

    protected void adjustCalendarProperties(JDateChooser c) {
        IPropertyWrapper<T> pr = getPropertyWrapper();
        if (pr.isReadOnly()) {
            c.setEnabled(false);
            c.setForeground(Color.black);
            c.setBackground(DIASABLED_BACKGROUND);
            return;
        }
        if (pr.isRequired()) {
            Date date = c.getDate();
            if (date == null || Math.abs(date.getTime() - System.currentTimeMillis()) < 50)
                c.setBackground(JSingleLineTextField.REQUIRED_COLOR);
            else {
                c.setBackground(Color.white);
            }
        }
    }


    public void focusGained(FocusEvent e) {

    }

    public void focusLost(FocusEvent e) {
        onBlur();
    }

    protected abstract void onBlur();

    public boolean isRequired() {
        return m_Wrapper.isRequired();
    }

    public boolean isReadOnly() {
        return m_Wrapper.isReadOnly();
    }


    protected String buildLabelText() {
        IPropertyWrapper<T> wrapper = getPropertyWrapper();
        String ret = wrapper.getDisplayName();
        String className = ClassUtilities.shortClassName(wrapper.getOwningClass());
        ret = ResourceString.parameterToText(className + "." + ret);
        if (wrapper.isRequired()) {
            ret = "<html>" + ret + "<font color=\"red\">*</font></html>";
        }
        return ret;
    }

    public JComponent[] getStylizableComponents() {
        List<JComponent> holder = new ArrayList<JComponent>();
        holder.add(m_Label);
        JComponent[] ret = new JComponent[holder.size()];
        holder.toArray(ret);
        return ret;
    }

    /**
     * return self as Jcomponent to a stylizer
     *
     * @return
     */
    public JComponent getStylizableSelf() {
        return null; // all parts in sub panel
    }

    public JLabel getLabel() {
        return m_Label;
    }

    public abstract JComponent getMainDisplay();

    /**
     * do something to rebuild the panel
     */
    public void rebuildPanel() {
        JComponent jc = getComponent();
        jc.removeAll();
        jc.add(getLabel());
        jc.add(getMainDisplay());
    }

    /**
     * this neeed not Be a component but is better manage one
     *
     * @return
     */
    public JComponent getComponent() {
        return m_Panel;
    }

    public IPropertyWrapper<T> getPropertyWrapper() {
        return m_Wrapper;
    }

    /**
     * return the current value when the editor was created or last committed
     *
     * @return
     */
    public T getOriginalValue() {
        return m_OriginalValue;
    }

    /**
     * return the current value as represented by the editor
     *
     * @return
     */
    public abstract T getValue();

    public abstract void setValue(T value);

    /**
     * set the value to that represented by the data source
     */
    public void reconcile() {
        if (SwingUtilities.isEventDispatchThread()) {
            doReconcile();
        } else {
            SwingUtilities.invokeLater(
                    new Runnable() {  // start anonymous inner class

                        public void run() {
                            doReconcile();
                        }
                    }  // end anonymous inner class
            );
        }
    }

    protected void doReconcile() {
        setValue(getPropertyWrapper().getCurrentValue());
    }

    /**
     * set the value of the source to that represented by the editor
     */
    public void commit() {
        IPropertyWrapper<T> pw = getPropertyWrapper();
        if(!pw.isReadOnly())
            pw.setCurrentValue(getValue());
    }

    /**
     * roll changes back to creation or lar commit
     */
    public void rollback() {
        getPropertyWrapper().setCurrentValue(getOriginalValue());
    }
}
