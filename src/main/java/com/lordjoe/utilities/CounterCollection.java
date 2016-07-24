package com.lordjoe.utilities;

import java.io.Serializable;
import java.util.*;

/**
 * com.lordjoe.Utilities.CounterCollection
 * @Author Steve Lewis smlewis@lordjoe.com
 */
public class CounterCollection  implements INameable,Serializable {
    public static final CounterCollection[] EMPTY_ARRAY = {};

    private String m_Name;


    private Date m_Start;
    private CountHolder[] m_items;

    public CounterCollection(String name) {
        setName(name);
        setStart(new Date());
        m_items = CountHolder.EMPTY_ARRAY;
    }

    public Date getStart() {
        return m_Start;
    }

    public void setStart(Date start) {
        m_Start = start;
    }

    public String getName() {
        return m_Name;
    }

    public void setName(String name) {
        m_Name = name;
    }

    public CountHolder[] getItems() {
        return m_items;
    }

    public CountHolder[] getAllItems() {
         return(getItems() );
     }


    public synchronized CountHolder createActivity(String name)
    {
        if(findActivity(name) != null)
            throw new IllegalArgumentException("activity '" + name + "' already exists");
        CountHolder ret = new CountHolder(name);
        m_items = (CountHolder[])Util.addToArray(m_items,ret);
        return(ret);
    }

    public synchronized CountHolder findActivity(String name)
      {
         for (int i = 0; i < m_items.length; i++) {
             CountHolder test = m_items[i];
             if(name.equals(test.getName()))
                 return(test);
         }
         return(null);
     }

    protected synchronized void addCounts(CounterCollection in)
    {
        in.m_items = new  CountHolder[m_items.length];
        for (int i = 0; i < m_items.length; i++) {
            in.m_items[i] = m_items[i].cloneMe();

        }
    }

    public CounterCollection extractActivity()
    {
        CounterCollection ret = cloneMe();
        clearMe();
        return(ret);
    }

    public synchronized CounterCollection cloneMe()
    {
        CounterCollection ret = (CounterCollection)buildClone(getName());
        populateClone(ret);
        return(ret);
    }

    protected synchronized void populateClone(CounterCollection in)
     {
        in.setStart(getStart());
        addCounts(in);
     }

    protected Object buildClone(String name)
    {
        return(new CounterCollection(name));
    }


    protected synchronized void extractCounts(CounterCollection in)
    {
        in.m_items = new  CountHolder[m_items.length];
        for (int i = 0; i < m_items.length; i++) {
            in.m_items[i] = m_items[i].cloneMe();
             m_items[i].clearCount();
        }
    }

    public void accumulate(CounterCollection in)
    {
        String[] added = in.getActivityNames();
        for (int i = 0; i < added.length; i++) {
            String s = added[i];
             accumulateActivity(in,s);
        }
        if(in.getStart().getTime() < getStart().getTime())
            setStart(in.getStart());

    }

    protected void accumulateActivity( CounterCollection in,String name)
    {
        CountHolder activity = in.findActivity(name);
        if(activity != null) {
            addToActivityCreatingIfNeeded(activity);
        }
        else {
            activity = in.findActivity(name); // huh
        }
    }

    public void addToActivityCreatingIfNeeded(CountHolder added)
     {
         String name = added.getName();
         CountHolder TheCounter = findActivity(name);
         if( TheCounter == null) {
             TheCounter = createActivity(name);
         }
         TheCounter.combine(added);
     }

    public void addToActivityCreatingIfNeeded(String name, int added)
    {
        CountHolder TheCounter = findActivity(name);
        if( TheCounter == null) {
            TheCounter = createActivity(name);
        }
        TheCounter.addCount(added);
    }

    public void addToActivity(String name, int added)
    {
        CountHolder TheCounter = findActivity(name);
        if( TheCounter == null) {
            String msg = buildActivitiesMessage( getActivityNames());
            throw new IllegalArgumentException("Activity '" + name +
                       "' does not exist\n The following do: " + msg);
        }
        TheCounter.addCount(added);
    }

    protected static String buildActivitiesMessage(String[] names)
    {
        Util.sort(names);
        String ret = Util.buildListString(names,0);
        return(ret);
    }
    public synchronized void clearMe()
    {
        for (int i = 0; i < m_items.length; i++) {
               m_items[i].clearCount();
         }
        setStart(new Date());
    }

   public String[] getActivityNames()
    {
        List holder = new ArrayList();
        accumulateActivityNames(holder);
       String[] ret = new String[holder.size()];
        holder.toArray(ret);
        return(ret);
   }

   protected void accumulateActivityNames(Collection holder)
   {
         for (int i = 0; i < m_items.length; i++) {
             holder.add(m_items[i].getName());
         }
    }
}
