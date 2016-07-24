/**{ file
    @name DependencyClassLoader.java
    @function - A classloader which reports Dependencies
    @author> Steven M. Lewis
    @copyright>
	************************
	*  Copyright (c) 98
	*  Steven M. Lewis smlewis@lordjoe.co,
	*  www.LordJoe.com
	************************
    @date> Mon Jun 22 21:48:24 PDT 1998
    @version> 1.0
}*/
package com.lordjoe.utilities;
import java.util.*;
import java.io.*;

public class DependencyClassLoader extends ClassLoader
{
    private Map m_Dependencies;
    private OStack m_Classes;
    public static final Dependency[] NULL_ARRAY = {};
    
    
    public DependencyClassLoader()
    {
        super(java.lang.String.class.getClassLoader());
        m_Dependencies = new HashMap();
        m_Classes = new OStack();
    }
    public static Dependency getDependencies(String ClassName) 
    {
        DependencyClassLoader TheLoader = new DependencyClassLoader();
        TheLoader.clearDependencies();
        try {
            TheLoader.loadClass(ClassName,true);
            return((TheLoader.getClassDependencies(ClassName)));
        }
        catch(ClassNotFoundException ex) {
            return(null);
        } 
    }
    
    public static void listDependencies(String ClassName ,PrintStream out)
    {
        Dependency dep = getDependencies(ClassName);
        dep.list(out);
        
    }
    
    
    
    /**
     * Finds and loads the class with the specified name from the URL search
     * path. Any URLs referring to JAR files are loaded and opened as needed
     * until the class is found.
     *
     * @param name the name of the class
     * @return the resulting class
     * @exception ClassNotFoundException if the class could not be found
     */
    protected Class findClass(final String name)
	 throws ClassNotFoundException
    {
        return(super.findClass(name));
    }
    
    
     /**
     * Loads the class with the specified name.  The default implementation of
     * this method searches for classes in the following order:<p>
     *
     * <ol>
     * <li> Call {@link #findLoadedClass(String)} to check if the class has
     *      already been loaded. <p>
     * <li> Call the <code>loadClass</code> method on the parent class
     *      loader.  If the parent is <code>null</code> the class loader
     *      built-in to the virtual machine is used, instead. <p>
     * <li> Call the {@link #findClass(String)} method to find the class. <p>
     * </ol>
     *
     * If the class was found using the above steps, and the
     * <code>resolve</code> flag is true, this method will then call the
     * {@link #resolveClass(Class)} method on the resulting class object.
     * <p>
     * From JDK1.2, subclasses of ClassLoader are encouraged to override
     * {@link #findClass(String)}, rather than this method.<p>
     *
     * @param     name the name of the class
     * @param     resolve if <code>true</code> then resolve the class
     * @return	  the resulting <code>Class</code> object
     * @exception ClassNotFoundException if the class could not be found
     */
    protected synchronized Class loadClass(String name, boolean resolve)
	throws ClassNotFoundException
    {
       Dependency thisDep = getClassDependencies(name);
       if(m_Classes.size() > 0) {
             ((Dependency)m_Classes.top()).addDependency(thisDep);
       }
       m_Classes.push(thisDep);
       Class ret = super.loadClass(name,resolve);
       m_Classes.pop();
       return(ret);
    }
   
    
    public void clearDependencies()
    {
        m_Dependencies.clear();
    }
    
    public Dependency[] getAllDependencies()
    {
        Collection values = m_Dependencies.values();
        Dependency[] ret = new Dependency[values.size()];
        values.toArray(ret);
        return(ret);
    }
    
    public Dependency getClassDependencies(String ClassName)
    {
        Dependency ret = (Dependency)m_Dependencies.get(ClassName);
        if(ret == null) {
            ret = new Dependency(ClassName);
            m_Dependencies.put(ClassName,ret);
        }
        return(ret);
    }
    
    
    public class Dependency implements Struct
    {
        public String m_ClassName;
        public Dependency[] m_Dependencies;
        
        
        public Dependency(String name) {
            m_ClassName = name;
            m_Dependencies = NULL_ARRAY;
        }
        
        public synchronized void addDependency(Dependency added)
        {
            m_Dependencies = (Dependency[])Util.addToArray(m_Dependencies, added);
        }
        
        public void list(PrintStream out) {
            list(out,0);
        }
        
        protected void list(PrintStream out,int indent)
        {
            Util.indent(out, indent);
            out.println(m_ClassName);
            for(int i = 0; i < m_Dependencies.length; i++) {
                m_Dependencies[i].list(out,indent + 1);
            }
        }
            
        
    }
    
    
    
    public static void main(String[] args) 
    {
        for(int i = 0; i < args.length; i++) {
            listDependencies(args[i],System.out);
        }
    }
}
