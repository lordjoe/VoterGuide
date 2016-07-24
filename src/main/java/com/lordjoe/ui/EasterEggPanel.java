package com.lordjoe.ui;

import com.lordjoe.exceptions.ExceptionHandlingManager;
import com.lordjoe.general.UIUtilities;
import com.lordjoe.lang.FileUtil;
import com.lordjoe.lib.xml.XMLSerializer;
import com.lordjoe.utilities.FileUtilities;
import com.lordjoe.utilities.StrokeDrawListener;
import com.lordjoe.utilities.StrokePlayerPanel;
import com.lordjoe.utilities.StrokeSet;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * com.lordjoe.ui.EasterEggPanel
 *
 * @author slewis
 * @date Feb 21, 2005
 */
public class EasterEggPanel extends ImageBackgroundPanel implements FadeListener,
        StrokeDrawListener
{
    public static final Class THIS_CLASS = EasterEggPanel.class;
    public static final EasterEggPanel EMPTY_ARRAY[] = {};

    public static final Random RND = new Random();

    public static final String DEFAULT_IMAGES_DIRECTORY = "ProjectImages";
    public static final String DEFAULT_BACKGROUND_IMAGE = "ProjectImages/Background.jpg";
    public static final int DEFAULT_ABOUT_WIDTH = 800;
    public static final int DEFAULT_ABOUT_HEIGHT = 600;

    public static void preloadImages()
    {
        buildImages(new File(DEFAULT_IMAGES_DIRECTORY), new File(DEFAULT_BACKGROUND_IMAGE));
    }
    
    protected static ImageIcon loadImage(File image)
    {
        try {
            // look for an image to associate
            InputStream inp = new FileInputStream(image);
            if (inp != null) {
                ImageIcon img = new ImageIcon(FileUtil.readInBytes(inp));
                return img;
            }
        }
        catch (IOException ex) {
            ExceptionHandlingManager.log(ex);
        }
        return null;
    }


    protected static ImageIcon[] buildImages(File imageDirectory, File background)
    {
        String[] images = FileUtilities.getAllFilesWithExtension(imageDirectory, "jpg");
        List<ImageIcon> holder = new ArrayList<ImageIcon>();

        for (int i = 0; i < images.length; i++) {
            String image = images[i];
            File imageFile = new File(image);
            if (image.indexOf("Background.") > -1)
                continue;
            ImageIcon icn = ResourceImages.getImage(ResourceImages.FILE_STRING + imageFile);
            holder.add(icn);
        }
        ImageIcon[] ret = new ImageIcon[holder.size()];
        holder.toArray(ret);
        return ret;
    }

    private static StrokeSet buildSignature(File imageDirectory)
    {
        File sifFile = new File(imageDirectory,"Signature.xml");
        if(sifFile.exists() && sifFile.canRead())  {
            StrokeSet signature = new  StrokeSet();
            XMLSerializer.parseFile(sifFile,signature);
            return signature;
        }
        return null;
    }


    private final File m_ImagesDirectory;
    private final ImageIcon[] m_Images;
    private JButton m_Close;
    private int m_CurrentImage;
    private JFadeInLabel m_Fader;
    private JLabel m_VersionLabel;
    private StrokeSet m_SignatureSet;

    private StrokePlayerPanel m_Signature;

    public EasterEggPanel( File imagesDirectory, File backgroundImage)
    {
        super(ResourceImages.getImage(ResourceImages.FILE_STRING + backgroundImage).getImage());
        m_ImagesDirectory = imagesDirectory;
        m_Images = buildImages(m_ImagesDirectory, backgroundImage);
        m_SignatureSet = buildSignature(m_ImagesDirectory);
        setLayout(null);
        setupMainPanel();
        setSize(EasterEggPanel.DEFAULT_ABOUT_WIDTH, EasterEggPanel.DEFAULT_ABOUT_HEIGHT);
    }


    private void setupMainPanel()
    {
        String versionString = "";//CodeVersion.INSTANCE.getVersionString();
        m_VersionLabel = new JLabel("Version " + versionString);
        m_VersionLabel.setFont(UIUtilities.BIG_FONT);
        m_VersionLabel.setOpaque(false);
        add(m_VersionLabel);

        m_Close = new JButton();
        m_Close.setAction(UIUtilities.buildCloseFrameAction(this));
        add(m_Close);

        m_Fader = new JFadeInLabel(null);
        m_Fader.addFadeListener(this);
        add(m_Fader);

        if(m_SignatureSet != null) {
             m_Signature  = new StrokePlayerPanel(m_SignatureSet);
            m_SignatureSet.setTimeFactor(4.0);
             m_Signature.addStrokeDrawListener(this);
            add(m_Signature);
        }
     }

    /**
     * Causes this container to lay out its components.  Most programs
     * should not call this method directly, but should invoke
     * the <code>validate</code> method instead.
     *
     * @see java.awt.LayoutManager#layoutContainer
     * @see #setLayout
     * @see #validate
     * @since JDK1.1
     */
    public void doLayout()
    {
        super.doLayout();
        UIUtilities.centerOnWall(m_Close,this,UIUtilities.BOTTOM_WALL);
        UIUtilities.centerOnWall(m_VersionLabel,this,UIUtilities.TOP_WALL);
        setSignatureSize();
        relocateFader();

    }

    private void setSignatureSize()
    {
        if(m_Signature == null || m_Close == null || m_VersionLabel == null)
            return;
        Insets ins = getInsets();
        int width = getWidth();
        int height = getHeight();
        Rectangle closeLoc =  m_Close.getBounds();
        Rectangle labelLoc =  m_VersionLabel.getBounds();
        int start = (int)labelLoc.getY() + (int)labelLoc.getHeight();
        int end = (int)closeLoc.getY();
        int sigHeight = width / 10;
        m_Signature.setBounds(ins.left,Math.max(start,end - sigHeight),width - ins.left - ins.right,sigHeight );
    }

    private void relocateFader()
    {
        if(m_Fader == null)
             return;
        if(m_Fader.getIcon() == null)
             return;
        Dimension faderSize = setFaderSize();
        int width = getWidth();
        int height = getHeight();
        int width2 = width - faderSize.width;
        int height2 = height - faderSize.height;
        m_Fader.setLocation(RND.nextInt(width2),RND.nextInt(height2));
    }

    private Dimension setFaderSize()
    {
        Icon icn = m_Fader.getIcon();
        int icnWidth = icn.getIconWidth();
        int icnHeight = icn.getIconHeight();
        double icnRatio = (double)icnWidth / (double)icnHeight;
        int width = getWidth();
        int height = getHeight();
        double canvasRatio = (double)width / (double)height;
        int faderWidth = width / 2;
        int faderHeight = height / 2;
         if(icnRatio > canvasRatio)  {
             faderHeight =  (int)(faderHeight * canvasRatio /  icnRatio);
        }
        else {
             faderWidth =  (int)(faderWidth * icnRatio /  canvasRatio);
         }
        m_Fader.setSize(faderWidth,faderHeight);
        return new Dimension(faderWidth,faderHeight);

    }

    public ImageIcon[] getImages()
    {
        return m_Images;
    }




    public void setVisible(boolean visible)
    {
        boolean wasVisible = isVisible();
//        if (wasVisible == visible)
//            return;
        super.setVisible(visible);
        if (visible) {
            if(m_Signature != null)
                 m_Signature.setVisible(false);
            m_CurrentImage = 0;
            showNextImage();
        }
    }


//
//    public void setMainDisplay(JPanel panel)
//    {
//        m_MainPanel.removeAll();
//        m_MainPanel.add(panel, BorderLayout.CENTER);
//    }

    public void onFadeStarted(Fadeable fader)
    {
    }

    public void onFadeEnded(Fadeable fader)
    {
        if (m_Fader.getTransperancy() > 0.5) {
            m_Fader.startFadeOut();
        }
        else {
            boolean hasNext = showNextImage();
            if (hasNext)
                return;
            else {
                if(m_Signature != null)
                    m_Signature.setVisible(true);
            }
        }
        return;
    }

    public void onStrokeDrawStart()
    {
    }

    public void onStrokeDrawEnd()
    {
        if(m_Signature != null)
             m_Signature.setVisible(false);
         m_CurrentImage = 0;
        showNextImage();
    }

    protected boolean showNextImage()
    {
        ImageIcon[] images = getImages();
        if (m_CurrentImage >= images.length)
            return false;

        ImageIcon icn = images[m_CurrentImage++];
        m_Fader.setIcon(icn);
        relocateFader();
        m_Fader.startFadeIn();
        repaint(50);
        return true;
    }




}
