package com.landrycarroll.leaflog.plantmanagement.domain.valueobjects;

import com.landrycarroll.leaflog.plantmanagement.exceptions.DomainValidationException;

/**
 * Value object representing the species of a plant.
 *
 * <p>Encapsulates a {@link String} and ensures that the value is not null or empty.</p>
 *
 * @param value the species name of the plant
 */
public record PlantSpecies(String value) {

    /**
     * Constructs a {@code PlantSpecies} and validates that it is not null or empty.
     *
     * @param value the species string to wrap
     * @throws DomainValidationException if {@code value} is null or empty
     */
    public PlantSpecies {
        if (isNullOrEmpty(value)) {
            throw new DomainValidationException("PlantSpecies must not be null or empty");
        }
    }

    /**
     * Checks whether the provided string is null or empty.
     *
     * @param value the string to check
     * @return {@code true} if the string is null or empty; {@code false} otherwise
     */
    private boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }
}
