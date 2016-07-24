package com.lordjoe.utilities;



/**
 * enumarated type integer values
 */
public abstract class EnumeratedString extends EnumeratedType {
    private final String m_test;

    protected EnumeratedString(String name, String value) {
        super(name);
        m_test = value;
        rememberValue();
    }


    /**
     * return the class of the object held by the
     * type
     * @return non-null class
     */
    public Class getEnumeratedClass() {
        return (String.class);
    }


    /**
     * return the data underlying the enumeration
     * @return non-null class
     */
    public Object getEnumeratedData() {
        return (m_test);
    }


    /**
     * return the data underlying the enumeration
     * @return non-null class
     */
    public String getEnumeratedString() {
        return ((String) getEnumeratedData());
    }

    /**
      * return the data underlying the enumeration
      * @return non-null class
      */
     public String getValue() {
         return (getEnumeratedString());
     }
    /**
      * return the data underlying the enumeration
      * @return non-null class
      */
     public String getStringValue() {
         return (getValue());
     }

 

}
