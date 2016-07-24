package com.lordjoe.general;

import com.lordjoe.propertyeditor.*;

/**
 * com.lordjoe.general.ISavable
 *   general interface for an object which can
 * be set dirty and saved
 * @author Steve Lewis
 * @date Jan 22, 2008
 */
public interface ISavable {
    public static ISavable[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ISavable.class;

    /**
     * set the dirty flag
     * @param dirty
     */
    public void setDirty(boolean dirty) ;

    /**
     * is dirty until saved
     */
    public boolean isDirty( );

    /**
     * save the object and presumably clear dirty
     */
    public void save( ) ;
    /**
     * rollback the object and presumably clear dirty
     */
    public void rollback( ) ;

    /**
     * add a change listener
     *
     * @param added non-null change listener
     */
    public void addDirtyChangeListener(DirtyChangeListener added);

    /**
     * remove a change listener
     *
     * @param removed non-null change listener
     */
    public void removeDirtyChangeListener(DirtyChangeListener removed);
}
