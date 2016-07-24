package com.lordjoe.ui;

import com.lordjoe.general.UIUtilities;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ResourceBundle;


/**
 * com.lordjoe.ui.DefaultAboutDialog
 *
 * @author slewis
 * @date Feb 21, 2005
 */
public class DefaultAboutDialog extends JDialog  {
    public static final Class THIS_CLASS = DefaultAboutDialog.class;
    public static final DefaultAboutDialog EMPTY_ARRAY[] = {};

    public static final String DEFAULT_IMAGES_DIRECTORY = "ProjectImages";
    public static final String DEFAULT_BACKGROUND_IMAGE = "ProjectImages/Background.jpg";
    public static final int DEFAULT_ABOUT_WIDTH = 800;
    public static final int DEFAULT_ABOUT_HEIGHT = 600;

    public static final boolean STANDARD_DIALOG = true;

    // Force image preload
    private static Preloader DO_PRELOAD = new Preloader();

    private static class Preloader implements Runnable
    {
        private Preloader()
        {
            new Thread(this, "ImagePreloader").start();
        }

        public void run()
        {
            EasterEggPanel.preloadImages();
        }
    }


    private final DefaultFrame m_ParentFrame;
    private JPanel m_MainPanel;
    private String m_TitleString;

    public DefaultAboutDialog(DefaultFrame parent, File imagesDirectory, File backgroundImage)
    {
        super(UIUtilities.buildParentFrame(parent));
        m_ParentFrame = parent;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String versionString = ""; //CodeVersion.INSTANCE.getVersionString();
        setTitleString("About This Application " + versionString);
        if(!STANDARD_DIALOG) {
            setLayout(new BorderLayout());
            m_MainPanel = new EasterEggPanel(imagesDirectory, backgroundImage);
            add(m_MainPanel, BorderLayout.CENTER);
            setSize(DEFAULT_ABOUT_WIDTH, DEFAULT_ABOUT_HEIGHT);
        }
        UIUtilities.centerOnFrame(this, DefaultFrame.getCurrentFrame());
    }

    public DefaultAboutDialog(String title, JFrame parent, File imagesDirectory, File backgroundImage)
    {
        super(parent);
        m_ParentFrame = null;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitleString(title);

        if(!STANDARD_DIALOG) {
            setLayout(new BorderLayout());
            m_MainPanel = new EasterEggPanel(imagesDirectory, backgroundImage);
            add(m_MainPanel, BorderLayout.CENTER);
            setSize(DEFAULT_ABOUT_WIDTH, DEFAULT_ABOUT_HEIGHT);
        }
        UIUtilities.centerOnFrame(this, parent);
    }

    public DefaultAboutDialog(String title)
    {
        super();
        m_ParentFrame = null;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitleString(title);

        setLayout(new BorderLayout());
        if(!STANDARD_DIALOG) {
            setLayout(new BorderLayout());
            m_MainPanel = new EasterEggPanel(null, null);
            add(m_MainPanel, BorderLayout.CENTER);
            setSize(DEFAULT_ABOUT_WIDTH, DEFAULT_ABOUT_HEIGHT);
        }
        UIUtilities.centerOnFrame(this, DefaultFrame.getCurrentFrame());
    }

//    public AppVersion getAppVersion()
//    {
//        String folderName = System.getProperty("user.dir");
//         String product = "QuadroCAS";
//         final String version = CodeVersion.INSTANCE.getVersionString();
//         final String versionDetail = CodeVersion.INSTANCE.getVersionString();
//         final String copyright  = "2006";
//
//        AppVersion ret = new AppVersion(folderName,product,version,versionDetail,copyright);
//        return ret;
//    }


    public String getTitleString()
    {
        return m_TitleString;
    }

    public void setTitleString(String name)
    {
        m_TitleString = name;
        setTitle(m_TitleString);
    }

    /**
     * return ancestor combe frame
     *
     * @return non-null frame
     */
    public DefaultFrame getDefaultFrame()
    {
        return m_ParentFrame.getDefaultFrame();
    }


    public ResourceBundle getResources()
    {
        return getDefaultFrame().getResources();
    }

    public String getResourceString(String nm)
    {
        return getDefaultFrame().getResourceString(nm);
    }

    public Object getResourceObject(String nm)
    {
        return getDefaultFrame().getResourceObject(nm);
    }


    public void setVisible(boolean visible)
    {
        boolean wasVisible = isVisible();
        if (wasVisible == visible)
            return;
        super.setVisible(visible);
        if(!STANDARD_DIALOG) {
             m_MainPanel.setVisible(visible);
        }
    }

}