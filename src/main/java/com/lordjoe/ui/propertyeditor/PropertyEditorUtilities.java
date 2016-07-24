package com.lordjoe.ui.propertyeditor;

import com.lordjoe.exceptions.*;
import com.lordjoe.lang.*;
import com.lordjoe.propertyeditor.*;

import java.lang.reflect.*;
import java.net.*;


/**
 * com.lordjoe.ui.propertyeditor.PropertyEditorUtilities
 *
 * @author slewis
 * @date Apr 21, 2005
 */
public class PropertyEditorUtilities
{
    public static final Class THIS_CLASS = PropertyEditorUtilities.class;
    public static final PropertyEditorUtilities EMPTY_ARRAY[] = {};

    /**
     * return an ip address or null if
     * host is not an ip address
     * @param host
     * @return
     */
    public static InetAddress buildINetAddress(String host)
    {
        try {
            InetAddress[] ret = InetAddress.getAllByName( host);
            if(ret.length > 0)
                return ret[0];
        } catch (UnknownHostException e) {
            return null;
        }
        return null;
    }

   private static ICollectionPanelBuilder gCollectionPanelBuilder;

    public static ICollectionPanelBuilder getCollectionPanelBuilder() {
        return gCollectionPanelBuilder;
    }

    public static void setCollectionPanelBuilder(ICollectionPanelBuilder pCollectionPanelBuilder) {
        gCollectionPanelBuilder = pCollectionPanelBuilder;
    }

    public static <T> ICollectionPropertyPanel<T> buildCollectionPropertyPanel(Object owner,
                                         ICollectionWrapper<T> pWrapper)
    {
        return getCollectionPanelBuilder().buildCollectionPropertyPanel(owner,pWrapper);
    }

 //   public static T  ReflectionPropertyCreator(T[] examplar,Object... args)
    public static IPropertyEditor buildPropertyEditor(IEditableProperty prop)
    {
        Class targetClass = prop.getType();
        Object[] options = prop.getOptions();
        if (options != null)
        {
            return buildPropertyComboBox(prop,options);
        }
        if (Integer.class.isAssignableFrom(targetClass))
        {
            return buildIntegerEditor(prop);
        }
        if (Boolean.class.isAssignableFrom(targetClass))
        {
            return buildBooleanEditor(prop);
        }
         if (Long.class.isAssignableFrom(targetClass))
        {
            return buildLongEditor(prop);
        }
        if (Float.class.isAssignableFrom(targetClass))
        {
            return buildFloatEditor(prop);
        }
        if (Double.class.isAssignableFrom(targetClass))
        {
            return buildDoubleEditor(prop);
        }
        if (Position3d.class.isAssignableFrom(targetClass))
         {
             return buildPositionEditor(prop);
         }
        if (InetAddress.class.isAssignableFrom(targetClass))
         {
             return buildINetAddressEditor(prop);
         }
        if (String.class.isAssignableFrom(targetClass))
         {
             return buildStringEditor(prop);
         }
        if (String[].class.isAssignableFrom(targetClass))
         {
             return buildStringArrayEditor(prop);
         }
        throw new UnsupportedOperationException("Cannot build Property Editor for class " +
                targetClass.getName());
    }

    public static IPropertyEditor buildLayerEditor(IEditableProperty prop)
    {
        return new JLayerPropertyEditor(prop);
    }

    public static IPropertyEditor buildINetAddressEditor(IEditableProperty prop)
    {
        return new JNetAddressPropertyEditor(prop);
    }

    public static IPropertyEditor buildIntegerEditor(IEditableProperty prop)
    {
        return new JIntegerPropertyEditor(prop);
    }
    public static IPropertyEditor buildBooleanEditor(IEditableProperty prop)
    {
        return new JBooleanPropertyEditor(prop);
    }

    public static IPropertyEditor buildDoubleEditor(IEditableProperty prop)
    {
        return new JDoublePropertyEditor(prop);
    }

