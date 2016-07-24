package com.lordjoe.ui;

import java.awt.*;

/**
 * com.lordjoe.ui.ConservativeGridBagLayout
 *
 * @author Steve Lewis
 * @date Dec 14, 2005
 */
public class ConservativeGridBagLayout extends ConservativeLayoutManager
{

     public ConservativeGridBagLayout()
     {
         super(new GridBagLayout());
     }


  
    /**
     * Calculates the preferred size dimensions for the specified
     * container, given the components it contains.
     *
     * @param parent the container to be laid out
     * @see #minimumLayoutSize
     */
    public Dimension preferredLayoutSize(Container parent)
    {
        return super.preferredLayoutSize(parent);

    }

    /**
     * Calculates the minimum size dimensions for the specified
     * container, given the components it contains.
     *
     * @param parent the component to be laid out
     * @see #preferredLayoutSize
     */
    public Dimension minimumLayoutSize(Container parent)
    {
        return super.minimumLayoutSize(parent);

    }


}
