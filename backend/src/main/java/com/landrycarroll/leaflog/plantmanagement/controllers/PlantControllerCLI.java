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
 * Provides inputs for Plant CRUD operations to Plant Service and returns output to user
 */
public class PlantControllerCLI {
    private final UserIO io;
    private final PlantService service;

    public PlantControllerCLI(UserIO io, PlantService plantService) {
        this.io = new ConsoleIO();
        this.service = plantService;
    }


    public void addPlant() {
        while (true) {
            try {
                // Gather all inputs from user
                String name = io.readInput("Enter the name of the plant: ");
                String species = io.readInput("Enter the species of the plant: ");
                String wateringFrequencyAsString = io.readInput("Enter the wateringFrequency: ");
                String notes = io.readInput("Enter any notes for the plant: ");

                // Pass the values into the use case and attempt to add plant
                Plant plant = service.createPlant(new PlantDto.Request(name, species, new Date(), Integer.parseInt(wateringFrequencyAsString), notes));

                // Create CLI Table headers
                io.writeOutput("Plant added successfully!");

                // Print plant
                io.writeOutput(plant.toString());

                // Extra spacing for CLI
                io.writeOutput("\n");
                break;
            } catch (PlantException.PlantAlreadyExists e) {
                io.writeOutput("A plant with that ID already exists. Please try again.\n");
            } catch (PlantException.InvalidInput e) {
                io.writeOutput("Invalid Input! " + e.getMessage() + "\n");
                break;
            } catch (DomainValidationException e) {
                io.writeOutput("Invalid Data! " + e.getMessage());
                break;
            } catch (Exception e) {
                io.writeOutput("An unexpected error occurred! " + e.getMessage());
                break;
            }
        }
    }

    public void addPlantsFromFile() {
        // Create CLI prompts
        String path = io.readInput("Enter file path:");

        // Try-with-resources statement that uses the BufferedReader. This syntax provides ease of use,
        // since we don't have to manually close the resource.
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line; // store the value of the line that's being read

            // Loop through the file while there's still a line
            while ((line = reader.readLine()) != null) {
                // Grab the entry
                String entry = line.trim();

                // Check for an empty line and tell the loop to continue
                if (entry.isEmpty()) continue;

                try {
                    String[] fields = entry.split("-", 5);

                    // Get the fields that exist
                    String name = fields.length > 0 ? fields[0].trim() : "";
                    String species = fields.length > 1 ? fields[1].trim() : "";
                    String wateringFrequency = fields.length > 2 ? fields[2].trim() : "";
                    String notes = fields.length > 3 ? fields[3].trim() : "";

                    Plant plant = service.createPlant(new PlantDto.Request(
                            name,
                            species,
                            new Date(),
                            Integer.parseInt(wateringFrequency),
                            notes
                    ));

                    io.writeOutput(plant.toString());

                } catch (PlantException e) {
                    io.writeOutput(e.getMessage());
                }
            }

        } catch (IOException e) {
            // Handle IO error
            io.writeOutput("Failed to read file: " + e.getMessage());
        } catch (Exception e) {
            io.writeOutput("Unknown error occurred: " + e.getMessage());
        }
    }

    public void deletePlant() {
        try {
            // Gather all inputs from user
            String plantId = io.readInput("Enter the id of the plant: ");

            // Pass the values into the use case and attempt to add plant
            boolean success = service.deletePlant(UUID.fromString(plantId));

            if (success) {
                io.writeOutput("Plant deleted successfully!");
            } else {
                io.writeOutput("Plant could not be deleted!");
            }

            // Extra spacing for CLI
            io.writeOutput("\n");
        } catch (PlantException.InvalidInput e) {
            io.writeOutput("Invalid Input! " + e.getMessage());
        } catch (PlantException.PlantNotFound e) {
            io.writeOutput("Plant not found! " + e.getMessage());
        } catch (DomainValidationException e) {
            io.writeOutput("Invalid Data! " + e.getMessage());
        } catch (Exception e) {
            io.writeOutput("An unexpected error occurred! " + e.getMessage());
        }
    }

    public void editPlant() {
        try {
            // Gather all inputs from user
            String id = io.readInput("Enter the Id of the plant: ");
            String name = io.readInput("Enter the name of the plant: ");
            String species = io.readInput("Enter the species of the plant: ");
            String wateringFrequencyAsString = io.readInput("Enter the wateringFrequency: ");
            String notes = io.readInput("Enter any notes for the plant: ");

            // Pass the values into the use case and attempt to add plant
            Plant plant = service.updatePlant(UUID.fromString(id), new PlantDto.Request(
                    name, species, new Date(), Integer.parseInt(wateringFrequencyAsString), notes));

            // Create CLI Table headers
            io.writeOutput("Plant updated successfully!");

            // Print plant
            io.writeOutput(plant.toString());

            // Extra spacing for CLI
            io.writeOutput("\n");
        } catch (PlantException.InvalidInput e) {
            io.writeOutput("Invalid Input! " + e.getMessage());
        } catch (PlantException.PlantNotFound e) {
            io.writeOutput("Plant not found! " + e.getMessage());
        } catch (DomainValidationException e) {
            io.writeOutput("Invalid Data! " + e.getMessage());
        } catch (Exception e) {
            io.writeOutput("An unexpected error occurred! " + e.getMessage());
        }

    }

    public void viewPlantList() {
        try {
            // Gather all inputs from user
            // Pass the values into the use case and attempt to add plant
            List<Plant> plantList = service.findAll();

            // Create CLI Table headers
            io.writeOutput("Plant deleted successfully!");

            for (Plant plant : plantList) {
                io.writeOutput(plant.toString());
            }

            // Extra spacing for CLI
            io.writeOutput("\n");
        } catch (Exception e) {
            io.writeOutput("An unexpected error occurred! " + e.getMessage());
        }
    }

    public void waterPlant() {
        try {
            // Gather all inputs from user
            String id = io.readInput("Enter the Id of the plant: ");

            // Pass the values into the use case and attempt to add plant
            Plant plant = service.markAsWatered(UUID.fromString(id));

            // Create CLI Table headers
            io.writeOutput("Plant updated watering frequency: !");

            // Print plant
            io.writeOutput(plant.toString());

            // Extra spacing for CLI
            io.writeOutput("\n");
        } catch (PlantException.InvalidInput e) {
            io.writeOutput("Invalid Input: " + e.getMessage());
        } catch (PlantException.PlantNotFound e) {
            io.writeOutput("Plant not found! " + e.getMessage());
        } catch (DomainValidationException e) {
            io.writeOutput("Invalid Data! " + e.getMessage());
        } catch (Exception e) {
            io.writeOutput("An unexpected error occurred! " + e.getMessage());
        }
    }
}
