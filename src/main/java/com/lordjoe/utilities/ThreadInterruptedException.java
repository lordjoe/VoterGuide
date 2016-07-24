package com.lordjoe.utilities;

import java.util.*;

/**
 * Exception to throw when a thread is unexpectedly interrupted
 * @author Steve Lewis
 */
public class ThreadInterruptedException extends RuntimeException {
    public ThreadInterruptedException() {
    }

    public ThreadInterruptedException(String s) {
        super(s);
    }

    public ThreadInterruptedException(Exception s) {
        super(s);
    }


}
