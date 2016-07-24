package com.lordjoe.ui.general;

import com.lordjoe.utilities.*;

import javax.swing.table.*;
import java.util.*;
import java.lang.reflect.*;

/**
 * com.lordjoe.ui.general.FilteredPropertyTable
 *   Property table with the ability to filter the displayed contents
 * @author Steve Lewis
 * @date Jul 4, 2008
 */
public class FilteredPropertyTable<T> extends PropertyTable<T>
{
    public static FilteredPropertyTable[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = FilteredPropertyTable.class;

    private final Set<IObjectFilter<T>> m_Filters;
    private final List<T> m_Data;
    private final T[] m_Sample;

    public FilteredPropertyTable(String[] props, T[] data)
    {
        this(props, Util.displayStrings(props), data);
    }

    public FilteredPropertyTable(String[] props, String[] userProps, T[] data,ObjectActionTemplate<T>... actions)
    {
        super(props, userProps, data,actions);
        m_Filters = new HashSet<IObjectFilter<T>>();
        m_Data = new ArrayList<T>(Arrays.asList(data));
        m_Sample = (T[]) Array.newInstance(data.getClass().getComponentType(), 0);
        applyFilters();
    }

    public void setFilter(IObjectFilter<T> filter)
    {
         m_Filters.clear();
         addFilter(filter);
    }

    public void addFilter(IObjectFilter<T> filter)
    {
         m_Filters.add(filter);
    }

    public void removeFilter(IObjectFilter<T> filter)
    {
         m_Filters.remove(filter);
    }

    public void applyFilters()
    {
        IObjectFilter<T>[] filters = m_Filters.toArray(IObjectFilter.EMPTY_ARRAY);
        T[] originalData = m_Data.toArray(m_Sample);
        DefaultTableModel tm = getTableModel();
        // clear
        while (tm.getRowCount() > 0)
            tm.removeRow(0);
        for (int i = 0; i < originalData.length; i++) {
            T t = originalData[i];
            boolean accept = true;
            for (int j = 0; j < filters.length; j++) {
                IObjectFilter<T> t1 = filters[j];
                boolean accapt = t1.acceptable(t);
                if (!accapt) {
                    accept = false;
                    break;
                }
            }
            if(accept) {
               addMember(t);
            }
        }
    }


}
