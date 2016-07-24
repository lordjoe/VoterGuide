/**{ file
 @name Developer.java
 @function Developer implements Developer.validate which functions
 much like a C++ assert statement - this works with
 Developer.break which allows developers to trap and trace
 suspicious behavior.
 @author> Steven M. Lewis
 @copyright>
 ************************
 *  Copyright (c) 1996,97,98
 *  Steven M. Lewis
 *  www.LordJoe.com
 ************************
 @date> Mon Jun 22 21:48:24 PDT 1998
 @version> 1.0
 }*/
package com.lordjoe.utilities;

import com.lordjoe.exceptions.*;

import java.awt.*;
/* import java.awt.event.*;
import javax.swing.*;
*/

/**{ class
 @name Developer
 @function Developer implements Developer.validate which functions
 much like a C++ assert statement - this works with
 Developer.break which allows developers to trap and trace
 suspicious behavior.
 }*/
public class Developer {

    //- *******************
    //- Methods
    /**{ method
     @name outln
     @function - print a line - this version is like System.out.println
     but allows insertion of a break point to see who is printing what
     }*/
    static public void outln(String line) {
        System.out.println(line);
    }

    /**{ method
     @name out
     @function - print a string - this version is like System.out.print
     but allows insertion of a break point to see who is printing what
     }*/
    static public void out(String line) {
        System.out.print(line);
    }

    /**{ method
     @name halt
     @function - something bad happened
     }*/
    static public void halt() {
        halt("Unspecified Fatal Error");
    }

    /**{ method
     @name halt
     @function - something bad happened
     @param s - comment string
     }*/
    static public void halt(String s) {
        halt(s, new WrapperException());
    }

    /**{ method
     @name halt
     @function - something bad happened
     @param s - comment string
     @param ex - causing exception
     }*/
    static public void halt(String s, Exception ex) {
        // if this is called - use the debugger to find out
        // what is going on
        String StackTrace = getStackTrace(ex);
        // **************************
        // Place a break here always
        // *************************
        //  AssertionFailureDialog.showAssertionFailure(s,StackTrace);

        String FullError = s + "\n" + StackTrace;
        //   ex.printStackTrace(); // try to stdout
        throw new AssertionFailureException(FullError);
    }

    /**{ method
     @name report
     @function - put out a message - currently sends to System.out
     may change in future
     @param s - non-null comment string
     }*/
    static public void report(String s) {
        System.out.println(s);
    }

    /**{ method
     @name report
     @function - put out a message - currently sends to System.out
     may change in future
     @param s <Add Comment Here>
     }*/
    static public void report(Exception e) {
        String out = e.toString() + "\n";
        out += getStackTrace();
        System.out.println(out);
    }

    /**{ method
     @name getStackTrace
     @function <Add Comment Here>
     @return <Add Comment Here>
     }*/
    static public String getStackTrace() {
        WrapperException temp = new WrapperException();
        return (getStackTrace(temp));
    }

    /**{ method
     @name getStackTrace
     @function <Add Comment Here>
     @param ex <Add Comment Here>
     @return <Add Comment Here>
     }*/
    static public String getStackTrace(Throwable ex) {
        return (StackTrace.getStackTrace(ex));
    }

    /**{ method
     @name showStackTrace
     @function <Add Comment Here>
     @param ex <Add Comment Here>
     }*/
    public static void showStackTrace(Throwable ex) {
        System.out.println(getStackTrace(ex));
    }

    /**{ method
     @name expandColor
     @function - Allow developers to look at components oa a color
     in the debugger
     @param c - color to test
     }*/
    public static void expandColor(Color c) {
        int red = c.getRed();
        int green = c.getGreen();
        int blue = c.getBlue();
        Assertion.useArg(red);
        Assertion.useArg(green);
        Assertion.useArg(blue);
    }
/*
    public static class AssertionFailureDialog extends JDialog implements ActionListener
    {
        private Icon RedDeadPC;
        private JButton m_OKButton;

        public static void showAssertionFailure(String Failure,String StackTrace) {
            AssertionFailureDialog me = new AssertionFailureDialog(Failure,StackTrace);
            me.show();
            me.toFront();
        }
        protected  AssertionFailureDialog(String Failure,String StackTrace) {
            super(new Frame(),true); // modal
            getContentPane().removeAll(); // get rid of the old OK button
            setTitle("A Fatal Error Has Occured");
            getContentPane().setLayout(new BorderLayout());
            getContentPane().add(makePicturePanel(),BorderLayout.WEST);
            getContentPane().add(makeMessagePanel(Failure,StackTrace),BorderLayout.CENTER);
            setLocationRelativeTo(null); // center on screen
            setSize(600,300);
        }

        protected JPanel makePicturePanel() {
            JPanel ret = new JPanel();
            ret.add(new JLabel(getDeadPC()));
            ret.setBackground(Color.red);
            return(ret);
        }
        protected Box makeMessagePanel(String Message,String Stack) {
            Box ret = Box.createVerticalBox();
            JTextArea msg = new JTextArea(Message);
            msg.setBackground(Color.red);
            ret.add(msg);

            JTextArea stackarea = new JTextArea(Stack);
            stackarea.setBackground(Color.red);
            ret.add(new JScrollPane(stackarea));
            ret.add(makeButtonPanel());
            ret.setBackground(Color.red);
            return(ret);
        }

        protected JPanel makeButtonPanel() {
            JPanel ret = new JPanel();
            ret.setBackground(Color.red);
            m_OKButton = new JButton("OK");
            m_OKButton.addActionListener(this);
            ret.add(m_OKButton);
            return(ret);
        }


        protected Icon getDeadPC() {
            if(RedDeadPC == null) {
                RedDeadPC = FileUtilities.getResourceIcon(getClass(),"images/RedDeadPC.jpg");
            }
            return(RedDeadPC);
        }
        public void actionPerformed(ActionEvent ev) {
            Object test = ev.getSourceId();
            if(test == m_OKButton) {
                setVisible(false);
                dispose();
            }
        }
    }

*/
//- *******************
//- End Class Developer
}
