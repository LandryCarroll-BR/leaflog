package com.landrycarroll.leaflog.plants.controllers;

import com.landrycarroll.leaflog.infrastructure.UserIO;
import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import com.landrycarroll.leaflog.plants.domain.exceptions.DomainValidationException;
import com.landrycarroll.leaflog.plants.usecases.AddPlantUseCase;
import com.landrycarroll.leaflog.plants.usecases.dtos.AddPlantDTO;
import com.landrycarroll.leaflog.plants.usecases.exceptions.UseCaseException;

import java.util.Date;

public class AddPlantController implements Runnable {
    private final UserIO io;
    private final AddPlantUseCase useCase;

    public AddPlantController(UserIO io, AddPlantUseCase useCase) {
        this.io = io;
        this.useCase = useCase;
    }

    @Override
    public void run() {
        try {
            // Gather all inputs from user
            String name = io.readInput("Enter the name of the plant: ");
            String species = io.readInput("Enter the species of the plant: ");
            String wateringFrequencyAsString = io.readInput("Enter the wateringFrequency: ");
            String notes = io.readInput("Enter any notes for the plant: ");

            // Pass the values into the use case and attempt to add plant
            Plant plant = useCase.execute(new AddPlantDTO(name, species, wateringFrequencyAsString, notes, new Date()));

            // Create CLI Table headers
            io.writeOutput("Plant added successfully!");

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
