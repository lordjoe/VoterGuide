package com.lordjoe.ui;

import java.awt.*;

/**
 * com.lordjoe.ui.IImageSource
 * implemented by an object that can supply an image
 * this allows a display to deal with both images and
 * generated images
 * @see ImageHolder
 * @author Steve Lewis
 * @date Sep 22, 2005
 */
public interface IImageSource
{
    /**
     * return the image
     * @return presumably non-null image
     */
    public Image getImage();
}
