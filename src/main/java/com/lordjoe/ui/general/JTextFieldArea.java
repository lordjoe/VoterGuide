package com.lordjoe.ui.general;

import com.lordjoe.general.UIUtilities;
import com.lordjoe.lang.StringOps;
import com.lordjoe.utilities.AWTUtil;
import com.lordjoe.utilities.Util;
import com.lordjoe.utilities.ValidityChangeListener;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * com.lordjoe.ui.general.JSingleLineTextField
 * This version of a TextField will not grow vertically
 * Developed to aid in layout
 *
 * @date Mar 1, 2005
 */
public class JTextFieldArea extends JTextArea implements PropertyChangeListener,ComponentListener {
    public static final Class THIS_CLASS = JSingleLineTextField.class;
    public static final JTextFieldArea[] EMPTY_ARRAY = {};
    public static final int DEFAULT_WIDTH = 100;
    public static final int INSET_SIZE = 40;


    public static final Color REQUIRED_COLOR = new Color(255, 230, 230);
    public static final Color DEFAULT_COLOR = Color.white;
    public static final Font DEFAULT_FONT =  UIManager.getFont("Label.font");

    private boolean m_Required;
    private int m_MaxCharacters;
    private JFrame m_ParentFrame;
    private int m_PreferredWidth;
    private final List<ValidityChangeListener> m_ValidityChangeListeners;

    public JTextFieldArea() {
        super();
        m_MaxCharacters = Util.DEFAULT_MAX_STRING_LENGTH;
        m_ValidityChangeListeners = new CopyOnWriteArrayList<ValidityChangeListener>();
        addPropertyChangeListener("text", this);
        initComponent();
    }


