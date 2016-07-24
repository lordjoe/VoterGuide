package com.lordjoe.propertyeditor;

import com.lordjoe.exceptions.*;

import java.lang.reflect.*;

/**
 * com.lordjoe.propertyeditor.ReflectiveOptionsGenerator
 *
 * @author Steve Lewis
 * @date Jan 1, 2008
 */
public class ReflectiveOptionsGenerator<T extends Object,V>  implements IOptionsGenerator<T,V>
{
    public static ReflectiveOptionsGenerator[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ReflectiveOptionsGenerator.class;

    private final Method m_Method;
    private final Class m_OutputClass;
    private final Class m_InputClass;

    public ReflectiveOptionsGenerator(Method pMethod) {
        m_Method = pMethod;
        Class ret = m_Method.getReturnType();
        m_OutputClass = ret.getComponentType();
        m_InputClass = m_Method.getDeclaringClass();
    }

    public Method getMethod() {
        return m_Method;
    }

    public Class getOutputClass() {
        return m_OutputClass;
    }

    public Class getInputClass() {
        return m_InputClass;
    }

    public T[] generateOptions(V input) {

        try {
            Object[] ret = (Object[])m_Method.invoke(input);
            if(ret == null)
                return null;
            if(m_OutputClass.isAssignableFrom(ret.getClass().getComponentType()))
                return (T[])ret;
            throw new IllegalStateException("Eventually rebuild the array"); // ToDo change
        } catch (IllegalAccessException e) {
            throw new WrappingException(e);
        } catch (InvocationTargetException e) {
            throw new WrappingException(e);
        }
    }
}
