package com.landrycarroll.leaflog.infrastructure;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code MenuBuilder} is a utility class for building and displaying a CLI-based menu.
 * It enables developers to register menu options with corresponding {@link Runnable} actions,
 * and allows users to select options through indexed input.
 * <p>
 * The menu is displayed in a loop until the user chooses to exit.
 * This class is particularly useful for CLI-driven applications where user interaction is required.
 * </p>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * new MenuBuilder(io)
 *     .addOption("Add Plant", addPlantController)
 *     .addOption("List Plants", listPlantsController)
 *     .build();
 * }</pre>
 */
public class MenuBuilder {
    private final UserIO io;
    private final Map<String, Runnable> menuOptions = new HashMap<>();

    /**
     * Constructs a new {@code MenuBuilder} with the specified I/O handler.
     *
     * @param io the {@link UserIO} interface used for input/output operations
     */
    public MenuBuilder(UserIO io) {
        this.io = io;
    }

    /**
     * Adds a new option to the menu.
     *
     * @param option     the label to display in the menu
     * @param controller the action to execute when the option is selected
     * @return the current instance of {@code MenuBuilder} for fluent chaining
     */
    public MenuBuilder addOption(String option, Runnable controller) {
        menuOptions.put(option, controller);
        return this;
    }

    /**
     * Builds and displays the menu in a loop. Users can select options by number.
     * Selecting the final option (Exit) ends the loop and exits the menu.
     */
    public void build() {
        io.writeOutput("\n ====== | Main Menu | ======");

        while (true) {
            int index = 1;

            // Display options
            for (String option : menuOptions.keySet()) {
                io.writeOutput(index + ". " + option);
                index++;
            }

            // Display Exit option
            io.writeOutput(index + ". Exit");

            // Get user input
            String input = io.readInput("Choose an option: 1-" + index + " ");

            // Exit if last option is selected
            if (input.equals(String.valueOf(index))) {
                break;
            }

            // Attempt to handle the user's input
            handleInput(input);
        }
    }

    /**
     * Parses and handles user input, executing the corresponding menu option.
     * If the input is invalid, an error message is printed.
     *
     * @param input the user's input string
     */
    private void handleInput(String input) {
        try {
            if (input == null || input.isBlank()) {
                throw new IllegalArgumentException("Must provide an option");
            }

            int parsedInput = Integer.parseInt(input);

            if (parsedInput <= 0 || parsedInput > menuOptions.size()) {
                throw new IllegalArgumentException("Please provide a valid option");
            }

            int index = parsedInput - 1;
            String option = menuOptions.keySet().toArray()[index].toString();
            Runnable controller = menuOptions.get(option);

            controller.run();

        } catch (Exception e) {
            io.writeOutput("Invalid option: " + input + ". " + e.getMessage());
        }
    }
}
