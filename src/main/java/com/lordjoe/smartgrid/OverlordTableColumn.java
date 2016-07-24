
package com.lordjoe.smartgrid;

import com.lordjoe.utilities.*;
import java.awt.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.table.*;

public class OverlordTableColumn extends TableColumn {

  public static final String VISIBLE_PROPERTY = "visible";
//  public static final String FIXED_PROPERTY = "fixed";

  private boolean m_Visible = true;
//  private boolean m_Fixed = false;
  private PropertyChangeSupport m_ChangeSupport;

  public OverlordTableColumn() {
  	super();
}

  public OverlordTableColumn(int anIdentifier) {
  	super(anIdentifier);
  }


  public OverlordTableColumn(int anIdentifier, boolean visible) {
  	this(anIdentifier);
    setVisible(visible);
  }

  public static TableCellEditor createCellEditor(JTextField x) {
  	return new GridCellEditor(x);
  }
  
 // public static TableCellEditor createCellEditor(JTextArea x) {
  //	return new GridCellEditor(x);
 // }

  public static TableCellEditor createCellEditor(JCheckBox x) {
  	return new GridCellEditor(x);
  }

  public static TableCellEditor createCellEditor(JComboBox x) {
  	return new GridCellEditor(x);
  }

  public static TableCellRenderer createCellRenderer(JLabel x) {
  	return new OverlordCellRenderer();
  }

 public void setWidth(int newWidth) {
    if(newWidth != OverlordGrid.DEFAULT_COLUMN_WIDTH)
        Assertion.doNada();
    super.setWidth(newWidth);
 }
 
 public void setMinWidth(int newWidth) {
    super.setMinWidth(newWidth);
 }
 public void setMaxWidth(int newWidth) {
    super.setMaxWidth(newWidth);
 }
 
/*  public static TableCellRenderer createCellRenderer(JButton x) {
  	return new DefaultTableCellRenderer(x);
  }

  public static TableCellRenderer createCellRenderer(JCheckBox x) {
  	return new DefaultTableCellRenderer(x);
  }
 */

    /**
     * Returns the <code>TableCellRenderer</code> used to draw the header of the
     * <code>TableColumn</code>. When the <code>headerRenderer</code> is
     * <code>null</code>, the <code>JTableHeader</code>
     * uses its <code>defaultRenderer</code>. The default value for a
     * <code>headerRenderer</code> is <code>null</code>.
     *
     * @return the <code>headerRenderer</code> property
     * @see    #setHeaderRenderer
     * @see    #setHeaderValue
     * @see    javax.swing.table.JTableHeader#getDefaultRenderer()
     */
    public TableCellRenderer getHeaderRenderer()
    {
        TableCellRenderer headerRenderer = super.getHeaderRenderer();
        if(headerRenderer != null)
            return(headerRenderer);
        setHeaderRenderer(new OverlordHeaderRenderer());
        return(super.getHeaderRenderer());

    }

    public void setCellRenderer(TableCellRenderer aRenderer)
    {
        super.setCellRenderer(aRenderer);
    }
    
    public TableCellRenderer getCellRenderer()
    {
        TableCellRenderer ret = super.getCellRenderer();
        if(ret != null)
            return(ret);
        setCellRenderer(new OverlordCellRenderer());
        return(super.getCellRenderer());
    }

  public void setFont(Font font) {
    if (getCellRenderer() != null && getCellRenderer() instanceof DefaultTableCellRenderer) {
      DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)getCellRenderer();
      renderer.setFont(font);
    }
    if (getCellEditor() != null && getCellEditor() instanceof GridCellEditor) {
      GridCellEditor editor = (GridCellEditor)getCellRenderer();
      editor.getComponent().setFont(font);
    }
  }

  public boolean isVisible() {
  	return m_Visible;
  }

  public void setVisible(boolean value) {
  	if (m_Visible != value) {
    	boolean oldValue = m_Visible;
    	m_Visible = value;
      firePropertyChange(VISIBLE_PROPERTY, new Boolean(oldValue), new Boolean(value));
    }
  }
  

/*  public boolean isFixed() {
  	return m_Fixed;
  }

  public void setFixed(boolean value) {
  	if (m_Fixed != value) {
    	boolean oldValue = m_Fixed;
	  	m_Fixed = value;
      firePropertyChange(FIXED_PROPERTY, new Boolean(oldValue), new Boolean(value));
    }
  }
  */

  protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		if (m_ChangeSupport == null)
			return;
		m_ChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
  }

  public synchronized void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
    if (m_ChangeSupport == null)
			m_ChangeSupport = new PropertyChangeSupport(this);
    m_ChangeSupport.addPropertyChangeListener(propertyChangeListener);
    super.addPropertyChangeListener(propertyChangeListener);
  }

  public synchronized void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
    if (m_ChangeSupport != null)
			m_ChangeSupport.removePropertyChangeListener(propertyChangeListener);
    super.removePropertyChangeListener(propertyChangeListener);
  }

    public void setHeaderRenderer(TableCellRenderer aRenderer)
    {
        super.setHeaderRenderer(aRenderer);
    }
    
    protected TableCellRenderer createDefaultHeaderRenderer() {
    	OverlordHeaderRenderer label = new OverlordHeaderRenderer();
    //	label.setHorizontalAlignment(JLabel.CENTER);
    	label.setBorder(BorderFactory.createRaisedBevelBorder());

    	return label;
    }
  
  static class GridCellEditor extends DefaultCellEditor {
  	public GridCellEditor(JTextField x) {
    	super(x);
    }
  	public GridCellEditor(JCheckBox x) {
    	super(x);
    }
  	public GridCellEditor(JComboBox x) {
    	super(x);
    }
    public Component getComponent() {
    	return editorComponent;
    }
  }
  
  
// end class OverlordTableColmmn
}


