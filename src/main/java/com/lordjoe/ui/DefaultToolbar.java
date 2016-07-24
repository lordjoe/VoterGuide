package com.lordjoe.ui;

import com.lordjoe.lang.FileUtil;
import com.lordjoe.ui.general.WrappedAction;
import com.lordjoe.utilities.ResourceString;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ResourceBundle;


/**
 * com.lordjoe.ui.DefaultToolbar
 *
 * @author slewis
 * @date Feb 21, 2005
 */
public class DefaultToolbar extends JToolBar {
    public static final Class THIS_CLASS = DefaultToolbar.class;
    public static final DefaultToolbar EMPTY_ARRAY[] = {};

    public static String fromResource(String in) {
        String val = ResourceString.getStringFromText(in);
        return val;
    }

    private final DefaultFrame m_ParentFrame;

    private WrappedAction m_NewAction;
    private WrappedAction m_OpenAction;
    private WrappedAction m_CloseAction;
    private WrappedAction m_SaveAction;
    private WrappedAction m_SaveAsAction;
    private WrappedAction m_DiscardAction;
    private WrappedAction m_UndoAction;
    private WrappedAction m_RedoAction;
    private WrappedAction m_CutAction;
    private WrappedAction m_CopyAction;
    private WrappedAction m_PasteAction;
    private WrappedAction m_SelectAllAction;
    private WrappedAction m_MailAction;
    private WrappedAction m_AboutAction;
    private WrappedAction m_ExitAction;

    public DefaultToolbar(DefaultFrame parent) {
        m_ParentFrame = parent;
        createActions();
        initToolBar();
    }

    private void initToolBar() {

    }

    public void setNewAction(AbstractAction newAction) {
        m_NewAction.setRealAction(newAction);
    }


    public void setMailAction(AbstractAction newAction) {
        m_MailAction.setRealAction(newAction);
    }

    public void setOpenAction(Action openAction) {
        m_OpenAction.setRealAction((AbstractAction)openAction);
    }

    public void setCloseAction(Action openAction) {
        m_CloseAction.setRealAction((AbstractAction)openAction);
    }

    public void setDiscardAction(Action openAction) {
        m_DiscardAction.setRealAction((AbstractAction)openAction);
    }

    public void setSaveAction(Action saveAction) {
        m_SaveAction.setRealAction((AbstractAction)saveAction);
    }

    public void setUndoAction(Action saveAsAction) {
        m_UndoAction.setRealAction((AbstractAction)saveAsAction);
    }

    public void setRedoAction(Action saveAsAction) {
        m_RedoAction.setRealAction((AbstractAction)saveAsAction);
    }

    public void setSaveAsAction(Action saveAsAction) {
        m_SaveAsAction.setRealAction((AbstractAction)saveAsAction);
    }

    public void setCutAction(Action cutAction) {
        m_CutAction.setRealAction((AbstractAction)cutAction);
    }

    public void setCopyAction(Action copyAction) {
        m_CopyAction.setRealAction((AbstractAction)copyAction);
    }

    public void setPasteAction(Action pasteAction) {
        m_PasteAction.setRealAction((AbstractAction)pasteAction);
    }

    public void setSelectAllAction(Action selectAllAction) {
        m_SelectAllAction.setRealAction((AbstractAction)selectAllAction);
    }

    public void setExitAction(Action exitAction) {
        m_ExitAction.setRealAction((AbstractAction)exitAction);
    }

    public void setAboutAction(Action exitAction) {
        m_AboutAction.setRealAction((AbstractAction)exitAction);
    }

