package com.lordjoe.utilities;

import java.io.*;
import java.util.*;

/**
 * class to pass a map with a name
 * @author Steve Lewis
 */
public class NamedMap extends HashMap  implements INameable,Serializable {
    private String m_Name;

    public NamedMap() {
        super();
    }

    public NamedMap(String name) {
        this();
        setName(name);
    }


    /**
     * code to get parameter Name
     * @return <Add Comment Here>
     * @see getName
     */
    public String getName() {
        return (m_Name);
    }

    /**
     * code to set parameter Name
     * @param in <Add Comment Here>
     * @see getName
     */
    public void setName(String in) {
        m_Name = in;
    }
}
