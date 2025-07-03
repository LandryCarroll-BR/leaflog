package com.landrycarroll.leaflog.plants.domain.valueobjects;

import com.landrycarroll.leaflog.plants.exceptions.DomainValidationException;

import java.util.Date;

public record LastWateredDate(Date value) {
    public LastWateredDate {
        if (isFutureDate(value)) {
            throw new DomainValidationException("Last watered dates can't be in the future");
        }
    }

    private boolean isFutureDate(Date value) {
        return value != null && value.getTime() > System.currentTimeMillis();
    }
}
