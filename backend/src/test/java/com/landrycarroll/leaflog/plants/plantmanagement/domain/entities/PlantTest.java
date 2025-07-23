package com.landrycarroll.leaflog.plants.plantmanagement.domain.entities;

import com.landrycarroll.leaflog.plantmanagement.domain.entities.Plant;
import com.landrycarroll.leaflog.plantmanagement.domain.valueobjects.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlantTest {

    @Test
    public void shouldCreatePlant() {
        // Arrange
        Date date = new Date();

        Plant plant = Plant.newPlant(
                new PlantName("Name"),
                new PlantSpecies("Species"),
                new LastWateredDate(date),
                new WateringFrequencyInDays(7),
                new Notes("Notes")
        );

        // Assert
        assertEquals("Name", plant.getPlantName().value());
        assertEquals("Species", plant.getSpecies().value());
        assertEquals(date, plant.getLastWatered().value());
        assertEquals(7, plant.getWateringFrequency().value());
        assertEquals("Notes", plant.getNotes().value());
    }

    @Test
    public void shouldResetLastWateredDateWhenMarkedAsWatered() {
        // Arrange
        Date date = new Date();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = () -> System.out.println("Wait for 1 second");
        scheduler.schedule(task, 1, TimeUnit.SECONDS);
        scheduler.shutdown();

        Plant plant = Plant.newPlant(
                new PlantName("Name"),
                new PlantSpecies("Species"),
                new LastWateredDate(date),
                new WateringFrequencyInDays(7),
                new Notes("Notes")
        );


        // Act
        plant.markAsWatered();

        // Assert
        assertTrue(plant.getLastWatered().value().getTime() > date.getTime());
    }

    @Test
    void shouldUpdateWateringFrequency() {
        // Arrange
        Date date = new Date();
        Plant plant = Plant.newPlant(
                new PlantName("Name"),
                new PlantSpecies("Species"),
                new LastWateredDate(date),
                new WateringFrequencyInDays(7),
                new Notes("Notes")
        );

        // Act
        plant.setWateringFrequency(new WateringFrequencyInDays(14));

        // Assert
        assertEquals(14, plant.getWateringFrequency().value());
    }

    @Test
    void shouldThrowExceptionWhenInvalidFrequency() {
        // Arrange
        Date date = new Date();
        Plant plant = Plant.newPlant(
                new PlantName("Name"),
                new PlantSpecies("Species"),
                new LastWateredDate(date),
                new WateringFrequencyInDays(7),
                new Notes("Notes")
        );


        // Act & Assert
        assertThrows(Exception.class, () -> plant.setWateringFrequency(new WateringFrequencyInDays(400)));
    }
}