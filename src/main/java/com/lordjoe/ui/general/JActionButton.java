package com.lordjoe.ui.general;

import com.lordjoe.general.UIUtilities;
import com.lordjoe.lang.StringOps;
import com.lordjoe.ui.UIOps;

import javax.swing.*;
import java.awt.event.MouseEvent;


/**
 * com.lordjoe.ui.general.JActionButton
 *
 * @author slewis
 * @date May 3, 2005
 */
public class JActionButton extends JButton
{
    public static final Class THIS_CLASS = JActionButton.class;
    public static final JActionButton EMPTY_ARRAY[] = {};

    public JActionButton(Action act)
    {
        super(act);
        UIUtilities.registerComponent(this);
    }

    /**
     * Returns the string to be used as the tooltip for <i>event</i>.
     * By default this returns any string set using
     * <code>setToolTipText</code>.  If a component provides
     * more extensive API to support differing tooltips at different locations,
     * this method should be overridden.
     */
    public String getToolTipText(MouseEvent event)
    {
        Action act = getAction();
        String description = (String)act.getValue(Action.LONG_DESCRIPTION);
         if(!StringOps.isBlank(description))
             return UIOps.convertTooltipToHtml(description);
         description = (String)act.getValue(Action.SHORT_DESCRIPTION);
         if(!StringOps.isBlank(description))
             return description;
         return null;
    }

    /**
     * Makes the component visible or invisible.
     * Overrides <code>Component.setVisible</code>.
     *
     * @param aFlag true to make the component visible; false to
     *              make it invisible
     * @beaninfo attribute: visualUpdate true
     */
    public void setVisible(boolean aFlag)
    {
        super.setVisible(aFlag);
    }

    /**
      * @deprecated As of JDK version 1.1,
      *             replaced by <code>setVisible(boolean)</code>.
      */
     @Deprecated public void show()
     {
         super.show();
     }
    /**
      * @deprecated As of JDK version 1.1,
      *             replaced by <code>setVisible(boolean)</code>.
      */
     @Deprecated public void hide()
     {
         super.hide();

     }


}
