package com.landrycarroll.leaflog.plants.domain.valueobjects;

import com.landrycarroll.leaflog.plants.domain.exceptions.DomainValidationException;

public record Notes(String value) {
    private static final int MAXIMUM_LENGTH = 500;

    public Notes {
        if (isExceedsMaximumLength(value)) {
            throw new DomainValidationException("Notes must be less than " + MAXIMUM_LENGTH + " characters.");
        }
    }

    private boolean isExceedsMaximumLength(String value) {
        return value != null && value.length() > MAXIMUM_LENGTH;
    }
}
