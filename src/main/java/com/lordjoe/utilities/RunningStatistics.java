package com.lordjoe.utilities;
import com.lordjoe.utilities.*;

import java.util.*;

/**
 * com.portalworks.cdo.common.RunningStatistics
 * @Author Steve Lewis smlewis@lordjoe.com
 */
public class RunningStatistics {
    public static final RunningStatistics[] EMPTY_ARRAY = {};
    public static final int CUMMULATIVE_TYPE = -1;
    public static final int CURRENT_DAY_TYPE = -2;
    public static final int CURRENT_HOUR_TYPE = -3;
     public static final int DAY_FACTOR = 100;

    public static int dayType(int dayOfWeek)
    {
        return(DAY_FACTOR * (1 +  dayOfWeek));
    }

    protected static RunningStatistics gInstance = new RunningStatistics();

    public static RunningStatistics getInstance() {
        return(gInstance);
    }

    public static CounterCollection[] update(CounterCollection[] in)
    {
        return(getInstance().doUpdate(in));
    }

    public static Map getStatistics(int type)
    {
         return(getInstance().doGetStatistics(type));
    }



    private Map m_Cummulative;
    private Map[] m_Daily;
    private Map[] m_Hourly;
    private int m_CurrentDay;
    private int m_CurrentHour;

    public  RunningStatistics()
    {
        init();
    }

    protected void init()
    {
         m_Cummulative = new HashMap();
         m_Daily = new  HashMap[7];
         for (int i = 0; i < m_Daily.length; i++) {
            m_Daily[i] = new HashMap();
         }
         m_Hourly = new HashMap[24];
        for (int i = 0; i < m_Hourly.length; i++) {
            m_Hourly[i] = new HashMap();
        }

        Calendar now = getNow();
        m_CurrentHour = now.get(Calendar.HOUR);
        m_CurrentDay = now.get(Calendar.DAY_OF_WEEK);
    }

    protected void clearStatistics(Map statistics)
    {
        Collection values = statistics.values();
        CounterCollection[] items = new CounterCollection[values.size()];
        values.toArray(items);
        for (int i = 0; i < items.length; i++) {
            CounterCollection item = items[i];
            item.clearMe();
        }
    }

    protected CounterCollection[] accumulateStatistics(CounterCollection[] items,Map statistics)
    {
        for (int i = 0; i < items.length; i++) {
            CounterCollection item = items[i];
            accumulateStatistic(item,statistics);
        }
        Collection values = statistics.values();
        CounterCollection[] ret = new CounterCollection[values.size()];
        values.toArray(ret);
        return(ret);
    }

    protected void accumulateStatistic(CounterCollection item,Map statistics)
    {
         CounterCollection olditem = (CounterCollection)statistics.get(item.getName());
        if(olditem != null)
             olditem.accumulate(item);
        else
              statistics.put(item.getName(),item);
    }

   /**
    * this is a function so I can change it for testing purposes
    */
    protected Calendar getNow()
    {
        Calendar now = new GregorianCalendar();
        return(now);
    }

   protected  CounterCollection[] doUpdate(CounterCollection[] in)
     {
        Calendar now = getNow();
        int currentHour = now.get(Calendar.HOUR);
        int currentDay = now.get(Calendar.DAY_OF_WEEK);

       if( currentDay == m_CurrentDay) {
            accumulateStatistics(in,m_Daily[m_CurrentDay]);
       }
       else   {
             m_CurrentDay = currentDay;
              clearStatistics(m_Daily[m_CurrentDay]);
              accumulateStatistics(in,m_Daily[m_CurrentDay]);
       }

       if( currentHour == m_CurrentHour) {
            accumulateStatistics(in,m_Hourly[m_CurrentHour]);
       }
       else   {
             m_CurrentHour = currentDay;
              clearStatistics(m_Hourly[m_CurrentHour]);
              accumulateStatistics(in,m_Hourly[m_CurrentHour]);
       }
       return(accumulateStatistics(in,m_Cummulative));
    }

    protected  Map doGetStatistics(int type)
     {
          if(type < 0)  {
              switch(type) {
                  case CUMMULATIVE_TYPE :
                     return(m_Cummulative);
                  case CURRENT_DAY_TYPE :
                      return(m_Daily[m_CurrentDay]);
                  case CURRENT_HOUR_TYPE :
                      return(m_Hourly[m_CurrentHour]);
                  default:
                    return(m_Cummulative);
              }
          }
          if(type < 24)
            return(m_Hourly[type]);
          type /= 100;
          type -= 1;
          if(type < 7)
            return(m_Daily[type]);
        throw new IllegalArgumentException("Type must be -1 for cummulative;0-23 for ourly 100-700 for daily");
     }

}
