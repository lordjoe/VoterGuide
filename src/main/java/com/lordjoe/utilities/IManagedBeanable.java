package com.lordjoe.utilities;

import java.beans.PropertyChangeListener;

/**
 * Interface marking the object as a
 * following bean rules and offering property change
 * notification
 * @see IBeanable
 * @author Steve Lewis smlewis@lordjoe.com
 */
public interface IManagedBeanable extends IBeanable {
    public void addPropertyChangeListener(PropertyChangeListener added);

    public void removePropertyChangeListener(PropertyChangeListener added);
}
