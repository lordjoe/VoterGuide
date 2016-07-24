package com.lordjoe.ui.general;

import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;


/**
 * com.lordjoe.ui.general.ActionPanel
 *
 * @author slewis
 * @date Apr 21, 2005
 */
public class ActionPanel  extends SubjectDisplayPanel
{
    public static final Class THIS_CLASS = ActionPanel.class;
    public static final ActionPanel EMPTY_ARRAY[] = {};

    private final IDisplay m_Parent;
    private final Action[] m_Actions;

    public ActionPanel(IDisplay parent,Action[] actions)
    {
        m_Actions = actions;
        m_Parent = parent;
        buildPanel();
    }

    protected void buildPanel()
    {
        FormLayout layout = new FormLayout(
                buildLayoutString(), "p");
        setLayout(layout);
        for(int i = 0; i < m_Actions.length; i++) {
            JActionButton button = new JActionButton(m_Actions[i]);
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
