package com.landrycarroll.leaflog.plants.domain.valueobjects;

import com.landrycarroll.leaflog.plants.domain.exceptions.DomainValidationException;

public record PlantId(Long value) {

    public PlantId {
        if (isNegative(value)) {
            throw new DomainValidationException("PlantId must be positive");
        }
    }

    private boolean isNegative(long value) {
        return value <= 0;
    }
}
