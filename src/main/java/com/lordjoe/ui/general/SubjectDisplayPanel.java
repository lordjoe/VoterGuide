package com.lordjoe.ui.general;

import com.lordjoe.general.UIUtilities;
import com.lordjoe.lang.GeneralUtilities;
import com.lordjoe.ui.IInfoTextBuilder;
import com.lordjoe.ui.UIOps;
import com.lordjoe.utilities.JConsole;
import com.lordjoe.utilities.Util;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;


/**
 * com.lordjoe.ui.general.SubjectDisplayPanel
 * Any panel representing the display of a subject
 *
 * @author slewis
 * @date Feb 24, 2005
 */
public class SubjectDisplayPanel extends JPanel implements IDisplay,
        MouseInputListener, IInfoTextBuilder
{
    public static final Class THIS_CLASS = SubjectDisplayPanel.class;
    public static final SubjectDisplayPanel EMPTY_ARRAY[] = {};


    /**
     * if true this is a detail view and more than the basics can be shown
     */
    private boolean m_Detail;
    private Object m_Subject;
    private boolean m_ReconcileRequired;
    private boolean m_Initialized;

    public SubjectDisplayPanel()
    {
        addMouseListener(this);
        setOpaque(false);
        UIUtilities.registerComponent(this);
    }

    public boolean isInitialized()
    {
        return m_Initialized;
    }

    public void setInitialized(boolean pInitialized)
    {
        m_Initialized = pInitialized;
    }

    public boolean isReconcileRequired()
    {
        return m_ReconcileRequired;
    }

    public void setReconcileRequired(boolean pReconcileRequired)
    {
        m_ReconcileRequired = pReconcileRequired;
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     */
    public void mouseClicked(MouseEvent e)
    {
        if (UIUtilities.isInformationRequest(e)) {
            showInfoDialog();
        }
    }

    public void showInfoDialog()
    {
        String text = buildInfoText();
        JConsole console = new JConsole("Details", text);
    }

    /**
     * build a string describing the current object
     *
     * @return
     */
    public String buildInfoText()
    {
        return buildInfoText(0, false);
    }
    /**
     * build a string describing the current object
     * @return
     */

    /**
     * build a string describing the current object
     *
     * @param indent indent level
     * @return
     */
    public String buildInfoText(int indent, boolean asChild)
    {
        Set<Component> handled = new HashSet<Component>();
        StringBuffer sb = new StringBuffer();
        sb.append(buildSelfInfoText(indent));
        if (!asChild)
            sb.append(UIUtilities.buildParentInfoText(this, indent + 1));
        Component[] components = getComponents();
        if (components.length > 0) {
            for (Component c : components) {
                if (handled.contains(c))
                    continue;
                handled.add(c);
                sb.append(UIUtilities.buildInfoText(c, indent + 1, true, handled));
            }
        }
        return sb.toString();
    }


    /**
     * build a string describing the current object
     *
     * @param indent non-negative indent level
     * @return
     */
    public String buildSelfInfoText(int indent)
    {
        StringBuffer sb = new StringBuffer();
        Util.indentStringBuffer(sb, indent);
        sb.append("Class " + getClass().getName());
        sb.append("\n");
        Object subjectO = getSubjectObject();
        if (subjectO != null) {
            Util.indentStringBuffer(sb, indent);
            sb.append("Subject " + subjectO.toString());
            sb.append("\n");
            Util.indentStringBuffer(sb, indent);
            sb.append("Subject Class " + subjectO.getClass().getName());
            sb.append("\n");

        }
        return sb.toString();
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     */
    public void mouseMoved(MouseEvent e)
    {
        // Do nothing but maybe override
    }

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  <code>MOUSE_DRAGGED</code> events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     *                                                                                          <p>
     * Due to platform-dependent Drag&Drop implementations,
     * <code>MOUSE_DRAGGED</code> events may not be delivered during a native
     * Drag&Drop operation.
     */
    public void mouseDragged(MouseEvent e)
    {
        // Do nothing but maybe override
    }

    /**
     * Invoked when the mouse exits a component.
     */
    public void mouseExited(MouseEvent e)
    {
        // Do nothing but maybe override
    }

    /**
     * Invoked when the mouse enters a component.
     */
    public void mouseEntered(MouseEvent e)
    {
        // Do nothing but maybe override
    }

    /**
     * Invoked when a mouse button has been released on a component.
     */
    public void mouseReleased(MouseEvent e)
    {
        // Do nothing but maybe override
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    public void mousePressed(MouseEvent e)
    {
        // Do nothing but maybe override
    }

//    /**
//     * Returns the instance of <code>JToolTip</code> that should be used
//     * to display the tooltip.
//     * Components typically would not override this method,
//     * but it can be used to
//     * cause different tooltips to be displayed differently.
//     *
//     * @return the <code>JToolTip</code> used to display this toolTip
//     */
//    public JToolTip createToolTip()
//    {
//        NDCTooltip tip = new NDCTooltip();
//         tip.setComponent(this);
//         return tip;
//     }

    /**
     * Returns the tooltip location in this component's coordinate system.
     * If <code>null</code> is returned, Swing will choose a location.
     * The default implementation returns <code>null</code>.
     *
     * @param event the <code>MouseEvent</code> that caused the
     *              <code>ToolTipManager</code> to show the tooltip
     * @return always returns <code>null</code>
     */
    public Point getToolTipLocation(MouseEvent event)
    {
        Point ret = super.getToolTipLocation(event);
        String text = getToolTipText(event);
        if (text == null)
            return ret;
        ret = event.getPoint();
        Point loc = UIUtilities.getWindowLocation(this);
        Dimension size = UIUtilities.getWindowSize(this);

        String[] lines = text.split("\n");
        int maxLen = 0;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            maxLen = Math.max(maxLen, line.length());
        }
        int charSize = 12;
        int lineSize = 12;

        int tipWidth = maxLen * charSize;
        int tipHeight = lines.length * lineSize;
        if (loc.x + ret.x + tipWidth >= size.width) {
            int inWindowLocX = size.width - loc.x - tipWidth;
            int newX = Math.max(-loc.x, inWindowLocX);
            ret = new Point(newX, ret.y);
        }
        if (loc.y + ret.y + tipHeight >= size.height) {
            int inWindowLocY = size.height - loc.y - tipHeight;
            int newY = Math.max(-loc.y, inWindowLocY);
            ret = new Point(ret.x, newY);
        }
        return ret;

    }

    public String getToolTipText(MouseEvent m)
    {
        Point pos = m.getPoint();
        Rectangle active = getTooltipRectangle();
        if (active.contains(pos)) {

            Object subjectO = getSubjectObject();
            if (subjectO == null)
                return null;
            String description = null; // ((IDevice) subjectO).getDeviceDescription();
            description = UIOps.convertTooltipToHtml(description);
            if (isSubjectTooltipInhibited())
                return null;
            return description;
        }
        else {
            Component parent = getParent();
            if (parent instanceof SubjectDisplayPanel) {
                MouseEvent parentM = UIUtilities.parentMouseEvent(this, m);
                return ((SubjectDisplayPanel) parent).getToolTipText(parentM);
            }
            return null;
        }
    }

    protected boolean isSubjectTooltipInhibited()
    {
        Component parent = this;
        while (parent != null) {
            if (parent instanceof ISubjectTooltipSupporter)
                return false; // allow tooltips
            if (parent instanceof ISubjectTooltipInhibitor)
                return true;  // forbid tooltips
            parent = parent.getParent();
        }
        return false; // allow tooltips
    }

    protected Rectangle getTooltipRectangle()
    {
        return UIUtilities.getActiveRectangle(this);
    }


    /**
     * if true this is a detail view and more than the basics can be shown
     *
     * @return
     */
    public boolean isDetail()
    {
        return m_Detail;
    }

    /**
     * see isDetail
     *
     * @param detail
     */
    public void setDetail(boolean detail)
    {
        m_Detail = detail;
    }

    /**
     * return the class of the represented object
     *
     * @return non-null class
     */
    public Class getSubjectClass()
    {
        return Object.class;
    }

    /**
     * return the represented object
     *
     * @return usually nopn-null Subject
     */
    public Object getSubjectObject()
    {
        return m_Subject;
    }


    public void setSubject(Object subject) //throws IllegalSubjectException
    {
        if (GeneralUtilities.equivalentObject(m_Subject, subject))
            return;
        validateSubjectClass(subject);
        setSubjectObject(subject);
        reconcile();
    }

    private void validateSubjectClass(Object subject)
    {
        if(subject == null)
            return;
        Class subjectClass = getSubjectClass();
        if (!subjectClass.isInstance(subject)) {
            throw new IllegalArgumentException("Bad Subject: " +
                    "of class " + subject.getClass() +
                    " not of class " + subjectClass
            );  
        }
    }


    /**
     * set the represented object
     *
     * @param usually non-null Subject
     */
    public void setSubjectObject(Object subject)
    {
        if (GeneralUtilities.equivalentObject(m_Subject, subject))
            return;
        validateSubjectClass(subject);
        m_Subject = subject;
        reconcile();
    }

    private static Set<Class> ALREADY_WARNED = new HashSet();

    /**
     * Thread safe call to make the models consistent with the
     * subject
     *
     * @return usually non-null Subject
     */
    public final void reconcile()
    {
        if (!isReconcileRequired())
            setReconcileRequired(true);
        if (!isInitialized())
        {
//            ISynthesis synth = DeviceFactory.getSynthesis();
//            // warn aboiut uninitialized displays
//            if(synth != null && synth.isSynthesizing()) {
//                if(!ALREADY_WARNED.contains(getClass())) {
//                    ALREADY_WARNED.add(getClass());
//                    ObjectOps.breakHere();
//                    GeneralUtilities.showString("reconcile uninitialized Object " + getClass() + "\n");
//                }
//
//            }
//            return;
        }
        UIUtilities.doReconcile(this);
    }

    /**
     * Implementing code for reconcile - must be run in the
     * swing thread
     *
     * @return usually non-null Subject
     */
    public final void swingReconcile()
    {
        if(!isInitialized())
            return;
        UIUtilities.guaranteeSwingThread();
        swingReconcileSelf();
        swingReconcileChildren();
        setReconcileRequired(false);
    }

    /**
     * Implementing code for reconcile - must be run in the
     * swing thread
     *
     * @return usually non-null Subject
     */
    protected void swingReconcileSelf()
    {
        // override to do more meaningful things
    }

    /**
     * Implementing code for reconcile - must be run in the
     * swing thread
     *
     * @return usually non-null Subject
     */
    protected void swingReconcileChildren
            ()
    {
        for (Component c : getComponents()) {
            if (c instanceof IDisplay) {
                ((IDisplay) c).swingReconcile();
            }
        }
    }

}
