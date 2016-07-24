package com.lordjoe.utilities;

/**
 * This allows an object to declarer registrable name
 * usually to register with RMI
 * @author STeve Lewis smlewis@lordjoe.com
 */
public interface IRegisterable {
    /**
     * responds with the System name to a ping
     */
    public String getRegisteredName();

}
