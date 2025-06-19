package com.landrycarroll.leaflog.plants.controllers;

import com.landrycarroll.leaflog.infrastructure.UserIO;
import com.landrycarroll.leaflog.plants.domain.exceptions.DomainValidationException;
import com.landrycarroll.leaflog.plants.usecases.DeletePlantUseCase;
import com.landrycarroll.leaflog.plants.usecases.dtos.DeletePlantDTO;
import com.landrycarroll.leaflog.plants.usecases.exceptions.UseCaseException;

public class DeletePlantController implements Runnable {
    private final UserIO io;
    private final DeletePlantUseCase useCase;

    public DeletePlantController(UserIO io, DeletePlantUseCase useCase) {
        this.io = io;
        this.useCase = useCase;
    }

    @Override
    public void run() {
        try {
            // Gather all inputs from user
            String plantId = io.readInput("Enter the id of the plant: ");

            // Pass the values into the use case and attempt to add plant
            boolean plant = useCase.execute(new DeletePlantDTO(plantId));

            // Create CLI Table headers
            io.writeOutput("Plant deleted successfully!");

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
