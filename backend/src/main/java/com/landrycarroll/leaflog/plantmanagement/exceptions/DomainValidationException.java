package com.landrycarroll.leaflog.plantmanagement.exceptions;

/**
 * Custom exception thrown when a domain value object fails validation.
 *
 * <p>This exception indicates that a provided input violates one or more domain-specific
 * business rules, such as string length constraints, null values, or invalid formats.</p>
 *
 * <p>It is typically thrown from value object constructors and is meant to enforce
 * invariants within the domain model.</p>
 */
public class DomainValidationException extends RuntimeException {

    /**
     * Constructs a new {@code DomainValidationException} with the specified detail message.
     *
     * @param message the detail message explaining the validation failure
     */
    public DomainValidationException(String message) {
        super(message);
    }
}
