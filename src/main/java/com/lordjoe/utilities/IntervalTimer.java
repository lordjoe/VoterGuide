/**{ file
    @name IntervalTimer.java
    @function this class - a class which marks time since its creation - the most obvious use is
         to track execution times
    @author> Steven M. Lewis
    @copyright> 
	************************
	*  Copyright (c) 1996,97,98
	*  Steven M. Lewis
	*  www.LordJoe.com
	************************

    @date> Mon Jun 22 21:48:27 PDT 1998
    @version> 1.0
}*/ 
package com.lordjoe.utilities;
import java.util.*;
/**{ class
    @name IntervalTimer
    @function this class - - a Nulliton implements a method to track time since the
         last call - obvious use is in timing execution
}*/ 
public class IntervalTimer {

    //- ******************* 
    //- Fields 
    /**{ field
        @name m_MarkTime
        @function <Add Comment Here>
    }*/ 
    private double m_MarkTime;


    //- ******************* 
    //- Methods 
    /**{ constructor
        @name IntervalTimer
        @function Constructor of IntervalTimer
    }*/ 
    public IntervalTimer() {
        m_MarkTime = Util.currentTime() / 1000.0;
}

    /**{ method
        @name timeSinceMark
        @function return time in sec since the last time this function
             was called - used for timing operations
        @return the time
        @policy <Add Comment Here>
    }*/ 
    public synchronized double timeSinceMark() {
        double oldMark = m_MarkTime;
        m_MarkTime = Util.currentTime() / 1000.0;
        double ret = m_MarkTime - oldMark;
        return(ret);
    }

    /**{ method
        @name getTime
        @function return time in sec since the timer was reset either by
             creation or by calling timeSinceMark
        @return the time
        @policy <Add Comment Here>
    }*/ 
    public synchronized double getTime() {
        double newMark = Util.currentTime() / 1000.0;
        double ret = newMark - m_MarkTime;
        return(ret);
    }


//- ******************* 
//- End Class IntervalTimer
}
