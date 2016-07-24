package com.lordjoe.ui;

import javax.swing.filechooser.FileFilter;
import java.util.*;
import java.io.*;

/**
 * com.lordjoe.runner.ui.SwingExtensionFileFilter
 *
 * @author Steve Lewis
 * @date Feb 26, 2007
 */
/**
 * A convenience implementation of FileFilter that filters out
 * all files except for those type extensions that it knows about.
 * <p/>
 * Extensions are of the type ".foo", which is typically found on
 * Windows and Unix boxes, but not on Macinthosh. Case is ignored.
 * <p/>
 * Example - create a new filter that filerts out all files
 * but gif and jpg image files:
 * <p/>
 * JFileChooser chooser = new JFileChooser();
 * ExtensionFileFilter filter = new ExtensionFileFilter(
 * new String{"gif", "jpg"}, "JPEG & GIF Images")
 * chooser.addChoosableFileFilter(filter);
 * chooser.showOpenDialog(this);
 */
public class SwingExtensionFileFilter extends javax.swing.filechooser.FileFilter
{

    private static String TYPE_UNKNOWN = "Type Unknown";
    private static String HIDDEN_FILE = "Hidden File";

    private final Map<String, FileFilter> filters;
    private Collection m_AlreadyUsed;
    private String description = null;
    private String fullDescription = null;
    private boolean useExtensionsInDescription = true;

    /**
     * Creates a file filter. If no filters are added, then all
     * files are accepted.
     *
     * @see #addExtension
     */
    public SwingExtensionFileFilter()
    {
        this.filters = new HashMap<String, FileFilter>();
    }

    /**
     * Creates a file filter that accepts files with the given extension.
     * Example: new ExtensionFileFilter("jpg");
     *
     * @see #addExtension
     */
    public SwingExtensionFileFilter(String extension)
    {
        this(extension, null);
    }

    /**
     * Creates a file filter that accepts the given file type.
     * Example: new ExtensionFileFilter("jpg", "JPEG Image Images");
     *                                                                                          <p>
     * Note that the "." before the extension is not needed. If
     * provided, it will be ignored.
     *
     * @see #addExtension
     */
    public SwingExtensionFileFilter(String extension, String description)
    {
        this();
        if (extension != null) addExtension(extension);
        if (description != null) setDescription(description);
    }

    public Collection getAlreadyUsed()
    {
        return m_AlreadyUsed;
    }

    public void setAlreadyUsed(Collection alreadyUsed)
    {
        m_AlreadyUsed = alreadyUsed;
    }

    /**
     * Creates a file filter from the given string array.
     * Example: new ExtensionFileFilter(String {"gif", "jpg"});
     *                                                                                          <p>
     * Note that the "." before the extension is not needed and
     * will be ignored.
     *
     * @see #addExtension
     */
    public SwingExtensionFileFilter(String[] filters)
    {
        this(filters, null);
    }

    /**
     * Creates a file filter from the given string array and description.
     * Example: new ExtensionFileFilter(String {"gif", "jpg"}, "Gif and JPG Images");
     *                                                                                          <p>
     * Note that the "." before the extension is not needed and will be ignored.
     *
     * @see #addExtension
     */
    public SwingExtensionFileFilter(String[] filters, String description)
    {
        this();
        for (int i = 0; i < filters.length; i++)
        {
            // add filters one by one
            addExtension(filters[i]);
        }
        if (description != null) setDescription(description);
    }

    /**
     * Return true if this file should be shown in the directory pane,
     * false if it shouldn't.
     *                                                                                          <p>
     * Files that begin with "." are ignored.
     *
     * @see #getExtension
     * @see javax.swing.filechooser.FileFilter#accepts
     */
    public boolean accept(File f)
    {
        if (f != null)
        {
            if (f.isDirectory())
            {
                return true;
            }
            String extension = getExtension(f);
            if (extension != null && filters.get(getExtension(f)) != null)
            {
                if (m_AlreadyUsed != null)
                    return !m_AlreadyUsed.contains(f);
                else
                    return true;
            }
            ;
        }
        return false;
    }

    /**
     * Return the extension portion of the file's name .
     *
     * @see #getExtension
     * @see javax.swing.filechooser.FileFilter#accept
     */
    public String getExtension(File f)
    {
        if (f != null)
        {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if (i > 0 && i < filename.length() - 1)
            {
                return filename.substring(i + 1).toLowerCase();
            }
            ;
        }
        return null;
    }

    /**
     * Adds a filetype "dot" extension to filter against.
     *                                                                                          <p>
     * For example: the following code will create a filter that filters
     * out all files except those that end in ".jpg" and ".tif":
     *                                                                                          <p>
     * ExtensionFileFilter filter = new ExtensionFileFilter();
     * filter.addExtension("jpg");
     * filter.addExtension("tif");
     *                                                                                          <p>
     * Note that the "." before the extension is not needed and will be ignored.
     */
    public void addExtension(String extension)
    {
        filters.put(extension.toLowerCase(), this);
        fullDescription = null;
    }


    /**
     * Returns the human readable description of this filter. For
     * example: "JPEG and GIF Image Files (*.jpg, *.gif)"
     *
     * @see setDescription
     * @see setExtensionListInDescription
     * @see isExtensionListInDescription
     * @see javax.swing.filechooser.FileFilter#getDescription
     */
    public String getDescription()
    {
        if (fullDescription == null)
        {
            if (description == null || isExtensionListInDescription())
            {
                fullDescription = description == null ? "(" : description + " (";
                // build the description from the extension list
                Iterator extensions = filters.keySet().iterator();
                if (extensions != null)
                {
                    fullDescription += "." + (String) extensions.next();
                    while (extensions.hasNext())
                    {
                        fullDescription += ", " + (String) extensions.next();
                    }
                }
                fullDescription += ")";
            }
            else
            {
                fullDescription = description;
            }
        }
        return fullDescription;
    }

    /**
     * Sets the human readable description of this filter. For
     * example: filter.setDescription("Gif and JPG Images");
     *
     * @see setDescription
     * @see setExtensionListInDescription
     * @see isExtensionListInDescription
     */
    public void setDescription(String description)
    {
        this.description = description;
        fullDescription = null;
    }

    /**
     * Determines whether the extension list (.jpg, .gif, etc) should
     * show up in the human readable description.
     *                                                                                          <p>
     * Only relevent if a description was provided in the constructor
     * or using setDescription();
     *
     * @see getDescription
     * @see setDescription
     * @see isExtensionListInDescription
     */
    public void setExtensionListInDescription(boolean b)
    {
        useExtensionsInDescription = b;
        fullDescription = null;
    }

    /**
     * Returns whether the extension list (.jpg, .gif, etc) should
     * show up in the human readable description.
     *                                                                                          <p>
     * Only relevent if a description was provided in the constructor
     * or using setDescription();
     *
     * @see getDescription
     * @see setDescription
     * @see setExtensionListInDescription
     */
    public boolean isExtensionListInDescription()
    {
        return useExtensionsInDescription;
    }
}
