package com.lordjoe.ui.general;

import javax.swing.*;
import java.awt.event.*;
//import javax.help.*;
//import javax.help.DefaultHelpBroker;

/**
 * com.lordjoe.ui.general.ShowHelpAction
 *
 * @author Steve Lewis
 * @date Feb 28, 2006
 */
public class ShowHelpAction extends AbstractAction
{
    public final static ShowHelpAction[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = ShowHelpAction.class;

    public static final String HELP_LOC = null; //TODO

//    private HelpBroker m_HelpBroker;
    private final String m_HelpSource;
    private ActionListener m_RealAction;

    public ShowHelpAction(String source)
    {
        m_HelpSource = source;
        putValue(Action.NAME, "Manual...");
        //  putValue(Action.ACCELERATOR_KEY, "Manual");
    }

//    private HelpBroker getHelpBroker()
//            throws HelpSetException
//    {
//        if (m_HelpBroker == null) {
//            try {
//                HelpSet hs = new HelpSet(null, new URL("file:" + m_HelpSource));
//                m_HelpBroker = hs.createHelpBroker(m_HelpSource);
//                DefaultHelpBroker defaultHelpBroker = (DefaultHelpBroker) m_HelpBroker;
//                WindowPresentation wp = defaultHelpBroker.getWindowPresentation();
//                m_HelpBroker.initPresentation();
//                Frame f = (Frame)wp.getHelpWindow();
//                f.setIconImage(NDCFrame.getFrameImage());
//                UIUtilities.centerOnFrame(f,NDCFrame.getCurrentFrame());
//                m_RealAction = new CSH.DisplayHelpFromSource(m_HelpBroker);
//            }
//            catch (Exception ex) {
//                ex.printStackTrace();
//                ExceptionHandlingManager.log(ex);
//            }
//        }
//        return m_HelpBroker;
//    }
//
//    /**
//     * Invoked when an action occurs.
//     */
//    public void actionPerformed(ActionEvent e)
//    {
//        try {
//            getHelpBroker();
//            m_RealAction.actionPerformed(e);
//        }
//        catch (HelpSetException ex) {
//            ex.printStackTrace();
//            ExceptionHandlingManager.log(ex);
//        }
//
//    }


    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e)
    {
        if (true) throw new UnsupportedOperationException("Fix This");

    }

    protected void setHelpIcon()
    {

    }
}
