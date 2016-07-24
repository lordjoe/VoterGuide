package com.lordjoe.ui.propertyeditor;

import com.jgoodies.forms.layout.FormLayout;
import com.lordjoe.exceptions.WrappingException;
import com.lordjoe.general.UIUtilities;
import com.lordjoe.lang.ObjectOps;
import com.lordjoe.ui.DefaultFrame;
import com.lordjoe.ui.general.JActionButton;
import com.lordjoe.utilities.ResourceString;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * com.lordjoe.ui.propertyeditor.PropertyEditorDialog
 *
 * @author slewis
 * @date Apr 21, 2005
 */
public class PropertyEditorDialog extends JDialog
{
    public static final Class THIS_CLASS = PropertyEditorDialog.class;
    public static final PropertyEditorDialog EMPTY_ARRAY[] = {};

    public static Integer chooseInteger(String name)
    {
        return chooseInteger(name, null);
    }

    public static Integer chooseInteger(String name, Integer defaultValue)
    {
        PropertyEditorDialog pd = new PropertyEditorDialog();
        pd.addIntegerProperty(name, defaultValue);
        pd.setTitle(ResourceString.parameterToText("Choose") + " " +
                ResourceString.parameterToText(name));
        boolean input = pd.getInput();
        if (input) {
            return (Integer) pd.getProperty(name);
        }
        return null;
    }


    public static String chooseString(String name)
    {
        return chooseString(name, null);
    }


    public static String chooseString(String name, String defaultValue)
    {
        PropertyEditorDialog pd = new PropertyEditorDialog();
        pd.addStringProperty(name, defaultValue);
        pd.setTitle(ResourceString.parameterToText("Choose") + " " +
                ResourceString.parameterToText(name));
        boolean input = pd.getInput();
        if (input) {
            return (String) pd.getProperty(name);
        }
        return null;
    }

    private IPropertySource m_Ps;
    private Map<String, Object> m_Data;
    private IEditableProperty[] m_Properties;
    private PropertiesEditorPanel m_Editor;
    private PropertiesDialogActionPanel m_Actions;
    private boolean m_Canceled;
    private boolean m_Finished;

    public PropertyEditorDialog()
    {
        super(DefaultFrame.getCurrentFrame());
        m_Data = new HashMap<String, Object>();
        m_Ps = new MapPropertySource(m_Data);
        m_Properties = IEditableProperty.EMPTY_ARRAY;
        setModal(true);
    }

    public void addIntegerProperty(String name, Integer defaultValue)
    {
        IEditableProperty prop = PropertyEditorUtilities.buildProperty(name,
                Integer.class, defaultValue, null, m_Ps);
        m_Properties = (IEditableProperty[]) ObjectOps.addToArray(m_Properties, prop);
    }

    public void addStringProperty(String name, String defaultValue)
    {
        IEditableProperty prop = PropertyEditorUtilities.buildProperty(name,
                String.class, defaultValue, null, m_Ps);
        m_Properties = (IEditableProperty[]) ObjectOps.addToArray(m_Properties, prop);
    }


    public void addNetAddressProperty(String name, InetAddress defaultValue)
    {
        IEditableProperty prop = PropertyEditorUtilities.buildProperty(name,
                InetAddress.class, defaultValue, null, m_Ps);
        m_Properties = (IEditableProperty[]) ObjectOps.addToArray(m_Properties, prop);
    }

      public void addChoiceProperty(String name, Object defaultValue,Object[] options)
    {
         addChoiceProperty( name,  defaultValue,options,defaultValue.getClass());
    }

    public void addChoiceProperty(String name, Object defaultValue,Object[] options,Class type)
    {
        IEditableProperty prop = PropertyEditorUtilities.buildProperty(name,
                type, defaultValue, options, m_Ps);
        m_Properties = (IEditableProperty[]) ObjectOps.addToArray(m_Properties, prop);
    }

    public PropertiesEditorPanel getEditor()
    {
        return m_Editor;
    }

    public Object getProperty(String name)
    {
        return m_Data.get(name);
    }


    public boolean isCanceled()
    {
        return m_Canceled;
    }

    public void setCanceled(boolean pCanceled)
    {
        m_Canceled = pCanceled;
    }

    public boolean isFinished()
    {
        return m_Finished;
    }

