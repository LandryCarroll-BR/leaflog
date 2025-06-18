package com.landrycarroll.leaflog.plants.domain.valueobjects;

import com.landrycarroll.leaflog.plants.domain.exceptions.DomainValidationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WateringFrequencyTest {

    @Test
    public void shouldCreateWateringFrequency() {
        WateringFrequencyInDays wateringFrequency = new WateringFrequencyInDays(7);
        assertEquals(7, wateringFrequency.value());
    }

    @Test
    public void shouldThrowExceptionWhenValueIsNegative() {
        DomainValidationException ex =assertThrows(DomainValidationException.class, () ->  new WateringFrequencyInDays(-1));
        assertEquals("Value must be greater than or equal to 0", ex.getMessage() );
    }

    @Test
    public void shouldThrowExceptionWhenValueExceedsMaximum() {
        int MAXIMUM_VALUE = 365;
        DomainValidationException ex =assertThrows(DomainValidationException.class, () ->  new WateringFrequencyInDays(MAXIMUM_VALUE + 1));
        assertEquals("Value must be less than or equal to " + MAXIMUM_VALUE, ex.getMessage() );
    }

}
