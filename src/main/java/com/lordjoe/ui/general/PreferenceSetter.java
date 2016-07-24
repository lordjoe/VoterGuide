package com.lordjoe.ui.general;

import com.lordjoe.general.UIUtilities;
import com.lordjoe.lang.ObjectOps;
import com.lordjoe.ui.DefaultComponentPanel;
import com.lordjoe.ui.DefaultFrame;
import com.lordjoe.ui.IDefaultComponent;
import com.lordjoe.ui.propertyeditor.*;
import com.lordjoe.utilities.ResourceString;
import com.lordjoe.utilities.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.prefs.Preferences;

/**
 * com.lordjoe.ui.general.PreferenceSetter
 *
 * @author Steve Lewis
 * @date Sep 14, 2005
 */
public class PreferenceSetter extends DefaultComponentPanel implements IPropertySource
{

    public static final Preferences USER_NODE = Preferences.userRoot();

    public static final String MACHINE_PROP_NAME = "MachineName";
    public static final String CHIP_VERSION_PROP_NAME = "ChipVersion";

    private String m_MachineName;
    private String m_ChipVersion;
     private final List<PropertyChangeListener> m_Listeners;
    private final PropertiesEditorPanel m_Editor;

    public PreferenceSetter(IDefaultComponent ndcParent)
    {
        super(ndcParent);
        m_Listeners = new CopyOnWriteArrayList();
        setLayout(new BorderLayout());
        IEditableProperty[] props = buildProperties();
        m_Editor = new PropertiesEditorPanel(props,null);
        add(new JScrollPane(m_Editor), BorderLayout.CENTER);
        ActionPanel actions = buildActionPanel(this, m_Editor);
        add(actions, BorderLayout.SOUTH);
    }

    protected ActionPanel buildActionPanel(SubjectDisplayPanel panel, PropertiesEditorPanel editor)
    {
        Action[] items = {
                new SaveAction(),
                new CloseAction(),
        };
        return new ActionPanel(panel, items);
    }


    protected IEditableProperty[] buildProperties()
    {
        List<IEditableProperty> holder = new ArrayList<IEditableProperty>();
        IPropertySource ps = this;
        IEditableProperty prop = null;
        prop = buildProperty(MACHINE_PROP_NAME, getMachineName(), null);
        holder.add(prop);
        holder.add(prop);
        IEditableProperty[] ret = new IEditableProperty[holder.size()];
        holder.toArray(ret);
        return ret;
    }


    public Object getProperty(String name)
    {
        if (MACHINE_PROP_NAME.equalsIgnoreCase(name))
            return getMachineName();
        if (CHIP_VERSION_PROP_NAME.equalsIgnoreCase(name))
            return getChipVersion();
        throw new IllegalArgumentException("unknown Property " + name);
    }

    public void setProperty(String name, Object value)
    {
        if (MACHINE_PROP_NAME.equalsIgnoreCase(name)) {
            setMachineName((String) value);
            return;
        }
        if (CHIP_VERSION_PROP_NAME.equalsIgnoreCase(name)) {
            setChipVersion((String) value);
            return;
        }
        throw new IllegalArgumentException("unknown Property " + name);
    }

    public String[] getPropertyNames()
    {

        String[] ret = {MACHINE_PROP_NAME, CHIP_VERSION_PROP_NAME};
        return ret;
    }

    public IEditableProperty buildProperty(String name, String value, String[] options)
    {
        ObjectPropertyImpl desc = new ObjectPropertyImpl(name, String.class);
        Object defaultValue = value;
        desc.setOptions(options);

        //     desc.setOptions(options);

        IEditableProperty prop = new EditableProperty(desc, this);
        if (defaultValue != null)
            prop.setValue(defaultValue);
        prop.setReadableName(Util.nerdCapsToWords(name));
        return prop;
    }


    public void addPropertyChangeListener(PropertyChangeListener prop)
    {
        m_Listeners.add(prop);
    }

    public void removePropertyChangeListener(PropertyChangeListener prop)
    {
        m_Listeners.remove(prop);
    }

    protected void notifyPropertyChangeListeners(String propName, Object oldVal,
                                                 Object newVal)
    {
        if (m_Listeners.isEmpty())
            return;
        PropertyChangeEvent evt = new PropertyChangeEvent(this, propName, oldVal, newVal);
        for (PropertyChangeListener listener : m_Listeners)
            listener.propertyChange(evt);
    }



    public String getMachineName()
    {
        return m_MachineName;
    }

    public void setMachineName(String pMachineName)
    {
        String old = m_MachineName;
        if (ObjectOps.equalsNullSafe(old, pMachineName))
            return;
        m_MachineName = pMachineName;
        notifyPropertyChangeListeners(MACHINE_PROP_NAME, old, pMachineName);
    }

    public String getChipVersion()
    {
        return m_ChipVersion;
    }

    public void setChipVersion(String pChipVersion)
    {
        String old = m_ChipVersion;
        if (ObjectOps.equalsNullSafe(old, pChipVersion))
            return;
        m_ChipVersion = pChipVersion;
        notifyPropertyChangeListeners(CHIP_VERSION_PROP_NAME, old, pChipVersion);
    }


    public class CloseAction extends AbstractAction
    {
        public CloseAction()
        {
            super();
            String name = ResourceString.getStringFromText("Close");
            this.putValue(Action.NAME, name);
        }

        public void actionPerformed(ActionEvent ev)
        {
            getDefaultFrame().close();
        }
    }

    public class SaveAction extends AbstractAction
    {
        public SaveAction()
        {
            super();
            String name = ResourceString.getStringFromText("Save");
            this.putValue(Action.NAME, name);
        }

        public void actionPerformed(ActionEvent ev)
        {
            m_Editor.apply();
             getDefaultFrame().close();
        }
    }

    public static void main(String[] args)
    {
        DefaultFrame test = new DefaultFrame();
        test.setTitle("Machine Properties Setter");
        PreferenceSetter ps = new PreferenceSetter(test);
        test.setMainDisplay(ps);
        test.setSize(500, 400);
        UIUtilities.becomeVisible(test);
    }
}
