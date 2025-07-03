package com.landrycarroll.leaflog.plants.domain.valueobjects;

import com.landrycarroll.leaflog.plants.exceptions.DomainValidationException;

public record WateringFrequencyInDays(int value) {
    private static final int MAXIMUM_VALUE = 365;

    public WateringFrequencyInDays {
        if (isNegative(value)) {
            throw new DomainValidationException("Watering Frequency must be greater than or equal to 0");
        }

        if (isExceedsMaximum(value)) {
            throw new DomainValidationException("Watering Frequency must be less than or equal to " + MAXIMUM_VALUE);
        }
    }

    private boolean isNegative(int value) {
        return value < 0;
    }

    private boolean isExceedsMaximum(int value) {
        return value > MAXIMUM_VALUE;
    }

}
