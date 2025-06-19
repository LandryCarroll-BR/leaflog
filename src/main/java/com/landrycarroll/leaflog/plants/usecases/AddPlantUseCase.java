package com.landrycarroll.leaflog.plants.usecases;

import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import com.landrycarroll.leaflog.plants.repositories.PlantRepository;
import com.landrycarroll.leaflog.plants.usecases.dtos.AddPlantDTO;
import com.landrycarroll.leaflog.plants.usecases.exceptions.UseCaseException;

public class AddPlantUseCase {
    private final PlantRepository repository;

    public AddPlantUseCase(PlantRepository repository) {
        this.repository = repository;
    }

    public Plant execute(AddPlantDTO dto) {
        if (dto == null) {
            throw new UseCaseException("AddPlantUseCase#execute called with null parameters");
        }

        try {

            int wateringFrequency = Integer.parseInt(dto.wateringFrequencyAsString());

            Plant plant = new Plant(dto.getName(), dto.getSpecies(), dto.getLastWatered(), wateringFrequency, dto.getNotes());


            repository.save(plant);

            return plant;

        } catch (NumberFormatException e) {
            throw new UseCaseException("AddPlantUseCase#execute must provide valid watering frequency");
        }
    }
}
