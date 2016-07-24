package com.lordjoe.lang;

/**
 * com.lordjoe.lang.ResourceListener
 *
 * @author Steve Lewis
 * @date Jan 24, 2006
 */
public interface ResourceListener
{
    /**
     * take action when resource availibility is different
     * @param target non-null resource
     */
    public void onResourceAvailabilityChange(IResource target);
}
