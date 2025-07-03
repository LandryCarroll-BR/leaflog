package com.landrycarroll.leaflog.plants.services;

import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import com.landrycarroll.leaflog.plants.exceptions.PlantException;
import com.landrycarroll.leaflog.plants.repositories.PlantRepository;
import com.landrycarroll.leaflog.plants.services.dtos.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Responsible for core CRUD operations for Plant entity
 */
@Service
public class PlantService {
    private final PlantRepository repository;

    // Constructor requires plantRepository dependency
    public PlantService(PlantRepository repository) {
        this.repository = repository;
    }

    public Plant addPlant(AddPlantDTO dto) throws PlantException.PlantAlreadyExists, PlantException.InvalidInput {
        if (dto == null) {
            throw new PlantException.InvalidInput("PlantService.addPlant: DTO must not be null");
        }

        try {
            int wateringFrequency = Integer.parseInt(dto.wateringFrequencyAsString());

            Plant plant = new Plant(dto.getName(), dto.getSpecies(), dto.getLastWatered(), wateringFrequency, dto.getNotes());

            Plant existingPlant = repository.findById(plant.getId().value());

            if (existingPlant != null) {
                throw new PlantException.PlantAlreadyExists("PlantService.addPlant: Plant Already Exists");
            }

            repository.save(plant);

            return plant;

        } catch (NumberFormatException e) {
            throw new PlantException.InvalidInput("PlantService.addPlant: Must provide valid watering frequency");
        }
    }

    public boolean deletePlant(DeletePlantDTO dto) throws PlantException.InvalidInput, PlantException.PlantNotFound {
        if (dto == null) {
            throw new PlantException.InvalidInput("PlantService.deletePlant: DTO must not be null");
        }

        try {
            long id = Long.parseLong(dto.getId());

            Plant exitingPlant = this.repository.findById(id);

            if (exitingPlant == null) {
                throw new PlantException.PlantNotFound("PlantService.deletePlant: Plant with ID " + id + " not found");
            }

            this.repository.deleteById(id);

            return true;

        } catch (NumberFormatException e) {
            throw new PlantException.InvalidInput("PlantService.deletePlant: Must provide valid ID");
        }
    }

    public Plant editPlant(EditPlantDTO dto) throws PlantException.InvalidInput, PlantException.PlantNotFound {
        if (dto == null) {
            throw new PlantException.InvalidInput("PlantService.editPlant: DTO must not be null");
        }

        try {
            long id = Long.parseLong(dto.getId());
            Plant plant = repository.findById(id);

            if (plant == null) {
                throw new PlantException.PlantNotFound("PlantService.editPlant: Plant with id " + id + " not found");
            }

            plant.updateDetails(dto.getName(), dto.getSpecies(), dto.getNotes(), dto.getLastWatered());
            int frequency = Integer.parseInt(dto.getWateringFrequency());
            plant.updateWateringFrequency(frequency);

            Plant updatedPlant = new Plant(
                    plant.getId().value(),
                    plant.getName().value(),
                    plant.getSpecies().value(),
                    plant.getLastWatered().value(),
                    plant.getWateringFrequency().value(),
                    plant.getNotes().value()
            );

            repository.save(updatedPlant);

            return plant;

        } catch (NumberFormatException e) {
            throw new PlantException.InvalidInput("PlantService.editPlant: " + e.getMessage());
        }
    }

    public Plant saveWateringInterval(SaveWateringIntervalDTO dto) throws PlantException.InvalidInput, PlantException.PlantNotFound {
        if (dto == null) {
            throw new PlantException.InvalidInput("PlantService.saveWateringInterval: Dto cannot be null");
        }

        if (dto.getPlantId() == null) {
            throw new PlantException.InvalidInput("PlantService.saveWateringInterval: Plant id cannot be null");
        }

        try {
            int frequency = Integer.parseInt(dto.getWateringInterval());

            long id = Long.parseLong(String.valueOf(dto.getPlantId()));

            Plant plant = repository.findById(id);

            if (plant == null) {
                throw new PlantException.InvalidInput("PlantService.saveWateringInterval: Plant with id " + id + " not found");
            }

            plant.updateWateringFrequency(frequency);
            this.repository.update(plant);
            return plant;
        } catch (NumberFormatException e) {
            throw new PlantException.InvalidInput("PlantService.saveWateringInterval " + e.getMessage());
        }
    }

    public List<Plant> viewPlantList() {
        return repository.findAll();
    }

    public Plant waterPlant(WaterPlantDTO dto) throws PlantException.InvalidInput, PlantException.PlantNotFound {
        if (dto == null) {
            throw new PlantException.InvalidInput("PlantService.waterPlant: Invalid waterPlantDTO");
        }

        try {
            long id = Long.parseLong(String.valueOf(dto.getId()));

            Plant plant = this.repository.findById(id);

            if (plant == null) {
                throw new PlantException.PlantNotFound("PlantService.waterPlant: Plant with id " + id + " not found");
            }

            plant.markAsWatered();
            this.repository.save(plant);
            return plant;

        } catch (NumberFormatException e) {
            throw new PlantException.InvalidInput(e.getMessage());
        }
    }
}
