package com.lordjoe.runner;

import com.lordjoe.lib.xml.*;
import com.lordjoe.utilities.*;
import com.lordjoe.general.*;

import java.util.*;
import java.util.concurrent.locks.*;
import java.util.concurrent.*;
import java.io.*;

/**
 * com.lordjoe.runner.RunActionManager
 *
 * @author Steve Lewis
 * @date Feb 21, 2007
 */
public class RunActionManager extends ImplementationBase implements Runnable
{
    public static RunActionManager[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = RunActionManager.class;
    public static final int MAX_THREADS = 20;
    public static final String AUTORUN_PROPERTY = "RunRunActions";

    private static boolean gSetup;


    protected static boolean isSetup()
    {
        return gSetup;
    }

    protected static void setSetup(boolean pSetup)
    {
        gSetup = pSetup;
    }

    private static RunActionManager gInstance = new RunActionManager();

    public static RunActionManager getInstance()
    {
        return gInstance;
    }

    private final Map<String, IRunAction> m_RunActions;
    private final Map<String, Object> m_DefaultParameters;
    //    private final List<IRunAction> m_AutoRunRunActions;
    private final Lock m_RunActionRunningLock;
    private IActionRunner m_runningAction;

    private String[] m_RunActionsToRunAtStartup;
    private final BlockingQueue<IActionRunner> m_queuedActions;
    private IActionRunner m_runner;
    private final Stack<IActionRunner> m_currentActions;
    private final List<ActionQueueChangeListener> m_queueListeners;
    private final List<RunningActionChangeListener> m_runningListeners;
    private final List<EnvironmentChangedListener> m_environmentChangedListeners;
    private final List<RunActionRunningListener> m_Listeners;
    private final List<TopLeveActionCompleteListener> m_TopLevelRunActionCompleteListeners;
    private final ExecutorService m_Executor;
    private File m_OutputFile;
    //  private ConfigReader m_Config;
    private Thread m_TaskThread;
    private boolean m_CurrentTaskTerminated;

    private RunActionManager()
    {
        m_RunActions = new HashMap<String, IRunAction>();
        m_queuedActions = new ArrayBlockingQueue<IActionRunner>(256);
        m_DefaultParameters = new HashMap<String, Object>();
        m_RunActionRunningLock = new ReentrantLock();
        m_Executor = Executors.newFixedThreadPool(MAX_THREADS);
        m_Listeners = Collections.synchronizedList(new ArrayList<RunActionRunningListener>());
        m_environmentChangedListeners = Collections.synchronizedList(
                new ArrayList<EnvironmentChangedListener>());
        m_currentActions = new Stack<IActionRunner>();
        m_queueListeners = Collections.synchronizedList(new ArrayList<ActionQueueChangeListener>());
        m_runningListeners = Collections.synchronizedList(
                new ArrayList<RunningActionChangeListener>());
        m_TopLevelRunActionCompleteListeners = Collections.synchronizedList(
                new ArrayList<TopLeveActionCompleteListener>());

        Thread ManagerThread = new Thread(this, "RunAction Manager Runner");
        ManagerThread.setDaemon(true);
        ManagerThread.start();
    }

    public boolean isCurrentTaskTerminated()
    {
        return m_CurrentTaskTerminated;
    }

    public void setCurrentTaskTerminated(boolean pCurrentTaskTerminated)
    {
        m_CurrentTaskTerminated = pCurrentTaskTerminated;
    }

    public synchronized void terminate()
    {
        IActionRunner[] iActionRunners = null;
        synchronized (m_queuedActions) {
            iActionRunners = getQueuedRunActions();
            m_queuedActions.clear();
        }
        for (int i = 0; i < iActionRunners.length; i++) {
            IActionRunner tr = iActionRunners[i];
            tr.terminate();
        }
        notifyRunActionQueueChangeListener();
        IActionRunner rt = getRunningRunAction();
        if (rt == null)
            return;
        rt.terminate();
        //     setRunningRunAction(null);
        setCurrentTaskTerminated(true);
        if (m_TaskThread != null)
            m_TaskThread.interrupt();
    }

//
//
//    public ConfigReader getConfig()
//    {
//        return m_Config;
//    }
//
//    /**
//     * return a configuration property
//     *
//     * @param s non-null property name
//     * @return possibly null property
//     */
//    public int findNumber(String s)
//    {
//        Object o = m_Config.findConfigProperty(s);
//        if (o == null || !(o instanceof String))
//            return -1;
//        return Integer.parseInt((String) o);
//    }
//
//    /**
//     * return a configuration property
//     *
//     * @param s non-null property name
//     * @return possibly null property
//     */
//    public Object findConfigProperty(String s)
//    {
//        return m_Config.findConfigProperty(s);
//    }
//
//    /**
//     * return a configuration property
//     *
//     * @param s non-null property name
//     * @return non null property
//     */
//    public Object getConfigProperty(String s) throws ConfigReader.NonexistantPropertyException
//    {
//        return m_Config.getConfigProperty(s);
//    }
//
//    public void setConfig(ConfigReader pConfig)
//    {
//        m_Config = pConfig;
//    }
//


    public String[] getRunActionsToRunAtStartup()
    {
        return m_RunActionsToRunAtStartup;
    }

    public void setRunActionsToRunAtStartup(String[] pRunActionsToRunAtStartup)
    {
        m_RunActionsToRunAtStartup = pRunActionsToRunAtStartup;
    }


    /**
     * off the executor to other parts of the program
     *
     * @param r
     */
    public void runTask(Runnable r)
    {
        m_Executor.submit(r);
    }


    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    public void run()
    {
        m_TaskThread = Thread.currentThread();
        IActionRunner action = null;
        while (true) {

            try {
                action = m_queuedActions.take();
                action.setState(RunStateEnum.Running);
                setRunningRunAction(action);
                notifyRunActionQueueChangeListener();
                IRunAction bt = action.getRunAction();
                String answer = bt.performRunAction(action);
            }
            catch (InterruptedException ex) {
                if (!isCurrentTaskTerminated()) {
                    m_TaskThread = null;
                    return; // I guess
                }
                else {
                    setCurrentTaskTerminated(false);
                }
            }
            catch (RuntimeException ex) {
                if (!isCurrentTaskTerminated()) {
                    m_TaskThread = null;
                    return; // I guess
                }
                else {
                    setCurrentTaskTerminated(false);
                }
            }
            finally {
                if (action != null) {
                    notifyTopLevelRunActionCompleteListeners(action);
                    action = null;
                }
                setRunningRunAction(null);

            }
        }


    }

    public void setDefaultParametersFile(String fileName)
    {
        Properties props = new Properties();
        File theFile = new File(fileName);
        if (!theFile.exists() || !theFile.isFile())
            throw new IllegalArgumentException("Bad default properties file " + fileName);
        XMLSerializer.parseFile(theFile, RunActionManager.getInstance());
    }

//    public String performAutoRunRunActions()
//    {
//        StringBuilder report = new StringBuilder();
//        StringBuilder sb = new StringBuilder();
//        try {
//            String msg = "<AutoRunActions date=\"" + new Date().toString() + "\"  >";
//            appendLogText(msg);
//            report.append(msg);
//            report.append("\n");
//            IRunAction[] runActions = getAutoRunRunActions();
//            for (int i = 0; i < runActions.length; i++) {
//                IRunAction test = runActions[i];
//                long start = System.currentTimeMillis();
//                String result = perform(test);
//                long duration = System.currentTimeMillis() - start;
//                report.append("   ");
//                report.append("<RunAction name=\"" + test.getName() + "\" ");
//                report.append(" durationMSec=\"" + duration + "\" ");
//
//                if (result != null) {
//                    report.append(" result=\"failure\"  >\n");
//                    sb.append(result + "\n");
//                    report.append("   ");
//                    report.append("   ");
//                    report.append("<Failure>" + result + "\n");
//                    report.append("   ");
//                    report.append("   ");
//                    report.append("</Failure>\n");
//                    report.append("   ");
//                    report.append("</RunAction>");
//                }
//                else {
//                    report.append(" result=\"success\" />\n");
//                }
//            }
//            if (sb.length() == 0) {
//                appendLogText("   <Success />");
//                return null;
//            }
//            String problem = sb.toString();
//            appendLogText("     <Failure>\n");
//            appendLogText("     " + problem + "\n");
//            appendLogText("     </Failure>\n");
//            return problem;
//        }
//        catch (Exception e) {
//            return RunActionUtilities.buildExceptionString(e);
//        }
//        finally {
//            String closeStr = "</AutoRunActions>";
//            appendLogText(closeStr);
//            report.append(closeStr);
//            File reportFile = ConfigReader.getRunActionReportFile();
//            FileUtilities.writeFile(reportFile, report.toString());
//        }
//    }

    private void appendLogText(String text)
    {
        try {
            File out = getOutputFile();
            PrintWriter pw = new PrintWriter(new FileWriter(out, true));
            pw.println(text);
            pw.close();
        }
        catch (IOException e) {
            // give up
        }
    }

    public IActionRunner getCurrentRunAction()
    {
        if (m_currentActions.isEmpty())
            return null;
        return m_currentActions.peek();
    }

    public void pushCurrentRunAction(IActionRunner pAction)
    {
        m_currentActions.push(pAction);
        notifyCurrentRunActionChangeListener(pAction);
    }

    public IActionRunner popCurrentRunAction()
    {
        IActionRunner ret = m_currentActions.pop();
        notifyCurrentRunActionChangeListener(getCurrentRunAction());
        return ret;
    }


    public String[] getDefaultparameterKeys()
    {

        String[] ret = new String[m_DefaultParameters.size()];
        m_DefaultParameters.keySet().toArray(ret);
        Arrays.sort(ret);
        return ret;
    }


    public ExecutorService getExecutor()
    {
        return m_Executor;
    }

    public IRunEnvironment getEnvironment()
    {
        return new RunEnvironmentImpl(m_runner, m_DefaultParameters);
    }

    public IActionRunner getRunner()
    {
        return m_runner;
    }

    public void setRunner(IActionRunner pRunner)
    {
        m_runner = pRunner;
    }

    public File getOutputFile()
    {
        return m_OutputFile;
    }

    public void setOutputFile(File pOutputFile)
    {
        m_OutputFile = pOutputFile;
    }

    public void addRunAction(IRunAction added)
    {
        m_RunActions.put(added.getName(), added);
    }

    public IRunAction getRunAction(String name)
    {
        return m_RunActions.get(name);
    }

//    protected String[] getAutoRunRunActionNames()
//    {
//        String autorunRunActions = (String) getConfig().getConfigProperty(AUTORUN_PROPERTY);
//        String[] items = autorunRunActions.split(";");
//        List<String> holder = new ArrayList<String>();
//        for (int i = 0; i < items.length; i++) {
//            String item = items[i];
//            if (item.contains(":")) {
//                String[] parts = item.split(":");
//                item = parts[0];
//            }
//            holder.add(item);
//        }
//
//        String[] ret = new String[holder.size()];
//        holder.toArray(ret);
//        return ret;
//    }

//    public IRunAction[] getAutoRunRunActions()
//    {
//        String[] testNames = getAutoRunRunActionNames();
//
//        List<IRunAction> holder = new ArrayList<IRunAction>();
//        //      String[] testNames = getRunActionsToRunAtStartup();
//        Set<String> runAtStartup = new HashSet<String>(Arrays.asList(testNames));
//        IRunAction[] runActions = getAllRunActions();
//        for (int i = 0; i < runActions.length; i++) {
//            IRunAction runAction = runActions[i];
//            String name = runAction.getName();
//            if (runAtStartup.contains(name)) {
//                holder.add(runAction);
//                runAtStartup.remove(name);
//            }
//        }
//        if (runAtStartup.size() > 0) {
//            String[] notFound = Util.collectionToStringArray(runAtStartup);
//            Arrays.sort(notFound);
//            String notFountRunActions = Util.buildDelimitedString(notFound, ",");
//            throw new IllegalStateException("StartUp RunAction not found " + notFountRunActions);
//        }
//        IRunAction[] ret = new IRunAction[holder.size()];
//        holder.toArray(ret);
//        return ret;
//    }


    public IRunAction[] getAllRunActions()
    {
        String[] keys = new String[m_RunActions.size()];
        m_RunActions.keySet().toArray(keys);
        Arrays.sort(keys);
        List<IRunAction> holder = new ArrayList<IRunAction>();
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            holder.add(getRunAction(key));
        }
        IRunAction[] ret = new IRunAction[holder.size()];
        holder.toArray(ret);
        return ret;
    }


