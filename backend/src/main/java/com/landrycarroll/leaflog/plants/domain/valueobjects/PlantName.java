package com.landrycarroll.leaflog.plants.domain.valueobjects;

import com.landrycarroll.leaflog.plants.exceptions.DomainValidationException;

public record PlantName(String value) {
    private static final int MAXIMUM_NAME_LENGTH = 64;

    public PlantName {
        if (isNullOrEmpty(value)) {
            throw new DomainValidationException("PlantName must not be null");
        }

        if (isMaximumLength(value)) {
            throw new DomainValidationException("PlantName must be less than " + MAXIMUM_NAME_LENGTH + " characters");
        }
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    private boolean isMaximumLength(String value) {
        return MAXIMUM_NAME_LENGTH <= value.length();
    }
}
