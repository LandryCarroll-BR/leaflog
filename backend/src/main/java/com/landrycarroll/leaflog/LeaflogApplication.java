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

@SpringBootApplication
public class LeaflogApplication {
    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");

        SpringApplication app = new SpringApplication(LeaflogApplication.class);
        app.addInitializers(new DynamicDbInitializer());
        ConfigurableApplicationContext context = app.run(args);

        openBrowser("http://localhost:8080");

        // Construct IO
        UserIO io = new ConsoleIO();

        // Get Plant Service
        PlantService plantService = context.getBean(PlantService.class);

        // Construct controller
        PlantControllerCLI plantController = new PlantControllerCLI(io, plantService);

        // Construct Menu
        new MenuBuilder(io)
                .addOption("Add a Plant", plantController::addPlant)
                .addOption("Delete a Plant", plantController::deletePlant)
                .addOption("Edit a Plant", plantController::editPlant)
                .addOption("Water a plant", plantController::waterPlant)
                .addOption("View plant list", plantController::viewPlantList)
                .addOption("Add plant from file", plantController::addPlantsFromFile)
                .build();
    }

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