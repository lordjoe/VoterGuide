package com.lordjoe.ui;

import javax.swing.*;
import java.util.*;

/**
 * com.lordjoe.ui.IconUtilities
 *
 * @author Steve Lewis
 * @date May 20, 2008
 */
public abstract class IconUtilities
{
    public static IconUtilities[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IconUtilities.class;

    private IconUtilities() {}

    /**
     * must pass 3,5 or 6 names
     * pass
     *    normal,disabled,pressed <selected>,<selectedPressed>
     * @param icons
     * @return
     */
    public static Map<ImageState, Icon> buildIconMap(String[] icons)
    {
        Map<ImageState, Icon> ret = new HashMap<ImageState, Icon>();
         Icon icn = null;
        int index = 0;
        icn = ResourceImages.getTransparentImage(icons[index++]);
        if(icn != null)
            ret.put(ImageState.Normal,icn);
        icn = ResourceImages.getTransparentImage(icons[index++]);
        if(icn != null)
            ret.put(ImageState.Disabled,icn);
        icn = ResourceImages.getTransparentImage(icons[index++]);
        if(icn != null)
            ret.put(ImageState.Pressed,icn);
        if(icons.length > index) {
            icn = ResourceImages.getTransparentImage(icons[index++]);
            if(icn != null)
                ret.put(ImageState.Selected,icn);

        }
        if(icons.length > index) {
            icn = ResourceImages.getTransparentImage(icons[index++]);
            if(icn != null)
                ret.put(ImageState.SelectedPressed,icn);

        }
        return ret;
    }
}
