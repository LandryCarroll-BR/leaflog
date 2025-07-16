package com.landrycarroll.leaflog.plantmanagement.exceptions;

import java.util.UUID;

/**
 * Base class for domain-specific exceptions related to {@code Plant} operations.
 *
 * <p>This is a sealed class that permits only specific, well-defined subclasses
 * to represent different failure scenarios (e.g., not found, invalid input, etc.).
 * This approach improves exhaustiveness and type safety in exception handling.</p>
 */
public sealed class PlantException extends RuntimeException permits
        PlantException.InvalidInput,
        PlantException.PlantNotFound,
        PlantException.InvalidFrequency,
        PlantException.PlantAlreadyExists,
        PlantException.RepositoryError {

    /**
     * Constructs a new {@code PlantException} with the given detail message.
     *
     * @param message the explanation of the exception
     */
    public PlantException(String message) {
        super(message);
    }

    /**
     * Thrown when the input data (e.g., from a DTO) is null, incomplete, or malformed.
     */
    public static final class InvalidInput extends PlantException {

        /**
         * Constructs an {@code InvalidInput} exception with a custom message.
         *
         * @param message description of the input validation issue
         */
        public InvalidInput(String message) {
            super(message);
        }
    }

    /**
     * Thrown when no plant with the specified ID exists in the system.
     */
    public static final class PlantNotFound extends PlantException {

        /**
         * Constructs a {@code PlantNotFound} exception using the missing plant's ID.
         *
         * @param id the UUID of the plant that was not found
         */
        public PlantNotFound(UUID id) {
            super("Plant with id " + id + " not found");
        }
    }

    /**
     * Thrown when the watering frequency input is not a valid integer or out of range.
     */
    public static final class InvalidFrequency extends PlantException {

        /**
         * Constructs an {@code InvalidFrequency} exception with the raw invalid input.
         *
         * @param rawValue the invalid input string
         */
        public InvalidFrequency(String rawValue) {
            super("Invalid watering frequency: '" + rawValue + "'");
        }
    }

    /**
     * Thrown when a plant already exists with the same ID or unique field.
     */
    public static final class PlantAlreadyExists extends PlantException {

        /**
         * Constructs a {@code PlantAlreadyExists} exception with a custom message.
         *
         * @param message the explanation of the conflict
         */
        public PlantAlreadyExists(String message) {
            super(message);
        }
    }

    /**
     * Thrown when an unexpected error occurs during repository interaction.
     */
    public static final class RepositoryError extends PlantException {

        /**
         * Constructs a {@code RepositoryError} with additional context.
         *
         * @param message the underlying repository error detail
         */
        public RepositoryError(String message) {
            super("Repository error: " + message);
        }
    }
}
