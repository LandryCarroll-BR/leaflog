package com.landrycarroll.leaflog.plants.domain.valueobjects;

import com.landrycarroll.leaflog.plants.exceptions.DomainValidationException;

public record PlantSpecies(String value) {
    public PlantSpecies {
        if (isNullOrEmpty(value)) {
            throw new DomainValidationException("PlantSpecies must not be null or empty");
        }
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }
}
