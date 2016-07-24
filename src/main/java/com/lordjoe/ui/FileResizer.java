package com.lordjoe.ui;

import com.sun.image.codec.jpeg.*;
import com.lordjoe.utilities.*;

import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * com.lordjoe.ui.FileResizer
 *
 * @author Steve Lewis
 * @date Mar 25, 2009
 */
public class FileResizer
{
    public static FileResizer[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = FileResizer.class;

    private Properties m_Properties;
    private List<File> m_SourceDirectories;
    private File m_Thumbnails;
    private int m_ThumbnailsWidth = 108;

    private File m_FullSize;
    private int m_FullSizeWidth = 630;

    public FileResizer()
    {
        m_SourceDirectories = new ArrayList<File>();
    }

    public Properties getProperties()
    {
        return m_Properties;
    }

    public void setProperties(Properties pProperties)
    {
        m_Properties = pProperties;
        Enumeration<?> enumeration = pProperties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String test = (String) enumeration.nextElement();
            if (test.startsWith("File.Directory."))
                addSourceDirectories(new File(pProperties.getProperty(test)));
        }
        setThumbnails(new File((String) pProperties.get("Thunbnail.Directory")));
        setFullSize(new File((String) pProperties.get("Display.Directory")));
    }

    public File[] getSourceDirectories()
    {
        return m_SourceDirectories.toArray(Util.EMPTY_FILE_ARRAY);
    }

    public void addSourceDirectories(File added)
    {
        m_SourceDirectories.add(added);
    }

    public File getThumbnails()
    {
        return m_Thumbnails;
    }

    public void setThumbnails(File pThumbnails)
    {
        m_Thumbnails = pThumbnails;
    }


    public int getThumbnailsWidth()
    {
        return m_ThumbnailsWidth;
    }

    public void setThumbnailsWidth(int pThumbnailsWidth)
    {
        m_ThumbnailsWidth = pThumbnailsWidth;
    }

    public File getFullSize()
    {
        return m_FullSize;
    }

    public void setFullSize(File pFullSize)
    {
        m_FullSize = pFullSize;
    }

    public int getFullSizeWidth()
    {
        return m_FullSizeWidth;
    }

    public void setFullSizeWidth(int pFullSizeWidth)
    {
        m_FullSizeWidth = pFullSizeWidth;
    }

    public void shrinkFiles()
    {
        File[] inputs = getSourceDirectories();
        for (int i = 0; i < inputs.length; i++) {
            File input = inputs[i];
            shrinkFiles(input);
        }
    }

    public void shrinkFiles(File input)
    {
        if (!input.exists())
            return;
        File[] images = FileUtilities.getAllFiles(input);
        for (int i = 0; i < images.length; i++) {
            File image = images[i];
            try {
                shrinkFile(image);
            }
            catch(IOException ex)  {

            }
            catch(Exception ex)  {

             }
            catch(OutOfMemoryError ex)  {

            }
 
     
        }
    }

    public void shrinkFile(File input) throws IOException
    {
        if(!isImageFile(input))
            return;
        File thuumbNail = buildThumbnail(input);
        if(thuumbNail.exists())
            return;
        Image img = readImage(input);
        if(img == null)
            return;
        BufferedImage resized = createResizedCopy(img,getThumbnailsWidth());
        ImageIO.write(resized,"JPG",thuumbNail);

        File fs = buildFullSizeFile(input);
         resized = createResizedCopy(img,getFullSizeWidth());
        int width = resized.getWidth();
        int height = resized.getHeight();
        ImageIO.write(resized,"JPG",fs);

    }

    private File buildThumbnail(File input)
    {
        File arent = getThumbnails();
        String child = buildJPGName(input);
        return new File(arent, child);
    }

    private File buildFullSizeFile(File input)
    {
        File arent = getFullSize();
        String child = buildJPGName(input);
        return new File(arent, child);
    }



    public static  boolean isImageFile(File input)
    {
        String child = input.getName().toUpperCase();
        if(child.endsWith(".TIF"))
            return true;
        if(child.endsWith(".TIFF"))
            return true;
        if(child.endsWith(".JPG"))
            return true;
        if(child.endsWith(".GIF"))
            return true;

        return false;
    }

    private String buildJPGName(File input)
    {
        String child = input.getName();
        child = child.replace(".tif",".jpg");
        child = child.replace(".gif",".jpg");
        child = child.replace(".tiff",".jpg");
        child = child.replace(".GIF",".jpg");
        child = child.replace(".JPG",".jpg");
        child = child.replace(".TIF",".jpg");
        return child;
    }


    public Image readImage(File theFile)
    {
        try {
            return ImageIO.read(theFile);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public BufferedImage createResizedCopy(Image originalImage, int scaledWidth)
    {
        int ht = originalImage.getHeight(null);
        int wd = originalImage.getWidth(null);
        int scaledHeight = (scaledWidth * ht)  / wd;
        return createResizedCopy(originalImage, scaledWidth, scaledHeight);
    }

    public BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int pScaledHeight)
    {
        BufferedImage scaledBI = new BufferedImage(scaledWidth, pScaledHeight,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaledBI.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(originalImage, 0, 0, scaledWidth, pScaledHeight, null);
        g.dispose();
        return scaledBI;
    }

    /**
     * This method takes in an image as a byte array (currently supports GIF, JPG, PNG and possibly other formats) and
     * resizes it to have a width no greater than the pMaxWidth parameter in pixels. It converts the image to a standard
     * quality JPG and returns the byte array of that JPG image.
     *
     * @param pImageData the image data.
     * @param pMaxWidth  the max width in pixels, 0 means do not scale.
     * @return the resized JPG image.
     * @throws IOException if the iamge could not be manipulated correctly.
     */
    public byte[] resizeImageAsJPG(byte[] pImageData, int pMaxWidth) throws IOException
    {
        // Create an ImageIcon from the image data
        ImageIcon imageIcon = new ImageIcon(pImageData);
        int width = imageIcon.getIconWidth();
        int height = imageIcon.getIconHeight();
        //      mLog.info("imageIcon width: #0  height: #1", width, height);
        // If the image is larger than the max width, we need to resize it
        if (pMaxWidth > 0 && width > pMaxWidth) {
            // Determine the shrink ratio
            double ratio = (double) pMaxWidth / imageIcon.getIconWidth();
            //        mLog.info("resize ratio: #0", ratio);
            height = (int) (imageIcon.getIconHeight() * ratio);
            width = pMaxWidth;
            //         mLog.info("imageIcon post scale width: #0  height: #1", width, height);
        }
        // Create a new empty image buffer to "draw" the resized image into
        BufferedImage bufferedResizedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        // Create a Graphics object to do the "drawing"
        Graphics2D g2d = bufferedResizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        // Draw the resized image
        g2d.drawImage(imageIcon.getImage(), 0, 0, width, height, null);
        g2d.dispose();
        // Now our buffered image is ready
        // Encode it as a JPEG
        ByteArrayOutputStream encoderOutputStream = new ByteArrayOutputStream();
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(encoderOutputStream);
        encoder.encode(bufferedResizedImage);
        byte[] resizedImageByteArray = encoderOutputStream.toByteArray();
        return resizedImageByteArray;
    }

    public static void main(String[] args) throws IOException
    {
        FileResizer fr = new FileResizer();
        File test = new File(args[0]);
        Properties p = new Properties();
        p.load(new FileReader(test));
        fr.setProperties(p);
        fr.shrinkFiles();
    }

}
