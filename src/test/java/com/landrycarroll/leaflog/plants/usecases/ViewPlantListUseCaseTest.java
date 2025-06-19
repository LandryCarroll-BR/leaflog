package com.landrycarroll.leaflog.plants.usecases;

import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import com.landrycarroll.leaflog.plants.repositories.PlantRepository;
import com.landrycarroll.leaflog.plants.repositories.PlantRepositoryInMemory;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ViewPlantListUseCaseTest {

    @Test
    public void shouldReturnEmptyListWhenNoPlants() {
        PlantRepository plantRepository = new PlantRepositoryInMemory();
        ViewPlantListUseCase useCase = new ViewPlantListUseCase(plantRepository);
        List<Plant> plants = useCase.execute();
        assertTrue(plants.isEmpty());
    }

    @Test
    public void shouldReturnPreviouslySavedPlants() {
        // Arrange
        PlantRepository plantRepository = new PlantRepositoryInMemory();
        ViewPlantListUseCase useCase = new ViewPlantListUseCase(plantRepository);
        Plant plant1 = new Plant("Name", "Species", new Date(), 8, "Notes");
        Plant plant2 = new Plant("Name", "Species", new Date(), 8, "Notes");
        plantRepository.save(plant1);
        plantRepository.save(plant2);

        // Act
        List<Plant> plantList = useCase.execute();

        // Assert
        assertEquals(plant1, plantList.get(0));
        assertEquals(plant2, plantList.get(1));
    }
}
