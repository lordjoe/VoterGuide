package com.lordjoe.ui.general;

import com.lordjoe.lang.*;

import javax.swing.*;
import java.util.*;
import java.awt.*;

/*
 * $Id: MLUtils.java,v 1.2 2007/11/30 02:18:05 smlewis Exp $
 *
 * Copyright 2001-2001 Volker H. Simonis.
 *
 * Permission is granted to use this code without restrictions as long as this
 * copyright notice appears in all source files.
 *
 *
 */


/**
 *
 * @version $Revision: 1.2 $ $Date: 2007/11/30 02:18:05 $
 * @author Volker Simonis
 */
public class MLUtils {

  // BEGIN getResourceString1
  public static String getResourceString(String key) {
    if (key == null || key.equals("")) return key;
    else {
      String mainClass = System.getProperty("MainClassName");
      if (mainClass != null) {
        return getResourceString(key, "resources/" + mainClass);
      }
      return getResourceString(key, "resources/ML");
    }
  }
  // END getResourceString1

  // We use a hashtable to cache already opened Resource bundles.
  // REMEBER: this means that we can't add new Resource bundles at run time.
  // BEGIN getResourceString2
  private static Hashtable resourceBundles = new Hashtable();

  public static String getResourceString(String key, String baseName) {
    if (key == null || key.equals("")) return key;
    Locale locale = Locale.getDefault();
    ResourceBundle resource =
      (ResourceBundle)resourceBundles.get(baseName + "_" + locale.toString());
    if (resource == null) {
      try {
        resource = ResourceBundle.getBundle(baseName, locale);
        if (resource != null) {
          resourceBundles.put(baseName + "_" + locale.toString(), resource);
        }
      }
      catch (Exception e) {
        GeneralUtilities.printString(e.getMessage());
      }
    }
    if (resource != null) {
      try {
        String value = resource.getString(key);
        if (value != null) return value;
      }
      catch (java.util.MissingResourceException mre) {}
    }
    return key;
  }
  // END getResourceString2

  public static int getResourceMnemonic(String key) {
    if (key == null || key.equals("")) return 0;
    else {
      String mainClass = System.getProperty("MainClassName");
      if (mainClass != null) {
        return getResourceMnemonic(key, "resources/" + mainClass);
      }
      return getResourceMnemonic(key, "resources/ML");
    }
  }

  public static int getResourceMnemonic(String key, String baseName) {
    String mnemonicKey = key + "Mnemonic";
    String mnemonic = getResourceString(mnemonicKey, baseName);
    if (!mnemonic.equals(mnemonicKey)) {
      return mnemonic.charAt(0);
    }
    else return 0;
  }

  /*
  // BEGIN repaint1
  public static void repaintMLJComponents(Container root) {
    Vector validate = recursiveFindMLJComponents(root);
    for (Enumeration e = validate.elements(); e.hasMoreElements();) {
      JComponent jcomp = (JComponent)e.nextElement();
      jcomp.revalidate();
    }
  }
  // END repaint1
  */
  public static void repaintMLJComponents(Container root) {
    Vector validate = recursiveFindMLJComponents(root);
    Iterator it = repaintWindows.iterator();
    while (it.hasNext()) {
      Container cont = (Container)it.next();
      validate.addAll(recursiveFindMLJComponents(cont));
      // Also add the Dialog or top level window itself.
      validate.add(cont);
    }
    for (Enumeration e = validate.elements(); e.hasMoreElements(); ) {
      Object obj = e.nextElement();
      if (obj instanceof JComponent) {
        JComponent jcomp = (JComponent)obj;
        jcomp.revalidate();
      }
      else if (obj instanceof Window) {
        // This part is for the Dialogs and top level windows added with the
        // 'registerForRepaint()' method.
        Window cont = (Window)obj;
        cont.pack();
      }
    }
  }

  private static Vector repaintWindows = new Vector();

  public static void registerForRepaint(Container dialog) {
    repaintWindows.add(dialog);
  }

  // This method should return all the components which have to be repainted
  // after a locale change. For now, it returns ALL the components beneath
  // the 'root' Container, but for performance reasons, the returned set should
  // be narrowed somehow in the future.
  // BEGIN repaint2
  private static Vector recursiveFindMLJComponents(Container root) {
    // java.awt.Container.getComponents() doesn't return null!
    Component[] tmp = root.getComponents();
    Vector v = new Vector();
    for (int i = 0; i < tmp.length; i++) {
      if (tmp[i] instanceof JComponent) {
        JComponent jcomp = (JComponent)tmp[i];
        if (jcomp.getComponentCount() == 0) {
          v.add(jcomp);
        }
        else {
          v.addAll(recursiveFindMLJComponents(jcomp));
        }
      }
      else if (tmp[i] instanceof Container) {
        v.addAll(recursiveFindMLJComponents((Container)tmp[i]));
      }
    }
    return v;
  }
  // END repaint2

}
