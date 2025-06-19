package com.landrycarroll.leaflog.plants.controllers;

import com.landrycarroll.leaflog.infrastructure.UserIO;
import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import com.landrycarroll.leaflog.plants.domain.exceptions.DomainValidationException;
import com.landrycarroll.leaflog.plants.usecases.ViewPlantListUseCase;
import com.landrycarroll.leaflog.plants.usecases.exceptions.UseCaseException;

import java.util.List;

public class ViewPlantListController implements Runnable {
    private final UserIO io;
    private final ViewPlantListUseCase useCase;

    public ViewPlantListController(UserIO io, ViewPlantListUseCase useCase) {
        this.io = io;
        this.useCase = useCase;
    }

    @Override
    public void run() {
        try {
            // Gather all inputs from user
            // Pass the values into the use case and attempt to add plant
            List<Plant> plantList = useCase.execute();

            // Create CLI Table headers
            io.writeOutput("Plant deleted successfully!");

            io.writeOutput(plantList.toString());

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
