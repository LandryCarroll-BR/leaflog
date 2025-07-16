package com.landrycarroll.leaflog.plantmanagement.domain.entities;

import com.landrycarroll.leaflog.plantmanagement.domain.converters.*;
import com.landrycarroll.leaflog.plantmanagement.domain.valueobjects.*;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * JPA entity representing a plant in the system.
 *
 * <p>Encapsulates plant-specific attributes such as name, species, watering frequency, last watered date,
 * and optional notes. This entity uses value objects to enforce domain constraints and converters to
 * persist them as simple types in the database.</p>
 */
@Entity
public class Plant {

    /**
     * Unique identifier for the plant, generated automatically.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Name of the plant, stored as a {@link PlantName} value object.
     */
    @Column(name = "name")
    @Convert(converter = PlantNameConverter.class)
    private PlantName plantName;

    /**
     * Species of the plant, stored as a {@link PlantSpecies} value object.
     */
    @Column(name = "species")
    @Convert(converter = PlantSpeciesConverter.class)
    private PlantSpecies species;

    /**
     * Date when the plant was last watered, stored as a {@link LastWateredDate} value object.
     */
    @Column(name = "last_watered_date")
    @Convert(converter = LastWateredDateConverter.class)
    private LastWateredDate lastWatered;

    /**
     * Frequency (in days) with which the plant should be watered, stored as a {@link WateringFrequencyInDays} value object.
     */
    @Column(name = "watering_frequency")
    @Convert(converter = WateringFrequencyConverter.class)
    private WateringFrequencyInDays wateringFrequency;

    /**
     * Additional notes about the plant, stored as a {@link Notes} value object.
     */
    @Column(name = "notes")
    @Convert(converter = NotesConverter.class)
    private Notes notes;

    /**
     * Factory method to create a new {@link Plant} instance.
     *
     * @param plantName         the name of the plant
     * @param species           the species of the plant
     * @param lastWatered       the date the plant was last watered
     * @param wateringFrequency how often the plant should be watered
     * @param notes             any additional notes
     * @return a new {@link Plant} instance
     */
    public static Plant newPlant(PlantName plantName, PlantSpecies species, LastWateredDate lastWatered, WateringFrequencyInDays wateringFrequency, Notes notes) {
        Plant plant = new Plant();
        plant.plantName = plantName;
        plant.species = species;
        plant.lastWatered = lastWatered;
        plant.wateringFrequency = wateringFrequency;
        plant.notes = notes;
        return plant;
    }

    /**
     * Updates the {@code lastWatered} property to the current system date.
     */
    public void markAsWatered() {
        Date now = new Date();
        this.setLastWatered(new LastWateredDate(now));
    }

    // === Getters ===

    public UUID getId() {
        return id;
    }

    public PlantName getPlantName() {
        return plantName;
    }

    public PlantSpecies getSpecies() {
        return species;
    }

    public LastWateredDate getLastWatered() {
        return lastWatered;
    }

    public WateringFrequencyInDays getWateringFrequency() {
        return wateringFrequency;
    }

    public Notes getNotes() {
        return notes;
    }

    // === Setters ===

    public void setPlantName(PlantName plantName) {
        this.plantName = plantName;
    }

    public void setSpecies(PlantSpecies species) {
        this.species = species;
    }

    public void setLastWatered(LastWateredDate lastWatered) {
        this.lastWatered = lastWatered;
    }

    public void setWateringFrequency(WateringFrequencyInDays wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    // === Object Overrides ===

    @Override
    public String toString() {
        return "Plant{" +
                "id=" + id +
                ", plantName=" + plantName +
                ", species=" + species +
                ", lastWatered=" + lastWatered +
                ", wateringFrequency=" + wateringFrequency +
                ", notes=" + notes +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Plant plant)) return false;
        return Objects.equals(id, plant.id) && Objects.equals(plantName, plant.plantName) && Objects.equals(species, plant.species) && Objects.equals(lastWatered, plant.lastWatered) && Objects.equals(wateringFrequency, plant.wateringFrequency) && Objects.equals(notes, plant.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, plantName, species, lastWatered, wateringFrequency, notes);
    }
}

