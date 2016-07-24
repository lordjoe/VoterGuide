
package com.lordjoe.lib.ftp;

import java.net.*;
import java.io.*;
import java.util.*;
import com.lordjoe.utilities.*;

/**
   DirectoryStructure maps the subdirectories of a 
    target directory
*/
public class DirectoryStructure
{
    private String m_name;
    private DirectoryStructure   m_parent;
    private DirectoryStructure[] m_subdirectories;
        
    public DirectoryStructure(File start,DirectoryStructure parent) 
    {
        m_name = start.getName();
        setParent(parent);
        makeSubdirectories(start);
    }
    
    public DirectoryStructure(String name,DirectoryStructure parent) 
    {
        m_name = name;
        setParent(parent);
    }
    
    public void setParent(DirectoryStructure parent)
    {
        m_parent = parent;
        if(parent != null)
            parent.addSubdirectory(this);
    }
    
    protected void  makeSubdirectories(File start) 
    {
        String[] items = start.list();
        Vector holder = new Vector();
        for(int i = 0; i < items.length; i++) {
             File OneItem = new File(start,items[i]);
             if(OneItem.isDirectory()) {
                holder.addElement(new DirectoryStructure(OneItem,this));
             }
        }
        m_subdirectories = new DirectoryStructure[holder.size()];
        holder.copyInto(m_subdirectories);
    }

    protected void  addSubdirectory(DirectoryStructure added) 
    {
        if(m_subdirectories == null) {
            m_subdirectories = new DirectoryStructure[1];
            m_subdirectories[0] = added;
            return;
        }
        // make sure not present
        for(int i = 0; i < m_subdirectories.length; i++) {
            if(m_subdirectories[0] == added) return;
        }
        DirectoryStructure[] old = m_subdirectories;
        m_subdirectories = new DirectoryStructure[old.length + 1];
        System.arraycopy(old,0,m_subdirectories,0,old.length);
        m_subdirectories[old.length] = added;
    }
    
    public String getName() {  return(m_name); } 
        
    public DirectoryStructure[] getSubdirectories() 
    { 
        return(m_subdirectories); 
    } 
        
    /**
         Makes sure the described directory tree exists
         Use this version on the root directory
         @parem Base - directory name of base directory - ignore root name
         @return - true for success
    */
    public boolean guaranteeExistance(String Base) 
    { 
        File test = new File(Base);
        if(!guaranteeDirectory(test)) return(false);
        DirectoryStructure[] subs = getSubdirectories();
        boolean ret = true;
        if(subs != null) {
            for(int i = 0; i < subs.length; i++) {
                ret &= subs[i].guaranteeExistance(test);
            }
        }
        return(ret);
    } 
    
    /**
         Makes sure the described directory exists
         @parem Base - directory to create if necessary
         @return - true for success
    */
    public boolean guaranteeDirectory(File Base) 
    { 
        if(Base.exists()) {
            if(Base.isDirectory()) 
                return(true);
            else 
            { // exists and is not directory 
                if(!Base.delete()) // try to delete
                    return(false); 
                return(Base.mkdir()); // and to remake as directory
            }
        }
        else { // does not exist
           return(Base.mkdir()); // so create
        }
    }

    /**
         Makes sure the described directory tree exists
         Use this version on subdirectories
         @parem Base - Root File
         @return - true for success
    */
    public boolean guaranteeExistance(File Base) 
    { 
        File test = new File(Base,getName());
        if(!guaranteeDirectory(test)) return(false);
        boolean ret = true;
        DirectoryStructure[] subs = getSubdirectories();
        if(subs != null) {
            for(int i = 0; i < subs.length; i++) {
                ret &= subs[i].guaranteeExistance(test);
            }
        }
        return(ret);
    } 

    /**
         Makes sure the described Structure as Sub1/Sub2 ...
         exists in the current tree
         @parem Path  - String describing the structure
    */
   public void guaranteePath(String Path) 
    { 
        if(Path == null || Path.length() == 0) 
            return;
        String[] items = Util.parseTokenDelimited(Path,'/');
        guaranteePath(items,0);
    } 

    /**
         Makes sure the described Structure as Sub1,Sub2 ...
         exists in the current tree
         @parem Path  - String array describing the directory names
         @parem index  - level to create
    */
    public void guaranteePath(String[] Path,int index) 
    { 
        if(index >= Path.length) return;
        DirectoryStructure Sub = getSubdirectory(Path[index]);
        index++;
        if(index < Path.length)
            Sub.guaranteePath(Path,index);
    } 
    
    /**
         return the requested subdirectory at this level creating a new one
         as needed
         @parem name  - subdirectory name
         @return - Subdirectory with that name - non-null
    */
    public DirectoryStructure getSubdirectory(String name)
    {
        DirectoryStructure ret =  findSubdirectory(name);
        if(ret != null) 
            return(ret);
        return(new DirectoryStructure(name,this));
    }
    
    /**
         return the requested subdirectory at this level or null if 
           not found
         @parem name  - subdirectory name
         @return - Subdirectory with that name - null of not found
    */
    public DirectoryStructure findSubdirectory(String name)
    {
        DirectoryStructure[] subs = getSubdirectories();
        if(subs == null) return(null); // fail;
        for(int i = 0; i < subs.length; i++) {
            if(subs[i].getName().equals(name))
                return(subs[i]); // found
        }
        return(null); // fail;
    }
    
    /**
         Print the hierarchy to the stream - good debugging tool
         @parem out  - output stream
    */
    public void printDirectories(PrintStream out) 
    {
        printDirectories(out,0);
    }

    /**
         Print the hierarchy to the stream - good debugging tool use 
         indent level indent - this can be called recursively
         @parem out  - output stream
         @parem indent  - indent level (times 4)
    */
    protected void printDirectories(PrintStream out,int indent) 
    {
        for(int i = 0; i < indent; i++)
            out.print("    ");
        out.println(getName());
        DirectoryStructure[] subs = getSubdirectories();
        if(subs == null) return;
        for(int i = 0; i < subs.length; i++) {
            subs[i].printDirectories(out,indent + 1);
        }
    }

// end class DirectoryStructure    
}
