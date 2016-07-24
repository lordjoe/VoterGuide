package com.lordjoe.runner.ui;


import com.lordjoe.general.UIUtilities;
import com.lordjoe.lib.xml.NameValue;
import com.lordjoe.logging.LogEnum;
import com.lordjoe.runner.RunStateEnum;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

/**
 * com.lordjoe.runner.ui.DisplayProperties
 *
 * @author Steve Lewis
 * @date Feb 26, 2007
 */
public class RunnerDisplayProperties
{
    public static final Class THIS_CLASS = RunnerDisplayProperties.class;
    public static final RunnerDisplayProperties EMPTY_ARRAY[] = {};

   private static String gDisplayPropertiesFile;
    public static String getDisplayPropertiesFile()
    {
        return gDisplayPropertiesFile;
    }

    public static void setDisplayPropertiesFile(String pDisplayPropertiesFile)
    {
        gDisplayPropertiesFile = pDisplayPropertiesFile;
    }

    public static final String UNKNOWN_NAME = "unknown";
    public static final Color WASTE_COLOR = new Color(255, 165, 0); // Orange

    public static final String EXPOSED_OPERATIONS_PROPERTY = "ExposedOperations";
    public static final String EXPOSED_LOGS_PROPERTY = "ExposedLogs";
    public static final String EXPOSED_TESTS_PROPERTY = "ExposedRunActions";
    public static final String REQUIREMENTS_TESTS_PROPERTY = "RequirementsRunActions";
    public static final String AIL_COMPLIANCE_TESTS_PROPERTY = "AILComplianceRunActions";
    public static final String AIL_PARAMETERIZED_COMPLIANCE_TESTS_PROPERTY = "ExposedParameterizedAILRunActions";
    public static final String EXPOSED_PARAMETERIZED_OPERATIONS_PROPERTY =
            "ExposedParameterizedOperations";
    public static final String EXPOSED_PARAMETERIZED_TESTS_PROPERTY =
            "ExposedParameterizedRunActions";
    public static final String UNLISTED_STEPS_PROPERTY =
            "UnlistedSteps";
    public static final String EDITOR_IGNORES_PROPERTY =
            "propertyEditorIgnores";
    public static final String ADVANCED_PROPERTIES_PROPERTY =
            "advancedProperties";

    private static boolean gSystemIsSlow = true;

    public static boolean isSystemIsSlow()
    {
        return gSystemIsSlow;
    }

    public static void setSystemIsSlow(boolean systemIsSlow)
    {
        gSystemIsSlow = systemIsSlow;
    }

    private static final Map<String, Color> gDisplayNameToColor =
            new HashMap<String, Color>();
    private static final Map<RunStateEnum, Color> gRunActionStateToToColor =
            new HashMap<RunStateEnum, Color>();
    private static final List<LogEnum> gExposedLogs =
            new ArrayList<LogEnum>();
    private static final Map<String, String> gExposedOperations =
            new HashMap<String, String>();
    private static final Map<String, String> gExposedRunActions =
            new HashMap<String, String>();
    private static final Map<String, String> gRequirementsRunActions =
            new HashMap<String, String>();
    private static final Map<String, String> gAILComplianceRunActions =
        new HashMap<String, String>();
    private static final Map<String, String> gExposedParameterizedAilOperations =
            new HashMap<String, String>();
    private static final Map<String, String> gExposedParameterizedOperations =
            new HashMap<String, String>();
    private static final Map<String, String> gExposedParameterizedRunActions =
            new HashMap<String, String>();
    private static final Set<String> gUnlistedSteps =
            new HashSet<String>();
    private static final Set<String> gEditorIgnores =
            new HashSet<String>();
    private static final Set<String> gPropertyInAdvancedEditor =
            new HashSet<String>();

    public static Color displayNameToColor(String name)
    {
        guaranteeMapPopulated();
        Color ret = gDisplayNameToColor.get(name);
        if (ret == null)
            return Color.black;
        if (ret == null)
            throw new IllegalArgumentException("cannot find color for " + name);
        return ret;
    }


    public static Color testStatusToColor(RunStateEnum name)
    {
        guaranteeMapPopulated();
        if(name == null)
            name = RunStateEnum.NotRun;
        Color ret = gRunActionStateToToColor.get(name);
        if (ret == null)
            return Color.lightGray;
        if (ret == null)
            throw new IllegalArgumentException("cannot find color for " + name);
        return ret;
    }


