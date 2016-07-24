package com.lordjoe.concurrent;

import java.util.*;

/**
 * com.lordjoe.concurrent.IConflict
 *
 * @author Steve Lewis
 * @date Nov 8, 2005
 */
public interface IConflict
{

    boolean hasConflict(Collection c);

    void addItem(Object added);
}
