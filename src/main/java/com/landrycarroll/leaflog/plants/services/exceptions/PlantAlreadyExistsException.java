package com.landrycarroll.leaflog.plants.services.exceptions;

public class PlantAlreadyExistsException extends RuntimeException {
    public PlantAlreadyExistsException(String message) {
        super(message);
    }
}
