package com.landrycarroll.leaflog.plants.usecases;

import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import com.landrycarroll.leaflog.plants.repositories.PlantRepository;
import com.landrycarroll.leaflog.plants.repositories.PlantRepositoryInMemory;
import com.landrycarroll.leaflog.plants.usecases.dtos.EditPlantDTO;
import com.landrycarroll.leaflog.plants.usecases.exceptions.UseCaseException;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class EditPlantUseCaseTest {

    @Test
    public void shouldThrowExceptionIfNoDtoProvided() {
        // Arrange
        PlantRepository plantRepository = new PlantRepositoryInMemory();
        EditPlantUseCase useCase = new EditPlantUseCase(plantRepository);

        // Act & Assert
        assertThrows(UseCaseException.class, () -> useCase.execute(null));
    }

    @Test
    public void shouldUpdatePlant() {
        // Arrange
        PlantRepository plantRepository = new PlantRepositoryInMemory();
        EditPlantUseCase useCase = new EditPlantUseCase(plantRepository);
        Plant plant1 = new Plant("Name", "Species", new Date(), 8, "Notes");
        plantRepository.save(plant1);
        EditPlantDTO dto = new EditPlantDTO(
                plant1.getId().value().toString(),
                "New Name",
                "New Species",
                "New Notes",
                "14"
        );

        // Act
        Plant updatedPLant = useCase.execute(dto);

        // Assert
        assertEquals("New Name", updatedPLant.getName().value());
        assertEquals("New Species", updatedPLant.getSpecies().value());
        assertEquals("New Notes", updatedPLant.getNotes().value());
        assertEquals(14, updatedPLant.getWateringFrequency().value());
    }

}
