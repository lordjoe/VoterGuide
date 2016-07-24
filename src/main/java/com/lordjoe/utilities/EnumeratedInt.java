package com.lordjoe.utilities;



/**
 * enumarated type integer values
 */
public abstract class EnumeratedInt extends EnumeratedType
{
    private final Integer m_test;

    protected EnumeratedInt(String name, Integer value) {
        super(name);
        m_test = value;
        rememberValue();
    }

    protected EnumeratedInt(String name, int value) {
        this(name, new Integer(value));
    }


    protected EnumeratedInt getEnumeratedIntByValue(int value) {
        return ((EnumeratedInt) getEnumeratedTypeByValue(new Integer(value)));
    }

    /**
     * return the class of the object held by the
     * type
     * @return non-null class
     */
    public Class getEnumeratedClass() {
        return (Integer.class);
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
    public Integer getEnumeratedInteger() {
        return ((Integer) getEnumeratedData());
    }

    /**
     * return the data underlying the enumeration
     * @return non-null class
     */
    public int getValue() {
        return (getEnumeratedInteger().intValue());
    }

    public String getStringValue()
    {
        return Integer.toString(getValue());
    }

    public boolean equals(Object test) {
        if(test == null)
             return false;
        if (getClass() != test.getClass())
            return (false);
        return (((EnumeratedInt) test).getValue() == getValue());
    }

    public int hashCode() {
        return (getValue());
    }

    public int compareTo(Object test)
    {
        // first sort by class then value
        if(test.getClass() != getClass()) {
            String myClass = ClassUtilities.shortClassName(getClass());
            String testClass = ClassUtilities.shortClassName(test.getClass());
            return myClass.compareTo(testClass);
          }
        EnumeratedInt realIn = (EnumeratedInt)test;
        int testValue = realIn.getValue();
        int val = getValue();
        if(val == testValue)
            return(0);
         if(val > testValue)
             return(1);
        else
             return(-1);
    }

}
