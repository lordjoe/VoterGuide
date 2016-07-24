/**{ file
 @name ComponentTreeModel.java
 @function <Add Comment Here>
 @author> Steven M. Lewis
 @copyright>
  ************************
  *  Copyright (c) 1996,97
  *  Steven M. Lewis
 ************************

 @date> Wed May 28 17:44:32  1997
 @version> 1.0
 }*/
package com.lordjoe.utilities;

import javax.swing.event.*;
import javax.swing.tree.*;
import java.awt.*;
import java.util.concurrent.*;
import java.util.List;

public class ComponentTreeModel implements TreeModel
{
    private ComponentTreeModel m_Parent;
    private Component m_Base;
    private ComponentTreeModel[] m_Children;
    private int m_Index;
    private final List<TreeModelListener> m_TreeModelListeners;


    public ComponentTreeModel(Component Base)
    {
        m_Base = Base;
        m_TreeModelListeners = new CopyOnWriteArrayList<TreeModelListener>();
        if (m_Base.getParent() != null)
            m_Parent = new ComponentTreeModel(m_Base.getParent(), this);
        constructChildren();
        computeIndex();
    }

    protected ComponentTreeModel(Component Base, ComponentTreeModel ConstructingChild)
    {
        m_Base = Base;
        m_TreeModelListeners = new CopyOnWriteArrayList<TreeModelListener>();
        if (m_Base.getParent() != null)
            m_Parent = new ComponentTreeModel(m_Base.getParent(), this);
        // show only constructing child
        m_Children = new ComponentTreeModel[1];
        m_Children[0] = ConstructingChild;
    }

    protected ComponentTreeModel(Component Base, ComponentTreeModel ConstructingParent, int ignore)
    {
        m_Base = Base;
        m_Parent = ConstructingParent;
        m_TreeModelListeners = new CopyOnWriteArrayList<TreeModelListener>();
        constructChildren();
    }


    /**
     * add a change listener
     *
     * @param added non-null change listener
     */
    public void addTreeModelListener(TreeModelListener added)
    {
        if (!m_TreeModelListeners.contains(added))
            m_TreeModelListeners.add(added);
    }

    /**
     * remove a change listener
     *
     * @param removed non-null change listener
     */
    public void removeTreeModelListener(TreeModelListener removed)
    {
        while (m_TreeModelListeners.contains(removed))
            m_TreeModelListeners.remove(removed);
    }


    /**
     * notify any state change listeners - probably should
     * be protected but is in the interface to form an event cluster
     *
     * @param oldState
     * @param newState
     * @param commanded
     */
    public void notifyTreeModelListeners(Object changeTHis)
    {
        throw new UnsupportedOperationException("Fix This"); // ToDo
    }


    public String toString()
    {
        return ("Component of Class " + m_Base.getClass().getName());
    }

    protected int computeIndex()
    {
        ComponentTreeModel test = this;
        while (test.m_Parent != null)
            test = test.m_Parent;
        return (test.computeIndex(0));
    }

    protected int computeIndex(int start)
    {
        m_Index = start++;
        if (m_Children != null) {
            for (int i = 0; i < m_Children.length; i++)
                start = m_Children[i].computeIndex(start);
        }
        return (start);
    }


    protected void constructChildren()
    {
        if (m_Base instanceof Container) {
            Container realContainer = (Container) m_Base;
            if (realContainer.getComponentCount() > 0) {
                m_Children = new ComponentTreeModel[realContainer.getComponentCount()];
                for (int i = 0; i < m_Children.length; i++) {
                    m_Children[i] = new ComponentTreeModel(realContainer.getComponent(i), this, i);
                }
            }
        }
    }


    /**
     * Returns the root of the tree.  Returns null only if the tree has
     * no nodes.
     *
     * @return the root of the tree
     */
    public Object getRoot()
    {
        if (m_Base != null) {
            if (m_Parent != null)
                return (m_Parent.getRoot());
            return (this);
        }
        return (null); // empty
    }


    /**
     * Returns the child of <I>parent</I> at index <I>index</I> in the parent's
     * child array.  <I>parent</I> must be a node previously obtained from
     * this data source.
     *
     * @param parent a node in the tree, obtained from this data source
     * @return the child of <I>parent</I> at index <I>index</I>
     */
    public Object getChild(Object parent, int index)
    {
        if (parent instanceof ComponentTreeModel)
            return (((ComponentTreeModel) parent).m_Children[index]);
        return (null);
    }


    /**
     * Returns the number of children of <I>parent</I>.  Returns 0 if the node
     * is a leaf or if it has no children.  <I>parent</I> must be a node
     * previously obtained from this data source.
     *
     * @param parent a node in the tree, obtained from this data source
     * @return the number of children of the node <I>parent</I>
     */
    public int getChildCount(Object parent)
    {
        if (parent instanceof ComponentTreeModel)
            return (((ComponentTreeModel) parent).m_Children.length);
        return (0);
    }


    /**
     * Returns true if <I>node</I> is a leaf.  It is possible for this method
     * to return false even if <I>node</I> has no children.  A directory in a
     * filesystem, for example, may contain no files; the node representing
     * the directory is not a leaf, but it also has no children.
     *
     * @param node a node in the tree, obtained from this data source
     * @return true if <I>node</I> is a leaf
     */
    public boolean isLeaf(Object node)
    {
        if (node instanceof ComponentTreeModel)
            return (((ComponentTreeModel) node).m_Children == null);
        return (true);
    }

    /**
     * Messaged when the user has altered the value for the item identified
     * by <I>path</I> to <I>newValue</I>.  If <I>newValue</I> signifies
     * a truly new value the model should post a treeNodesChanged
     * event.
     *
     * @param path     path to the node that the user has altered.
     * @param newValue the new value from the TreeCellEditor.
     */
    public void valueForPathChanged(TreePath path, Object newValue)
    {
        // ignore this
    }


    /**
     * Returns the index of child in parent.
     */
    public int getIndexOfChild(Object parent, Object child)
    {
        if (m_Children == null)
            return (-1);
        for (int i = 0; i < m_Children.length; i++) {
            if (m_Children[i] == child)
                return (i);
        }
        return (-1); // fail
    }

//
//  Change Events
//

// end class ComponentTreeModel
}
