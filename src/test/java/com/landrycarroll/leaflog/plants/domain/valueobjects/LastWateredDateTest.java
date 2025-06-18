package com.landrycarroll.leaflog.plants.domain.valueobjects;

import com.landrycarroll.leaflog.plants.domain.exceptions.DomainValidationException;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class LastWateredDateTest {

    @Test
    public void shouldCreateLastWateredDate() {
        Date date = new Date();
        LastWateredDate lastWateredDate = new LastWateredDate(date);
        assertEquals(date, lastWateredDate.value());
    }

    @Test
    public void shouldThrowExceptionWhenIsFutureDate() {
        Date currentDate = new Date();
        Date futureDate = new Date(currentDate.getTime() + 1000 * 60 * 60 * 24);
        DomainValidationException ex = assertThrows(DomainValidationException.class,() -> new LastWateredDate(futureDate));
        assertEquals("Last watered dates can't be in the future", ex.getMessage());
    }

}
