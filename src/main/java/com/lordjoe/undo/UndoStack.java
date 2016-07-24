package com.lordjoe.undo;

import com.lordjoe.exceptions.*;

import java.util.*;
import java.util.concurrent.*;

/**
 * com.lordjoe.undo.UndoStack
 *
 * @author Steve Lewis
 * @date Nov 9, 2008
 */
public class UndoStack
{
    public static UndoStack[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = UndoStack.class;

    private static UndoStack gInstance = new UndoStack();

    public static UndoStack getInstance()
    {
        return gInstance;
    }

    private final Stack<Undoable> m_Undoables;
    private final Stack<Undoable> m_Redoables;
    private final List<UndoStateListener> m_UndoStateListeners;

    public UndoStack()
    {
        m_Undoables = new Stack<Undoable>();
        m_Redoables = new Stack<Undoable>();
        m_UndoStateListeners = new CopyOnWriteArrayList<UndoStateListener>();
    }

    /**
     * add an undoable
     */
    public void pushUndoable(Undoable added)
    {
        synchronized (m_Undoables) {
             m_Undoables.push(added);
        }
        notifyUndoStateListeners();
    }



    /**
     * add a change listener
     * final to make sure this is not duplicated at multiple levels
     *
     * @param added non-null change listener
     */
    public final void addUndoStateListener(UndoStateListener added)
    {
        if (!m_UndoStateListeners.contains(added))
            m_UndoStateListeners.add(added);
    }

    /**
     * remove a change listener
     *
     * @param removed non-null change listener
     */
    public final void removeUndoStateListener(UndoStateListener removed)
    {
        while (m_UndoStateListeners.contains(removed))
            m_UndoStateListeners.remove(removed);
    }


    /**
     * notify any state change listeners - probably should
     * be protected but is in the interface to form an event cluster
     *
     * @param oldState
     * @param newState
     * @param commanded
     */
    public void notifyUndoStateListeners()
    {
        if (m_UndoStateListeners.isEmpty())
            return;
        for (UndoStateListener listener : m_UndoStateListeners) {
            listener.onUndoStateChange();
        }
    }


    public int getUndoCount()
    {
        return m_Undoables.size();
    }


    public int getRedoCount()
    {
        return m_Redoables.size();
    }


    /**
     * undo the action
     */
    public void undo()
    {
        Undoable undone;
        synchronized (m_Undoables) {
            if (m_Undoables.isEmpty())
                return;
            undone = m_Undoables.pop();
        }
        try {
            undone.undo();
        }
        catch (Exception e) {
            e.printStackTrace();
            notifyUndoStateListeners();
            throw new WrappingException(e);
         }
        synchronized (m_Redoables) {
            m_Redoables.push(undone);
        }
        notifyUndoStateListeners();
    }

    /**
     * redo the action
     */
    public void redo()
    {
        Undoable undone;
        synchronized (m_Redoables) {
            if (m_Redoables.isEmpty())
                return;
            undone = m_Redoables.pop();
        }
        try {
            undone.redo();
        }
        catch (Exception e) {
            notifyUndoStateListeners();
            throw new WrappingException(e);
         }
        synchronized (m_Undoables) {
            m_Undoables.push(undone);
        }
        notifyUndoStateListeners();

    }
}
