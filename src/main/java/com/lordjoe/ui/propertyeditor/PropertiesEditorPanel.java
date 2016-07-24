package com.lordjoe.ui.propertyeditor;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.lordjoe.interpreter.*;
import com.lordjoe.ui.general.ActionPanel;
import com.lordjoe.ui.general.SubjectDisplayPanel;
import com.lordjoe.utilities.ResourceString;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * com.lordjoe.ui.propertyeditor.PropertiesEditorPanel
 *
 * @author slewis
 * @date Apr 21, 2005
 */
public class PropertiesEditorPanel<T extends Object> extends JPanel implements IPropertyEditor<T>
{
    public static final Class THIS_CLASS = PropertiesEditorPanel.class;
    public static final PropertiesEditorPanel EMPTY_ARRAY[] = {};
    public static final int LABEL_COLUMN = 2;
    public static final int EDITOR_COLUMN = LABEL_COLUMN + 2;
    public static final int UNITS_COLUMN = EDITOR_COLUMN + 2;
    public static final int ADDED_COLUMN = UNITS_COLUMN + 2;

    private IEditableProperty[] m_Properties;
    private IPropertyEditor[] m_Editors;
    private final Map<String, Object> m_Params;
    private IStylizer m_Stylizer;


    public static SubjectDisplayPanel fullPropertiesEditor(IEditableProperty[] props,
                                                           Map<String, Object> params)
    {
        SubjectDisplayPanel ret = new SubjectDisplayPanel();
        ret.setLayout(new BorderLayout());
        PropertiesEditorPanel editor = partPropertiesEditor(props, params);
        ret.add(editor, BorderLayout.CENTER);
        ActionPanel comp = buildActionPanel(ret, editor);
        ret.add(comp, BorderLayout.SOUTH);
        return ret;
    }

    public static PropertiesEditorPanel partPropertiesEditor(IEditableProperty[] props,
                                                             Map<String, Object> params)
    {
        PropertiesEditorPanel editor = new PropertiesEditorPanel(props, params);
        return editor;
    }

    public static ActionPanel buildActionPanel(SubjectDisplayPanel panel,
                                               PropertiesEditorPanel editor)
    {
        Action[] items = {
                new SetDefaultAction(editor),
                new RevertAction(editor),
                new ApplyAction(editor),
        };
        return new ActionPanel(panel, items);
    }


    public PropertiesEditorPanel(IEditableProperty[] props, Map<String, Object> params)
    {
        this(props, params, false);
    }

    /**
     * allow delay of build display for subclassing
     *
     * @param props
     * @param params
     * @param doNotBuild
     */
    protected PropertiesEditorPanel(IEditableProperty[] props, Map<String, Object> params,
                                    boolean doNotBuild)
    {
        m_Properties = props;
        m_Params = params;
        if (!doNotBuild)
            setEditors(buildDisplay());
    }

    /**
     * return the value of the variable when the editor was created or the
     * last time appyly was called
     *
     * @return possibly null value
     */
    public T getOriginalValue()
    {
         throw new UnsupportedOperationException("Not supported at this level");
     }

    /**
     * set the value as a string
     *
     * @param s possibly null String
     */
    public void setStringValue(String s)
    {
         throw new UnsupportedOperationException("Not supported at this level");

    }
    /**
     * return current value as a String or null if no
     * value
     * @return
     */
    public String getStringValue( )
    {
        throw new UnsupportedOperationException("Not supported at this level");
    }
    

    /**
     * return the value represented by the cuttent state of the editor
     *
     * @return possibly null value as above
     * @throws IllegalStateException when the currrent state of the
     *                               editor is not a legal value
     */
    public T getCurrentValue() throws IllegalStateException
    {
        throw new UnsupportedOperationException("Not supported at this level");
    }

    /**
     * make the  current value is either valid or invalid
     *
     * @param b as ab0ve
     */
    public void setValueValid(boolean valid)
    {
        throw new UnsupportedOperationException("Not supported at this level");


    }

    /**
     * test whether the value is valid
     * return null if unable to decide
     *
     * @param b possibly null value
     * @return as above null is no opinion
     */
    public Boolean isValueValid(T in)
    {
        return null;
      }



    /**
     * set value when focus was gained - used
     * on loass to see if action is required
     *
     * @param value
     */
    public void setValueOnEntry(T value)
    {
        throw new UnsupportedOperationException("Not supported at this level");

    }

    /**
     * get the value when the fucus was gained
     *
     * @return possiblt null value
     * @throws IllegalStateException
     */
    public T getValueOnEntry()
    {
        throw new UnsupportedOperationException("Not supported at this level");
    }


