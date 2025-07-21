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

/**
 * {@code PlantService} contains the core business logic for managing plants in the system.
 * It provides operations to create, read, update, delete, and bulk import {@link Plant} entities.
 * <p>
 * This service acts as an intermediary between controllers and the {@link PlantRepository}.
 * </p>
 */
@Service
public class PlantService {

    private final PlantRepository repository;

    /**
     * Constructs a new {@code PlantService} with the given repository.
     *
     * @param repository the data access layer for plant entities
     */
    public PlantService(PlantRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves a list of all plants in the system.
     *
     * @return a list of {@link Plant} objects
     */
    public List<Plant> findAll() {
        return repository.findAll();
    }

    /**
     * Finds a plant by its unique ID.
     *
     * @param id the UUID of the plant
     * @return the corresponding {@link Plant} entity
     * @throws PlantException.PlantNotFound if no plant is found with the given ID
     */
    public Plant findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new PlantException.PlantNotFound(id));
    }

    /**
     * Creates a new {@link Plant} from the provided DTO request.
     *
     * @param request the plant creation data
     * @return the created {@link Plant} entity
     */
    public Plant createPlant(PlantDto.Request request) {
        Plant plant = PlantDto.createPlantFromDto(request);
        return repository.save(plant);
    }

    /**
     * Updates an existing plant with the given ID using new data.
     * If no existing plant is found, a new one is created and saved.
     *
     * @param id      the ID of the plant to update
     * @param request the updated plant data
     * @return the updated (or newly created) {@link Plant} entity
     */
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

    /**
     * Deletes the plant with the given ID.
     *
     * @param id the UUID of the plant to delete
     * @return {@code true} if the operation completes without exception
     */
    public boolean deletePlant(UUID id) {
        repository.deleteById(id);
        return true;
    }

    /**
     * Marks the specified plant as watered by updating its last watered timestamp.
     *
     * @param id the UUID of the plant to mark as watered
     * @return the updated {@link Plant} entity
     * @throws PlantException.PlantNotFound if the plant does not exist
     */
    public Plant markAsWatered(UUID id) {
        Plant plant = repository.findById(id)
                .orElseThrow(() -> new PlantException.PlantNotFound(id));
        plant.markAsWatered();
        return repository.save(plant);
    }

    /**
     * Performs bulk creation of plants from a file. The file is expected to contain lines with the following format:
     * <pre>
     * name - species - wateringFrequency - notes
     * </pre>
     *
     * @param file a {@link MultipartFile} containing plant data
     * @return a {@code Map} with keys {@code "plants"} (List&lt;Plant&gt;) and {@code "errors"} (List&lt;String&gt;)
     * @throws RuntimeException if file processing fails
     */
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
