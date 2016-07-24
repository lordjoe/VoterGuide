package com.lordjoe.ui;

/**
 * com.lordjoe.ui.IArrayColorData
 * this is a general interface for representing the state of a
 * number of locations on a 2d surface - the most obvious
 * use is for draging chips
 * @author slewis
 * @date Apr 26, 2005
 */
public interface IArrayColorDataWithErrors extends IArrayColorData,IColorDataErrorSetter
{
    public static final Class THIS_CLASS = IArrayColorDataWithErrors.class;
    public static final IArrayColorDataWithErrors EMPTY_ARRAY[] = {};



}