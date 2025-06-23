package com.landrycarroll.leaflog.plants.usecases;

import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import com.landrycarroll.leaflog.plants.repositories.PlantRepository;
import com.landrycarroll.leaflog.plants.usecases.dtos.WaterPlantDTO;
import com.landrycarroll.leaflog.plants.usecases.exceptions.UseCaseException;

public class WaterPlantUseCase {
    private final PlantRepository plantRepository;

    public WaterPlantUseCase(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public Plant execute(WaterPlantDTO dto) {
        if (dto == null) {
            throw new UseCaseException("WaterPlantUseCase#execute: Invalid waterPlantDTO");
        }

        try {
            long id = Long.parseLong(String.valueOf(dto.getId()));

            Plant plant = this.plantRepository.findById(id);

            if (plant == null) {
                throw new UseCaseException("WaterPlantUseCase#execute: Plant with id " + id + " not found");
            }

            plant.markAsWatered();
            this.plantRepository.save(plant);
            return plant;

        } catch (NumberFormatException e) {
            throw new UseCaseException(e.getMessage());
        }
    }
}
