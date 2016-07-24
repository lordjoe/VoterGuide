package com.lordjoe.ui.general;


import com.lordjoe.utilities.*;
import com.lordjoe.ui.propertyeditor.*;
import com.lordjoe.ui.general.*;
import com.lordjoe.ui.general.ActionPanel;
import com.lordjoe.runner.*;
import com.lordjoe.runner.ui.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;


/**
 * com.lordjoe.runner.ui.general.AdvancedPropertiesEditor
 *
 * @author Steve Lewis
 * @date Oct 2, 2007
 */
public class AdvancedPropertiesEditor extends SubjectDisplayPanel
{
    public static final Class THIS_CLASS = AdvancedPropertiesEditor.class;
    public static final AdvancedPropertiesEditor EMPTY_ARRAY[] = {};

    private final Map<String, Object> m_Data;
    private final PropertiesEditorPanel m_Editors;
    private final IRunAction m_RunAction;

    public AdvancedPropertiesEditor(IRunAction subj)
    {
        super();
        m_RunAction = subj;
        setLayout(new BorderLayout());
        m_Data = RunActionManager.getInstance().getDefaultParameters();
        IEditableProperty[] props = buildProperties(m_Data);
        m_Editors = new PropertiesEditorPanel(props, m_Data);
        m_Editors.setEditable(false);
        add(new JScrollPane(m_Editors), BorderLayout.CENTER);
        ActionPanel actions = PropertiesEditorPanel.buildActionPanel(this, m_Editors);
        add(actions, BorderLayout.SOUTH);
        setInitialized(true);
    }

    public IRunAction getRunAction()
    {
        return m_RunAction;
    }

    protected IEditableProperty[] buildProperties(Map<String, Object> data)
    {
        List<IEditableProperty> holder = new ArrayList<IEditableProperty>();
        IPropertySource ps = new MapPropertySource(data);
        RunActionParameter[] params = getRunAction().getAllRunActionParameters();
        IEditableProperty prop = null;
        for (int i = 0; i < params.length; i++) {
            RunActionParameter param = params[i];
            if (RunnerDisplayProperties.isPropertyIgnoredInEditor(param.getName()))
                continue;
            if (RunnerDisplayProperties.isPropertyInAdvancedEditor(param.getName()))
                continue;
            prop = buildProperty(param, ps);
            holder.add(prop);
        }
        IEditableProperty[] ret = new IEditableProperty[holder.size()];
        holder.toArray(ret);
        return ret;
    }

    public PropertiesEditorPanel getEditors()
    {
        return m_Editors;
    }

    /**
     * Implementing code for reconcile - must be run in the
     * swing thread
     *
     * @return usually non-null Subject
     */
    protected void swingReconcileChildren()
    {
        super.swingReconcileChildren();
        PropertiesEditorPanel edPanel = getEditors();
        if(true)
            throw new UnsupportedOperationException("Fix This"); // ToDo
//       IPropertyEditor[] eds = edPanel.getEditors();
//        for (int i = 0; i < eds.length; i++) {
//            IPropertyEditor ed = eds[i];
//            ed.revert();
//        }

    }


    public Object getProperty(String name)
    {
        return m_Data.get(name);
    }

    public static IEditableProperty buildProperty(RunActionParameter param, IPropertySource ps)
    {
        String name = param.getName();
        Class theClass = parameterTypeToClass(param.getType());
        String units = parameterTypeToUnits(param.getType());

        ObjectPropertyImpl desc = new ObjectPropertyImpl(name, theClass);
  //      desc.setDescription(param.getDescription());
        Object defaultValue = param.getValue();
        if(defaultValue == null)
             defaultValue = param.getDefault();
        if (units != null)
            desc.setUnits(units);
        desc.setDefaultValue(defaultValue);

        //     desc.setOptions(options);

        IEditableProperty prop = new EditableProperty(desc, ps);
        if (defaultValue != null)
            prop.setValue(defaultValue);
        prop.setReadableName(Util.nerdCapsToWords(name));
        return prop;
    }

    public static Class parameterTypeToClass(String pt)
    {
        ParameterTypeEnum ptx = ParameterTypeEnum.getOption(pt);
        return parameterTypeToClass(ptx);
    }
    public static Class parameterTypeToClass(ParameterTypeEnum pt)
    {
        if (pt == ParameterTypeEnum.TIME_ENUM)
            return Long.class;
        if (pt == ParameterTypeEnum.INTEGER_ENUM)
            return Integer.class;
        if (pt == ParameterTypeEnum.BOOLEAN_ENUM)
            return Boolean.class;
        if (pt == ParameterTypeEnum.STRING_ENUM)
            return String.class;
        if (pt == ParameterTypeEnum.FILE_ENUM)
            return File.class;
        if (pt == ParameterTypeEnum.PART_ENUM)
            return File.class;

        throw new UnsupportedOperationException("Unsupported type " + pt);
    }

    public static String parameterTypeToUnits(String pt)
    {
        ParameterTypeEnum ptx = ParameterTypeEnum.getOption(pt);
        return parameterTypeToUnits(ptx);
    }

    public static String parameterTypeToUnits(ParameterTypeEnum pt)
    {
        if (pt == ParameterTypeEnum.TIME_ENUM)
            return ("MilliSec");
        return null;
    }


    protected static IEditableProperty buildProperty(String name,
                                                     Class cls, Object defaultValue,
                                                     Object[] options, IPropertySource ps)
    {
        ObjectPropertyImpl desc = new ObjectPropertyImpl(name, cls);
        desc.setDefaultValue(defaultValue);
        desc.setOptions(options);

        IEditableProperty prop = new EditableProperty(desc, ps);
        if (defaultValue != null)
            prop.setValue(defaultValue);
        return prop;
    }

    protected static IEditableProperty buildProperty(String name,
                                                     Class cls, Object defaultValue,
                                                     Object[] options, IPropertySource ps,
                                                     String units)
    {
        ObjectPropertyImpl desc = new ObjectPropertyImpl(name, cls);
        desc.setDefaultValue(defaultValue);
        desc.setOptions(options);
        desc.setUnits(units);

        IEditableProperty prop = new EditableProperty(desc, ps);
        if (defaultValue != null)
            prop.setValue(defaultValue);
        return prop;
    }


}