    /**
     * expose to let a subclass handle buildDisplay();
     *
     * @param pEditors
     */
    protected void setEditors(IPropertyEditor[] pEditors)
    {
        m_Editors = pEditors;
    }

    public Map<String, Object> getParams()
    {
        return m_Params;
    }

    public IStylizer getStylizer()
    {
        return m_Stylizer;
    }

    public void setStylizer(IStylizer pStylizer)
    {
        if (m_Stylizer == pStylizer)
            return;
        m_Stylizer = pStylizer;
    }

    public void setProperties(IEditableProperty[] pProperties)
    {
        m_Properties = pProperties;
    }

    protected IPropertyEditor[] buildDisplay()
    {
         
         String cl = layoutColumnsString();
        String rl = layoutRowString();
        FormLayout layout = new FormLayout(cl, rl);
        setLayout(layout);
        List<IPropertyEditor> holder = new ArrayList<IPropertyEditor>();
        for (int i = 0; i < m_Properties.length; i++) {
            IEditableProperty iEditableProperty = m_Properties[i];
            IPropertyEditor theItem = buildPropertyEditor(iEditableProperty, i);
            if (theItem != null)
                holder.add(theItem);
        }
        IPropertyEditor[] ret = new IPropertyEditor[holder.size()];
        holder.toArray(ret);
        return ret;
    }

    protected IPropertyEditor buildPropertyEditor(IEditableProperty prop, int row)
    {
        String propName = prop.getReadableName();
        String labelStr = ResourceString.parameterToText(propName);
        JLabel lbl = new JLabel(labelStr);
        JLabel units = null;
        String unitsString = prop.getUnits();
        Map<String, Object> params = getParams();
        if (prop.getValue() != null) {
            if (prop.getValue() instanceof String) {
                String val = (String) prop.getValue();
                if (val.startsWith("@")) {
                    String key = val.substring(1);
                    Object value = params.get(key);
                    prop.setValue(value);
                }
                if (val.startsWith("=")) {
                    String expression = val.substring(1);
                    Object value = Interpreter.eval(expression, params);
                    prop.setValue(value);
                }
            }
        }

        if ("Fluid".equals(propName))
            propName = "Fluid";    // break here
        if ("Sourcing Type".equals(propName))
            propName = "Sourcing Type";    // break here

        IPropertyEditor editor = PropertyEditorUtilities.buildPropertyEditor(prop);
        if (unitsString != null)
            units = new JLabel(unitsString);

        CellConstraints cc = new CellConstraints();
        int layoutRow = (2 * row) + 1;
        add(lbl, cc.xy(LABEL_COLUMN, layoutRow));
        JComponent editorComponent = (JComponent) editor;
        if (units == null) {
            add(editorComponent, cc.xyw(EDITOR_COLUMN, layoutRow, 7));
        }
        else {

            add(editorComponent, cc.xyw(EDITOR_COLUMN, layoutRow, 2));
            add(units, cc.xy(UNITS_COLUMN, layoutRow));
        }
        return editor;
    }

    public IEditableProperty[] getProperties()
    {
        return m_Properties;
    }

    public IPropertyEditor[] getEditors()
    {
        return m_Editors;
    }

    protected String layoutColumnsString()
    {
        return "4dlu, pref, 4dlu, 100dlu, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, 10dlu";
    }

    protected String layoutRowString()
    {
        StringBuffer sb = new StringBuffer();
        int nrows = m_Properties.length;
        sb.append("p");
        for (int i = 0; i < nrows; i++) {
            if (i < nrows - 1)
                sb.append(", 2dlu");
            sb.append(",p");
        }
        return sb.toString();
    }

    /**
     * allow user editing
     *
     * @param enabled
     */
    public void setEditable(boolean enabled)
    {
        IPropertyEditor[] editors = m_Editors;
        for (int i = 0; i < editors.length; i++) {
            editors[i].setEditable(enabled);
        }
    }

    /**
     * restore the property to the original value
     * or value set at last apply
     */
    public void revert()
    {
        IPropertyEditor[] editors = m_Editors;
        for (int i = 0; i < editors.length; i++) {
            editors[i].revert();
        }
    }

    /**
     * restore the property to the original value
     * or value set at last apply
     */
    public void revertToOriginal()
    {
        IPropertyEditor[] editors = m_Editors;
        for (int i = 0; i < editors.length; i++) {
            editors[i].revertToOriginal();
        }
    }

