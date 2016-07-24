package com.lordjoe.ui;

import com.lordjoe.general.UIUtilities;
import com.lordjoe.ui.general.JToolBarButton;
import com.lordjoe.ui.general.LocaleChooser;
import com.lordjoe.ui.general.WrappedAction;
import com.lordjoe.utilities.ProgressManager;
import com.lordjoe.utilities.ResourceString;
import com.lordjoe.utilities.Util;
import sun.awt.WindowClosingListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * com.lordjoe.ui.DefaultFrame
 *
 * @author slewis
 * @date Feb 21, 2005
 */
public class DefaultFrame extends JFrame implements IDefaultComponent,
        WindowClosingListener,
        MouseListener
{
    public static final Class THIS_CLASS = DefaultFrame.class;
    public static final DefaultFrame EMPTY_ARRAYs[] = {};

    /**
     * Suffix applied to the key used in resource file
     * lookups for an image.
     */
    public static final String IMAGE_SUFFIX = "Image";
    /**
     * Prefix applied to the key used in resource file
     * lookups for an image.
     */
    public static final String IMAGE_PREFIX = "images/";

    /**
     * Suffix applied to the key used in resource file
     * lookups for a label.
     */
    public static final String LABEL_SUFFIX = "Label";

    /**
     * Suffix applied to the key used in resource file
     * lookups for an action.
     */
    public static final String ACTION_SUFFIX = "Action";
    /**
     * Suffix applied to the key used in resource file
     * lookups for tooltip text.
     */
    public static final String TIP_SUFFIX = "Tooltip";

    private static String gResourceName = "BasicApplication";

    public static String getResourceName()
    {
        return gResourceName;
    }

    public static void setResourceName(String pResourceName)
    {
        gResourceName = pResourceName;
    }


    private static DefaultFrame gCurrentFrame;

    public static DefaultFrame getCurrentFrame()
    {
        return gCurrentFrame;
    }

    private static Image gFrameImage = null;

    public static Image getFrameImage()
    {
        return gFrameImage;
    }

    public static void setFrameImage(Image pFrameImage)
    {
        gFrameImage = pFrameImage;
    }


    public static JMenu getViewMenu(DefaultFrame frame)
    {
        DefaultMenuBar bar = frame.getDefaultMenuBar();
        return getViewMenu(bar);
    }

    public static JMenu getViewMenu(DefaultMenuBar bar)
    {
        String viewText = ResourceString.getStringFromText("View");
        int count = bar.getMenuCount();
        for (int i = 0; i < count; i++) {
            JMenu mnu = bar.getMenu(i);
            String name = mnu.getName();
            if (viewText.equals(name))
                return mnu;
        }
        return buildViewMenu(bar);
    }


    /**
     * Builds a Action Menu
     */
    protected static JMenu buildViewMenu(DefaultMenuBar bar)
    {
        String stringFromText = ResourceString.getStringFromText("View");
        JMenu ret = new JMenu(stringFromText);
        bar.add(ret);
        return (ret);
    }


    /**
     * drop ... from name so we can use name for a file name
     *
     * @param name - name to condition
     * @return name minus ... (elipsis) and with spaces changed to _
     */
    public static String conditionMenuName(String name)
    {
        // drop ...
        if (name.endsWith("..."))
            name = name.substring(0, name.length() - 3);

        name = name.trim();

        // change space to _ so file names will not have spaces
        if (name.contains(" "))
            name = name.replace(' ', '_');

        return (name);
    }

    private final DefaultMenuBar m_MenuBar;
    //    private final ApplicationLabelBar m_LogoBar;
    private final DefaultToolbar m_ToolBar;
    private final JPanel m_ToolsPanel;
    private final DefaultStatusbar m_StatusBar;
    private JPanel m_MainPanel;
    private String m_ApplicationName;
    protected boolean m_ExitOnClose;
    private final Object m_Subject;
    // Actions for automatically adding to Toolbar and menus
    private final Map<String, WrappedAction> m_Actions;  // key is name value is Actions
    private ResourceBundle m_Resources;
    private final Set<IExitBlocker> m_ExitBlocks;  // key is name value is Actions
    private final Set<Runnable> m_OnVisibleTasks;  // key is name value is Actions
    private File m_DefaultDirectory;

    public DefaultFrame(String title)
    {
        this(title, (Object) null);
    }

    public DefaultFrame(String title, Object subject)
    {
        this(subject);
        setTitle(title);
    }

    public DefaultFrame(Object subject)
    {
        m_Subject = subject;
        UIUtilities.systemLookAndFeel();

        m_DefaultDirectory = new File(System.getProperty("user.dir"));
        m_ExitOnClose = true;
        //     ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        m_Actions = new HashMap<String, WrappedAction>();
        m_ExitBlocks = new CopyOnWriteArraySet<IExitBlocker>();
        m_OnVisibleTasks = new CopyOnWriteArraySet<Runnable>();
        setApplicationName(getResourceName());

        m_ToolBar = initToolBar();

        m_MenuBar = initMenuBar();

        //      Icon logoBar = getLogoBar();
        //      m_LogoBar = new ApplicationLabelBar(this, logoBar);


        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
        m_ToolsPanel = new JPanel();
        m_ToolsPanel.setLayout(new BorderLayout());
        m_ToolsPanel.add(m_MenuBar, BorderLayout.NORTH);
        m_ToolsPanel.add(m_ToolBar, BorderLayout.CENTER);
//        m_ToolsPanel.add(m_LogoBar, BorderLayout.SOUTH);

        m_MainPanel = new JPanel();
        m_MainPanel.setLayout(new BorderLayout());
        pane.add(m_ToolsPanel, BorderLayout.NORTH);
        pane.add(m_MainPanel, BorderLayout.CENTER);
        m_StatusBar = new DefaultStatusbar(this);
        pane.add(m_StatusBar, BorderLayout.SOUTH);
        //     getResourceBundle(String applicationName)
        setStatus("Loaded");
        gCurrentFrame = this;

        // JFrames generate WindowEvents and send messages to WindowListeners
        addWindowListener(new ClosingListener());
        //    addWindowStateListener(this);
        addMouseListener(this);
    }

    public DefaultFrame()
    {
        this(null);
        ProgressManager.setActiveFrame(this);
        
    }

    public void repack()
    {
        SwingUtilities.invokeLater(
                new Runnable()
                {
                    public void run()
                    {
                        pack();
                    }
                }
        );
    }

    public final String getResourceString(String nm)
    {
        String str;
        try {
            ResourceBundle resources = getResources();
            str = resources.getString(nm);
        }
        catch (MissingResourceException mre) {
            str = null;
        }
        return str;
    }


    public Object getSubject()
    {
        return m_Subject;
    }

    public File getDefaultDirectory()
    {
        return m_DefaultDirectory;
    }

    public void setDefaultDirectory(File pDefaultDirectory)
    {
        m_DefaultDirectory = pDefaultDirectory;
    }

    /**
     * Sets the <code>glassPane</code> property.
     * This method is called by the constructor.
     *
     * @param glassPane the <code>glassPane</code> object for this frame
     * @beaninfo hidden: true
     * description: A transparent pane used for menu rendering.
     * @see #getGlassPane
     * @see javax.swing.RootPaneContainer#setGlassPane
     */
    public void setGlassPane(Component glassPane)
    {
        super.setGlassPane(glassPane);

    }


    public JMenu getMenu(String name)
    {
        DefaultMenuBar bar = getDefaultMenuBar();
        return bar.getMenu(name);
    }


    public boolean isExitOnClose()
    {
        return m_ExitOnClose;
    }

    public void setExitOnClose(boolean pExitOnClose)
    {
        m_ExitOnClose = pExitOnClose;
    }

    /**
     * Paints the container. This forwards the paint to any lightweight
     * components that are children of this container. If this method is
     * reimplemented, super.paint(g) should be called so that lightweight
     * components are properly rendered. If a child component is entirely
     * clipped by the current clipping setting in g, paint() will not be
     * forwarded to that child.
     *
     * @param g the specified Graphics window
     * @see java.awt.Component#update(java.awt.Graphics)
     */
    public void paint(Graphics g)
    {
        super.paint(g);
    }

    public void addExitBlocker(IExitBlocker blocker)
    {
        m_ExitBlocks.add(blocker);
    }

    public void removeExitBlocker(IExitBlocker blocker)
    {
        m_ExitBlocks.remove(blocker);
    }


    public void addWindowVisibleTask(Runnable task)
    {
        m_OnVisibleTasks.add(task);
    }

    public void removeWindowVisibleTask(Runnable task)
    {
        m_OnVisibleTasks.remove(task);
    }


    //Overridden so we can exit when window is closed
    public void close()
    {
        setVisible(false);
        WindowEvent we = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        processWindowEvent(we);
    }

    /**
     * Invoked when window state is changed.
     */
//    public void setVisible(boolean isso)
//    {
//        // this hack allows code to run on the OLPC
//        if (UIUtilities.isOLPC()) {
//            if (isso) {
//                UIUtilities.showFrame(this);
//            }
//            else {
//                UIUtilities.hideFrame(this);
//            }
//        }
//        else {
//            super.setVisible(isso);
//        }
//        if (!isso)
//            return;
////        Window w = e.getWindow();
////        int state = e.getNewState();
//        Runnable[] tasks = null;
//        boolean visible = isVisible();
//        //    boolean active = isActive();
//        if (visible) {
//            synchronized (m_OnVisibleTasks) {
//                if (m_OnVisibleTasks.isEmpty())
//                    return;
//                tasks = new Runnable[m_OnVisibleTasks.size()];
//                m_OnVisibleTasks.toArray(tasks);
//            }
//            for (int i = 0; i < tasks.length; i++) {
//                Runnable task = tasks[i];
//                if (SwingUtilities.isEventDispatchThread()) {
//                    task.run();
//                }
//                else {
//                    try {
//                        SwingUtilities.invokeAndWait(task);
//                    }
//                    catch (InterruptedException e1) {
//                        throw new WrappingException(e1);
//                    }
//                    catch (InvocationTargetException e1) {
//                        throw new WrappingException(e1);
//                    }
//                }
//            }
//        }
//
//    }

    //Overridden so we can exit when window is closed
    protected void processWindowEvent(WindowEvent e)
    {
        if (e.getID() == WindowEvent.WINDOW_CLOSING && isExitOnClose()) {
            if (isWindowCloseBlocked()) return; // ignore exit

        }
        super.processWindowEvent(e);

        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            if (isExitOnClose()) {
                System.exit(0);
            }
            else {
                setVisible(false);
                dispose();
            }
        }
    }

    public boolean isWindowCloseBlocked()
    {
        StringBuffer sb = new StringBuffer();
        for (IExitBlocker blocker : m_ExitBlocks) {
            String reaonNotToClose = blocker.reasonForBlockingExit();
            if (reaonNotToClose != null) {
                if (sb.length() > 0)
                    sb.append("\n");
                sb.append(reaonNotToClose);
            }
        }
        if (sb.length() > 0) {
            String message = sb.toString();
            AlertDialog.showWarningMessage("Application cannot shutdown because:\n" + message);
            return true;
        }
        return false;
    }


    public class ClosingListener extends WindowAdapter
    {
        public void windowClosing(WindowEvent e)
        {
            super.windowClosing(e);
        }
    }

    public String getApplicationName()
    {
        return m_ApplicationName;
    }

    public void setApplicationName(String name)
    {
        m_ApplicationName = name;
        m_Resources = ResourceUtilities.getResourceBundle(name);
    }

    public RuntimeException windowClosingNotify(WindowEvent pWindowEvent)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public RuntimeException windowClosingDelivered(WindowEvent pWindowEvent)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * return ancestor combe frame
     *
     * @return non-null frame
     */
    public DefaultFrame getDefaultFrame()
    {
        return this;
    }

    public DefaultToolbar getToolBar()
    {
        return m_ToolBar;
    }

    public void setStatus(String text)
    {
        m_StatusBar.setText(text);
    }


    public void setAction(AbstractAction action)
    {
        String name = (String) action.getValue(Action.NAME);
        String realname = (String) action.getValue(WrappedAction.REAL_NAME);
        if (name == null) {
            name = (String) action.getValue(Action.NAME);
            throw new UnsupportedOperationException("Fix This"); // ToDO Fix Thix
        }
        WrappedAction ret = null;
        synchronized (m_Actions) {
            name = conditionMenuName(name);
            ret = m_Actions.get(name);
            if (ret == null && realname != null) {
                ret = m_Actions.get(realname);
            }
            if (ret == null) {
                if (action instanceof WrappedAction)
                    ret = (WrappedAction) action;
                else {
                    ret = new WrappedAction();
                    ret.setRealAction(action);
                }
                m_Actions.put(name, ret);
                m_Actions.put(name.toLowerCase(), ret);
                if (realname != null) {
                    m_Actions.put(realname, ret);
                    m_Actions.put(realname.toLowerCase(), ret);
                }
                else {
                    Util.breakHere();
                }
            }

        }

    }

    public WrappedAction setAction(String name, AbstractAction realAction)
    {
        name = conditionMenuName(name);
        WrappedAction ret = null;
        synchronized (m_Actions) {
            String[] keys = Util.collectionToStringArray(m_Actions.keySet());
            ret = m_Actions.get(name);
            if (ret == null) {
                ret = new WrappedAction();
                m_Actions.put(name, ret);
                m_Actions.put(name.toLowerCase(), ret);
                ret.putValue(Action.NAME, name);
            }
            ret.setRealAction(realAction);
        }
        return ret;
    }

    public Action getAction(String name)
    {
        name = conditionMenuName(name);
        synchronized (m_Actions) {
            String[] keys = Util.collectionToStringArray(m_Actions.keySet());
            WrappedAction ret = m_Actions.get(name);
            if (ret == null && name.contains("_")) {
                ret = m_Actions.get(name.replace("_", " "));
            }
            if (ret == null) {
                ret = new WrappedAction();
                m_Actions.put(name, ret);
                m_Actions.put(name.toLowerCase(), ret);
                ret.putValue(Action.NAME, name);
            }
            return ret;
        }
    }

    public DefaultMenuBar getDefaultMenuBar()
    {
        return m_MenuBar;
    }

    public DefaultMenuBar createMenuBar()
    {
        return new DefaultMenuBar(this);
    }

    protected DefaultMenuBar initMenuBar()
    {
        DefaultMenuBar ret = createMenuBar();
        return ret;
    }

    public ResourceBundle getResources()
    {
        return m_Resources;
    }


    public Object getResourceObject(String nm)
    {
        Object ret;
        try {
            ResourceBundle resources = getResources();
            ret = resources.getObject(nm);
        }
        catch (MissingResourceException mre) {
            ret = null;
        }
        return ret;
    }

    /**
     * create a toolbar - youcan override this to create a subclass of
     * DefaultToolbar
     *
     * @return
     */
    protected DefaultToolbar createToolbar()
    {
        return new DefaultToolbar(this);
    }

    protected DefaultToolbar initToolBar()
    {
        DefaultToolbar toolbar = createToolbar();
        toolbar.setFloatable(false);
        String toolbarItems = getResourceString("toolbar");
        if (toolbarItems.length() > 0) {
            String[] toolKeys = toolbarItems.split(" ");
            for (int i = 0; i < toolKeys.length; i++) {
                String toolKey = toolKeys[i];
                if (toolKey.equals("-")) {
                    toolbar.add(Box.createHorizontalStrut(20));
                }
                else {
                    Component tool = createTool(toolKey);
                    toolbar.add(tool);
                }
            }
        }
        
        return toolbar;
    }

    public void addLocaleChooser()
    {
        JToolBar toolbar = m_ToolBar;
        toolbar.add(Box.createHorizontalGlue());
        toolbar.add(new LocaleChooser("resources/i18n", this));
    }


    /**
     * Hook through which every toolbar item is created.
     */
    protected Component createTool(String key)
    {
        return createToolbarButton(key);
    }

    /**
     * Create a button to go inside of the toolbar.  By default this
     * will load an image resource.  The image filename is relative to
     * the classpath (including the '.' directory if its a part of the
     * classpath), and may either be in a JAR file or a separate file.
     *
     * @param key The key in the resource file to serve as the basis
     *            of lookups.
     */
    protected JButton createToolbarButton(String key)
    {
        String anm = key + ACTION_SUFFIX;
        String astr = getResourceString(anm);
        if (astr == null) {
            astr = key.replace("_", " ");
        }
        Action a = getAction(astr);
        ImageIcon icon = (ImageIcon) a.getValue(Action.SMALL_ICON);
        if (icon == null) {
            icon = UIUtilities.getImageIcon(key);
        }
        JToolBarButton b = null;

        b = new JToolBarButton(a);
        b.setIcon(icon);
        b.setRequestFocusEnabled(false);
        b.setMargin(new Insets(1, 1, 1, 1));

        if (a != null) {
            b.setActionCommand(astr);
            b.addActionListener(a);
        }
        else {
            b.setEnabled(false);
        }

        String tnm = key + TIP_SUFFIX;
        String tip = getResourceString(tnm);
        if (tip != null) {
            b.setToolTipText(tip);
        }
        else {
            b.setToolTipText(tnm);
        }

        return b;
    }

