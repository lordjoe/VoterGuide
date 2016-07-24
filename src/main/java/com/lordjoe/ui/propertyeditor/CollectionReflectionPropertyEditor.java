package com.lordjoe.ui.propertyeditor;

import com.lordjoe.propertyeditor.*;
import com.lordjoe.utilities.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;


/**
 * com.lordjoe.ui.propertyeditor.CollectionReflectionPropertyEditor
 *
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public class CollectionReflectionPropertyEditor<T> extends AbstractCollectionPropertyEditor<T>
    implements CollectionChangeListener
{

    private ICollectionPropertyPanel m_Panel;

    public CollectionReflectionPropertyEditor(ICollectionWrapper<T> pWrapper)
    {
        super(pWrapper);
        Object owner = ((ReflectionCollectionWrapper) pWrapper).getOwner();
        m_Panel = buildPanel(pWrapper, owner);
        if(owner instanceof ICollectionHolder) {
            ((ICollectionHolder)owner).addCollectionChangeListener(this);
        }
    }

    protected ICollectionPropertyPanel<T> buildPanel(ICollectionWrapper<T> pWrapper, Object pOwner) {
        return PropertyEditorUtilities.buildCollectionPropertyPanel(pOwner, pWrapper);
    }

    /**
     * handle member added to a collection
     *
     * @param collection possibly null collection name
     * @param added      non-null added member
     */
    public void onMemberAdded(String collection, Object added)
    {
        if (isMyCollection(collection)) {
            m_Panel.addItem(added);
        }

    }

    /**
     * handle member removed from a collection
     *
     * @param collection possibly null collection name
     * @param added      non-null added member
     */
    public void onMemberRemoved(String collection, Object removed)
    {
        if (isMyCollection(collection)) {
            m_Panel.removeItem(removed);
         }

    }

    /**
     * handle all members removed
     *
     * @param collection possibly null collection name
     */
    public void onCollectionCleared(String collection)
    {
        if (isMyCollection(collection)) {
            m_Panel.clearItems();
         }

    }

// Add to constructor


    public JComponent[] getStylizableComponents()
    {
        List<JComponent> holder = new ArrayList<JComponent>();
        holder.add(m_Panel.getStylizableSelf());
        JComponent[] ret = new JComponent[holder.size()];
        holder.toArray(ret);
        return ret;
    }

    /**
     * return self as Jcomponent to a stylizer
     *
     * @return
     */
    public JComponent getStylizableSelf()
    {
        return null; // all parts in sub panel
    }


    public JComponent getMainDisplay()
    {
        return m_Panel.getStylizableSelf();
    }

    /**
     * do something to rebuild the panel
     */
    public void rebuildPanel()
    {
    }

    /**
     * this neeed not Be a component but is better manage one
     *
     * @return
     */
    public JComponent getComponent()
    {
        return m_Panel.getStylizableSelf();
    }


    /**
     * set the value of the source to that represented by the editor
     */
    public void commit()
    {
        Object[] items = m_Panel.getCurrentItems();
        setCollectionContents(items);
    }

}