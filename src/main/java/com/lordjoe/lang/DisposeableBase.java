package com.lordjoe.lang;

/**
 * @author slewis
 *
 * implementing code for IDIsposeable interface - implementing classes
 * should inherit from this class or do these things
 */
public abstract class DisposeableBase implements IDisposable {
    /**
     * marker that the object has been disposedd
     */
    private boolean m_Disposed;
    
    /**
     * mark the object as having been disposed
     * @param value must be true
     */
   protected void setDisposed(boolean value)
    {
        if(value == m_Disposed)
            	return;
         guaranteeNotDisposed();
        m_Disposed = value;
    }
    /**
      * perform cleanup and mark the object as dispose
      * multiple calls to this method haveno effect after the first
      * classes implementing the interfdace should call dispose in
      * finalize
      */
    public void disposeObject() throws Throwable
    {
        if(isDisposed())
            return;
        setDisposed(true);
     }
    /**
      * perform cleanup and mark the object as dispose
      * multiple calls to this method haveno effect after the first
      * classes implementing the interfdace should call dispose in
      * finalize
      */
    public void tryDisposeObject()
    {
        try {
           disposeObject();
        }
        catch(Throwable ex) {

        }
     }

    
    /**
     * true if the object has been disposed
     * @return
     */
    public boolean isDisposed() {
         return m_Disposed;
    }

    /**
     * throw UseOfDisposedObjectException if the object has been disposed otherwise 
     * do nothing 
     * @throws UseOfDisposedObjectException as above
     */
    public void guaranteeNotDisposed()  {
       if(isDisposed())
          throw new IllegalStateException("already disposed"); 

    }
    /**
     * override of finalize to call dispose
     * Make final to force use of disposeObject for now slewis
     */
    protected void finalize() throws Throwable
    {
        disposeObject();
        super.finalize();
    }

}
