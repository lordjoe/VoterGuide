/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package com.lordjoe.utilities;

import java.io.*;
import java.util.*;
import java.util.zip.*;

/**
 * com.lordjoe.lib.tools.RootZipFileVirtualFile
 * @see DiskFileVirtualFile
 */
public class RootZipFileVirtualFile extends ZipFileVirtualFile 
	implements IRootVirtualFile {
	private ZipFile m_RootFile;
	private Map		m_Files;
	private List	m_TopDirectories;

	/**
	 * Default constructor for class
	 * NOTE this leaves the class in an illegal state until
	 * a file is set
	 */
	public RootZipFileVirtualFile() {
		m_TopDirectories = new ArrayList();
		m_Files = new HashMap();
	}

	/**
	 * Constructor 
	*
	 * @param TheFile
	 * @see
	 */
	public RootZipFileVirtualFile(File TheFile) {
		this();

		if (!TheFile.exists() ||!TheFile.isFile()) {
			throw new IllegalArgumentException("File " + TheFile.getName() 
											   + " does not exist or is not a zip file");

			// setFile(null,TheFile);
		} 

		try {
			m_RootFile = new ZipFile(TheFile);

			Enumeration e = m_RootFile.entries();

			buildTree(e);
		} 
		catch (IOException ex) {
			throw new IllegalArgumentException("File " + TheFile.getName() 
											   + " does not exist or is not a zip file");
		} 
	}

	/**
	 * Constructor 
	*
	 * @param TheFile
	 * @param ThePath
	 * @see
	 */
	public RootZipFileVirtualFile(String TheFile, ClassPath ThePath) {
		this(new File(TheFile));
	}

	/**
	 * add a top level directory to the registry
	 * @param NewMain non-null top level file
	 */
	public void registerMainDir(IVirtualFile NewMain) {
		m_TopDirectories.add(NewMain);
	} 

	/**
	 * add a file to rememberd files
	 * @param TheFile non-null top file
	 */
	public void registerFile(IVirtualFile TheFile) {
		m_Files.put(TheFile.getPath(), TheFile);
	} 

	/**
	 * Build a tree of all files in the diractory
	 */
	protected void buildTree(Enumeration e) {
		String Test = null;
		List   SubFiles = new ArrayList();

		while (e.hasMoreElements()) {
			ZipEntry item = (ZipEntry) e.nextElement();

			Test = item.getName();
			Test = ClassPath.cannonicalPath(Test);

			int DirIndex = Test.indexOf("/");

			if (DirIndex == -1 || DirIndex == (Test.length() - 1)) {
				SubFiles.add(Test);

				ZipFileVirtualFile current = new ZipFileVirtualFile(this, 
						item);

				item = current.buildTree(this, e);
			} 
		} 

		String[] SubFileNames = new String[SubFiles.size()];

		SubFiles.toArray(SubFileNames);
		setSubFiles(SubFileNames);

		// m_Files.put(cannonicalPath(getUrl()),this);
		// buildTree(this);
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
		return (true);
	} 

	/**
	 * Build a tree of all files in the diractory
	 * @param TheFile non-null Base file - already in the tree and a directory
	 */
	protected void buildTree(IRootVirtualFile TheRoot, 
							 IVirtualFile TheFile) {
		String[] SubFiles = TheFile.list();

		for (int i = 0; i < SubFiles.length; i++) {
			IVirtualFile NewFile = new DiskFileVirtualFile(TheFile, 
					SubFiles[i]);
			String		  NewFileName = NewFile.getName();
			String		  Key = ClassPath.cannonicalPath(NewFileName);

			m_Files.put(Key, NewFile);
			TheRoot.registerFile(NewFile);

			if (NewFile.isDirectory()) {
				buildTree(TheRoot, NewFile);
			} 
		} 
	} 

	/**
	 * locate a file given by a path where the path string may be
	 * of the form com/onvia/tools/MyFile,  com.lordjoe.lib.tools.MyFile or
	 * com.lordjoe.lib.tools.MyFile
	 * 
	 * @return  a File - possibly null represented by the path
	 */
	protected InputStream getInputStream(ZipEntry entry) {
		try {
			return (m_RootFile.getInputStream(entry));
		} 
		catch (IOException ex) {
			throw new IllegalStateException("Cannot open StreaM " 
											+ entry.getName());
		} 
	} 

	/**
	 * locate a file given by a path where the path string may be
	 * of the form com/onvia/tools/MyFile,  com.lordjoe.lib.tools.MyFile or
	 * com.lordjoe.lib.tools.MyFile
	 * 
	 * @return  a File - possibly null represented by the path
	 */
	public IVirtualFile findFile(String Path) {
		Path = ClassPath.cannonicalPath(Path);

		return ((IVirtualFile) m_Files.get(Path));
	} 

	/**
	 * allow iteration over all files
	 * 
	 * @return  non-null iterator over all files
	 */
	public Iterator iterateFiles() {
		return (m_Files.values().iterator());
	} 

}



/*--- formatting done in "Onvia Java Convention" style on 04-18-2000 ---*/


