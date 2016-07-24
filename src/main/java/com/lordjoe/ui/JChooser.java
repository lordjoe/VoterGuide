package com.lordjoe.ui;

import com.lordjoe.general.UIUtilities;
import com.lordjoe.utilities.ResourceString;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Array;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * com.lordjoe.ui.JChooser
 *  Generalized chooser control to present the user with a list of items and
 *    add to another list -
 * @author Steve Lewis
 * @date Jul 22, 2008
 */
public class JChooser<T> extends JPanel implements ItemSelectable
{
    public static JChooser[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = JChooser.class;


    private final DefaultListModel m_Sources;
    private final DefaultListModel m_Used;
    private final JList m_DragFrom;
    private final JList m_DragTo;

    private final JLabel m_FromLabel;
    private final JLabel m_ToLabel;
    private final DataFlavor m_Flavor;

    private final T[] m_Items;
    private final List<ItemListener> m_ItemListeners;

    public JChooser(T[] items)
    {
        this(items, (T[]) Array.newInstance(items.getClass().getComponentType(), 0));
    }

    public JChooser(T[] items, T[] selected)
    {
        super();
        String colorType = DataFlavor.javaJVMLocalObjectMimeType +
                           ";class=" + items.getClass().getComponentType().getName();
        try {
            m_Flavor = new DataFlavor(colorType);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("should never get here",e);
        }


        m_FromLabel = new JLabel("Drag from here:");
        m_ToLabel = new JLabel("Drop to MOVE to here:");
        m_ItemListeners = new CopyOnWriteArrayList<ItemListener>();
        m_Items = items;
        m_Sources = new DefaultListModel();
        for (int i = 0; i < items.length; i++) {
            T item = items[i];
            m_Sources.addElement(item);
        }
        m_Used = new DefaultListModel();
        for (int i = 0; i < selected.length; i++) {
            T item = selected[i];
            m_Used.addElement(item);
        }


        m_DragFrom = new JList(m_Sources);
        m_DragFrom.setTransferHandler(new FromTransferHandler());
        m_DragFrom.setPrototypeCellValue("List Item WWWWWW");
        m_DragFrom.setDragEnabled(true);
        m_DragFrom.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        m_DragTo = new JList(m_Used);
        m_DragTo.setPrototypeCellValue("List Item WWWWWW");
         m_DragTo.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        m_DragTo.setTransferHandler(new ToTransferHandler(TransferHandler.COPY));
        m_DragTo.setDropMode(DropMode.INSERT);

        init_components();
    }

    

    public void setFromLabel(String label)
    {
        m_FromLabel.setText(label);
    }

    public void setToLabel(String label)
    {
        m_ToLabel.setText(label);
    }

    protected void init_components()
    {
        setLayout(new BorderLayout());
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        m_FromLabel.setOpaque(false);
        m_FromLabel.setAlignmentX(0f);
        p.add(m_FromLabel);
        JScrollPane sp = new JScrollPane(m_DragFrom);
        sp.setAlignmentX(0f);
        p.add(sp);
        add(p, BorderLayout.WEST);
        p = new JPanel();
        p.setOpaque(false);

        JPanel p2 = new JPanel();
        p2.setOpaque(false);
        p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
        Action action = new MoveToAction();
        JButton button = buildActionButton(action);
         p2.add(button);

        action = new MoveFromAction();
        button = buildActionButton(action);
        p2.add(button);
        p.add(p2);

        add(p, BorderLayout.CENTER);

        p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        m_ToLabel.setOpaque(false);
        m_ToLabel.setAlignmentX(0f);
        p.add(m_ToLabel);
        sp = new JScrollPane(m_DragTo);
        sp.setAlignmentX(0f);
        p.add(sp);
        p.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
        add(p, BorderLayout.EAST);

        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

    }

    protected JButton buildActionButton(Action pAction)
    {
        JButton button;
        button = new JButton(pAction);
        button.setOpaque(false);
        Icon icon = button.getIcon();
        button.setPreferredSize(new Dimension(icon.getIconWidth(),icon.getIconHeight()));
        UIUtilities.registerComponent(button); // sign up for tooltips
        return button;
    }

    // Add to constructor


    /**
     * add a change listener
     * final to make sure this is not duplicated at multiple levels
     *
     * @param added non-null change listener
     */
    public final void addItemListener(ItemListener added)
    {
        if (!m_ItemListeners.contains(added))
            m_ItemListeners.add(added);
    }

    /**
     * remove a change listener
     *
     * @param removed non-null change listener
     */
    public final void removeItemListener(ItemListener removed)
    {
        while (m_ItemListeners.contains(removed))
            m_ItemListeners.remove(removed);
    }


    /**
     * notify any state change listeners - probably should
     * be protected but is in the interface to form an event cluster
     *
     * @param oldState
     * @param newState
     * @param commanded
     */
    public void notifyItemListeners(T value, boolean added)
    {
        if (m_ItemListeners.isEmpty())
            return;
        ItemEvent evt = new ItemEvent(this, 0, value, 0);  // todo fix
        for (ItemListener listener : m_ItemListeners) {
            listener.itemStateChanged(evt);
        }
    }


    public T[] getSelectedItems()
    {
        T[] ret = (T[]) Array.newInstance(m_Items.getClass().getComponentType(), m_Used.size());
        m_Used.copyInto(ret);
        return ret;
    }

    public void setUsed(T[] items)
    {
         m_Used.clear();
        for (int i = 0; i < items.length; i++) {
            T item = items[i];
            m_Used.addElement(item);
        }
    }


    public void setSources(T[] items)
    {
         m_Sources.clear();
        for (int i = 0; i < items.length; i++) {
            T item = items[i];
            m_Sources.addElement(item);
        }
    }

    /**
     * Returns the selected items or <code>null</code> if no
     * items are selected.
     */
    public Object[] getSelectedObjects()
    {
        return getSelectedItems();
    }

    private class FromTransferHandler extends TransferHandler
    {
        public int getSourceActions(JComponent comp)
        {
            return COPY_OR_MOVE;
        }

        private int index = 0;

        public Transferable createTransferable(JComponent comp)
        {
            index = m_DragFrom.getSelectedIndex();
            if (index < 0 || index >= m_Sources.getSize()) {
                return null;
            }

            return new ObjectDataFlavor<T>( getSelectedItems());
        }

        public void exportDone(JComponent comp, Transferable trans, int action)
        {
            if (action != MOVE) {
                return;
            }

            m_Sources.removeElementAt(index);
        }
    }

    private class ToTransferHandler extends TransferHandler
    {
        int action;

        public ToTransferHandler(int action)
        {
            this.action = action;
        }

        public boolean canImport(TransferHandler.TransferSupport support)
        {
            // for the demo, we'll only support drops (not clipboard paste)
            if (!support.isDrop()) {
                return false;
            }

            // we only import Strings
            if (!support.isDataFlavorSupported(m_Flavor)) {
                return false;
            }

            boolean actionSupported = (action & support.getSourceDropActions()) == action;
            if (actionSupported) {
                support.setDropAction(action);
                return true;
            }

            return false;
        }

        public boolean importData(TransferHandler.TransferSupport support)
        {
            // if we can't handle the import, say so
            if (!canImport(support)) {
                return false;
            }

            // fetch the drop location
            JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();

            int index = dl.getIndex();

            // fetch the data and bail if this fails
            T data;
            try {
                data = (T) support.getTransferable().getTransferData(m_Flavor);
            }
            catch (UnsupportedFlavorException e) {
                return false;
            }
            catch (java.io.IOException e) {
                return false;
            }

            JList list = (JList) support.getComponent();
//            DefaultListModel model = (DefaultListModel) list.getModel();
//            model.insertElementAt(data, index);

            transferFromSelection();

            Rectangle rect = list.getCellBounds(index, index);
            list.scrollRectToVisible(rect);
            list.setSelectedIndex(index);
            list.requestFocusInWindow();

            return true;
        }
    }

    protected void transferFromSelection()
    {
        Object[] objects = m_DragFrom.getSelectedValues();
        if (objects.length == 0)
            return;
        for (int i = 0; i < objects.length; i++) {
            Object object = objects[i];
            if (m_Sources.contains(object)) {
                m_Sources.removeElement(object);
                if (!m_Used.contains(object)) {
                    m_Used.addElement(object);
                }
            }
        }
        notifyItemListeners((T) objects[0], true);
    }


    protected void transferToSelection()
    {
        Object[] objects = m_DragTo.getSelectedValues();
        if (objects.length == 0)
            return;
        for (int i = 0; i < objects.length; i++) {
            Object object = objects[i];
            if (m_Used.contains(object)) {
                m_Used.removeElement(object);
                if (!m_Sources.contains(object)) {
                    m_Sources.addElement(object);
                }
            }
        }
        notifyItemListeners((T) objects[0], false);
    }


    public static final String MOVE_TO_TEXT = "Adds the selections to the used set";
    public static final String DISABLED_MOVE_TO_TEXT = "There is no selected item to add";

    private class MoveToAction extends AbstractAction implements ListSelectionListener
    {
        private MoveToAction()
        {
            ImageIcon icon = ResourceImages.getImage("move_right.png");
            putValue(Action.LARGE_ICON_KEY, icon);
            valueChanged(null);
            m_DragFrom.getSelectionModel().addListSelectionListener(this);
        }

        /**
         * Called whenever the value of the selection changes.
         *
         * @param e the event that characterizes the change.
         */
        public void valueChanged(ListSelectionEvent e)
        {
            Object[] objects = m_DragFrom.getSelectedValues();
            boolean enables = objects != null && objects.length > 0;
            if (enables)
                putValue(Action.SHORT_DESCRIPTION,
                        ResourceString.getStringFromText(MOVE_TO_TEXT));
            else
                putValue(Action.SHORT_DESCRIPTION,
                        ResourceString.getStringFromText(DISABLED_MOVE_TO_TEXT));
            setEnabled(enables);
        }

        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e)
        {
            transferFromSelection();
        }

    }

