/**{ file
 @name AWTUtil.java
 @function this class - a Nullity implements a large number of
 useful functions
 @author> Steven M. Lewis
 @copyright>
  ************************
  *  Copyright (c) 1996,97,98
  *  Steven M. Lewis
  *  www.LordJoe.com
 ************************

 @date> Mon Jun 22 21:48:24 PDT 1998
 @version> 1.0
 }*/
package com.lordjoe.utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * { class
 *
 * @name Util
 * @function this class - a Nullity implements a large number of
 * useful functions
 * }
 */
abstract public class AWTUtil extends Nulleton {
    // most systems use this as the default
    private static Font gSysDefaultFont = new Font("Dialog", 12, Font.PLAIN);
    private static Component gDefaultComponent;

    public static void setDefaultComponent(Component TheComponent) {
        gDefaultComponent = TheComponent;
    }

    public static void doBeep()
    {
        Toolkit.getDefaultToolkit().beep();
    }

    /**
     * { method
     *
     * @return the Font
     *         }
     * @name defaultFont
     * @function return the system default font
     */
    public static Font defaultFont() {
        Graphics g = defaultGraphics();
        if (g != null)
            return (g.getFont());
        return (gSysDefaultFont);
    }

    /**
     * { method
     *
     * @return the Font
     *         }
     * @name defaultFontHeight
     * @function return the height of the system default font
     */
    @SuppressWarnings(value = "deprecated")
    public static int defaultFontHeight() {
        Font f = defaultFont();
        FontMetrics metrics = Toolkit.getDefaultToolkit().getFontMetrics(f);
        return (metrics.getHeight());
    }

    /**
     * go to parent that is not a scroll pane
     * @param jc
     * @return
     */
    public static Container getParentPanel(JComponent jc) {
        Container parent = jc.getParent();
        while (parent != null) {
            if(parent instanceof JPanel)
                return parent;
            if(parent instanceof JFrame)
                return parent;
            if(parent instanceof JDialog)
                return parent;
            if(parent instanceof JRootPane)
                return parent;
            parent = parent.getParent();
        }
        return null;
    }

    /**
     * { method
     *
     * @return the Graphics
     *         }
     * @name defaultGraphics
     * @function return the system default Graphics
     */
    public static Graphics defaultGraphics() {
        return (gDefaultComponent.getGraphics());
    }

    /**
     * { method
     *
     * @param text the string
     * @param f    Font to use
     * @return height + width
     *         }
     * @name measureText
     * @function measure a text string in some font -
     * useful for non awt objects
     */
    public static Dimension measureText(String text, Font f) {
        Graphics g = defaultGraphics();
        if (g == null) {
            return (new Dimension(10, text.length() * 10));
        }
        FontMetrics metrics = g.getFontMetrics(f);
        g.dispose();
        return (new Dimension(metrics.stringWidth(text), metrics.getHeight()));
    }

    /**
     * { method
     *
     * @param text the string
     * @param f    Font to use
     * @return height + width
     *         }
     * @name measureText
     * @function measure a text string in some font -
     * useful for non awt objects
     */
    @SuppressWarnings(value = "deprecated")
    public static Dimension fontSize(Font f) {
        Toolkit g = Toolkit.getDefaultToolkit(); // defaultGraphics();
        if (g == null) {
            return (new Dimension(10, 10));
        }
        FontMetrics metrics = g.getFontMetrics(f);
        return (new Dimension(metrics.charWidth('W'), metrics.getHeight()));
    }


    /**
     * { method
     *
     * @param text the string
     * @return height + width
     *         }
     * @name measureText
     * @function measure a text string in the default font -
     * useful for non awt objects
     */
    public static Dimension measureText(String text) {
        return (measureText(text, defaultFont()));
    }

    public static Dimension measureMultiLineText(String text, Graphics G) {
        FontMetrics metrics = G.getFontMetrics();
        if (metrics == null) {
            return (new Dimension(0, 0));
        }
        return (measureMultiLineText(text, metrics));
    }

    @SuppressWarnings(value = "deprecated")
    public static Dimension measureMultiLineText(String text, Component c) {
        Font fn = c.getFont();
        FontMetrics metrics = Toolkit.getDefaultToolkit().getFontMetrics(fn);
        if (metrics == null) {
            return (new Dimension(0, 0));
        }
        return (measureMultiLineText(text, metrics));
    }

    public static Dimension measureMultiLineText(String text, FontMetrics metrics) {
        StringTokenizer str = new StringTokenizer(text, "\n\r");
        int width = 0;
        int height = 0;

        while (str.hasMoreTokens()) {
            String token = str.nextToken();
            height += (metrics.getAscent() + metrics.getLeading());
            width = Math.max(metrics.stringWidth(token), width);
        }
        return (new Dimension(width, height));
    }

    public static void drawMultiLineText(String text, Graphics G, int x, int y) {
        FontMetrics metrics = G.getFontMetrics();

        StringTokenizer str = new StringTokenizer(text, "\n\r");
        int height = y;

        while (str.hasMoreTokens()) {
            String token = str.nextToken();

            G.drawString(token, x, height + metrics.getAscent());
            height += (metrics.getAscent() + metrics.getLeading());
        }
    }

    public static void drawRightMultiLineText(String text, Graphics G, int x, int y) {
        FontMetrics metrics = G.getFontMetrics();
        Dimension size = measureMultiLineText(text, G);

        StringTokenizer str = new StringTokenizer(text, "\n\r");
        int height = y;

        while (str.hasMoreTokens()) {
            String token = str.nextToken();

            G.drawString(token, x + size.width - metrics.stringWidth(token),
                    height + metrics.getAscent());
            height += (metrics.getAscent() + metrics.getLeading());
        }
    }

