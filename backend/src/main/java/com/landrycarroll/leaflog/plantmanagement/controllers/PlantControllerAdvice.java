package com.landrycarroll.leaflog.plantmanagement.controllers;


import com.landrycarroll.leaflog.plantmanagement.exceptions.DomainValidationException;
import com.landrycarroll.leaflog.plantmanagement.exceptions.PlantException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * Handles exceptions related to plant operations such as not found, invalid input,
 * or repository errors.
 */
@RestControllerAdvice
public class PlantControllerAdvice {

    /**
     * Handles {@link PlantException.PlantNotFound} exceptions.
     *
     * @param ex the exception thrown when a plant is not found
     * @return a descriptive error message
     */
    @ExceptionHandler(PlantException.PlantNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<?> plantNotFoundHandler(PlantException.PlantNotFound ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage()));
    }

    /**
     * Handles {@link PlantException.InvalidFrequency} exceptions.
     *
     * @param ex the exception thrown when the watering frequency is invalid
     * @return a descriptive error message
     */
    @ExceptionHandler(PlantException.InvalidFrequency.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<?> plantNotFoundHandler(PlantException.InvalidFrequency ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage()));
    }

    /**
     * Handles {@link PlantException.InvalidInput} exceptions.
     *
     * @param ex the exception thrown for general invalid input
     * @return a descriptive error message
     */
    @ExceptionHandler(PlantException.InvalidInput.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<?> plantNotFoundHandler(PlantException.InvalidInput ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage()));
    }

    /**
     * Handles {@link PlantException.PlantAlreadyExists} exceptions.
     *
     * @param ex the exception thrown when a plant with the same ID already exists
     * @return a descriptive error message
     */
    @ExceptionHandler(PlantException.PlantAlreadyExists.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<?> plantNotFoundHandler(PlantException.PlantAlreadyExists ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage()));
    }

    /**
     * Handles {@link PlantException.RepositoryError} exceptions.
     *
     * @param ex the exception thrown when an error occurs at the repository level
     * @return a descriptive error message
     */
    @ExceptionHandler(PlantException.RepositoryError.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    ResponseEntity<?> plantNotFoundHandler(PlantException.RepositoryError ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(Map.of("message", ex.getMessage()));
    }

    /**
     * Handles {@link DomainValidationException} errors.
     *
     * @param ex the exception thrown when a domain object fails validation
     * @return a descriptive error message
     */
    @ExceptionHandler(DomainValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<?> domainValidationHandler(DomainValidationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage()));
    }
}