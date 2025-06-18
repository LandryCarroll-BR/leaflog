package com.landrycarroll.leaflog.plants.domain.valueobjects;

import com.landrycarroll.leaflog.plants.domain.exceptions.DomainValidationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlantSpeciesTest {

    @Test
    public void shouldCreatePlantSpecies() {
        PlantSpecies plantSpecies = new PlantSpecies("testSpecies");

        assertEquals("testSpecies", plantSpecies.value());
    }

    @Test
    public void shouldThrowExceptionWhenNullOrEmpty() {
       DomainValidationException ex = assertThrows(
                DomainValidationException.class, () -> new PlantSpecies(null)
        );

       DomainValidationException ex2 = assertThrows(
               DomainValidationException.class, () -> new PlantSpecies("")
       );

       assertEquals("PlantSpecies must not be null or empty", ex.getMessage());
       assertEquals("PlantSpecies must not be null or empty", ex2.getMessage());
    }
}
