package com.landrycarroll.leaflog;

import com.landrycarroll.leaflog.infrastructure.ConsoleIO;
import com.landrycarroll.leaflog.infrastructure.MenuBuilder;
import com.landrycarroll.leaflog.infrastructure.UserIO;
import com.landrycarroll.leaflog.plants.controllers.*;
import com.landrycarroll.leaflog.plants.repositories.PlantRepository;
import com.landrycarroll.leaflog.plants.repositories.PlantRepositoryInMemory;
import com.landrycarroll.leaflog.plants.services.PlantService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LeaflogApplication {

    public static void main(String[] args) {

        // Construct IO
        UserIO io = new ConsoleIO();

        // Construct repository
        PlantRepository repository = new PlantRepositoryInMemory();

        // Construct service
        PlantService plantService = new PlantService(repository);

        // Construct controller
        PlantController plantController = new PlantController(io, plantService);

        // Construct Menu
        new MenuBuilder(io)
                .addOption("Add a Plant", plantController::addPlant)
                .addOption("Delete a Plant", plantController::deletePlant)
                .addOption("Edit a Plant", plantController::editPlant)
                .addOption("Update the Watering Interval", plantController::saveWateringInterval)
                .addOption("Water a plant", plantController::waterPlant)
                .addOption("View plant list", plantController::viewPlantList)
                .addOption("Add plant from file", plantController::addPlantsFromFile)
                .build();

    }

}