    /**
     * Actions are handlers for one menu. button or Toolbar -
     * This creates actions.
     * Note we may drop ... and change spaces to _ when
     * asspociating files i.e. images with actions
     */
    protected void createActions() {
        DefaultFrame cf = getDefaultFrame();
        if (cf == null)
            return;
        Action action = null;
        m_NewAction = new NewAction();
        cf.setAction(m_NewAction);

        m_MailAction = new MailAction();
        cf.setAction(m_MailAction);

        m_OpenAction = new OpenFileAction();
        cf.setAction(m_OpenAction);

        m_CloseAction = new CloseFileAction();
        cf.setAction(m_CloseAction);

        m_DiscardAction = new DiscardAction();
        cf.setAction(m_DiscardAction);

        m_SaveAction = new SaveAction();
        cf.setAction(m_SaveAction);

        m_SaveAsAction = new SaveAsAction();
        cf.setAction(m_SaveAsAction);

        m_UndoAction = new UndoAction();
        cf.setAction(m_UndoAction);

        m_RedoAction = new RedoAction();
        cf.setAction(m_RedoAction);

//        m_CutAction = new CutAction();
//        cf.setAction(m_CutAction);
//        m_CopyAction = new CopyAction();
//        cf.setAction(m_CopyAction);
//        m_PasteAction = new PasteAction();
//        cf.setAction(m_PasteAction);
//        m_SelectAllAction = new SelectAllAction();
//        cf.setAction(m_SelectAllAction);
        m_ExitAction = new ExitAction();
        cf.setAction(m_ExitAction);
        m_AboutAction = new AboutAction();
        cf.setAction(m_AboutAction);

//         Action act = new ChooseTextColorAction();
//         act.putValue(Action.SMALL_ICON, m_TextColorIcon);
//         cf.setAction("Text Color ...",act);
//
//         act = new ChooseBackColorAction();
//         act.putValue(Action.SMALL_ICON, m_BackColorIcon);
//         cf.setAction("Back Color ...",act);
    }

    /**
     * return ancestor combe frame
     *
     * @return non-null frame
     */
    public DefaultFrame getDefaultFrame() {
        return m_ParentFrame;
    }


    /**
     * Adds an action to the actions hashtable. Key is
     * conditioned name with ... dropped and space changed to _
     * If these is a gif file in images with the name of the action
     * associate it with the action
     * also seta the action name
     *
     * @param name - action name
     * @param act  - affected action
     */
    protected void setAction(Action act) {
        String name = (String) act.getValue(Action.NAME);
        String ConditionedName = DefaultFrame.conditionMenuName(name);
        act.putValue(Action.NAME, ConditionedName);
        getDefaultFrame().setAction((AbstractAction)act);
    }

    /**
     * Adds an action to the actions hashtable. Key is
     * conditioned name with ... dropped and space changed to _
     * If these is a gif file in images with the name of the action
     * associate it with the action
     * also seta the action name
     *
     * @param name - action name
     * @param act  - affected action
     */
    protected void setAction(String name, Action act) {
        String ConditionedName = DefaultFrame.conditionMenuName(name);
        act.putValue(Action.NAME, name);
        act.putValue(Action.NAME, ConditionedName);
        // look for an image to associate
        InputStream inp = getClass().getResourceAsStream("images/" + ConditionedName + ".gif");
        if (inp != null) {
            ImageIcon img = new ImageIcon(FileUtil.readInBytes(inp));
            act.putValue(Action.SMALL_ICON, img);
        }
        // remember the action
        getDefaultFrame().setAction((AbstractAction)act);
    }

    /**
     Associate actions with Keystrokes
     */
//     protected void setKeyBoardActions()
//     {
//         // In each case we create a KeyStroke to associate with the action
//         setKeyBoardAction(m_Text,"New",KeyStroke.getKeyStroke('N',Event.CTRL_MASK));
//         setKeyBoardAction(m_Text,"Open ...",KeyStroke.getKeyStroke('O',Event.CTRL_MASK));
//         setKeyBoardAction(m_Text,"Cut",KeyStroke.getKeyStroke('X',Event.CTRL_MASK));
//         setKeyBoardAction(m_Text,"Copy",KeyStroke.getKeyStroke('C',Event.CTRL_MASK));
//         setKeyBoardAction(m_Text,"Paste",KeyStroke.getKeyStroke('V',Event.CTRL_MASK));
//     }

