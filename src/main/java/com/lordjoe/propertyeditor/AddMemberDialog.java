package com.lordjoe.propertyeditor;

import com.lordjoe.utilities.*;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.*;

/**
 * com.lordjoe.propertyeditor.AddMemberDialog
 *
 * @author Steve Lewis
 * @date Dec 20, 2007
 */
public class AddMemberDialog<T> extends JPanel {
    public static AddMemberDialog[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = AddMemberDialog.class;
    public static final int DEFAULT_HEIGHT = 300;
    public static final int DEFAULT_WIDTH = 500;

    private Object[] m_Selection;
    private final JList m_List;
    private final AddAction m_AddAction;
    private final CancelAction m_CancelAction;
    private JDialog m_Dialog;

    public AddMemberDialog(T[] options, boolean multipleAllowed, Component parent) {
        m_List = new JList(options);
        m_List.setSelectionMode(multipleAllowed ? ListSelectionModel.MULTIPLE_INTERVAL_SELECTION : ListSelectionModel.SINGLE_SELECTION);
        m_AddAction = new AddAction();
        m_CancelAction = new CancelAction();
        m_List.addListSelectionListener(m_AddAction);
        setLayout(new BorderLayout());
        add(new JScrollPane(m_List), BorderLayout.CENTER);
        JPanel jp = new JPanel();
        jp.add(new JButton(m_AddAction));
        jp.add(new JButton(m_CancelAction));
        add(jp, BorderLayout.SOUTH);
    }

    public T[] selectAddedItems() {
        m_Dialog = new JDialog();
        m_Dialog.setTitle("Add");
        m_Dialog.setLayout(new GridLayout(1, 1));
        m_Dialog.add(this);
        //   dlg.pack();
        m_Dialog.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        m_Dialog.setModal(true);
        if (SwingUtilities.isEventDispatchThread()) {
            m_Dialog.setVisible(true);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    m_Dialog.setVisible(true);
                }
            }
            );
        }
        m_Dialog = null;

        return (T[]) m_Selection;
    }

    private void closeDialog() {
        if (m_Dialog != null)
            m_Dialog.setVisible(false);
    }


    private class AddAction extends AbstractAction implements ListSelectionListener {
        private AddAction() {
            putValue(Action.NAME, ResourceString.parameterToText("Add"));
            setEnabled(false);
        }

        public void actionPerformed(ActionEvent e) {
            m_Selection = m_List.getSelectedValues();
            closeDialog();
        }

        public void valueChanged(ListSelectionEvent e) {
            Object[] selkections = m_List.getSelectedValues();
            setEnabled(selkections.length > 0);
        }
    }

    private class CancelAction extends AbstractAction {
        private CancelAction() {
            putValue(Action.NAME, ResourceString.getStringFromText("Cancel"));
        }

        public void actionPerformed(ActionEvent e) {

            closeDialog();
        }

    }

}
