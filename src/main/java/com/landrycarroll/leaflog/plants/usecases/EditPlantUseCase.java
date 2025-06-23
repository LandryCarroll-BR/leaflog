package com.landrycarroll.leaflog.plants.usecases;

import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import com.landrycarroll.leaflog.plants.repositories.PlantRepository;
import com.landrycarroll.leaflog.plants.usecases.dtos.EditPlantDTO;
import com.landrycarroll.leaflog.plants.usecases.exceptions.UseCaseException;

public class EditPlantUseCase {
    private final PlantRepository plantRepository;

    public EditPlantUseCase(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public Plant execute(EditPlantDTO dto) {
        if (dto == null) {
            throw new UseCaseException("EditPlantUseCase#execute: dto is null");
        }

        try {
            long id = Long.parseLong(dto.getId());
            Plant plant = plantRepository.findById(id);

            if (plant == null) {
                throw new UseCaseException("EditPlantUseCase#execute: Plant with id " + id + " not found");
            }

            plant.updateDetails(dto.getName(), dto.getSpecies(), dto.getNotes());
            int frequency = Integer.parseInt(dto.getWateringFrequency());
            plant.updateWateringFrequency(frequency);

            plantRepository.save(plant);

            return plant;

        } catch (NumberFormatException e) {
            throw new UseCaseException(e.getMessage());
        }

    }
}