    /**
     * Associate one actions with  a Keystroke
     *
     * @param c        - component recieving the stroke
     * @param itemName - name we associate with the action
     * @param TheKey   - Keystroke to assoriate
     */
    protected void setKeyBoardAction(JComponent c, String ItemName, KeyStroke TheKey) {
        // Make lookup name
        String testName = DefaultFrame.conditionMenuName(ItemName);
        // Look up the action by name
        Action act = getDefaultFrame().getAction(testName);
        if (act != null)
            // NOTE register the action with the component
            // NOTE I wanted to have this work on the JFrame but a JFrame is NOT
            // a JComponent
            // This Line does all the work
            c.registerKeyboardAction(act, TheKey, JComponent.WHEN_FOCUSED);
    }


    /**
     * Adds the action indexed by ItemName to the TheMenu
     *
     * @param TheMenu  - menu to add
     * @param ItemName - key used to look up the action
     */
    protected void setActionMenuItem(JMenu TheMenu, String ItemName) {
        String testName = DefaultFrame.conditionMenuName(ItemName);
        Action act = getDefaultFrame().getAction(testName);
        if (act != null)
            // NOTE this will construct a menu item
            TheMenu.add(act);
    }

    /**
     Builds a Color menu
     */
//     protected JMenu buildColorMenu()
//     {
//         JMenu ret = new JMenu("Colors");
//         for(int i = 0; i < BackColorNames.length; i++)
//             buildBackColorMenuItem(ret,i);
//
//         setActionMenuItem(ret,"Back Color ...");
//         ret.add(new JSeparator());
//
//         for(int i = 0; i < TextColorNames.length; i++)
//             buildTextColorMenuItem(ret,i);
//
//         setActionMenuItem(ret,"Text Color ...");
//
//         return(ret);
//     }
//
    /**
     Builds a menu for Text Color
     */
//     protected JMenuItem buildTextColorMenuItem(JMenu TheMenu,int i)
//     {
//         JMenuItem ret = new JMenuItem(TextColorNames[i]);
//         ret.setForeground(TextColors[i]);
//
//         // Note we multiplex one action so we cannot add an action to the
//         // menu
//         ret.addActionListener(new SetForeGroundAction());
//         TheMenu.add(ret);
//         return(ret);
//     }
//
//     /**
//       Builds a menu for Background Color
//     */
//     protected JMenuItem buildBackColorMenuItem(JMenu TheMenu,int i)
//     {
//         JMenuItem ret = new JMenuItem(BackColorNames[i]);
//         ret.setBackground(BackColors[i]);
//         // Note we multiplex one action so we cannot add an action to the
//         // menu
//         ret.addActionListener(new SetBackGroundAction());
//         TheMenu.add(ret);
//         return(ret);
//     }

    /**
     * Builds a tool bar
     */
    protected JToolBar createToolBar() {
        JToolBar ret = new JToolBar();
        addToolBarActionItem(ret, "New");
        addToolBarActionItem(ret, "Open"); // ...");
        addToolBarActionItem(ret, "Save");
        addToolBarActionItem(ret, "Cut");
        addToolBarActionItem(ret, "Copy");
        addToolBarActionItem(ret, "Paste");
        addToolBarActionItem(ret, "Back Color ...");
        addToolBarActionItem(ret, "Text Color ...");
        return (ret);
    }

    /**
     * Adds the action indexed by ItemName to the TheBar
     *
     * @param TheBar   - Toolbar to add
     * @param ItemName - key used to look up the action
     */
    protected void addToolBarActionItem(JToolBar TheBar, String ItemName) {
        String testName = DefaultFrame.conditionMenuName(ItemName);
        Action act = getDefaultFrame().getAction(testName);
        if (act != null)
            // NOTE this will construct a toolbar button
            TheBar.add(act);
    }

