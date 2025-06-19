package com.landrycarroll.leaflog.plants.usecases.dtos;

import java.util.Date;

public class AddPlantDTO {
    private final String name;
    private final String species;
    private final String wateringFrequencyAsString;
    private final String notes;
    private final Date lastWatered;

    public AddPlantDTO(String name, String species, String wateringFrequencyAsString, String notes, Date lastWatered) {
        this.name = name;
        this.species = species;
        this.wateringFrequencyAsString = wateringFrequencyAsString;
        this.notes = notes;
        this.lastWatered = lastWatered;
    }

    public String wateringFrequencyAsString() {
        return wateringFrequencyAsString;
    }

    public String getSpecies() {
        return species;
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public Date getLastWatered() {
        return lastWatered;
    }
}
