package com.landrycarroll.leaflog.plantmanagement.domain.valueobjects;

import com.landrycarroll.leaflog.plantmanagement.exceptions.DomainValidationException;

import java.util.Date;

/**
 * Value object representing the date a plant was last watered.
 *
 * <p>Encapsulates a {@link java.util.Date} and ensures domain integrity by
 * preventing future dates.</p>
 *
 * <p>This class is used to enforce business rules related to watering history,
 * and should be used in place of raw {@code Date} fields to maintain consistency
 * and validation across the domain.</p>
 *
 * @param value the date the plant was last watered
 */
public record LastWateredDate(Date value) {
    
    /**
     * Constructs a {@code LastWateredDate} and validates that the date is not in the future.
     *
     * @param value the date to wrap
     * @throws DomainValidationException if {@code value} is a future date
     */
    public LastWateredDate {
        if (isFutureDate(value)) {
            throw new DomainValidationException("Last watered dates can't be in the future");
        }
    }

    /**
     * Checks whether the given date is in the future relative to the current system time.
     *
     * @param value the date to validate
     * @return {@code true} if the date is in the future; {@code false} otherwise
     */
    private boolean isFutureDate(Date value) {
        return value != null && value.getTime() > System.currentTimeMillis();
    }
}
