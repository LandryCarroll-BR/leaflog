package com.landrycarroll.leaflog.plants.services;

import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import com.landrycarroll.leaflog.plants.exceptions.PlantException;
import com.landrycarroll.leaflog.plants.repositories.PlantRepository;
import com.landrycarroll.leaflog.plants.repositories.PlantRepositoryInMemory;
import com.landrycarroll.leaflog.plants.services.dtos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class PlantServiceTest {
    private PlantService service;
    private PlantRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new PlantRepositoryInMemory();
        service = new PlantService(repository);

        Plant plant1 = new Plant("Name", "Species", new Date(), 8, "Notes");
        Plant plant2 = new Plant("Name", "Species", new Date(), 8, "Notes");

        repository.save(plant1);
        repository.save(plant2);
    }

    @Nested
    class AddPlant {

        @Test
        public void shouldThrowExceptionIfNoDtoProvided() {
            assertThrows(PlantException.class, () -> service.addPlant(null));
        }

        @Test
        public void shouldAddPlant() {
            Date now = new Date();
            AddPlantDTO dto = new AddPlantDTO("Name", "Species", "8", "Notes", now);

            // Act
            Plant addedPlant = service.addPlant(dto);

            // Assert
            assertEquals("Name", addedPlant.getName().value());
            assertEquals("Species", addedPlant.getSpecies().value());
            assertEquals(8, addedPlant.getWateringFrequency().value());
            assertEquals("Notes", addedPlant.getNotes().value());
            assertEquals(now, addedPlant.getLastWatered().value());
        }

        @Test
        public void shouldThrowIfWateringFrequencyInvalid() {
            AddPlantDTO dto = new AddPlantDTO("Name", "Species", "invalid", "Notes", new Date());
            assertThrows(PlantException.InvalidInput.class, () -> service.addPlant(dto));
        }
    }


    @Nested
    class DeletePlant {

        @Test
        public void shouldThrowExceptionIfNoDtoProvided() {
            assertThrows(PlantException.class, () -> service.deletePlant(null));
        }

        @Test
        public void shouldDeletePlant() {
            // Arrange
            Date now = new Date();
            Plant plant = new Plant("Name", "Species", now, 8, "Notes");
            repository.save(plant);
            DeletePlantDTO dto = new DeletePlantDTO(plant.getId().value().toString());
            int plantListSize = repository.findAll().size();

            // Act
            boolean success = service.deletePlant(dto);

            // Assert
            assertEquals(plantListSize - 1, repository.findAll().size());
            assertFalse(repository.findAll().contains(plant));
            assertTrue(success);
        }

        @Test
        public void shouldThrowIfIdIsInvalidFormat() {
            DeletePlantDTO dto = new DeletePlantDTO("invalid");
            assertThrows(PlantException.InvalidInput.class, () -> service.deletePlant(dto));
        }

        @Test
        public void shouldThrowIfPlantNotFound() {
            DeletePlantDTO dto = new DeletePlantDTO("99999");
            assertThrows(PlantException.PlantNotFound.class, () -> service.deletePlant(dto));
        }


    }

    @Nested
    class EditPlant {

        @Test
        public void shouldThrowExceptionIfNoDtoProvided() {
            assertThrows(PlantException.class, () -> service.editPlant(null));
        }

        @Test
        public void shouldUpdatePlant() {
            // Arrange
            Plant plant = new Plant("Name", "Species", new Date(), 8, "Notes");
            repository.save(plant);
            EditPlantDTO dto = new EditPlantDTO(
                    plant.getId().value().toString(),
                    "New Name",
                    "New Species",
                    "New Notes",
                    "14"
            );

            // Act
            Plant updatedPLant = service.editPlant(dto);

            // Assert
            assertEquals(plant.getId(), updatedPLant.getId());
            assertEquals("New Name", updatedPLant.getName().value());
            assertEquals("New Species", updatedPLant.getSpecies().value());
            assertEquals("New Notes", updatedPLant.getNotes().value());
            assertEquals(14, updatedPLant.getWateringFrequency().value());
        }

        @Test
        public void shouldThrowIfIdInvalidFormat() {
            EditPlantDTO dto = new EditPlantDTO("bad-id", "A", "B", "C", "10");
            assertThrows(PlantException.InvalidInput.class, () -> service.editPlant(dto));
        }

        @Test
        public void shouldThrowIfPlantNotFound() {
            EditPlantDTO dto = new EditPlantDTO("99999", "A", "B", "C", "10");
            assertThrows(PlantException.PlantNotFound.class, () -> service.editPlant(dto));
        }


    }

    @Nested
    class SavePlant {

        @Test
        public void shouldThrowExceptionWhenNoDTOProvided() {
            assertThrows(PlantException.class, () -> service.saveWateringInterval(null));
        }

        @Test
        public void shouldUpdateWateringInterval() {
            // Arrange
            Plant plant1 = new Plant("Name", "Species", new Date(), 8, "Notes");
            Plant plant2 = new Plant("Name", "Species", new Date(), 8, "Notes");
            repository.save(plant1);
            repository.save(plant2);
            SaveWateringIntervalDTO dto = new SaveWateringIntervalDTO("14", plant1.getId().value().toString());

            // Act
            Plant updatedPlant = service.saveWateringInterval(dto);

            // Assert
            assertNotNull(updatedPlant);
            assertEquals(14, updatedPlant.getWateringFrequency().value());
        }

        @Test
        public void shouldThrowIfPlantIdIsNull() {
            SaveWateringIntervalDTO dto = new SaveWateringIntervalDTO("10", null);
            assertThrows(PlantException.InvalidInput.class, () -> service.saveWateringInterval(dto));
        }

        @Test
        public void shouldThrowIfPlantIdInvalid() {
            SaveWateringIntervalDTO dto = new SaveWateringIntervalDTO("10", "bad-id");
            assertThrows(PlantException.InvalidInput.class, () -> service.saveWateringInterval(dto));
        }

        @Test
        public void shouldThrowIfPlantNotFound() {
            SaveWateringIntervalDTO dto = new SaveWateringIntervalDTO("10", "99999");
            assertThrows(PlantException.InvalidInput.class, () -> service.saveWateringInterval(dto));
        }

    }

    @Nested
    class ViewPlants {

        @Test
        public void shouldReturnEmptyListWhenNoPlants() {
            repository = new PlantRepositoryInMemory();
            service = new PlantService(repository);

            List<Plant> plants = service.viewPlantList();
            assertTrue(plants.isEmpty());
        }

        @Test
        public void shouldReturnPreviouslySavedPlants() {
            // Arrange
            Plant plant1 = new Plant("Name", "Species", new Date(), 8, "Notes");
            Plant plant2 = new Plant("Name", "Species", new Date(), 8, "Notes");
            repository.save(plant1);
            repository.save(plant2);

            // Act
            List<Plant> plantList = service.viewPlantList();

            // Assert
            assertTrue(plantList.contains(plant1));
            assertTrue(plantList.contains(plant2));
        }

    }

    @Nested
    class WaterPlant {

        @Test
        public void shouldThrowExceptionIfNoDTOProvided() {
            assertThrows(PlantException.class, () -> service.waterPlant(null));
        }

        @Test
        public void shouldWaterPlant() {
            repository = new PlantRepositoryInMemory();
            service = new PlantService(repository);

            // Arrange
            Date now = new Date();

            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            Runnable task = () -> System.out.println("Wait for 1 second");
            scheduler.schedule(task, 1, TimeUnit.SECONDS);
            scheduler.shutdown();

            Plant plant = new Plant("Name", "Species", now, 8, "Notes");
            repository.save(plant);
            WaterPlantDTO dto = new WaterPlantDTO(plant.getId().value().toString());

            // Act
            Plant updatedPlant = service.waterPlant(dto);

            // Assert
            assertTrue(now.getTime() < updatedPlant.getLastWatered().value().getTime());
        }

        @Test
        public void shouldThrowIfIdInvalidFormat() {
            WaterPlantDTO dto = new WaterPlantDTO("not-a-number");
            assertThrows(PlantException.InvalidInput.class, () -> service.waterPlant(dto));
        }

        @Test
        public void shouldThrowIfPlantNotFound() {
            WaterPlantDTO dto = new WaterPlantDTO("99999");
            assertThrows(PlantException.PlantNotFound.class, () -> service.waterPlant(dto));
        }


    }

}
