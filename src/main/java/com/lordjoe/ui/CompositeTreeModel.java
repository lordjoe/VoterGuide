package com.lordjoe.ui;

import com.lordjoe.lang.*;

import javax.swing.tree.*;

/**
 * com.lordjoe.ui.CompositeTreeModel
 *
 * @author Steve Lewis
 * @date Nov 26, 2007
 */
public class CompositeTreeModel extends DefaultTreeModel
{
    public static CompositeTreeModel[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = CompositeTreeModel.class;



    /**
     * Creates a tree in which any node can have children.
     *
     * @param root a TreeNode object that is the root of the tree
     * @see #DefaultTreeModel(javax.swing.tree.TreeNode , boolean)
     */
    public CompositeTreeModel(IComposite cmp)
    {
        super(new CompositeTreeNode((cmp)));
    }

}
