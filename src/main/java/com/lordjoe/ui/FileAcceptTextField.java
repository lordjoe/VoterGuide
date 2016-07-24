package com.lordjoe.ui;

import com.lordjoe.utilities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * com.lordjoe.ui.FileAcceptTextField
 *
 * @author Steve Lewis
 * @date Oct 12, 2007
 */
public class FileAcceptTextField  extends JTextField implements FocusListener
{
    public static FileAcceptTextField[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = FileAcceptTextField.class;
    private static int gIndex = 0;

    private FileDropHandler m_Handler;
    private boolean m_DirectoryOnly;
    private boolean m_FileOnly;
    private String m_OldText;
    private int m_Index;
    /**
     * Constructs a new <code>TextField</code>.  A default model is created,
     * the initial string is <code>null</code>,
     * and the number of columns is set to 0.
     */
    public FileAcceptTextField()
    {
        m_Handler = new FileDropHandler(this);
        m_Index = gIndex++;
        addFocusListener(this);
      }

    /**
     * Constructs a new empty <code>TextField</code> with the specified
     * number of columns.
     * A default model is created and the initial string is set to
     * <code>null</code>.
     *
     * @param columns the number of columns to use to calculate
     *                the preferred width; if columns is set to zero, the
     *                preferred width will be whatever naturally results from
     *                the component implementation
     */
    public FileAcceptTextField(int columns)
    {
        super(columns);
        m_Handler = new FileDropHandler(this);
        m_Index = gIndex++;
        addFocusListener(this);
      }


    public void focusGained(FocusEvent e) {

    }

    public void focusLost(FocusEvent e) {
        String s = getText();
        if(s != null && !s.equals(m_OldText))  {
            m_OldText = s;
            int index = m_Index;
           fireActionPerformed();
        }
    }

 
    protected FileDropHandler getHandler()
    {
        return m_Handler;
    }

    public boolean isDirectoryOnly()
    {
        return getHandler().isDirectoryOnly();
    }

    public void setDirectoryOnly(boolean pDirectoryOnly)
    {
        getHandler().setDirectoryOnly(pDirectoryOnly);
    }

    public boolean isFileOnly()
    {
        return getHandler().isFileOnly();
    }

    public void setFileOnly(boolean pFileOnly)
    {
        getHandler().setFileOnly(pFileOnly);
    }

    public void setText(String t) {
        String old = getText();
        if(old != null && old.equals(t))
            return;
        m_OldText = old;
        if(t.length() > 60) {
            Util.breakHere();  // there is a bug in the code
        }

        super.setText(t);
        fireActionPerformed();

    }


    public void setFile(File fl)
    {
        if(fl == null)   {
            setText("");
            return;
        }
        String oldT = getText();
        String t = fl.getAbsolutePath();
        setText(t);
        if(!t.equals(oldT))
            fireActionPerformed();
    }

    public File getFile()
    {
        String s = getText();
        if(Util.isEmptyString(s))
            return null;
        return new File(s);
    }

    public static void main(String[] args)
    {
        JFrame jf = new JFrame();
        int fieldLangth = 30;
        jf.setTitle("Drag and Drop a File");
        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout());
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();

        /** this field accepts a file
         *
         */
        FileAcceptTextField acceptTextField = new FileAcceptTextField(fieldLangth);
        acceptTextField.setFileOnly(true);
        p2.add(new JLabel("File"));
        p2.add(acceptTextField);
        p1.add(p2, BorderLayout.NORTH);

        /**
         * this field accepts a directory
          */
        FileAcceptTextField acceptDir = new FileAcceptTextField(fieldLangth);
        acceptDir.setDirectoryOnly(true);
         p3.add(new JLabel("Directory"));
        p3.add(acceptDir);

        p1.add(p3, BorderLayout.SOUTH);
        jf.add(p1);
        jf.setSize(400,200);
        jf.setVisible(true);
    }
}