    public static void centerMultiLineText(String text, Graphics G, Component c) {
        Font oldFont = G.getFont();
        synchronized (G) {
            G.setFont(c.getFont());
            centerMultiLineText(text, G, new IRect(c.getSize()));
            G.setFont(oldFont);
        }
    }

    public static void centerMultiLineText(String text, Graphics G, IRect r) {
        FontMetrics metrics = G.getFontMetrics();
        Dimension size = measureMultiLineText(text, G);

        StringTokenizer str = new StringTokenizer(text, "\n\r");
        int height = r.y + (r.height - size.height) / 2;
        int x = r.x + (r.width - size.width) / 2;

        while (str.hasMoreTokens()) {
            String token = str.nextToken();
            int dx = (size.width - metrics.stringWidth(token)) / 2;
            G.drawString(token, x + dx, height + metrics.getAscent());
            height += (metrics.getAscent() + metrics.getLeading());
        }
    }

    public static void addMouseListenerTree(Component target, MouseListener l) {
        target.addMouseListener(l);
        if (target instanceof Container) {
            Container CTarget = (Container) target;
            for (int i = 0; i < CTarget.getComponentCount(); i++) {
                addMouseListenerTree(CTarget.getComponent(i), l);
            }
        }
    }

    public static void addMouseMotionListenerTree(Component target, MouseMotionListener l) {
        target.addMouseMotionListener(l);
        if (target instanceof Container) {
            Container CTarget = (Container) target;
            for (int i = 0; i < CTarget.getComponentCount(); i++) {
                addMouseMotionListenerTree(CTarget.getComponent(i), l);
            }
        }
    }

    void addKeyListenerTree(Component target, KeyListener l) {
        target.addKeyListener(l);
        if (target instanceof Container) {
            Container CTarget = (Container) target;
            for (int i = 0; i < CTarget.getComponentCount(); i++) {
                addKeyListenerTree(CTarget.getComponent(i), l);
            }
        }
    }

    public static final Color[] gDefinedColors = {
            Color.black,
            Color.blue,
            Color.cyan,
            Color.darkGray,
            Color.gray,
            Color.green,
            Color.lightGray,
            Color.magenta,
            Color.orange,
            Color.pink,
            Color.red,
            Color.white,
            Color.yellow
    };

    // Colors suitable for dark backgrounds or
    // dark text on light background
    public static final Color[] gDarkColors = {
            Color.black,
            Color.blue,
            Color.darkGray,
            Color.green,
            Color.magenta,
            Color.orange,
            Color.red,
    };

    // Colors suitable for light backgrounds
    public static final Color[] gLightColors = {
            Color.cyan,
            Color.lightGray,
            Color.magenta,
            Color.orange,
            Color.pink,
            Color.white,
            Color.yellow
    };

    public static Color randomLightColor() {
        return ((Color) Util.randomElement(gLightColors));
    }

    public static Color randomDarkColor() {
        return ((Color) Util.randomElement(gDarkColors));
    }

    public static Color randomColor() {
        return ((Color) Util.randomElement(gDefinedColors));
    }

    /**
     * { method
     *
     * @param start     - Component to test
     * @param testClass - class sought
     * @return - first ancestor of the desired class - null if none exist
     *         }
     * @name getAncestorOfClass
     * @function - run up the Component hierarchy looking for a specific class. For example
     * to get the enclosing Frame call Util.getAncestorOfClass(test,Frame.class)
     */
    public static Component getAncestorOfClass(Component start, Class testClass) {
        Component testContainer = start;
        while (testContainer != null) {
            if (testClass.isInstance(testContainer))
                return ((Container) testContainer);
            testContainer = testContainer.getParent();
        }
        return (null); // failure
    }

    /**
     * { method
     *
     * @param r the rectangle
     * @return the edge
     *         }
     * @name right
     * @function get rectangles right edge
     */
    public static int right(Rectangle r) {
        if (r != null) {
            return (r.x + r.width);
        }
        return (0);
    }

    /**
     * { method
     *
     * @param r the rectangle
     * @return the edge
     *         }
     * @name bottom
     * @function get rectangles bottom edge
     */
    public static int bottom(Rectangle r) {
        if (r != null) {
            return (r.y + r.height);
        }
        return (0);
    }

    /**
     * { method
     *
     * @param r the rectangle
     * @param w new value
     *          }
     * @name setRight
     * @function set rectangles right edge - holding left fixed
     */
    public static void setRight(Rectangle r, int w) {
        if (r != null) {
            r.width = r.x + w;
        }
    }

    /**
     * { method
     *
     * @param r the rectangle
     * @param h new value
     *          }
     * @name setBottom
     * @function set rectangles bottom edge - holding top fixed
     */
    public static void setBottom(Rectangle r, int h) {
        if (r != null) {
            r.height = r.y + h;
        }
    }

    /**
     * { method
     *
     * @param s input string may be null
     * @return a Color where the items are red, green, blue
     *         }
     * @name parseColor
     * @function - convert a String like 128, 27, 54 to a color
     */
    public static Color parseColor(String s) {
        if (s == null || s.length() == 0) {
            return (Color.black);
        }
        String[] tokens = Util.parseTokenDelimited(s, ',');
        int red = 0;
        int blue = 0;
        int green = 0;
        try {
            switch (tokens.length) {
                case 3:
                    blue = Integer.parseInt(tokens[2].trim());
                case 2:
                    green = Integer.parseInt(tokens[1].trim());
                case 1:
                    red = Integer.parseInt(tokens[0].trim());
            }
            return (new Color(red, green, blue));
        }
        catch (NumberFormatException ex) {
            return (null);
        }
    }

// end class AWTUtil
}

