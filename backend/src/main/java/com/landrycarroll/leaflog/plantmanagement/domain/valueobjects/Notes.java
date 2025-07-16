package com.landrycarroll.leaflog.plantmanagement.domain.valueobjects;

import com.landrycarroll.leaflog.plantmanagement.exceptions.DomainValidationException;

/**
 * Value object representing optional notes about a plant.
 *
 * <p>Encapsulates a {@link String} and enforces a maximum character length
 * to maintain domain integrity.</p>
 *
 * @param value the notes text
 */
public record Notes(String value) {
    private static final int MAXIMUM_LENGTH = 500;

    /**
     * Constructs a {@code Notes} object and validates its length.
     *
     * @param value the string to wrap
     * @throws DomainValidationException if the string is longer than 500 characters
     */
    public Notes {
        if (isExceedsMaximumLength(value)) {
            throw new DomainValidationException("Notes must be less than " + MAXIMUM_LENGTH + " characters.");
        }
    }

    /**
     * Checks whether the provided string exceeds the maximum allowed length.
     *
     * @param value the string to check
     * @return {@code true} if the string is too long; {@code false} otherwise
     */
    private boolean isExceedsMaximumLength(String value) {
        return value != null && value.length() > MAXIMUM_LENGTH;
    }
}
