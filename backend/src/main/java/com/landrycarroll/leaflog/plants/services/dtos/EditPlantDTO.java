package com.landrycarroll.leaflog.plants.services.dtos;

import java.util.Date;

public class EditPlantDTO {
    private final String id;
    private final String name;
    private final String species;
    private final String notes;
    private final String wateringFrequency;
    private final Date lastWatered;

    public EditPlantDTO(String id, String name, String species, String notes, String wateringFrequency, Date lastWatered) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.notes = notes;
        this.wateringFrequency = wateringFrequency;
        this.lastWatered = lastWatered;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getWateringFrequency() {
        return wateringFrequency;
    }

    public String getNotes() {
        return notes;
    }

    public String getSpecies() {
        return species;
    }

    public Date getLastWatered() {
        return lastWatered;
    }
}
