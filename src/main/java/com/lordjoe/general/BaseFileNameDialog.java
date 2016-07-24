package com.lordjoe.general;

import com.jgoodies.forms.layout.*;
import com.lordjoe.ui.*;
import com.lordjoe.utilities.*;
import com.lordjoe.ui.general.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * com.lordjoe.general.BaseFileNameDialog
 *
 * @author Steve Lewis
 * @date Oct 12, 2007
 */
public class BaseFileNameDialog extends JDialog
{
    public static BaseFileNameDialog[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = BaseFileNameDialog.class;

    public static File getPreferredDirectoryAndName( File base,String endStr)
    {
        return getPreferredDirectoryAndName(DefaultFrame.getCurrentFrame(),base,endStr);
    }


    public static File getPreferredDirectoryAndName( JFrame parent,File base,String endStr)
    {
        BaseFileNameDialog fb = new BaseFileNameDialog(parent,base,endStr);
        fb.setVisible(true);
        return fb.getSelectedFile();
    }

    private File m_BaseDir;
    private File m_SelectedFile;
    private String m_BaseName;
    private FileAcceptTextField m_BaseDirDisplay;
    private JSingleLineTextField m_NameDisplay;

    /**
     * Creates a non-modal dialog without a title with the
     * specified <code>Frame</code> as its owner.  If <code>owner</code>
     * is <code>null</code>, a shared, hidden frame will be set as the
     * owner of the dialog.
     * <p/>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @param owner the <code>Frame</code> from which the dialog is displayed
     * @throws java.awt.HeadlessException if GraphicsEnvironment.isHeadless()
     *                                    returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see javax.swing.JComponent#getDefaultLocale
     */
    public BaseFileNameDialog(JFrame parent,File baseName, String endString)
            throws HeadlessException
    {
        super(parent, true);
        setTitle("Specify Directory and Name Start");
        m_BaseDir = baseName.getParentFile();
        m_BaseName = baseName.getName().replace(endString, "");
        buildUI();
    }

    protected void buildUI()
    {
        setLayout(new BorderLayout(20,20));
        JPanel jp = new JPanel();
        add(jp, BorderLayout.CENTER);
         FormLayout layout = new FormLayout("5dlu , pref,  4dlu, 300dlu, 4dlu, pref,5dlu", // columns
            "5dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref"); // rows
         layout.setRowGroups(new int[][]{{1,3,5}});
        jp.setLayout(layout);
        CellConstraints cc = new CellConstraints();
        JLabel jLabel = new JLabel("Directory");
        jp.add(jLabel, cc.xy (2, 2));
        m_BaseDirDisplay = new FileAcceptTextField(50);
        m_BaseDirDisplay.setDirectoryOnly(true);
        m_BaseDirDisplay.setFile(m_BaseDir);
        jp.add(m_BaseDirDisplay, cc.xy(4,2));

        jp.add(new JButton(new BrowseAction()), cc.xy(6,2));

        JLabel jLabel2 = new JLabel("Name Base");
        jp.add(jLabel2, cc.xy (2,4));
        m_NameDisplay = new JSingleLineTextField(30);
        m_NameDisplay.setText(m_BaseName);
        jp.add(m_NameDisplay, cc.xy(4, 4));

        jp.add(new JButton(new CopyTextAction(m_NameDisplay)), cc.xy(6,4));

        jp = new JPanel();
        add(jp, BorderLayout.SOUTH);
        jp.add(new JButton(new OKAction()));
        jp.add(new JButton(new CancelAction()));
 
        pack();
    }

    public File getSelectedFile()
    {
        return m_SelectedFile;
     }

    // Action of the browse button is to bring up a JFileChooser
    // and select a file
    public class OKAction extends AbstractAction
    {

        public OKAction()
        {
            this.putValue(Action.NAME,  "OK");
        }

        /**
     * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e)
        {
            File arent = m_BaseDirDisplay.getFile();
             if(arent == null || arent.exists() || !arent.isDirectory())
                 m_SelectedFile = null;
             String name = m_NameDisplay.getText();
             if(Util.isEmptyString(name))
                 m_SelectedFile = null;
             m_SelectedFile =  new File(arent, name);
            setVisible(false);
        }
    }
    // Action of the browse button is to bring up a JFileChooser
    // and select a file
    public class CancelAction extends AbstractAction
    {

        public CancelAction()
        {
            this.putValue(Action.NAME,  "Cancel");
        }

        /**
     * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e)
        {
            setVisible(false);
        }
    }

    //
    // Action of the browse button is to bring up a JFileChooser
    // and select a file
    public class BrowseAction extends AbstractAction
    {
        private String m_LastPath = System.getProperty("user.dir") + "/plans";

        public BrowseAction()
        {
            this.putValue(Action.NAME,  "...");
        }

        public void actionPerformed(ActionEvent ev)
        {

            JFileChooser chooser = new JFileChooser();

            chooser.setDialogTitle("Choose Base Directory");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setCurrentDirectory(m_BaseDir);
          // Do the dialog
            int returnVal = chooser.showOpenDialog(BaseFileNameDialog.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File Selection = chooser.getSelectedFile();
                m_BaseDirDisplay.setFile(Selection);
                m_BaseDir = Selection;
            }
            BaseFileNameDialog.this.toFront();

        }
        // end class BrowseAction
    }

    public static void main(String[] args)
    {
        String basrDir = "//main/public/Work/NDC-B016/NDC-B016-C20/CVclean";
        File baseDir = new File(basrDir);
        File test = new File(basrDir,"B016-C20_L3_A_10");
        JFrame foo = new JFrame("Foo");
        foo.setVisible(true);
        File fl = getPreferredDirectoryAndName(foo,test,"_A_10");

    }
}
