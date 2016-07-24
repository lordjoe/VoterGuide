/**{ file
    @name TaskProgress.java
    @function TaskProgress shows the user how a task is progressing
    @author> Steven Lewis
    @copyright> 
	************************
	*  Copyright (c) 1996,97
	*  Steven M. Lewis
	************************

    @date> Fri May 30 11:05:42  1997
    @version> 1.0
}*/ 
package com.lordjoe.utilities;
import java.beans.*;
import java.util.*;
import javax.swing.*;
/**{ class
    @name TaskProgress
    @function TaskProgress shows the user how a task is progressing
}*/ 
public class TaskProgress
        implements ITaskProgress
{
    public static final String NAME_PROPERTY = "name";
    
    public static final double RANGE_FACTOR = 1000;
    //- ******************* 
    //- Fields 
    /**{ field
        @name m_Thread
        @function Task Thread
    }*/ 
    private Thread m_Thread; 
    
    private  DefaultBoundedRangeModel m_data;
    
    /**{ field
        @name m_name
        @function task name
    }*/ 
    private String m_name;

    /**{ field
        @name m_StartTime
        @function time task started
    }*/ 
    private Calendar m_StartTime;

    /**{ field
        @name m_LastUpdate
        @function time task last changed
    }*/ 
    private Calendar m_LastUpdate;
    
    private List m_listeners;
    
    private boolean m_IsDone;


    //- ******************* 
    //- Methods 
    /**{ constructor
        @name TaskProgress
        @function Constructor of TaskProgress
        @param end value at end of task
        @param name task name
    }*/ 
    public TaskProgress(String name,double end) {
        this();
        m_data = new DefaultBoundedRangeModel(0,0,0,(int)end);
        m_name = name;
        register();
    }

    /**{ constructor
        @name TaskProgress
        @function Constructor of TaskProgress
    }*/ 
    public TaskProgress() {
        m_LastUpdate  = new GregorianCalendar();
        m_StartTime   = new GregorianCalendar();
        m_Thread      = Thread.currentThread(); // executing Thread
    }

    /**{ method
        @name setTask
        @function set name + value
             This is more efficient than setUrl() + setValue()
             since only one update needed
        @param newName the name
        @param newValue the value
        @policy call super last
        @primary
    }*/ 
    public synchronized void setTask(String newName,double newValue) {
        if(m_data.getValue() == newValue && newName.equals(m_name)) {
            return;
        }
        m_name = newName;
        m_data.setValue((int)newValue);
        update();
    }

    /**{ method
        @name setUrl
        @function set the task name - this is something to tell the
             user what is being done - it can change as a task progresses
        @param newName - the new name
        @policy call super last
        @primary
    }*/ 
    public synchronized void setName(String newName) {
        if(newName.equals(m_name)) {
            return;
        }
        String OldName = m_name;
        m_name = newName;
        if(m_listeners != null && m_listeners.size() > 0) {
            PropertyChangeEvent ev = 
                new PropertyChangeEvent(this,NAME_PROPERTY,OldName,newName);
            for(int i = 0; i < m_listeners.size(); i++)
                ((PropertyChangeListener)m_listeners.get(i)).propertyChange(ev);
        }
        update();
    }

    /**{ method
        @name setValue
        @function set the value ot the progress
        @param newValue new task value
        @policy call super last
        @primary
    }*/ 
    public synchronized void setValue(double newValue) {
        m_data.setValue((int)newValue);
        update();
    }

    /**{ method
        @name increment
        @function - add 1 to the value
        @policy call super last
        @primary
    }*/ 
    public synchronized void increment() {
        increment(1.0);
    }

    /**{ method
        @name increment
        @function Add d to the value
        @param d what to add
        @policy call super last
        @primary
    }*/ 
    public synchronized void increment(double d) {
        setValue(m_data.getValue() + d);
    }

    /**{ method
        @name setStart
        @function set the value at which the task started
        @param newValue the new value
        @policy call super last
        @primary
    }*/ 
    public synchronized void setStart(double newValue) {
        m_data.setMinimum((int)newValue);
        update();
    }

    /**{ method
        @name setEnd
        @function set the value at which the task will be done
        @param newValue new end>
        @policy call super last
        @primary
    }*/ 
    public synchronized void setEnd(double newValue) {
        m_data.setMaximum((int)newValue);
        update();
    }

    /**
     * return progress as a model
     * @return
     */
    public BoundedRangeModel getModel() {
        return(m_data);
    }
    
     /**{ method
        @name getThread
        @function return the executing Thread
        @return the value
        @policy rarely override
        @primary
    }*/ 
    public Thread getThread() {
        return(m_Thread);
    }

   /**{ method
        @name start
        @function return the start value of the task - usually 0
        @return the value
        @policy call super last
        @primary
    }*/ 
    public int getStart() {
        return(m_data.getMinimum());
    }

    /**{ method
        @name end
        @function return the value at whoich the task will be done
        @return the end
        @policy rarely override
        @primary
    }*/ 
    public int getEnd() {
        return(m_data.getMaximum());
    }

    /**{ method
        @name value
        @function - return the current value
        @return the value
        @policy rarely override
        @primary
    }*/ 
    public int getValue() {
        return(m_data.getValue());
    }

    /**{ method
        @name fraction
        @function return the fraction of the task completed
        @return - the fraction completed 0-1.0
        @policy rarely override
        @primary
    }*/ 
    public double getFraction() {
        double diff = m_data.getMaximum() - m_data.getMinimum();
        if(diff <= 0) {
            return(0.0);
        }
        double frac = m_data.getValue() / diff;
        return(Math.min(1.0,frac));
    }

    /**{ method
        @name getUrl
        @function get the task name
        @return the name
        @policy <Add Comment Here>
        @primary
    }*/ 
    public String getName() {
        return(m_name);
    }

    /**{ method
        @name lastUpdate
        @function return the time the task was last updated
        @return the tile
        @policy rarely override
        @primary
    }*/ 
    public Calendar getLastUpdate() {
        return(m_LastUpdate);
    }

    /**{ method
        @name startTime
        @function return the time the task was started
        @return the tile
        @policy <Add Comment Here>
        @primary
    }*/ 
    public Calendar getStartTime() {
        return(m_StartTime);
    }

    /**{ method
        @name update
        @function - call to update display
        @policy rarely override
        @primary
    }*/ 
    protected void update() {
        if(!m_IsDone) {
            ProgressManager.update(this);
            m_LastUpdate = new GregorianCalendar();
        }
        // remember when we were last updated
    }

    /**{ method
        @name register
        @function - register the task
        @policy rarely override
        @primary
    }*/ 
    protected void register() {
        ProgressManager.register(this);
    }

    /**{ method
        @name done
        @function call when the task is completed - put in a finally
             block so this is always called
        @policy rarely override
        @primary
    }*/ 
    public synchronized void done() {
        if(!m_IsDone) { 
            m_IsDone = true; // only do once
            ProgressManager.done(this);
        }
    }
    
    public void addPropertyChangeListener(PropertyChangeListener l) 
    {
        if(m_listeners == null) 
            m_listeners = new Vector();
        m_listeners.add(l);
    }
     public void removePropertyChangeListener(PropertyChangeListener l) 
    {
        if(m_listeners == null) 
            m_listeners.remove(l);
    }
   


//- ******************* 
//- End Class TaskProgress
}
/* 
Examples
	In  the first example a task executes a known number of iterations and the progress is incremented on each iteration. Note we first test to for an empty task. The test is not necessary but saves a great deal of work on the part of the ProgressManager.
void DoLongTask(int numberIterations) 
{
	if (numberIterations <= 0)
		return;
	TaskProgress TheTask = new TaskProgress("Doing a very long task" ,numberIterations);
	try {
		for(int k = 0; k < numberIterations; k++) {
			doSomeTask();
			TheTask.increment(); // update the user
		}
	} // and try
	finally {
		TheTask.done();
	} // end finally	
}
	In  the second example we alter the name of the task to better inform the user about what work is being performed.. Note we first test to for an empty task. The test is not necessary but saves a great deal of work on the part of the ProgressManager.

void ReadSeveralFiles(String[] FileNames) 
{
	if (FileNames == null || FileNames.length == 0)
		return;
	TaskProgress TheTask = new TaskProgress("Reading Files",FileNames.length);
	try {
		for(int k = 0; k < numberIterations; k++) {
			TheTask .setUrl("Reading " + FileNames[k]);
				// tell the user we are reading file k
			diReadAFile(FileNames[k]);
			TheTask.increment(); // update the user
		}
	} // and try
	finally {
		TheTask.done();
	} // end finally	
}
}*/ 
