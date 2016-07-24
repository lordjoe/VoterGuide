package com.lordjoe.runner.ui;


import com.lordjoe.utilities.*;
import com.lordjoe.runner.*;

import javax.swing.*;
import java.awt.*;

/**
 * com.lordjoe.runner.ui.HighLevelCommandRunnerPanel
 *
 * @author Steve Lewis
 * @date Feb 26, 2007
 */
public class HighLevelCommandRunnerPanel extends JPanel
{
    public static final Class THIS_CLASS = HighLevelCommandRunnerPanel.class;
    public static final HighLevelCommandRunnerPanel EMPTY_ARRAY[] = {};
    public static final String TAB_NAME = "High Level Commands";


    private final JComponent m_MaintenancePanelParent;

    public HighLevelCommandRunnerPanel(JComponent parent)
    {
        super();
        m_MaintenancePanelParent = parent;
        String name = ResourceString.getStringFromText(TAB_NAME);
        setName(name);
        initDisplay();

    }

    public JComponent getMaintenancePanelParent()
    {
        return m_MaintenancePanelParent;
    }


    private void initDisplay()
    {
        int yIndex = 0;
        GridBagConstraints gc = null;
        Action[] actions = DeviceFactory.buildExposedActions();
        setLayout(new BorderLayout());

        JPanel upper = new JPanel();
        upper.setOpaque(false);
        upper.setLayout(new BorderLayout());
        JPanel access = new JPanel();
        access.setOpaque(false);

        upper.add(access, BorderLayout.NORTH);

        JPanel commandsPanel = new JPanel();
        commandsPanel.setOpaque(false);
          int nActions = actions.length;
        int rowLength = 5;
        int rows = 1 + (nActions / rowLength);
        int cols = rowLength;
        commandsPanel.setLayout(new GridLayout(rows, cols));
        for (int i = 0; i < nActions; i++) {
            Action action = actions[i];
            commandsPanel.add(new JButton(action));
        }
        upper.add(commandsPanel, BorderLayout.CENTER);

        add(upper, BorderLayout.NORTH);
    }

//    public ICommandStringHandler getHandler()
//    {
//        return m_Handler;
//    }

    public static final String EXPOSED_OPERATIONS = "GateRedy:Gate Ready;" +
            "GateNRdy:Gate Not Ready";


}
