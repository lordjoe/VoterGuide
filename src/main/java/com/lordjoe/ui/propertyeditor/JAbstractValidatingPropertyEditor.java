package com.lordjoe.ui.propertyeditor;

import com.lordjoe.general.UIUtilities;
import com.lordjoe.ui.general.JSingleLineTextField;

import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;

/**
 * com.lordjoe.ui.propertyeditor.JLongPropertyEditor
 *
 * @author slewis
 * @date Apr 21, 2005
 */
public abstract class JAbstractValidatingPropertyEditor<T> extends JSingleLineTextField
        implements IPropertyEditor<T> {
    public static final Class THIS_CLASS = JAbstractValidatingPropertyEditor.class;
    public static final JAbstractValidatingPropertyEditor EMPTY_ARRAY[] = {};

    public static final Color DARK_RED = new Color(64,0,0);

    protected JAbstractValidatingPropertyEditor() {
     //   getDocument().addDocumentListener(this);
        addPropertyChangeListener("text",this);
    }


    
    /**
     * Sets the text of this <code>TextComponent</code>
     * to the specified text.  If the text is <code>null</code>
     * or empty, has the effect of simply deleting the old text.
     * When text has been inserted, the resulting caret location
     * is determined by the implementation of the caret class.
     * <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see
     * <A HREF="http://java.sun.com/products/jfc/swingdoc-archive/threads.html">Threads
     * and Swing</A> for more information.
     *
     * Note that text is not a bound property, so no <code>PropertyChangeEvent
     * </code> is fired when it changes. To listen for changes to the text,
     * use <code>DocumentListener</code>.
     *
     * @param t the new text to be set
     * @see #getText
     * @see javax.swing.text.DefaultCaret
     * @beaninfo
     * description: the text of this component
     */
    public void setText(String t) {
        try {
            Document doc = getDocument();
            if (doc instanceof AbstractDocument) {
                ((AbstractDocument)doc).replace(0, doc.getLength(), t,null);
            }
            else {
                doc.remove(0, doc.getLength());
                doc.insertString(0, t, null);
            }
        } catch (BadLocationException e) {
	           super.setText(t);
        }
        setRequiredColor();
        if(isValid())
             setForeground(Color.black);
         else
             setForeground(DARK_RED);

    }



    /**
     * make the  current value is either valid or invalid
     *
     * @param b as ab0ve
     */
    public void setValueValid(boolean valid)
    {
        if(valid)
            setBackground(Color.white);
        else
            setBackground(UIUtilities.INVALID_COLOR);


    }

    /**
     * test whether the value is valid
     * return null if unable to decide
     *
     * @param b possibly null value
     * @return as above null is no opinion
     */
    public Boolean isValueValid(String b)
    {
        return null;
    }


    /**
      * This method gets called when a bound property is changed.
      * @param evt A PropertyChangeEvent object describing the event source
      *   	and the property that has changed.
      */

     public void propertyChange(PropertyChangeEvent evt)
    {
        if("text".equals(evt.getPropertyName())) {
            if(isValid())
                 setForeground(Color.black);
             else
                 setForeground(DARK_RED);

        }
        super.propertyChange(evt);
    }





    public void processKeyEvent(KeyEvent ev) {

        char c = ev.getKeyChar();
         super.processKeyEvent(ev);

    }

}