    public static boolean isStepUnlisted(String step)
    {
        guaranteeMapPopulated();
        return gUnlistedSteps.contains(step);
    }


    public static boolean isPropertyIgnoredInEditor(String step)
    {
        guaranteeMapPopulated();
        return gEditorIgnores.contains(step);
    }

    public static boolean isPropertyInAdvancedEditor(String step)
    {
        guaranteeMapPopulated();
        return gPropertyInAdvancedEditor.contains(step);
    }

    public static NameValue[] getExposedOperations()
    {
        guaranteeMapPopulated();
        return UIUtilities.mapToNameValues(gExposedOperations);
    }

    public static LogEnum[] getExposedLogs()
    {
        guaranteeMapPopulated();
        return gExposedLogs.toArray(LogEnum.EMPTY_ARRAY);
    }


    public static NameValue[] getExposedRunActions()
    {
        guaranteeMapPopulated();
        return UIUtilities.mapToNameValues(gExposedRunActions);
    }

    public static NameValue[] getRequirementsRunActions()
    {
        guaranteeMapPopulated();
        return UIUtilities.mapToNameValues(gRequirementsRunActions);
    }

    public static NameValue[] getAILComplianceRunActions()
    {
        guaranteeMapPopulated();
        return UIUtilities.mapToNameValues(gAILComplianceRunActions);
    }

    public static NameValue[] getExposedParameterizedOperations()
    {
        guaranteeMapPopulated();
        return UIUtilities.mapToNameValues(gExposedParameterizedOperations);
    }

    public static NameValue[] getExposedParameterizedAilOperations()
    {
        guaranteeMapPopulated();
        return UIUtilities.mapToNameValues(gExposedParameterizedAilOperations);
    }

    public static NameValue[] getExposedParameterizedRunActions()
    {
        guaranteeMapPopulated();
        return UIUtilities.mapToNameValues(gExposedParameterizedRunActions);
    }

    /**
     * get the names of exposed operations
     * @return
     */
    public static String[] getExposedOperationNames()
    {
        Set<String> holder = new HashSet<String>();
        NameValue[] values =  getExposedOperations();
        for (int i = 0; i < values.length; i++) {
            NameValue value = values[i];
            holder.add(value.getName());
        }
        values =  getExposedParameterizedOperations();
        for (int i = 0; i < values.length; i++) {
            NameValue value = values[i];
            holder.add(value.getName());
        }
        String[] ret = new String[holder.size()];
        holder.toArray(ret);
        Arrays.sort(ret);
        return ret;
    }

    /**
     * get the names of exposed operations
     * @return
     */
    public static String[] getExposedRunActionNames()
    {
        Set<String> holder = new HashSet<String>();
        NameValue[] values =  getExposedRunActions();
        for (int i = 0; i < values.length; i++) {
            NameValue value = values[i];
            holder.add(value.getName());
        }
        values =  getExposedParameterizedOperations();
        for (int i = 0; i < values.length; i++) {
            NameValue value = values[i];
            holder.add(value.getName());
        }
        String[] ret = new String[holder.size()];
        holder.toArray(ret);
        Arrays.sort(ret);
        return ret;
    }




    protected static void guaranteeMapPopulated()
    {
        synchronized (gExposedOperations) {
            if (!gExposedOperations.isEmpty())
                return;
            try {
                String propertiesFile = getDisplayPropertiesFile();
                InputStream is = new FileInputStream(propertiesFile);
                Properties props = new Properties();
                props.load(is);
                mapToColors(props);
                mapToOperations(props);
            }
            catch (IOException e) {
                throw new RuntimeException(e);  //ToDo Add better handling
            }

        }
    }

