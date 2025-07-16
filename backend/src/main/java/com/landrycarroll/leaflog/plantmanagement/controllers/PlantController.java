package com.landrycarroll.leaflog.plantmanagement.controllers;

import com.landrycarroll.leaflog.plantmanagement.assemblers.PlantModelAssembler;
import com.landrycarroll.leaflog.plantmanagement.domain.entities.Plant;
import com.landrycarroll.leaflog.plantmanagement.domain.valueobjects.*;
import com.landrycarroll.leaflog.plantmanagement.dtos.PlantDto;
import com.landrycarroll.leaflog.plantmanagement.exceptions.PlantException;
import com.landrycarroll.leaflog.plantmanagement.repositories.PlantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@RestController
@RequestMapping("/api/plants")
public class PlantController {

    private final PlantRepository repository;

    private final PlantModelAssembler assembler;

    public PlantController(PlantRepository plantRepository) {
        this.repository = plantRepository;
        this.assembler = new PlantModelAssembler();
    }

    @GetMapping
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> plant(@PathVariable UUID id) {
        Plant plant = repository.findById(id).orElseThrow(() -> new PlantException.PlantNotFound(id));

        return ResponseEntity.ok(assembler.toModel(plant));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PlantDto.Request request) {
        Plant savedPlant = repository.save(PlantDto.createPlantFromDto(request));

        return ResponseEntity.created(linkTo(methodOn(PlantController.class).plant(savedPlant.getId())).toUri())
                .body(assembler.toModel(savedPlant));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody PlantDto.Request request) {
        Plant newPlant = PlantDto.createPlantFromDto(request);
        Plant updatedPlant = repository.findById(id)
                .map(plant -> {
                    plant.setPlantName(newPlant.getPlantName());
                    plant.setSpecies(newPlant.getSpecies());
                    plant.setLastWatered(newPlant.getLastWatered());
                    plant.setWateringFrequency(newPlant.getWateringFrequency());
                    plant.setNotes(newPlant.getNotes());
                    return repository.save(plant);
                }).orElseGet(() -> repository.save(newPlant));

        return ResponseEntity.ok(assembler.toModel(updatedPlant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        repository.deleteById(UUID.fromString(id));
        return ResponseEntity.ok("Successfully deleted plant with id " + id);
    }

    @PutMapping("/water/{id}")
    public ResponseEntity<?> water(@PathVariable String id) {
        Plant watered = repository.findById(UUID.fromString(id)).map(plant -> {
            plant.markAsWatered();
            return repository.save(plant);
        }).orElseThrow(() -> new PlantException.PlantNotFound(UUID.fromString(id)));
        return ResponseEntity.ok(assembler.toModel(watered));
    }


    @PostMapping("/bulk")
    public ResponseEntity<?> createBulk(@RequestParam("file") MultipartFile file) {
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

                    Plant plant = repository.save(newPlant);

                    addedPlants.add(plant);
                } catch (PlantException e) {
                    errors.add("Error in line: \"" + line + "\" — " + e.getMessage());
                }
            }

            if (!errors.isEmpty()) {
                return ResponseEntity.badRequest().body(errors);
            }

            return ResponseEntity.ok(addedPlants);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
        }
    }

}
