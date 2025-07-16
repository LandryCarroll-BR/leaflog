package com.landrycarroll.leaflog.plantmanagement.services;

import com.landrycarroll.leaflog.plantmanagement.domain.entities.Plant;
import com.landrycarroll.leaflog.plantmanagement.domain.valueobjects.*;
import com.landrycarroll.leaflog.plantmanagement.dtos.PlantDto;
import com.landrycarroll.leaflog.plantmanagement.exceptions.PlantException;
import com.landrycarroll.leaflog.plantmanagement.repositories.PlantRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class PlantService {

    private final PlantRepository repository;

    public PlantService(PlantRepository repository) {
        this.repository = repository;
    }

    public List<Plant> findAll() {
        return repository.findAll();
    }

    public Plant findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new PlantException.PlantNotFound(id));
    }

    public Plant createPlant(PlantDto.Request request) {
        Plant plant = PlantDto.createPlantFromDto(request);
        return repository.save(plant);
    }

    public Plant updatePlant(UUID id, PlantDto.Request request) {
        Plant newPlant = PlantDto.createPlantFromDto(request);
        return repository.findById(id)
                .map(existing -> {
                    existing.setPlantName(newPlant.getPlantName());
                    existing.setSpecies(newPlant.getSpecies());
                    existing.setLastWatered(newPlant.getLastWatered());
                    existing.setWateringFrequency(newPlant.getWateringFrequency());
                    existing.setNotes(newPlant.getNotes());
                    return repository.save(existing);
                })
                .orElseGet(() -> repository.save(newPlant));
    }

    public boolean deletePlant(UUID id) {
        repository.deleteById(id);
        return true;
    }

    public Plant markAsWatered(UUID id) {
        Plant plant = repository.findById(id)
                .orElseThrow(() -> new PlantException.PlantNotFound(id));
        plant.markAsWatered();
        return repository.save(plant);
    }

    public Map<String, Object> bulkCreate(MultipartFile file) {
        List<Plant> addedPlants = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String entry = line.trim();
                if (entry.isEmpty()) continue;

                try {
                    String[] fields = entry.split("-", 5);
                    String name = fields.length > 0 ? fields[0].trim() : "";
                    String species = fields.length > 1 ? fields[1].trim() : "";
                    String wateringFrequency = fields.length > 2 ? fields[2].trim() : "";
                    String notes = fields.length > 3 ? fields[3].trim() : "";

                    Plant newPlant = new Plant();
                    newPlant.setPlantName(new PlantName(name));
                    newPlant.setSpecies(new PlantSpecies(species));
                    newPlant.setLastWatered(new LastWateredDate(new Date()));
                    newPlant.setWateringFrequency(new WateringFrequencyInDays(Integer.parseInt(wateringFrequency)));
                    newPlant.setNotes(new Notes(notes));

                    addedPlants.add(repository.save(newPlant));
                } catch (Exception e) {
                    errors.add("Error in line: \"" + line + "\" — " + e.getMessage());
                }
            }
            return Map.of("plants", addedPlants, "errors", errors);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process bulk file: " + e.getMessage(), e);
        }
    }
}
