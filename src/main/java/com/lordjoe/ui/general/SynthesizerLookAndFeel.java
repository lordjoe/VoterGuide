package com.lordjoe.ui.general;
/*
* This file is copyright to javareference.com
* for more information visit,
* http://www.javareference.com
*/

import com.lordjoe.general.UIUtilities;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;


/**
 * com.lordjoe.ui.general.SynthesizerLookAndFeel
 *
 * @author slewis
 * @date Jun 23, 2005
 */
public class SynthesizerLookAndFeel extends MetalLookAndFeel
{
    public static final Class THIS_CLASS = SynthesizerLookAndFeel.class;
    public static final SynthesizerLookAndFeel EMPTY_ARRAY[] = {};



    /**
     * This method is overriden from MetalLookAndFeel to set a different color for
     * the ToolTip background
     */
    protected void initSystemColorDefaults(UIDefaults table)
    {
        //call the super method and populate the UIDefaults table
        super.initSystemColorDefaults(table);
        ToolTipManager.sharedInstance().setLightWeightPopupEnabled(true);
        //After populating the UIDefaults table reset the info key value to the
        //desired color. In this case lightGrey
        Color clr = UIUtilities.TOOLTIP_COLOR;
        table.put("info", new ColorUIResource(clr.getRed(), clr.getGreen(), clr.getBlue()));
    }

}