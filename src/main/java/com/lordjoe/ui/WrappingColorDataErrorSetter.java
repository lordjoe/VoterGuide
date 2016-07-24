package com.lordjoe.ui;

import java.util.*;

/**
 * com.lordjoe.ui.WrappingProgressMonitor
 * a progress monitor wrapping multipla monitors
 *
 * @author Steve Lewis
 * @date Feb 2, 2006
 */
public class WrappingColorDataErrorSetter implements IColorDataErrorSetter
{
    public final static WrappingColorDataErrorSetter[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = WrappingColorDataErrorSetter.class;

    private final List m_WrappedItems;

    public WrappingColorDataErrorSetter()
    {
        m_WrappedItems = Collections.synchronizedList(new ArrayList());
    }

    public void addErrorMonitor(IColorDataErrorSetter added)
    {
        m_WrappedItems.add(added);
    }

    public void removeErrorMonitor(IColorDataErrorSetter added)
    {
        m_WrappedItems.remove(added);
    }

    protected IColorDataErrorSetter[] getElements()
    {
        synchronized (m_WrappedItems) {
            IColorDataErrorSetter[] ret = new IColorDataErrorSetter[m_WrappedItems.size()];
            m_WrappedItems.toArray(ret);
            return ret;
        }
    }


    /**
     * clear errors
     */
    public void clearErrors()
    {
        synchronized (m_WrappedItems) {
            if (m_WrappedItems.isEmpty())
                return;
            IColorDataErrorSetter[] items = getElements();
            for (int i = 0; i < items.length; i++) {
                IColorDataErrorSetter item = items[i];
                item.clearErrors();
            }
        }
    }

    /**
     * set the given location as having an error
     *
     * @param location
     */
    public void setError(int location)
    {
        synchronized (m_WrappedItems) {
            if (m_WrappedItems.isEmpty())
                return;
            IColorDataErrorSetter[] items = getElements();
            for (int i = 0; i < items.length; i++) {
                IColorDataErrorSetter item = items[i];
                item.setError(location);
            }
        }
    }

    /**
     * true if the location has an error
     *
     * @param location
     * @return
     */
    public boolean hasError(int location)
    {
        synchronized (m_WrappedItems) {
            if (m_WrappedItems.isEmpty())
                return false;
            IColorDataErrorSetter[] items = getElements();
            IColorDataErrorSetter item = items[0];
            return item.hasError(location);
        }
    }
}


