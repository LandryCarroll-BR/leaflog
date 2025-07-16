package com.landrycarroll.leaflog.infrastructure;

/**
 * An interface that defines an API for I/O operations in this application.
 */
public interface UserIO {
    String readInput(String prompt);

    void writeOutput(String message);
}
