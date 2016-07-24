/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package com.lordjoe.utilities;

import java.io.*;

/**
 * Adaptor class for converting java.io.file into
 * com.lordjoe.lib.tools.IVirtualFile
 */
public class DiskFileVirtualFile implements IVirtualFile {

	/**
	 * Implementing file
	 */
	private File		  m_File;

	/**
	 * Implementing file
	 */
	private String		  m_Path;

	/**
	 * Implementing file
	 */
	private IVirtualFile m_ParentFile;

	/**
	 * Default constructor for class
	 * NOTE this leaves the class in an illegal state until
	 * a file is set
	 */
	public DiskFileVirtualFile() {}

	/**
	 * Constructor 
	*
	 * @param Parent
	 * @param TheFile
	 */
	public DiskFileVirtualFile(IVirtualFile Parent, File TheFile) {
		this();

		setFile(Parent, TheFile);
	}

	/**
	 * Constructor 
	*
	 * @param Parent
	 * @param TheFile
	 * @param Name
	 */
	public DiskFileVirtualFile(IVirtualFile Parent, File TheFile, 
								 String Name) {
		this(Parent, new File(TheFile, Name));
	}

	/**
	 * Constructor 
	*
	 * @param Parent
	 * @param Name
	 */
	public DiskFileVirtualFile(IVirtualFile Parent, String Name) {
		this(Parent, 
			 new File(((DiskFileVirtualFile) Parent).getFile(), Name));
	}

	/**
	 * set the implementing file
	 * @param TheFile non-null File
	 */
	protected void setFile(IVirtualFile Parent, File TheFile) {
		m_File = TheFile;
		m_ParentFile = Parent;

		if (Parent != null) {
			m_Path = TheFile.getPath().substring(getRoot().getPath().length() 
												 + 1);
		} 
	} 

	/**
	 * get the implementing file
	 * @return  non-null File
	 */
	protected File getFile() {
		return (m_File);
	} 

	/**
	 * Returns the name of the file or directory denoted by this abstract
	 * pathname.  This is just the last name in the pathname's name
	 * sequence.  If the pathname's name sequence is empty, then the empty
	 * string is returned.
	 * 
	 * @return  The name of the file or directory denoted by this abstract
	 * pathname, or the empty string if this pathname's name sequence
	 * is empty
	 */
	public String getName() {
		return (m_File.getName());
	} 

	/**
	 * Returns the pathname string of this abstract pathname's parent, or
	 * <code>null</code> if this pathname does not name a parent directory.
	 * 
	 * <p> The <em>parent</em> of an abstract pathname consists of the
	 * pathname's prefix, if any, and each name in the pathname's name
	 * sequence except for the last.  If the name sequence is empty then
	 * the pathname does not name a parent directory.
	 * 
	 * @return  The pathname string of the parent directory named by this
	 * abstract pathname, or <code>null</code> if this pathname
	 * does not name a parent
	 */
	public String getParent() {
		if (getParentFile() != null) {
			return (m_File.getParent());
		} 

		return (null);
	} 

	/**
	 * Returns the abstract pathname of this abstract pathname's parent,
	 * or <code>null</code> if this pathname does not name a parent
	 * directory.
	 * 
	 * <p> The <em>parent</em> of an abstract pathname consists of the
	 * pathname's prefix, if any, and each name in the pathname's name
	 * sequence except for the last.  If the name sequence is empty then
	 * the pathname does not name a parent directory.
	 * 
	 * @return  The abstract pathname of the parent directory named by this
	 * abstract pathname, or <code>null</code> if this pathname
	 * does not name a parent
	 * 
	 * @since JDK1.2
	 */
	public IVirtualFile getParentFile() {
		return (m_ParentFile);
	} 

	/**
	 * Converts this abstract pathname into a pathname string.  The resulting
	 * string uses the {@link #separator default name-separator character} to
	 * separate the names in the name sequence.
	 * 
	 * @return  The string form of this abstract pathname
	 */
	public String getPath() {
		if (m_Path != null) {
			return (m_Path);
		} 

		return (m_File.getPath());
	} 

	/**
	 * Tests whether the application can read the file denoted by this
	 * abstract pathname.
	 * 
	 * @return  <code>true</code> if and only if the file specified by this
	 * abstract pathname exists <em>and</em> can be read by the
	 * application; <code>false</code> otherwise
	 * 
	 * @throws  SecurityException
	 * If a security manager exists and its <code>{@link
	 * java.lang.SecurityManager#checkRead}</code> method denies
	 * read access to the file
	 */
	public boolean canRead() {
		return (m_File.canRead());
	} 

	/**
	 * Tests whether the application can modify to the file denoted by this
	 * abstract pathname.
	 * 
	 * @return  <code>true</code> if and only if the file system actually
	 * contains a file denoted by this abstract pathname <em>and</em>
	 * the application is allowed to write to the file;
	 * <code>false</code> otherwise.
	 * 
	 * @throws  SecurityException
	 * If a security manager exists and its <code>{@link
	 * java.lang.SecurityManager#checkWrite}</code> method denies
	 * write access to the file
	 */
	public boolean canWrite() {
		return (m_File.canWrite());
	} 

	/**
	 * Tests whether the file denoted by this abstract pathname exists.
	 * 
	 * @return  <code>true</code> if and only if the file denoted by this
	 * abstract pathname exists; <code>false</code> otherwise
	 * 
	 * @throws  SecurityException
	 * If a security manager exists and its <code>{@link
	 * java.lang.SecurityManager#checkRead}</code> method denies
	 * read access to the file
	 */
	public boolean exists() {
		return (m_File.exists());
	} 

