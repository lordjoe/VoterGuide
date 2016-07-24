package com.lordjoe.logging;

import com.lordjoe.utilities.*;

import javax.swing.*;
import java.util.*;
import java.awt.*;

/**
 * com.lordjoe.logging.LoggerTestFrame
 *
 * @author Steve Lewis
 * @date Jun 30, 2008
 */
public class LoggerTestFrame extends JFrame
{
    public static LoggerTestFrame[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = LoggerTestFrame.class;
    public static final Random RND = new Random();
    public static final int CREATE_INTERVAL = 2000;
    public static final int DATA_INTERVAL = 10000;

    protected static String intToColorString(int value)
    {
        if (value < DATA_INTERVAL / 4)
            return "black";
        if (value < DATA_INTERVAL / 2)
            return "green";
        if (value < (3 * DATA_INTERVAL) / 4)
            return "yellow";
        return "red";
    }

    private IObjectLogger<NumericEvent> m_NumericLogger;
    private IObjectLogger<TimedNumericEvent> m_TimedNumericLogger;

    /**
     * Constructs a new frame that is initially invisible.
     * <p/>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @throws java.awt.HeadlessException if GraphicsEnvironment.isHeadless()
     *                                    returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see java.awt.Component#setSize
     * @see java.awt.Component#setVisible
     * @see javax.swing.JComponent#getDefaultLocale
     */
    public LoggerTestFrame()
            throws HeadlessException
    {
        m_NumericLogger = new AbstractObjectLogger<NumericEvent>("Numeric Events",new NumericEvent[0]);
        m_TimedNumericLogger = new AbstractObjectLogger<TimedNumericEvent>("Timed Numeric Events",new TimedNumericEvent[0]);
        addWindowListener(new ExitOnClose());

        Thread thr = new Thread(new NEMaker(), "Numeric Event Maker");
        thr.setDaemon(true);
        thr.start();
        thr = new Thread(new TNEMaker(), "Timed Numeric Event Maker");
        thr.setDaemon(true);
        thr.start();
    }

    public class NumericEvent
    {
        private final int m_Number;

        public NumericEvent(int pN_Number)
        {
            m_Number = pN_Number;
        }

        public int getNumber()
        {
            return m_Number;
        }
    }

    public class TimedNumericEvent implements ITimedEvent
    {
        private final int m_Number;
        private final Calendar m_Time;

        public TimedNumericEvent(int pN_Number)
        {
            m_Number = pN_Number;
            m_Time = Calendar.getInstance();
        }

        public int getNumber()
        {
            return m_Number;
        }

        public Calendar getTime()
        {
            return m_Time;
        }
    }

    public class NEMaker implements Runnable
    {
        public void run()
        {
            while (true) {
                ThreadUtilities.waitFor(CREATE_INTERVAL);
                NumericEvent ne = new NumericEvent(RND.nextInt(DATA_INTERVAL));
                m_NumericLogger.log(ne);
            }

        }
    }

    public class TNEMaker implements Runnable
    {
        public void run()
        {
            while (true) {
                ThreadUtilities.waitFor(CREATE_INTERVAL);
                TimedNumericEvent ne = new TimedNumericEvent(RND.nextInt(DATA_INTERVAL));
                m_TimedNumericLogger.log(ne);
             }

        }
    }

    public static class TimedNumericEventRenderer extends
            AbstractLoggedObjectListCellRenderer<TimedNumericEvent>
    {

        protected String buildDataString(TimedNumericEvent evt)
        {
            int number = evt.getNumber();
            StringBuilder sb = new StringBuilder();
            sb.append("<font color=\"" + intToColorString(number) + "\">");
            sb.append(number);
            sb.append("</font>");
            return sb.toString();
        }
    }

    public static class NumericEventRenderer extends
            AbstractLoggedObjectListCellRenderer<NumericEvent>
    {
        protected String buildDataString(NumericEvent evt)
        {
            int number = evt.getNumber();
            StringBuilder sb = new StringBuilder();
            sb.append("<font color=\"" + intToColorString(number) + "\">");
            sb.append(number);
            sb.append("</font>");
            return sb.toString();
          }

    }


    public static void main(String[] args)
    {
        LoggingUtilities.registerCellRenderer(TimedNumericEvent.class,
                new TimedNumericEventRenderer());
        LoggingUtilities.registerCellRenderer(NumericEvent.class, new NumericEventRenderer());
        LoggerTestFrame lt = new LoggerTestFrame();
        lt.setLayout(new BorderLayout());
        
        JObjectLoggerPanel<NumericEvent> p1 = new JObjectLoggerPanel<NumericEvent>(new NumericEvent[0]);
        p1.setLogger(lt.m_NumericLogger);
        lt.add(p1,BorderLayout.NORTH);
        
        JObjectLoggerPanel<TimedNumericEvent> p2 = new JObjectLoggerPanel<TimedNumericEvent>(new TimedNumericEvent[0]);
        p2.setLogger(lt.m_TimedNumericLogger);
        lt.add(p2,BorderLayout.SOUTH);

        lt.pack();
        lt.setSize(300,500);
        lt.setVisible(true);
    }
}
