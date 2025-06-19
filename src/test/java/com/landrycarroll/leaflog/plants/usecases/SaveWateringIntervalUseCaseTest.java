package com.landrycarroll.leaflog.plants.usecases;

import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import com.landrycarroll.leaflog.plants.repositories.PlantRepository;
import com.landrycarroll.leaflog.plants.repositories.PlantRepositoryInMemory;
import com.landrycarroll.leaflog.plants.usecases.dtos.SaveWateringIntervalDTO;
import com.landrycarroll.leaflog.plants.usecases.exceptions.UseCaseException;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class SaveWateringIntervalUseCaseTest {

    @Test
    public void shouldThrowExceptionWhenNoDTOProvided() {
        // Arrange
        PlantRepository plantRepository = new PlantRepositoryInMemory();
        SaveWateringIntervalUseCase useCase = new SaveWateringIntervalUseCase(plantRepository);

        // Act & Assert
        assertThrows(UseCaseException.class, () -> useCase.execute(null));
    }

    @Test
    public void shouldUpdateWateringInterval() {
        // Arrange
        PlantRepository plantRepository = new PlantRepositoryInMemory();
        SaveWateringIntervalUseCase useCase = new SaveWateringIntervalUseCase(plantRepository);
        Plant plant1 = new Plant("Name", "Species", new Date(), 8, "Notes");
        Plant plant2 = new Plant("Name", "Species", new Date(), 8, "Notes");
        plantRepository.save(plant1);
        plantRepository.save(plant2);
        SaveWateringIntervalDTO dto = new SaveWateringIntervalDTO("14", plant1.getId().value().toString());

        // Act
        Plant plantList = useCase.execute(dto);

        // Assert
        assertNotNull(plantList);
        assertEquals(14, plantList.getWateringFrequency().value());
    }
}
