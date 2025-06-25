package com.landrycarroll.leaflog.infrastructure;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility for creating different menu options in the CLI. Creates a hash map
 * of options that can be selected from the user input. Handles exiting the program.
 */
public class MenuBuilder {
    UserIO io;
    private final Map<String, Runnable> menuOptions = new HashMap<>();

    public MenuBuilder(UserIO io) {
        this.io = io;
    }

    public MenuBuilder addOption(String option, Runnable controller) {
        menuOptions.put(option, controller);
        return this;
    }

    public void build() {
        io.writeOutput("\n ====== | Main Menu | ======");

        // Keep running the cli until user exits
        while (true) {
            // Store the index
            int index = 1;

            // Loop through the option keys and print out menu label
            for (String option : menuOptions.keySet()) {
                io.writeOutput(index + ". " + option);
                index++;
            }

            // Write out the exit option
            io.writeOutput(index + ". Exit");

            // Gather the user's input
            String input = io.readInput("Choose an option: 1-" + index + " ");

            // Exit if the user selects exit option
            if (input.equals(String.valueOf(index))) {
                break;
            }

            // Handle all other input options
            handleInput(input);
        }
    }

    private void handleInput(String input) {
        try {
            // Check for empty input
            if (input == null || input.isBlank()) {
                throw new IllegalArgumentException("Must provide an option");
            }

            // Parse the string input to an int so we can grab index
            int parsedInput = Integer.parseInt(input);

            // Check that the input is a valid option
            if (parsedInput == 0 || parsedInput > menuOptions.size() + 1) {
                throw new IllegalArgumentException("Please provide a valid option");
            }

            // convert input into int
            int index = Integer.parseInt(input) - 1;

            // get runnable key from index
            String option = menuOptions.keySet().toArray()[index].toString();
            Runnable controller = menuOptions.get(option);

            // run the controller
            controller.run();

        } catch (Exception e) {
            io.writeOutput("Invalid option: " + input + e.getMessage());
        }
    }
}
