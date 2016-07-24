/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package com.lordjoe.utilities;

import java.io.*;
import java.util.*;
import java.util.zip.*;

/**
 * This class represents the current classpath
 */
public class ClassPath {
    
    protected static ClassPath gInstance;
	private IRootVirtualFile[] m_Roots;
	private Map					m_PrimaryClassItem;
	private Map					m_SecondaryClassItems;

	/**
	 * Default constructor for class
	 * NOTE this leaves the class in an illegal state until
	 * a file is set
	 */
	public ClassPath() {
		createClassPathItems();

		m_PrimaryClassItem = new HashMap();
		m_SecondaryClassItems = new HashMap();

		registerClassPathItems();
		validateClassPathItems();
	}

	/**
	 * locate a file given by a path where the path string may be
	 * of the form com/lordjoe/Utilities/MyFile,  com.lordjoe.Utilities.MyFile or
	 * com.lordjoe.Utilities.MyFile
	 * 
	 * @return  a File - possibly null represented by the path
	 */
	public IVirtualFile findFile(String Path) {
		IVirtualFile ret = null;

		for (int i = 0; i < m_Roots.length; i++) {
			ret = m_Roots[i].findFile(Path);

			if (ret != null) {
				return (ret);
			} 
		} 

		return (ret);
	} 

	/**
	 * convert path to form com/lordjoe/MyFile
	 * @param Path - non-null string describing path with separators any mix of
	 * 
	 */
	public static String cannonicalPath(String Path) {
		if (Path.indexOf("\\") > -1) {
			Path = Path.replace('\\', '/');
		} 

		if (Path.endsWith("/")) {
			Path = Path.substring(0, Path.length() - 1);
		} 

		return (Path);
	} 

	/**
	 * allow iteration over all files
	 * 
	 * @return  non-null iterator over all files
	 */
	public Iterator iterateFiles() {
		return (null);
	} 

	/**
	 * Test Code for Classpath
	 * @param args non-null array of arguments
	 */
	protected static List getClassPathItems() {
		String			Classpath = System.getProperty("java.class.path");
		StringTokenizer st = new StringTokenizer(Classpath, ";");
		List			PathItems = new ArrayList();

		while (st.hasMoreTokens()) {
			String element = st.nextToken();

			if (element.equals(".")) {
				element = System.getProperty("user.dir");
			} 

			element = cannonicalPath(element);

			// do not use Set beacause order is important
			if (!PathItems.contains(element)) {
				PathItems.add(element);
			} 
		} 

		return (PathItems);
	} 

	/**
	 * Test Code for Classpath
	 * @param args non-null array of arguments
	 */
	protected void validateClassPathItems() {
		List	 TheRoots = getClassPathItems();
		Iterator it = TheRoots.iterator();

		while (it.hasNext()) {
			File TheRoot = new File(it.next().toString());

			validateClassPathItems(TheRoot);
		} 
	} 

	/**
	 * Test Code for Classpath
	 * @param TestFile non-null file
	 */
	protected void validateClassPathItems(File TestFile) {
		if (!TestFile.exists()) {
			return;
		} 

		if (TestFile.isDirectory()) {
			validateClassPathDirectory(TestFile);
		} 
		else {
			validateClassPathZip(TestFile);
		}
	} 

	/**
	 * Test Code for Classpath
	 * @param TestFile non-null file
	 */
	protected void validateClassPathZip(File TestFile) {
		try {
			ZipFile		Test = new ZipFile(TestFile);
			Enumeration it = Test.entries();

			while (it.hasMoreElements()) {

				// validateClassPathFile(cannonicalPath(DirectoryPath,AllFiles[i]));
			} 
		} 
		catch (IOException ex) {
			ex.printStackTrace();
		} 
	} 

	/**
	 * Test Code for Classpath
	 * @param TestFile non-null file
	 */
	protected void validateClassPathDirectory(File TestFile) {
		String   DirectoryPath = cannonicalPath(TestFile.getPath());
		String[] AllFiles = FileUtilities.getAllFiles(DirectoryPath);

		for (int i = 0; i < AllFiles.length; i++) {
			validateClassPathFile(DirectoryPath, cannonicalPath(AllFiles[i]));
		} 
	} 

	/**
	 * Test Code for Classpath
	 * @param Path path for a non-null file
	 */
	protected void validateClassPathFile(String Dir, String Path) {
		String		  TestPath = Path.substring(Dir.length() + 1);
		IVirtualFile RealFile = findFile(TestPath);

		if (RealFile == null) {
			System.out.println("Cannot Find File " + " Path in Directory " 
							   + " Dir");
		} 
	} 

	/**
	 * Test Code for Classpath
	 * @param args non-null array of arguments
	 */
	protected void registerClassPathItems() {
		for (int i = 0; i < m_Roots.length; i++) {
			registerClassPathItems(m_Roots[i]);
		} 
	} 

	/**
	 * add all files in the path to a Map
	 * @param TheDirectory non-null Root virtual file
	 */
	protected void registerClassPathItems(IRootVirtualFile TheDirectory) {
		Iterator it = TheDirectory.iterateFiles();

		while (it.hasNext()) {
			IVirtualFile TheFile = (IVirtualFile) it.next();

			registerClassPathItem(TheFile, TheDirectory);
		} 
	} 

	/**
	 * add all files in the path to a Map
	 * @param TheFile non-null  virtual file
	 * @param TheDirectory non-null Root virtual file
	 */
	protected void registerClassPathItem(IVirtualFile TheFile, 
										 IRootVirtualFile TheDirectory) {
		String				RegPath = cannonicalPath(TheFile.getPath());
		ClassPathRegistry prev = 
			(ClassPathRegistry) m_PrimaryClassItem.get(TheFile.getPath());

		if (prev == null) {
			m_PrimaryClassItem.put(RegPath, 
								   new ClassPathRegistry(TheFile, 
								   TheDirectory));
		} 
		else {
			prev.addEntry(TheFile, TheDirectory);
		} 
	} 

	/**
	 * build the files representing the classpath
	 */
	protected void createClassPathItems() {
		List	 PathItems = getClassPathItems();
		List	 Holder = new ArrayList();
		Iterator it = PathItems.iterator();

		while (it.hasNext()) {
			String PathItem = it.next().toString();
			File   Test = new File(PathItem);

			if (Test.exists()) {
				if (Test.isDirectory()) {
					System.out.println("Building tree " + Test);

					RootDiskFileVirtualFile TheRoot = 
						new RootDiskFileVirtualFile(Test);

					Holder.add(TheRoot);
				} 
				else {

					// add zip code processing
					System.out.println("Building tree " + Test);

					RootZipFileVirtualFile TheRoot = 
						new RootZipFileVirtualFile(Test);

					Holder.add(TheRoot);
				} 
			} 
		} 

		m_Roots = new IRootVirtualFile[Holder.size()];

		Holder.toArray(m_Roots);
	} 

	/**
	 * Test Code for Classpath
	 * @param args non-null array of arguments
	 */
	public static void main(String[] args) {
		ClassPath ThePath = new ClassPath();

		System.out.println("Done");
	} 

}



/*--- formatting done in "Onvia Java Convention" style on 04-18-2000 ---*/


