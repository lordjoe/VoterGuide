package com.lordjoe.ui.propertyeditor;


import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;

import com.jgoodies.forms.layout.*;

/**
 * com.lordjoe.ui.propertyeditor.FormStylizer
 *
 * @author Steve Lewis
 * @date Nov 26, 2007
 */
public class FormStylizer extends AbstractStylizer {
    public static FormStylizer[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = FormStylizer.class;

    protected static ColumnSpec[] getColSpecs(FormLayout layout) {
        List<ColumnSpec> holder = new ArrayList<ColumnSpec>();
        int ncols = layout.getColumnCount();
        for (int i = 0; i < ncols; i++) {
            ColumnSpec spec = layout.getColumnSpec(i + 1);
            holder.add(spec);
        }

        ColumnSpec[] ret = new ColumnSpec[holder.size()];
        holder.toArray(ret);
        return ret;
    }

    protected static RowSpec[] getRowSpecs(FormLayout layout) {
        List<RowSpec> holder = new ArrayList<RowSpec>();
        int ncols = layout.getRowCount();
        for (int i = 0; i < ncols; i++) {
            holder.add(layout.getRowSpec(i + 1));
        }
        RowSpec[] ret = new RowSpec[holder.size()];
        holder.toArray(ret);
        return ret;
    }

    private final ColumnSpec[] m_ColSpecs;
    private final RowSpec[] m_RowsSpecs;

    public FormStylizer(ColumnSpec[] csps, RowSpec[] rspecs) {
        m_ColSpecs = csps;
        m_RowsSpecs = rspecs;
    }

    public FormStylizer(FormLayout layout) {
        this(getColSpecs(layout), getRowSpecs(layout));
    }

    public FormLayout buildLayout() {
        return new FormLayout(m_ColSpecs, m_RowsSpecs);
    }

    /**
     * build data for layout
     *
     * @param jc
     * @return
     */
    protected Object buildLayoutData(JComponent jc) {
        return new LayoutData();
    }

    /**
     * set the stype of the component to the current rules
     *
     * @param jc
     */
    protected void doSylize(JComponent jc, Object Data) {
        jc.setLayout(new BorderLayout());
        JPanel components = null;
        LayoutData ld = (LayoutData) Data;
        CellConstraints constraints = ld.getCC();
        JComponent[][] jComponents = ld.getComponents();
        int numberRows = jComponents.length;
        if (numberRows > 0) {
            components = new JPanel();
            RowSpec[] rows = new RowSpec[2 * numberRows];
            System.arraycopy(m_RowsSpecs,0,rows,0,rows.length);
            components.setLayout(new FormLayout(m_ColSpecs, rows));
            for (int row = 0; row < jComponents.length; row++) {
                JComponent[] jComponent = jComponents[row];
                int layoutRow = (2 * row) + 2;
                int col = 2;
                int length = jComponent.length;
                if (length == 1)
                    col += 2;
                for (int i = 0; i < length; i++) {
                    JComponent component = jComponent[i];
                    components.add(component, constraints.xy(col, layoutRow));
                    col += 2;
                }
            }
            jc.add(components, BorderLayout.NORTH);
        }
        JPanel[] panels = ld.getPanels();
        if (panels.length > 0) {
            numberRows = panels.length;
            components = new JPanel();
            RowSpec[] rows = new RowSpec[2 * numberRows];
            System.arraycopy(m_RowsSpecs,0,rows,0,rows.length);
            components.setLayout(new FormLayout(m_ColSpecs, rows));
            for (int row = 0; row < panels.length; row++) {
                JPanel panel = panels[row];
                int layoutRow = (2 * row) + 2;
                components.add(panel, constraints.xy(2, layoutRow));

            }
            jc.add(components, BorderLayout.CENTER);
        }
    }


    /**
     * set the stype of the component to the current rules
     *
     * @param jc
     */
    protected void doSylizeComponent(JComponent jc, Object Data) {
        LayoutData ld = (LayoutData) Data;
        if (jc instanceof IStylizableComponent) {
            JComponent[] components = ((IStylizableComponent) jc).getStylizableComponents();
            ld.addComponentSet(components);
            jc.removeAll();
        }

    }


    /**
     * set the stype of the component to the current rules
     *
     * @param jc
     */
    protected void doSylizePanel(JComponent jc, Object Data) {
        LayoutData ld = (LayoutData) Data;
        if (jc instanceof ICollectionPropertyPanel) {
            JComponent[] components = ((IStylizableComponent) jc).getStylizableComponents();
            ld.addPanel((JPanel) components[0]);
            return;
        }

    }


    protected static class LayoutData {
        private CellConstraints m_CC = new CellConstraints();
        private int m_Row;
        private int m_Col;
        private List<JComponent[]> m_ComponentList = new ArrayList<JComponent[]>();
        private List<JPanel> m_PanelList = new ArrayList<JPanel>();

        public LayoutData() {
        }

        public CellConstraints getCC() {
            return m_CC;
        }

        public void setCC(CellConstraints pCC) {
            m_CC = pCC;
        }

        public int getRow() {
            return m_Row;
        }

        public void setRow(int pRow) {
            m_Row = pRow;
        }

        public int incrementRow() {
            setCol(0);
            setRow(getRow() + 1);
            return getRow();
        }

        public int getCol() {
            return m_Col;
        }

        public void setCol(int pCol) {
            m_Col = pCol;
        }

        public void addComponentSet(JComponent[] items) {
            m_ComponentList.add(items);
        }

        public JComponent[][] getComponents() {
            JComponent[][] ret = new JComponent[m_ComponentList.size()][];
            m_ComponentList.toArray(ret);
            return ret;
        }

        public void addPanel(JPanel item) {
            m_PanelList.add(item);
        }

        public JPanel[] getPanels() {
            JPanel[] ret = new JPanel[m_PanelList.size()];
            m_PanelList.toArray(ret);
            return ret;
        }
    }
}
