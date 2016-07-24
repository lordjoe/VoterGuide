package com.lordjoe.ui.general;

import com.lordjoe.ui.*;

import javax.swing.*;


/**
 * com.lordjoe.ui.general.JLabeledCheckBoxFeature
 *
 * @author slewis
 * @date Apr 7, 2005
 */
public class JLabeledCheckBoxFeature extends JLabeledCheckBox implements IFeatureSizeConsumer
{
    public static final Class THIS_CLASS = JLabeledCheckBoxFeature.class;
    public static final JLabeledCheckBoxFeature EMPTY_ARRAY[] = {};

     public JLabeledCheckBoxFeature(String label)
    {
        super(label);
    }

    protected JLabel buildLabel(String s)
    {
       return new JSizeableLabel(s);
    }


    public void setBounds(int x, int y, int width, int height)
     {
         super.setBounds(x, y, width, height);
     }

    public JSizeableLabel getSizeableLabel()
    {
        return (JSizeableLabel)getLabel();
    }

    /**
     * set the number of grid divisions we prefer
     * in the X direction
     * @param XDivisions positive number of divisions
     */
    public void setXDivisions(int XDivisions)
    {
        getSizeableLabel().setXDivisions(XDivisions);
      }

    /**
     * set the number of grid divisions we prefer
     * in the Y direction
     * @param XDivisions positive number of divisions
     */
    public void setYDivisions(int YDivisions)
    {
        getSizeableLabel().setYDivisions(YDivisions);
    }

    /**
     * get the number of grid divisions we prefer
     * in the X direction
     * @return non-negative number
     */
    public int getXDivisions()
    {
       return  getSizeableLabel().getXDivisions();
     }

    /**
      * get the number of grid divisions we prefer
      * in the Y direction
      * @return non-negative number
      */
    public int getYDivisions()
    {
        return  getSizeableLabel().getYDivisions();
       }




}
