package com.landrycarroll.leaflog.plants.controllers;

import com.landrycarroll.leaflog.infrastructure.MockUserIO;
import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import com.landrycarroll.leaflog.plants.repositories.PlantRepository;
import com.landrycarroll.leaflog.plants.repositories.PlantRepositoryInMemory;
import com.landrycarroll.leaflog.plants.services.PlantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlantControllerTest {

    private MockUserIO io;
    private PlantRepository repository;
    private PlantController controller;

    @BeforeEach
    void setUp() {
        io = new MockUserIO();
        repository = new PlantRepositoryInMemory();
        PlantService service = new PlantService(repository);
        controller = new PlantController(io, service);
    }

    @Test
    void shouldAddPlantSuccessfully() {
        io.addInput("Fiddle Leaf");
        io.addInput("Ficus");
        io.addInput("7");
        io.addInput("Bright indirect light");
        controller.addPlant();

        List<Plant> allPlants = repository.findAll();
        assertEquals(1, allPlants.size());
        assertTrue(io.getOutputs().contains("Plant added successfully!"));
        assertTrue(io.getOutputs().contains(allPlants.get(0).toString()));
    }

    @Test
    void shouldHandleInvalidWateringFrequency() {
        io.addInput("Fiddle Leaf");
        io.addInput("Ficus");
        io.addInput("invalid-number");
        io.addInput("Bright indirect light");

        controller.addPlant();

        System.out.println(io.getOutputs());

        assertTrue(io.getOutputs().stream().anyMatch(output -> output.contains("Invalid Input!")));
    }

    @Test
    void shouldHandleInvalidDeleteInput() {
        io.addInput("not-a-uuid");
        controller.deletePlant();

        assertTrue(io.getOutputs().stream().anyMatch(output -> output.contains("Invalid Input!")));
    }

    @Test
    void shouldHandleDeleteNonExistentPlant() {
        io.addInput("123");
        controller.deletePlant();

        assertTrue(io.getOutputs().stream().anyMatch(output -> output.contains("Plant not found!")));
    }

    @Test
    void shouldHandleEditNonExistentPlant() {
        io.addInput("123");
        io.addInput("Name");
        io.addInput("Species");
        io.addInput("7");
        io.addInput("Notes");

        controller.editPlant();

        assertTrue(io.getOutputs().stream().anyMatch(output -> output.contains("Plant not found!")));
    }

    @Test
    void shouldEditPlantSuccessfully() {
        Plant plant = new Plant("Monstera", "Deliciosa", new Date(), 5, "Split leaves");
        repository.save(plant);

        io.addInput(plant.getId().value().toString());
        io.addInput("Swiss Cheese Plant");
        io.addInput("Monstera");
        io.addInput("10");
        io.addInput("Prefers humidity");

        controller.editPlant();

        Plant updated = repository.findById(plant.getId().value());
        assertEquals("Swiss Cheese Plant", updated.getName().value());
        assertEquals("Monstera", updated.getSpecies().value());
        assertEquals("Prefers humidity", updated.getNotes().value());
        assertEquals(10, updated.getWateringFrequency().value());
        assertTrue(io.getOutputs().contains("Plant updated successfully!"));
    }

    @Test
    void shouldViewPlantList() {
        Plant plant1 = new Plant("Aloe", "Vera", new Date(), 14, "");
        Plant plant2 = new Plant("Cactus", "Desert Gem", new Date(), 30, "");
        repository.save(plant1);
        repository.save(plant2);

        controller.viewPlantList();

        assertTrue(io.getOutputs().contains("Plant deleted successfully!"));
        assertTrue(io.getOutputs().contains(plant1.toString()));
        assertTrue(io.getOutputs().contains(plant2.toString()));
    }

    @Test
    void shouldWaterPlantSuccessfully() {
        Plant plant = new Plant("Snake Plant", "Sansevieria", new Date(), 14, "Low light tolerant");
        repository.save(plant);
        io.addInput(plant.getId().value().toString());

        controller.waterPlant();

        assertTrue(io.getOutputs().contains("Plant updated watering frequency: !"));
        assertTrue(io.getOutputs().contains(plant.toString()));
    }

    @Test
    void shouldAddPlantsFromValidFile() throws IOException {
        Path tempFile = Files.createTempFile("plants", ".txt");
        Files.write(tempFile, List.of(
                "Aloe-Vera-14-Succulent",
                "Snake Plant-Sansevieria-10-Low light tolerant"
        ));

        io.addInput(tempFile.toAbsolutePath().toString());
        controller.addPlantsFromFile();

        List<Plant> allPlants = repository.findAll();
        assertEquals(2, allPlants.size());
        assertTrue(io.getOutputs().stream().anyMatch(out -> out.contains("Aloe")));
        assertTrue(io.getOutputs().stream().anyMatch(out -> out.contains("Snake Plant")));

        Files.deleteIfExists(tempFile);
    }

    @Test
    void shouldShowErrorForMissingFile() {
        io.addInput("/fake/path/does-not-exist.txt");
        controller.addPlantsFromFile();

        assertTrue(io.getLastOutput().contains("Failed to read file"));
    }
}
