package com.lordjoe.general;

import com.lordjoe.exceptions.WrappingException;
import com.lordjoe.lib.xml.NameValue;
import com.lordjoe.runner.DeviceFactory;
import com.lordjoe.ui.IInfoTextBuilder;
import com.lordjoe.ui.SwingExtensionFileFilter;
import com.lordjoe.ui.SwingThreadUtilities;
import com.lordjoe.ui.general.IDisplay;
import com.lordjoe.ui.general.IFeatureSizeDefiner;
import com.lordjoe.utilities.JConsole;
import com.lordjoe.utilities.ResourceString;
import com.lordjoe.utilities.Util;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;


/**
 * com.lordjoe.ui.general.UIUtilities
 *
 * @author slewis
 * @date Feb 23, 2005
 */
public abstract class UIUtilities
{
    public static final Class THIS_CLASS = UIUtilities.class;
    public static final UIUtilities EMPTY_ARRAY[] = {};

    /**
     * Suffix applied to the key used in resource file
     * lookups for an image.
     */
    public static final String JPG_IMAGE_EXTENSION = ".jpg";

    public static final String IMAGE_EXTENSION = ".gif";
    public static final String PNG_IMAGE_EXTENSION = ".png";



    public static final Map ADDED_HINTS = new HashMap();
    public static final Dimension DEFAULT_FEATURE_SIZE = new Dimension(20, 20);

    public static final int DEFAULT_CAP = BasicStroke.CAP_BUTT;
    public static final int DEFAULT_JOIN = BasicStroke.JOIN_ROUND;
    public static final int MINIMUM_COLOR_DISTANCE = 30;
    public static final int TOOLTIP_GREY = 200;
    public static final Color TOOLTIP_COLOR = new Color(TOOLTIP_GREY, TOOLTIP_GREY, TOOLTIP_GREY);

    public static final Font MONSTER_FONT = new Font("New Times Roman", Font.BOLD, 32);
    public static final Font BIG_FONT = new Font("New Times Roman", Font.BOLD, 32);
    public static final Font MID_FONT = new Font("New Times Roman", Font.BOLD, 18);

    public static final int TOP_WALL = 0;
    public static final int BOTTOM_WALL = 1;
    public static final int LEFT_WALL = 2;
    public static final int RIGHT_WALL = 3;

    public static final Color[] COLORS32 =
            {
                    new Color(0x7FFFD4), // 0
                    new Color(0xF5F5DC), // 1
                    new Color(0x7FFFD4), // 2
                    new Color(0xA52A2A), // 3
                    new Color(0x5F9EA0), // 4
                    new Color(0xD2691E), // 5
                    new Color(0x6495ED), // 6
                    new Color(0xDC1436), // 7
                    new Color(0x00008B), // 8
                    new Color(0xB8860B), // 9
                    new Color(0x006400), // 10
                    new Color(0x8B008B), // 11
                    new Color(0xFF8C00), // 12
                    new Color(0x8B0000), // 13
                    new Color(0x8FBC8F), // 14
                    new Color(0x2F4F4F), // 15
                    new Color(0x9400D3), // 16
                    new Color(0x00BFFF), // 17
                    new Color(0x1E90FF), // 18
                    new Color(0xFFFAF0), // 19
                    new Color(0xDCDCDC), // 20
                    new Color(0xFFD700), // 21
                    new Color(0xADFF2F), // 22
                    new Color(0xFF69B4), // 23
                    new Color(0x4B0082), // 24
                    new Color(0xF0E68C), // 25
                    new Color(0xFFF0F5), // 26
                    new Color(0xFFFACD), // 27
                    new Color(0xF08080), // 28
                    new Color(0xFAFAD2), // 29
                    new Color(0xD3D3D3), // 30
                    new Color(0x87CEFA), // 31
                    new Color(0xB0C4DE), // 32

            };


    // background for bad data
    public static final Color INVALID_COLOR = new Color(255, 230, 230);
    private static ImageIcon gDefaultFrameIcon;

    public static void registerComponent(JComponent c)
    {
        //ensure InputMap and ActionMap are created
        InputMap imap = c.getInputMap();
        ActionMap amap = c.getActionMap();
        //put dummy KeyStroke into InputMap if is empty:
        boolean removeKeyStroke = false;
        KeyStroke[] ks = imap.keys();
        if (ks == null || ks.length == 0) {
            imap.put(KeyStroke.getKeyStroke(
                    KeyEvent.VK_BACK_SLASH, 0), "backSlash");
            removeKeyStroke = true;
        }
        //now we can register by ToolTipManager
        ToolTipManager.sharedInstance().registerComponent(c);
        //and remove dummy KeyStroke
        if (removeKeyStroke) {
            imap.remove(KeyStroke.getKeyStroke(
                    KeyEvent.VK_BACK_SLASH, 0));
        }
        //now last part - add appropriate MouseListener and
        //hear to mouseEntered events
        // c.addMouseListener(MOUSE_HANDLER);
    }

    public static ImageIcon getImageIcon(String key)
    {
        String nm = key + PNG_IMAGE_EXTENSION;
        //       String item = getResourceString(nm);
        //       InputStream inp = getClass().getResourceAsStream(item);
        ImageIcon icon = ResourceImages.getImage(nm);
        if (icon == null) {
            icon = ResourceImages.getImage(key + IMAGE_EXTENSION);
        }
        if (icon == null) {
            icon = ResourceImages.getImage(key + JPG_IMAGE_EXTENSION);
        }

        if (icon == null)
            throw new IllegalStateException("no icon for " + nm);
        return icon;
    }

