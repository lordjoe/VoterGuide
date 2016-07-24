package com.lordjoe.ui;


import com.lordjoe.general.UIUtilities;
import com.lordjoe.runner.*;
import com.lordjoe.ui.general.AdvancedPropertiesEditor;
import com.lordjoe.ui.general.OKCancelDialog;
import com.lordjoe.ui.propertyeditor.IEditableProperty;
import com.lordjoe.ui.propertyeditor.IPropertySource;
import com.lordjoe.ui.propertyeditor.MapPropertySource;
import com.lordjoe.ui.propertyeditor.PropertiesEditorPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.*;

/**
 * com.lordjoe.runner.ui.ExposedParameterizedOperationAction
 *
 * @author Steve Lewis
 * @date Oct 2, 2007
 */
public class ExposedParameterizedOperationAction extends ExposedOperationAction
{
    public static ExposedParameterizedOperationAction[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ExposedParameterizedOperationAction.class;

    private final Map<String, Object> m_Data;
    private final IEditableProperty[] m_Params;

    public ExposedParameterizedOperationAction(String opName,String actionName, JComponent cmp)
    {
        super(opName, actionName,cmp);
        m_Data = new HashMap<String, Object>();
        this.putValue(Action.NAME,actionName + "...");
        IRunAction op = getOperation();

        RunActionParameter[] props = op.getAllRunActionParameters();
        List<RunActionParameter> holder = new ArrayList<RunActionParameter>();
        for (int i = 0; i < props.length; i++) {
            RunActionParameter prop = props[i];
            if(prop.getValue() == null)
                 holder.add(prop);
        }
        props = new RunActionParameter[holder.size()];
        holder.toArray(props);
         m_Params = new IEditableProperty[props.length];
        IPropertySource ps = new MapPropertySource(getData());
         for (int i = 0; i < props.length; i++) {
             RunActionParameter prop = props[i];
             m_Params[i] = AdvancedPropertiesEditor.buildProperty(prop,ps);
         }

    }

    public Map<String, Object> getData()
    {
        return m_Data;
    }

    public void actionPerformed(ActionEvent e)
    {
        JFrame frame = DefaultFrame.getCurrentFrame();
        Map<String,Object> params = RunActionManager.getInstance().getDefaultParameters();
        ParameterDialog parameterDialog = new ParameterDialog(
                frame,params);
        parameterDialog.normalizesSize();
        if(frame != null)
            UIUtilities.centerOnFrame(parameterDialog,frame);
        parameterDialog.setVisible(true);
     }

    public class ParameterDialog extends OKCancelDialog
    {
        public ParameterDialog( JFrame aParent,Map<String,Object> params)
         {
             this("Set Parameters",aParent,params);
          }
        public ParameterDialog(String aTitle, JFrame aParent,Map<String,Object> params)
         {
             super(aTitle,aParent);
             PropertiesEditorPanel editor = new PropertiesEditorPanel(m_Params,params);
             getOKAction().setRealAction(new RunOpAction(editor));
             setComponent(editor);
             setModal(true);

          }
     }

    public class RunOpAction extends AbstractAction
    {
        private final PropertiesEditorPanel m_Editor;
        public RunOpAction(PropertiesEditorPanel editor)
        {
            putValue(Action.NAME,"OK");
            m_Editor = editor;
        }
        public void actionPerformed(ActionEvent e)
        {
            if(m_Editor != null)
                m_Editor.apply();

            performTask();
           
        }
    }

    protected void performTask()
    {

        RunActionManager tm = RunActionManager.getInstance();
        IActionRunner runner = tm.queueRunAction(getOperation());
        IRunEnvironment env = runner.getEnv();
        for (Iterator<String> iterator = m_Data.keySet().iterator(); iterator.hasNext();) {
            String  s =  iterator.next();
            Object o = m_Data.get(s);
             env.setParameter(s,o);
        }
         setRunner(runner);
    }


}

