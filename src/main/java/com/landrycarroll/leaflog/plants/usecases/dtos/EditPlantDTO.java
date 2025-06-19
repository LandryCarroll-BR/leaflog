package com.landrycarroll.leaflog.plants.usecases.dtos;

public class EditPlantDTO {
    private final String id;
    private final String name;
    private final String species;
    private final String notes;
    private final String wateringFrequency;

    public EditPlantDTO(String id, String name, String species, String notes, String wateringFrequency) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.notes = notes;
        this.wateringFrequency = wateringFrequency;
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
}
