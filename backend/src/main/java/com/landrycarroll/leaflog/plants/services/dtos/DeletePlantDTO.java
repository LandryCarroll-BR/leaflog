package com.landrycarroll.leaflog.plants.services.dtos;

public class DeletePlantDTO {
    public final String id;

    public DeletePlantDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
