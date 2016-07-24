package com.lordjoe.ui;

import com.lordjoe.general.UIUtilities;
import com.lordjoe.ui.general.WrappedAction;
import com.lordjoe.utilities.ResourceString;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;


/**
 * com.lordjoe.ui.DefaultMenuBar
 *
 * @author slewis
 * @date Feb 21, 2005
 */
public class DefaultMenuBar extends JMenuBar
{
    public static final Class THIS_CLASS = DefaultMenuBar.class;
    public static final DefaultMenuBar EMPTY_ARRAY[] = {};

    public static final String NEW_NAME = ResourceString.getStringFromText("New");
    public static final String EXIT_NAME = ResourceString.getStringFromText("Exit");
    public static final String OPEN_NAME = ResourceString.getStringFromText("Open");
    public static final String UNDO_NAME = ResourceString.getStringFromText("Undo");
    public static final String REDO_NAME = ResourceString.getStringFromText("Redo");
    public static final String CLOSE_NAME = ResourceString.getStringFromText("Close");
    public static final String SAVE_NAME = ResourceString.getStringFromText("Save");
    public static final String SAVE_AS_NAME = ResourceString.getStringFromText("Save As");
    public static final String DISCARD_CHANGES_NAME = ResourceString.getStringFromText(
            "discard_changes");


    private final DefaultFrame m_ParentFrame;

    //  private final Map<String,JMenu> m_Menus;
    public DefaultMenuBar(DefaultFrame parent)
    {
        m_ParentFrame = parent;
        initMenuBar();
    }


    protected void initMenuBar()
    {
        JMenu fileMenu = buildFileMenu();
        add(fileMenu);
        JMenu editMenu = buildEditMenu();
        add(editMenu);
        JMenu helpMenu = buildHelpMenu();
        add(helpMenu);
    }

    /**
     * Appends the specified menu to the end of the menu bar.
     *
     * @param c the <code>JMenu</code> component to add
     * @return the menu component
     */
    public JMenu add(JMenu c)
    {
        super.add(c);
        String name = c.getText();
        JMenu[] orderedMenus = UIUtilities.orderMenus(this);
        removeAll();
        for (int i = 0; i < orderedMenus.length; i++) {
            JMenu orderedMenu = orderedMenus[i];
            super.add(orderedMenu);
        }
        return c;
    }

    /**
     * Adds the action indexed by ItemName to the TheMenu
     *
     * @param TheMenu  - menu to add
     * @param ItemName - key used to look up the action
     */
    public WrappedAction addActionMenuItem(JMenu TheMenu, String ItemName)
    {
        String testName = DefaultFrame.conditionMenuName(ItemName);
        DefaultFrame DefaultFrame = getDefaultFrame();
        Action act = DefaultFrame.getAction(testName);
        //    if(act == null)
        //        throw new IllegalArgumentException("action " + testName + " fnot found");
        if (act != null) {
            // NOTE this will construct a menu item
            String name = (String) act.getValue(Action.NAME);
            TheMenu.add(act);
        }
        if (act instanceof WrappedAction)
            return (WrappedAction) act;
        else
            return null;
    }


    /**
     * Builds a File Menu
     */
    protected JMenu buildFileMenu()
    {
        String stringFromText = ResourceString.getStringFromText("File");
        JMenu ret = new JMenu(stringFromText);
//         addActionMenuItem(ret,"New");
//         addActionMenuItem(ret,"Open...");
//         addActionMenuItem(ret,"Save");
//         addActionMenuItem(ret,"Save As ...");
//         addActionMenuItem(ret,"Exit");
        addActionMenuItem(ret, NEW_NAME);
        addActionMenuItem(ret, OPEN_NAME + "...");
        addActionMenuItem(ret, SAVE_NAME);
        addActionMenuItem(ret, SAVE_AS_NAME + "...");
        addActionMenuItem(ret, CLOSE_NAME + "...");
        addActionMenuItem(ret, EXIT_NAME);
        return (ret);
    }

    /**
     * Builds a Edit Menu
     */
    protected JMenu buildEditMenu()
    {
        String stringFromText = ResourceString.getStringFromText("Edit");
        JMenu ret = new JMenu(stringFromText);
        WrappedAction act = null;
        act = addActionMenuItem(ret, "Cut");
        if (act != null)
            act.setRealAction((AbstractAction) EditActionsFactory.getCutAction());

        act = addActionMenuItem(ret, "Copy");
        if (act != null)
            act.setRealAction((AbstractAction) EditActionsFactory.getCopyAction());

        act = addActionMenuItem(ret, "Paste");
        if (act != null)
            act.setRealAction((AbstractAction) EditActionsFactory.getPasteAction());

        act = addActionMenuItem(ret, "Select All");
        if (act != null)
            act.setRealAction((AbstractAction) EditActionsFactory.getSelectAllAction());
        return (ret);
    }

    /**
     * Builds a Edit Menu
     */
    protected JMenu buildHelpMenu()
    {
        String stringFromText = ResourceString.getStringFromText("Help");
        JMenu ret = new JMenu(stringFromText);
        addActionMenuItem(ret, ResourceString.getStringFromText("Manual") + "...");
        addActionMenuItem(ret, ResourceString.getStringFromText("About") + "...");
        return (ret);
    }

    public synchronized JMenu getMenu(String name)
    {
        int count = getMenuCount();
        for (int i = 0; i < count; i++) {
            JMenu test = getMenu(i);
            if (name.equals(test.getText())) {
                return test;
            }
        }
        return null;
    }

    /**
     * return ancestor combe frame
     *
     * @return non-null frame
     */
    public DefaultFrame getDefaultFrame()
    {
        return m_ParentFrame;
    }

    public ResourceBundle getResources()
    {
        return getDefaultFrame().getResources();
    }

    public String getResourceString(String nm)
    {
        return getDefaultFrame().getResourceString(nm);
    }

    public Object getResourceObject(String nm)
    {
        return getDefaultFrame().getResourceObject(nm);
    }


    public void paint(Graphics g)
    {
        super.paint(g);
        // repaint(10000); // sometines this does not redraw
    }

}