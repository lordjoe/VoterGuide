package com.lordjoe.ui.propertyeditor;

/**
 * com.lordjoe.ui.propertyeditor.ReflectionPropertyCreator
 *
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public class ReflectionPropertyCreator<T> implements IObjectCreatorDialog<T>
{
    public static ReflectionPropertyCreator[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ReflectionPropertyCreator.class;

    
    private final ReflectionPropertyEditorPanel<T>  m_Panel;
    public ReflectionPropertyCreator(T[] examplar,String[] properties)
    {
        T newObject = PropertyEditorUtilities.createNewInstance(examplar);
        m_Panel = new ReflectionPropertyEditorPanel(newObject,properties);

    }
    public ReflectionPropertyCreator(T[] examplar,String[] properties,Object... args)
    {
         T newObject = PropertyEditorUtilities.createNewInstance(examplar,args);
         m_Panel = new ReflectionPropertyEditorPanel(newObject,properties);

    }

    /**
     * set the style of the panel
     * @param style
     */
    public void applyStyle(IStylizer style)
    {
         style.stylize(m_Panel);
    }

    /**
     * offer the user a modal dialog to create s new object
     *
     * @return possibly null created object -
     */
    public T createObject()
    {
        return m_Panel.doEditTarget();
    }
}
