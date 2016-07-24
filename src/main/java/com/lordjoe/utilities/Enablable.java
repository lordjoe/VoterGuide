/**{ file
 @name Enablable.java
 @function - Interface for objects supporting enabling

 @author> Steven M. Lewis
 @copyright>
 ************************
 *  Copyright (c) 1996,97,98
 *  Steven M. Lewis
 *  www.LordJoe.com
 ************************

 @date> Mon Jun 22 21:48:27 PDT 1998
 @version> 1.0
 }*/
package com.lordjoe.utilities;

/**
 Interface used by list selector to turn items on and off
 */

public interface Enablable {
    /**
     Enablable object do something is enabled - nothing if not
     @return - true if enabled
     */
    public boolean isEnabled();

    /**
     set enabled state
     @param doit - new state
     */
    public void setEnabled(boolean doit);

    /**
     return short text explaining the action - this can be
     used as a title in a checkbox
     */
    public String getText();

    /**
     return longer text more fully explaining the action - this can be
     used as a text in a milti-line tooltip
     */
    public String getExplaination();
}
