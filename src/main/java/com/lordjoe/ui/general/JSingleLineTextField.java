package com.lordjoe.ui.general;

import com.lordjoe.lang.*;
import com.lordjoe.utilities.*;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.List;
import java.awt.event.*;
import java.beans.*;
import java.util.concurrent.*;


/**
 * com.lordjoe.ui.general.JSingleLineTextField
 * This version of a TextField will not grow vertically
 * Developed to aid in layout
 *
 * @date Mar 1, 2005
 */
public class JSingleLineTextField extends JTextField implements PropertyChangeListener
{
    public static final Class THIS_CLASS = JSingleLineTextField.class;
    public static final JSingleLineTextField[] EMPTY_ARRAY = {};

    public static final Color REQUIRED_COLOR = new Color(255, 230, 230);
    public static final Color DEFAULT_COLOR = Color.white;

    private boolean m_Required;
    private int m_MaxCharacters;
    private Dimension m_ChachedPreferedSize;
    private final List<ValidityChangeListener> m_ValidityChangeListeners;

    public JSingleLineTextField()
    {
        super();
        m_MaxCharacters =  Util.DEFAULT_MAX_STRING_LENGTH;
        m_ValidityChangeListeners = new CopyOnWriteArrayList<ValidityChangeListener>();
        addPropertyChangeListener("text", this);
    }


    public JSingleLineTextField(String s)
    {
        super(s);
        m_MaxCharacters =  Util.DEFAULT_MAX_STRING_LENGTH;
        m_ValidityChangeListeners = new CopyOnWriteArrayList<ValidityChangeListener>();
        addPropertyChangeListener("text", this);
    }
    /**
      * Constructs a new empty <code>TextField</code> with the specified
      * number of columns.
      * A default model is created and the initial string is set to
      * <code>null</code>.
      *
      * @param columns the number of columns to use to calculate
      *                the preferred width; if columns is set to zero, the
      *                preferred width will be whatever naturally results from
      *                the component implementation
      */
     public JSingleLineTextField(int columns)
     {
         this(null, null, columns);
     }

     /**
      * Constructs a new <code>TextField</code> initialized with the
      * specified text and columns.  A default model is created.
      *
      * @param text    the text to be displayed, or <code>null</code>
      * @param columns the number of columns to use to calculate
      *                the preferred width; if columns is set to zero, the
      *                preferred width will be whatever naturally results from
      *                the component implementation
      */
     public JSingleLineTextField(String text, int columns)
     {
         this(null, text, columns);
      }

     /**
      * Constructs a new <code>JTextField</code> that uses the given text
      * storage model and the given number of columns.
      * This is the constructor through which the other constructors feed.
      * If the document is <code>null</code>, a default model is created.
      *
      * @param doc     the text storage to use; if this is <code>null</code>,
      *                a default will be provided by calling the
      *                <code>createDefaultModel</code> method
      * @param text    the initial string to display, or <code>null</code>
      * @param columns the number of columns to use to calculate
      *                the preferred width >= 0; if <code>columns</code>
      *                is set to zero, the preferred width will be whatever
      *                naturally results from the component implementation
      * @throws IllegalArgumentException if <code>columns</code> < 0
      */
     public JSingleLineTextField(Document doc, String text, int columns)
     {
         super(doc, text, columns);
         m_MaxCharacters =  Util.DEFAULT_MAX_STRING_LENGTH;
         addPropertyChangeListener("text", this);
         m_ValidityChangeListeners = new CopyOnWriteArrayList<ValidityChangeListener>();
     }

    public int getMaxCharacters() {
        return m_MaxCharacters;
    }

    public void setMaxCharacters(int pMaxCharacters) {
        m_MaxCharacters = pMaxCharacters;
    }

    public boolean isValid()
    {
        if(!isLegal(getText()))
            return false;
        int len = getText().length();
        return len > 0;
    }

    public void setBackground(Color bg) {
        super.setBackground(bg);

    }

    /**
     * set the text - presumably to an old value - then
     * reset caret
     * @param oldText
     */
    protected void resetText(String oldText)
    {
        int pos = getCaretPosition();
        setText(oldText);
        pos = Math.min(pos,oldText.length());
        setCaretPosition(pos);
    }


    /**
     * add a change listener
     *
     * @param added non-null change listener
     */
    public void addValidityChangeListener(ValidityChangeListener added)
    {
        if (!m_ValidityChangeListeners.contains(added))
            m_ValidityChangeListeners.add(added);
    }

    /**
     * remove a change listener
     *
     * @param removed non-null change listener
     */
    public void removeValidityChangeListener(ValidityChangeListener removed)
    {
        while (m_ValidityChangeListeners.contains(removed))
            m_ValidityChangeListeners.remove(removed);
    }