    public static void mapToOperations(Properties prop)
    {
        String exposedOps = prop.getProperty(EXPOSED_OPERATIONS_PROPERTY);
        handleOperationsProperty(exposedOps, gExposedOperations);

        String exposedLogs = prop.getProperty(EXPOSED_LOGS_PROPERTY);
   //     handleLogProperty(exposedLogs, gExposedLogs);

        String exposedRunActions = prop.getProperty(EXPOSED_TESTS_PROPERTY);
        handleOperationsProperty(exposedRunActions, gExposedRunActions);

        String requirementsRunActions = prop.getProperty(REQUIREMENTS_TESTS_PROPERTY);
        handleOperationsProperty(requirementsRunActions, gRequirementsRunActions);

        String ailComplianceRunActions = prop.getProperty(AIL_COMPLIANCE_TESTS_PROPERTY);
        handleOperationsProperty(ailComplianceRunActions, gAILComplianceRunActions);

        String ailParameterizedComplianceRunActions = prop.getProperty(AIL_PARAMETERIZED_COMPLIANCE_TESTS_PROPERTY);
        handleOperationsProperty(ailParameterizedComplianceRunActions, gExposedParameterizedAilOperations);

        String exposedParameterizedOps = prop.getProperty(
                EXPOSED_PARAMETERIZED_OPERATIONS_PROPERTY);
        handleOperationsProperty(exposedParameterizedOps, gExposedParameterizedOperations);

        String exposedParameterizedRunActions = prop.getProperty(
                EXPOSED_PARAMETERIZED_TESTS_PROPERTY);
        handleOperationsProperty(exposedParameterizedRunActions, gExposedParameterizedRunActions);


        String unlistedSteps = prop.getProperty(UNLISTED_STEPS_PROPERTY);
        if (unlistedSteps != null) {
            String[] items = unlistedSteps.split(",");
            for (int i = 0; i < items.length; i++) {
                String item = items[i];
                gUnlistedSteps.add(item);
            }
        }
        String ignoredProperties = prop.getProperty(EDITOR_IGNORES_PROPERTY);
        if (ignoredProperties != null) {
            String[] items = ignoredProperties.split(",");
            for (int i = 0; i < items.length; i++) {
                String item = items[i];
                gEditorIgnores.add(item);
            }
        }
        String advancedProperties = prop.getProperty(ADVANCED_PROPERTIES_PROPERTY);
        if (ignoredProperties != null) {
            String[] items = advancedProperties.split(",");
            for (int i = 0; i < items.length; i++) {
                String item = items[i];
                gPropertyInAdvancedEditor.add(item);
            }
        }

    }


    protected static void handleLogProperty(String prop, List<LogEnum> logs)
    {
        if(prop == null)
            return;
        String[] items = prop.split(",");
        for (int i = 0; i < items.length; i++) {
            String item = items[i];
            logs.add((LogEnum)LogEnum.valueOf(LogEnum.class,item));

        }
    }


    protected static void handleOperationsProperty(String prop, Map<String, String> theMap)
    {
        if(prop == null)
            return;
        String[] items = prop.split(";");
        for (int i = 0; i < items.length; i++) {
            String item = items[i];
            handleOperationsItem(item, theMap);

        }
    }

    protected static void handleOperationsItem(String item, Map<String, String> theMap)
    {
        String[] items = item.split(":");
        if (items.length != 2)
            throw new IllegalArgumentException("Must have 2 items separated by \';\' " +
                    item);
        theMap.put(items[0], items[1]);
    }


    protected static void mapToColors(Properties prop)
    {
        for (Object test : prop.keySet()) {

            String testStr = test.toString();
            if (testStr.startsWith("Exposed"))
                continue;
            if (testStr.startsWith("RequirementsRunActions"))
                continue;
            if (testStr.startsWith("AILComplianceRunActions"))
                continue;
            if (testStr.startsWith("UnlistedSteps"))
                continue;
            if (testStr.startsWith(EDITOR_IGNORES_PROPERTY))
                continue;
            if (testStr.startsWith(ADVANCED_PROPERTIES_PROPERTY))
                continue;
            if (testStr.startsWith("app.color.")) {
                mapColor(testStr.substring("app.color.".length()),
                        prop.getProperty(testStr));
            }
            if (testStr.startsWith("RunActionState.color.")) {
                mapStateColor(testStr.substring("RunActionState.color.".length()),
                        prop.getProperty(testStr));
            }
            else {
                Color theColor = stringToColor(prop.getProperty(testStr));
                gDisplayNameToColor.put(testStr, theColor);
            }
        }
    }

    protected static void mapColor(String key, String colorStr)
    {
        Color fluidColor = stringToColor(colorStr);
        gDisplayNameToColor.put(key, fluidColor);
    }

    protected static void mapStateColor(String key, String colorStr)
    {
        Color fluidColor = stringToColor(colorStr);
        RunStateEnum ts = RunStateEnum.valueOf(key);
       gRunActionStateToToColor.put(ts, fluidColor);
    }

    protected static Color stringToColor(String colorStr)
    {
        try {
            String[] items = colorStr.split(",");
            int r = Integer.parseInt(items[0]);
            int g = Integer.parseInt(items[1]);
            int b = Integer.parseInt(items[2]);
            return new Color(r, g, b);
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();  // break here
            throw ex;
        }
    }


}
