package com.lordjoe.ui;

//import com.jgoodies.forms.layout.*;

import com.lordjoe.utilities.*;

import javax.swing.*;

/**
 * com.lordjoe.runner.ui.ActionPanel
 *
 * @author Steve Lewis
 * @date Feb 26, 2007
 */
public class ActionPanel extends JPanel
{
    public static ActionPanel[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ActionPanel.class;
    private final JComponent m_Parent;
    private final Action[] m_Actions;

    public ActionPanel(JComponent parent,Action[] actions)
    {
        m_Actions = actions;
        m_Parent = parent;
        buildPanel();
    }

    protected void buildPanel()
    {
        if(true)
            throw new UnsupportedOperationException("Fix This"); // ToDo
//        FormLayout layout = new FormLayout(
//                buildLayoutString(), "p");
//        setLayout(layout);
        for(int i = 0; i < m_Actions.length; i++) {
            Action action = m_Actions[i];
            JActionButton button = new JActionButton(action.getValue(Action.NAME).toString(),action);
            String positionString = Integer.toString(2 * (i + 1)) + ",1";
            add(button,positionString);
        }
    }
    protected String buildLayoutString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("0:grow");
        for(int i = 0; i < m_Actions.length; i++) {
            sb.append(",p");
            if(i < m_Actions.length - 1)
                sb.append(", 4px"); // add spacing
        }
        return sb.toString();
    }



}
