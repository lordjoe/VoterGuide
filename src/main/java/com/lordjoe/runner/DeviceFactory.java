package com.lordjoe.runner;

import com.lordjoe.exceptions.WrappingException;
import com.lordjoe.lib.xml.NameValue;
import com.lordjoe.logging.LogEnum;
import com.lordjoe.runner.ui.ExposedOperationAction;
import com.lordjoe.runner.ui.ExposedParameterizedOperationAction;
import com.lordjoe.runner.ui.RunnerDisplayProperties;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * com.lordjoe.runner.DeviceFactory
 *
 * @author Steve Lewis
 * @date Feb 26, 2007
 */
public abstract class DeviceFactory
{
    public static DeviceFactory[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = DeviceFactory.class;

    public static final Comparator<Action> CMP = new ActionNameComparator();
    public static ConfigReader gConfig;
    private static List<LogEnum> gExposedLogs = new ArrayList<LogEnum>();



    public static ExposedOperationAction[] buildExposedActions()
    {
        List<ExposedOperationAction> holder = new ArrayList<ExposedOperationAction>();
        accumulateExposedActions(holder);
        accumulateExposedParameterizedActions(holder);

        ExposedOperationAction[] ret = new ExposedOperationAction[holder.size()];
        holder.toArray(ret);
        Arrays.sort(ret, CMP);
        return ret;
    }

    public static void setConfigFile(String pSynthesizerFile)
    {
        gConfig = new ConfigReader(pSynthesizerFile);
    }

    public static File getConfigFile()
    {
        return gConfig.getFile();
    }

    public static ConfigReader getConfig()
    {
        return gConfig;
    }


    public static void tryClassLoad(String item)
    {
        try {
            Class.forName(item);
        }
        catch (ClassNotFoundException e) {
            throw new WrappingException(e);
        }
    }


    protected static void accumulateExposedActions(List<ExposedOperationAction> holder)
    {
        NameValue[] exposedActions = RunnerDisplayProperties.getExposedOperations();
        for (int i = 0; i < exposedActions.length; i++) {
            NameValue exposedAction = exposedActions[i];
            ExposedOperationAction act = new ExposedOperationAction(exposedAction.getName(),
                    (String) exposedAction.getValue(), null);
            holder.add(act);

        }
    }


    protected static void accumulateExposedParameterizedActions(List<ExposedOperationAction> holder)
    {
        NameValue[] exposedActions = RunnerDisplayProperties.getExposedParameterizedRunActions();
        for (int i = 0; i < exposedActions.length; i++) {
            NameValue exposedAction = exposedActions[i];
            ExposedOperationAction act = new ExposedParameterizedOperationAction(
                    exposedAction.getName(),
                    (String) exposedAction.getValue(), null);
            holder.add(act);

        }
    }

    protected static void accumulateExposedParameterizedAilActions(
            List<ExposedOperationAction> holder)
    {
        NameValue[] exposedAIlActions = RunnerDisplayProperties.getExposedParameterizedAilOperations();

        for (int i = 0; i < exposedAIlActions.length; i++) {
            NameValue exposedAction = exposedAIlActions[i];
            ExposedOperationAction act = new ExposedParameterizedOperationAction(
                    exposedAction.getName(),
                    (String) exposedAction.getValue(), null);
            holder.add(act);

        }
    }


    public static ExposedOperationAction[] buildRequirementsRunActions()
    {
        List<ExposedOperationAction> holder = new ArrayList<ExposedOperationAction>();
        accumulateRequirementsRunActions(holder);
        //accumulateExposedParameterizedActions( holder);

        ExposedOperationAction[] ret = new ExposedOperationAction[holder.size()];
        holder.toArray(ret);
        Arrays.sort(ret, CMP);
        return ret;
    }

    protected static void accumulateRequirementsRunActions(List<ExposedOperationAction> holder)
    {
        NameValue[] ExposedRunActions = RunnerDisplayProperties.getRequirementsRunActions();
        for (int i = 0; i < ExposedRunActions.length; i++) {
            NameValue ExposedRunAction = ExposedRunActions[i];
            ExposedOperationAction act = new ExposedOperationAction(ExposedRunAction.getName(),
                    (String) ExposedRunAction.getValue(), null);
            holder.add(act);
        }
    }

    public static ExposedOperationAction[] buildAILComplianceRunActions()
    {
        List<ExposedOperationAction> holder = new ArrayList<ExposedOperationAction>();
        accumulateAILComplianceRunActions(holder);
        accumulateExposedParameterizedAilActions(holder);

        ExposedOperationAction[] ret = new ExposedOperationAction[holder.size()];
        holder.toArray(ret);
        Arrays.sort(ret, CMP);
        return ret;
    }

    protected static void accumulateAILComplianceRunActions(List<ExposedOperationAction> holder)
    {
        NameValue[] ExposedRunActions = RunnerDisplayProperties.getAILComplianceRunActions();
        for (int i = 0; i < ExposedRunActions.length; i++) {
            NameValue ExposedRunAction = ExposedRunActions[i];
            ExposedOperationAction act = new ExposedOperationAction(ExposedRunAction.getName(),
                    (String) ExposedRunAction.getValue(), null);
            holder.add(act);
        }
    }

    public static ExposedOperationAction[] buildExposedRunActions()
    {
        List<ExposedOperationAction> holder = new ArrayList<ExposedOperationAction>();
        accumulateExposedRunActions(holder);
        accumulateExposedParameterizedActions(holder);

        ExposedOperationAction[] ret = new ExposedOperationAction[holder.size()];
        holder.toArray(ret);
        Arrays.sort(ret, CMP);
        return ret;
    }

    protected static void accumulateExposedRunActions(List<ExposedOperationAction> holder)
    {
        NameValue[] ExposedRunActions = RunnerDisplayProperties.getExposedRunActions();
        for (int i = 0; i < ExposedRunActions.length; i++) {
            NameValue ExposedRunAction = ExposedRunActions[i];
            ExposedOperationAction act = new ExposedOperationAction(ExposedRunAction.getName(),
                    (String) ExposedRunAction.getValue(), null);
            holder.add(act);
        }
    }


    public static class ActionNameComparator implements Comparator<Action>
    {

        public int compare(Action o1, Action o2)
        {
            return o1.getValue(Action.NAME).toString().compareTo(
                    o2.getValue(Action.NAME).toString());
        }
    }

//     protected static void accumulateExposedParameterizedActions(
//                                                                 List<Action> holder) {
//         NameValue[] ExposedRunActions = RunnerDisplayProperties.getExposedParameterizedOperations();
//         for (int i = 0; i < ExposedRunActions.length; i++) {
//             NameValue ExposedRunAction = ExposedRunActions[i];
//             Action act = new ExposedParameterizedOperationAction(ExposedRunAction.getName(),
//                     (String) ExposedRunAction.getValue());
//             holder.add(act);
//         }
//     }
}
