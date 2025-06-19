package com.landrycarroll.leaflog.plants.domain.entities;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class PlantTest {

    @Test
    public void shouldCreatePlant() {
        // Arrange
        Date date = new Date();
        Plant plant = new Plant("Name", "Species", date, 7, "Notes");

        // Assert
        assertEquals("Name", plant.getName().value());
        assertEquals("Species", plant.getSpecies().value());
        assertEquals(date, plant.getLastWatered().value());
        assertEquals(7, plant.getWateringFrequency().value());
        assertEquals("Notes", plant.getNotes().value());
    }

    @Test
    public void shouldResetLastWateredDateWhenMarkedAsWatered() {
        // Arrange
        Date date = new Date();
        Plant plant = new Plant("Name", "Species", date, 7, "Notes");

        // Act
        plant.markAsWatered();

        // Assert
        assertTrue(plant.getLastWatered().value().getTime() > date.getTime());
    }

    @Test
    public void shouldUpdateDetails() {
        // Arrange
        Date date = new Date();
        Plant plant = new Plant("Name", "Species", date, 7, "Notes");

        // Act
        plant.updateDetails("New Name", "New Species", "New Notes");

        // Assert
        assertEquals("New Name", plant.getName().value());
        assertEquals("New Species", plant.getSpecies().value());
        assertEquals("New Notes", plant.getNotes().value());
    }

    @Test
    void shouldUpdateWateringFrequency() {
        // Arrange
        Date date = new Date();
        Plant plant = new Plant("Name", "Species", date, 7, "Notes");

        // Act
        plant.updateWateringFrequency(14);

        // Assert
        assertEquals(14, plant.getWateringFrequency().value());
    }

    @Test
    void shouldThrowExceptionWhenInvalidFrequency() {
        // Arrange
        Date date = new Date();
        Plant plant = new Plant("Name", "Species", date, 7, "Notes");

        // Act & Assert
        assertThrows(Exception.class, () -> plant.updateWateringFrequency(400));
    }
}
