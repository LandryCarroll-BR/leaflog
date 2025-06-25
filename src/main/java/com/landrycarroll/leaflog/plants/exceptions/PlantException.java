package com.landrycarroll.leaflog.plants.exceptions;

public sealed class PlantException extends RuntimeException permits
        PlantException.InvalidInput,
        PlantException.PlantNotFound,
        PlantException.InvalidFrequency,
        PlantException.PlantAlreadyExists,
        PlantException.RepositoryError {

    public PlantException(String message) {
        super(message);
    }

    // Thrown when input DTO is null or missing fields
    public static final class InvalidInput extends PlantException {
        public InvalidInput(String message) {
            super(message);
        }
    }

    // Thrown when no plant with the given ID exists
    public static final class PlantNotFound extends PlantException {
        public PlantNotFound(String message) {
            super(message);
        }
    }

    // Thrown when watering frequency is not a valid number
    public static final class InvalidFrequency extends PlantException {
        public InvalidFrequency(String rawValue) {
            super("Invalid watering frequency: '" + rawValue + "'");
        }
    }

    public static final class PlantAlreadyExists extends PlantException {
        public PlantAlreadyExists(String message) {
            super(message);
        }
    }

    // Generic repository error
    public static final class RepositoryError extends PlantException {
        public RepositoryError(String message) {
            super("Repository error: " + message);
        }
    }
}
