package com.lordjoe.ui.general;

import com.lordjoe.ui.*;

/**
 * com.lordjoe.ui.general.IDisplayStackChangeListener
 *
 * @author Steve Lewis
 * @date Jan 19, 2008
 */
public interface IDisplayStackChangeListener {
    public static IDisplayStackChangeListener[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IDisplayStackChangeListener.class;

    /**
     * respond to changes in the display stack
     * @param stk
     */
    public void onDisplayStackChange(IDisplayStack stk);

}
