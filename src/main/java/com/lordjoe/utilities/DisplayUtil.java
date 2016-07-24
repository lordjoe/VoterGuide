/**{ file
 @name DisplayUtil.java
 @function this class - a Nullity implements a large number of
 useful functions
 @author> Steven M. Lewis
 @copyright>
  ************************
  *  Copyright (c) 1996,97
  *  Steven M. Lewis
 ************************

 @date> Fri May 30 11:05:41  1997
 @version> 1.0
 }*/
package com.lordjoe.utilities;

import java.awt.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 * { class
 *
 * @name Util
 * @function this class - a Nullity implements a large number of
 * useful functions for rdisplay
 * }
 */
public abstract class DisplayUtil extends Nulleton
{
    // most systems use this as the default
    private static Font gSysDefaultFont = new Font("Dialog", 12, Font.PLAIN);

    public static final int LINE_SIZE = 72;


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
    public static Dimension fontSize(Font f)
    {
        Toolkit g = Toolkit.getDefaultToolkit(); // defaultGraphics();
        if (g == null) {
            return (new Dimension(10, 10));
        }
        FontRenderContext frc = new FontRenderContext(null, false, false);

        Rectangle2D bounds = f.getStringBounds("W", frc);
        double height = bounds.getHeight();
        double width = bounds.getWidth();
        return (new Dimension((int) width, (int) height));
    }

    /**
     * { method
     *
     * @param in title
     * @param c  some Component in the Frame
     *           }
     * @name setFrameTitle
     * @function Set the title of the Components Frame
     * Filtering out control characters
     */
    public static void setFrameTitle(String in, Component c)
    {
        String title = Util.filterControlCharacters(in);
        Frame f = getComponentFrame(c);
        if (f != null)
            f.setTitle(title);
    }

    /**
     * { method
     *
     * @param in title
     * @param c  some Component in the Frame
     *           }
     * @name getComponentFrame
     * @function return the frame for a component
     */
    public static Frame getComponentFrame(Component c)
    {
        Component myFrame = c;
        while (!(myFrame instanceof Frame)) {
            Component myParent = myFrame.getParent();
            if (myParent == null) {
                myFrame = null;
                break;
            }
            myFrame = myParent;
        }
        return ((Frame) myFrame);
    }


    /**
     * { method
     *
     * @param b <Add Comment Here>
     *          }
     * @name outline
     * @function <Add Comment Here>
     */
    public static void outline(Graphics g, Color c, Rectangle r, int inset, int width)
    {
        Color oldColor = g.getColor();
        g.setColor(c);
        for (int i = 0; i < width; i++) {
            g.drawRect(r.x + inset + i, r.y + inset + i, r.width - 2 * (inset + i),
                    r.height - 2 * (inset + i));
        }
        g.setColor(oldColor);
    }

    /**
     * { method
     *
     * @param b <Add Comment Here>
     *          }
     * @name outline
     * @function <Add Comment Here>
     */
    public static void outline(Graphics g, Color c, IRect r, int inset, int width)
    {
        Color oldColor = g.getColor();
        g.setColor(c);
        for (int i = 0; i < width; i++) {
            g.drawRect(r.x + inset + i, r.y + inset + i, r.width - 2 * (inset + i),
                    r.height - 2 * (inset + i));
        }
        g.setColor(oldColor);
    }

    /**
     * { method
     *
     * @param b <Add Comment Here>
     *          }
     * @name outline
     * @function <Add Comment Here>
     */
    public static void outline(Graphics g, Color c, Dimension r, int inset, int width)
    {
        Color oldColor = g.getColor();
        g.setColor(c);
        for (int i = 0; i < width; i++) {
            g.drawRect(inset + i, inset + i, r.width - 2 * (inset + i), r.height - 2 * (inset + i));
        }
        g.setColor(oldColor);
    }


