package com.landrycarroll.leaflog.plants.domain.exceptions;

public class DomainValidationException extends RuntimeException {
    public DomainValidationException(String message) {
        super(message);
    }
}