	/**
	 * Tests whether the file denoted by this abstract pathname is a
	 * directory.
	 * 
	 * @return <code>true</code> if and only if the file denoted by this
	 * abstract pathname exists <em>and</em> is a directory;
	 * <code>false</code> otherwise
	 * 
	 * @throws  SecurityException
	 * If a security manager exists and its <code>{@link
	 * java.lang.SecurityManager#checkRead}</code> method denies
	 * read access to the file
	 */
	public boolean isDirectory() {
		return (m_File.isDirectory());
	} 

	/**
	 * Tests whether the file denoted by this abstract pathname is a normal
	 * file.  A file is <em>normal</em> if it is not a directory and, in
	 * addition, satisfies other system-dependent criteria.  Any non-directory
	 * file created by a Java application is guaranteed to be a normal file.
	 * 
	 * @return  <code>true</code> if and only if the file denoted by this
	 * abstract pathname exists <em>and</em> is a normal file;
	 * <code>false</code> otherwise
	 * 
	 * @throws  SecurityException
	 * If a security manager exists and its <code>{@link
	 * java.lang.SecurityManager#checkRead}</code> method denies
	 * read access to the file
	 */
	public boolean isFile() {
		return (m_File.isFile());
	} 

	/**
	 * Returns the time that the file denoted by this abstract pathname was
	 * last modified.
	 * 
	 * @return  A <code>long</code> value representing the time the file was
	 * last modified, measured in milliseconds since the epoch
	 * (00:00:00 GMT, January 1, 1970), or <code>0L</code> if the
	 * file does not exist or if an I/O error occurs
	 * 
	 * @throws  SecurityException
	 * If a security manager exists and its <code>{@link
	 * java.lang.SecurityManager#checkRead}</code> method denies
	 * read access to the file
	 */
	public long lastModified() {
		return (m_File.lastModified());
	} 

	/**
	 * Returns the length of the file denoted by this abstract pathname.
	 * 
	 * @return  The length, in bytes, of the file denoted by this abstract
	 * pathname, or <code>0L</code> if the file does not exist
	 * 
	 * @throws  SecurityException
	 * If a security manager exists and its <code>{@link
	 * java.lang.SecurityManager#checkRead}</code> method denies
	 * read access to the file
	 */
	public long length() {
		return (m_File.length());
	} 

	/**
	 * Returns an array of strings naming the files and directories in the
	 * directory denoted by this abstract pathname.
	 * 
	 * <p> If this abstract pathname does not denote a directory, then this
	 * method returns <code>null</code>.  Otherwise an array of strings is
	 * returned, one for each file or directory in the directory.  Names
	 * denoting the directory itself and the directory's parent directory are
	 * not included in the result.  Each string is a file name rather than a
	 * complete path.
	 * 
	 * <p> There is no guarantee that the name strings in the resulting array
	 * will appear in any specific order; they are not, in particular,
	 * guaranteed to appear in alphabetical order.
	 * 
	 * @return  An array of strings naming the files and directories in the
	 * directory denoted by this abstract pathname.  The array will be
	 * empty if the directory is empty.  Returns <code>null</code> if
	 * this abstract pathname does not denote a directory, or if an
	 * I/O error occurs.
	 * 
	 * @throws  SecurityException
	 * If a security manager exists and its <code>{@link
	 * java.lang.SecurityManager#checkRead}</code> method denies
	 * read access to the directory
	 */
	public String[] list() {
		return (m_File.list());
	} 

	/**
	 * Creates the directory named by this abstract pathname, including any
	 * necessary but nonexistent parent directories.  Note that if this
	 * operation fails it may have succeeded in creating some of the necessary
	 * parent directories.
	 * 
	 * @return  <code>true</code> if and only if the directory was created,
	 * along with all necessary parent directories; <code>false</code>
	 * otherwise
	 * 
	 * @throws  SecurityException
	 * If a security manager exists and its <code>{@link
	 * java.lang.SecurityManager#checkRead}</code> method does not
	 * permit the named directory and all necessary parent directories
	 * and to be created
	 */
	public boolean mkdirs() {
		return (m_File.mkdirs());
	} 

	/**
	 * Tests whether the file is at the top ot the Hierarchy
	 * 
	 * @return  <code>true</code> if and only if the file is at the top of the
	 * hierarchy
	 * 
	 * @throws  SecurityException
	 * If a security manager exists and its <code>{@link
	 * java.lang.SecurityManager#checkWrite}</code> method does not
	 * permit the named directory and all necessary parent directories
	 * and to be created
	 */
	public boolean isRoot() {
		return (m_File.getParentFile() == null);
	} 

	/**
	 * Return the root file for the hierarchy
	 * @return non-null IRootVirtualFile
	 */
	public IRootVirtualFile getRoot() {
		if (this instanceof IRootVirtualFile) {
			return ((IRootVirtualFile) this);
		} 

		return (getParentFile().getRoot());
	} 

	/**
	 * Tests whether the file is at the top ot the Hierarchy
	 * 
	 * @return  <code>true</code> if and only if the file is at the top of the
	 * hierarchy
	 * 
	 * @throws  IllegalStateException
	 * If the file does not exist ot cannot be read;
	 * @throws  SecurityException
	 * If a security manager exists and its <code>{@link
	 * java.lang.SecurityManager#checkRead}</code> method does not
	 * permit the named directory and all necessary parent directories
	 * and to be created
	 */
	public InputStream toInputStream() {
		try {
			return (new FileInputStream(m_File));
		} 
		catch (IOException ex) {
			throw new IllegalStateException("Cannot open file " + getName());
		} 
	} 

}



/*--- formatting done in "Onvia Java Convention" style on 04-18-2000 ---*/


