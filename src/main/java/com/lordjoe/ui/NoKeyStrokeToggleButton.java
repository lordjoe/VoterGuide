package com.lordjoe.ui;

import javax.swing.InputMap;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
 /**
     *
     * <p>Title: NoKeyStrokesToggleButton</p>
     * <p>Description: Cancels keystroke bunctionality for JToggleButton.
     * Spacebar action toggles the JToggleButton. This can potentially
     * disrupt long experiment. Removed on request of Tai 03/30/05</p>
     * <p> The java code resource: http://java.sun.com/products/jfc/tsc/special_report/kestrel/keybindings.html#new_bindings </p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: CBMX</p>
     *
     * @version 1.0
     */
    public class NoKeyStrokeToggleButton extends JToggleButton
    {
        public NoKeyStrokeToggleButton()
        {
            super();
            cancelKeyStrokes();
        }

        public void cancelKeyStrokes ()
        {
            InputMap im0 = this.getInputMap();
            KeyStroke [] ks0 = im0.allKeys();

            for(int i = 0; i < ks0.length; i++)
            {
                this.getInputMap().put(ks0[i],"none");
            }
        }
    }
