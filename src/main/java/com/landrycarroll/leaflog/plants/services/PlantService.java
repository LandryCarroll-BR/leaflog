package com.landrycarroll.leaflog.plants.services;

import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import com.landrycarroll.leaflog.plants.repositories.PlantRepository;
import com.landrycarroll.leaflog.plants.services.dtos.*;
import com.landrycarroll.leaflog.plants.services.exceptions.UseCaseException;

import java.util.List;

public class PlantService {
    private final PlantRepository repository;

    public PlantService(PlantRepository repository) {
        this.repository = repository;
    }

    public Plant addPlant(AddPlantDTO dto) {
        if (dto == null) {
            throw new UseCaseException("AddPlantUseCase#execute called with null parameters");
        }

        try {
            int wateringFrequency = Integer.parseInt(dto.wateringFrequencyAsString());

            Plant plant = new Plant(dto.getName(), dto.getSpecies(), dto.getLastWatered(), wateringFrequency, dto.getNotes());

            repository.save(plant);

            return plant;

        } catch (NumberFormatException e) {
            throw new UseCaseException("AddPlantUseCase#execute must provide valid watering frequency");
        }
    }

    public boolean deletePlant(DeletePlantDTO dto) {
        if (dto == null) {
            throw new UseCaseException("DeletePlantUseCase#execute Plant not found");
        }

        try {
            long id = Long.parseLong(dto.getId());

            Plant exitingPlant = this.repository.findById(id);

            if (exitingPlant == null) {
                throw new UseCaseException("DeletePlantUseCase#execute: Plant with id " + id + " not found");
            }

            this.repository.deleteById(id);

            return true;

        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public Plant editPlant(EditPlantDTO dto) {
        if (dto == null) {
            throw new UseCaseException("EditPlantUseCase#execute: dto is null");
        }

        try {
            long id = Long.parseLong(dto.getId());
            Plant plant = repository.findById(id);

            if (plant == null) {
                throw new UseCaseException("EditPlantUseCase#execute: Plant with id " + id + " not found");
            }

            plant.updateDetails(dto.getName(), dto.getSpecies(), dto.getNotes());
            int frequency = Integer.parseInt(dto.getWateringFrequency());
            plant.updateWateringFrequency(frequency);

            repository.save(plant);

            return plant;

        } catch (NumberFormatException e) {
            throw new UseCaseException(e.getMessage());
        }
    }

    public Plant saveWateringInterval(SaveWateringIntervalDTO dto) {
        if (dto == null) {
            throw new UseCaseException("SaveWateringIntervalUseCase#execute: dto cannot be null");
        }

        if (dto.getPlantId() == null) {
            throw new UseCaseException("SaveWateringIntervalUseCase#execute: plant id cannot be null");
        }

        try {
            int frequency = Integer.parseInt(dto.getWateringInterval());

            long id = Long.parseLong(String.valueOf(dto.getPlantId()));

            Plant plant = repository.findById(id);

            if (plant == null) {
                throw new UseCaseException("SaveWateringIntervalUseCase#execute: Plant with id " + id + " not found");
            }

            plant.updateWateringFrequency(frequency);
            this.repository.update(plant);
            return plant;
        } catch (NumberFormatException e) {
            throw new UseCaseException(e.getMessage());
        }
    }

    public List<Plant> viewPlantList() {
        return repository.findAll();
    }

    public Plant waterPlant(WaterPlantDTO dto) {
        if (dto == null) {
            throw new UseCaseException("WaterPlantUseCase#execute: Invalid waterPlantDTO");
        }

        try {
            long id = Long.parseLong(String.valueOf(dto.getId()));

            Plant plant = this.repository.findById(id);

            if (plant == null) {
                throw new UseCaseException("WaterPlantUseCase#execute: Plant with id " + id + " not found");
            }

            plant.markAsWatered();
            this.repository.save(plant);
            return plant;

        } catch (NumberFormatException e) {
            throw new UseCaseException(e.getMessage());
        }
    }
}
