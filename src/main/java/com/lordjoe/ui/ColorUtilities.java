package com.lordjoe.ui;

import com.lordjoe.lang.*;

import java.awt.*;
import java.util.*;

/**
 * com.lordjoe.ui.ColorUtilities
 *
 * @author Steve Lewis
 * @date Oct 17, 2005
 */
public class ColorUtilities
{
    public static final Random RND = new Random();

    public static final Color NORMAL_COLOR = new Color(0, 180, 0);
    public static final Color WARNING_COLOR = new Color(128, 128, 0);
    public static final Color SEVERE_WARNING_COLOR = Color.orange;
    public static final Color ERROR_COLOR = Color.red;
    public static final Color UNKNOWN_BACKGROUND_COLOR =  new Color(0, 128, 128);

    /**
     * Applications can use this color to indicate an unknown
     * value
     */
    public static final Color DEFAULT_UNKNOWN_COLOR = Color.CYAN;


    public static final int UNKNOWN_LEVEL = 256;
    private static Color[] gGreylevels;
    private static Color[] gRedBluelevels;
    static {
        gGreylevels = new Color[257];
          for (int i = 0; i < 256; i++) {
              gGreylevels[i] = new Color(i,i,i);
           }
           gGreylevels[256] = Color.cyan;
        gRedBluelevels = new Color[257];
          for (int i = 0; i < 256; i++) {
              gRedBluelevels[i] = new Color(255 - i,128,i);
           }
           gRedBluelevels[256] = Color.cyan;
     }

    public static Color operationStateToColor(OperationStateEnum state)
    {
        if(state == OperationStateEnum.NORMAL)
            return  NORMAL_COLOR;
        if(state == OperationStateEnum.WARNING)
            return  WARNING_COLOR;
        if(state == OperationStateEnum.SEVERE_WARNING)
            return  SEVERE_WARNING_COLOR;
        if(state == OperationStateEnum.ERROR)
            return  ERROR_COLOR;
        if(state == OperationStateEnum.UNKNOWN)
            return  DEFAULT_UNKNOWN_COLOR;
        throw new IllegalStateException("We cannot get here");
     }

    /**
     * return an array of colors representing grey levels
     * @return
     */
    public static Color[] getGreyLevels()
    {
        return gGreylevels;
    }

    public static ArrayColorData buildGreyLevels(double[] levels,double max,double min,int width)
    {
        int[] items = buildGreyLevels(levels,max,min);
        return new ArrayColorData(items,width,getGreyLevels());
    }
    public static ArrayColorData buildGreyLevels(double[] levels,int width)
    {
        int[] items = buildGreyLevels(levels);
        return new ArrayColorData(items,width,getGreyLevels());
    }

    public static int[] buildGreyLevels(double[] levels)
    {
        double max = 0;
        double min = 0;
        for (int i = 0; i < levels.length; i++) {
            max = Math.max(max,levels[i]);
            min = Math.min(min,levels[i]);
         }
        return buildGreyLevels(levels,max,min);
    }
    public static int[] buildGreyLevels(double[] levels,double max,double min)
    {
        double del = max - min;
        double inc = del / levels.length;
        int[] items = new int[levels.length];
        for (int i = 0; i < items.length; i++) {
           items[i] = (int)(256 * (levels[i] - min) /  del);
        }
        return items;
    }

    public static double[] randomGreyLevels(double max,double min)
    {
        double del = max - min;
         double[] ret = new double[224 * 65];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = del * RND.nextDouble() + min;

        }
        return ret;
    }

 


}
