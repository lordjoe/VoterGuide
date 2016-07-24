/**{ file
    @name FullColumnWidthLayout.java
    @function
         A layout manager for a container that lays out LabeledFields
         combonations of labels + fields


    @author>
    @copyright>
	************************
	*  Copyright (c) 1996,97
	*  Steven M. Lewis
	************************

    @date> Wed May 28 17:44:48  1997
    @version> 1.11, 14 Dec 1995
}*/
package com.lordjoe.smartgrid;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
/**{ class
    @name FullColumnWidthLayout
    @function
         A layout manager for a container that lays out grids.


    @author>
    @copyright>
         ************************
         Copyright (c) 1996,97
         Steven M. Lewis
         ************************

    @date> Mon Mar 10 22:31:03 1997
    @version> 1.11, 14 Dec 1995
}*/
public class FullColumnWidthLayout implements LayoutManager {

    //- *******************
    //- Methods
    /**{ constructor
        @name FullColumnWidthLayout
        @function Constructor of FullColumnWidthLayout
        @param cols the columns
        @param rows the rows
    }*/
    public FullColumnWidthLayout() {
    }

    /**{ method
        @name addLayoutComponent
        @function
             Adds the specified component with the specified name to the layout.
        @param comp the component to be added
        @param name the name of the component
        @policy <Add Comment Here>
        @primary
    }*/
    public void addLayoutComponent(String name,Component comp) {
    }

    /**{ method
        @name removeLayoutComponent
        @function
             Removes the specified component from the layout. Does not apply.
        @param comp the component to be removed
        @policy <Add Comment Here>
        @primary
    }*/
    public void removeLayoutComponent(Component comp) {
    }

    /**{ method
        @name preferredLayoutSize
        @function
             Returns the preferred dimensions for this layout given the components
             int the specified panel.
        @param parent the component which needs to be laid out
        @return <Add Comment Here>
        @policy <Add Comment Here>
        @primary
        @see #minimumLayoutSize
    }*/
    public Dimension preferredLayoutSize(Container parent) {
        JTable realParent = (JTable)parent;
        Insets insets = realParent.getInsets();
         int NColumns = realParent.getColumnCount();
         TableColumnModel cm = realParent.getColumnModel();
         Dimension ret = new Dimension(insets.left + insets.right,insets.top + insets.bottom);
         for(int i = 0; i < NColumns; i++) {
            TableColumn TheColumn = cm.getColumn(i);

         }
        return(ret);

    }

    /**{ method
        @name minimumLayoutSize
        @function
             Returns the minimum dimensions needed to layout the components
             contained in the specified panel.
        @param parent the component which needs to be laid out
        @return <Add Comment Here>
        @policy <Add Comment Here>
        @primary
        @see #preferredLayoutSize
    }*/
    public Dimension minimumLayoutSize(Container parent) {
        return(preferredLayoutSize(parent));
    }

    /**{ method
        @name layoutContainer
        @function
             Lays out the container in the specified panel.
        @param parent the specified component being laid out
        @policy <Add Comment Here>
        @primary
        @see Container
    }*/
    public void layoutContainer(Container parent) {
     
    }

    /**{ method
        @name toString
        @function
             Returns a String that represents the value of this Object. It is recommended
             that all subclasses override this method.
        @return <Add Comment Here>
        @overrideReason> <Add Comment Here>
        @policy <Add Comment Here>
        @primary
    }*/
    public String toString() {
        return getClass().getName() + "[ ]";
    }


//- *******************
//- End Class FullColumnWidthLayout
}
