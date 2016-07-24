package com.lordjoe.utilities;

/**
 * com.lordjoe.Utilities.MaxHolder
 * @Author Steve Lewis smlewis@lordjoe.com
 */
public class MaxHolder extends CountHolder {
    public static final MaxHolder[] EMPTY_ARRAY = {};



    private int m_Max;

    public  MaxHolder(String name)
     {
         super(name);
     }

    public int getMax() {
        return m_Max;
    }

    public void setMax(int max) {
        m_Max = max;
    }

    public synchronized void addCount(int added) {
        super.addCount(added);
        if(getCount() > getMax())
            setMax(getCount());
    }

    public void combine(  CountHolder added)
    {
        super.combine(added);
        if(added instanceof MaxHolder) {
            int test =  ((MaxHolder)added).getMax();
            int test2 = getMax();
            setMax(Math.max(test,test2));
        }
    }


     public synchronized CountHolder cloneMe()
    {
        MaxHolder ret = new MaxHolder(getName());
        ret.combine(this);
        return(ret);
    }

    public String toString()
     {
         StringBuffer sb = new StringBuffer();
         sb.append(super.toString());
         sb.append(" Max ") ;
         sb.append(getMax());
         return(sb.toString());
     }

}
