package com.landrycarroll.leaflog.plants.controllers;

import com.landrycarroll.leaflog.infrastructure.UserIO;
import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import com.landrycarroll.leaflog.plants.domain.exceptions.DomainValidationException;
import com.landrycarroll.leaflog.plants.usecases.SaveWateringIntervalUseCase;
import com.landrycarroll.leaflog.plants.usecases.dtos.SaveWateringIntervalDTO;
import com.landrycarroll.leaflog.plants.usecases.exceptions.UseCaseException;


public class SaveWateringIntervalController implements Runnable {
    private final UserIO io;
    private final SaveWateringIntervalUseCase useCase;

    public SaveWateringIntervalController(UserIO io, SaveWateringIntervalUseCase useCase) {
        this.io = io;
        this.useCase = useCase;
    }

    @Override
    public void run() {
        try {
            // Gather all inputs from user
            String id = io.readInput("Enter the Id of the plant: ");
            String wateringFrequencyAsString = io.readInput("Enter the wateringFrequency: ");

            // Pass the values into the use case and attempt to add patron
            Plant plant = useCase.execute(new SaveWateringIntervalDTO(wateringFrequencyAsString, id));

            // Create CLI Table headers
            io.writeOutput("Plant updated watering frequency: !");

            // Print plant
            io.writeOutput(plant.toString());

            // Extra spacing for CLI
            io.writeOutput("\n");
        } catch (UseCaseException e) {
            io.writeOutput("Failed to add plant! " + e.getMessage());
        } catch (DomainValidationException e) {
            io.writeOutput("Invalid Data! " + e.getMessage());
        } catch (Exception e) {
            io.writeOutput("An unexpected error occurred! " + e.getMessage());
        }
    }
}
