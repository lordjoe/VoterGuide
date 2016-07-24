package com.lordjoe.ui;


import com.lordjoe.lang.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * com.lordjoe.ui.AbstractResourceAction
 *
 * @author Steve Lewis
 * @date Jan 25, 2006
 */
public abstract class  AbstractResourceAction extends AbstractAction implements ResourceListener
{
    private final Set m_Resources;
    public AbstractResourceAction()
    {
       m_Resources = Collections.synchronizedSet(new HashSet());
    }

    /**
     * Invoked when an action occurs.
     */
    public synchronized void actionPerformed(ActionEvent e)
    {
        new Thread(new PerformanceRunner()).start();
     }

     public class PerformanceRunner implements Runnable
     {
            public void run()
         {
             IAquirableResource[] resources = getResources();
             try {

                 for (int i = 0; i < resources.length; i++) {
                     IAquirableResource resource = resources[i];
                     if(!resource.acquire())
                         return;
                 }
                 performWithResources();

             }
             finally {
                 for (int i = 0; i < resources.length; i++) {
                      IAquirableResource resource = resources[i];
                      resource.release();
                  }
             }
         }
     }

     protected synchronized IAquirableResource[] getResources()
     {
         IAquirableResource[] ret = new IAquirableResource[m_Resources.size()];
         m_Resources.toArray(ret);
         return ret;
     }

    /**
     *
     */
     protected abstract void performWithResources();

    /**
     * take action when resource availibility is different
     *
     * @param target non-null resource
     */
    public void onResourceAvailabilityChange(IResource target)
    {
        revalidate();
    }

    public synchronized void addResource(IAquirableResource res) {
        res.addResourceListener(this);
        m_Resources.add(res);
        revalidate();
    }

    public synchronized void removeResource(IAquirableResource res) {
        res.removeResourceListener(this);
        m_Resources.remove(res);
        revalidate();
    }

    public synchronized void revalidate()
    {
        StringBuffer sb = new StringBuffer();
        IAquirableResource[] resources = getResources();
        for (int i = 0; i < resources.length; i++) {
            IAquirableResource resource = resources[i];
            if(!resource.isAvailable()) {
                if(sb.length() != 0)
                    sb.append("\n");
                 sb.append("resource " + resource.getName() + "is not available\n");
            }
        }
        if(sb.length() == 0) {
            setEnabled(true);
        }
        else {
             setEnabled(false);
             putValue(Action.SHORT_DESCRIPTION,sb.toString());
        }
    }
}