    /**
     * notify any state change listeners - probably should
     * be protected but is in the interface to form an event cluster
     *
     * @param oldState
     * @param newState
     * @param commanded
     */
    public void notifyValidityChangeListeners(boolean valid)
    {
        if (m_ValidityChangeListeners.isEmpty())
            return;
        for (ValidityChangeListener listener : m_ValidityChangeListeners) {
            listener.onValidityChange(this, valid);
        }
    }

     /**
     * when the field is required it shows when text is not present
     *
     * @return as above
     */
    public boolean isRequired()
    {
        return m_Required;
    }

    /**
     * when the field is required it shows when text is not present
     *
     * @param pRequired as above
     */
    public void setRequired(boolean pRequired)
    {
        boolean oldRequired = m_Required;
        if (oldRequired == pRequired)
            return;
        m_Required = pRequired;
        if (pRequired)
            setRequiredColor();
        else
            setBackground(DEFAULT_COLOR);
    }

    /**
     * This should stop vertical resizing
     */
    public Dimension getMinimumSize()
    {
        Dimension minSize = getPreferredSize();
        return minSize;
    }

    /**
     * This should stop vertical resizing
     */
    public Dimension getPreferredSize()
    {
        if (m_ChachedPreferedSize != null)
            return m_ChachedPreferedSize;
        m_ChachedPreferedSize = super.getPreferredSize();
        return m_ChachedPreferedSize;
    }


    /**
     * This should stop vertical resizing
     */
    public Dimension getMaximumSize()
    {
        Dimension maxSize = super.getMaximumSize();
        Dimension prefSize = getPreferredSize();
        return new Dimension(maxSize.width, prefSize.height);
    }

    public void processKeyEvent(KeyEvent ev)
    {
        // probably only typed events to things
        int id = ev.getID();
        int keyCode = ev.getKeyCode();


        boolean wasValid = isValid();
        String oldText  = getText();
        char c = ev.getKeyChar();
        if(oldText == null)
             oldText = "";
        if(isInsertableCharacter(c)) {
            super.processKeyEvent(ev);
            if(!isLegal(getText()))  {
                setCaretPosition(Math.max(getCaretPosition() - 1,0));
                resetText(oldText);
                AWTUtil.doBeep();
            }
            boolean nowValid = isValid();
            if (nowValid != wasValid)
                notifyValidityChangeListeners(nowValid);
            if (isRequired())
                setRequiredColor();
            return;
        }
        if(KeyEvent.VK_PASTE == keyCode) {
              super.processKeyEvent(ev);
                if(!isLegal(getText()))     {
                    resetText(oldText);
                    AWTUtil.doBeep();
                }
              return;
          }

        super.processKeyEvent(ev);
        if(!isLegal(getText()))     {
            resetText(oldText);
            AWTUtil.doBeep();
        }
        if (isRequired())
            setRequiredColor();
        if(wasValid != isValid()) {
            notifyValidityChangeListeners(isValid());
        }
    }

    protected static void consomeEvent(KeyEvent ev)
    {
        ev.consume();
        if(ev.getID() == KeyEvent.KEY_TYPED)
            AWTUtil.doBeep();
     }

    /**
     * we actually will enter this key
     *
     * @param ev
     */
    protected void performKeyProcess(KeyEvent ev) {
        boolean wasValid = isValid();
        super.processKeyEvent(ev);
        boolean nowValid = isValid();
        if (nowValid != wasValid)
            notifyValidityChangeListeners(nowValid);


    }


    public static boolean isInsertableCharacter(char c)
    {
        if(Character.isJavaIdentifierPart(c))
            return true;
        if(Character.isWhitespace(c))
            return true;
        return false;
    }

    public void setText(String t)
    {
        if(t != null && t.length() > getMaxCharacters()) {
            AWTUtil.doBeep();
            return;
        }
        synchronized (this) {
             super.setText(t);
            setRequiredColor();
        }
    }

    public String getText()
    {
        return super.getText();
    }

    /**
     * true if test is a legal value -
     *   this does not say valid sinse "-" is a legal
     *   int value of negative is allowed
     * @param text
     * @return
     */
    public boolean isLegal(String text)
    {
        int len = getText().length();
        return   len <= getMaxCharacters();

    }


    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */

    public void propertyChange(PropertyChangeEvent evt)
    {
        setRequiredColor();

    }

    protected void setRequiredColor()
    {
        Color oldColor = getBackground();
        boolean valid = true;
        if (isRequired()) {
            String s = getText();
            Color newColor = DEFAULT_COLOR;
            if (StringOps.isBlank(s)) {
                newColor = REQUIRED_COLOR;
                valid = false;
            }

           if(newColor.equals(oldColor))
             return;
            setBackground(newColor);
            notifyValidityChangeListeners(true);
        }
    }

}

