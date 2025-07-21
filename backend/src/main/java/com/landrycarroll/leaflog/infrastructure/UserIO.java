package com.landrycarroll.leaflog.infrastructure;

/**
 * {@code UserIO} is an interface that defines an abstraction for input/output operations
 * in the application. It decouples the I/O handling logic from the business logic,
 * enabling flexibility for CLI-based implementations, GUI environments, or automated tests.
 * <p>
 * Common implementations may include reading from the console, writing to standard output,
 * or simulating I/O for testing purposes.
 * </p>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * UserIO io = new ConsoleIO();
 * String name = io.readInput("Enter your name: ");
 * io.writeOutput("Hello, " + name);
 * }</pre>
 */
public interface UserIO {

    /**
     * Prompts the user and reads input as a string.
     *
     * @param prompt the message to display before waiting for user input
     * @return the user's input as a string
     */
    String readInput(String prompt);

    /**
     * Outputs the given message to the user.
     *
     * @param message the message to display
     */
    void writeOutput(String message);
}
