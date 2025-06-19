package com.landrycarroll.leaflog.plants.usecases;

import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import com.landrycarroll.leaflog.plants.repositories.PlantRepository;
import com.landrycarroll.leaflog.plants.repositories.PlantRepositoryInMemory;
import com.landrycarroll.leaflog.plants.usecases.dtos.WaterPlantDTO;
import com.landrycarroll.leaflog.plants.usecases.exceptions.UseCaseException;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class WaterPlantUseCaseTest {

    @Test
    public void shouldThrowExceptionIfNoDTOProvided() {
        // Arrange
        PlantRepository plantRepository = new PlantRepositoryInMemory();
        WaterPlantUseCase useCase = new WaterPlantUseCase(plantRepository);

        // Act & Assert
        assertThrows(UseCaseException.class, () -> useCase.execute(null));
    }

    @Test
    public void shouldWaterPlant() {
        // Arrange
        Date now = new Date();
        PlantRepository plantRepository = new PlantRepositoryInMemory();
        WaterPlantUseCase useCase = new WaterPlantUseCase(plantRepository);
        Plant plant1 = new Plant("Name", "Species", now, 8, "Notes");
        plantRepository.save(plant1);
        WaterPlantDTO dto = new WaterPlantDTO(plant1.getId().value().toString());

        // Act
        Plant updatedPlant = useCase.execute(dto);

        // Assert
        assertTrue(now.getTime() < updatedPlant.getLastWatered().value().getTime());
    }

}
