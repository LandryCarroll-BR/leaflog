package com.landrycarroll.leaflog.plants.usecases;

import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import com.landrycarroll.leaflog.plants.repositories.PlantRepository;
import com.landrycarroll.leaflog.plants.repositories.PlantRepositoryInMemory;
import com.landrycarroll.leaflog.plants.usecases.dtos.DeletePlantDTO;
import com.landrycarroll.leaflog.plants.usecases.dtos.WaterPlantDTO;
import com.landrycarroll.leaflog.plants.usecases.exceptions.UseCaseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeletePlantUseCaseTest {

    @Test
    public void shouldThrowExceptionIfNoDtoProvided() {
        // Arrange
        PlantRepository plantRepository = new PlantRepositoryInMemory();
        DeletePlantUseCase useCase = new DeletePlantUseCase(plantRepository);

        // Act & Assert
        assertThrows(UseCaseException.class, () -> useCase.execute(null));
    }

    @Test
    public void shouldDeletePlant() {
        // Arrange
        Date now = new Date();
        PlantRepository plantRepository = new PlantRepositoryInMemory();
        DeletePlantUseCase useCase = new DeletePlantUseCase(plantRepository);
        Plant plant1 = new Plant("Name", "Species", now, 8, "Notes");
        plantRepository.save(plant1);
        DeletePlantDTO dto = new DeletePlantDTO(plant1.getId().value().toString());

        // Act
        boolean success = useCase.execute(dto);

        // Assert
        assertEquals(0, plantRepository.findAll().size());
        assertTrue(success);
    }

}
