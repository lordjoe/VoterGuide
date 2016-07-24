/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package com.lordjoe.utilities;

import java.io.*;
import java.util.*;
import java.util.zip.*;

/**
 * Adaptor class for converting java.io.file into
 * com.lordjoe.lib.tools.IVirtualFile
 */
public class ZipFileVirtualFile implements IVirtualFile {

	/**
	 * Implementing file
	 */
	private ZipEntry	  m_File;

	/**
	 * Implementing file name
	 */
	private String		  m_Name;

	/**
	 * Implementing file path
	 */
	private String		  m_Path;

	/**
	 * Implementing files to add
	 */
	private String[]	  m_SubFiles;

	/**
	 * Parent file
	 */
	private IVirtualFile m_ParentFile;

	/**
	 * Default constructor for class
	 * NOTE this leaves the class in an illegal state until
	 * a file is set
	 */
	public ZipFileVirtualFile() {}

	/**
	 * Constructor 
	*
	 * @param Parent
	 * @param TheFile
	 * @see
	 */
	public ZipFileVirtualFile(IVirtualFile Parent, ZipEntry TheFile) {
		this();

		setEntry(Parent, TheFile);

		// System.out.println(TheFile.getUrl());
	}

	/**
	 * set the implementing file
	 * @param TheFile non-null File
	 */
	protected void setEntry(IVirtualFile Parent, ZipEntry TheFile) {
		m_File = TheFile;
		m_Path = ClassPath.cannonicalPath(TheFile.getName());

		int index = m_Path.lastIndexOf("/");

		if (index > -1) {
			m_Name = m_Path.substring(index + 1);
		} 
		else {
			m_Name = m_Path;
		}

		m_ParentFile = Parent;

		if (m_ParentFile.isRoot()) {
			((RootZipFileVirtualFile) m_ParentFile).registerMainDir(this);
		} 
	} 

	/**
	 * get the implementing file
	 * @return  non-null File
	 */
	protected ZipEntry getEntry() {
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
		return (m_Name);
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
			return (m_ParentFile.getPath());
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
		return (m_Path);
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
		return (true);
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
		return (false);
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
		return (true);
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
		return (!m_File.isDirectory());
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
		return (m_File.getTime());
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
		return (m_File.getSize());
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
		return (m_SubFiles);
	} 

	/**
	 * set names of subfiles
	 * @param SubFileNames non-null array of subfile names
	 */
	protected void setSubFiles(String[] SubFileNames) {
		m_SubFiles = SubFileNames;
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
		return (false);
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
		return (false);
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
		RootZipFileVirtualFile TheRoot = 
			(RootZipFileVirtualFile) getRoot();

		return (TheRoot.getInputStream(getEntry()));
	} 

	/**
	 *  
	 * @param TheRoot non-null root file
	 * @param e non-null enumeration
	 * @return possibly null entry
	 */
	protected ZipEntry buildTree(RootZipFileVirtualFile TheRoot, 
								 Enumeration e) {
		String   Test = null;
		List	 SubFiles = new ArrayList();
		ZipEntry item = null;

		while (e.hasMoreElements()) {
			item = (ZipEntry) e.nextElement();
			Test = item.getName();
			Test = ClassPath.cannonicalPath(Test);

			while (Test.startsWith(getPath())) {
				ZipFileVirtualFile SubFile = new ZipFileVirtualFile(this, 
						item);

				TheRoot.registerFile(SubFile);

				item = SubFile.buildTree(TheRoot, e);

				if (item == null) {
					break;
				} 

				Test = item.getName();
				Test = ClassPath.cannonicalPath(Test);
			} 

			break;
		} 

		m_SubFiles = new String[SubFiles.size()];

		SubFiles.toArray(m_SubFiles);

		return (item);
	} 

}



/*--- formatting done in "Onvia Java Convention" style on 04-18-2000 ---*/


