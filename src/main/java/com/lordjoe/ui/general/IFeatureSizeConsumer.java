package com.lordjoe.ui.general;

/**
 * com.lordjoe.ui.general.IFeatureSizeDefiner
 *   Interface allowing a component to define a useful scale for
 * subcomponents - this allows subcomponents to set a good prefreeed size
 * @author slewis
 * @date Mar 24, 2005
 */
public interface IFeatureSizeConsumer
{
    public static final Class THIS_CLASS = IFeatureSizeConsumer.class;
    public static final IFeatureSizeConsumer EMPTY_ARRAY[] = {};
    /**
     * set the number of grid divisions we prefer
     * in the X direction
     * @param XDivisions positive number of divisions
     */
    public void setXDivisions(int XDivisions);

    /**
     * set the number of grid divisions we prefer
     * in the Y direction
     * @param XDivisions positive number of divisions
     */
    public void setYDivisions(int YDivisions);

    /**
     * get the number of grid divisions we prefer
     * in the X direction
     * @return non-negative number
     */
    public int getXDivisions();

    /**
      * get the number of grid divisions we prefer
      * in the Y direction
      * @return non-negative number
      */
    public int getYDivisions();

 }