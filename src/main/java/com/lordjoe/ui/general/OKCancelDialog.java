package com.lordjoe.ui.general;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 * com.lordjoe.ui.general.OKCancelDialog
 *
 * @author Steve Lewis
 * @date Sep 2, 2005
 */
public abstract class OKCancelDialog extends JDialog
{
    // My guess for room to allow for a title bar
    public static final int WINDOW_TITLE_SIZE = 32;
    public static final int WINDOW_BORDER_SIZE = 16;

    // PRIVATE //
    private final WrappedAction m_OKAction;
    private final JPanel m_Commands;
    private JComponent m_Component;
    private JButton m_OKButton;

    public OKCancelDialog(String aTitle, JFrame aParent)
    {
        super(aParent, aTitle);
        setLayout(new BorderLayout());
        m_Commands = new JPanel();
        m_OKAction = new OKAction();
        m_OKButton = new JButton(m_OKAction);
        m_Commands.add(m_OKButton);
        m_Commands.add(new JButton(new CancelAction()));
        add(m_Commands, BorderLayout.SOUTH);
    }

    public void setComponent(JComponent added)
    {
        m_Component = added;
        add(added, BorderLayout.CENTER);
    }

    protected JPanel getCommands()
    {
        return m_Commands;
    }

    protected JComponent getComponent()
    {
        return m_Component;
    }

    protected JButton getOKButton()
    {
        return m_OKButton;
    }

    public void normalizesSize()
    {
        Dimension mySize = buildMySize();
        setSize(mySize);
    }

    protected Dimension buildMySize()
    {
        Insets ins = this.getInsets();
        JPanel commands = getCommands();
        Point loc = commands.getLocation();
        Dimension commandSize = commands.getPreferredSize();
        JComponent component = getComponent();
        Dimension componentSize = component.getPreferredSize();
        JButton okButton = getOKButton();
        Dimension buttonSize = okButton.getPreferredSize();
        int cmdHeight = Math.max(commandSize.height,buttonSize.height + 10);
        Dimension mySize = new Dimension(
              WINDOW_BORDER_SIZE + (int)loc.getX() + ins.left + ins.right + Math.max(commandSize.width, componentSize.width),
              WINDOW_TITLE_SIZE +  (int)loc.getY() + ins.top + ins.bottom + cmdHeight + componentSize.height);
        return mySize;
    }


    public void setRealAction(AbstractAction realAction)
    {
        getOKAction().setRealAction(realAction);
    }

    public WrappedAction getOKAction()
    {
        return m_OKAction;
    }


    private class OKAction extends WrappedAction
    {
        public OKAction()
        {
            putValue(Action.NAME, "OK");
        }

        public void actionPerformed(ActionEvent e)
        {
            setVisible(false);
            super.actionPerformed(e);

        }
    }

    private class CancelAction extends AbstractAction
    {
        public CancelAction()
        {
            putValue(Action.NAME, "Cancel");
        }

        public void actionPerformed(ActionEvent e)
        {
            setVisible(false);
            dispose();
        }
    }
}
