package com.jameslandrum.openptp;

/**
 * LogStub.java
 * Allows for logging to be handled per-platform.
 * (C) 2014 James Landrum
 */

public interface LogStub {
    public static final int INFO    = 0;
    public static final int WARNING = 1;
    public static final int ERROR   = 2;

    public void log(int level, String message);
}