//    protected Icon getLogoBar()
//    {
//        Object logo = RESOURCES.getObject(LogoResources.LOGO_KEY);
//        Icon theImage = (Icon) logo;
//        return theImage;
//    }


    public void setMainDisplay(Container panel)
    {
        m_MainPanel.removeAll();
        m_MainPanel.add(panel, BorderLayout.CENTER);
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     */
    public void mouseClicked(MouseEvent e)
    {
        if (UIUtilities.isInformationRequest(e)) {
            UIUtilities.displayInformation(e, this);
        }
    }


    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     */
    public void mouseMoved(MouseEvent e)
    {
        // Do nothing but maybe override
    }

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  <code>MOUSE_DRAGGED</code> events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p/>
     * Due to platform-dependent Drag&Drop implementations,
     * <code>MOUSE_DRAGGED</code> events may not be delivered during a native
     * Drag&Drop operation.
     */
    public void mouseDragged(MouseEvent e)
    {
        // Do nothing but maybe override
    }

    /**
     * Invoked when the mouse exits a component.
     */
    public void mouseExited(MouseEvent e)
    {
        // Do nothing but maybe override
    }

    /**
     * Invoked when the mouse enters a component.
     */
    public void mouseEntered(MouseEvent e)
    {
        // Do nothing but maybe override
    }

    /**
     * Invoked when a mouse button has been released on a component.
     */
    public void mouseReleased(MouseEvent e)
    {
        // Do nothing but maybe override
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    public void mousePressed(MouseEvent e)
    {
        // Do nothing but maybe override
    }
}