    public static IPropertyEditor buildFloatEditor(IEditableProperty prop)
    {
        return new JFloatPropertyEditor(prop);
    }

    public static IPropertyEditor buildLongEditor(IEditableProperty prop)
    {
        return new JLongPropertyEditor(prop);
    }

    public static IPropertyEditor buildStringEditor(IEditableProperty prop)
    {
        return new JStringPropertyEditor(prop);

    }

    public static IPropertyEditor buildStringArrayEditor(IEditableProperty prop)
    {
        return new JStringArrayPropertyEditor(prop);

    }

    public static IPropertyEditor buildPositionEditor(IEditableProperty prop)
    {
        return new JPosition3dEditor(prop);

    }

    public static IPropertyEditor buildPropertyComboBox(IEditableProperty prop,Object[] options)
    {
        Class target = prop.getType();
        if(options == null)
          options = prop.getOptions();
        if (options != null)
        {
            return new JComboBoxPropertyEditor(prop,options);
        }
        throw new UnsupportedOperationException("Fix This"); // ToDO Fix Thix
    }

    public static IEditableProperty buildProperty(String name,
             Class cls,Object defaultValue,Object[] options, IPropertySource ps)
      {
          ObjectPropertyImpl desc = new ObjectPropertyImpl(name,cls);
          desc.setDefaultValue(defaultValue);
          desc.setOptions(options);

          IEditableProperty prop = new EditableProperty(desc,ps);
          if(defaultValue != null)
              prop.setValue(defaultValue);
          return prop;
      }

     public static IEditableProperty buildProperty(String name,
             Class cls,Object defaultValue,Object[] options, IPropertySource ps,String units)
      {
          ObjectPropertyImpl desc = new ObjectPropertyImpl(name,cls);
          desc.setDefaultValue(defaultValue);
          desc.setOptions(options);
          desc.setUnits(units);

          IEditableProperty prop = new EditableProperty(desc,ps);
          if(defaultValue != null)
              prop.setValue(defaultValue);
          return prop;
      }

    public static  <T>   T createNewInstance(T[] examplar,Object ... args)
    {
        Class cls = examplar.getClass().getComponentType();
        try {
            if(args.length == 0)  {
               return (T)cls.newInstance();
            }
            else {
                return (T)constructNewInstance(cls,args);
            }
        } catch (InstantiationException e) {
            throw new WrappingException(e);
        } catch (IllegalAccessException e) {
            throw new WrappingException(e);
        } catch (InvocationTargetException e) {
            throw new WrappingException(e);
        }
    }

    public static Object constructNewInstance(Class cls,Object[] args) throws InstantiationException,
            IllegalAccessException,InvocationTargetException
    {
        Constructor[] constructors = cls.getConstructors();
        for (int i = 0; i < constructors.length; i++) {
            Constructor constructor = constructors[i];
            if(isCompatable(constructor,args)) {
                return constructor.newInstance(args);
            }
        }
        Object[] args2 = {};
        // try null constructoe
        for (int i = 0; i < constructors.length; i++) {
            Constructor constructor = constructors[i];
            if(isCompatable(constructor,args2)) {
                return constructor.newInstance(args2);
            }
        }

        // debug repeat
        for (int i = 0; i < constructors.length; i++) {
            Constructor constructor = constructors[i];
            if(isCompatable(constructor,args2)) {
                return constructor.newInstance(args2);
            }
        }

        throw new IllegalArgumentException("No suitable constructor");
    }

    /**
     * can the constructor be called with the passed arguments
     * @param constructor non-null constructor
     * @param args non-null arguments
     * @return   as above
     */
    public static boolean isCompatable(Constructor constructor,Object[] args)
    {
        Class[] classes = constructor.getParameterTypes();
        if(classes.length != args.length)
            return false;
        for (int i = 0; i < classes.length; i++) {
            Class aClass = classes[i];
            if(!aClass.isInstance(args[i]))
                return false;
        }
        return true;
    }


}
