package com.landrycarroll.leaflog.plants.usecases;

import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import com.landrycarroll.leaflog.plants.repositories.PlantRepository;
import com.landrycarroll.leaflog.plants.usecases.dtos.SaveWateringIntervalDTO;
import com.landrycarroll.leaflog.plants.usecases.exceptions.UseCaseException;

public class SaveWateringIntervalUseCase {
    private final PlantRepository plantRepository;

    public SaveWateringIntervalUseCase(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public Plant execute(SaveWateringIntervalDTO dto) {
        if (dto == null) {
            throw new UseCaseException("SaveWateringIntervalUseCase#execute: dto cannot be null");
        }

        if (dto.getPlantId() == null) {
            throw new UseCaseException("SaveWateringIntervalUseCase#execute: plant id cannot be null");
        }

        try {
            int frequency = Integer.parseInt(dto.getWateringInterval());

            long id = Long.parseLong(String.valueOf(dto.getPlantId()));

            Plant plant = plantRepository.findById(id);

            if (plant == null) {
                throw new UseCaseException("SaveWateringIntervalUseCase#execute: Plant with id " + id + " not found");
            }

            plant.updateWateringFrequency(frequency);
            this.plantRepository.update(plant);
            return plant;
        } catch (NumberFormatException e) {
            throw new UseCaseException(e.getMessage());
        }

    }
}
