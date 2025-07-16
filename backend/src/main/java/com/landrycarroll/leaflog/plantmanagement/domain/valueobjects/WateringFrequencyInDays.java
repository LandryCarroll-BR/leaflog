package com.landrycarroll.leaflog.plantmanagement.domain.valueobjects;

import com.landrycarroll.leaflog.plantmanagement.exceptions.DomainValidationException;

/**
 * Value object representing how often a plant should be watered, in days.
 *
 * <p>Encapsulates an {@code int} and enforces domain constraints:
 * <ul>
 *     <li>Must be greater than or equal to 0</li>
 *     <li>Must not exceed {@value #MAXIMUM_VALUE} days</li>
 * </ul>
 * </p>
 *
 * @param value the number of days between waterings
 */
public record WateringFrequencyInDays(int value) {
    private static final int MAXIMUM_VALUE = 365;

    /**
     * Constructs a {@code WateringFrequencyInDays} and validates the frequency.
     *
     * @param value the number of days between waterings
     * @throws DomainValidationException if value is negative or exceeds {@code 365}
     */
    public WateringFrequencyInDays {
        if (isNegative(value)) {
            throw new DomainValidationException("Watering Frequency must be greater than or equal to 0");
        }

        if (isExceedsMaximum(value)) {
            throw new DomainValidationException("Watering Frequency must be less than or equal to " + MAXIMUM_VALUE);
        }
    }

    /**
     * Checks if the given value is negative.
     *
     * @param value the value to check
     * @return {@code true} if less than 0
     */
    private boolean isNegative(int value) {
        return value < 0;
    }

    /**
     * Checks if the given value exceeds the maximum allowed frequency.
     *
     * @param value the value to check
     * @return {@code true} if greater than {@link #MAXIMUM_VALUE}
     */
    private boolean isExceedsMaximum(int value) {
        return value > MAXIMUM_VALUE;
    }
}
