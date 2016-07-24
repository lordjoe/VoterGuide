package com.lordjoe.smartgrid;

import com.lordjoe.utilities.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;


//public class OverlordHeaderRenderer extends JLabel implements TableCellRenderer {
public class OverlordHeaderRenderer extends JExtendedLabel implements TableCellRenderer {

    protected static Border gRaisedBorder  =  BorderFactory.createRaisedBevelBorder();
    protected static Border gLoweredBorder =  BorderFactory.createLoweredBevelBorder();

    public OverlordHeaderRenderer()
    {
        Assertion.doNada();
    }
    
    // Debugging Override
    public void paint(Graphics g) {
        Rectangle r = getBounds();
        if(getText().equals("First\nName"))
            Assertion.doNada();
		super.paint(g);
		// CellRenderer paintComponent screws with the bounds
		// this puts them back where they were
		// setBounds(r.x,r.y,r.width,r.height);
		r = null;
    }


    /**
      * Sets the color to use for the background if node is selected.
      */
    public Color getBackgroundSelectionColor() {
			return UIManager.getColor("textHighlight");
    }

    /**
      * Returns the background color to be used for non selected nodes.
      */
    public Color getBackgroundNonSelectionColor() {
			return getBackground();
    }

    /**
      * Returns the color the text is drawn with when the node is selected.
      */
    public Color getTextSelectionColor() {
    	return UIManager.getColor("textHighlightText");
    }

    /**
      * Returns the color the text is drawn with when the node isn't selected.
      */
    public Color getTextNonSelectionColor() {
    	return getForeground();
    }
    
    // debugging override
    public Dimension getPreferredSize() 
    {
        Dimension d = super.getPreferredSize();
        return(d);
    }

    // debugging override
    public Dimension getMinimumSize() 
    {
        Dimension d = super.getMinimumSize();
        return(d);
    }
    
   // debugging override
    public void setBounds(int x,int y,int w,int h)
    {
        super.setBounds(x,y,w,h);
    }


    /**
      * Returns the color the border is drawn.
      */
    public Color getBorderSelectionColor() {
			return Color.yellow;
    }

	public Component getTableCellRendererComponent(JTable table, Object value,
						boolean isSelected,boolean hasFocus, int rowIndex, int col) 
	{

      String stringValue = (value == null) ? "" : value.toString();
      setText(stringValue);
      Object me = this;
      if(me instanceof JTextArea) {
          if(stringValue.indexOf('\n') > -1)
            ((JTextArea)me).setRows(2);
           else 
             ((JTextArea)me).setRows(1);
             
           ((JTextArea)me).setEditable(false);
      }
        
       
      
      OverlordTableModelInterface TheModel = (OverlordTableModelInterface)table.getModel();
      int ModelSortColumn = TheModel.getSortColumn();
      boolean IsSortColumn = (ModelSortColumn == col);
      if(TheModel.isHeader3D()) {
          if(IsSortColumn)
            setBorder(gLoweredBorder);
          else 
            setBorder(gRaisedBorder);
      }
      else {
        setBorder(null);
      }
        
      setBackground(TheModel.getHeaderBackground());
    //  setBackground(Color.green);
      setOpaque(true);
      Color fore = TheModel.getHeaderForeground();
      if(fore == null)
        fore = Color.black;
      setForeground(fore);
      
      return this;
    }

  // end class OverlordHeaderRenderer
  }
