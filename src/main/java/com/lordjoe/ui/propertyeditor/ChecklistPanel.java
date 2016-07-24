package com.lordjoe.ui.propertyeditor;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.lordjoe.ui.general.IProblemHandler;
import com.lordjoe.ui.general.ProblemChangeListener;
import com.lordjoe.utilities.ResourceString;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;


/**
 * com.lordjoe.ui.propertyeditor.PropertiesEditorPanel
 *
 * @author slewis
 * @date Apr 21, 2005
 */
public class ChecklistPanel extends JPanel implements
        ItemListener,IProblemHandler
{
    public static final Class THIS_CLASS = ChecklistPanel.class;
    public static final ChecklistPanel EMPTY_ARRAY[] = {};
    public static final int LABEL_COLUMN = 2;
    public static final int EDITOR_COLUMN = LABEL_COLUMN + 2;
    public static final int UNITS_COLUMN = EDITOR_COLUMN + 2;

    private final String[] m_Properties;
    private final JCheckBox[] m_CheckBoxes;
    private final List<ProblemChangeListener> m_Listeners;
    private boolean m_NotifyAllChechecked;


    public ChecklistPanel(String[] props)
    {
        m_Properties = props;
        m_CheckBoxes = buildDisplay();
        m_Listeners = new ArrayList<ProblemChangeListener>();
     }

    public boolean isNoProblems()
    {
        return  getProblems() == null;
    }

    public String getProblems()
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < m_CheckBoxes.length; i++)
        {
            JCheckBox cb = m_CheckBoxes[i];
            if(!cb.isSelected()) {
                if(sb.length() > 0)
                    sb.append("\n");
                sb.append(m_Properties[i]);
            }


        }
        if(sb.length() == 0)
            return null;
        return sb.toString();
    }

    protected JCheckBox[] buildDisplay()
    {
        String cl = layoutColumnsString();
        String rl = layoutRowString();
         FormLayout layout = new FormLayout(cl,rl);
        setLayout(layout);
        List<JCheckBox> holder = new ArrayList<JCheckBox>();
        for (int i = 0; i < m_Properties.length; i++)
        {
            JCheckBox theItem = buildPropertyEditor(m_Properties[i],i);
            holder.add(theItem);
            theItem.addItemListener(this);
        }
        JCheckBox[] ret = new JCheckBox[holder.size()];
        holder.toArray(ret);
        return ret;
    }

    protected JCheckBox buildPropertyEditor(String propName,int row)
    {
        String labelStr = ResourceString.parameterToText(propName);
        JLabel lbl = new JLabel(labelStr);
        JLabel units = null;
         JCheckBox editor = new JCheckBox();

        CellConstraints cc = new CellConstraints();
        int layoutRow = (2 * row) + 1;
        add(lbl,cc.xy(LABEL_COLUMN,layoutRow));
        JComponent editorComponent = (JComponent)editor;
        if(units == null) {
            add(editorComponent,cc.xyw(EDITOR_COLUMN,layoutRow,7));
        }
        else {

            add(editorComponent,cc.xyw(EDITOR_COLUMN,layoutRow,2));
            add(units,cc.xy(UNITS_COLUMN,layoutRow));
        }
        return editor;
    }
    protected String layoutColumnsString()
     {
         return "4dlu,pref, 4dlu, 50dlu, 4dlu, 50dlu, 4dlu, 10dlu, 2dlu, 10dlu";
     }

    protected String layoutRowString()
    {
        StringBuffer sb = new StringBuffer();
        int nrows = m_Properties.length;
        sb.append("p");
        for(int i = 0; i < nrows; i++) {
           if(i < nrows - 1)
              sb.append(", 2dlu");
            sb.append(",p");
        }
        return sb.toString();
    }

    /**
     * Returns the selected items or <code>null</code> if no
     * items are selected.
     */
    public Object[] getSelectedObjects()
    {

        if(this.isNoProblems())  {
            Object[] ret = { this };
            return ret;
        }
        else {
            Object[] ret = {  };
             return ret;
         }

    }

    /**
     * Adds a listener to receive item events when
     * problem state is altered
     * @param l the listener to receive events
     * @see java.awt.event.ItemEvent
     */
    public void addProblemChangeListener(ProblemChangeListener added)
    {
        synchronized (m_Listeners)
         {
             m_Listeners.add(added);
         }
     }

    /**
     * Removes an ProblemChangeListener
     * If <code>l</code> is <code>null</code>,
     * no exception is thrown and no action is performed.
     *
     * @param l the listener being removed
     * @see java.awt.event.ItemEvent
     */
    public void removeProblemChangeListener(ProblemChangeListener removed)
    {
        synchronized (m_Listeners)
           {
               m_Listeners.remove(removed);
           }
    }

    /**
     * Invoked when an item has been selected or deselected by the user.
     * The code written for this method performs the operations
     * that need to occur when an item is selected (or deselected).
     */
    public void itemStateChanged(ItemEvent e)
    {
        boolean allChecked = isNoProblems();
        if(m_NotifyAllChechecked && !allChecked) {
                notifyProblemListeners();
              m_NotifyAllChechecked = false;
             return;
         }
        if(!m_NotifyAllChechecked && allChecked) {
                notifyProblemListeners();
              m_NotifyAllChechecked = true;
             return;
         }
      }

    public void notifyProblemListeners()
     {
         if (m_Listeners.isEmpty())
             return;
         ProblemChangeListener[] listeners = null;
         synchronized (m_Listeners)
         {
             listeners = new ProblemChangeListener[m_Listeners.size()];
             m_Listeners.toArray(listeners);
         }
           for (int i = 0; i < listeners.length; i++)
         {
             ProblemChangeListener listener = listeners[i];
              listener.onProblemChange(this);
         }

     }

}