    /**
     * { method
     *
     * @param b <Add Comment Here>
     *          }
     * @name makeWarnStatement
     * @function <Add Comment Here>
     */
    public static void makeErrorStatement(MessageBoxData b)
    {
        makeMessageDialog(null, b, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * { method
     *
     * @param b <Add Comment Here>
     *          }
     * @name makeWarnStatement
     * @function <Add Comment Here>
     */
    public static void makeErrorStatement(String Title, String Text)
    {
        makeErrorStatement(new MessageBoxData(Title, Text));
    }

    /**
     * { method
     *
     * @param b <Add Comment Here>
     *          }
     * @name makeWarnStatement
     * @function <Add Comment Here>
     */
    public static void makeErrorStatement(String Text)
    {
        makeErrorStatement("A Fatal Error Has Occured", Text);
    }

    /**
     * { method
     *
     * @param b <Add Comment Here>
     *          }
     * @name makeWarnStatement
     * @function <Add Comment Here>
     */
    public static void makeWarnStatement(MessageBoxData b)
    {
        makeMessageDialog(null, b, JOptionPane.WARNING_MESSAGE);
    }


    /**
     * { method
     *
     * @param b <Add Comment Here>
     *          }
     * @name makeMessageDialog
     * @function <Add Comment Here>
     */
    protected static void makeMessageDialog(JFrame modal, MessageBoxData b, int Type)
    {
        if (needsScreenLines(b.m_Text)) {
            String[] lines = findScreenLines(b.m_Text);
            JOptionPane.showMessageDialog(modal, lines, b.m_TitleText, Type);
        }
        else {
            JOptionPane.showMessageDialog(modal, b.m_Text, b.m_TitleText, Type);
        }
    }

    protected static boolean needsScreenLines(String in)
    {
        if (in.indexOf('\n') == -1)
            return (true);
        if (in.length() > LINE_SIZE)
            return (true);
        return (false);
    }


    protected static String[] findScreenLines(String in)
    {
        Vector holder = new Vector();
        StringBuffer sb = new StringBuffer(LINE_SIZE + 10);
        for (int i = 0; i < in.length(); i++) {
            char c = in.charAt(i);
            switch (c) {
                case '\n':
                    holder.addElement(sb.toString());
                    sb.setLength(0);
                    break;
                case '\r':
                    break; // ignore
                case '\t':
                    sb.append("   ");
                    break;
                default:
                    sb.append(c);
            }
            if (sb.length() >= LINE_SIZE) {
                if (c == ' ' || c == '>') {
                    holder.addElement(sb.toString());
                    sb.setLength(0);
                }
            }
        }
        if (sb.length() > 0)
            holder.addElement(sb.toString());


        return ((String[]) Util.vectorToArray(holder, String.class));
    }


    /**
     * { method
     *
     * @param b <Add Comment Here>
     *          }
     * @name makeWarnStatement
     * @function <Add Comment Here>
     */
    public static void makeWarnStatement(String Title, String Text)
    {
        makeWarnStatement(new MessageBoxData(Title, Text));
    }

    /**
     * { method
     *
     * @param b <Add Comment Here>
     *          }
     * @name makeWarnStatement
     * @function <Add Comment Here>
     */
    public static void makeWarnStatement(String Text)
    {
        makeWarnStatement("Warning", Text);
    }

    /**
     * { method
     *
     * @param b <Add Comment Here>
     *          }
     * @name makeWarnStatement
     * @function <Add Comment Here>
     */
    public static void makeStatement(MessageBoxData b)
    {
        makeMessageDialog(null, b, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * { method
     *
     * @param b <Add Comment Here>
     *          }
     * @name makeStatement
     * @function <Add Comment Here>
     */
    public static void makeStatement(String Title, String Text)
    {
        makeStatement(new MessageBoxData(Title, Text));
    }

    /**
     * { method
     *
     * @param b <Add Comment Here>
     *          }
     * @name makeStatement
     * @function <Add Comment Here>
     */
    public static void makeStatement(String Text)
    {
        makeStatement("Attention", Text);
    }

    public static Frame toFrame(Component c)
    {
        while (c != null) {
            if (c instanceof Frame)
                return ((Frame) c);
            c = c.getParent();
        }
        return (null);
    }

    public static Color toSelectionBackground(Color in)
    {
        int red = in.getRed();
        int green = in.getGreen();
        int blue = in.getBlue();
        // Algorithm does not do well for white
        if (red > 230 && green > 230 && blue > 230)
            return (Color.pink);

        int newRed = toSelectionBackComponent(red);
        int newGreen = toSelectionBackComponent(green);
        int newBlue = toSelectionBackComponent(blue);
        return (new Color(newRed, newGreen, newBlue));
    }

    public static Color toSelectionForeground(Color in)
    {
        int red = in.getRed();
        int green = in.getGreen();
        int blue = in.getBlue();

        int newRed = toSelectionForeComponent(red);
        int newGreen = toSelectionForeComponent(green);
        int newBlue = toSelectionForeComponent(blue);
        return (new Color(newRed, newGreen, newBlue));
    }

    static protected int toSelectionBackComponent(int in)
    {
        if (in < 100 || in > 150)
            return (255 - in);
        // map middle to dark
        return (255 - in + 100);
    }

    static protected int toSelectionForeComponent(int in)
    {
        if (in < 100 || in > 150)
            return (255 - in);
        // map middle to light
        return (in - 100);
    }


    public static Image createImage(int width, int height, Component TheComponent)
    {
        return (TheComponent.createImage(width, height));
    }

    public static ImageIcon makeDisabledImage(ImageIcon in)
    {
        if (in == null)
            return (null);
        Image greyImage = makeGrayImage(in.getImage());
        return (new ImageIcon(greyImage));
    }

    public static Image makeGrayImage(Image in)
    {
        return (GrayFilter.createDisabledImage(in)); // swing 05
        // return(GrayFilter.createGrayImage(in)); // swing 04
    }

    public static Image makeGrayImage2(Image in, Component TopComponent)
    {
        MediaTracker
                tracker = new MediaTracker(TopComponent);
        tracker.addImage(in, 0);
        // load it all before continuing
        try {
            tracker.waitForAll();
        }
        catch (InterruptedException e) {
            Assertion.ignoreException(e);
        }
        ;
        Assertion.validate(!tracker.isErrorID(0));

        tracker.removeImage(in);
        tracker = null;

        int width = in.getWidth(TopComponent);
        int height = in.getHeight(TopComponent);
        int NPixels = width * height;

        int[] pixels = new int[NPixels];
        PixelGrabber myPixels = new PixelGrabber(in.getSource(), 0, 0, width, height,
                pixels, 0, width);
        try {
            Assertion.validate(myPixels.grabPixels(100000));
        }
        catch (InterruptedException e) {
            return (null);
        }
        /*
          if ((myPixels.getStatus() & ImageObserver.ABORT) != 0) {
              return(null);
          }
          */
        /*
            myPixels = null; // done with this
        */

        // grey out image - this assumes upper left pixel is grey
        int GreyValue = pixels[0];
        int n = 0;
        for (int j = 0; j < height; j++) {
            int startI = j % 2;
            for (int i = startI; i < width; i += 2) {
                pixels[i + n] = GreyValue;
            }
            n += width;
        }
        MemoryImageSource GreyImage = new MemoryImageSource(width, height, pixels, 0, width);
        return (Toolkit.getDefaultToolkit().createImage(GreyImage));
    }


}
