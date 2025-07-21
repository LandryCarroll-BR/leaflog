package com.landrycarroll.leaflog.plantmanagement.controllers;

import com.landrycarroll.leaflog.infrastructure.ConsoleIO;
import com.landrycarroll.leaflog.infrastructure.UserIO;
import com.landrycarroll.leaflog.plantmanagement.domain.entities.Plant;
import com.landrycarroll.leaflog.plantmanagement.exceptions.DomainValidationException;
import com.landrycarroll.leaflog.plantmanagement.exceptions.PlantException;
import com.landrycarroll.leaflog.plantmanagement.services.PlantService;
import com.landrycarroll.leaflog.plantmanagement.dtos.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * {@code PlantControllerCLI} provides a command-line interface (CLI) for interacting with
 * plant-related features of the application. It delegates all business logic to {@link PlantService}
 * and interacts with the user via a {@link UserIO} implementation (e.g., {@link ConsoleIO}).
 *
 * <p>This controller supports operations such as adding, editing, deleting, listing, and watering plants,
 * as well as bulk importing plants from a file.</p>
 */
public class PlantControllerCLI {
    private final UserIO io;
    private final PlantService service;

    /**
     * Constructs a new {@code PlantControllerCLI} using the given {@link UserIO} and {@link PlantService}.
     *
     * @param io           the I/O handler to interact with the user
     * @param plantService the service handling business logic for plants
     */
    public PlantControllerCLI(UserIO io, PlantService plantService) {
        this.io = new ConsoleIO();
        this.service = plantService;
    }

    /**
     * Prompts the user for plant details and creates a new plant.
     * Outputs success or error messages based on the outcome.
     */
    public void addPlant() {
        while (true) {
            try {
                String name = io.readInput("Enter the name of the plant: ");
                String species = io.readInput("Enter the species of the plant: ");
                String wateringFrequencyAsString = io.readInput("Enter the wateringFrequency: ");
                String notes = io.readInput("Enter any notes for the plant: ");

                Plant plant = service.createPlant(new PlantDto.Request(
                        name, species, new Date(), Integer.parseInt(wateringFrequencyAsString), notes));

                io.writeOutput("Plant added successfully!");
                io.writeOutput(plant.toString());
                io.writeOutput("\n");
                break;

            } catch (PlantException.PlantAlreadyExists e) {
                io.writeOutput("A plant with that ID already exists. Please try again.\n");
            } catch (PlantException.InvalidInput | DomainValidationException e) {
                io.writeOutput("Invalid Input! " + e.getMessage() + "\n");
                break;
            } catch (Exception e) {
                io.writeOutput("An unexpected error occurred! " + e.getMessage());
                break;
            }
        }
    }

    /**
     * Prompts the user for a file path and attempts to import plant records from that file.
     * Each valid line results in a new plant being created and printed to the screen.
     */
    public void addPlantsFromFile() {
        String path = io.readInput("Enter file path:");

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String entry = line.trim();
                if (entry.isEmpty()) continue;

                try {
                    String[] fields = entry.split("-", 5);
                    String name = fields.length > 0 ? fields[0].trim() : "";
                    String species = fields.length > 1 ? fields[1].trim() : "";
                    String wateringFrequency = fields.length > 2 ? fields[2].trim() : "";
                    String notes = fields.length > 3 ? fields[3].trim() : "";

                    Plant plant = service.createPlant(new PlantDto.Request(
                            name, species, new Date(), Integer.parseInt(wateringFrequency), notes));
                    io.writeOutput(plant.toString());
                } catch (PlantException e) {
                    io.writeOutput(e.getMessage());
                }
            }
        } catch (IOException e) {
            io.writeOutput("Failed to read file: " + e.getMessage());
        } catch (Exception e) {
            io.writeOutput("Unknown error occurred: " + e.getMessage());
        }
    }

    /**
     * Prompts the user for a plant ID and deletes the corresponding plant.
     * Prints a success or failure message.
     */
    public void deletePlant() {
        try {
            String plantId = io.readInput("Enter the id of the plant: ");
            boolean success = service.deletePlant(UUID.fromString(plantId));

            if (success) {
                io.writeOutput("Plant deleted successfully!");
            } else {
                io.writeOutput("Plant could not be deleted!");
            }

            io.writeOutput("\n");
        } catch (PlantException.InvalidInput | PlantException.PlantNotFound | DomainValidationException e) {
            io.writeOutput("Error: " + e.getMessage());
        } catch (Exception e) {
            io.writeOutput("An unexpected error occurred! " + e.getMessage());
        }
    }

    /**
     * Prompts the user for updated plant details and updates the corresponding plant record.
     */
    public void editPlant() {
        try {
            String id = io.readInput("Enter the Id of the plant: ");
            String name = io.readInput("Enter the name of the plant: ");
            String species = io.readInput("Enter the species of the plant: ");
            String wateringFrequencyAsString = io.readInput("Enter the wateringFrequency: ");
            String notes = io.readInput("Enter any notes for the plant: ");

            Plant plant = service.updatePlant(UUID.fromString(id),
                    new PlantDto.Request(name, species, new Date(), Integer.parseInt(wateringFrequencyAsString), notes));

            io.writeOutput("Plant updated successfully!");
            io.writeOutput(plant.toString());
            io.writeOutput("\n");

        } catch (PlantException.InvalidInput | PlantException.PlantNotFound | DomainValidationException e) {
            io.writeOutput("Error: " + e.getMessage());
        } catch (Exception e) {
            io.writeOutput("An unexpected error occurred! " + e.getMessage());
        }
    }

    /**
     * Retrieves and prints the full list of plants.
     */
    public void viewPlantList() {
        try {
            List<Plant> plantList = service.findAll();
            io.writeOutput("Plant List:");
            for (Plant plant : plantList) {
                io.writeOutput(plant.toString());
            }
            io.writeOutput("\n");
        } catch (Exception e) {
            io.writeOutput("An unexpected error occurred! " + e.getMessage());
        }
    }

    /**
     * Prompts the user for a plant ID and marks that plant as watered.
     */
    public void waterPlant() {
        try {
            String id = io.readInput("Enter the Id of the plant: ");
            Plant plant = service.markAsWatered(UUID.fromString(id));

            io.writeOutput("Plant updated watering frequency:");
            io.writeOutput(plant.toString());
            io.writeOutput("\n");

        } catch (PlantException.InvalidInput | PlantException.PlantNotFound | DomainValidationException e) {
            io.writeOutput("Error: " + e.getMessage());
        } catch (Exception e) {
            io.writeOutput("An unexpected error occurred! " + e.getMessage());
        }
    }
}
