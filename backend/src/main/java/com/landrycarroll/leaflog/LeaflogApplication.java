package com.landrycarroll.leaflog;

import com.landrycarroll.leaflog.infrastructure.ConsoleIO;
import com.landrycarroll.leaflog.infrastructure.DynamicDbInitializer;
import com.landrycarroll.leaflog.infrastructure.MenuBuilder;
import com.landrycarroll.leaflog.infrastructure.UserIO;
import com.landrycarroll.leaflog.plantmanagement.controllers.PlantControllerCLI;
import com.landrycarroll.leaflog.plantmanagement.services.PlantService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;
import java.net.URI;

/**
 * The entry point for the LeafLog application.
 * <p>
 * This Spring Boot CLI application allows users to manage a plant care log
 * with options to create, update, delete, view, water, and bulk import plant data.
 * </p>
 * <p>
 * It initializes the SQLite database dynamically, starts the Spring context, opens
 * a browser window for the web interface, and launches an interactive console menu.
 * </p>
 */
@SpringBootApplication
public class LeaflogApplication {

    /**
     * Starts the LeafLog application.
     * <ul>
     *   <li>Sets AWT to headless mode = false (to allow browser to open)</li>
     *   <li>Uses {@link DynamicDbInitializer} to prompt for SQLite DB path</li>
     *   <li>Starts Spring Boot and retrieves necessary beans</li>
     *   <li>Launches a CLI-based menu for interacting with plants</li>
     * </ul>
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");

        // Start Spring Boot with dynamic DB initialization
        SpringApplication app = new SpringApplication(LeaflogApplication.class);
        app.addInitializers(new DynamicDbInitializer());
        ConfigurableApplicationContext context = app.run(args);

        // Optionally open browser to localhost:8080
        openBrowser("http://localhost:8080");

        // Set up CLI dependencies
        UserIO io = new ConsoleIO();
        PlantService plantService = context.getBean(PlantService.class);
        PlantControllerCLI plantController = new PlantControllerCLI(io, plantService);

        // Build CLI menu
        new MenuBuilder(io)
                .addOption("Add a Plant", plantController::addPlant)
                .addOption("Delete a Plant", plantController::deletePlant)
                .addOption("Edit a Plant", plantController::editPlant)
                .addOption("Water a Plant", plantController::waterPlant)
                .addOption("View Plant List", plantController::viewPlantList)
                .addOption("Add Plant from File", plantController::addPlantsFromFile)
                .build();
    }

    /**
     * Opens the default web browser to the specified URL.
     * Logs a message to the console if the platform does not support desktop operations.
     *
     * @param url the URL to open in the default browser
     */
    private static void openBrowser(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                System.out.println("Desktop not supported. Please open " + url + " manually.");
            }
        } catch (Exception e) {
            System.err.println("Failed to open browser: " + e.getMessage());
        }
    }
}
