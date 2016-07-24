package com.lordjoe.utilities;

import com.lordjoe.lib.xml.*;

import java.util.*;
import java.lang.reflect.*;

/**
 * A factoy with several was to create
 * objects implementing IIntegerValue
 * @author Steve Lewis
 * @see IIntegerValue
 */
public abstract class IntegerValueFactory {

    public static IIntegerValue buildValue(int in) {
        return (new FixedIntegerValue(in));
    }


    public static IIntegerValue buildValue(Map theMap, Object key) {
        return (new MapIntegerValue(theMap, key));
    }

    public static IIntegerValue buildValue(Object obj, String PropName) {
        if (obj instanceof Map)
            return (new MapIntegerValue((Map) obj, PropName));

        return (new PropertyIntegerValue(obj, PropName));
    }

    protected static class PropertyIntegerValue implements IIntegerValue {
        private Object m_Obj;
        private Method m_Method;

        public PropertyIntegerValue(Object obj, String PropName) {
            m_Obj = obj;
            Class objClass = obj.getClass();
            ClassProperty prop = ClassAnalyzer.getClassProperty(objClass, PropName);
            if (prop == null)
                throw new IllegalArgumentException(PropName + " is not a property of class '" +
                        objClass.getName() + "'");
            m_Method = prop.getGetMethod();
        }

        public int intValue() {
            try {
                Integer ret = (Integer) m_Method.invoke(m_Obj, Util.EMPTY_OBJECT_ARRAY);
                if (ret != null)
                    return (ret.intValue());
                return (0);
            } catch (Exception ex) {
                return (0);
            }
        }
    }


    protected static class MapIntegerValue implements IIntegerValue {
        private Map m_Map;
        private Object m_Key;

        public MapIntegerValue(Map theMap, Object key) {
            m_Map = theMap;
            m_Key = key;
        }

        public int intValue() {
            Object value = m_Map.get(m_Key);
            if (value == null)
                return (0);
            if (value instanceof Integer)
                return (((Integer) value).intValue());
            if (value instanceof IIntegerValue)
                return (((IIntegerValue) value).intValue());
            throw new IllegalStateException("Objects of class " + value.getClass().getName() +
                    " cannot have intValues");
        }
    }


    protected static class FixedIntegerValue implements IIntegerValue {
        private int m_value;

        public FixedIntegerValue(int in) {
            m_value = in;
        }

        public int intValue() {
            return (m_value);
        }

    }
}