    /**
     Action for all menu items which set background
     This transfers the background color of the source (menu item)
     to the Text Field
     */
//     public class SetBackGroundAction extends AbstractAction {
//         public void actionPerformed(ActionEvent ev) {
//             setBackgrouDefaultolor(((Component)ev.getSourceId()).getBackground());
//         }
//     }
    /**
     Action for all menu items which set foreground
     This transfers the foreground color of the source (menu item)
     to the Text Field
     */
//    public class SetForeGroundAction extends AbstractAction {
//         public void actionPerformed(ActionEvent ev) {
//             setTextColor(((Component)ev.getSourceId()).getForeground());
//         }
//     }

    /**
     * Handler for Open menu item - uses JFileChooser to
     */
    public class OpenFileAction extends WrappedAction {
        public OpenFileAction() {
            super("Open");
            putValue(Action.NAME, ResourceString.getStringFromText("Open") + "...");
            putValue(REAL_NAME, "Open");
            setEnabled(false);
        }

    }

    /**
     * Handler for Open menu item - uses JFileChooser to
     */
    public class CloseFileAction extends WrappedAction {
        public CloseFileAction() {
            super(fromResource("Close"));
            putValue(Action.NAME, ResourceString.getStringFromText("Close"));
            putValue(REAL_NAME, "Close");
            setEnabled(false);
        }

    }
    /**
     * Handler for Open menu item - uses JFileChooser to
     */
    public class DiscardAction extends WrappedAction {
        public DiscardAction() {
            super(fromResource("Discard Changes"));
            putValue(Action.NAME, ResourceString.getStringFromText("Discard Changes"));
            putValue(REAL_NAME, "discard_changes");
            setEnabled(false);
        }

    }

    /**
      * Handler for Mail menu item -
      */
     public class NewAction extends WrappedAction {
         public NewAction() {
             super("New");
             putValue(Action.NAME, ResourceString.getStringFromText("New"));
             putValue(REAL_NAME, "New");
             setEnabled(false);
         }


     }
    /**
      * Handler for mailing data set
      */
     public class MailAction extends WrappedAction {
         public MailAction() {
             super("Upload");
             putValue(Action.NAME, ResourceString.getStringFromText("Upload"));
             putValue(REAL_NAME, "Upload");
             setEnabled(false);
         }


     }

    /**
     * Handler for close data set 
     */
    public class CloseAction extends WrappedAction {
        public CloseAction() {
            super("Close");
            putValue(Action.NAME, ResourceString.getStringFromText("Close"));
            putValue(REAL_NAME, "Close");
            setEnabled(false);
        }

    }

    /**
     * Handler for Save menu item - uses JFileChooser to
     */
    public class SaveAction extends WrappedAction {
        public SaveAction() {
            super("Save");
            putValue(Action.NAME, ResourceString.getStringFromText("Save"));
            putValue(REAL_NAME, "Save");
            setEnabled(false);
        }

    }

    /**
     * Handler for Save As menu item - uses JFileChooser to
     */
    public class SaveAsAction extends WrappedAction {
        public SaveAsAction() {
            super("Save As");
            putValue(Action.NAME, ResourceString.getStringFromText("Save As") + "...");
            putValue(REAL_NAME, "Save As" );
            setEnabled(false);
        }
    }
    /**
     * Handler for Save As menu item - uses JFileChooser to
     */
    public class UndoAction extends WrappedAction {
        public UndoAction() {
            super("Undo");
            putValue(Action.NAME, ResourceString.getStringFromText("Undo") );
            putValue(REAL_NAME, "Undo" );
            setEnabled(false);
        }
    }
    /**
     * Handler for Save As menu item - uses JFileChooser to
     */
    public class RedoAction extends WrappedAction {
        public RedoAction() {
            super("Redo");
            putValue(Action.NAME, ResourceString.getStringFromText("Redo")  );
            putValue(REAL_NAME, "Redo" );
            setEnabled(false);
        }
    }

    /**
     * Action to exit - a better version would ask the user
     * if he wants to save text
     */
    public class ExitAction extends WrappedAction {
        public ExitAction() {
            super("Exit");
            putValue(Action.NAME, ResourceString.getStringFromText("Exit"));
            putValue(WrappedAction.REAL_NAME, "Exit");
            setRealAction(new DoExitAction());
            setEnabled(true);
        }
    }

