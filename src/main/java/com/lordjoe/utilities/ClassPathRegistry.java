/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package com.lordjoe.utilities;

import java.io.*;
import java.util.*;

/**
 * Object representing an file in the classpath as registered in ClassPath
 * handles both one and multiple assignments
 * 
 * @author Stave Lewis
 * see ClassPath
 */
public class ClassPathRegistry implements Serializable {
	private IVirtualFile	  m_PrimaryFile;
	private IRootVirtualFile m_PrimaryRoot;
	private List			  m_Secondary;
	private List			  m_SecondaryRoot;

	/**
	 * Constructor 
	 * @see
	 */
	public ClassPathRegistry() {}

	/**
	 * Constructor 
	 * @param Primary non-null virtual file
	 * @param Root non-null virtual file root
	 * @see
	 */
	public ClassPathRegistry(IVirtualFile Primary, 
							   IRootVirtualFile Root) {
		this();

		setPrimaryFile(Primary);
		setPrimaryRoot(Root);
	}

	/**
	 * Add a an itel to the classpath
	 * @param Secondary non-null virtual file
	 * @param Root non-null virtual file root
	 */
	public synchronized void addEntry(IVirtualFile Secondary, 
									  IRootVirtualFile Root) {
		if (m_Secondary == null) {
			m_Secondary = new ArrayList();
			m_SecondaryRoot = new ArrayList();
		} 

		m_Secondary.add(Secondary);
		m_SecondaryRoot.add(Root);
	} 

	/**
	 * Method <Add Comment Here>
	 * @return 
	 * @see setPrimaryFile
	 */
	public IVirtualFile getPrimaryFile() {
		return (m_PrimaryFile);
	} 

	/**
	 * Set the associated file
	 * @param Primary  non-null file
	 * @see getPrimaryFile
	 */
	public void setPrimaryFile(IVirtualFile Primary) {
		if (m_PrimaryFile != null) {
			throw new IllegalStateException("Primary Root already set");
		} 

		m_PrimaryFile = Primary;
	} 

	/**
	 * set root for this path
	 * @param Primary non-null root file
	 * @see getPrimaryRoot
	 */
	public void setPrimaryRoot(IRootVirtualFile Primary) {
		if (m_PrimaryRoot != null) {
			throw new IllegalStateException("Primary already set");
		} 

		m_PrimaryRoot = Primary;
	} 

	/**
	 * return associated file
	 * @return non-null primary root
	 * @see setPrimaryRoot
	 */
	public IRootVirtualFile getPrimaryRoot() {
		return (m_PrimaryRoot);
	} 

	/**
	 * 
	 * @return true if the item entered more than once
	 */
	public boolean isEntryMultiple() {
		return (m_SecondaryRoot != null);
	} 

}



/*--- formatting done in "Onvia Java Convention" style on 04-18-2000 ---*/


