package com.landrycarroll.leaflog;

import com.landrycarroll.leaflog.infrastructure.ConsoleIO;
import com.landrycarroll.leaflog.infrastructure.MenuBuilder;
import com.landrycarroll.leaflog.infrastructure.UserIO;
import com.landrycarroll.leaflog.plants.controllers.*;
import com.landrycarroll.leaflog.plants.repositories.PlantRepository;
import com.landrycarroll.leaflog.plants.repositories.PlantRepositoryInMemory;
import com.landrycarroll.leaflog.plants.usecases.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LeaflogApplication {

    public static void main(String[] args) {

        // Construct IO
        UserIO io = new ConsoleIO();

        // Construct repository
        PlantRepository repository = new PlantRepositoryInMemory();

        // Construct use cases
        AddPlantUseCase addPlantFromInputUseCase = new AddPlantUseCase(repository);
        DeletePlantUseCase deletePlantFromInputUseCase = new DeletePlantUseCase(repository);
        EditPlantUseCase editPlantFromInputUseCase = new EditPlantUseCase(repository);
        SaveWateringIntervalUseCase saveWateringIntervalUseCase = new SaveWateringIntervalUseCase(repository);
        WaterPlantUseCase waterPlantUseCase = new WaterPlantUseCase(repository);
        ViewPlantListUseCase viewPlantListUseCase = new ViewPlantListUseCase(repository);

        // Construct controllers
        Runnable addFromInput = new AddPlantController(io, addPlantFromInputUseCase);
        Runnable deleteFromInput = new DeletePlantController(io, deletePlantFromInputUseCase);
        Runnable editFromInput = new EditPlantController(io, editPlantFromInputUseCase);
        Runnable saveWateringIntervalFromInput = new SaveWateringIntervalController(io, saveWateringIntervalUseCase);
        Runnable waterPlantFromInput = new WaterPlantController(io, waterPlantUseCase);
        Runnable viewPlantList = new ViewPlantListController(io, viewPlantListUseCase);

        // Construct Menu
        new MenuBuilder(io)
                .addOption("Add a Plant", addFromInput)
                .addOption("Delete a Plant", deleteFromInput)
                .addOption("Edit a Plant", editFromInput)
                .addOption("Update the Watering Interval", saveWateringIntervalFromInput)
                .addOption("Water a plant", waterPlantFromInput)
                .addOption("View plant list", viewPlantList)
                .build();

    }

}
