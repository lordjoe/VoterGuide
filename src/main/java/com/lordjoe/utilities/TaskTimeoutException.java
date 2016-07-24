package com.lordjoe.utilities;

import java.util.*;

/**
 * Exception to throw when a task times out without completion
 * @author Steve Lewis
 */
public class TaskTimeoutException extends RuntimeException {
    public TaskTimeoutException() {
    }

    public TaskTimeoutException(long time) {
        this("Task timed out after " + time + " millisec");
    }

    public TaskTimeoutException(String s) {
        super(s);
    }


}
