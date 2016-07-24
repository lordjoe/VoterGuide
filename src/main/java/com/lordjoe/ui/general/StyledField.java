package com.lordjoe.ui.general;

/*
 * This tests StyledField - a simple version of TextPane
	************************
	*  Copyright (c) 1996,97,98
	*  Steven M. Lewis
	*  www.LordJoe.com
	************************
Public Interface

public setCurrentColor(Color in)  - set the color used for
    future additions to the field
public void setCurrentFont(String Family,int size,int Attributes)  -
    set the font used for future additions - note the Font object is
    generated internally
public void setCurrentBold(boolean doit) - set future additions to
    be bold or not
public void setCurrentItalic (boolean doit) - set future additions
    to be italic or not
public void append(String str) - add str to the end of text in
    the field using the current attributes
public void insert(String st , int pos) - insert str at character
    position pos using the current attributes
*/

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

/**
 * Version of JTextPane which exposes Style, Font, Color
 */
public class StyledField extends JTextPane
{
    private Style m_CurrentAttributes;

    /**
     * default constructor
     */
    public StyledField()
    {
        super();
        //  super(new DefaultStyledDocument());
        m_CurrentAttributes = getStyle(StyleContext.DEFAULT_STYLE);
    }

    /**
     * constructor
     *
     * @param text - text to add
     */
    public StyledField(String text)
    {
        this();
        setText(text);
    }

    /**
     * Set the current text color
     *
     * @param in - new color
     */
    public void setCurrentColor(Color in)
    {
        StyleConstants.setForeground(m_CurrentAttributes, in);
    }


    /**
     * Set the current font
     *
     * @param Family     - font family
     * @param size       - point zise
     * @param Attributes - Font.BOLD + Font.ITALIC
     */
    public void setCurrentFont(String Family, int size, int Attributes)
    {
        StyleConstants.setFontFamily(m_CurrentAttributes, Family);
        StyleConstants.setFontSize(m_CurrentAttributes, size);
        StyleConstants.setBold(m_CurrentAttributes,
                ((Attributes & Font.BOLD) != 0));
        StyleConstants.setItalic(m_CurrentAttributes,
                ((Attributes & Font.ITALIC) != 0));
    }

    /**
     * Set the current bold mode
     *
     * @param doit - true if bold
     */
    public void setCurrentBold(boolean doit)
    {
        StyleConstants.setBold(m_CurrentAttributes, doit);
    }

    /**
     * Set the current italic mode
     *
     * @param doit - true if italic
     */
    public void setCurrentItalic(boolean doit)
    {
        StyleConstants.setItalic(m_CurrentAttributes, doit);
    }


    /**
     * insert text
     *
     * @param str - text to insert
     * @param pos - location
     */
    public void insert(String str, int pos)
    {
        Document doc = getDocument();
        if (doc != null) {
            try {
                doc.insertString(pos, str, m_CurrentAttributes);
            }
            catch (BadLocationException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    /**
     * clea the document
     */
    public void clear()
    {
        Document doc = getDocument();
        if (doc != null) {
            doClear(doc, 0);
        }
    }

    /**
     * actually clear the document
     */
    protected void doClear(Document doc, int retry)
    {
        int length = doc.getLength();
        if (length == 0)
            return;
        try {
            doc.remove(0, length);
        }
        catch (BadLocationException ex) {
            if (retry < 2)
                doClear(doc, retry++);
        }
    }


    /**
     * Appends the given text to the end of the document.  Does nothing if
     * the model is null or the string is null or empty.
     *                                                                                          <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see
     * <A HREF="http://java.sun.com/products/jfc/swingdoc-archive/threads.html">Threads
     * and Swing</A> for more information.
     *
     * @param str the text to insert
     * @see #insert
     */
    public void append(String str)
    {
        Document doc = getDocument();
        if (doc != null) {
            try {
                doc.insertString(doc.getLength(), str, m_CurrentAttributes);
            }
            catch (BadLocationException e) {
            }
        }
    }

// end class StyledField
}
