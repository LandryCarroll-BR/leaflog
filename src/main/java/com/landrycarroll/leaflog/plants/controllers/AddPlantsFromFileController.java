package com.landrycarroll.leaflog.plants.controllers;

import com.landrycarroll.leaflog.infrastructure.UserIO;
import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import com.landrycarroll.leaflog.plants.usecases.AddPlantUseCase;
import com.landrycarroll.leaflog.plants.usecases.dtos.AddPlantDTO;
import com.landrycarroll.leaflog.plants.usecases.exceptions.UseCaseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

public class AddPlantsFromFileController implements Runnable {
    private final UserIO io;
    private final AddPlantUseCase useCase;

    // Constructor
    public AddPlantsFromFileController(UserIO io, AddPlantUseCase useCase) {
        this.io = io;
        this.useCase = useCase;
    }

    // Main execution
    @Override
    public void run() {
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

                    Plant plant = useCase.execute(new AddPlantDTO(
                            name,
                            species,
                            wateringFrequency,
                            notes,
                            new Date()
                    ));

                    io.writeOutput(plant.toString());

                } catch (UseCaseException e) {
                    io.writeOutput(e.getMessage());
                }
            }

        } catch (IOException e) {
            // Handle IO error
            io.writeOutput("❌ Failed to read file: " + e.getMessage());
        } catch (Exception e) {
            io.writeOutput("Unknown error occurred: " + e.getMessage());
        }
    }
}
