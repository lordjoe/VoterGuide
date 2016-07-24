package com.lordjoe.utilities;

import javax.swing.*;
import java.beans.PropertyChangeListener;

/**
 * com.lordjoe.utilities.ITaskProgress
 *
 * @author slewis
 * @date May 3, 2005
 */
public interface ITaskProgress extends INameable
{
    public static final Class THIS_CLASS = ITaskProgress.class;
    public static final ITaskProgress EMPTY_ARRAY[] = {};


    /**
       * return progress as a model
       * @return
       */
      public BoundedRangeModel getModel();

    /**{ method
         @name start
         @function return the start value of the task - usually 0
         @return the value
         @policy call super last
         @primary
     }*/
    int getStart();

    /**{ method
        @name end
        @function return the value at whoich the task will be done
        @return the end
        @policy rarely override
        @primary
    }*/
    int getEnd();

    /**{ method
        @name value
        @function - return the current value
        @return the value
        @policy rarely override
        @primary
    }*/
    int getValue();

    /**{ method
        @name fraction
        @function return the fraction of the task completed
        @return - the fraction completed 0-1.0
        @policy rarely override
        @primary
    }*/
    double getFraction();

    /**{ method
        @name getUrl
        @function get the task name
        @return the name
        @policy <Add Comment Here>
        @primary
    }*/
    String getName();

    /**{ method
        @name done
        @function call when the task is completed - put in a finally
             block so this is always called
        @policy rarely override
        @primary
    }*/
    void done();

    void addPropertyChangeListener(PropertyChangeListener l);

    void removePropertyChangeListener(PropertyChangeListener l);
}