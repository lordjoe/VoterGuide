/**
 * PathSelector - Swing Style
 */
package com.lordjoe.ui.general;
import com.lordjoe.utilities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.*;
import java.util.List;
/* Stock boilerplate applet - action is in
        PathSelectorPanel inner class
        */
public class FileSelectorPanel extends JPanel
{
    private JTextField    m_FileName;
    private JButton       m_Browse;
    private String        m_LastPath; // Last directory visited
    private String        m_description;
    private int           m_mode = JFileChooser.FILES_ONLY;
    private final List<String>        m_Extensions;
    
    public FileSelectorPanel() {
        m_FileName = new JTextField(30);
        add(m_FileName);
        m_Browse = new JButton("...");
        add(m_Browse);
        m_Browse.addActionListener(new BrowseAction());
        
        m_LastPath = System.getProperty("user.dir");
        m_description = "";
        m_Extensions = new ArrayList<String>();
    }
    
    public String getText() {
        return(m_FileName.getText());
    }
    
    public void setText(String in) {
        setFile(in);
    }
 
    public void setFile(String in) {
        m_FileName.setText(in);
        // set Last Directory for next look 
        File test = new File(in);
        if(test.exists()) {
            if(test.isDirectory()) // directory set it
                m_LastPath = test.getPath();
            else                   // file - use parent directory
                m_LastPath = test.getParent();
        }
    }
    
    public void setDescription(String mode) {
        m_description = mode;
    }
    
    public void setFileSelectionMode(int mode) {
        m_mode = mode;
    }
    
    public void setExtensions(String in) {
        m_Extensions.clear();
        StringTokenizer tk = new StringTokenizer(in,",");
        while(tk.hasMoreTokens())
            m_Extensions.add(tk.nextToken());
    }
    
    //
    // Action of the browse button is to bring up a JFileChooser
    // and select a file
    public class BrowseAction extends AbstractAction {


        public void actionPerformed(ActionEvent ev) {
            Frame DialogFrame;
            Container FrameQ = getTopLevelAncestor();
            if(FrameQ instanceof Frame) 
                  DialogFrame = (Frame)FrameQ;
            else 
                  DialogFrame = new Frame();
                  
            JFileChooser chooser = new JFileChooser();

            String titleStr = ResourceString.getStringFromText("Add to Class Path");
            chooser.setDialogTitle(titleStr);
            chooser.setFileSelectionMode(m_mode);

            String[] extensions = Util.collectionToStringArray(m_Extensions);
            SwingExtensionFileFilter filter = new SwingExtensionFileFilter(extensions);
            if(m_description.length() > 0)
                filter.setDescription(m_description);
            chooser.setFileFilter(filter); 
            // Start in last directory if possible
            if(m_LastPath.length() > 0) {
                File testDir = new File(m_LastPath);
                if(testDir.exists() && testDir.isDirectory()) {
                    chooser.setCurrentDirectory(testDir);
                }
            }
            // Do the dialog
            int returnVal = chooser.showOpenDialog(DialogFrame);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
               File Selection = chooser.getSelectedFile();
               setFile(Selection.getPath());
            }
            if(DialogFrame == getTopLevelAncestor())
                DialogFrame.toFront();
            
     }
    // end class BrowseAction
    }
// end class FileSelectorPanel
}