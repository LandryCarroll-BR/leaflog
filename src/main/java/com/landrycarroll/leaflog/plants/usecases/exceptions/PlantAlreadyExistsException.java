package com.landrycarroll.leaflog.plants.usecases.exceptions;

public class PlantAlreadyExistsException extends RuntimeException {
  public PlantAlreadyExistsException(String message) {
    super(message);
  }
}
