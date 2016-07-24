package com.lordjoe.utilities;

import java.io.File;
import java.util.*;


/*
 * Created by IntelliJ IDEA.
 * User: smlewis
 * Date: Oct 24, 2002
 * Time: 9:38:55 AM
 * To change this template use Options | File Templates.
 */


/*
 * com.lordjoe.Utilities.ReconcileDirectories
 * @author smlewis
 * Date: Oct 24, 2002
 */

public class ReconcileDirectories {
    public static final Class THIS_CLASS = ReconcileDirectories.class;
    public static final ReconcileDirectories[] EMPTY_ARRAY = {};

    private final File m_Source;
    private final File m_Dest;
    private String[] m_ExcludedExtensions = Util.EMPTY_STRING_ARRAY;

    public ReconcileDirectories(String src, String dst) {
        if (src.equals(dst))
            throw new IllegalArgumentException("source and destination are the same");

        File srcFile = new File(src);
        if (!srcFile.exists() || !srcFile.isDirectory())
            throw new IllegalArgumentException("File " + src + " does not exist or is not a directory");
        File dstFile = new File(dst);
        if (!dstFile.exists() || !dstFile.isDirectory())
            throw new IllegalArgumentException("File " + dst + " does not exist or is not a directory");

        m_Source = srcFile;
        m_Dest = dstFile;
    }

    public void reconcile() {
        File[] sourceFiles = FileUtilities.getAllFiles(getSource());
        File[] destFiles = FileUtilities.getAllFiles(getDest());
        Map srcMap = buildMap(sourceFiles, getSource().getPath());
        Map dstMap = buildMap(destFiles, getDest().getPath());
        File[] filesInDestNotSource = buildMapDifference(dstMap, srcMap);
        for (int i = 0; i < filesInDestNotSource.length; i++) {
            File file = filesInDestNotSource[i];
            System.out.println("Deleting file " + file.getPath());
            // file.delete();
        }

        List commonKeys = new ArrayList();
        File[] filesInSourceNotDest = buildMapDifferenceAccumulating(srcMap, dstMap, commonKeys);
        for (int i = 0; i < filesInSourceNotDest.length; i++) {
            File file = filesInSourceNotDest[i];
            System.out.println("Creating file " + file.getPath());
            FileUtilities.copyFileToPath(file,getSource().getPath(), getDest());
        }
        String[] commonNames = Util.collectionToStringArray(commonKeys);
        copyUpdatedFiles(dstMap, srcMap, commonNames);
    }

    // Add elements not indexed in both maps
    protected static void copyUpdatedFiles(Map dst, Map src, String[] common) {
        for (int i = 0; i < common.length; i++) {
            String s = common[i];
            File dstFile = (File) dst.get(s);
            File srcFile = (File) src.get(s);
            long srcModified = srcFile.lastModified();
            long dstModified = dstFile.lastModified();
            long diff = srcModified - dstModified;
            if (diff > 0)  {
                System.out.println("Updating file " + srcFile.getPath());
                FileUtilities.copyFile(srcFile, dstFile);
            }
        }

    }

    // Add elements not indexed in both maps
    protected static File[] buildMapDifferenceAccumulating(Map map1, Map map2, List common) {
        List holder = new ArrayList();
        Object[] keys = map1.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            Object key = keys[i];
            if (map2.get(key) == null)
                holder.add(map1.get(key));
            else
                common.add(key);
        }
        File[] ret = new File[holder.size()];
        holder.toArray(ret);
        return (ret);
    }

    // Add elements not indexed in both maps
    protected static File[] buildMapDifference(Map map1, Map map2) {
        List holder = new ArrayList();
        Object[] keys = map1.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            Object key = keys[i];
            if (map2.get(key) == null)
                holder.add(map1.get(key));
        }
        File[] ret = new File[holder.size()];
        holder.toArray(ret);
        return (ret);
    }

    protected Map buildMap(File[] sourceFiles, String topPath) {
        int len = topPath.length();
        Map ret = new HashMap();
        for (int i = 0; i < sourceFiles.length; i++) {
            File sourceFile = sourceFiles[i];
            String name = sourceFile.getName();
            String[] excludes = getExcludedExtensions();
            boolean use = true;
            for (int j = 0; j < excludes.length; j++) {
                String exclude = excludes[j];
                if (name.endsWith(exclude)) {
                    use = false;
                    break;
                }
            }
            if (use) {
                String path = sourceFile.getPath().substring(len).replace('\\', '/');
                ret.put(path, sourceFile);
            }
        }
        return (ret);
    }

    public File getSource() {
        return m_Source;
    }

    public File getDest() {
        return m_Dest;
    }

    public String[] getExcludedExtensions() {
        return m_ExcludedExtensions;
    }

    public void setExcludedExtensions(String[] excludedExtensions) {
        m_ExcludedExtensions = excludedExtensions;
    }

    public static void main(String[] args) {
        String[] exclude = {".di", "Root", "Entries", "Repository"};
        String src = "H:/eclipse/workspace/UnifiedSignal/webapps/htdocs";
        String dst = "J:/opt/apache/htdocs";
        if(args.length > 0)
            src = args[0];
        if(args.length > 1)
             dst = args[1];

        // String dst = "D:/webapps/htdocs";
        ReconcileDirectories rd = new ReconcileDirectories(src, dst);
        rd.setExcludedExtensions(exclude);
        rd.reconcile();
    }

}