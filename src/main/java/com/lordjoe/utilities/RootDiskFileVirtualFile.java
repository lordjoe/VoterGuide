/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package com.lordjoe.utilities;

import java.io.*;
import java.util.*;

/**
 * com.lordjoe.lib.tools.RootDiskFileVirtualFile
 * @see DiskFileVirtualFile
 */
public class RootDiskFileVirtualFile extends DiskFileVirtualFile 
	implements IRootVirtualFile
{
	private Map m_Files;

	/**
	 * Default constructor for class
	 * NOTE this leaves the class in an illegal state until
	 * a file is set
	 */
	public RootDiskFileVirtualFile() {}

	/**
	 * Constructor 
	 * @param TheFile non-null virtual file
	 */
	public RootDiskFileVirtualFile(File TheFile) {
		this();

		if (!TheFile.exists() ||!TheFile.isDirectory()) {
			throw new IllegalArgumentException("File " + TheFile.getName() 
											   + " does not exist or is not a directory");
		} 

		setFile(null, TheFile);
		buildTree();
	}

	/**
	 * Constructor 
	*
	 * @param TheFile non-null file used for construction
	 */
	public RootDiskFileVirtualFile(String TheFile) {
		this(new File(TheFile));
	}

	/**
	 * Build a tree of all files in the diractory
	 */
	protected void buildTree() {
		m_Files = new HashMap();

		String Path = getPath();
		String Key = ClassPath.cannonicalPath(Path);

		m_Files.put(Key, this);
		buildTree(this, this);
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
			String		  NewFileName = NewFile.getPath();
			String		  Key = ClassPath.cannonicalPath(NewFileName);

			m_Files.put(Key, NewFile);

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

	/**
	 * Remember the fils for further reference
	 * @param  non-null FIle to remember
	 */
	public void registerFile(IVirtualFile TheFile) {
		m_Files.put(TheFile.getPath(), TheFile);
	} 

}



/*--- formatting done in "Onvia Java Convention" style on 04-18-2000 ---*/


