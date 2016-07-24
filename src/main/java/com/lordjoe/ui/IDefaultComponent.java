package com.lordjoe.ui;


import java.util.*;

/**
 * com.lordjoe.ui.IDefaultComponent
 * A component that is part of a generic NDC application
 * @author slewis
 * @date Feb 21, 2005
 */
public interface IDefaultComponent {
    public static final Class THIS_CLASS = IDefaultComponent.class;
    public static final IDefaultComponent EMPTY_ARRAY[] = {};

   /**
    * return ancestor combe frame
    * @return non-null frame
    */
    public DefaultFrame getDefaultFrame();

   /**
    * get the application's resource bundle
     * @return
    */
    public ResourceBundle getResources();

    /**
     * convert a key to a resource string
     * @param nm  key - possibly english text
     * @return non-null resource string
     */
      public String getResourceString(String nm);


    /**
      * convert a key to a resource object
      * @param nm  key - possibly english text
      * @return possibly null resource object
      */
       public Object getResourceObject(String nm);

}