    public JTextFieldArea(String s) {
        super(s);
        m_MaxCharacters = Util.DEFAULT_MAX_STRING_LENGTH;
        m_ValidityChangeListeners = new CopyOnWriteArrayList<ValidityChangeListener>();
        addPropertyChangeListener("text", this);
        initComponent();
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
    public JTextFieldArea(int columns, int rows) {
        this(null, null, columns, rows);
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
    public JTextFieldArea(String text, int columns) {
        this(null, text, columns, 1);
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
    public JTextFieldArea(String text, int columns, int rows) {
        this(null, text, columns, rows);
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
    public JTextFieldArea(Document doc, String text, int columns, int rows) {
        super(doc, text, columns, rows);
        m_MaxCharacters = Util.DEFAULT_MAX_STRING_LENGTH;
        addPropertyChangeListener("text", this);
        m_ValidityChangeListeners = new CopyOnWriteArrayList<ValidityChangeListener>();
        initComponent();

    }

    protected void initComponent() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setFont(DEFAULT_FONT);
        // Add actions
        // see http://www.exampledepot.com/egs/javax.swing.text/ta_OverrideTab.html
        getActionMap().put(nextFocusAction.getValue(Action.NAME), nextFocusAction);
        getActionMap().put(prevFocusAction.getValue(Action.NAME), prevFocusAction);
        // make tab move rather than insert
        int id = KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS;
        setFocusTraversalKeys(id,
                                 KeyboardFocusManager.getCurrentKeyboardFocusManager().getDefaultFocusTraversalKeys(id));
        id = KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS;
                setFocusTraversalKeys(id,
                                         KeyboardFocusManager.getCurrentKeyboardFocusManager().getDefaultFocusTraversalKeys(id));
        id = KeyboardFocusManager.DOWN_CYCLE_TRAVERSAL_KEYS;
                setFocusTraversalKeys(id,
                                         KeyboardFocusManager.getCurrentKeyboardFocusManager().getDefaultFocusTraversalKeys(id));
        id = KeyboardFocusManager.UP_CYCLE_TRAVERSAL_KEYS;
                setFocusTraversalKeys(id,
                                         KeyboardFocusManager.getCurrentKeyboardFocusManager().getDefaultFocusTraversalKeys(id));


    }


    public int getMaxCharacters() {
        return m_MaxCharacters;
    }

    public void setMaxCharacters(int pMaxCharacters) {
        m_MaxCharacters = pMaxCharacters;
    }

    public boolean isValid() {
        if (!isLegal(getText()))
            return false;
        return getText().length() > 0;
    }

    /**
     * true if test is a legal value -
     * this does not say valid sinse "-" is a legal
     * int value of negative is allowed
     *
     * @param text
     * @return
     */
    public boolean isLegal(String text) {
        int len = getText().length();
        return len <= getMaxCharacters();

    }


    public void setBackground(Color bg) {
        super.setBackground(bg);

    }

    // Add to constructor
    //


    /**
     * add a change listener
     *
     * @param added non-null change listener
     */
    public void addValidityChangeListener(ValidityChangeListener added) {
        if (!m_ValidityChangeListeners.contains(added))
            m_ValidityChangeListeners.add(added);
    }

    /**
     * remove a change listener
     *
     * @param removed non-null change listener
     */
    public void removeValidityChangeListener(ValidityChangeListener removed) {
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
    public void notifyValidityChangeListeners(boolean valid) {
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
    public boolean isRequired() {
        return m_Required;
    }

    /**
     * when the field is required it shows when text is not present
     *
     * @param pRequired as above
     */
    public void setRequired(boolean pRequired) {
        boolean oldRequired = m_Required;
        if (oldRequired == pRequired)
            return;
        m_Required = pRequired;
        if (pRequired)
            setRequiredColor();
        else
            setBackground(DEFAULT_COLOR);
    }


    public void processKeyEvent(KeyEvent ev) {
        int keyCode = ev.getKeyCode();
        int id = ev.getID();

        boolean wasValid = isValid();
        String oldText = getText();
        char c = ev.getKeyChar();
        if (oldText == null)
            oldText = "";
        if (isInsertableCharacter(c)) {
            super.processKeyEvent(ev);
            if (!isLegal(getText())) {
                setCaretPosition(Math.max(getCaretPosition() - 1, 0));
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
        if (KeyEvent.VK_PASTE == keyCode) {
            super.processKeyEvent(ev);
            if (!isLegal(getText())) {
                resetText(oldText);
                AWTUtil.doBeep();
            }
            return;
        }
        super.processKeyEvent(ev);
        if (isRequired())
            setRequiredColor();
        if (wasValid != isValid()) {
            notifyValidityChangeListeners(isValid());
        }
    }


    public static boolean isInsertableCharacter(char c) {
        if (Character.isJavaIdentifierPart(c))
            return true;
        if (Character.isWhitespace(c))
            return true;
        return false;
    }


    /**
     * set the text - presumably to an old value - then
     * reset caret
     *
     * @param oldText
     */
    protected void resetText(String oldText) {
        int pos = getCaretPosition();
        setText(oldText);
        pos = Math.min(pos, oldText.length());
        setCaretPosition(pos);
    }


    public void setText(String t) {
        if (t != null && t.length() > getMaxCharacters()) {
            AWTUtil.doBeep();
            return;
        }
        synchronized (this) {
            super.setText(t);
            setRequiredColor();
        }
    }

    public String getText() {
        return super.getText();
    }


    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */

    public void propertyChange(PropertyChangeEvent evt) {
        setRequiredColor();

    }

    protected void setRequiredColor() {
        Color oldColor = getBackground();
        boolean valid = true;
        if (isRequired()) {
            String s = getText();
            Color newColor = DEFAULT_COLOR;
            if (StringOps.isBlank(s)) {
                newColor = REQUIRED_COLOR;
                valid = false;
            }
            if (newColor.equals(oldColor))
                return;
            setBackground(newColor);
            notifyValidityChangeListeners(true);
        }
    }



    public int getPreferredWidth() {
        return Math.max(DEFAULT_WIDTH, m_PreferredWidth);
    }

    public void setPreferredWidth(int pPreferredWidth) {
        m_PreferredWidth = pPreferredWidth;
    }

    public synchronized JFrame getParentFrame() {
        if(m_ParentFrame == null)   {
            JFrame fr = (JFrame) UIUtilities.getTopAncestor(this);
            if(fr != null)
                setParentFrame(fr);
        }
        return m_ParentFrame;
    }

    public synchronized void setParentFrame(JFrame pParentFrame) {
        if(m_ParentFrame != null)
            throw new IllegalStateException("cannot reset parent frame"); // ToDo change
        m_ParentFrame = pParentFrame;
        m_ParentFrame.addComponentListener(this);
        sizeFromComponent(m_ParentFrame);
    }



    public void componentResized(ComponentEvent e) {
        Component component1 = e.getComponent();
        sizeFromComponent(component1);

    }

    private void sizeFromComponent(Component pComponent1) {
        Dimension dimension = pComponent1.getSize();
        int preferedWidth = Math.max(DEFAULT_WIDTH,dimension.width - INSET_SIZE);
        setPreferredWidth(preferedWidth);
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    protected int getPreferredHeight()
    {
        Dimension preferredSize = super.getPreferredSize();
        return  preferredSize.height;
    }



    public Dimension getPreferredSize() {
      return new Dimension(getPreferredWidth(),getPreferredHeight());
   }



    // The actions
    public Action nextFocusAction = new AbstractAction("Move Focus Forwards") {
        public void actionPerformed(ActionEvent evt) {
            ((Component) evt.getSource()).transferFocus();
        }
    };
    public Action prevFocusAction = new AbstractAction("Move Focus Backwards") {
        public void actionPerformed(ActionEvent evt) {
            ((Component) evt.getSource()).transferFocusBackward();
        }
    };


}