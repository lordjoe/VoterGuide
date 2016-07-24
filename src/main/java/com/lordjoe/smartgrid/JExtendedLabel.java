/* 
    file JExtendedLabel.java
	************************
	*  Copyright (c) 98
	*  Steven M. Lewis
	*  smlewis@LordJoe.com
	*  www.LordJoe.com
	************************
*/
package com.lordjoe.smartgrid;

import javax.swing.*;
import javax.swing.plaf.basic.*;

import java.awt.*;
import java.util.*;


public class JExtendedLabel extends JLabel
    {
    public JExtendedLabel (String text, Icon icon, int horizontalAlignment)
        {
    	setText(text);
    	setIcon(icon);
        setVerticalAlignment(TOP);
        setVerticalTextPosition(TOP);
    	setHorizontalAlignment(horizontalAlignment);
    	setUI(new ExtendedLabelUI());

//    	super(text, icon, horizontalAlignment);
        }

    public JExtendedLabel (String text, int horizontalAlignment)
        {
        this(text, null, horizontalAlignment);
        }

    public JExtendedLabel (String text)
        {
        this(text, null, CENTER);
        }

    public JExtendedLabel (Icon image, int horizontalAlignment)
        {
        this(null, image, horizontalAlignment);
        }

    public JExtendedLabel (Icon image)
        {
        this(null, image, CENTER);
        }

    public JExtendedLabel ()
        {
        this(null, null, CENTER);
        }

}
class ExtendedLabelUI extends BasicLabelUI
    {
    protected void paintEnabledText(JLabel l, Graphics g, String s, int textX, int textY)
        {
 //   	char accChar = l.getDisplayedKeyAccelerator();
    	g.setColor(l.getForeground());
    	g.drawString(s, textX, textY);
        }

  protected void paintDisabledText(JLabel l, Graphics g, String s, int textX, int textY)
        {
  //  	char accChar = l.getDisplayedKeyAccelerator();
    	Color background = l.getBackground();
    	g.setColor(background.brighter());
    	g.drawString(s, textX, textY);
    	g.setColor(background.darker());
    	g.drawString(s, textX + 1, textY + 1);
        }

    public void paint(Graphics g, JComponent c)
        {
        JLabel label = (JLabel)c;
    	String text = label.getText();

    	Icon icon;
    	if(label.isEnabled()) {
    	    icon = label.getIcon();
    	} else {
    	    icon = label.getDisabledIcon();
    	}

    	if ((icon == null) && (text == null)) {
    	    return;
    	}
    	g.setFont(label.getFont());
     	FontMetrics fm = g.getFontMetrics();
        Rectangle textR = new Rectangle();
    	Rectangle viewR = new Rectangle(c.getSize());
    	Insets viewInsets = c.getInsets();
    	viewR.x = viewInsets.left;
    	viewR.y = viewInsets.top;
    	viewR.width -= (viewInsets.left + viewInsets.right);
    	viewR.height -= (viewInsets.top + viewInsets.bottom);
        int cyOffset = 0;

        StringTokenizer str = new StringTokenizer(text, "\n\r");

        while (str.hasMoreTokens())
            {
    	    Rectangle iconR = new Rectangle();

        	String clippedText = layoutCL(label, fm, str.nextToken(), icon, viewR, iconR, textR);

        	if (icon != null) {
        	    //icon.paint(g, iconR.x, iconR.y);
        	}

        	if (text != null) {
        	    int textX = textR.x;
        	    int textY = textR.y + fm.getAscent() + cyOffset;
        	    char accChar = 0;//label.getRepresentedKeyAccelerator();

        	    if (label.isEnabled()) {
        		g.setColor(label.getForeground());
                g.drawString(clippedText, textX, textY);
        		//BasicGraphicsUtils.drawString(g, clippedText, accChar, textX, textY);
        	    }
        	    else {
        		g.setColor(Color.gray);
                g.drawString(clippedText, textX, textY);
        		//BasicGraphicsUtils.drawString(g, clippedText, accChar, textX, textY);
        		g.setColor(Color.white);
                g.drawString(clippedText, textX + 1, textY + 1);
        		//BasicGraphicsUtils.drawString(g, clippedText, accChar, textX + 1, textY + 1);
        	    }

            cyOffset += (fm.getAscent()+ fm.getLeading());
            }

    	}
        
     }

    public Dimension getPreferredSize(JComponent c) 
    {
	JLabel label = (JLabel)c;
	String text = label.getText();
	Icon icon = label.getIcon();
	Insets viewInsets = c.getInsets();

	if ((icon == null) && (text == null)) {
	    return new Dimension();
	}

	if (text == null) {
	    return new Dimension(0, 0);
	}
	else {
	    Font font = label.getFont();

	    if (font == null) {
		return new Dimension(0,0);
	    }

	    FontMetrics fm = label.getToolkit().getFontMetrics(font);

  	    Rectangle iconR = new Rectangle();	    
	    Rectangle textR = new Rectangle();
	    Rectangle viewR = new Rectangle(Short.MAX_VALUE, Short.MAX_VALUE);

        StringTokenizer str = new StringTokenizer(text, "\n\r");
        int cyOffset = 0;
        int cxOffset = 0;

        while (str.hasMoreTokens())
            {
            String token = str.nextToken();
            int width = fm.stringWidth(token) + viewInsets.left + viewInsets.right;
    	//    layoutCL(label, fm, token, icon, viewR, iconR, textR);
        //    cxOffset = Math.max(cxOffset,textR.width);

            cyOffset += (fm.getAscent() + fm.getLeading());
            cxOffset = Math.max(cxOffset,width);
            }

	    return new Dimension(cxOffset, cyOffset  + viewInsets.top + viewInsets.bottom);
	}
    }


}
