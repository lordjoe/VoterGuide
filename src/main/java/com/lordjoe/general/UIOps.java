package com.lordjoe.general;

/**
 * com.lordjoe.runner.ui.general.UIOps
 *
 * @author Steve Lewis
 * @date Mar 9, 2007
 */
public abstract class UIOps
{
    public final static UIOps[] EMPTY_ARRAY = {};
    public final static Class THIS_CLASS = UIOps.class;
    private UIOps() {}

   /**
     * HTML works well for multiline tooltips
     *
     * @param in non-html string
     * @return Html out
     */
    public static String convertTooltipToHtml(String in)
    {
        if (in.startsWith("<html>"))
            return in;
        StringBuffer sb = new StringBuffer();
        sb.append("<html>\n");
        for (int i = 0; i < in.length(); i++) {
            char c = in.charAt(i);
            if (c == '\n')
                sb.append("\n<br>\n");
            else
                sb.append(c);
        }
        sb.append("</html>\n");
        String s = sb.toString();
        return s;
    }
}
