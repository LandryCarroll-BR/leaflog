package com.landrycarroll.leaflog.infrastructure;

import java.util.Scanner;

/**
 * Uses the native Java Scanner, Files, and Paths utilities to implement the UserIO interface.
 *
 * @see UserIO
 */
public class ConsoleIO implements UserIO {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Provide a prompt for user, and then return that
     * input as a string
     */
    @Override
    public String readInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Provide a message that gets printed to the console
     */
    @Override
    public void writeOutput(String message) {
        System.out.println(message);
    }
}