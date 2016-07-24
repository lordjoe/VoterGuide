package com.lordjoe.ui.general;

import java.awt.*;
import java.util.*;
import javax.swing.*;


/**
 * com.lordjoe.ui.general.JSingleLineComboBox
 * This version of a JComboBox will not grow vertically
 * Developed to aid in layout
 * @date Mar 1, 2005
 */
public class JSingleLineComboBox extends JComboBox
{
    public static final Class THIS_CLASS = JSingleLineComboBox.class;
    public static final JSingleLineComboBox[] EMPTY_ARRAY = {};

    /**
      * Creates a <code>JComboBox</code> that takes it's items from an
      * existing <code>ComboBoxModel</code>.  Since the
      * <code>ComboBoxModel</code> is provided, a combo box created using
      * this constructor does not create a default combo box model and
      * may impact how the insert, remove and add methods behave.
      *
      * @param aModel the <code>ComboBoxModel</code> that provides the
      * 		displayed list of items
      * @see DefaultComboBoxModel
      */
     public JSingleLineComboBox(ComboBoxModel aModel) {
          super(aModel);
      }

     /**
      * Creates a <code>JComboBox</code> that contains the elements
      * in the specified array.  By default the first item in the array
      * (and therefore the data model) becomes selected.
      *
      * @param items  an array of objects to insert into the combo box
      * @see DefaultComboBoxModel
      */
     public JSingleLineComboBox(final Object items[]) {
         super(items);
       }

     /**
      * Creates a <code>JComboBox</code> that contains the elements
      * in the specified Vector.  By default the first item in the vector
      * and therefore the data model) becomes selected.
      *
      * @param items  an array of vectors to insert into the combo box
      * @see DefaultComboBoxModel
      */
     public JSingleLineComboBox(Vector<?> items) {
         super(items);
      }

     /**
      * Creates a <code>JComboBox</code> with a default data model.
      * The default data model is an empty list of objects.
      * Use <code>addItem</code> to add items.  By default the first item
      * in the data model becomes selected.
      *
      * @see DefaultComboBoxModel
      */
     public JSingleLineComboBox() {
         super();
     }


    /**
      * This should stop vertical resizing
      */
     public Dimension getMaximumSize() {
        Dimension maxSize = super.getMaximumSize();
        Dimension prefSize = super.getPreferredSize();
        return new Dimension(maxSize.width,prefSize.height);
     }
    
}

