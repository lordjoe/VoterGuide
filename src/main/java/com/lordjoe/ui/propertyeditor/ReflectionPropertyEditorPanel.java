package com.lordjoe.ui.propertyeditor;

import com.jgoodies.forms.layout.FormLayout;
import com.lordjoe.general.ISavable;
import com.lordjoe.general.UIUtilities;
import com.lordjoe.lib.xml.ClassAnalyzer;
import com.lordjoe.lib.xml.ClassProperty;
import com.lordjoe.propertyeditor.*;
import com.lordjoe.propertyeditor.PropertyEditorUtilities;
import com.lordjoe.ui.IDisplayStack;
import com.lordjoe.ui.WindowWidthPanel;
import com.lordjoe.ui.general.SubjectDisplayPanel;
import com.lordjoe.utilities.ClassUtilities;
import com.lordjoe.utilities.ResourceString;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.*;
import java.util.List;

/**
 * com.lordjoe.ui.propertyeditor.ReflectionPropertyEditorPanel
 *
 * @author Steve Lewis
 * @date Nov 20, 2007
 */
public class ReflectionPropertyEditorPanel<T> extends SubjectDisplayPanel implements WindowListener {
    public static ReflectionPropertyEditorPanel[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ReflectionPropertyEditorPanel.class;

    public static final String SAVE_TEXT = "Save the edited Object";
    public static final String CANCEL_TEXT = "Discard all edits and return";


    private static FormLayout gDefaultLayout;

    public static FormLayout getDefaultLayout() {
        return gDefaultLayout;
    }

    public static void setDefaultLayout(FormLayout pDefaultLayout) {
        gDefaultLayout = pDefaultLayout;
    }

    private IGenericWrapper[] m_Wrappers;
    private Map<String, IGenericWrapper> m_Lookup;
    private IGenericPropertyEditor[] m_Editors;
    private final List<Action> m_Actions;
    private final SaveAction m_SaveAction;
    private IStylizer m_Stylizer;
    private boolean m_SaveIsCalled;
    private JPanel m_ActionsPanel;

    public static final String[] getEditableClassPropertyNames(Class clas) {
        ClassProperty[] strings = ClassAnalyzer.getProperties(clas);
        List<String> holder = new ArrayList<String>();
        for (int i = 0; i < strings.length; i++) {
            ClassProperty cp = strings[i];
            if (cp.isStatic() || cp.isReadOnly())
                continue;
            holder.add(cp.getName());
        }
        String[] ret = new String[holder.size()];
        holder.toArray(ret);
        Arrays.sort(ret);
        return ret;
    }

    public ReflectionPropertyEditorPanel(T target) {
        this(target, getEditableClassPropertyNames(target.getClass()));
    }


    public ReflectionPropertyEditorPanel(T target, String[] properties) {
        m_Stylizer = PropertyEditorFactory.buildDefaultStylizer();
        m_Actions = new ArrayList<Action>();
        setSubjectObject(target);
        IGenericWrapper[] ret = buildWrappers(target, properties);
        m_Wrappers = ret;
        m_Lookup = PropertyEditorUtilities.buildNameToPropertyMap(m_Wrappers);

        List<IGenericPropertyEditor> hd2 = new ArrayList<IGenericPropertyEditor>();

        for (int i = 0; i < m_Wrappers.length; i++) {
            IGenericWrapper p = m_Wrappers[i];
            if (p instanceof IPropertyWrapper) {
                IComponentPropertyEditor pe = buildPropertyEditor((IPropertyWrapper) p);
                if (pe != null)    // might return null saying no editor
                    hd2.add(pe);
            }
            if (p instanceof ICollectionWrapper) {
                IGenericPropertyEditor pe = buildCollectionEditor((ICollectionWrapper) p);
                if (pe != null)   // might return null saying no editor
                    hd2.add(pe);
            }
        }
        IGenericPropertyEditor[] editors = new IGenericPropertyEditor[hd2.size()];
        hd2.toArray(editors);
        m_Editors = editors;

        m_SaveAction = new SaveAction();
        for (int i = 0; i < editors.length; i++) {
            if (editors[i] instanceof IComponentPropertyEditor) {
                IComponentPropertyEditor editor = (IComponentPropertyEditor) editors[i];
                editor.addEditedPropertyChangeListener(m_SaveAction);
            }
        }
        addAction(m_SaveAction);
        addAction(new CancelAction());
        rebuildPanel();
        setInitialized(true);
        reconcile();
    }

    protected IGenericWrapper[] buildWrappers(T target, String[] properties) {
        IGenericWrapper[] allWrappers = PropertyEditorUtilities.buildPropertyWrappers(target, properties);
        Map<String, IGenericWrapper> lookup = PropertyEditorUtilities.buildNameToPropertyMap(
                allWrappers);
        List<IGenericWrapper> holder = new ArrayList<IGenericWrapper>();

        for (int i = 0; i < properties.length; i++) {
            IGenericWrapper wp = lookup.get(properties[i]);
            holder.add(wp);

        }
        IGenericWrapper[] ret = new IGenericWrapper[holder.size()];
        holder.toArray(ret);
        return ret;
    }

    public boolean isSaveIsCalled() {
        return m_SaveIsCalled;
    }

    public void setSaveIsCalled(boolean pSaveIsCalled) {
        m_SaveIsCalled = pSaveIsCalled;
    }

    public IStylizer getStylizer() {
        return m_Stylizer;
    }

    public void setStylizer(IStylizer pStylizer) {
        if (m_Stylizer == pStylizer)
            return;
        m_Stylizer = pStylizer;
    }


    public void addAction(Action added) {
        m_Actions.add(added);
    }


    public Action[] getActions() {
        Action[] ret = new Action[m_Actions.size()];
        m_Actions.toArray(ret);
        return ret;
    }


    public void setActions(Action[] actions) {
        m_Actions.clear();
        for (int i = 0; i < actions.length; i++) {
            Action action = actions[i];
            m_Actions.add(action);
        }
        repopulateActionsPanel();
    }


    public void rebuildPanel() {
        setLayout(new BorderLayout());
        WindowWidthPanel center = new WindowWidthPanel();
        center.setOpaque(false);

        IGenericPropertyEditor[] eds = m_Editors;
        for (int i = 0; i < eds.length; i++) {
            IGenericPropertyEditor ed = eds[i];
            if (ed instanceof IComponentPropertyEditor) {
                ed.rebuildPanel();
                JComponent cmpt = ed.getComponent();
                center.add(cmpt);
            }
            if (ed instanceof ICollectionPropertyEditor) {
                ed.rebuildPanel();
                JComponent cmpt = ed.getComponent();
                center.add(cmpt);
            }
        }

        IStylizer style = getStylizer();
        if (style != null)
            style.stylize(center);

        add(new JScrollPane(center), BorderLayout.CENTER);

        m_ActionsPanel = buildActionPanel();
        repopulateActionsPanel();
        add(m_ActionsPanel, BorderLayout.SOUTH);
    }

    protected JPanel buildActionPanel() {
        JPanel ret = new JPanel();
        ret.setOpaque(false);
        return ret;
    }

    protected void repopulateActionsPanel() {
        m_ActionsPanel.removeAll();
        Action[] actions = getActions();
        for (int i = 0; i < actions.length; i++) {
            Action action = actions[i];
            m_ActionsPanel.add(new JButton(action));
        }
    }

    protected IComponentPropertyEditor buildPropertyEditor(IPropertyWrapper p) {
        return PropertyEditorFactory.buildPropertyEditor(p);
    }

    protected IGenericPropertyEditor buildCollectionEditor(ICollectionWrapper p) {
        return PropertyEditorFactory.buildCollectionEditor(p);
    }

    public T getTarget() {
        return (T) getSubjectObject();
    }

    public void setTarget(T pTarget) {
        setSubjectObject(pTarget);
    }

    public T doEditTarget() {
        JFrame app = PropertyEditorFactory.getMainFrame();
        final JDialog dlg = new JDialog(app);
        dlg.setTitle(ResourceString.parameterToText("Edit") + " " +
                ResourceString.parameterToText(ClassUtilities.shortClassName(getTarget().getClass())));
        dlg.setLayout(new GridLayout(1, 1));
        dlg.add(this);
        dlg.addWindowListener(this);
        dlg.pack();
        dlg.setSize(800, 500);
        dlg.setModal(true);
        setSaveIsCalled(false);
        UIUtilities.centerOnFrame(dlg, app);
        if (SwingUtilities.isEventDispatchThread()) {
            dlg.setVisible(true);
            dlg.removeWindowListener(this);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    dlg.setVisible(true);
                    dlg.removeWindowListener(ReflectionPropertyEditorPanel.this);
                }
            }
            );
        }


        return getTarget();

    }

    public IGenericWrapper[] getWrappers() {
        return m_Wrappers;
    }

    public IGenericPropertyEditor[] getEditors() {
        return m_Editors;
    }

    public void commit() {
        IGenericPropertyEditor[] eds = getEditors();
        for (int i = 0; i < eds.length; i++) {
            IGenericPropertyEditor ed = eds[i];
            ed.commit();
        }

    }

    protected void swingReconcileSelf() {
        super.swingReconcileSelf();
    }


    protected void swingReconcileChildren() {
        super.swingReconcileChildren();
        IGenericPropertyEditor[] eds = getEditors();
        for (int i = 0; i < eds.length; i++) {
            IGenericPropertyEditor ed = eds[i];
            ed.reconcile();
        }
        m_SaveAction.swingReconcileSelf();

    }

    public void windowOpened(WindowEvent e) {

    }

    public void windowClosing(WindowEvent e) {
        if (!isSaveIsCalled()) {
            rollbackSubject();

        }

    }

    protected void rollbackSubject() {
        Object o = getSubjectObject();
        ISavable sv = (ISavable) o;
        if (sv.isDirty())
            sv.rollback();

        setSubject(null); // this says we were canceled
    }

    public void windowClosed(WindowEvent e) {

    }

    public void windowIconified(WindowEvent e) {

    }

    public void windowDeiconified(WindowEvent e) {

    }

    public void windowActivated(WindowEvent e) {

    }

    public void windowDeactivated(WindowEvent e) {

    }

    public class CancelAction extends AbstractAction {

        public static final String CANCEL = "Cancel";

		/**
         * Defines an <code>Action</code> object with a default
         * description string and default icon.
         */
        private CancelAction() {
            putValue(Action.NAME, ResourceString.parameterToText(CANCEL));
            putValue(Action.LONG_DESCRIPTION, CANCEL_TEXT);
            putValue(Action.SHORT_DESCRIPTION, CANCEL_TEXT);
        }

        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e) {
            rollbackSubject();
            Component ancestor = UIUtilities.getTopAncestor(ReflectionPropertyEditorPanel.this);
            if (ancestor instanceof JDialog) {
                ancestor.setVisible(false);
            } else {
                ancestor = UIUtilities.getAncestorOfClass(ReflectionPropertyEditorPanel.this, IDisplayStack.class);
                if (ancestor != null) {
                    ((IDisplayStack) ancestor).popComponent();
                }
            }

        }
    }

    public class SaveAction extends AbstractAction implements EditedPropertyChangeListener {

        public static final String SAVE_EDITS = "Save Edits";

		/**
         * Defines an <code>Action</code> object with a default
         * description string and default icon.
         */
        private SaveAction() {
            putValue(Action.NAME, ResourceString.parameterToText(SAVE_EDITS));
            putValue(Action.LONG_DESCRIPTION, SAVE_TEXT);
            putValue(Action.SHORT_DESCRIPTION, SAVE_TEXT);
        }

        public void onEditedPropertyChange(Object oldValue, Object value) {
            swingReconcileSelf();

        }

        public void setEnabled(boolean newValue) {
            if (newValue)
                super.setEnabled(newValue);
            else
                super.setEnabled(newValue);

        }

        /**
         * Invoked when an action occurs.
         */
        public void actionPerformed(ActionEvent e) {
            setSaveIsCalled(true);
            IGenericPropertyEditor[] eds = getEditors();
            for (int i = 0; i < eds.length; i++) {
                IGenericPropertyEditor edx = eds[i];
                edx.commit();
            }
            Component ancestor = UIUtilities.getTopAncestor(ReflectionPropertyEditorPanel.this);
            if (ancestor instanceof JDialog)
                ancestor.setVisible(false);
            else {
                ancestor = UIUtilities.getAncestorOfClass(ReflectionPropertyEditorPanel.this, IDisplayStack.class);
                if (ancestor != null) {
                    ((IDisplayStack) ancestor).popComponent();
                }
            }
            Object o = getSubjectObject();
            if (o instanceof ISavable) {
                ISavable sv = (ISavable) o;
                if (sv.isDirty())
                    sv.save();
            }
        }

        protected void swingReconcileSelf() {
            StringBuilder sb = new StringBuilder();
            Map<String, Object> values = new HashMap<String, Object>();
            IGenericPropertyEditor[] eds = getEditors();
            for (int i = 0; i < eds.length; i++) {
                IGenericPropertyEditor edx = eds[i];
                if (edx instanceof IComponentPropertyEditor) {
                    IComponentPropertyEditor ed = (IComponentPropertyEditor) edx;
                    Object o = ed.getValue();
                    values.put(ed.getPropertyWrapper().getPropertyName(), o);
                    IPropertyWrapper pw = ed.getPropertyWrapper();
                    if (pw.isRequired() && (o == null || "".equals(o))) {
                        setEnabled(false);
                        if (sb.length() == 0)
                            sb.append("<html>");
                        sb.append("Required property " + pw.getDisplayName() + " not set <p/>");
                    }
                    if (!pw.isAcceptable(o)) {
                        setEnabled(false);
                        pw.isAcceptable(o); // debug redo
                        sb.append("Property " + pw.getDisplayName() + " not set to a valid value<p/>");
                    }
                }
            }
            if (sb.length() > 0) {
                sb.append("</html>");
                String error = sb.toString();
                putValue(Action.LONG_DESCRIPTION, error);
                putValue(Action.SHORT_DESCRIPTION, error);
                return;
            }
            if (getSubjectObject() instanceof IChangeValidator) {
                IChangeValidator cv = (IChangeValidator) getSubjectObject();
                IValidationReason[] reasons = cv.validateProposedChange(values);
                if (reasons != null) {
                    sb.append("<html>");
                    sb.append("Disabled because <b>");
                    for (int i = 0; i < reasons.length; i++) {
                        IValidationReason reason = reasons[i];
                        sb.append(reason.getReason());
                        String[] pn = reason.getPropertyNames();
                        for (int j = 0; j < pn.length; j++) {
                            String s = pn[j];
                            sb.append(" " + s);
                        }
                    }
                    sb.append("<b>");
                    sb.append("</html>");
                    String error = sb.toString();
                    putValue(Action.LONG_DESCRIPTION, error);
                    putValue(Action.SHORT_DESCRIPTION, error);
                    setEnabled(false);
                    return;

                }

            }
            setEnabled(true);
            putValue(Action.LONG_DESCRIPTION, SAVE_TEXT);
            putValue(Action.SHORT_DESCRIPTION, SAVE_TEXT);

        }


    }
}
