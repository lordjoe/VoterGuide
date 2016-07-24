package com.lordjoe.ui;

import com.lordjoe.lang.*;
import com.lordjoe.propertyeditor.*;

import javax.swing.tree.*;

/**
 * com.lordjoe.ui.CompositeTreeNode
 *
 * @author Steve Lewis
 * @date Nov 26, 2007
 */
public class CompositeTreeNode extends DefaultMutableTreeNode implements CompositeChangeListener
{
    public static CompositeTreeNode[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = CompositeTreeNode.class;

    /**
     * Creates a tree node with no parent, no children, but which allows
     * children, and initializes it with the specified user object.
     *
     * @param userObject an Object provided by the user that constitutes
     *                   the node's data
     */
    public CompositeTreeNode(IComposite userObject)
    {
        super(userObject, true);
        userObject.addCompositeChangeListener(this);
        if(userObject.getClass().getAnnotation(TerminalNode.class) != null)
            return; 
        IComposite[] objectItems = userObject.getChildObjectItems();
        for (int i = 0; i < objectItems.length; i++) {
            IComposite child = objectItems[i];
            add(new CompositeTreeNode(child));
        }

    }

    /**
     * notification of a child being added
     *
     * @param parent non-null parent
     * @param child  non-null child
     */
    public void onAddChild(IComposite parent, IComposite child)
    {
        this.add(new CompositeTreeNode(child));

    }

    /**
     * notification of a child being removec
     *
     * @param parent non-null parent
     * @param child  non-null child
     */
    public void onRemoveChild(IComposite parent, IComposite child)
    {
        int nchildren = getChildCount();
        for (int i = 0; i < nchildren; i++) {
            CompositeTreeNode at = (CompositeTreeNode) getChildAt(i);
            if (at.getUserObject() == child) {
                remove(at);
                child.removeCompositeChangeListener(at);
                return;
            }
        }

    }

    /**
     * notification removal of all children
     *
     * @param parent non-null parent
     */
    public void onClearChildren(IComposite parent)
    {
        while (getChildCount() > 0) {
            CompositeTreeNode nod = (CompositeTreeNode)getChildAt(0);
            IComposite cm= (IComposite)nod.getUserObject();
            cm.removeCompositeChangeListener(nod);
            remove(0);
        }

    }

    /**
     * notification of bulk add
     *
     * @param parent non-null parent
     * @param child  non-null array of added children
     */
    public void onAddChildren(IComposite parent, IComposite[] child)
    {
        for (int i = 0; i < child.length; i++) {
            IComposite cld = child[i];
            onAddChild(parent, cld);
        }

    }
}
