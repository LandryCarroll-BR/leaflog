package com.landrycarroll.leaflog.plants.usecases.dtos;

public class SaveWateringIntervalDTO {
    private final String wateringInterval;
    private final String plantId;

    public SaveWateringIntervalDTO(String wateringInterval, String plantId) {
        this.wateringInterval = wateringInterval;
        this.plantId = plantId;
    }

    public String getWateringInterval() {
        return wateringInterval;
    }

    public String getPlantId() {
        return plantId;
    }
}
