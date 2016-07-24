
/**
*  <Add Comment Here>
* @author> Steven M. Lewis
* <>copyright>
  ************************
  *  Copyright (c) 1996,97,98
  *  Steven M. Lewis
  *  www.LordJoe.com
  ************************

* <>date> Thu Aug 13 15:56:51 PDT 1998
* @version> 1.0
*/
package com.lordjoe.utilities;


import javax.swing.*;
import javax.swing.event.*;

/**
*  Make list model turns an array into a read only list model - no updates but a
* good way to display array data in a list
* only useful method is ListModel makeListModel(Object in)
*/
public abstract class ArrayListModel implements ListModel {
    
    public static ListModel makeListModel(Object in) {
        if(in == null || !in.getClass().isArray())
            throw new IllegalArgumentException();
//        if(in instanceof Object[]) {
//            return(new ObjectArrayListModel((Object[])in));
//        }
//        if(in instanceof int[]) {
//            return(new IntArrayListModel((int[])in));
//        }
//        if(in instanceof double[]) {
//            return(new DoubleArrayListModel((double[])in));
//        }
//        // other way to test - this shows how to get the  class of an array
//        if(in instanceof boolean[]) {
//            return(new BooleanArrayListModel((boolean[])in));
//        }
//        if(in instanceof short[]) {
//            return(new ShortArrayListModel((short[])in));
//        }
//        if(in instanceof float[]) {
//            return(new FloatArrayListModel((float[])in));
//        }
//        if(in instanceof byte[]) {
//            return(new ByteArrayListModel((byte[])in));
//        }
//        if(in instanceof char[]) {
//            return(new CharArrayListModel((char[])in));
//        }
        throw new IllegalArgumentException();
    }
    
    protected static final String[] padding = { " ","  ","   ","    ","     ","      ","       ",
        "        ","         ","          ","            " } ;
    //
    // Formal index in an set of langel length - guaranteeing that all Strings 
    // have the same, maximal length
    protected String formatIndex(int index,int length) {
        int FormatLength = 1;
        while(length > 9) {
            length /= 10;
            FormatLength++;
        }
        String s2 = Integer.toString(index);
        int l2 = s2.length();
        int pad = FormatLength - l2;
        if(pad <= 0) 
            return(s2 + " ");
        else
            return(padding[pad] + s2 + " ");
        
    }
    
    // Override these
    public int getSize()                   {  throw new IllegalArgumentException(); }
    public Object getElementAt(int index)  {  throw new IllegalArgumentException(); }
    
    public void addListDataListener(ListDataListener l) {
    }
    
    public void removeListDataListener(ListDataListener l) {
    }


//- *******************
//- End Class ArrayListModel
}

class IntArrayListModel extends ArrayListModel {
    int[] m_data; 
    IntArrayListModel(int[] in) { m_data = in; }
    public int getSize() { return(m_data.length); }
    public Object getElementAt(int index) { 
        return("" + formatIndex(index,m_data.length)  +" " + m_data[index]); }
    
}


class ShortArrayListModel extends ArrayListModel {
    short[] m_data; 
    ShortArrayListModel(short[] in) { m_data = in; }
    public int getSize() { return(m_data.length); }
    public Object getElementAt(int index) { 
        return("" + formatIndex(index,m_data.length)  +" " + m_data[index]); }
    
}

