package com.lordjoe.ui;

import java.awt.*;
import java.io.*;

/**
 * com.lordjoe.ui.ImageHolder
 *  holder for a static image
 * @author Steve Lewis
 * @date Sep 22, 2005
 */
public class ImageHolder implements IImageSource
{
    private final Image m_Image;
    public ImageHolder(Image img)
     {
        m_Image = img;
     }
    public ImageHolder(File img)
      {
         this(buildImage(img));
      }
    public ImageHolder(String fileName)
      {
         this(buildImage(fileName));
      }

    protected static Image buildImage(String s)
    {
        Toolkit tk = Toolkit.getDefaultToolkit();
        return tk.getImage(s);
    }

    protected static Image buildImage(File s)
    {
         return buildImage(s.getAbsolutePath());
    }

    public Image getImage()
    {
        return m_Image;
     }

}
