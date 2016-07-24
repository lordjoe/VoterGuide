package com.lordjoe.ui;

import com.lordjoe.lang.*;

import java.util.*;

/**
 * com.lordjoe.ui.WrappingProgressMonitor
 * a progress monitor wrapping multipla monitors
 * @author Steve Lewis
 * @date Feb 2, 2006
 */
public class WrappingProgressMonitor implements IProgressMonitor
{
    public final static WrappingProgressMonitor[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = WrappingProgressMonitor.class;

    private final List m_WrappedItems;

    public WrappingProgressMonitor()
    {
        m_WrappedItems = Collections.synchronizedList(new ArrayList());
    }

    public void addProgressMonitor(IProgressMonitor added)
    {
        m_WrappedItems.add(added);
    }

    public void removeProgressMonitor(IProgressMonitor added)
    {
        m_WrappedItems.remove(added);
    }

    protected IProgressMonitor[] getElements()
    {
        synchronized (m_WrappedItems) {
            IProgressMonitor[] ret = new IProgressMonitor[m_WrappedItems.size()];
            m_WrappedItems.toArray(ret);
            return ret;
        }
    }

    /**
     * set the display color
     *
     * @param c
     */
    public void setProgressState(OperationStateEnum c)
    {
        synchronized (m_WrappedItems) {
            if (m_WrappedItems.isEmpty())
                return;
            IProgressMonitor[] items = getElements();
            for (int i = 0; i < items.length; i++) {
                IProgressMonitor item = items[i];
                item.setProgressState(c);
            }
        }
    }

    /**
     * set the maximum value
     *
     * @param c value
     */
    public void setProgressMax(int c)
    {
        synchronized (m_WrappedItems) {
             if (m_WrappedItems.isEmpty())
                 return;
             IProgressMonitor[] items = getElements();
             for (int i = 0; i < items.length; i++) {
                 IProgressMonitor item = items[i];
                 item.setProgressMax(c);
             }
         }
      }

    /**
     * set the current value
     *
     * @param c
     */
    public void setProgress(int c)
    {
        synchronized (m_WrappedItems) {
            if (m_WrappedItems.isEmpty())
                return;
            IProgressMonitor[] items = getElements();
            for (int i = 0; i < items.length; i++) {
                IProgressMonitor item = items[i];
                item.setProgress(c);
            }
        }

    }

    /**
     * set the displayed text
     *
     * @param c
     */
    public void setProgressText(String c)
    {
        synchronized (m_WrappedItems) {
             if (m_WrappedItems.isEmpty())
                 return;
             IProgressMonitor[] items = getElements();
             for (int i = 0; i < items.length; i++) {
                 IProgressMonitor item = items[i];
                 item.setProgressText(c);
             }
         }
     }
}


