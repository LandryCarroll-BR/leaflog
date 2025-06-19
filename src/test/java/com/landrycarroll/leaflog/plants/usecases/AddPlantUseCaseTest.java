package com.landrycarroll.leaflog.plants.usecases;

import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import com.landrycarroll.leaflog.plants.repositories.PlantRepository;
import com.landrycarroll.leaflog.plants.repositories.PlantRepositoryInMemory;
import com.landrycarroll.leaflog.plants.usecases.dtos.AddPlantDTO;
import com.landrycarroll.leaflog.plants.usecases.exceptions.UseCaseException;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class AddPlantUseCaseTest {

    @Test
    public void shouldThrowExceptionIfNoDtoProvided() {
        // Arrange
        PlantRepository plantRepository = new PlantRepositoryInMemory();
        AddPlantUseCase useCase = new AddPlantUseCase(plantRepository);

        // Act & Assert
        assertThrows(UseCaseException.class, () -> useCase.execute(null));
    }

    @Test
    public void shouldAddPlant() {
        // Arrange
        PlantRepository plantRepository = new PlantRepositoryInMemory();
        AddPlantUseCase useCase = new AddPlantUseCase(plantRepository);
        Plant plant1 = new Plant("Name", "Species", new Date(), 8, "Notes");
        plantRepository.save(plant1);
        Date now = new Date();
        AddPlantDTO dto = new AddPlantDTO("Name", "Species", "8", "Notes", now);

        // Act
        Plant addedPlant = useCase.execute(dto);

        // Assert
        assertEquals("Name", addedPlant.getName().value());
        assertEquals("Species", addedPlant.getSpecies().value());
        assertEquals(8, addedPlant.getWateringFrequency().value());
        assertEquals("Notes", addedPlant.getNotes().value());
        assertEquals(now, addedPlant.getLastWatered().value());
    }

}
