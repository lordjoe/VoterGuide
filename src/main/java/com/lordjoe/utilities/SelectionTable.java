/**
 * SelectionTableTest - Swing Style
 */

package com.lordjoe.utilities;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

/* Stock boilerplate applet - action is in
        ApplicationTemplatePanel inner class
        */

public class SelectionTable extends JPanel implements  ListSelectionListener,
        ListDataListener
{
    private JPanel m_ListsPanel;
    private JPanel m_ButtonsPanel;
    private JToggleList  m_Posibilities;
    private JToggleList  m_Selections;
    
    private Action  m_ClearAction;
    private Action  m_SetAction;
    private Action  m_OKAction;
    private Action  m_RemoveAction;
    
    private NonReplaceListModel m_PosibilitiesData;
    private NonReplaceListModel m_SelectionsData;
    
    private ActionListener m_Listeners; // OK produces actions
    
     /*
        Methods for handling action listeners - 
        Action Events are generated when the selected button changes
        */
     public void addActionListener(ActionListener l) {
	   m_Listeners = AWTEventMulticaster.add(m_Listeners, l);
     }
     public void removeActionListener(ActionListener l) {
  	   m_Listeners = AWTEventMulticaster.remove(m_Listeners, l);
     }
     public void processEvent(ActionEvent e) {
         if (m_Listeners != null) {
             m_Listeners.actionPerformed(e);
         }         
    }
    
    /* 
        Method required by ListSelectionListener
    */
    public void valueChanged(ListSelectionEvent e) {
        testActions();
    }
    
    /* 
        Method required by ListDataListener
    */
    public void contentsChanged(ListDataEvent e) {
        testActions();
    }
    public void intervalAdded(ListDataEvent e) {
        testActions();
    }
    public void intervalRemoved(ListDataEvent e) {
        testActions();
    }


    
    protected SelectionTable getPanel() { return(this); } 

    public SelectionTable() {


        setLayout(new BorderLayout());
        m_ListsPanel = new JPanel();
        m_ListsPanel.setLayout(new GridLayout(1,2));
        add(m_ListsPanel,BorderLayout.CENTER);
        
        m_Posibilities = new JToggleList();
        // Override the model
        m_PosibilitiesData = new NonReplaceListModel();
        m_PosibilitiesData.addListDataListener(this);
        m_Posibilities.setModel(m_PosibilitiesData);
        // override look and feel 
      //  m_Posibilities.setUI(ToggleListUI.createUI(m_Posibilities));
        m_ListsPanel.add(new JScrollPane(m_Posibilities));
        m_Posibilities.addListSelectionListener(this); 
        
        m_Selections = new JToggleList();
        // Override the model
        m_SelectionsData = new NonReplaceListModel();
        m_SelectionsData.addListDataListener(this);
        m_Selections.setModel(m_SelectionsData);
        // override look and feel 
     //   m_Selections.setUI(ToggleListUI.createUI(m_Selections));
        m_ListsPanel.add(new JScrollPane(m_Selections));
        m_Selections.addListSelectionListener(this); 

        m_ButtonsPanel = new JPanel();
        m_ButtonsPanel.setLayout(new FlowLayout());
        add(m_ButtonsPanel,BorderLayout.SOUTH);
        JActionButton TheButton = new JActionButton("Add Selections");
        m_SetAction = new SetAction();
        TheButton.setAction(m_SetAction);
        m_ButtonsPanel.add(TheButton);

        TheButton = new JActionButton("Clear Selections");
        m_ClearAction = new ClearAction();
        TheButton.setAction(m_ClearAction);
        m_ButtonsPanel.add(TheButton);

        TheButton = new JActionButton("Remove");
        m_RemoveAction = new RemoveAction();
        TheButton.setAction(m_RemoveAction);
        m_ButtonsPanel.add(TheButton);

        TheButton = new JActionButton("OK");
        m_OKAction = new OKAction();
        TheButton.setAction(m_OKAction);
        m_ButtonsPanel.add(TheButton);

    }
    
    public void addPossibility(Object o) {
        m_PosibilitiesData.addElement(o);
    }
    
    
    public void addPossibilities(Object[] o) {        
        for(int i = 0; i < o.length; i++)
            m_PosibilitiesData.addElement(o[i]);
    }
    
    public void setPossibilities(Object[] newData) {
        m_PosibilitiesData.removeAllElements();
        addPossibilities(newData);
    }

    public String[] getSelections() {
        ListModel m = m_Selections.getModel();
        int n = m.getSize();
        String[] ret = new String[n];
        for(int i = 0; i < n; i++)
            ret[i] = m.getElementAt(i).toString();
        return(ret);
    }

    public void clearSelections() {
        m_SelectionsData.removeAllElements();
    }
    
    
    protected void testActions() {
        // to add selections we must have a selection
        m_SetAction.setEnabled(!m_Posibilities.isSelectionEmpty());
        // to clear selections we must have a selection
        m_ClearAction.setEnabled(!m_SelectionsData.isEmpty());
        // to act we must have a selection
        m_OKAction.setEnabled(!m_SelectionsData.isEmpty());
        // to remove selection we must have a selection
        m_RemoveAction.setEnabled(!m_Selections.isSelectionEmpty());
            
    }
    
    public class ClearAction extends AbstractAction {
        public ClearAction() {
            super("Clears the Selection List");
        }
        public void actionPerformed(ActionEvent e) {
            clearSelections();
        }
    }
    
    public class SetAction extends AbstractAction {
        
        public SetAction() {
            super("Puts selected possibilities in the Selection List");
        }
        
        public void actionPerformed(ActionEvent e) {
            int[] newIndices =  m_Posibilities.getSelectedIndices();
            
            // new data 
            for(int i = 0; i < newIndices.length; i++)
                m_SelectionsData.addElement(m_PosibilitiesData.getElementAt(newIndices[i]));
            
            // clear selections
            m_Posibilities.clearSelection();
        }
    }

    public class RemoveAction extends AbstractAction {
        public RemoveAction() {
            super("Removes selected Items from the possibilities List");
        }
        public void actionPerformed(ActionEvent e) {
            int n = m_SelectionsData.getSize();
            int[] newIndices =  m_Selections.getSelectedIndices();
            if(newIndices.length == n) {
                // all selected so clear
                m_SelectionsData.removeAllElements(); 
                return;
            }
            Object[] newData = new Object[newIndices.length];
             
            for(int i = 0; i < newIndices.length; i++) {
                newData[i] = m_SelectionsData.getElementAt(newIndices[i]);
            }   
            for(int i = 0; i < newData.length; i++) 
                m_SelectionsData.removeElement(newData[i]);
                
        }
    }

    public class OKAction extends AbstractAction {
        public OKAction() {
            super("Does intended action Selection List");
        }
        public void actionPerformed(ActionEvent e) {
            processEvent(new ActionEvent(getPanel(),0,null));
            testActions();
        }
    }

}


