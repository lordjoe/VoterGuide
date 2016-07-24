package com.lordjoe.runner;

import com.lordjoe.lib.xml.*;
import com.lordjoe.utilities.*;
import com.lordjoe.runner.ui.*;

import java.io.*;
import java.text.*;
import java.util.*;
/**
 * com.lordjoe.runner.ConfigReader
 *
 * @author Steve Lewis
 * @date Mar 19, 2007
 */
public class ConfigReader
{
    public static ConfigReader[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ConfigReader.class;

    public static final String CONFIG_ARGS = "config";
    public static final String LOADCLASSES_ARGS = "loadclasses";
    public static final String APPLICATION_ARGS = "application";
    public static final String DISPLAY_ARGS = "Display.configuration.file";
    public static final String TESTS_ARGS = "tests";
    public static final String USER_ARGS = "userId";
    public static final String PASSWORD_ARGS = "password";
    public static final String DEFAULT_PARAMETERS_ARGS = "defaultParameters";
    public static final String RUN_TESTS_ARGS = "RunRunActions";
    public static final String TEST_REPORT_ARGS = "testReport";

    public static final String DEFAULT_REPORT_NAME  ="RunActionReport%DATE%.xml";

    private static String gWaveServerUrl;
   private static boolean gUseExternalOGC;

    public static File getRunActionReportFile()
    {
        String fn = getRunActionReportFileName();
        return new File(fn);
    }


    protected static String getRunActionReportFileName()
    {
        ConfigReader cf = DeviceFactory.getConfig();
        String ret = (String)cf.findConfigProperty(TEST_REPORT_ARGS);
        if(ret == null)
            ret = DEFAULT_REPORT_NAME;
        if(ret.contains("%DATE%"))  {
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String dateStr = df.format(new Date());
            ret = ret.replace("%DATE%",dateStr);
        }
        return ret;
    }



    public static String getWaveServerUrl()
    {
        return gWaveServerUrl;
    }

    public static void setWaveServerUrl(String pWaveServerUrl)
    {
        gWaveServerUrl = pWaveServerUrl;
    }

    public static boolean isUseExternalOGC()
    {
        return gUseExternalOGC;
    }


    public static void setUseExternalOGC(boolean pUseExternalOGC)
    {
        gUseExternalOGC = pUseExternalOGC;
    }

    public static ConfigReader handleArgs(String[] args)
    {
        args = filterArgs(args);
        ConfigReader ret = DeviceFactory.getConfig();
  //      RunActionManager.getInstance().setConfig(ret);
        return ret;
    }


    public static String[] filterArgs(String[] args)
    {
        List<String> holder = new ArrayList<String>();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("-")) {
                i = handleArg(i, args);
            }
            else {
                holder.add(arg);
            }
        }
        ConfigReader conf = DeviceFactory.getConfig();
        String value = (String) conf.findConfigProperty(DEFAULT_PARAMETERS_ARGS);
        if (value != null)
            RunActionManager.getInstance().setDefaultParametersFile(value);

        value = (String) conf.findConfigProperty(RUN_TESTS_ARGS);
        if (value != null)  {
            String[] tests = value.split(";");
            List<String> testHolder = new ArrayList<String>();
            for (int i = 0; i < tests.length; i++) {
                String test = tests[i].trim();
                if(test.length() > 0)
                    testHolder.add(test);
            }

            String[] theRunActions = new String[testHolder.size()];
            testHolder.toArray(theRunActions);
            RunActionManager.getInstance().setRunActionsToRunAtStartup(theRunActions);
        }


     value = (String) conf.findConfigProperty(TESTS_ARGS);
        if (value != null) {
            File theFile = new File(value);
            if (!theFile.exists() || !theFile.isFile() || !theFile.canRead())
                throw new IllegalStateException("Cannot read test file " + value);
            XMLSerializer.parseFile(theFile, RunActionManager.getInstance());
        }
//        value = (String) conf.findConfigProperty(USER_ARGS);
//        if (value != null) {
//            DeviceFactory.setWaveServerUser(value);
//        }
//        value = (String) conf.findConfigProperty(PASSWORD_ARGS);
//        if (value != null) {
//            DeviceFactory.setWaveServerPassword(value);
//        }
        value = (String) conf.findConfigProperty(DISPLAY_ARGS);
        if (value != null) {
            RunnerDisplayProperties.setDisplayPropertiesFile(value);
        }


        String[] items = conf.findConfigList(LOADCLASSES_ARGS);
        if (items != null) {
            for (int i = 0; i < items.length; i++) {
                String item = items[i];
                DeviceFactory.tryClassLoad(item);
            }
        }

