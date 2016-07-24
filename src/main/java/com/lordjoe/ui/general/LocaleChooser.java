package com.lordjoe.ui.general;


/*
 * $Id: LocaleChooser.java,v 1.2 2007/11/30 02:18:05 smlewis Exp $
 *
 * Copyright 2001-2001 Volker H. Simonis.
 *
 * Permission is granted to use this code without restrictions as long as this
 * copyright notice appears in all source files.
 *
 *
 */


import com.lordjoe.lang.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.jar.*;
import java.net.*;
import java.io.File;


public class LocaleChooser extends JComboBox {

  private static ComboBoxRenderer renderer = new ComboBoxRenderer();
  private static final String FLAG_NAME = "flag.gif";

  public LocaleChooser(String resDir, final Container root) {
    setEditable(false);
    setRenderer(renderer);

    String defLang = Locale.getDefault().getLanguage();
    ImageIcon defImage = null;

    URL url = ClassLoader.getSystemResource(resDir);
    if (url != null) {
      URLConnection urlc = null;
      try {
        urlc = url.openConnection();
      }
      catch (Exception e) {
        GeneralUtilities.printString(e.getMessage());
      }
      // If the resources are located in a JAR file, we need this version
      // to get the available locales and flag icons ..
      if (urlc != null && urlc instanceof JarURLConnection) {
        JarURLConnection jurlc = (JarURLConnection)urlc;
        JarFile jarf = null;
        try {
          jarf = jurlc.getJarFile();
        }
        catch (Exception e) {
          GeneralUtilities.printString(e.getMessage());
        }
        if (jarf != null) {
          for (Enumeration en = jarf.entries(); en.hasMoreElements();) {
            JarEntry jare = (JarEntry)en.nextElement();
            String name = jare.getName();
            if (name.endsWith(FLAG_NAME) && name.indexOf(resDir) == 0) {
              String country = "";
              String code =
                name.substring(resDir.length() + 1,
                               name.length() - FLAG_NAME.length() - 1);
              int pos = code.indexOf("_");
              if (pos != -1) {
                country = code.substring(pos + 1);
                code = code.substring(0, pos);
              }

              Locale locale = new Locale(code, country);

              String language = locale.getDisplayLanguage(locale);
              if(code.equals(language)) {
                  language = getLanguage(language);
              }
              ImageIcon image =
                new ImageIcon( ClassLoader.getSystemResource(name),
                               language + " (" + code + ")");
              addItem(image);
              if (code.equals(defLang)) {
                defImage = image;
              }
            }
          }
        }
      }
      // .. else if the resources are in the file system, we use the default
      // version to get the available locales and flag icons.
      else {
        File dir = new File(url.getFile());
        File[] subdirs = dir.listFiles();
        if (subdirs != null) {
          for (int i = 0; i < subdirs.length; i++) {
             if("cvs".equals(subdirs[i].getName().toLowerCase()))
                continue;
            File gifFile = new File(subdirs[i], FLAG_NAME);
            try {
              String country = "";
              String code = subdirs[i].getName();
              int pos = code.indexOf("_");
              if (pos != -1) {
                country = code.substring(pos + 1);
                code = code.substring(0, pos);
              }
              if(code.equals("de"))
                code = "de";
              Locale locale = new Locale(code, country);
              String language = locale.getDisplayLanguage(locale);
                if(code.equals(language)) {
                      language = getLanguage(language);
                  }
                URI uri = gifFile.toURI();
                URL url1 = uri.toURL();
                ImageIcon image =
                new ImageIcon(url1, language + " (" + code + ")");
              addItem(image);
              if (code.equals(defLang)) {
                defImage = image;
              }
            }
            catch (Exception e) {
              GeneralUtilities.printString(e.getMessage());
            }
          }
        }
      }
    }
    else {
      System.err.println("Couldn't find System Resource \"" + resDir + "\"");
    }

     addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          JComboBox cb = (JComboBox)e.getSource();
          String lString = ((ImageIcon)cb.getSelectedItem()).getDescription();
          String localeStr = lString.substring(lString.indexOf('(') + 1,
                                               lString.indexOf(')'));
          Locale locale = new Locale(localeStr, "");
          if (!Locale.getDefault().equals(locale)) {
            GeneralUtilities.printString("Setting default Locale from '" +
                               Locale.getDefault().getLanguage() + "' to '" +
                               locale + "'.");
            Locale.setDefault(locale);
            MLUtils.repaintMLJComponents(root);
          }
        }
      });

    if (defImage != null) setSelectedItem(defImage);
    else setSelectedItem(getSelectedItem());

  }
    public static String getLanguage(String code)
       {
           if("pg".equals(code))
               return "Elbonian";
           if("jp".equals(code))
               return "Japanese";
           return code;
       }


  // We dont want this component to grow..
  public Dimension getMaximumSize() {
    return getPreferredSize();
  }
}

class ComboBoxRenderer extends JLabel implements ListCellRenderer {

  public ComboBoxRenderer() {
    setOpaque(true);
    setHorizontalAlignment(LEFT);
    setVerticalAlignment(CENTER);
  }

  public Component getListCellRendererComponent(JList list, Object value,
                                                int index, boolean isSelected,
                                                boolean cellHasFocus) {
    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    } else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }

    if (value != null) {
      ImageIcon icon = (ImageIcon)value;
      setText(icon.getDescription());
      setIcon(icon);
      setIconTextGap(5);
      setBorder(leftBorder);
    }
    return this;
  }

  private static Border leftBorder = new EmptyBorder(0, 5, 0, 0);
}

