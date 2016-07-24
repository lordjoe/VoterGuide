package com.lordjoe.ui.general;

import java.awt.*;

/**
 * com.lordjoe.ui.general.IFeatureSizeDefiner
 *   Interface allowing a component to define a useful scale for
 * subcomponents - this allows subcomponents to set a good prefreeed size
 * @author slewis
 * @date Mar 24, 2005
 */
public interface IFeatureSizeDefiner
{
    public static final Class THIS_CLASS = IFeatureSizeDefiner.class;
    public static final IFeatureSizeDefiner EMPTY_ARRAY[] = {};

    public static final int DEFAULT_GRID_DIVISIONS = 50;

    /**
     * return the size of a "typical feature" allowing
     * subcomponents to scale
     * @return non-null dimension
     */
    public Dimension getFeatureSize();
}