/**
 * JToggleList - create a list with a di
 */
package com.lordjoe.utilities;
import javax.swing.plaf.basic.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.*;

import java.awt.*;
import java.awt.event.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;


public class JToggleList extends JList
{
    private static final String uiClassID = "ToggleListUI";
    private static final Class uiClassLoad = com.lordjoe.utilities.ToggleListUI.class;

    static {
        UIDefaults defaults = UIManager.getDefaults();
        defaults.put(uiClassID,"selector.ToggleListUI");
    }

// end class JToggleList
    /**
     * Returns the name of the UIFactory class that generates the
     * look and feel for this component.
     *
     * @return "ToggleListUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }
}