    public synchronized void setFinished(boolean pFinished)
    {
        m_Finished = pFinished;
        notifyAll();
    }


    public boolean getInput()
    {
        setLayout(new BorderLayout());
        m_Editor = PropertiesEditorPanel.partPropertiesEditor(m_Properties, null);
        add(m_Editor, BorderLayout.CENTER);
        m_Actions = buildActionPanel();
        add(m_Actions, BorderLayout.SOUTH);
        Dimension preferedSize = getPreferredSize();
        Dimension actionSize = m_Actions.getPreferredSize();
        setSize(Math.max(preferedSize.width, actionSize.width + 20),
                preferedSize.height + actionSize.height + 20);
        UIUtilities.centerOnFrame(this, DefaultFrame.getCurrentFrame());
        UIUtilities.becomeVisible(this);
        synchronized (this) {
            while (!isFinished()) {
                try {
                    wait(2000);
                    if (!isVisible())
                        return !isCanceled();
                }
                catch (InterruptedException e) {
                    throw new WrappingException(e);
                }
            }
        }
        return isCanceled();
    }

    public PropertiesDialogActionPanel buildActionPanel()
    {
        Action[] items = {
                new OKAction(),
                new CancelAction(),
        };
        return new PropertiesDialogActionPanel(items);
    }

    public class OKAction extends AbstractAction
    {
        public OKAction()
        {
            String name = ResourceString.parameterToText("OK");
            this.putValue(Action.NAME, name);
        }

        public void actionPerformed(ActionEvent ev)
        {
            try {
                IPropertyEditor editor = getEditor();
                editor.apply();
                setCanceled(false);
                setVisible(false);
                setFinished(true);
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new WrappingException(e);
            }
        }
    }

    public class CancelAction extends AbstractAction
    {
        public CancelAction()
        {
            String name = ResourceString.parameterToText("Cancel");
            this.putValue(Action.NAME, name);
        }

        public void actionPerformed(ActionEvent ev)
        {
            setCanceled(true);
            setVisible(false);
            setFinished(true);
        }
    }


    public class PropertiesDialogActionPanel extends JPanel
    {

        private final Action[] m_Actions;

        public PropertiesDialogActionPanel(Action[] actions)
        {
            m_Actions = actions;
            buildPanel();
        }

        protected void buildPanel()
        {
            FormLayout layout = new FormLayout(
                    buildLayoutString(), "p");
            setLayout(layout);
            for (int i = 0; i < m_Actions.length; i++) {
                JActionButton button = new JActionButton(m_Actions[i]);
                String positionString = Integer.toString(2 * (i + 1)) + ",1";
                add(button, positionString);
            }
        }

        protected String buildLayoutString()
        {
            StringBuffer sb = new StringBuffer();
            sb.append("0:grow");
            for (int i = 0; i < m_Actions.length; i++) {
                sb.append(",p");
                if (i < m_Actions.length - 1)
                    sb.append(", 4px"); // add spacing
            }
            return sb.toString();
        }


    }

    /**
     * Shows or hides this component depending on the value of parameter
     * <code>b</code>.
     *
     * @param b if <code>true</code>, shows this component;
     *          otherwise, hides this component
     * @see #isVisible
     * @since JDK1.1
     */
    public synchronized void setVisible(boolean b)
    {
        super.setVisible(b);
        notifyAll();
    }

    public static enum GateChoices {
       SMT,GATE,BOSS,SBB;
    }

    public static void main(String[] args)
    {
//        Integer i = chooseInteger("Count");
//        if (i != null)
//            GeneralUtilities.printString("Count=" + i);
//        String s = chooseString("Name");
//        if (s != null)
//            GeneralUtilities.printString("Name=" + s);

        PropertyEditorDialog pd = new PropertyEditorDialog();
        pd.addNetAddressProperty("IPAddress",
                PropertyEditorUtilities.buildINetAddress("127.0.0.1"));
        pd.addChoiceProperty("GateType",GateChoices.SMT,GateChoices.values(),GateChoices.class);
         pd.setTitle(ResourceString.parameterToText("Set Properties") );
        boolean input = pd.getInput();
        if (input) {
            String ip = (String)pd.getProperty("IPAddress");
            System.out.println(ip);
            GateChoices gate = (GateChoices)pd.getProperty("GateType");
            System.out.println(gate);
        }

    }


}
