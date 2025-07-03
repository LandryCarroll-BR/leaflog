package com.landrycarroll.leaflog.plants.domain.entities;

import com.landrycarroll.leaflog.plants.domain.valueobjects.*;

import java.util.Date;
import java.util.Random;

public class Plant {
    private final PlantId id;
    private PlantName name;
    private PlantSpecies species;
    private LastWateredDate lastWatered;
    private WateringFrequencyInDays wateringFrequency;
    private Notes notes;

    public Plant(
            String name,
            String species,
            Date lastWatered,
            int wateringFrequency,
            String notes
    ) {
        Long id = generateRandomId();
        this.id = new PlantId(id);
        this.name = new PlantName(name);
        this.species = new PlantSpecies(species);
        this.lastWatered = new LastWateredDate(lastWatered);
        this.wateringFrequency = new WateringFrequencyInDays(wateringFrequency);
        this.notes = new Notes(notes);
    }

    public Plant(
            Long id,
            String name,
            String species,
            Date lastWatered,
            int wateringFrequency,
            String notes
    ) {
        this.id = new PlantId(id);
        this.name = new PlantName(name);
        this.species = new PlantSpecies(species);
        this.lastWatered = new LastWateredDate(lastWatered);
        this.wateringFrequency = new WateringFrequencyInDays(wateringFrequency);
        this.notes = new Notes(notes);
    }

    private long generateRandomId() {
        Random random = new Random();

        // 100000000000L is the smallest 12-digit number
        // 999999999999999999L is the largest long value with 18 digits
        long min = 100000000000L;
        long max = 999999999999L;

        long result;
        do {
            result = min + (Math.abs(random.nextLong()) % (max - min + 1));
        } while (String.valueOf(result).charAt(0) == '0'); // Redundant check; already avoided with min

        return result;
    }

    public void markAsWatered() {
        Date now = new Date();
        setLastWatered(new LastWateredDate(now));
    }

    public void updateDetails(String newName, String newSpecies, String newNotes, Date newLastWatered) {
        setName(new PlantName(newName));
        setSpecies(new PlantSpecies(newSpecies));
        setNotes(new Notes(newNotes));
        setLastWatered(new LastWateredDate(newLastWatered));
    }

    public void updateWateringFrequency(int i) {
        setWateringFrequency(new WateringFrequencyInDays(i));
    }

    public PlantId getId() {
        return id;
    }

    public PlantName getName() {
        return name;
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

    public void setName(PlantName name) {
        this.name = name;
    }

    public void setSpecies(PlantSpecies species) {
        this.species = species;
    }

    public void setWateringFrequency(WateringFrequencyInDays wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public void setLastWatered(LastWateredDate lastWatered) {
        this.lastWatered = lastWatered;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "id=" + id.value() +
                ", name=" + name.value() +
                ", species=" + species.value() +
                ", lastWatered=" + lastWatered.value() +
                ", wateringFrequency=" + wateringFrequency.value() +
                ", notes=" + notes.value() +
                '}';
    }
}

