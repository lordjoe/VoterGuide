package com.lordjoe.utilities;
import java.io.*;

/**
* Pink Noise Generator
* com.lordjoe.Utilities.PinkNumber
* http://www.firstpr.com.au/dsp/pink-noise/#Pseudo
* adpoted by Steve Lewis
*/
public class PinkDouble 
{ 
    public static final int INTEGER_RANGE = 1024;
    private double m_StartValue;
    private PinkNumber m_Generator;
    private double m_Power;
    
    public PinkDouble(double StartValue)
    {
        this(StartValue,10);
    }
    
    
    public PinkDouble(double StartValue,int Smoothness)
    {
         this(StartValue,Smoothness,2.0);
   }
    
    public PinkDouble(double StartValue,int Smoothness,double power)
    {
        power = Math.max(0.5,power);
        m_Power = Math.min(5,power);
        
        Smoothness = Math.max(5,Smoothness);
        Smoothness = Math.min(50,Smoothness);
        
        m_StartValue = StartValue;
        m_Generator = new PinkNumber(INTEGER_RANGE,Smoothness);
    }
    
    public double getNextValue() 
    { 
        int actor = m_Generator.getNextValue();
        // this is 0 .. 2 * start
        double basic = (actor * 2.0) / INTEGER_RANGE;
        double ret = m_StartValue * Math.pow(basic,m_Power);
        return(ret);
    } 
    
    
    public static void main(String[] args) 
    { 
        PinkDouble pn = new PinkDouble(100.0); 

        for (int i = 0; i < 100; i++) 
        { 
            System.out.println((int)pn.getNextValue()); 
        } 
    } 
    
}