    public IRunAction[] getAllSuites()
    {
        IRunAction[] tests = getAllRunActions();
        List<IRunAction> holder = new ArrayList<IRunAction>();
        for (int i = 0; i < tests.length; i++) {
            IRunAction test = tests[i];
            if (test.isSuite())
                holder.add(test);
        }
        IRunAction[] ret = new IRunAction[holder.size()];
        holder.toArray(ret);
        return ret;
    }

    public IRunAction[] getAllNonSuites()
    {
        IRunAction[] tests = getAllRunActions();
        List<IRunAction> holder = new ArrayList<IRunAction>();
        for (int i = 0; i < tests.length; i++) {
            IRunAction test = tests[i];
            if (!test.isSuite())
                holder.add(test);
        }
        IRunAction[] ret = new IRunAction[holder.size()];
        holder.toArray(ret);
        return ret;
    }


    public Map<String, Object> getDefaultParameters()
    {
        return m_DefaultParameters;
    }


    public boolean isRunActionRunning()
    {
        return getRunningRunAction() != null;
    }


    public IActionRunner getRunningRunAction()
    {
        return m_runningAction;
    }

    public void setRunningRunAction(IActionRunner pRunningAction)
    {
        if (m_runningAction == pRunningAction)
            return;
        m_runningAction = pRunningAction;
        if (pRunningAction != null) {
            m_RunActionRunningLock.lock();
        }
        else {
            m_RunActionRunningLock.unlock();
        }
        m_runningAction = pRunningAction;
        notifyRunningRunActionChangeListener(getRunningRunAction());
        notifyRunActionRunningListener(isRunActionRunning());
    }


