package com.lordjoe.lang;

import com.lordjoe.utilities.*;

/**
 * com.lordjoe.lang.IResource
 *   a resource which may need
 * for an actio to proceed
 * @author Steve Lewis
 * @date Jan 24, 2006
 */
public interface IResource extends INamedObject
{
    /**
     * if true the resource is available
     * otherwise it is in use
     * @return as above
     */
     public boolean isAvailable();

    /**
     * add an interested party
     * @param listener non-null listener
     */
    public void addResourceListener(ResourceListener listener);

    /**
     *
     * @param listener
     */
    public void removeResourceListener(ResourceListener listener);
}
