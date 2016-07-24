package com.lordjoe.utilities;

/**
 * $PACKAGE_NAME.WatchedPropertiesListener
 * @Author Steve Lewis smlewis@lordjoe.com
 * *see  WatchedProperties
 */
public interface WatchedPropertiesListener {

    public static final WatchedPropertiesListener[] EMPTY_ARRAY = {};
    /**
     * do what is needful when the properties of in change
     * @param in non-null WatchedProperties
     */
    public void propertiesAreReloaded( WatchedProperties in);
}
