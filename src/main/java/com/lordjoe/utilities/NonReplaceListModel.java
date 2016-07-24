/**
 * NonReplaceListModel - Swing Style
 */
package com.lordjoe.utilities;
import javax.swing.*;
import javax.swing.event.*;

/**
 * com.lordjoe.NonReplaceListModel
 *   like a default list model but duplicates are not allowed
 * @author Steve Lewis
 * @date Dec 20, 2007
 */

public class NonReplaceListModel extends DefaultListModel
{
    
    public NonReplaceListModel() {
    }
    
    public NonReplaceListModel(Object[] elements) {
        this();
        for(int i = 0; i < elements.length; i++) {
            addElement(elements[i]);
        }
    }


    public void setElementAt(Object obj, int index) {
        if(this.contains(obj))
            return;
        super.setElementAt(obj, index);

    }

    public void insertElementAt(Object obj, int index) {
        if(this.contains(obj))
            return;
        super.insertElementAt(obj, index);

    }
    
    public void setElements(Object[] elements) {
        removeAllElements();
        for(int i = 0; i < elements.length; i++) {
            addElement(elements[i]);
        }
    }
    
    
  
  
  public void addElement(Object added) {
    if(contains(added))
        return;
    super.addElement(added);
  }
 
// end class NonReplaceList   
}



