package com.lordjoe.lib.ftp;
import java.io.*;
import java.net.*;
import java.util.*;
import com.lordjoe.utilities.*;



public abstract class Manifest 
{
    public static final String MANIFEST_NAME = "DirectManifest.req";
    public static final String REMOTE_MANIFEST_NAME = MANIFEST_NAME + "remote";
    
    
    public static void makeManifest(String directory) 
    {
        File[] TheFiles = getManifestFiles(directory);
        writeFilesManifest(TheFiles,directory);
    }
    
    public static void writeFilesManifest(File[] TheFiles,String directory)
    {
        try {
            FileOutputStream fout = new FileOutputStream(MANIFEST_NAME);
            PrintWriter out = new PrintWriter(fout);
            for(int i = 0; i < TheFiles.length; i++)
                writeManifestString(out,TheFiles[i],directory);
            out.close();
        }
        catch(IOException ex) {
            throw new TransferFailureException("IO Error WritingManifest");
        }
    }
    
    public static File[] getManifestFiles(String directory)
    {
        File testFile = new File(directory);
        return(getManifestFiles(testFile));
    }
    
    protected static File[] getManifestFiles(File directory)
    {
        if(directory.isDirectory()) 
            return(getManifestDirectoryFiles(directory));
  
        File[] ret = { directory };
        return(ret);
    }
    
    protected static File[] getManifestDirectoryFiles(File directory)
    {
        Vector holder = new Vector();
        File[] ret = null;
        String[] items = directory.list();
        for(int i = 0; i < items.length; i++) {
            if(items[i].endsWith(".req")) continue; // do not count req
            if(items[i].endsWith(".tmp")) continue; // do not count tmp
            char FirstChar = items[i].charAt(0);
            if(FirstChar == '.' || FirstChar >= '0' && FirstChar <= '9')
                 continue; // do not count or files starting with . of a digit
            File OneItem = new File(directory,items[i]);
            if(OneItem.isDirectory()) {
                File[] SubItems = getManifestDirectoryFiles(OneItem);
                for(int k = 0; k < SubItems.length; k++) 
                    holder.addElement(SubItems[k]);
            }
            else {
                holder.addElement(OneItem);
            }
        }
        ret = new File[holder.size()];
        holder.copyInto(ret);
        return(ret);
    }
   
    protected static void writeManifestString(PrintWriter out,File TheFile,String directory)
    {
        String name = TheFile.getName();
        out.print(name + "\t");
        if(name.startsWith("Button"))
            Assertion.doNada();
        
        String relativePath = makeRelativePath(TheFile.getParent(),directory);
        String AdjustRelative = relativePath.replace(File.separatorChar,'/');
        out.print(AdjustRelative + "\t");
        
        out.print(Long.toString(TheFile.length()) + "\t");
        
        String timeString = makeTimeString(TheFile.lastModified());
        out.println(timeString);
    }

    //
    // return MMDDhhmmYYYY.ss assuming Date is seconds 
    //   since 1970
    // 
    protected static FarTime BaseDate = new FarTime(1970,0,1);

    public static String makeTimeString(long Date) 
    {
        
        Date /= 1000; // convert to seconds
        int sec = (int)(Date % 60);
        Date = (Date - sec) / 60;
        int FarBase = (int)Date;
        FarTime ThisTime = new FarTime(BaseDate.getTime() + FarBase); //  + getTimeOffset());
        
     //   ThisTime = ThisTime.fromGMT();
        // OK - BIG HACK this is needed to make dates work well
    //   if(ThisTime.inDaylightTime())
     //       ThisTime.add(60);
        
        String out = Util.intString(ThisTime.getMonth() + 1,2);
        out += Util.intString(ThisTime.getDate(),2);
        out += Util.intString(ThisTime.getHours(),2);
        out += Util.intString(ThisTime.getMinutes(),2);
        out += Util.intString(ThisTime.getYear(),4);
        out += "." + Util.intString(sec,2); ;
        return(out);
   }

    public static String makeRelativePath(String path,String directory)
    {
        if(path == null) 
            return("");
        int index = path.indexOf(directory);
        if(index >= 0) {
            String ret = path.substring(index + directory.length()  + 1);
            return(ret);
        }
        else {
            return(path);
        }
    }
    
    public static File[] outdateList(String[] manifestList) {
        File[] ret = null;
        
        return(ret);
    }
    
    public static IFileDescriptor[] getDescribedFiles(String ManifestName)
    {
        String[] lines = FileUtilities.readInLines(ManifestName);
        Vector holder = new Vector();
        for(int i = 0; i < lines.length; i++)
            holder.addElement(getDescribedFile(lines[i]));
        
        IFileDescriptor[] ret = new IFileDescriptor[holder.size()];
        holder.copyInto(ret);
        return(ret);
    }
    
    public static IFileDescriptor getDescribedFile(String TheLine)
    {
        String[] tokens = Util.parseTabDelimited(TheLine);
        return(new ManifestFileDescriptor(tokens));
    }

    public static DirectoryStructure getDescribedDirectories(IFileDescriptor[] Files)
    {
       DirectoryStructure root = new DirectoryStructure("<root>",null);
        for(int i = 0; i < Files.length; i++)
            root.guaranteePath(Files[i].getPath());
        return(root);
    }
// end classManifest    
}
