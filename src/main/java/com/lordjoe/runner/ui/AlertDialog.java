package com.lordjoe.runner.ui;

import javax.swing.*;
import java.awt.*;

/**
 * com.lordjoe.runner.ui.AlertDialog
 *
 * @author Steve Lewis
 * @date Feb 26, 2007
 */
public abstract class AlertDialog
{
    public static final Class THIS_CLASS = AlertDialog.class;
    public static final AlertDialog EMPTY_ARRAY[] = {};

    public static void showErrorMessage(Exception ex)
    {
         String message = "The FOllowing Error has occurred: " + ex.getMessage();
         showErrorMessage(message);
    }

    public static void showErrorMessage(String message)
    {
         showErrorMessage(null, message);
    }
    public static void showErrorMessage(Component parent,String message)
     {
           showErrorMessage(parent, message, "An Error has Occurred");
     }
    public static void showErrorMessage(Component parent,String message,String title)
     {
           JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
     }


    public static void showWarningMessage(String message)
    {
         showWarningMessage(null, message);
    }
    public static void showWarningMessage(Component parent,String message)
     {
           showWarningMessage(parent, message, "An Error has Occurred");
     }
    public static void showWarningMessage(Component parent,String message,String title)
     {
           JOptionPane.showMessageDialog(parent, message, title, JOptionPane.WARNING_MESSAGE);
     }



    public static void showInformationMessage(String message)
    {
         showInformationMessage(null, message);
    }


    public static void showInformationMessage(Component parent,String message)
     {
           showInformationMessage(parent, message, "An Error has Occurred");
     }


    public static void showInformationMessage(Component parent,String message,String title)
     {
           JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
     }

}