    public IActionRunner queueRunAction(IRunAction test)
    {
        IRunEnvironment env = getEnvironment();
        IActionRunner runner = test.buildRunner(env);
        addQueuedRunAction(runner);
        return runner;
    }


    public String perform(IRunAction test)
    {
        try {
            IRunEnvironment env = getEnvironment();
            IActionRunner runner = test.buildRunner(env);
            setRunningRunAction(runner);
            String result = test.performRunAction(runner);
            if (result != null)
                return result;
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        finally {
            setRunningRunAction(null);
        }
    }


    /**
     * add a listener
     *
     * @param listener non-null listener to remove
     */
    public void addTopLevelRunActionCompleteListener(TopLeveActionCompleteListener listener)
    {
        m_TopLevelRunActionCompleteListeners.add(listener);
    }

    /**
     * remove a listener
     *
     * @param listener non-null listener to remove
     */
    public void removeTopLevelRunActionCompleteListener(TopLeveActionCompleteListener listener)
    {
        m_TopLevelRunActionCompleteListeners.remove(listener);
    }

    /**
     * Notify any listners
     */
    public void notifyTopLevelRunActionCompleteListeners(IActionRunner pArg)
    {
        TopLeveActionCompleteListener[] listeners = null;
        synchronized (m_TopLevelRunActionCompleteListeners) {
            if (m_TopLevelRunActionCompleteListeners.isEmpty())
                return;
            listeners = new TopLeveActionCompleteListener[m_TopLevelRunActionCompleteListeners.size()];
            m_TopLevelRunActionCompleteListeners.toArray(listeners);
        }

        for (int i = 0; i < listeners.length; i++) {
            TopLeveActionCompleteListener listener = listeners[i];
            listener.onTopLevelRunActionComplete(pArg);
        }

    }


    public void addRunActionRunningListener(RunActionRunningListener listener)
    {
        m_Listeners.add(listener);
    }

    public void removeRunActionRunningListener(RunActionRunningListener listener)
    {
        m_Listeners.remove(listener);
    }

    public void notifyRunActionRunningListener(boolean isRunning)
    {
        RunActionRunningListener[] listeners = null;
        synchronized (m_Listeners) {
            if (m_Listeners.isEmpty())
                return;
            listeners = new RunActionRunningListener[m_Listeners.size()];
            m_Listeners.toArray(listeners);
        }
        for (int i = 0; i < listeners.length; i++) {
            RunActionRunningListener listener = listeners[i];
            listener.onRunActionRunnerAvailabilityChange(isRunning);
        }

    }


    public void addEnvironmentChangedListener(EnvironmentChangedListener listener)
    {
        m_environmentChangedListeners.add(listener);
    }

    public void removeEnvironmentChangedListener(EnvironmentChangedListener listener)
    {
        m_environmentChangedListeners.remove(listener);
    }

    public void notifyEnvironmentChangedListeners()
    {
        EnvironmentChangedListener[] listeners = null;
        synchronized (m_environmentChangedListeners) {
            if (m_environmentChangedListeners.isEmpty())
                return;
            listeners = new EnvironmentChangedListener[m_Listeners.size()];
            m_environmentChangedListeners.toArray(listeners);
        }
        for (int i = 0; i < listeners.length; i++) {
            EnvironmentChangedListener listener = listeners[i];
            listener.onEnvironmentChanged();
        }

    }


    public void addRunActionQueueChangeListener(ActionQueueChangeListener pListener)
    {
        m_queueListeners.add(pListener);
    }

    public void removeRunActionQueueChangeListener(ActionQueueChangeListener pListener)
    {
        m_queueListeners.remove(pListener);
    }

    public void notifyRunActionQueueChangeListener()
    {
        ActionQueueChangeListener[] listeners = null;
        synchronized (m_queueListeners) {
            if (m_queueListeners.isEmpty())
                return;
            listeners = new ActionQueueChangeListener[m_queueListeners.size()];
            m_queueListeners.toArray(listeners);
        }
        IActionRunner[] runners = getQueuedRunActions();

        for (int i = 0; i < listeners.length; i++) {
            ActionQueueChangeListener listener = listeners[i];
            listener.onRunQueueChange(runners);
        }

    }


    public void addRunningRunActionChangeListener(RunningActionChangeListener pListener)
    {
        m_runningListeners.add(pListener);
    }

    public void removeRunningRunActionChangeListener(RunningActionChangeListener pListener)
    {
        m_runningListeners.remove(pListener);
    }

    public void notifyRunningRunActionChangeListener(IActionRunner pAction)
    {
        RunningActionChangeListener[] listeners = null;
        synchronized (m_runningListeners) {
            if (m_runningListeners.isEmpty())
                return;
            listeners = new RunningActionChangeListener[m_runningListeners.size()];
            m_runningListeners.toArray(listeners);
        }

        for (int i = 0; i < listeners.length; i++) {
            RunningActionChangeListener listener = listeners[i];
            listener.onRunningActionChange(pAction);
        }

    }

    public void notifyCurrentRunActionChangeListener(IActionRunner pAction)
    {
        RunningActionChangeListener[] listeners = null;
        synchronized (m_runningListeners) {
            if (m_runningListeners.isEmpty())
                return;
            listeners = new RunningActionChangeListener[m_runningListeners.size()];
            m_runningListeners.toArray(listeners);
        }

        for (int i = 0; i < listeners.length; i++) {
            RunningActionChangeListener listener = listeners[i];
            listener.onCurrentRunActionChange(pAction);
        }

    }


    public void addQueuedRunAction(IActionRunner pAction)
    {
        m_queuedActions.offer(pAction);
        pAction.setState(RunStateEnum.Queued);
        notifyRunActionQueueChangeListener();
    }

    public IActionRunner[] getQueuedRunActions()
    {
        IActionRunner[] runners = null;
        synchronized (m_queuedActions) {
            runners = new IActionRunner[m_queuedActions.size()];
            m_queuedActions.toArray(runners);
        }
        return runners;
    }


    /**
     * return the tag name for writing out XML
     *
     * @return
     */
    public String getTagName()
    {
        return "RunActionManager";
    }


    /* (non-Javadoc)
    * @see com.lordjoe.lib.xml.ITagHandler#handleTag(java.lang.String, com.lordjoe.lib.xml.NameValue[])
    */
    public Object handleTag(String tagName, NameValue[] attributes)
    {
        if (getTagName().equals(tagName)) {
            return this;
        }
        if ("Parameters".equals(tagName)) {
            return this;
        }
        if ("Parameter".equals(tagName)) {
            String name = XMLUtil.handleRequiredNameValueString("name", attributes);
            String value = XMLUtil.handleRequiredNameValueString("value", attributes);
            m_DefaultParameters.put(name, value);
            return this;
        }
        if ("Action".equals(tagName)) {
            IRunAction irunAction = handleRunActionTag(attributes);
            addRunAction(irunAction);
            return irunAction;
        }
        return super.handleTag(tagName, attributes);

    }

    public IRunAction handleRunActionTag(NameValue[] attributes)
    {
        String name = XMLUtil.handleRequiredNameValueString("name", attributes);
        String primitiveStr = XMLUtil.handleOptionalNameValueString("primitive", attributes);
        boolean primitive = primitiveStr != null && "true".equals(primitiveStr);

        if (primitive) {
            IRunAction irunAction = handlePrimitiveRunActionTag(name, attributes);
            return irunAction;
        }
        else {
            CompositeRunAction runAction = new CompositeRunAction(name);
            return runAction;
        }
    }


    public static IRunAction handlePrimitiveRunActionTag(String name, NameValue[] attributes)
    {
        String className = XMLUtil.handleRequiredNameValueString("class", attributes);
        try {
            IRunAction ret = (IRunAction) ObjectUtilities.buildFromString(name, className);
            return ret;
        }
        catch (RuntimeException e) {
            throw e;
        }
    }


    public static synchronized RunActionManager setupRunActionManager()
    {
        RunActionManager tm = getInstance();
        if (!isSetup()) {
            XMLSerializer.parseFile("config/RunActions.xml", tm);
            setSetup(true);
        }
        return tm;
    }

//    public static String runRunAction()
//    {
//
//        RunActionManager tm = setupRunActionManager();
//        OGCRunActionRunner testRunner = tm.getRunner();
//        File directory = new File(BASE_DIRECTORY);
//        testRunner.setActiveDirectory(directory);
//        testRunner.start();
//        IRunAction test = tm.getRunAction(TEST_NAME);
//        String ret = tm.perform(test);
//        return ret;
//    }
//

    // where crates live

    public static final String TEST_NAME = "PlayTone";

    public static void main(String[] args)
    {
        RunActionManager tm = setupRunActionManager();
        IRunAction test = tm.getRunAction(TEST_NAME);
        String ret = tm.perform(test);
    }

}
