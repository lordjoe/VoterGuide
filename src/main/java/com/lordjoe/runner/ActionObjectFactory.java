package com.lordjoe.runner;


/**
 * com.lordjoe.runner.RunActionObjectFactory
 *
 * @author Steve Lewis
 * @date Feb 22, 2007
 */
public abstract class ActionObjectFactory
{
    public static ActionObjectFactory[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ActionObjectFactory.class;

    private ActionObjectFactory() {}

    public static IActionRunner buildActionRunner(IRunAction test, IRunEnvironment pEnv)
    {
        ActionRunnerImpl ret = new ActionRunnerImpl(test, pEnv);
        return ret;
    }




    public static IParameterizedRunActionStep buildRunActionStep(String name) {
        RunActionManager tm = RunActionManager.getInstance();
        IRunAction test = tm.getRunAction(name);
        if(test == null) {
            throw new IllegalArgumentException("Unable to find test " + name); 
        }
        return buildRunActionStep(test);
    }

    public static IParameterizedRunActionStep buildRunActionStep(IRunAction test) {
        return new ParameterizedRunActionStepImpl(test);
    }

//    public IOGCCommand buildUploadCommand()
//    {
//        String id = RunActionUtilities.buildNewCommandId();
//        String[] apps = OGCAPIUtilities.UPLINKABLE_APPLICATIONS;
//        String app = apps[RND.nextInt(apps.length)];
//        app = "OSM"; // only OSM handles commands welll
//        File[] parts = getParts();
//        File part = parts[RND.nextInt(parts.length)];
//        IOGCCommand cmd = OGCAPIUtilities.buildUplinkCommand(id, app, part);
//        m_ActiveCommands.put(id, cmd);
//        incrementNumberCommands();
//        return cmd;
//    }
//
//    public IOGCCommand buildDeleteFilesCommand()
//    {
//        String id = RunActionUtilities.buildNewCommandId();
//        String[] apps = OGCAPIUtilities.DELETABLE_APPLICATIONS;
//        String app = apps[RND.nextInt(apps.length)];
//        String[] files = {"Fee.zip", "Fie.zip"};
//        IOGCCommand cmd = OGCAPIUtilities.buildDeleteFilesCommand(id, app, files);
//        m_ActiveCommands.put(id, cmd);
//        incrementNumberCommands();
//        return cmd;
//    }
//
//
//    public IOGCCommand buildDeletePartCommand()
//    {
//        String id = RunActionUtilities.buildNewCommandId();
//        String[] apps = OGCAPIUtilities.DELETABLE_APPLICATIONS;
//        String app = apps[RND.nextInt(apps.length)];
//        File[] parts = getParts();
//        File part = parts[RND.nextInt(parts.length)];
//        String partId = RunActionUtilities.fileToPartId(part);
//        String[] partArray = {partId};
//        IOGCCommand cmd = OGCAPIUtilities.buildDeletePartsCommand(id, app, partArray);
//        m_ActiveCommands.put(id, cmd);
//        incrementNumberCommands();
//        return cmd;
//    }
//
//
//    public IOGCCommand buildDownloadFilesCommand()
//    {
//        String id = RunActionUtilities.buildNewCommandId();
//        String[] apps = OGCAPIUtilities.DOWNLINKABLE_APPLICATIONS;
//        String app = apps[RND.nextInt(apps.length)];
//        String[] files = {"Fee.zip", "Fie.zip"};
//        IOGCCommand cmd = OGCAPIUtilities.buildDownlinkCommand(id, app, files);
//        m_ActiveCommands.put(id, cmd);
//        incrementNumberCommands();
//        return cmd;
//    }


}
