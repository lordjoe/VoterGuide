package com.lordjoe.lang;

/**
 * @author slewis
 *
 * alternative to directly calling finalize
 */
public interface IDisposable 
{
    /**
     * perform cleanup and mark the object as dispose
     * multiple calls to this method haveno effect after the first
     * classes implementing the interfdace should call dispose in
     * finalize
     */
    public void disposeObject() throws Throwable;

    /**
     * Dispose object which is allowed to fail
     */
    public void tryDisposeObject();

    /**
     * true if the object has been disposed
     * @return
     */
    public boolean isDisposed();
    
    /**
     * throw UseOfDisposedObjectException if the object has been disposed otherwise 
     * do nothing 
     * @throws IllegalStateException as above
     */
    public void guaranteeNotDisposed() throws IllegalStateException;
}
