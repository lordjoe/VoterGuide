package com.lordjoe.ui;

import com.lordjoe.general.UIUtilities;
import com.lordjoe.lang.FileUtil;
import com.lordjoe.lang.GeneralUtilities;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * com.lordjoe.ui.ResourceImages
 *
 * @author slewis
 * @date Mar 10, 2005
 */
public class ResourceImages
{
    public static final Class THIS_CLASS = ResourceImages.class;
    public static final ResourceImages EMPTY_ARRAY[] = {};
    public static final String  FILE_STRING = "file://";

    public static final String UNKNOWN_SENSOR_IMAGE = "bluemeta.gif";
    public static final String GREEN_SENSOR_IMAGE = "GreenLight.gif";
    public static final String RED_SENSOR_IMAGE = "RedLight.gif";
    public static final String YELLOW_SENSOR_IMAGE = "YellowLight.gif";
    public static final String GREY_SENSOR_IMAGE = "GreyLight.gif";

    public static final Map<String, ImageIcon> gImages =
            new HashMap<String, ImageIcon>();

    public static final Map<String, ImageIcon> gTransparentImages =
            new HashMap<String, ImageIcon>();

    public static ImageIcon getImage(String name)
    {
        ImageIcon ret = gImages.get(name);
        if (ret == null)  {
            if(isFileString(name))
                ret = getImageFile(name);
            else
                ret = getImageResource(name);
        }
        return ret;
    }

    /**
     * like get image but no foul for not found
     * @param name
     * @return
     */
    public static ImageIcon findImage(String name)
    {
        ImageIcon ret = gImages.get(name);
        if (ret == null)  {
            if(isFileString(name))
                ret = getImageFile(name);
            else
                ret = findImageResource(name);
        }
        return ret;
    }

    /**
     * set teh default image to be transperant
     * @param name
     * @return
     */
    public static ImageIcon setTransparentImage(String name)
    {
        ImageIcon icn = getTransparentImage( name);
        gImages.put(name, icn);
        return icn;
    }

    public static ImageIcon getTransparentImage(String name)
    {
        ImageIcon ret = gTransparentImages.get(name);
        if (ret == null)
            ret = getTransparentImageResource(name);
        return ret;
    }


    protected static ImageIcon getTransparentImageResource(String name)
    {
        ImageIcon image = getImage(name);
        if (image == null)
            return null;
        Image plainImage = image.getImage();
        Color cornerColor = UIUtilities.getCornerColor(plainImage);
        Image transparentImage = UIUtilities.makeColorTransparent(plainImage, cornerColor);
        ImageIcon icn = new ImageIcon(transparentImage);
        gTransparentImages.put(name, icn);
        return icn;
    }

    protected static ImageIcon getImageResource(String name)
    {
        // look for an image to associate
        ImageIcon ret = findImageResource( name);
        if(ret == null) {
            findImageResource( name); // redug repeat
            GeneralUtilities.printString("Failed to find Image resource " + name);
        }
        return ret;
    }

    protected static ImageIcon findImageResource(String name)
    {
        // look for an image to associate
        String resourcename = name;
        if(!name.startsWith("/"))
               resourcename = "/resources/images/" + name;
        InputStream inp = THIS_CLASS.getResourceAsStream(resourcename);
        if (inp != null) {
            ImageIcon img = new ImageIcon(FileUtil.readInBytes(inp));
            gImages.put(name, img);
            return img;
        }
        return null;
    }


    protected static ImageIcon getImageFile(String name)
    {

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

    public static boolean isFileString(String name)
    {
        return name.toLowerCase().startsWith(FILE_STRING);
    }

}
