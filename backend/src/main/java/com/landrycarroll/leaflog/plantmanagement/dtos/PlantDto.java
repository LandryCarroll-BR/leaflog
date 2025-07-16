package com.landrycarroll.leaflog.plantmanagement.dtos;

import com.landrycarroll.leaflog.plantmanagement.domain.entities.Plant;
import com.landrycarroll.leaflog.plantmanagement.domain.valueobjects.*;
import com.landrycarroll.leaflog.plantmanagement.exceptions.DomainValidationException;

import java.util.Date;

/**
 * Data Transfer Object (DTO) for creating and handling {@link Plant} resources.
 *
 * <p>Contains a nested {@code Request} record used for accepting user input from API calls,
 * and a utility method to transform the DTO into a domain {@code Plant} entity.</p>
 */
public class PlantDto {

    /**
     * Record representing the request body for creating or updating a plant.
     *
     * <p>This record holds raw data received from clients, which will be validated
     * and converted into value objects before entering the domain layer.</p>
     *
     * @param name                    the name of the plant
     * @param species                 the species of the plant
     * @param lastWatered             the date the plant was last watered
     * @param wateringFrequencyInDays how often the plant should be watered (in days)
     * @param notes                   additional notes about the plant
     */
    public record Request(
            String name,
            String species,
            Date lastWatered,
            int wateringFrequencyInDays,
            String notes
    ) {
    }

    /**
     * Creates a {@link Plant} domain object from a {@link Request} DTO.
     *
     * <p>This method wraps the raw fields from the DTO in domain-specific value objects,
     * ensuring validation and encapsulation of business rules.</p>
     *
     * @param request the DTO containing raw plant data from the client
     * @return a validated {@link Plant} entity ready for persistence or further processing
     * @throws DomainValidationException if any of the fields violate domain constraints
     */
    public static Plant createPlantFromDto(Request request) {
        return Plant.newPlant(
                new PlantName(request.name()),
                new PlantSpecies(request.species()),
                new LastWateredDate(request.lastWatered()),
                new WateringFrequencyInDays(request.wateringFrequencyInDays()),
                new Notes(request.notes())
        );
    }
}