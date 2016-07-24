/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package com.lordjoe.utilities;

import java.io.InputStream;
import java.util.Iterator;

/**
 * Common interface for files ot ZipFiles at the top of a hierarchy - typicallly
 * a classpath hierarchy
 * 
 * @author Stave Lewis
 * see IVirtualFile
 */
public interface IRootVirtualFile extends IVirtualFile {

	/**
	 * locate a file given by a path where the path string may be
	 * of the form com/onvia/tools/MyFile,  com.lordjoe.lib.tools.MyFile or
	 * com.lordjoe.lib.tools.MyFile
	 * 
	 * @return  a File - possibly null represented by the path
	 */
	public IVirtualFile findFile(String Path);

	/**
	 * allow iteration over all files
	 * 
	 * @return  non-null iterator over all files
	 */
	public Iterator iterateFiles();

	/**
	 * Remember the fils for further reference
	 * @param  non-null FIle to remember
	 */
	public void registerFile(IVirtualFile TheFile);
}



/*--- formatting done in "Onvia Java Convention" style on 04-18-2000 ---*/


