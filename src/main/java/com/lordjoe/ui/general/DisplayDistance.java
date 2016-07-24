package com.lordjoe.ui.general;



/**
 * com.lordjoe.ui.general.DisplayDistance
 *
 * @author slewis
 * @date Mar 25, 2005
 */
public class DisplayDistance implements Comparable
{
    public static final Class THIS_CLASS = DisplayDistance.class;
    public static final DisplayDistance EMPTY_ARRAY[] = {};
    public static final double EPSILON = 0.001;
     public static final double BIG_DISTANCE = 100000000;

    public static DisplayDistance farAway(IDisplay target)
    {
        return new DisplayDistance(target,BIG_DISTANCE);
    }
    private final double m_Distance;
    private final IDisplay m_Target;

    public DisplayDistance(IDisplay target,double distance)
    {
        m_Distance = distance;
        m_Target = target;
    }

    public boolean isFarAway()
    {
        return getDistance() >= 100000000;
    }

    public double getDistance()
    {
        return m_Distance;
    }

    public IDisplay getTarget()
    {
        return m_Target;
    }

   public int compareTo(Object o)
    {
       DisplayDistance realO = (DisplayDistance)o;
        double d1 = getDistance();
        double d2 = realO.getDistance();
        if(Math.abs(d1 - d2) > EPSILON) {
            return  d1 > d2 ? 1 : -1;
        }
        Object s1 = getTarget().getSubjectObject();
        Object s2 = realO.getTarget().getSubjectObject();
        return s1.toString().compareTo(s2.toString());
    }

}
