package com.lordjoe.ui.propertyeditor;

import javax.swing.*;
import java.awt.*;

/**
 * com.lordjoe.ui.propertyeditor.AbstractStylizer
 *
 * @author Steve Lewis
 * @date Nov 26, 2007
 */
public abstract class AbstractStylizer implements IStylizer
{
    public static FormStylizer[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = FormStylizer.class;


    public AbstractStylizer()
    {

    }
    /**
     * set the stype of the component to the current rules
     *
     * @param jc
     */
    public void stylize(JComponent jc)
    {
        Object data = buildLayoutData(jc);
        stylizeComponents(jc,data);
        doSylize(jc,data);

    }

    protected void stylizeComponents(JComponent jc,Object data)
    {
        Component[] cmps = jc.getComponents();
        for (int i = 0; i < cmps.length; i++) {
            Component cmp = cmps[i];
            if(cmp instanceof ICollectionPropertyPanel) {
                doSylizePanel((JComponent)cmp,data);
            }
            else {
            if(cmp instanceof JComponent)
                doSylizeComponent((JComponent)cmp,data);
            }
        }
    }

    /**
     * build data for layout
     * @param jc
     * @return
     */
    protected abstract Object buildLayoutData(JComponent jc);

    /**
     * perform layour using implementation dependent data
     * @param jc
     * @param data
     */
    protected abstract void doSylize(JComponent jc,Object data);

    /**
     * perform layour using implementation dependent data
     * @param jc
     * @param data
     */
    protected abstract void doSylizeComponent(JComponent jc,Object data);

    /**
     * perform layour using implementation dependent data
     * @param jc
     * @param data
     */
    protected abstract void doSylizePanel(JComponent jc,Object data);

}