    /**
     * If the <code>preferredSize</code> has been set to a
     * non-<code>null</code> value just returns it.
     * If the UI delegate's <code>getPreferredSize</code>
     * method returns a non <code>null</code> value then return that;
     * otherwise defer to the component's layout manager.
     *
     * @return the value of the <code>preferredSize</code> property
     * @see #setPreferredSize
     * @see javax.swing.plaf.ComponentUI
     */
    public Dimension getPreferredSize()
    {
        LayoutManager layoutManager = getLayout();
        if (layoutManager != null) {
            return layoutManager.preferredLayoutSize(this);
        }
        Dimension preferredSize = super.getPreferredSize();
        return preferredSize;

//        int width = 300;
//        int height = 0;
//        IPropertyEditor[] editors = m_Editors;
//        for (int i = 0; i < editors.length; i++) {
//            Component component1 = (Component) editors[i];
//            if (component1 != null) {
//                Dimension eSize = component1.getPreferredSize();
//                height += eSize.height;
//                width = Math.max(eSize.width, width);
//            }
//        }
//        return new Dimension(width, height);

    }


    /**
     * If the <code>preferredSize</code> has been set to a
     * non-<code>null</code> value just returns it.
     * If the UI delegate's <code>getPreferredSize</code>
     * method returns a non <code>null</code> value then return that;
     * otherwise defer to the component's layout manager.
     *
     * @return the value of the <code>preferredSize</code> property
     * @see #setPreferredSize
     * @see javax.swing.plaf.ComponentUI
     */
    public Dimension getMinimumSize()
    {
        int width = 0;
        int height = 0;
        IPropertyEditor[] editors = m_Editors;
        for (int i = 0; i < editors.length; i++) {
            Dimension eSize = ((Component) editors[i]).getMinimumSize();
            height += eSize.height;
            width = Math.max(eSize.width, width);
        }
        return new Dimension(width, height);

    }

    /**
     * If the <code>preferredSize</code> has been set to a
     * non-<code>null</code> value just returns it.
     * If the UI delegate's <code>getPreferredSize</code>
     * method returns a non <code>null</code> value then return that;
     * otherwise defer to the component's layout manager.
     *
     * @return the value of the <code>preferredSize</code> property
     * @see #setPreferredSize
     * @see javax.swing.plaf.ComponentUI
     */
    public Dimension getMaximumSize()
    {
        int width = 0;
        int height = 0;
        IPropertyEditor[] editors = m_Editors;
        for (int i = 0; i < editors.length; i++) {
            Dimension eSize = ((Component) editors[i]).getMaximumSize();
            height += eSize.height;
            width = Math.max(eSize.width, width);
        }
        return new Dimension(width, height);

    }

    /**
     * set to the default value
     */
    public void setDefault()
    {
        IPropertyEditor[] editors = m_Editors;
        for (int i = 0; i < editors.length; i++) {
            editors[i].setDefault();
        }
    }

    /**
     * apply current changes
     */
    public void apply()
    {
        IPropertyEditor[] editors = m_Editors;
        for (int i = 0; i < editors.length; i++) {
            editors[i].apply();
        }
    }

    public abstract static class PropertyEditorAction extends AbstractAction
    {
        private final IPropertyEditor m_Editor;

        public PropertyEditorAction(IPropertyEditor editor)
        {
            m_Editor = editor;
        }

        public IPropertyEditor getEditor()
        {
            return m_Editor;
        }

    }

    public static class SetDefaultAction extends PropertyEditorAction
    {
        public SetDefaultAction(IPropertyEditor editor)
        {
            super(editor);
            String name = ResourceString.parameterToText("Set Default");
            this.putValue(Action.NAME, name);
        }

        public void actionPerformed(ActionEvent ev)
        {
            IPropertyEditor editor = getEditor();
            editor.setDefault();
        }
    }

    public static class ApplyAction extends PropertyEditorAction
    {
        public ApplyAction(IPropertyEditor editor)
        {
            super(editor);
            String name = ResourceString.parameterToText("Apply");
            this.putValue(Action.NAME, name);
        }

        public void actionPerformed(ActionEvent ev)
        {
            getEditor().apply();
        }
    }

    public static class RevertAction extends PropertyEditorAction
    {
        public RevertAction(IPropertyEditor editor)
        {
            super(editor);
            String name = ResourceString.parameterToText("Revert");
            this.putValue(Action.NAME, name);
        }

        public void actionPerformed(ActionEvent ev)
        {
            getEditor().revert();
        }
    }

}
