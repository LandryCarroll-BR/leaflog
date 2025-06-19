package com.landrycarroll.leaflog.plants.domain.valueobjects;

import com.landrycarroll.leaflog.plants.domain.exceptions.DomainValidationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlantNameTest {

    @Test public void shouldCreatePlantName() {
        PlantName plantName = new PlantName("test");
        assertEquals("test", plantName.value());
    }

    @Test
    public void shouldThrowExceptionWhenNull() {
        DomainValidationException ex = assertThrows(
                DomainValidationException.class,
                () -> new PlantName(null)
        );
        assertEquals("PlantName must not be null", ex.getMessage());
    }

    @Test public void shouldThrowExceptionWhenEmpty() {
        DomainValidationException ex = assertThrows(
                DomainValidationException.class,
                () -> new PlantName("")
        );
        assertEquals("PlantName must not be null", ex.getMessage());
    }

    @Test public void shouldThrowExceptionWhenExceedsMaxLength() {
        int MAXIMUM_NAME_LENGTH = 64;

        StringBuilder maxLengthString = new StringBuilder("0");

        for (int i = 0; i < MAXIMUM_NAME_LENGTH; i++) {
            maxLengthString.append(i);
        }

        DomainValidationException ex = assertThrows(
                DomainValidationException.class,
                () -> new PlantName(maxLengthString.toString())
        );

        assertEquals("PlantName must be less than " + MAXIMUM_NAME_LENGTH + " characters", ex.getMessage());
    }

    @Test
    public void shouldBeEqualIfSame() {
        PlantName plantName1 = new PlantName("test");
        PlantName plantName2 = new PlantName("test");
        assertEquals(plantName1, plantName2);
    }

    @Test
    public void shouldNotBeEqualIfDifferent() {
        PlantName plantName1 = new PlantName("test");
        PlantName plantName2 = new PlantName("testt");
        assertNotEquals(plantName1, plantName2);
    }
}
