package com.landrycarroll.leaflog.plantmanagement.domain.valueobjects;

import com.landrycarroll.leaflog.plantmanagement.exceptions.DomainValidationException;

/**
 * Value object representing the name of a plant.
 *
 * <p>Encapsulates a {@link String} and enforces domain constraints including
 * non-nullity and maximum length.</p>
 *
 * @param value the plant's name
 */
public record PlantName(String value) {
    private static final int MAXIMUM_NAME_LENGTH = 64;

    /**
     * Constructs a {@code PlantName} with validation.
     *
     * @param value the name of the plant
     * @throws DomainValidationException if the name is null, empty, or exceeds 64 characters
     */
    public PlantName {
        if (isNullOrEmpty(value)) {
            throw new DomainValidationException("PlantName must not be null");
        }

        if (isMaximumLength(value)) {
            throw new DomainValidationException("PlantName must be less than " + MAXIMUM_NAME_LENGTH + " characters");
        }
    }

    /**
     * Checks whether the input string is null or empty.
     *
     * @param value the string to check
     * @return {@code true} if null or empty; {@code false} otherwise
     */
    private boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    /**
     * Checks whether the input string exceeds the maximum length.
     *
     * @param value the string to check
     * @return {@code true} if length is greater than or equal to {@link #MAXIMUM_NAME_LENGTH}
     */
    private boolean isMaximumLength(String value) {
        return MAXIMUM_NAME_LENGTH <= value.length();
    }
}
