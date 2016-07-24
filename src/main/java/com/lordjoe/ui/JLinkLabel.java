package com.lordjoe.ui;

import com.lordjoe.lang.*;
import com.lordjoe.exceptions.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/**
 * com.lordjoe.ui.JLinkLabel
 *
 * @author Steve Lewis
 * @date Aug 23, 2006
 */
public class JLinkLabel extends JLabel
{
    public final static JLinkLabel[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = JLinkLabel.class;


    private String m_HRef;
    private String m_DisplayText;

    public JLinkLabel()
    {
        m_HRef = "";
        m_DisplayText = "";
        super.setText("<html><a href=\"\"></a></html>");
        addMouseListener(new ClickHandler());
    }

    public JLinkLabel(String s)
    {
        super();
        setText(s);
        addMouseListener(new ClickHandler());
    }


    protected String buildLinkString(String in)
    {
        return ">" + in + "</a>";
    }

    public synchronized void setText(String text)
    {
        String oldText = getText();
        String oldDisplayText = m_DisplayText;
        oldText = oldText.replaceFirst(
                buildLinkString(oldDisplayText),
                buildLinkString(text)
        );
        super.setText(oldText);
        m_DisplayText = text;

    }

    public String getHRef()
    {
        return m_HRef;
    }

    protected String buildHRefString(String in)
    {
        return "href=\"" + in + "\"";
    }

    public void setHRef(String pHRef)
    {
        String oldText = getText();
        String oldDisplayText = m_HRef;
        oldText = oldText.replaceFirst(
                buildHRefString(oldDisplayText),
                buildHRefString(pHRef)
        );
        super.setText(oldText);
        m_HRef = pHRef;
    }

    protected class ClickHandler extends MouseAdapter
    {
        /**
         * Invoked when the mouse has been clicked on a component.
         */
        public void mouseClicked(MouseEvent e)
        {
            super.mouseClicked(e);
            String href = getHRef();
            if (!StringOps.isBlank(href)) {
                try {
                    BrowserLauncher.openURL(href);
                }
                catch (IOException e1) {
                    throw new WrappingException(e1);
                }
            }
        }
    }


}
