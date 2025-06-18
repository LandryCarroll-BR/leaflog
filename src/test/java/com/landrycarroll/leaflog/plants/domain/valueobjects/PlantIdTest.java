package com.landrycarroll.leaflog.plants.domain.valueobjects;


import com.landrycarroll.leaflog.plants.domain.exceptions.DomainValidationException;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class PlantIdTest {

    @Test
    void shouldCreatePlantIdFromPositiveValue() {
        PlantId plantId = new PlantId(123L);
        assertEquals(123L, plantId.value());
    }

    @Test
    void shouldThrowExceptionWhenIdIsZero() {
        DomainValidationException ex = assertThrows(
                DomainValidationException.class, () -> new PlantId(0L)
        );
        assertEquals("PlantId must be positive", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenIdIsNegative() {
        DomainValidationException ex = assertThrows(
                DomainValidationException.class,
                () -> new PlantId(-5L)
        );
        assertEquals("PlantId must be positive", ex.getMessage());
    }

    @Test
    void shouldCompareEqualIdsCorrectly() {
        PlantId id1 = new PlantId(10L);
        PlantId id2 = new PlantId(10L);
        assertEquals(id1, id2);
    }

    @Test
    void shouldNotBeEqualIfValuesDiffer() {
        PlantId id1 = new PlantId(1L);
        PlantId id2 = new PlantId(2L);
        assertNotEquals(id1, id2);
    }
}
