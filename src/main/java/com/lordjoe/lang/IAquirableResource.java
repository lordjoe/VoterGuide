package com.lordjoe.lang;

/**
 * com.lordjoe.lang.IResource
 *   a resource which may need  
 * for an actio to proceed
 * @author Steve Lewis
 * @date Jan 24, 2006
 */
public interface IAquirableResource extends IResource
{
    /**
     * Resources may be acquired 
     * @return true is acquire succeeded
     * @throws UnsupportedOperationException 
     */
     public boolean acquire();

    /**
     * release a resource being held
     */
     public void release();

}
