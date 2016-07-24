package com.lordjoe.utilities;

import java.io.Serializable;

/**
 * Class to pass size and Items
 * @author S Lewis
 */
public class ObjectAndSizeHolder implements Serializable {
    public Object[] m_Items;
    public int m_Size;

    public ObjectAndSizeHolder(Object[] items, int size) {
        m_Items = items;
        m_Size = size;
    }
}
