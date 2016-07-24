package com.lordjoe.ui;
/*
 * Copyright (c) Ian F. Darwin, http://www.darwinsys.com/, 1996-2002.
 * All rights reserved. Software written by Ian F. Darwin and others.
 * $Id: JClockComponent.java,v 1.2 2008/05/30 01:06:58 smlewis Exp $
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS''
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * Java, the Duke mascot, and all variants of Sun's Java "steaming coffee
 * cup" logo are trademarks of Sun Microsystems. Sun's, and James Gosling's,
 * pioneering role in inventing and promulgating (and standardizing) the Java
 * language and environment is gratefully acknowledged.
 *
 * The pioneering role of Dennis Ritchie and Bjarne Stroustrup, of AT&T, for
 * inventing predecessor languages C and C++ is also gratefully acknowledged.
 */

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * com.lordjoe.ui.JClockComponent
 *
 * @author Steve Lewis
 * @date May 29, 2008
 */
public class JClockComponent extends javax.swing.JComponent
{
    public static final int DEFAULT_HEIGHT = 15;
    public static final int DEFAULT_WIDTH = 100;
    public static final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 20);
    protected DecimalFormat tflz, tf;

    protected boolean done = false;

    public JClockComponent()
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                while (!done) {
                    JClockComponent.this.repaint(); // request a redraw
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) { /* do nothing */
                    }
                }
            }
        }).start();
        tf = new DecimalFormat("#0");
        tflz = new DecimalFormat("00");
    }

    public void stop()
    {
        done = true;
    }

    /* paint() - get current time and draw (centered) in Component. */
    public void paint(Graphics g)
    {
        g.setColor(getBackground());
        Dimension size = getSize();
        Insets insets = getInsets();
        g.fillRect(insets.left,insets.top,size.width - insets.left - insets.right,
               size.height - insets.top - insets.bottom );
        g.setColor(getForeground());
        Calendar myCal = Calendar.getInstance();
        StringBuffer sb = new StringBuffer();
        sb.append(tf.format(myCal.get(Calendar.HOUR)));
        sb.append(':');
        sb.append(tflz.format(myCal.get(Calendar.MINUTE)));
        sb.append(':');
        sb.append(tflz.format(myCal.get(Calendar.SECOND)));
        String s = sb.toString();
        Font font1 = g.getFont();
        g.setFont(DEFAULT_FONT);
        FontMetrics fm = getFontMetrics(DEFAULT_FONT);
        Dimension dimension = getSize();

        int x = (dimension.width - fm.stringWidth(s)) / 2;
        // System.out.println("Size is " + getSize());
        g.drawString(s, x, fm.getHeight() );
        g.setFont(font1);
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public Dimension getMinimumSize()
    {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}