    public static NameValue[] mapToNameValues(Map<String,String> map)
    {
        List<NameValue> holder = new ArrayList<NameValue>();
        for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext();) {
            String key = iterator.next();
            NameValue nv = new NameValue(key,map.get(key));
            holder.add(nv);
        }
        NameValue[] ret = new NameValue[holder.size()];
        holder.toArray(ret);
        return ret;
    }


    public static Color getCornerColor(Image img)
    {
        return getColorAt(img, 0, 0);
    }

    public static Color getColorAt(Image img, int x, int y)
    {
        if (x >= img.getWidth(null) || y >= img.getHeight(null))
            throw new IllegalArgumentException("Outside Image");
        int[] img_data = new int[1];

        //the PixelGrabber class is used to convert
        //Images to bytes

        PixelGrabber pg = new PixelGrabber(img, x, y, 1, 1, img_data, 0, 1);
        //copy pixels into array
        try {
            pg.grabPixels();
        }
        catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        Color ret = new Color(img_data[0]);
        return ret;
    }

    public static Image makeColorTransparent(Image im, final Color color)
    {
        ImageFilter filter = new RGBImageFilter()
        {
            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = color.getRGB() | 0xFF000000;

            public final int filterRGB(int x, int y, int rgb)
            {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                }
                else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }



    public static  boolean isErrorComment(String command)
    {
        return false;
    }


    public static String limitLength(String s,int maxLen)
    {
        String[] items = s.split("\n");
        if(items.length == 1) {
            return  internalLimitLength(s,maxLen);
        }
        else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < items.length; i++) {
                String item = items[i];
                sb.append(internalLimitLength(item,maxLen));
                sb.append("\n");
            }
            return sb.toString();
        }
    }

    public static String internalLimitLength(String s,int maxLen)
    {
        if(s.length() < maxLen)
            return s;
        if(maxLen < 6)
            return s;
        return s.substring(0,(int)(maxLen - 3)) + "...";
    }


    /**
     * draw the component as an image
     *
     * @param component
     * @return
     */
    public static void saveAsJPG(File saveFile, JComponent component)
    {
        try {
            BufferedImage img = getImage(component);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(saveFile);
                JPEGImageEncoder jpeg = JPEGCodec.createJPEGEncoder(fos);
                jpeg.encode(img);
            }
            finally {
                if (fos != null)
                    fos.close();
            }
        }
        catch (IOException e) {
            throw new WrappingException(e);
        }
    }


    public static ImageIcon getDefaultFrameIcon()
    {
        return gDefaultFrameIcon;
    }

    public static void setDefaultFrameIcon(ImageIcon icon)
    {
        gDefaultFrameIcon = icon;
    }

    public static void setWindowIcon(JDialog jd)
    {
        ImageIcon frameIcon = getDefaultFrameIcon();
        if (frameIcon != null)
            jd.setIconImage(frameIcon.getImage());
    }

    public static void setWindowIcon(JFrame jd)
    {
        ImageIcon frameIcon = getDefaultFrameIcon();
        if (frameIcon != null)
            jd.setIconImage(frameIcon.getImage());
    }

    /**
     * draw the component as an image
     *
     * @param component
     * @return
     */
    public static BufferedImage getImage(JComponent component)
    {
        BufferedImage ret = new BufferedImage(component.getWidth(), component.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics g = ret.getGraphics();
        component.paint(g);
        g.dispose();
        return ret;
    }

    private static Color internalGreyColor(int value)
    {
        value = 16 * value;
        value = Math.min(255, value);
        try {
            return new Color(value, value, value);
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static int gIndex = 0;
    public static final Color[] GREYS32 =
            {
                    internalGreyColor(gIndex++), // 0
                    internalGreyColor(gIndex++), // 1
                    internalGreyColor(gIndex++), // 2
                    internalGreyColor(gIndex++), // 3
                    internalGreyColor(gIndex++), // 5
                    internalGreyColor(gIndex++), // 6
                    internalGreyColor(gIndex++), // 7
                    internalGreyColor(gIndex++), // 8
                    internalGreyColor(gIndex++), // 9
                    internalGreyColor(gIndex++), // 10
                    internalGreyColor(gIndex++), // 12
                    internalGreyColor(gIndex++), // 13
                    internalGreyColor(gIndex++), // 14
                    internalGreyColor(gIndex++), // 15
                    internalGreyColor(gIndex++), // 16
                    internalGreyColor(gIndex++), // 17
                    internalGreyColor(gIndex++), // 18
                    internalGreyColor(gIndex++), // 19
                    internalGreyColor(gIndex++), // 20
                    internalGreyColor(gIndex++), // 21
                    internalGreyColor(gIndex++), // 22
                    internalGreyColor(gIndex++), // 23
                    internalGreyColor(gIndex++), // 24
                    internalGreyColor(gIndex++), // 25
                    internalGreyColor(gIndex++), // 26
                    internalGreyColor(gIndex++), // 27
                    internalGreyColor(gIndex++), // 28
                    internalGreyColor(gIndex++), // 29
                    internalGreyColor(gIndex++), // 30
                    internalGreyColor(gIndex++), // 31
            };

    public static final String[] COLOR_NAMES32 =
            {
                    "aquamarine",   // 0
                    "beige",   // 1
                    "blanchedalmond",   // 2
                    "brown",        //3
                    "cadetblue", //4
                    "chocolate", // 5
                    "cornflowerblue", //6
                    "crimson",   //7
                    "darkblue",   //8
                    "darkgoldenrod", //9
                    "darkgreen",   //10
                    "darkmagenta",  //11
                    "darkorange",  // 12
                    "darkred",   //13
                    "darkseagreen", //14
                    "darkslategray", //15
                    "darkviolet",  // 16
                    "deepskyblue", // 17
                    "dodgerblue",  // 18
                    "floralwhite",  //19
                    "gainsboro",   //20
                    "gold",       // 21
                    "greenyellow", // 22
                    "hotpink",  // 23
                    "indigo",  //24
                    "khaki",  //25
                    "lavenderblush", //26
                    "lemonchiffon",  //27
                    "lightcoral",  // 28
                    "lightgoldenrodyellow",  //29
                    "lightgrey",  // 30
                    "lightsalmon",  //31
                    "lightskyblue",  //32
                    "lightsteelblue",  //32

            };


    private static final Map<String, Color> gNamedColors = new HashMap<String, Color>();

    public static Color getNamedColor(String name)
    {
        return gNamedColors.get(name);
    }

    public static class NamedColor
    {
        private final String m_Name;
        private final Color m_Color;

        public NamedColor(String name, Color color)
        {
            m_Name = name;
            m_Color = color;
            gNamedColors.put(name, color);
        }

        public String getName()
        {
            return m_Name;
        }

        public Color getColor()
        {
            return m_Color;
        }
    }

    public static final NamedColor[] XWINDOWS_COLORS = {
            new NamedColor("aliceblue", new Color(0xF0F8FF)),
            new NamedColor("antiquewhite",
                    new Color(0xFAEBD7)),
            new NamedColor("aquamarine",
                    new Color(0x7FFFD4)),
            new NamedColor("azure", new Color(0xF0FFFF)),
            new NamedColor("beige",
                    new Color(0xF5F5DC)),
            new NamedColor("bisque", new Color(0xFFE4C4)),
            new NamedColor("blanchedalmond",
                    new Color(0xFFEBCD)),
            new NamedColor("blueviolet",
                    new Color(0x8A2BE2)),
            new NamedColor("brown",
                    new Color(0xA52A2A)),
            new NamedColor("burlywood",
                    new Color(0xDEB887)), // >DEB887
            new NamedColor("cadetblue",
                    new Color(0x5F9EA0)), // >5F9EA0
            new NamedColor("chartreuse",
                    new Color(0x7FFF00)), // >7FFF00
            new NamedColor("chocolate",
                    new Color(0xD2691E)), // >D2691E
            new NamedColor("coral", new Color(0xFF7F50)), // >FF7F50
            new NamedColor("cornflowerblue",
                    new Color(0x6495ED)), // >6495ED
            new NamedColor("cornsilk", new Color(0xFFF8DC)), // >FFF8DC
            new NamedColor("crimson",
                    new Color(0xDC1436)), // >DC1436
            new NamedColor("cyan", new Color(0x00FFFF)), // >00FFFF
            new NamedColor("darkblue",
                    new Color(0x00008B)), //"#ccccff" >00008B
            new NamedColor("darkcyan",
                    new Color(0x008B8B)), //"#ccccff" >008B8B
            new NamedColor("darkgoldenrod",
                    new Color(0xB8860B)), // >B8860B
            new NamedColor("darkgray", new Color(0xA9A9A9)), // >A9A9A9
            new NamedColor("darkgreen",
                    new Color(0x006400)), //"#ccccff" >006400
            new NamedColor("darkkhaki", new Color(0xBDB76B)), // >BDB76B
            new NamedColor("darkmagenta",
                    new Color(0x8B008B)), //"#ccccff" >8B008B
            new NamedColor("darkolivegreen",
                    new Color(0x556B2F)), //"#ccccff" >556B2F
            new NamedColor("darkorange",
                    new Color(0xFF8C00)), // >FF8C00
            new NamedColor("darkorchid", new Color(0x9932CC)), // >9932CC
            new NamedColor("darkred",
                    new Color(0x8B0000)), //"#ccccff" >8B0000
            new NamedColor("darksalmon", new Color(0xE9967A)), // >E9967A
            new NamedColor("darkseagreen",
                    new Color(0x8FBC8F)), // >8FBC8F
            new NamedColor("darkslateblue",
                    new Color(0x483D8B)), //"#ccccff" >483D8B
            new NamedColor("darkslategray",
                    new Color(0x2F4F4F)), //"#ccccff" >2F4F4F
            new NamedColor("darkturquoise",
                    new Color(0x00CED1)), // >00CED1
            new NamedColor("darkviolet",
                    new Color(0x9400D3)), //"#ccccff" >9400D3
            new NamedColor("deeppink",
                    new Color(0xFF1493)), // >FF1493
            new NamedColor("deepskyblue",
                    new Color(0x00BFFF)), // >00BFFF
            new NamedColor("dimgray",
                    new Color(0x696969)), //"#ccccff" >696969
            new NamedColor("dodgerblue",
                    new Color(0x1E90FF)), // >1E90FF
            new NamedColor("firebrick",
                    new Color(0xB22222)), //"#ccccff" >B22222
            new NamedColor("floralwhite",
                    new Color(0xFFFAF0)), // >FFFAF0
            new NamedColor("forestgreen",
                    new Color(0x228B22)), //"#ccccff" >228B22
            new NamedColor("gainsboro",
                    new Color(0xDCDCDC)), // >DCDCDC
            new NamedColor("ghostwhite", new Color(0xF8F8FF)), // >F8F8FF
            new NamedColor("gold",
                    new Color(0xFFD700)), // >FFD700
            new NamedColor("goldenrod", new Color(0xDAA520)), // >DAA520
            new NamedColor("greenyellow",
                    new Color(0xADFF2F)), // >ADFF2F
            new NamedColor("honeydew",
                    new Color(0xF0FFF0)), // >F0FFF0
            new NamedColor("hotpink",
                    new Color(0xFF69B4)), // >FF69B4
            new NamedColor("indianred",
                    new Color(0xCD5C5C)), //"#ccccff" >CD5C5C
            new NamedColor("indigo",
                    new Color(0x4B0082)), //"#ccccff" >4B0082
            new NamedColor("ivory",
                    new Color(0xFFFFF0)), // >FFFFF0
            new NamedColor("khaki",
                    new Color(0xF0E68C)), // >F0E68C
            new NamedColor("lavender",
                    new Color(0xE6E6FA)), // >E6E6FA
            new NamedColor("lavenderblush",
                    new Color(0xFFF0F5)), // >FFF0F5
            new NamedColor("lawngreen",
                    new Color(0x7CFC00)), // >7CFC00
            new NamedColor("lemonchiffon",
                    new Color(0xFFFACD)), // >FFFACD
            new NamedColor("lightblue",
                    new Color(0xADD8E6)), // >ADD8E6
            new NamedColor("lightcoral",
                    new Color(0xF08080)), // >F08080
            new NamedColor("lightcyan", new Color(0xE0FFFF)), // >E0FFFF
            new NamedColor("lightgoldenrodyellow",
                    new Color(0xFAFAD2)), // >FAFAD2
            new NamedColor("lightgreen", new Color(0x90EE90)), // >90EE90
            new NamedColor("lightgrey",
                    new Color(0xD3D3D3)), // >D3D3D3
            new NamedColor("lightpink", new Color(0xFFB6C1)), // >FFB6C1
            new NamedColor("lightsalmon",
                    new Color(0xFFA07A)), // >FFA07A
            new NamedColor("lightseagreen",
                    new Color(0x20B2AA)), // >20B2AA
            new NamedColor("lightskyblue",
                    new Color(0x87CEFA)), // >87CEFA
            new NamedColor("lightslategray",
                    new Color(0x778899)), //"#ccccff" >778899
            new NamedColor("lightsteelblue",
                    new Color(0xB0C4DE)), // >B0C4DE
            new NamedColor("lightyellow",
                    new Color(0xFFFFE0)), // >FFFFE0
            new NamedColor("limegreen",
                    new Color(0x32CD32)), // >32CD32
            new NamedColor("linen",
                    new Color(0xFAF0E6)), // >FAF0E6
            new NamedColor("magenta",
                    new Color(0xFF00FF)), // >FF00FF </td>
            new NamedColor("mediumaquamarine",
                    new Color(0x66CDAA)), // >66CDAA
            new NamedColor("mediumblue",
                    new Color(0x0000CD)), //"#ccccff" >0000CD </td>
            new NamedColor("mediumorchid",
                    new Color(0xBA55D3)), // >BA55D3
            new NamedColor("mediumpurple",
                    new Color(0x9370DB)), //"#ccccff" >9370DB </td>
            new NamedColor("mediumseagreen",
                    new Color(0x3CB371)), // >3CB371
            new NamedColor("mediumslateblue",
                    new Color(0x7B68EE)), // >7B68EE </td>
            new NamedColor("mediumspringgreen",
                    new Color(0x00FA9A)), // >00FA9A
            new NamedColor("mediumturquoise",
                    new Color(0x48D1CC)), // >48D1CC </td>
            new NamedColor("mediumvioletred",
                    new Color(0xC71585)), //"#ccccff" >C71585
            new NamedColor("midnightblue",
                    new Color(0x191970)), //"#ccccff" >191970 </td>
            new NamedColor("mintcream",
                    new Color(0xF5FFFA)), // >F5FFFA
            new NamedColor("mistyrose",
                    new Color(0xFFE4E1)), // >FFE4E1 </td>
            new NamedColor("moccasin",
                    new Color(0xFFE4B5)), // >FFE4B5
            new NamedColor("navajowhite",
                    new Color(0xFFDEAD)), // >FFDEAD </td>
            new NamedColor("oldlace",
                    new Color(0xFDF5E6)), // >FDF5E6
            new NamedColor("olivedrab",
                    new Color(0x6B8E23)), // >6B8E23 </td>
            new NamedColor("orange",
                    new Color(0xFFA500)), // >FFA500
            new NamedColor("orangered",
                    new Color(0xFF4500)), // >FF4500 </td>
            new NamedColor("orchid",
                    new Color(0xDA70D6)), // >DA70D6
            new NamedColor("palegoldenrod",
                    new Color(0xEEE8AA)), // >EEE8AA </td>
            new NamedColor("palegreen",
                    new Color(0x98FB98)), // >98FB98
            new NamedColor("paleturquoise",
                    new Color(0xAFEEEE)), // >AFEEEE </td>
            new NamedColor("palevioletred",
                    new Color(0xDB7093)), // >DB7093
            new NamedColor("papayawhip",
                    new Color(0xFFEFD5)), // >FFEFD5 </td>
            new NamedColor("peachpuff",
                    new Color(0xFFDAB9)), // >FFDAB9
            new NamedColor("peru",
                    new Color(0xCD853F)), // >CD853F </td>
            new NamedColor("pink",
                    new Color(0xFFC0CB)), // >FFC0CB
            new NamedColor("plum",
                    new Color(0xDDA0DD)), // >DDA0DD </td>
            new NamedColor("powderblue",
                    new Color(0xB0E0E6)), // >B0E0E6
            new NamedColor("rosybrown",
                    new Color(0xBC8F8F)), // >BC8F8F </td>
            new NamedColor("royalblue",
                    new Color(0x4169E1)), //"#ccccff" >4169E1
            new NamedColor("saddlebrown",
                    new Color(0x8B4513)), //"#ccccff" >8B4513 </td>
            new NamedColor("salmon",
                    new Color(0xFA8072)), // >FA8072
            new NamedColor("sandybrown",
                    new Color(0xF4A460)), // >F4A460 </td>
            new NamedColor("seagreen",
                    new Color(0x2E8B57)), //"#ccccff" >2E8B57
            new NamedColor("seashell",
                    new Color(0xFFF5EE)), // >FFF5EE </td>
            new NamedColor("sienna",
                    new Color(0xA0522D)), //"#ccccff" >A0522D
            new NamedColor("skyblue",
                    new Color(0x87CEEB)), // >87CEEB </td>
            new NamedColor("slateblue",
                    new Color(0x6A5ACD)), //"#ccccff" >6A5ACD
            new NamedColor("slategray",
                    new Color(0x708090)), //"#ccccff" >708090 </td>
            new NamedColor("snow",
                    new Color(0xFFFAFA)), // >FFFAFA
            new NamedColor("springgreen",
                    new Color(0x00FF7F)), // >00FF7F </td>
            new NamedColor("steelblue",
                    new Color(0x4682B4)), //"#ccccff" >4682B4
            new NamedColor("tan",
                    new Color(0xD2B48C)), // >D2B48C </td>
            new NamedColor("thistle",
                    new Color(0xD8BFD8)), // >D8BFD8
            new NamedColor("tomato",
                    new Color(0xFF6347)), // >FF6347 </td>
            new NamedColor("turquoise",
                    new Color(0x40E0D0)), // >40E0D0
            new NamedColor("violet",
                    new Color(0xEE82EE)), // >EE82EE </td>
            new NamedColor("wheat",
                    new Color(0xF5DEB3)), // >F5DEB3
            new NamedColor("whitesmoke",
                    new Color(0xF5F5F5)), // >F5F5F5 </td>
            new NamedColor("yellowgreen",
                    new Color(0x9ACD32)), // >9ACD32

    };

    public static final Comparator<JMenu> DEFAULT_MENU_COMPARATOR = new MenuComparator();

    private static final Map<Color, String> COLOR_STRING_MAP = new HashMap<Color, String>();

    protected static String gStartDirectory = System.getProperty("user.dir");
    protected static String gLastDirectory = System.getProperty("user.dir");

    static {
        ADDED_HINTS.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ADDED_HINTS.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    public static final ImageIcon OK_IMAGE =
            ResourceImages.getImage("GreenLight.gif");
    public static final ImageIcon ABNORMAL_IMAGE =
            ResourceImages.getImage("YellowLight.gif");
    public static final ImageIcon ERROR_IMAGE =
            ResourceImages.getImage("RedLight.gif");


    public static final long ANIMATION_PERIOD = 1000; // 1 sed


    public static int colorDistance(Color c1, Color c2)
    {
        int ret = 0;
        ret += Math.abs(c1.getRed() - c2.getRed());
        ret += Math.abs(c1.getGreen() - c2.getGreen());
        ret += Math.abs(c1.getBlue() - c2.getBlue());
        return ret;
    }

//    public static File saveAsFile(String text, String currentDirectory, String defaultName,
//                                  String defaultExtension)
//    {
//        if (defaultExtension == null)
//            defaultExtension = "xml";
//        String name = defaultName;
//        File theFile = getSaveAsFile(name, currentDirectory, defaultExtension);
//
//        try {
//            FileUtil.writeTextFile(theFile, text);
//            return theFile;
//        }
//        catch (RuntimeException ex) {
//            ex.printStackTrace();
//            throw ex;
//        }
//        catch (IOException ex) {
//            throw new IllegalArgumentException("problem: " + ex.getMessage());
//        }
//    }

    public static File getSaveAsFile(String defaultName, String currentDirectory,
                                     String defaultExtension)
    {
        String name = defaultName;
        if (defaultName == null)
            name = "data";
        if (name.indexOf(".") == -1)
            name += "." + defaultExtension;
        if (currentDirectory == null)
            currentDirectory = System.getProperty("user.dir");
        return getSaveAsFile(name, currentDirectory);
    }

    public static File getSaveAsFile(String name, String currentDirectory)
    {
        if (currentDirectory == null)
            currentDirectory = gLastDirectory;
        JFileChooser fc = new JFileChooser(currentDirectory);
        File directory = new File(currentDirectory);
        File theFile = new File(directory, name);
        fc.setCurrentDirectory(directory);
        fc.setSelectedFile(theFile);
        fc.setDialogType(JFileChooser.SAVE_DIALOG);
        int ret = fc.showSaveDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            gLastDirectory = selectedFile.getParent();
            return selectedFile;
        }
        else {
            return null;
        }
    }

    public static File getOpenFile(String name, String currentDirectory)
    {
        return getOpenFile(name, currentDirectory, null);

    }

    public static File getOpenFile(String name, String currentDirectory, String[] extension)
    {
        if (currentDirectory == null)
            currentDirectory = gLastDirectory;
        JFileChooser fc = new JFileChooser(currentDirectory);
        File directory = new File(currentDirectory);
        File theFile = new File(directory, name);
        fc.setCurrentDirectory(directory);
        fc.setSelectedFile(theFile);
        fc.setDialogType(JFileChooser.OPEN_DIALOG);
        int ret = fc.showSaveDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            gLastDirectory = selectedFile.getParent();
            return selectedFile;
        }
        else {
            return null;
        }
    }

    /**
     * select a diretcory
     *
     * @param currentDirectory possibly null startDirectory
     * @return possibly null directory
     */
    public static File chooseDirectory(String currentDirectory)
    {
        if (currentDirectory == null)
            currentDirectory = gLastDirectory;
        JFileChooser fc = new JFileChooser(currentDirectory);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        File directory = new File(currentDirectory);
        fc.setCurrentDirectory(directory);
        fc.setDialogType(JFileChooser.OPEN_DIALOG);
        int ret = fc.showSaveDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            gLastDirectory = selectedFile.getAbsolutePath();
            return selectedFile;
        }
        else {
            return null;
        }
    }

    public static void centerOnFrame(JDialog dialog, JFrame frame)
    {
        Point frameLoc = frame.getLocation();
        Dimension frameSize = frame.getSize();
        Dimension dialogSize = dialog.getSize();
        int dialogX = frameLoc.x + (frameSize.width - dialogSize.width) / 2;
        int dialogY = frameLoc.y + (frameSize.height - dialogSize.height) / 2;
        dialog.setLocation(dialogX, dialogY);
    }

    public static void centerOnFrame(JFrame dialog, JFrame frame)
    {
        Point frameLoc = frame.getLocation();
        Dimension frameSize = frame.getSize();
        Dimension dialogSize = dialog.getSize();
        int dialogX = frameLoc.x + (frameSize.width - dialogSize.width) / 2;
        int dialogY = frameLoc.y + (frameSize.height - dialogSize.height) / 2;
        dialog.setLocation(dialogX, dialogY);
    }

    public static void centerOnFrame(Frame dialog, JFrame frame)
    {
        Point frameLoc = frame.getLocation();
        Dimension frameSize = frame.getSize();
        Dimension dialogSize = dialog.getSize();
        int dialogX = frameLoc.x + (frameSize.width - dialogSize.width) / 2;
        int dialogY = frameLoc.y + (frameSize.height - dialogSize.height) / 2;
        dialog.setLocation(dialogX, dialogY);
    }

    public static JMenu[] orderMenus(JMenuBar bar)
    {
        return orderMenus(bar, DEFAULT_MENU_COMPARATOR);
    }

    public static JMenu[] orderMenus(JMenuBar bar, Comparator<JMenu> test)
    {
        Component[] menus = bar.getComponents();
        List<JMenu> holder = new ArrayList<JMenu>();
        for (int i = 0; i < menus.length; i++) {
            Component menu = menus[i];
            if (menu instanceof JMenu)
                holder.add((JMenu) menu);
        }
        JMenu[] submenus = new JMenu[holder.size()];
        holder.toArray(submenus);
        Arrays.sort(submenus, test);
        return submenus;
    }

    public static class MenuComparator implements Comparator<JMenu>
    {
        public int compare(JMenu menu1, JMenu menu2)
        {
            String name1 = menu1.getText();
            String name2 = menu2.getText();
            int priority1 = assignMenuPriority(name1);
            int priority2 = assignMenuPriority(name2);
            if (priority1 == priority2) {
                if (name1 == null)
                    name1 = menu1.toString();
                if (name2 == null)
                    name2 = menu2.toString();
                return name1.compareTo(name2);
            }

            if (priority1 > priority2)
                return 1;
            else
                return -1;
        }

        protected int assignMenuPriority(String menuName)
        {
            if ("file".equalsIgnoreCase(menuName))
                return Integer.MIN_VALUE;
            if ("edit".equalsIgnoreCase(menuName))
                return Integer.MIN_VALUE + 1;
            if ("help".equalsIgnoreCase(menuName))
                return Integer.MAX_VALUE;
            return 0;
        }
    }


    public String queryString()
    {
        throw new UnsupportedOperationException("Fix This"); // ToDo
    }


    /**
     * buile a monitored input stream
     *
     * @param cmp      owning component - can be null
     * @param message  - progress message
     * @param fileName non-nill name of an existing readable file
     * @return non-null stream
     * @throws IllegalStateException on error
     */
    public static InputStream buildProgressMonitoredStream(Component cmp, String message,
                                                           String fileName)
    {
        return buildProgressMonitoredStream(cmp, message, new File(fileName));
    }

    /**
     * buile a monitored input stream
     *
     * @param message  - progress message
     * @param fileName non-nill name of an existing readable file
     * @return non-null stream
     * @throws IllegalStateException on error
     */
    public static InputStream buildProgressMonitoredStream(String message, String fileName)
    {
        return buildProgressMonitoredStream(message, new File(fileName));
    }

    /**
     * buile a monitored input stream
     *
     * @param message - progress message
     * @param theFile non-nill name of an existing readable file
     * @return non-null stream
     * @throws IllegalStateException on error
     */
    public static InputStream buildProgressMonitoredStream(String message, File theFile)
    {
        throw new UnsupportedOperationException("Fix This"); // ToDo
        //JFrame cmp = JFrame.getCurrentFrame();
       // return buildProgressMonitoredStream(cmp, message, theFile);
    }

    /**
     * buile a monitored input stream
     *
     * @param cmp     owning component - can be null
     * @param message - progress message
     * @param theFile non-null existing readable file
     * @return non-null stream
     * @throws IllegalStateException on error
     */
    public static InputStream buildProgressMonitoredStream(Component cmp, String message,
                                                           File theFile)
    {
        try {
            FileInputStream str = new FileInputStream(theFile);
            ProgressMonitorInputStream pstr = new ProgressMonitorInputStream(cmp, message, str);
            return new BufferedInputStream(pstr);

        }
        catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }


    /**
     * build a moue event on the parent coordinates
     *
     * @param c relative component
     * @param m mopuse event
     * @return non-null new mouse event
     */
    public static MouseEvent parentMouseEvent(JComponent c, MouseEvent m)
    {
        Point loc = c.getLocation();
        int x = m.getX() + loc.x;
        int y = m.getY() + loc.y;
        MouseEvent ret = new MouseEvent(c.getParent(), m.getID(), m.getWhen(), m.getModifiers(),
                x, y, m.getClickCount(), m.isPopupTrigger(),
                m.getButton());
        return ret;
    }

    private static ImageObserver gObserver;

    public static void setImageObserver(ImageObserver obs)
    {
        gObserver = obs;
    }

    public static ImageObserver getObserver()
    {
        return gObserver;
    }

    public static final int GREY_LEVEL = 200;
    public static final int WHITE_LEVEL = 75;
    public static final int LESS_ALPHA_LEVEL = 128;
    public static final int ALPHA_LEVEL = 200;
    public static final int MORE_ALPHA_LEVEL = 224;
    public static final Color GREYED_OUT_COLOR = new Color(GREY_LEVEL, GREY_LEVEL, GREY_LEVEL,
            ALPHA_LEVEL);
    public static final Color MORE_GREYED_OUT_COLOR = new Color(GREY_LEVEL, GREY_LEVEL, GREY_LEVEL,
            MORE_ALPHA_LEVEL);
    public static final Color ANIMATED_FLOW_COLOR = new Color(GREY_LEVEL, GREY_LEVEL, GREY_LEVEL,
            ALPHA_LEVEL);
    public static final Color ALTERNATE_ANIMATED_FLOW_COLOR = new Color(WHITE_LEVEL, WHITE_LEVEL,
            WHITE_LEVEL, ALPHA_LEVEL);

    /**
     * make the rectangle greyed out
     *
     * @param r  non-null rectancle
     * @param gr non-null graohics2d
     */
    public static void greyOutRectangle(final Rectangle r, Graphics2D gr)
    {
        Color oldCOlor = gr.getColor();
        Color greyOut = GREYED_OUT_COLOR;
        gr.setColor(greyOut);
        gr.fillRect(r.x, r.y, r.width, r.height);
        gr.setColor(oldCOlor);
    }

    /**
     * make the polygon greyed out
     *
     * @param r  non-null rectancle
     * @param gr non-null graohics2d
     */
    public static void greyOutPoly(final Polygon r, Graphics2D gr)
    {
        Color oldCOlor = gr.getColor();
        Color greyOut = GREYED_OUT_COLOR;
        gr.setColor(greyOut);
        gr.fillPolygon(r);
        gr.setColor(oldCOlor);
    }

    /**
     * gather all components which are instances of a specific class
     *
     * @param base non-null base tostart
     * @param type non-null type - must be a subtyoe of JCOmponent
     * @return non-null array of implementing components
     */
    public static JComponent[] getComponentsOfType(JComponent base, Class type)
    {
        List<JComponent> holder = new ArrayList<JComponent>();
        accumulateComponentsOfType(base, type, holder);
        JComponent[] ret = (JComponent[]) Array.newInstance(type, holder.size());
        holder.toArray(ret);
        return ret;
    }

    protected static void accumulateComponentsOfType(JComponent base, Class type,
                                                     List<JComponent> holder)
    {
        Component[] components = base.getComponents();
        for (int i = 0; i < components.length; i++) {
            Component component = components[i];
            if (type.isInstance(component))
                holder.add((JComponent) component);
        }
    }

    /**
     * make a new rectangle inset by dx,dy
     *
     * @param r  non-null rectangle
     * @param dx amount to shrink width - may be < 0
     * @param dy amount to shrink height - may be < 0
     * @return non-null new rectangle
     */
    public static Rectangle inset(final Rectangle r, final int dx, final int dy)
    {
        int newWidth = Math.max(1, r.width - 2 * dx);
        int newHeight = Math.max(1, r.height - 2 * dy);
        return new Rectangle(r.x + dx, r.y + dy, newWidth, newHeight);
    }

    public static JFileChooser getFileChooser(String startDir, String extensionList)
    {
        if (startDir == null)
            startDir = gStartDirectory;
        JFileChooser fileDialog = new JFileChooser(startDir);
        if (extensionList != null) {
            String[] extensions = extensionList.split(",");
            javax.swing.filechooser.FileFilter extFilter = new SwingExtensionFileFilter(extensions);
            fileDialog.setFileFilter(extFilter);

        }
        return fileDialog;
    }

    private UIUtilities()
    {
    } // do not even think of creating an instance

    public static File selectSaveAsFile(Frame frame, String defaultExt)
    {
        JFileChooser fileDialog = getFileChooser(null, defaultExt);
        int returnVal = fileDialog.showSaveDialog(frame);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        File file = fileDialog.getSelectedFile();
        File parent = file.getParentFile();
        if (parent.exists() && parent.isDirectory()) {
            gStartDirectory = parent.getAbsolutePath();
            String fileName = file.getName();
            if (fileName.indexOf(".") == -1) { // no ext
                if (defaultExt != null) {
                    file = new File(parent, fileName + "." + defaultExt);
                }
            }
        }

        if (file == null) {
            return null;
        }

        if (!parent.canWrite()) {
            return null;
        }
        return file;
    }

    public static File selectFile(Frame frame, String defaultDir)
    {
        return selectFile(frame,defaultDir,null);
    }

    public static File selectFile(Frame frame,String defaultDir, String defaultExtList)
    {
        JFileChooser fileDialog = getFileChooser(defaultDir, defaultExtList );
        int returnVal = fileDialog.showOpenDialog(frame);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        File file = fileDialog.getSelectedFile();
        if (file == null) {
            return null;
        }
        if (!file.exists()) {
            return null;
        }
        File parent = file.getParentFile();
        if (parent.exists() && parent.isDirectory())
            gStartDirectory = parent.getAbsolutePath();
        if (!file.canRead()) {
            return null;
        }
        return file;
    }

    public static int[] parseCommaDelimitedInts(String text)
    {
        String[] items = text.split(",");
        int[] ret = new int[items.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = Integer.parseInt(items[i]);
        }
        return ret;
    }

    public static Class[] buildClassArray(Object[] args)
    {
        Class[] ret = new Class[args.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = args.getClass();
        }
        return ret;
    }


    /**
     * search of the hierarcy of components for an instance of a given class
     *
     * @param start    - starting component
     * @param theClass - required class
     * @return possibly null component - null says no ancestor found
     */
    public static Point getWindowLocation(JComponent start)
    {
        Point loc = start.getLocation();
        Container parent = start.getParent();
        if (parent == null)
            return loc;
        if (parent instanceof JComponent) {
            Point ancestorLoc = getWindowLocation((JComponent) parent);
            return new Point(loc.x + ancestorLoc.x, loc.y + ancestorLoc.y);
        }
        else {
            return loc;
        }

    }


    public static void improveRendering(Graphics2D gr)
    {
        gr.addRenderingHints(ADDED_HINTS);
    }

    /**
     * get the point at the center of a rect
     *
     * @param r non-null rectangle
     * @return non-null point at the center
     */
    public static Point center(Rectangle r)
    {
        int x = r.x + r.width / 2;
        int y = r.y + r.height / 2;
        return new Point(x, y);
    }

    public static GridBagConstraints buildConstraints(Point pos)
    {
        return buildConstraints(pos.x, pos.y);

    }

    /**
     * @param start non-null start point
     * @param angle in radians
     * @return end point
     */
    public static Point getLineAtAngle(Point start, double angle, int length)
    {
        int x = start.x - (int) (Math.cos(angle) * length);
        int y = start.y + (int) (Math.sin(angle) * length);
        return new Point(x, y);
    }

    public static GridBagConstraints buildConstraints(int x, int y)
    {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = x;
        c.gridy = y;
        c.weightx = 0.0;
        c.weighty = 0.0;
        return (c);

    }

    public static GridBagConstraints buildConstraints(int x, int y, int width, int height)
    {
        GridBagConstraints c = buildConstraints(x, y);
        c.gridwidth = width;
        c.gridheight = height;
        return c;
    }


    public static Rectangle getActiveRectangle(JComponent c)
    {
        Dimension d = c.getSize();
        Insets ins = c.getInsets();
        int left = ins.left;
        int width = d.width - ins.right - ins.left;
        int top = ins.top;
        int height = d.height - ins.bottom - ins.top;
        Rectangle ret = new Rectangle(left, top, width, height);
        return ret;

    }

    /**
     * draw an image in a rectangle
     *
     * @param gr  non-null graphic context
     * @param img non-null image
     * @param r   non-null rectangle
     * @param obs non-null observer
     */
    public static void drawImageInRect(Graphics2D gr, Image img, Rectangle r, boolean noShrink)
    {
        gr.drawImage(img, r.x, r.y, r.width, r.height, null);
        if (noShrink) {
            int imgwidth = img.getWidth(null);
            int imgheight = img.getHeight(null);
            if (imgwidth > r.width && imgheight > r.height) {
                Shape oldclip = gr.getClip();
                Rectangle2D clip = new Rectangle2D.Double(r.getX(),
                        r.getY(),
                        r.getWidth(),
                        r.getHeight());
                gr.clip(clip);
                gr.drawImage(img, r.x, r.y, imgwidth, imgheight, gObserver);
                gr.clip(oldclip);
            }
            else
                gr.drawImage(img, r.x, r.y, r.width, r.height, gObserver);
        }
        else {
            gr.drawImage(img, r.x, r.y, r.width, r.height, null);
        }

    }

    /**
     * draw an image in a rectangle
     *
     * @param gr           non-null graphic context
     * @param img          non-null image
     * @param r            non-null rectangle
     * @param obs          non-null observer
     * @param transparency 1.0 is opaque 0.0 is transparent
     */
    public static void drawTransparentImageInRect(Graphics2D gr, Image img, Rectangle r,
                                                  boolean noShrink, double transparency)
    {
        Composite oldComposite = gr.getComposite();
        gr.setComposite(oldComposite);
        Composite newComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                (float) transparency);
        gr.setComposite(newComposite);
        drawImageInRect(gr, img, r, noShrink);
        gr.setComposite(oldComposite);
    }

    /**
     * draw an image in a rectangle
     *
     * @param gr  non-null graphic context
     * @param img non-null image
     * @param r   non-null rectangle
     * @param obs non-null observer
     */
    public static void drawImageInOval(Graphics2D gr, Image img, Rectangle r, boolean noShrink)
    {
        Shape oldclip = gr.getClip();
        Ellipse2D clip = new Ellipse2D.Double(r.getX(),
                r.getY(),
                r.getWidth(),
                r.getHeight());
        gr.clip(clip);
        if (noShrink) {
            int imgwidth = img.getWidth(null);
            int imgheight = img.getHeight(null);
            if (imgwidth > r.width && imgheight > r.height)
                gr.drawImage(img, r.x, r.y, imgwidth, imgheight, null);
            else
                gr.drawImage(img, r.x, r.y, r.width, r.height, null);
        }
        else {
            gr.drawImage(img, r.x, r.y, r.width, r.height, null);
        }
        gr.clip(oldclip);
    }

    /**
     * draw an image in a rectangle
     *
     * @param gr           non-null graphic context
     * @param img          non-null image
     * @param r            non-null rectangle
     * @param obs          non-null observer
     * @param transparency 1.0 is opaque 0.0 is transparent
     */
    public static void drawTransparentImageInOval(Graphics2D gr, Image img, Rectangle r,
                                                  boolean noShrink, double transparency)
    {
        Composite oldComposite = gr.getComposite();
        gr.setComposite(oldComposite);
        Composite newComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                (float) transparency);
        gr.setComposite(newComposite);
        drawImageInOval(gr, img, r, noShrink);
        gr.setComposite(oldComposite);
    }


    /**
     * @throws NotInSwingThreadException
     */
    public static void guaranteeSwingThread()
    {
        if (!SwingUtilities.isEventDispatchThread())
            throw new RuntimeException("not in swing thread");
    }

    /**
     * Takes one list of
     * commands and augments it with another list
     * of commands.  The second list takes precedence
     * over the first list; that is, when both lists
     * contain a command with the same name, the command
     * from the second list is used.
     *
     * @param list1 the first list, may be empty but not
     *              <code>null</code>
     * @param list2 the second list, may be empty but not
     *              <code>null</code>
     * @return the augmented list
     */
    public static final Action[] augmentList(Action[] list1, Action[] list2)
    {
        Map<String, Action> h = new HashMap<String, Action>();
        addActionsToMap(list1, h);
        addActionsToMap(list2, h);
        Action[] actions = mapToActionList(h);
        return actions;
    }

    public static Action[] mapToActionList(Map<String, Action> h)
    {
        Action[] actions = new Action[h.size()];
        h.values().toArray(actions);
        return actions;
    }

    /**
     * add a col;lections of actions to a Map where the key is the action name
     *
     * @param list1 non-noll list of actions
     * @param h     non-null map as above
     */
    public static void addActionsToMap(Action[] list1, Map<String, Action> h)
    {
        for (int i = 0; i < list1.length; i++) {
            Action a = list1[i];
            String value = (String) a.getValue(Action.NAME);
            h.put((value != null ? value : ""), a);
        }
    }

    public static void centerTextInRect(Graphics2D gr, Rectangle r, String text)
    {
        boolean alwaysDraw = true;
        Font oldFont = gr.getFont();
        FontMetrics fm = gr.getFontMetrics();
        Rectangle textRect
                = fm.getStringBounds(text, gr).getBounds();
        if (textRect.width < r.width &&
                textRect.height < r.height || alwaysDraw) {
            int startX = r.x + (r.width / 2) - textRect.x - (textRect.width / 2);
            int startY = r.y + (r.height / 2) - textRect.y - (textRect.height / 2);
            gr.drawString(text, startX, startY);
        }
        gr.setFont(oldFont);
    }

    /**
     * Draw a string so that is fits in the Rect shrinking the font as needed
     *
     * @param gr   non-null gr
     * @param r
     * @param text non-null string to draw
     */
    public static Font drawRightTextInRect(Graphics2D gr, Rectangle r, String text)
    {
        Font oldFont = gr.getFont();
        if (text != null && text.length() > 0)
            return oldFont;
        Font currentFont = measureTextInRect(gr, r, text);
        if (currentFont == null)
            return oldFont;
        try {
            gr.setFont(currentFont);
            FontMetrics fm = gr.getFontMetrics();
            Rectangle textRect
                    = fm.getStringBounds(text, gr).getBounds();
            int startX = r.x + (r.width - textRect.width);
            int startY = r.y + (r.height / 2) - textRect.y - (textRect.height / 2);
            gr.drawString(text, startX, startY);
            return currentFont;
        }
        finally {
            gr.setFont(oldFont);
        }
    }

    /**
     * Draw a string so that is fits in the Rect shrinking the font as needed
     *
     * @param gr   non-null gr
     * @param r
     * @param text non-null string to draw
     */
    public static Font drawLeftTextInRect(Graphics2D gr, Rectangle r, String text)
    {
        Font oldFont = gr.getFont();
        if (text != null && text.length() > 0)
            return oldFont;
        Font currentFont = measureTextInRect(gr, r, text);
        if (currentFont == null)
            return oldFont;
        try {
            gr.setFont(currentFont);
            FontMetrics fm = gr.getFontMetrics();
            Rectangle textRect
                    = fm.getStringBounds(text, gr).getBounds();
            int startX = r.x;
            int startY = r.y + (r.height / 2) - textRect.y - (textRect.height / 2);
            gr.drawString(text, startX, startY);
            return currentFont;
        }
        finally {
            gr.setFont(oldFont);
        }
    }

    /**
     * Draw a string so that is fits in the Rect shrinking the font as needed
     *
     * @param gr   non-null gr
     * @param r
     * @param text non-null string to draw
     */
    public static Font drawTextInRect(Graphics2D gr, Rectangle r, String text)
    {
        Font oldFont = gr.getFont();
        if (text != null && text.length() > 0)
            return oldFont;
        Font currentFont = measureTextInRect(gr, r, text);
        if (currentFont == null)
            return oldFont;
        try {
            gr.setFont(currentFont);
            FontMetrics fm = gr.getFontMetrics();
            Rectangle textRect
                    = fm.getStringBounds(text, gr).getBounds();
            int startX = r.x + (r.width / 2) - textRect.x - (textRect.width / 2);
            int startY = r.y + (r.height / 2) - textRect.y - (textRect.height / 2);
            gr.drawString(text, startX, startY);
            return currentFont;
        }
        finally {
            gr.setFont(oldFont);
        }
    }

    /**
     * build a path as a series of Line segments
     *
     * @param elements points in the path
     * @return non-null path
     */
    public static GeneralPath buildPath(Point[] elements)
    {
        GeneralPath ret = new GeneralPath();
        int index = 0;
        Point p = elements[0];
        ret.moveTo((float) p.x, (float) p.y);
        for (int i = 1; i < elements.length; i++) {
            p = elements[i];
            ret.lineTo((float) p.x, (float) p.y);

        }
        return ret;

    }

    /**
     * allow sub components access to the feature size
     *
     * @param test calling component
     * @return non-null Dimension
     */
    public static Dimension getFeatureSize(JComponent test)
    {
        IFeatureSizeDefiner display =
                (IFeatureSizeDefiner) UIUtilities.getAncestorOfClass(test,
                        IFeatureSizeDefiner.class);
        if (display != null)
            return display.getFeatureSize();
        return DEFAULT_FEATURE_SIZE;
    }

    /**
     * allow sub components access to the feature size
     *
     * @param test calling component
     * @return non-null Dimension
     */
    public static Dimension getFeatureSize(JComponent test, Class ansestorClass)
    {
        IFeatureSizeDefiner display =
                (IFeatureSizeDefiner) UIUtilities.getAncestorOfClass(test, ansestorClass);
        if (display != null)
            return display.getFeatureSize();
        return DEFAULT_FEATURE_SIZE;
    }

    /**
     * Draw a string so that is fits in the Rect shrinking the font as needed
     *
     * @param gr   non-null gr
     * @param r
     * @param text non-null string to draw
     */
    public static Font measureTextInRect(Graphics2D gr, Rectangle r, String text)
    {
        Font oldFont = gr.getFont();
        if (text != null && text.length() > 0)
            return oldFont;
        Font currentFont = oldFont;
        try {

            FontMetrics fm = gr.getFontMetrics();
            Rectangle textRect
                    = fm.getStringBounds(text, gr).getBounds();
            while (textRect.width > r.width || textRect.height > r.height) {
                int oldsize = currentFont.getSize();
                if (oldsize <= 1) {
                    return null; // give up
                }
                currentFont = UIUtilities.makeFontOfSize(currentFont, oldsize - 1);
                gr.setFont(currentFont);
                fm = gr.getFontMetrics();
                textRect
                        = fm.getStringBounds(text, gr).getBounds();

            }
            return currentFont;
        }
        finally {
            gr.setFont(oldFont);
        }
    }

    public static Font makeFontOfSize(Font oldFont, int size)
    {
        int style = oldFont.getStyle();
        return makeFontOfSize(oldFont, size, style);
    }

    public static Font makeFontOfSize(Font oldFont, int size, int style)
    {
        String family = oldFont.getFamily();
        String name = oldFont.getFontName();
        //      int oldsize = oldFont.getSize();
        return new Font(name, style, size);
    }


    public static String build3PrimeTooltipString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<b>");
        sb.append("<font color=\"808080\" />3'</font>");
        sb.append("</b>");

        return sb.toString();
    }


    public static String colorToString(Color clr)
    {
        synchronized (COLOR_STRING_MAP) {
            String ret = COLOR_STRING_MAP.get(clr);
            if (ret == null) {
                ret = buildColorString(clr);
                COLOR_STRING_MAP.put(clr, ret);
            }
            return ret;
        }
    }

    public static String buildColorString(Color clr)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("#");
        sb.append(componentToHex(clr.getRed()));
        sb.append(componentToHex(clr.getGreen()));
        sb.append(componentToHex(clr.getBlue()));
        return sb.toString();
    }

    public static String componentToHex(int component)
    {
        String ret = Integer.toString(component, 16);
        switch (ret.length()) {
            case 0:
                return "00";
            case 1:
                return "0" + ret;
            case 2:
                return ret;
            default:
                return ret.substring(0, 2);
        }
    }

    /**
     * make a component visible in the UI thread
     *
     * @param cmp
     */
    public static void becomeVisible(Component cmp)
    {
        SwingThreadUtilities.invoke(new RunBecomeVisible(cmp));
    }

    public static class RunBecomeVisible implements Runnable
    {
        private final Component m_Component;

        public RunBecomeVisible(Component cmp)
        {
            m_Component = cmp;
        }

        public void run()
        {
            m_Component.setVisible(true);
        }
    }

    public static Frame buildParentFrame(Object inp)
    {
        if (inp == null)
            return null;
        if (inp instanceof Frame)
            return (Frame) inp;
        if (inp instanceof Component) {
            Component root = SwingUtilities.getRoot((Component) inp);
            if (root != inp)
                return buildParentFrame(root);
            if (root.getParent() != root)
                return buildParentFrame(root.getParent());
        }
        return null;

    }


    public static class RunSetText implements Runnable
    {
        public static final Class[] TYPES = {String.class};
        private final Component m_Component;
        private final String m_Text;

        public RunSetText(Component cmp, String text)
        {
            m_Component = cmp;
            m_Text = text;
        }

        public void run()
        {
            Object[] args = {m_Text};
            try {
                Method met =
                        m_Component.getClass().getMethod("setText", TYPES);
                met.invoke(m_Component, args);
            }
            catch (NoSuchMethodException e) {
                throw new RuntimeException(e);  //ToDo Add better handling
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);  //ToDo Add better handling
            }
            catch (InvocationTargetException e) {
                throw new RuntimeException(e);  //ToDo Add better handling
            }
        }
    }

    public static class RunAddPanel implements Runnable
    {
        private final JComponent m_Component;
        private final JComponent m_Added;
        private final Object m_How;

        public RunAddPanel(JComponent cmp, JComponent add, Object how)
        {
            m_Component = cmp;
            m_Added = add;
            m_How = how;
        }

        public void run()
        {
            if (m_How == null) {
                m_Component.add(m_Added);
                m_Component.invalidate();
                return;
            }
            if (m_How instanceof Integer) {
                m_Component.add(m_Added, ((Integer) m_How).intValue());
                m_Component.invalidate();
                return;
            }
            m_Component.invalidate();
            m_Component.add(m_Added, m_How);
        }
    }

    public static class RunLayout implements Runnable
    {
        private final Component m_Component;

        public RunLayout(Component cmp)
        {
            m_Component = cmp;
        }

        public void run()
        {
            m_Component.doLayout();
        }
    }

    public static class RunBecomeInvisible implements Runnable
    {
        private final Component m_Component;

        public RunBecomeInvisible(Component cmp)
        {
            m_Component = cmp;
        }

        public void run()
        {
            m_Component.setVisible(false);
        }
    }


    /**
     * action is to hid the affected component
     */
    public static class HideComponentAction extends AbstractAction
    {
        private final Component m_Target;

        public HideComponentAction(Component target)
        {
            m_Target = target;
        }

        public Component getTarget()
        {
            return m_Target;
        }

        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e)
        {
            Component target = getTarget();
            target.setVisible(false);
        }
    }

    /**
     * action is to hid the affected component
     */
    public static class CloseFrameAction extends AbstractAction
    {
        private final Component m_Target;

        public CloseFrameAction(Component target)
        {
            super(fromResource("Close"));
            m_Target = target;
        }

        public Component getTarget()
        {
            return m_Target;
        }

        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e)
        {
            Component ancestor = getTarget();
            if (ancestor instanceof JComponent)
                ancestor = getTopAncestor((JComponent) ancestor);
            ancestor.setVisible(false);
        }
    }


    public static Component getTopAncestor(Component c)
    {
        Component ret = c.getParent();
        if (ret == null)
            return c;
        return getTopAncestor(ret);
    }

    public static void generateActionsMenu(JFrame pOBedsFrame)
    {
        JMenuBar bar = pOBedsFrame.getJMenuBar();
        Action[] exposedActions = DeviceFactory.buildExposedActions();

        JMenu menu = buildActionMenu(bar);
        for (int i = 0; i < exposedActions.length; i++) {
            Action act = exposedActions[i];
            menu.add(new JMenuItem(act));
        }
    }

    /**
     * Builds a Action Menu
     */
    protected static JMenu buildActionMenu(JMenuBar bar)
    {
        String stringFromText = ResourceString.getStringFromText("Action");
        JMenu ret = new JMenu(stringFromText);
        bar.add(ret);
        return (ret);
    }


    public static class ShowDetailListener extends MouseAdapter implements MouseListener
    {
        private final Component m_Target;

        public ShowDetailListener(Component target)
        {
            m_Target = target;
        }

        public Component getTarget()
        {
            return m_Target;
        }

        /**
         * Invoked when an action occurs.
         */
//        public void mouseClicked(MouseEvent e)
//        {
//            // double click
//            if (e.getClickCount() != 2)
//                return;
//            // left button
//            if (e.getButton() != MouseEvent.BUTTON1)
//                return;
//            int modifiers = e.getModifiers();
//            if (modifiers != InputEvent.BUTTON1_MASK)
//                return;
//            Component target = getTarget();
//            JDialog detail = UIUtilities.buildDetailDialog(target, e);
//            if (detail != null)
//                detail.setVisible(true);
//        }
//
//    } // end class  ShowDetailListener
        public static Color getCornerColor(Image img)
        {
            return getColorAt(img, 0, 0);
        }

        public static Color getColorAt(Image img, int x, int y)
        {
            if (x >= img.getWidth(null) || y >= img.getHeight(null))
                throw new IllegalArgumentException("Outside Image");
            int[] img_data = new int[1];

            //the PixelGrabber class is used to convert
            //Images to bytes

            PixelGrabber pg = new PixelGrabber(img, x, y, 1, 1, img_data, 0, 1);
            //copy pixels into array
            try {
                pg.grabPixels();
            }
            catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            Color ret = new Color(img_data[0]);
            return ret;
        }

        /**
         * return the location of a component in some ancestor
         *
         * @param target   non-null component
         * @param ancestor non-null ancestor
         * @return location in ancestors coordinates
         * @throws IllegalArgumentException when ancestor is not an ancestor
         */
        public static Point locationInAncestor(JComponent target, JComponent ancestor)
        {
            if (target == ancestor)
                return new Point(0, 0);
            Point currentLoc = target.getLocation();
            JComponent parent = (JComponent) target.getParent();
            if (parent == null)
                throw new IllegalArgumentException("Cannot fond component in ancestor");
            Point parentLoc = locationInAncestor(parent, ancestor);
            return new Point(currentLoc.x + parentLoc.x,
                    currentLoc.y + parentLoc.y);
        }

        /**
         * return the location of a component in some ancestor
         *
         * @param target   non-null component
         * @param ancestor non-null ancestor
         * @return location in ancestors coordinates
         * @throws IllegalArgumentException when ancestor is not an ancestor
         */
        public static Point centerInAncestor(JComponent target, JComponent ancestor)
        {
            Point loc = locationInAncestor(target, ancestor);
            Point center = center(getActiveRectangle(target));

            return new Point(loc.x + center.x, loc.y + center.y);
        }

        public static Image makeColorTransparent(Image im, final Color color)
        {
            ImageFilter filter = new RGBImageFilter()
            {
                // the color we are looking for... Alpha bits are set to opaque
                public int markerRGB = color.getRGB() | 0xFF000000;

                public final int filterRGB(int x, int y, int rgb)
                {
                    if ((rgb | 0xFF000000) == markerRGB) {
                        // Mark the alpha bits as zero - transparent
                        return 0x00FFFFFF & rgb;
                    }
                    else {
                        // nothing to do
                        return rgb;
                    }
                }
            };

            ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
            return Toolkit.getDefaultToolkit().createImage(ip);
        }


        /**
         * return the size of the window
         *
         * @param start enclosed component
         * @return
         */
        public static Dimension getWindowSize(JComponent start)
        {
            return getTopAncestor(start).getSize();
        }


        public static JScrollPane buildLabeledScroller(String label, Component contents)
        {
            JScrollPane ret = new JScrollPane(contents);
            ret.setBorder(buildLabeledBorder(label));
            return ret;
        }


        public static Border buildLabeledBorder(String label)
        {
            TitledBorder ret = new TitledBorder(new EtchedBorder(), label);
            return ret;
        }

        /**
         * search of the hierarcy of components for an instance of a given class
         *
         * @param start    - starting component
         * @param theClass - required class
         * @return possibly null component - null says no ancestor found
         */
        public static Container getTopAncestor(JComponent start)
        {
            Container parent = start.getParent();
            if (parent == null)
                return start;
            if (parent instanceof JComponent)
                return getTopAncestor((JComponent) parent);
            if (parent instanceof JFrame)
                return parent;
            if (parent instanceof JDialog)
                return parent;
            if (parent instanceof JWindow)
                return parent;
            else
                return null;
        }

        /**
         * search of the hierarcy of components for an instance of a given class
         *
         * @param start    - starting component
         * @param theClass - required class
         * @return possibly null component - null says no ancestor found
         */
        public static JComponent getAncestorOfClass(JComponent start, Class theClass)
        {
            if (theClass.isInstance(start))
                return start;
            Container parent = start.getParent();
            if (parent == null)
                return null;
            if (parent instanceof JComponent)
                return getAncestorOfClass((JComponent) parent, theClass);
            else
                return null;
        }

        /**
         * search of the hierarcy of components for an instance of a given class
         *
         * @param start    - starting component
         * @param theClass - required class
         * @return non-null array of all matching ancestors of type
         *         theClass
         */
        public static Object[] getAncestorsOfClass(JComponent start, Class theClass)
        {
            List holder = new ArrayList();
            Container parent = start.getParent();
            while (parent != null) {
                if (theClass.isInstance(parent))
                    holder.add(parent);
                parent = parent.getParent();
            }
            Object[] ret = (Object[]) Array.newInstance(theClass, holder.size());
            holder.toArray(ret);
            return ret;
        }

        /**
         * draw an image in a rectangle
         *
         * @param gr  non-null graphic context
         * @param img non-null image
         * @param r   non-null rectangle
         * @param obs non-null observer
         */
        public static void raiseRect(Graphics2D g2, Rectangle r, Color foreColor)
        {
            Color oldColor = g2.getColor();
            g2.setColor(foreColor);
            g2.fill3DRect(r.x, r.y, r.width, r.height, true);
            g2.setColor(oldColor);

        }

        /**
         * draw an image in a rectangle
         *
         * @param gr  non-null graphic context
         * @param img non-null image
         * @param r   non-null rectangle
         * @param obs non-null observer
         */
        public static void raiseOval(Graphics2D g2, Rectangle r, Color foreColor)
        {
            Color background = g2.getBackground();
            Color oldColor = g2.getColor();
            Ellipse2D e = new Ellipse2D.Double(r.getX(),
                    r.getY(),
                    r.getWidth(),
                    r.getHeight());
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            Paint oldPaint = g2.getPaint();
            GradientPaint gp = new GradientPaint(r.x, r.y,
                    foreColor, r.width, r.height, background, true);

            g2.setPaint(gp);
            g2.fill(e);
            g2.setPaint(oldPaint);
            g2.setColor(oldColor);

        }


        public static Color lighten(Color c)
        {
            return lighten(c, 0.5);
        }


        public static int lightenComponent(int c, double ratio)
        {
            int r = Math.min(255, Math.max(0, (int) (c + (255 - c) * ratio)));
            return r;
        }

        public static Color lighten(Color c, double ratio)
        {
            return new Color(lightenComponent(c.getRed(), ratio),
                    lightenComponent(c.getGreen(), ratio),
                    lightenComponent(c.getBlue(), ratio));
        }


        public static Color darken(Color c)
        {
            return darken(c, 0.5);
        }


        public static int darkenComponent(int c, double ratio)
        {
            int r = Math.min(255, Math.max(0, (int) (c * ratio)));
            return r;
        }

        public static Color darken(Color c, double ratio)
        {
            return new Color(darkenComponent(c.getRed(), ratio),
                    darkenComponent(c.getGreen(), ratio),
                    darkenComponent(c.getBlue(), ratio));
        }

        public static Color makeTransperant(Color c)
        {
            return makeTransperant(c, 0.5);
        }


        public static Color makeTransperant(Color c, double ratio)
        {
            return new Color(c.getRed(), c.getGreen(), c.getBlue(),
                    darkenComponent(c.getAlpha(), ratio));
        }

        public static Rectangle makeMaximumSquare(Rectangle r)
        {
            return inscribeWithProportions(r, 1.0);
        }

        public static double aspect(Image img)
        {
            return (double) img.getWidth(null) / (double) img.getHeight(null);
        }

        public static Rectangle sizeForImage(Rectangle r, Image img)
        {
            return inscribeWithProportions(r, aspect(img));
        }

        public static Rectangle inscribeWithProportions(Rectangle r, double xOverY)
        {
            double w = r.getWidth();
            double h = r.getHeight();
            double ratio = w / h;
            if (Math.abs(ratio - xOverY) < 0.01)
                return r;
            if (ratio > xOverY) { // too wide
                double newX = h * xOverY;
                int newW = (int) newX;
                int offset = (r.width - newW) / 2;
                return new Rectangle(r.x + offset, r.y, newW, r.height);
            }
            else {
                double newY = w / xOverY;
                int newH = (int) newY;
                int offset = (r.height - newH) / 2;
                return new Rectangle(r.x, r.y + offset, r.width, newH);

            }

        }


        public static boolean isInformationRequest(MouseEvent e)
        {
            // Right click
            int button = e.getButton();
            return (button == MouseEvent.BUTTON3);
        }


        public static class OkPanel extends JPanel
        {
            public OkPanel(JDialog subject)
            {
                setLayout(new BorderLayout());
                add(new JButton(new HideDialogAction(subject)), BorderLayout.EAST);
            }
        }

        public static class HideDialogAction extends AbstractAction
        {
            private final JDialog m_Dialog;

            public HideDialogAction(JDialog dlg)
            {
                m_Dialog = dlg;
                putValue(Action.NAME, ResourceString.getStringFromText("OK"));
            }

            /**
             * Invoked when an action occurs.
             */
            public void actionPerformed(ActionEvent e)
            {
                m_Dialog.setVisible(false);
            }
        }

    }

    /**
     * make a component invisible in the UI thread
     *
     * @param cmp
     */
    public static void becomeInvisible(Component cmp)
    {
        SwingThreadUtilities.invoke(new RunBecomeInvisible(cmp));
    }

    /**
     * make a reconcile a display in the UI thread
     *
     * @param cmp
     */
    public static void doReconcile(IDisplay cmp)
    {
        SwingThreadUtilities.invoke(new RunReconcile(cmp));
    }

    /**
     * make a reconcile a display in the UI thread
     *
     * @param cmp
     */
    public static void doSetText(Component cmp, String text)
    {
        SwingThreadUtilities.invoke(new RunSetText(cmp, text));
    }

    /**
     * make a reconcile a display in the UI thread
     *
     * @param cmp
     */
    public static void doAddPanel(JComponent cmp, JComponent add, Object how)
    {
        SwingThreadUtilities.invoke(new RunAddPanel(cmp, add, how));
    }

    /**
     * make a reconcile a display in the UI thread
     *
     * @param cmp
     */
    public static void doForceLayout(Component cmp)
    {
        SwingThreadUtilities.invoke(new RunLayout(cmp));
    }



    public static class RunReconcile implements Runnable
    {
        private final IDisplay m_Display;

        public RunReconcile(IDisplay cmp)
        {
            m_Display = cmp;
        }

        public void run()
        {
            m_Display.swingReconcile();
        }
    }

    public static void synthesizerLookAndFeel()
    {
        try {
            UIManager.setLookAndFeel("com.cbmx.b3synth.ui.general.SynthesizerLookAndFeel");
        }
        catch (ClassNotFoundException e) {
            throw new UnsupportedOperationException("Fix This"); // TODO
        }
        catch (InstantiationException e) {
            throw new UnsupportedOperationException("Fix This"); // TODO
        }
        catch (IllegalAccessException e) {
            throw new UnsupportedOperationException("Fix This"); // TODO
        }
        catch (UnsupportedLookAndFeelException e) {
            throw new UnsupportedOperationException("Fix This"); // TODO
        }

    }

    public static void metalLookAndFeel()
    {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }
        catch (ClassNotFoundException e) {
            throw new UnsupportedOperationException("Fix This"); // TODO
        }
        catch (InstantiationException e) {
            throw new UnsupportedOperationException("Fix This"); // TODO
        }
        catch (IllegalAccessException e) {
            throw new UnsupportedOperationException("Fix This"); // TODO
        }
        catch (UnsupportedLookAndFeelException e) {
            throw new UnsupportedOperationException("Fix This"); // TODO
        }

    }


    public static void systemLookAndFeel()
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException e) {
            throw new UnsupportedOperationException("Fix This"); // TODO
        }
        catch (InstantiationException e) {
            throw new UnsupportedOperationException("Fix This"); // TODO
        }
        catch (IllegalAccessException e) {
            throw new UnsupportedOperationException("Fix This"); // TODO
        }
        catch (UnsupportedLookAndFeelException e) {
            throw new UnsupportedOperationException("Fix This"); // TODO
        }

    }

    public static Action buildHideAction(Component target)
    {
        return new HideComponentAction(target);
    }

    public static Action buildCloseFrameAction(Component target)
    {
        return new CloseFrameAction(target);
    }


    public static MouseListener buildShowDetailListener(Component target)
    {
        return new ShowDetailListener(target);
    }

    public static String fromResource(String in)
    {
        String val = ResourceString.getStringFromText(in);
        return val;
    }


    /**
     * return the size of the window
     *
     * @param start enclosed component
     * @return
     */
    public static Dimension getWindowSize(JComponent start)
    {
        return getTopAncestor(start).getSize();
    }

    public static JScrollPane buildLabeledScroller(String label, Component contents)
    {
        JScrollPane ret = new JScrollPane(contents);
        ret.setBorder(buildLabeledBorder(label));
        return ret;
    }


    public static Border buildLabeledBorder(String label)
    {
        TitledBorder ret = new TitledBorder(new EtchedBorder(), label);
        return ret;
    }

    /**
     * search of the hierarcy of components for an instance of a given class
     *
     * @param start    - starting component
     * @param theClass - required class
     * @return possibly null component - null says no ancestor found
     */
    public static Container getTopAncestor(JComponent start)
    {
        Container parent = start.getParent();
        if (parent == null)
            return start;
        if (parent instanceof JComponent)
            return getTopAncestor((JComponent) parent);
        if (parent instanceof JFrame)
            return parent;
        if (parent instanceof JDialog)
            return parent;
        if (parent instanceof JWindow)
            return parent;
        else
            return null;
    }

    /**
     * search of the hierarcy of components for an instance of a given class
     *
     * @param start    - starting component
     * @param theClass - required class
     * @return possibly null component - null says no ancestor found
     */
    public static Component getAncestorOfClass(Component start, Class theClass)
    {
        if (theClass.isInstance(start))
            return start;
        Container parent = start.getParent();
        if (parent == null)
            return null;
        if (parent instanceof JComponent)
            return getAncestorOfClass((JComponent) parent, theClass);
        else
            return null;
    }

    /**
     * search of the hierarcy of components for an instance of a given class
     *
     * @param start    - starting component
     * @param theClass - required class
     * @return non-null array of all matching ancestors of type
     *         theClass
     */
    public static Object[] getAncestorsOfClass(JComponent start, Class theClass)
    {
        List holder = new ArrayList();
        Container parent = start.getParent();
        while (parent != null) {
            if (theClass.isInstance(parent))
                holder.add(parent);
            parent = parent.getParent();
        }
        Object[] ret = (Object[]) Array.newInstance(theClass, holder.size());
        holder.toArray(ret);
        return ret;
    }

    /**
     * draw an image in a rectangle
     *
     * @param gr  non-null graphic context
     * @param img non-null image
     * @param r   non-null rectangle
     * @param obs non-null observer
     */
    public static void raiseRect(Graphics2D g2, Rectangle r, Color foreColor)
    {
        Color oldColor = g2.getColor();
        g2.setColor(foreColor);
        g2.fill3DRect(r.x, r.y, r.width, r.height, true);
        g2.setColor(oldColor);

    }

    /**
     * draw an image in a rectangle
     *
     * @param gr  non-null graphic context
     * @param img non-null image
     * @param r   non-null rectangle
     * @param obs non-null observer
     */
    public static void raiseOval(Graphics2D g2, Rectangle r, Color foreColor)
    {
        Color background = g2.getBackground();
        Color oldColor = g2.getColor();
        Ellipse2D e = new Ellipse2D.Double(r.getX(),
                r.getY(),
                r.getWidth(),
                r.getHeight());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Paint oldPaint = g2.getPaint();
        GradientPaint gp = new GradientPaint(r.x, r.y,
                foreColor, r.width, r.height, background, true);

        g2.setPaint(gp);
        g2.fill(e);
        g2.setPaint(oldPaint);
        g2.setColor(oldColor);

    }

    public static Rectangle makeMaximumSquare(Rectangle r)
    {
        return inscribeWithProportions(r, 1.0);
    }

    public static double aspect(Image img)
    {
        return (double) img.getWidth(null) / (double) img.getHeight(null);
    }

    public static Rectangle sizeForImage(Rectangle r, Image img)
    {
        return inscribeWithProportions(r, aspect(img));
    }

    public static Rectangle inscribeWithProportions(Rectangle r, double xOverY)
    {
        double w = r.getWidth();
        double h = r.getHeight();
        double ratio = w / h;
        if (Math.abs(ratio - xOverY) < 0.01)
            return r;
        if (ratio > xOverY) { // too wide
            double newX = h * xOverY;
            int newW = (int) newX;
            int offset = (r.width - newW) / 2;
            return new Rectangle(r.x + offset, r.y, newW, r.height);
        }
        else {
            double newY = w / xOverY;
            int newH = (int) newY;
            int offset = (r.height - newH) / 2;
            return new Rectangle(r.x, r.y + offset, r.width, newH);

        }

    }


    public static boolean isInformationRequest(MouseEvent e)
    {
        // Right click
        int button = e.getButton();
        return (button == MouseEvent.BUTTON3);
    }

    /**
     * build a string describing the current object
     *
     * @param cmp non-null component
     * @return
     */
    public static String buildInfoText(Component cmp, int indent, boolean asChild,
                                       Set<Component> handled)
    {
        if (cmp instanceof IInfoTextBuilder) {
            return ((IInfoTextBuilder) cmp).buildInfoText(indent, asChild);
        }
        StringBuffer sb = new StringBuffer();
        sb.append(buildSelfInfoText(cmp, indent));
        if (!asChild)
            sb.append(buildParentInfoText(cmp, indent + 1));
        if (cmp instanceof Container) {
            Component[] components = ((Container) cmp).getComponents();
            if (components.length > 0) {
                for (Component c : components) {
                    if (handled.contains(c))
                        continue;
                    handled.add(c);
                    sb.append(buildInfoText(c, indent + 1, true, handled));
                }
            }
        }
        return sb.toString();
    }

    public static String buildParentInfoText(Component cmp, int indent)
    {
        StringBuffer sb = new StringBuffer();
        //      int parentIndent = indent + 1;
        Component parent = cmp.getParent();
        while (parent != null) {
            //         parentIndent = indent + 1;
            Util.indentStringBuffer(sb, indent);
            sb.append("Parent " + parent.getClass().getName());
            sb.append("\n");
            //    String text = buildSelfInfoText(parent, parentIndent);
            //   Util.indentStringBuffer(sb, indent);
            //    sb.append(text);
            //    sb.append("\n");
            parent = parent.getParent();
            indent += 2;
        }
        return sb.toString();
    }

    /**
     * build a string describing the current object
     *
     * @param cmp non-null component
     * @return
     */
    public static String buildSelfInfoText(Component cmp, int indent)
    {
        StringBuffer sb = new StringBuffer();
        Util.indentStringBuffer(sb, indent);
        sb.append("Class " + cmp.getClass().getName());
        sb.append("\n");
        return sb.toString();
    }

    /**
     * tell me somethoine about the component clicked on
     *
     * @param e  non-null mouse event
     * @param pC non-null ancesdtor
     */
    public static void displayInformation(MouseEvent e, Component pC)
    {
        Point point = e.getPoint();
        int x = (int) point.getX();
        int y = (int) point.getY();
        Set<Component> handled = new HashSet<Component>();
        Component cmp = SwingUtilities.getDeepestComponentAt(pC, x, y);
        String text = null;
        if (cmp instanceof IInfoTextBuilder) {
            text = ((IInfoTextBuilder) cmp).buildInfoText();
        }
        else {
            UIUtilities.buildInfoText(cmp, 0, false, handled);
        }
        JConsole console = new JConsole("Details", text);
    }


    public static Object[] getListItems(JList list)
    {
        ListModel lm = list.getModel();
        synchronized (lm) {
            Object[] ret = new Object[lm.getSize()];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = lm.getElementAt(i);

            }
            return ret;
        }
    }


    /**
     * Builds a Action Menu
     */
    protected static JMenu buildActionMenu(JFrame bar)
    {
        String stringFromText = ResourceString.getStringFromText("Action");
        JMenu ret = new JMenu(stringFromText);
        bar.add(ret);
        return (ret);
    }

    public static final int SEPARATION = 5;

    public static void centerOnWall(JComponent comp, Container parent, int wall)
    {
        if (parent == null || comp == null)
            return;
        Dimension size = parent.getSize();
        Dimension compSize = comp.getPreferredSize();
        compSize = new Dimension(Math.min(size.width, compSize.width),
                Math.min(size.height, compSize.height));
        comp.setSize(compSize);
        int left = SEPARATION;
        int top = SEPARATION;
        switch (wall) {
            case BOTTOM_WALL :
                top = size.height - compSize.height - SEPARATION;
            case TOP_WALL :
                left = (size.width - compSize.width) / 2;
                break;
            case RIGHT_WALL :
                left = size.width - compSize.width - SEPARATION;
            case LEFT_WALL :
                top = (size.height - compSize.height) / 2;
                break;
            default:
                throw new IllegalArgumentException("only 0-3 allowed as wall");
        }
        comp.setLocation(left, top);
    }