    public static final String MOVE_FROM_TEXT = "Removes the selections from the used set";
    public static final String DISABLED_MOVE_FROM_TEXT = "There are no selected item to remove";

    private class MoveFromAction extends AbstractAction implements ListSelectionListener
    {
        /**
         * Defines an <code>Action</code> object with a default
         * description string and default icon.
         */
        private MoveFromAction()
        {
            ImageIcon icon = ResourceImages.getImage("button-replay-slower.png");
            putValue(Action.LARGE_ICON_KEY, icon);
            valueChanged(null);
            m_DragTo.getSelectionModel().addListSelectionListener(this);
        }

        /**
         * Invoked when an item has been selected or deselected by the user.
         * The code written for this method performs the operations
         * that need to occur when an item is selected (or deselected).
         */
        public void valueChanged(ListSelectionEvent e)
         {
            Object[] objects = m_DragTo.getSelectedValues();
            boolean enables = objects != null && objects.length > 0;
            if (enables)
                putValue(Action.SHORT_DESCRIPTION,
                        ResourceString.getStringFromText(MOVE_FROM_TEXT));
            else
                putValue(Action.SHORT_DESCRIPTION,
                        ResourceString.getStringFromText(DISABLED_MOVE_FROM_TEXT));
            setEnabled(enables);
        }

        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e)
        {
            transferToSelection();
        }
    }


    /**
     * TEST CODE
     */

    public static final String[] TEST_ITEMS = {
            "Item1",
            "Item2",
            "Item3",
            "Item4",
            "Item5",
            "Item6",
            "Item7",
            "Item8",
            "Item9",
            "Item10",
            "Item11",
            "Item12",
    };

    public static final String[] SELECTED_ITEMS = {
            "Item1",
            "Item6",
            "Item10",
    };

    private static void createAndShowGUI()
    {
        //Create and set up the window.
        JFrame test = new JFrame("ChooseDropActionDemo");
        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ((JPanel) test.getContentPane()).setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        test.getContentPane().setPreferredSize(new Dimension(420, 315));
        JChooser<String> jChooser = new JChooser<String>(TEST_ITEMS, SELECTED_ITEMS);
        jChooser.setBackground(UIUtilities.getNamedColor("chocolate"));
        jChooser.setFromLabel("Patchables");
        jChooser.setToLabel("Patched");
        test.add(jChooser);
        //Display the window.
        test.pack();
        test.setVisible(true);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }

}
