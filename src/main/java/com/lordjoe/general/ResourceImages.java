package com.lordjoe.general;

import com.lordjoe.lang.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * com.lordjoe.runner.ui.general.ResourceImages
 *
 * @author Steve Lewis
 * @date Feb 26, 2007
 */
public class ResourceImages {
    public static final Class THIS_CLASS = ResourceImages.class;
    public static final ResourceImages EMPTY_ARRAY[] = {};
    public static final String FILE_STRING = "file://";

    public static final String UNKNOWN_SENSOR_IMAGE = "bluemeta.gif";
    public static final String GREEN_SENSOR_IMAGE = "GreenLight.gif";
    public static final String RED_SENSOR_IMAGE = "RedLight.gif";
    public static final String YELLOW_SENSOR_IMAGE = "YellowLight.gif";

    public static final Map<String, ImageIcon> gImages =
            new HashMap<String, ImageIcon>();

    public static final Map<String, ImageIcon> gTransparentImages =
            new HashMap<String, ImageIcon>();

    public static ImageIcon getImage(String name) {
        ImageIcon ret = gImages.get(name);
        if (ret == null) {
            if (isFileString(name))
                ret = getImageFile(name);
            else
                ret = getImageResource(name);
        }
        return ret;
    }

    public static ImageIcon getTransparentImage(String name) {
        ImageIcon ret = gTransparentImages.get(name);
        if (ret == null)
            ret = getTransparentImageResource(name);
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


    protected static ImageIcon getTransparentImageResource(String name) {
        ImageIcon image = getImage(name);
        if (image == null)
            return null;
        try {
            Image plainImage = image.getImage();
            if (plainImage.getWidth(null) < 0) {
                gTransparentImages.put(name, image);
                return image;

            } else {
                Color cornerColor = UIUtilities.getCornerColor(plainImage);
                Image transparentImage = UIUtilities.makeColorTransparent(plainImage, cornerColor);
                ImageIcon icn = new ImageIcon(transparentImage);
                gTransparentImages.put(name, icn);
                return icn;
            }
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Cannot process Image " + name + " cause " + e.getMessage());
        }
    }

    protected static ImageIcon getImageResource(String name) {
        // look for an image to associate
        String resourcename = "/images/" + name;
        InputStream inp = THIS_CLASS.getResourceAsStream(resourcename);
        if (inp != null) {
            byte[] bytes = FileUtil.readInBytes(inp);
            ImageIcon img = new ImageIcon(bytes);
            gImages.put(name, img);
            return img;
        } else {
            return null;
        }
    }


    protected static ImageIcon getImageFile(String name) {

        if (!isFileString(name))
            throw new IllegalArgumentException(
                    "File images must start with \"file://\""); // ToDo change
        String filename = name.substring(FILE_STRING.length());
        try {
            // look for an image to associate
            InputStream inp = new FileInputStream(filename);
            if (inp != null) {
                ImageIcon img = new ImageIcon(FileUtil.readInBytes(inp));
                gImages.put(name, img);
                return img;
            }
        }
        catch (IOException ex) {

        }
        return null;
    }

    public static boolean isFileString(String name) {
        return name.toLowerCase().startsWith(FILE_STRING);
    }

}
