package com.lordjoe.utilities;



import java.util.*;


/**
 * com.lordjoe.utilities.ResourceString
 *
 * @author slewis
 * @date Feb 21, 2005
 */
public class ResourceString {
    public static final Class THIS_CLASS = ResourceString.class;
    public static final ResourceString EMPTY_ARRAY[] = {};

    public static String getStringFromText(String englishText) {
        return getStringFromText(englishText, Locale.getDefault());
    }


    public static String getStringFromText(String englishText, Locale locale) {
        return INSTANCE.doGetStringFromText(englishText, locale);
    }

    /**
     * convert a parameter to text
     *
     * @param param
     * @return
     */
    public static String parameterToText(String param) {
        return parameterToText(param, Locale.getDefault());
    }


    /**
     * convert a pratmeter to text
     *
     * @param param
     * @return
     */
    public static String parameterToText(String param, Locale l) {
        return INSTANCE.doParameterToText(param, l);
    }


    public static final String RESOURCE_STRING_FILE = "data/ResourceStrings.txt";
    public static final String PARAMETER_STRING_FILE = "data/ParameterizedStrings.properties";
    public static final long WRITE_INTERVAL = 10000; // 10 sec

    private static final ResourceString INSTANCE = new ResourceString();

    private final Set<String> m_ResourceStrings;
    private final Properties m_ParameterizedStrings;
    private boolean m_Dirty;
    private long m_LastWritten;
    private boolean m_ParamsDirty;
    private long m_ParamsLastWritten;

    public ResourceString() {
        String[] items = FileUtilities.readInLines(RESOURCE_STRING_FILE);
        if (null == items)
        	throw new IllegalStateException("Expected resource file '" + RESOURCE_STRING_FILE + "' inaccessible.");
        m_ResourceStrings = new HashSet<String>(Arrays.asList(items));
        m_ParameterizedStrings = FileUtilities.readProperties(PARAMETER_STRING_FILE);
    }


    /**
     * convert a pratmeter to text
     *
     * @param param
     * @return
     */
    protected String doParameterToText(String param, Locale l) {
        perhapsWriteParams();
        String ret = m_ParameterizedStrings.getProperty(param);
        if (ret != null)
            return doGetStringFromText(ret, l);
        if (param.contains(".")) {
            String[] items = param.split("\\.");
            if(items.length > 1)
                ret = items[items.length - 1];
            else
                ret = param;
        }
        else {
            ret = param;
        }

        m_ParameterizedStrings.setProperty(param, ret);
        setParamsDirty(true);
        return doGetStringFromText(ret, l);
    }


    protected void perhapsWriteParams() {
        synchronized (m_ParameterizedStrings) {
            if (!isParamsDirty())
                return;
            long now = System.currentTimeMillis();
            if (now - m_ParamsLastWritten < WRITE_INTERVAL)
                return;
            String[] ret = new String[m_ParameterizedStrings.keySet().size()];
            m_ParameterizedStrings.keySet().toArray(ret);
            Arrays.sort(ret);
            String[] lines = new String[ret.length];
            for (int i = 0; i < ret.length; i++) {
                String s = ret[i];
                String value = m_ParameterizedStrings.getProperty(s);
                lines[i] = s.replace(" ", "\\ ") + "=" + value;
            }
            FileUtilities.writeFileLines(PARAMETER_STRING_FILE, lines);
            setParamsDirty(false);
            m_ParamsLastWritten = now;
        }
    }


    public boolean isParamsDirty() {
        return m_ParamsDirty;
    }

    public void setParamsDirty(boolean pParamsDirty) {
        m_ParamsDirty = pParamsDirty;
    }

    public long getParamsLastWritten() {
        return m_ParamsLastWritten;
    }

    public void setParamsLastWritten(long pParamsLastWritten) {
        m_ParamsLastWritten = pParamsLastWritten;
    }

    protected String doGetStringFromText(String englishText, Locale locale) {
        guaranteeResourceString(englishText);
//        if (locale.equals(PigLatin.PIG_LATIN))
//            return PigLatin.translate(englishText);
        return englishText;
    }


    protected void guaranteeResourceString(String englishText) {
        if (!m_ResourceStrings.contains(englishText)) {
            m_ResourceStrings.add(englishText);
            setDirty(true);
        }
        if (isDirty())
            perhapsWrite();
    }

    public boolean isDirty() {
        return m_Dirty;
    }

    public void setDirty(boolean pDirty) {
        m_Dirty = pDirty;
    }

    public long getLastWritten() {
        return m_LastWritten;
    }

    public void setLastWritten(long pLastWritten) {
        m_LastWritten = pLastWritten;
    }

    protected void perhapsWrite() {
        long now = System.currentTimeMillis();
        if (now - m_LastWritten < WRITE_INTERVAL)
            return;
        String[] ret = new String[m_ResourceStrings.size()];
        m_ResourceStrings.toArray(ret);
        Arrays.sort(ret);
        FileUtilities.writeFileLines(RESOURCE_STRING_FILE, ret);
        setDirty(false);
        m_LastWritten = now;
    }

}
