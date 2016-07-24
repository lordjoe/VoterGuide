package com.lordjoe.lang;

import com.lordjoe.utilities.*;

/**
 * com.lordjoe.lang.ITextFilter
 *  interface to a Filter - a filter takes text and produces
 * a filtered version - usually dropping items -
 * sometimed formatting
 * @author slewis
 * @date May 2, 2005
 */
public interface ITextFilter extends INamedObject
{
    public static final Class THIS_CLASS = ITextFilter.class;
    public static final ITextFilter EMPTY_ARRAY[] = {};

    public static final ITextFilter NULL_FILTER = new NullTextFilter();
    /**
     * apply the filter
     * @param imp non-null input text
     * @return non-null possibly empty filtered text
     */
    public String applyFilter(String imp);

    /**
     * text filter which does nothing
     */
    public class NullTextFilter implements ITextFilter
    {
        /**
         * apply the filter
         *
         * @param imp non-null input text
         * @return non-null possibly empty filtered text
         */
        public String applyFilter(String imp)
        {
            return imp;
        }

        /**
         * return the object's name - frequently this is
         * final
         *
         * @return unsually non-null name
         */
        public String getName()
        {
            return "all";
        }
    }

  }