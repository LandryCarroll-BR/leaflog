package com.landrycarroll.leaflog.plants.usecases;

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
            throw new UseCaseException("DeletePlantUseCase#execute called with null dto");
        }

        try {
            Long id = Long.valueOf(dto.getId());

            this.plantRepository.deleteById(id);

            return true;

        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
