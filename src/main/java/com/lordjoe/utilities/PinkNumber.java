package com.lordjoe.utilities;
import java.io.*;

/**
* Pink Noise Generator
* com.lordjoe.Utilities.PinkNumber
* http://www.firstpr.com.au/dsp/pink-noise/#Pseudo
* adpoted by Steve Lewis
*/
public class PinkNumber 
{ 
    private int m_NumberKeys;
    private int max_key; 
    private int key; 
    private int[] white_values;
    private int range; 
    
    
    public PinkNumber() 
    { 
        this(128,5);
    }
    
    public PinkNumber(int arange) 
    { 
        this(arange,5);
    }
    
    
    public PinkNumber(int arange,int nkeys) 
    { 
        m_NumberKeys = nkeys;
        max_key = 0x1f; // Five bits set 
        range = arange; 
        key = 0; 
        white_values = new int[m_NumberKeys]; 
        for (int i = 0; i < m_NumberKeys; i++) {
            white_values[i] = (int)(Integer.MAX_VALUE * Math.random()) % (range/m_NumberKeys); 
        } 
    }
    
    
    public int getNextValue() 
    { 
        int last_key = key; 
        int sum; 

        key++; 
        if (key > max_key) 
            key = 0; 
        // Exclusive-Or previous value with current value. This gives 
        // a list of bits that have changed. 
        int diff = last_key ^ key; 
        sum = 0; 
        for (int i = 0; i < m_NumberKeys; i++) 
        { 
            // If bit changed get new random number for corresponding 
            // white_value 
            if ((diff & (1 << i)) != 0) 
            white_values[i] = (int)(Integer.MAX_VALUE * Math.random()) % (range/m_NumberKeys); 
            sum += white_values[i]; 
        } 
        return sum; 
    } 
    
    
    public static void main(String[] args) 
    { 
        PinkNumber pn = new PinkNumber(1024); 

        for (int i = 0; i < 100; i++) 
        { 
            System.out.println(pn.getNextValue()); 
        } 
    } 
}
