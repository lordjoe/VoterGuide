package com.lordjoe.ui;

/**
 * com.lordjoe.ui.IFastDrawComponent
 *  This is a tagging interface marking the component as
 * capable of short curcuiting drawing in a low memory
 * situation - used to deal with some redraw issues
 *   Components should check to see if ancestors implement this
 * interface in which case the action should be left to the ancestor
 * @author Steve Lewis
 * @date Jan 5, 2006
 */
public interface IFastDrawComponent
{
}