    public class DoExitAction extends AbstractAction {
        public DoExitAction() {
            super("Exit");
            putValue(Action.NAME, ResourceString.getStringFromText("Exit"));
            putValue(WrappedAction.REAL_NAME, "Exit");
            setEnabled(true);
        }

        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e) {
            System.exit(0);

        }
    }

    /**
     * Action to cut - all real work is done by the
     * text area
     */
    public class CutAction extends WrappedAction {
        public CutAction() {
            super(fromResource("Cut"));
            putValue(Action.NAME, ResourceString.getStringFromText("Cut"));
        }

        public void actionPerformed(ActionEvent ev) {
            //  m_Text.cut();
            throw new UnsupportedOperationException("Fix This"); // ToDO Fix Thix
        }
    }

    /**
     * Action to cut - all real work is done by the
     * text area
     */
    public class AboutAction extends WrappedAction {
        public AboutAction() {
            super(fromResource("About"));
            putValue(Action.NAME, ResourceString.getStringFromText("About") + "...");
            putValue(WrappedAction.REAL_NAME, "About");
        }

        public void actionPerformed(ActionEvent ev) {
            Action action = getRealAction();
            if(action != null)  {
                action.actionPerformed(ev );
                return;
            }
            DefaultFrame DefaultFrame = getDefaultFrame();
            DefaultAboutDialog da = new DefaultAboutDialog(DefaultFrame,
                    new File(DefaultAboutDialog.DEFAULT_IMAGES_DIRECTORY),
                    new File(DefaultAboutDialog.DEFAULT_BACKGROUND_IMAGE));
            da.setVisible(true);
        }

//        /**
//         * Returns the enabled state of the <code>Action</code>. When enabled,
//         * any component associated with this object is active and
//         * able to fire this object's <code>actionPerformed</code> method.
//         *
//         * @return true if this <code>Action</code> is enabled
//         */
//        public boolean isEnabled() {
//            return true;
//
//        }
    }

    /**
     * Action to copy - all real work is done by the
     * text area
     */
    public class CopyAction extends WrappedAction {
        public CopyAction() {
            super(fromResource("Copy"));
            putValue(Action.NAME, "Copy");
        }

    }

    /**
     * Action to paste - all real work is done by the
     * text area
     */
    public class PasteAction extends WrappedAction {
        public PasteAction() {
            super(fromResource("Paste"));
            putValue(Action.NAME, "Paste");
        }

    }


    /**
     * Action to select all text - all real work is done by the
     * text area
     */
    public class SelectAllAction extends WrappedAction {
        public SelectAllAction() {
            super(fromResource("Select All"));
            putValue(Action.NAME, "Select All");
        }

    }

    /**
     * Pops up a JColorChooser dialog to allow the user to
     * select a text color
     */
    public class ChooseTextColorAction extends WrappedAction {

    }

    /**
     * Pops up a JColorChooser dialog to allow the user to
     * select a background color
     */
    public class ChooseBackColorAction extends WrappedAction {

    }


    /**
     * This class implements th Icon interface to draw a square of
     * color witha black border
     */
    public static class ColorIcon implements Icon {
        Color m_Color;

        public ColorIcon(Color c) {
            m_Color = c;
        }

        public Color getColor() {
            return (m_Color);
        }

        //
        // Really ought to fire a property change
        public void setColor(Color in) {
            if (m_Color != null && m_Color.equals(in))
                return;
            m_Color = in;
        }

        public static final int COLOR_ICON_HEIGHT = 24;
        public static final int COLOR_ICON_WIDTH = 24;

        public int getIconHeight() {
            return (COLOR_ICON_HEIGHT);
        }

        public int getIconWidth() {
            return (COLOR_ICON_WIDTH);
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Color oldColor = g.getColor();
            g.setColor(m_Color);
            g.fillRect(x, y, COLOR_ICON_WIDTH, COLOR_ICON_HEIGHT);
            g.setColor(Color.black);
            g.drawRect(x, y, COLOR_ICON_WIDTH, COLOR_ICON_HEIGHT);

            g.setColor(oldColor);
        }

    }

    /**
     * A utility function use JFileChooser to select an existing file to
     * open
     *
     * @param Directory - starting directory
     * @return - selected file or null if no selection
     */
    public static File chooseOpenFile(File Directory,String[] extensions) {

        return (chooseOpenFile(Directory,new SwingExtensionFileFilter(extensions)));
    }

    /**
     * A utility function use JFileChooser to select an existing file to
     * open
     *
     * @param Directory - starting directory
     * @return - selected file or null if no selection
     */
    public static File chooseOpenFile(File Directory) {

        return (chooseOpenFile(Directory,(javax.swing.filechooser.FileFilter)null));
    }


    /**
     * A utility function use JFileChooser to select an existing file to
     * open
     *
     * @param Directory - starting directory
     * @return - selected file or null if no selection
     */
    public static File chooseOpenFile(File Directory,javax.swing.filechooser.FileFilter filter) {
        JFileChooser chooser = new JFileChooser();

        chooser.setDialogTitle("Select File to Open");
        // type is file open
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        // set starting directory
        chooser.setCurrentDirectory(Directory);
        chooser.setFileFilter(filter);
         // actually run the dialog
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return (chooser.getSelectedFile());
        }
        return (null);
    }


    /**
     * A utility function use JFileChooser to select a file for output
     *
     * @return - selected file or null if no selection
     */
    public static File chooseSaveFile(File StartFile) {
        File userDir = new File(System.getProperty("user.dir"));
        return chooseSaveFile(StartFile,userDir);
    }

    /**
     * A utility function use JFileChooser to select a file for output
     *
     * @return - selected file or null if no selection
     */
    public static File chooseSaveFile(File StartFile,File userDir) {

    JFileChooser chooser = new JFileChooser();

     String saveStr = fromResource("Select File to Save");
     chooser.setDialogTitle(saveStr);
     chooser.setDialogType(JFileChooser.SAVE_DIALOG);
     // set starting directory
     chooser.setCurrentDirectory(userDir);
     // set starting File
     chooser.setSelectedFile(StartFile);

     int returnVal = chooser.showSaveDialog(null);
     if (returnVal == JFileChooser.APPROVE_OPTION) {
         return (chooser.getSelectedFile());
     }
     return (null);
 }


    /**
     * A utility function to read a file as a string. The
     * use of getResource looks relative to the clas of the
     * Object and allows the text to be placed ina jar file
     *
     * @param me   - Object needed for class
     * @param name - name of the file
     * @return - text in the file
     */
    public static String getFileText(File theFile) {
        StringBuffer holder = new StringBuffer(2048);
        try {
            FileInputStream TheFile = new FileInputStream(theFile);
            int c = TheFile.read();
            while (c != -1) {
                holder.append((char) c);
                c = TheFile.read();
                // ought to look at non-printing chars
            }
            TheFile.close();
        }
        catch (IOException e) {
        }

        return (holder.toString());
    }

    /**
     * A utility function store text in a file designated by out
     *
     * @param out  - output file
     * @param text - text to save
     */
    public static void saveFileText(File out, String text) {
        try {
            FileOutputStream TheFile = new FileOutputStream(out);
            PrintStream RealOut = new PrintStream(TheFile);
            RealOut.print(text);
            RealOut.close();
        }
        catch (IOException e) {
        }

    }


    public void paint(Graphics g) {
        super.paint(g);
        // repaint(10000); // sometines this does not redraw
    }


    public ResourceBundle getResources() {
        return getDefaultFrame().getResources();
    }

    public String getResourceString(String nm) {
        return getDefaultFrame().getResourceString(nm);
    }

    public Object getResourceObject(String nm) {
        return getDefaultFrame().getResourceObject(nm);
    }

}