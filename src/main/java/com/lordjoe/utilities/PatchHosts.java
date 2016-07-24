package com.lordjoe.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * com.lordjoe.utilities.PatchHosts
 *
 * @author stevel
 *         created Oct 8, 2007
 */
public class PatchHosts {
    public static PatchHosts[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = PatchHosts.class;
    public static final  String HOSTS_PATH = "/Windows/System32/drivers/etc/hosts";

    private static String[] readHostsFile() {
        File test = new File(HOSTS_PATH);
        if(test.exists() && test.isFile())  {
            return FileUtilities.readInLines(test);
        }
        throw new IllegalArgumentException("Cannof find file " + test.getAbsolutePath());
      }

    public static void main(String[] args) {
        String[] patches = FileUtilities.readInLines("data/hostPatches.txt");
        String[] lines = readHostsFile();
        boolean altered = false;
        List<String> holder = new ArrayList<String>(Arrays.asList(lines));
        for (int i = 0; i < patches.length; i++) {
            String line = patches[i];
            if(!holder.contains(line)) {
                holder.add(line);
                altered = true;
            }
        }
        String[] hosts = holder.toArray(new String[0]);
        FileUtilities.writeFileLines(HOSTS_PATH,hosts);
    }


}
