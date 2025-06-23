package com.landrycarroll.leaflog.plants.usecases;

import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import com.landrycarroll.leaflog.plants.repositories.PlantRepository;
import com.landrycarroll.leaflog.plants.usecases.dtos.DeletePlantDTO;
import com.landrycarroll.leaflog.plants.usecases.exceptions.UseCaseException;

public class DeletePlantUseCase {
    private final PlantRepository plantRepository;

    public DeletePlantUseCase(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public boolean execute(DeletePlantDTO dto) {
        if (dto == null) {
            throw new UseCaseException("DeletePlantUseCase#execute Plant not found");
        }

        try {
            long id = Long.parseLong(dto.getId());

            Plant exitingPlant = this.plantRepository.findById(id);

            if (exitingPlant == null) {
                throw new UseCaseException("DeletePlantUseCase#execute: Plant with id " + id + " not found");
            }

            this.plantRepository.deleteById(id);

            return true;

        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