        String[] ret = new String[holder.size()];
        holder.toArray(ret);
        return ret;
    }

    protected static int handleArg(int index, String[] args)
    {
        String indicator = args[index].substring(1);
        String value = args[index + 1];
        int nextIndex = index + 1;
        if (CONFIG_ARGS.equalsIgnoreCase(indicator)) {
            DeviceFactory.setConfigFile(value);
            return nextIndex;
        }
        throw new IllegalArgumentException("indicator -" + indicator +
                " is not understood use -"

        );

    }


    private Map<String, Object> m_Values;
    private final File m_File;

    public ConfigReader()
    {
        m_Values = new HashMap<String, Object>();
        m_File = null;
    }

    public ConfigReader(String fileName)
    {
        this(new File(fileName));
    }

    public ConfigReader(File fileName)
    {
        m_Values = new HashMap<String, Object>();
        m_File = fileName;
        readFile(fileName);
    }


    public File getFile()
    {
        return m_File;
    }

    protected void readFile(String fileName)
    {
        File thefile = new File(fileName);
        readFile(thefile);
    }

    private void guaranteeGoodFile(File pThefile)
    {
        if (!pThefile.exists() || !pThefile.isFile() ||
                !pThefile.canRead())
            throw new IllegalArgumentException("Cannot read file " + pThefile.getName());
    }

    protected void readFile(File thefile)
    {
        guaranteeGoodFile(thefile);
        try {
            LineNumberReader rdr = new LineNumberReader(new FileReader(thefile));
            String line = rdr.readLine().trim();
            while (line != null) {
                // handle line extensiones
                while(line.endsWith("\\"))  {
                    line = line.substring(0,line.length() - 1); // drop \
                    String next = rdr.readLine().trim();
                    if(next == null)
                        break;
                    line += next;
                }
                handleLine(line);
                line = rdr.readLine();
            }
        }
        catch (IOException ex) {
            throw new IllegalArgumentException("Cannot read file " + thefile.getName());
        }
    }

    protected void handleLine(String line)
    {
        if (line.startsWith("#"))
            return;
        String[] items = line.split("=");
        if (items.length == 2) {
            handleValue(items[0], items[1]);
        }
    }

    protected void handleValue(String key, String value)
    {
        Object oldVal = m_Values.get(key);
        if (oldVal == null) {
            m_Values.put(key, value);
        }
        else {

        }

    }

    protected void addValue(Object oldVal, String key, String value)
    {
        if (oldVal instanceof String) {
            String[] vals = {(String) oldVal, value};
            m_Values.put(key, vals);
        }
        if (oldVal instanceof String[]) {
            String[] vals = (String[]) Util.addToArray((Object[]) oldVal, value);
            m_Values.put(key, vals);
        }

    }

    public boolean isStandAlone()
    {
        Object sa = getConfigProperty("StandAlone");
        if (!"true".equals(sa))
            return false;
        return true;
    }


    public String[] getConfigList(String prop) throws NonexistantPropertyException
    {
        String[] ret = findConfigList(prop);
        if (ret == null)
            throw new NonexistantPropertyException(prop);
        return ret;
    }

    public String[] findConfigList(String prop) throws NonexistantPropertyException
    {
        Object pval = findConfigProperty(prop);
        if (pval == null)
            return null;
        if (pval instanceof String) {
            String[] ret = {(String) pval};
            return ret;
        }
        if (pval instanceof String[]) {
            return (String[]) pval;
        }
        throw new IllegalStateException("Property " + prop +
                " of of class " + pval.getClass().getName());
    }

    public Object getConfigProperty(String prop) throws NonexistantPropertyException
    {
        Object ret = findConfigProperty(prop);
        if (ret == null)
            throw new NonexistantPropertyException(prop);
        return ret;
    }

    public Object findConfigProperty(String prop)
    {
        return m_Values.get(prop);
    }

    protected String buildNotFoundPropMessage(String prop)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("Property " + prop + " was not found.");
        sb.append(" Properties are");

        Set<String> keys = m_Values.keySet();
        String[] keyStrings = new String[keys.size()];
        keys.toArray(keyStrings);
        Arrays.sort(keyStrings);
        for (int i = 0; i < keyStrings.length; i++) {
            String keyString = keyStrings[i];
            sb.append(keyString);
            sb.append(",");
        }
        return sb.toString();
    }

    public class NonexistantPropertyException extends RuntimeException
    {
        public NonexistantPropertyException(String prop)
        {
            super(buildNotFoundPropMessage(prop));
        }
    }

}