//    public static void appendTime(StringBuilder pSb, ISynthesisLogger pLog)
//    {
//        appendTime(pSb, pLog.getElapsedTimeString());
//    }


    public static void appendTime(StringBuilder pSb, String tstr)
    {
        pSb.append(" time=\"");
        pSb.append(tstr);
        pSb.append("\" ");
    }


//    public static void logSignificantAction(String pFullPath, FluidMovementParameters fp)
//    {
//        ISynthesisLogger pLogger = (ISynthesisLogger) DeviceFactory.getLogger(
//                LogEnum.SIGNIFICANT_ACTIONS_LOG_ENUM);
//        String message = fp.buildLogMessage(pLogger.getElapsedTimeString());
//        StringBuilder sb = new StringBuilder();
//        sb.append(StringOps.indentString(
//                LogSignificantActionOperation.SIGNIFICANT_OPERATION_INDENT_LEVEL));
//        sb.append("<Comment text=\"");
//        String fixedCmt = pFullPath.replace('>', '|');
//        sb.append(fixedCmt);
//        sb.append("\" />\n");
//        pLogger.appendText(sb.toString());
//
//        pLogger.appendText(message);
//    }

    public static boolean confirm(String text)
    {
        throw new UnsupportedOperationException("Fix This"); // ToDo
       // return confirm(text,JFrame.getCurrentFrame());
    }

    public static boolean confirm(String text,Component parent)
    {
        int ret =  JOptionPane.showConfirmDialog(parent,text,"Are you sure?",JOptionPane.OK_CANCEL_OPTION);
        return ret ==  JOptionPane.OK_OPTION;
    }


    /**
     * HTML works well for multiline tooltips
     *
     * @param in non-html string
     * @return Html out
     */
    public static String convertTooltipToHtml(String in)
    {
        if (in.startsWith("<html>"))
            return in;
        StringBuffer sb = new StringBuffer();
        sb.append("<html>\n");
        for (int i = 0; i < in.length(); i++) {
            char c = in.charAt(i);
            if (c == '\n')
                sb.append("\n<br>\n");
            else
                sb.append(c);
        }
        sb.append("</html>\n");
        String s = sb.toString();
        return s;
    }


    public static class OkPanel extends JPanel
    {
        public OkPanel(JDialog subject)
        {
            setLayout(new BorderLayout());
            add(new JButton(new HideDialogAction(subject)), BorderLayout.EAST);
        }
    }

    public static class HideDialogAction extends AbstractAction
    {
        private final JDialog m_Dialog;

        public HideDialogAction(JDialog dlg)
        {
            m_Dialog = dlg;
            putValue(Action.NAME, ResourceString.getStringFromText("OK"));
        }

        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e)
        {
            m_Dialog.setVisible(false);
        }
    }


}

