/**{ file
    @name SwingHelpers.java
    @function Utility functions for use with swing
    @author> Steven Lewis
    @copyright>
	************************
	*  Copyright (c) 1996,97,98
	*  Steven M. Lewis
	*  www.LordJoe.com
	************************
    @date> Mon Jun 22 21:48:24 PDT 1998
    @version> 1.0
}*/
package com.lordjoe.utilities;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

/**{ class
    @name SwingHelpers
    @function helper functions for swing
}*/
public abstract class SwingHelpers extends Nulleton {

    //
    // Expands all nodes in a JTree
    public static void setFullyExpanded(JTree Target) {
        TreeModel TheModel = Target.getModel();
        Object Root = TheModel.getRoot();
        Vector Path = new Vector();
        Path.addElement(Root);
        setFullyExpandedPath(Target,TheModel,Root,Path);
    }
    protected static void setFullyExpandedPath(JTree Target,TreeModel TheModel,Object Node,Vector Path) {
        if(TheModel.isLeaf(Node) || TheModel.getChildCount(Node) == 0 ) {
            Object[] FullPath = new Object[Path.size()];
            Path.copyInto(FullPath);
            Target.expandPath(new TreePath(FullPath));
            return;
        }
        for(int i = 0; i < TheModel.getChildCount(Node); i++) {
            Object Child = TheModel.getChild(Node,i);
            Path.addElement(Child);
            setFullyExpandedPath(Target,TheModel,Child,Path);
            Path.removeElement(Child);
        }
    }


        public static final int COMPONENT_TREE_WIDTH = 300;
        public static final int COMPONENT_TREE_HEIGHT = 400;
        
        public static JFrame makeComponentTree(Component c) {
            JFrame TheFrame = new JFrame("Test Tree");
            JTree TheTree = new JTree(new ComponentTreeModel(c));
            SwingHelpers.setFullyExpanded(TheTree);
            JScrollPane TheScroll = new JScrollPane(TheTree);
            TheFrame.getContentPane().add(TheScroll);
            TheFrame.setSize(COMPONENT_TREE_WIDTH,COMPONENT_TREE_HEIGHT);
            TheFrame.setVisible(true);
            return(TheFrame);
            
        }
        
    /**
        return the size of a tree model
        @param in - the tree model
        @return - the size >= 0
    */
    public static int getTreeModelSize(TreeModel in) {
        Object root = in.getRoot();
        if(root == null) return(0);
        return(getTreeModelSize(in,root));
    }
    
    /**
        return the size of a tree starting at a specific root
        @param in - the tree model
        @parem root - starting node - non-null
        @return - the size >= 1
    */
    protected static int getTreeModelSize(TreeModel in,Object root) {
        int ret = 1; // add one for root
        for(int i = 0; i < in.getChildCount(root); i++)
            ret += getTreeModelSize(in,in.getChild(root,i));
        return(ret); 
    }
        
    /**
        return the size of a tree starting at a specific root
        @param in - the tree model
        @return - the indents 0 = root
    */
    public static int[] getTreeIndents(TreeModel in) 
    {
        int[] ret = new int[getTreeModelSize(in)];
        setTreeIndents(in,in.getRoot(),ret,0,0);
        return(ret); 
    }
 
    /**
        return array of indents for a tree 
        @param in - the tree model
        @parem root - starting node - non-null
        @parem items - array of indents
        @parem level - current indent level 0 = root
        @parem index - index on entry
        @return - index of first unused access
    */
    protected static int setTreeIndents(TreeModel in,Object root,int[] items,int level,int index) 
    {
        items[index++] = level;
        for(int i = 0; i < in.getChildCount(root); i++)
            index = setTreeIndents(in,in.getChild(root,i),items,level + 1,index);
        return(index); 
    }
 
    /**
        return the objects in a tree modes as a simple array
        @param in - the tree model
        @return - the Objects
    */
    public static Object[] getTreeObjects(TreeModel in) 
    {
        Object[] ret = new Object[getTreeModelSize(in)];
        setTreeObjects(in,in.getRoot(),ret,0);
        return(ret); 
    }
 
    /**
        return the objects in a tree modes as a simple array
        @param in - the tree model
        @parem root - starting node - non-null
        @parem items - array of Objects
        @parem index - index on entry
        @return - index of first unused access
    */
    protected static int setTreeObjects(TreeModel in,Object root,Object[] items,int index) 
    {
        items[index++] = root;
        for(int i = 0; i < in.getChildCount(root); i++)
            index = setTreeObjects(in,in.getChild(root,i),items,index);
        return(index); 
    }
    /**
        return an array of TreePaths for the entire tree
        @param in - the tree model
        @return - the paths
    */
    public static TreePath[] getTreePaths(TreeModel in) 
    {
        TreePath[] ret = new TreePath[getTreeModelSize(in)];
        setTreePaths(in,in.getRoot(),ret,Util.EMPTY_OBJECT_ARRAY,0);
        return(ret); 
    }
 
    /**
        Fill in an array of TreePaths for the entire tree
        @param in - the tree model
        @parem root - starting node - non-null
        @parem items - array of Objects
        @parem index - index on entry
        @return - index of first unused access
    */
    protected static int setTreePaths(TreeModel in,Object root,TreePath[] items,Object[] path, int index) 
    {
        // build a new path
        Object[] newPath = new Object[path.length + 1];
        System.arraycopy(path,0,newPath,0,path.length);
        newPath[path.length] = root;
        
        items[index++] = new TreePath(newPath);
        
        for(int i = 0; i < in.getChildCount(root); i++) {
            index = setTreePaths(in,in.getChild(root,i),items,newPath,index);
        }
        return(index); 
    }
       
       
// end class SwingUtilities
}
