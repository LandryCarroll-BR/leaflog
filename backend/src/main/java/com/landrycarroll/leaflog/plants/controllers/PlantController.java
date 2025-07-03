package com.landrycarroll.leaflog.plants.controllers;

import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import com.landrycarroll.leaflog.plants.exceptions.PlantException;
import com.landrycarroll.leaflog.plants.services.PlantService;
import com.landrycarroll.leaflog.plants.services.dtos.*;
import com.landrycarroll.leaflog.plants.services.dtos.AddPlantDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Provides inputs for Plant CRUD operations to Plant Service and returns output to user
 */
@RestController
@RequestMapping("/api/plants")
public class PlantController {
    private final PlantService service;

    public PlantController(PlantService plantService) {
        this.service = plantService;
    }

    @PostMapping
    public ResponseEntity<?> addPlant(@RequestBody AddPlantDTO dto) {
        try {
            Plant plant = service.addPlant(dto);
            return ResponseEntity.ok(plant);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getPlants() {
        try {
            List<Plant> plants = service.viewPlantList();
            return ResponseEntity.ok(plants);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editPlant(@PathVariable("id") String id, @RequestBody EditPlantDTO dto) {
        try {
            EditPlantDTO newDto = new EditPlantDTO(id,
                    dto.getName(),
                    dto.getSpecies(),
                    dto.getNotes(),
                    dto.getWateringFrequency(),
                    dto.getLastWatered()
            );
            Plant updated = service.editPlant(newDto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlant(@PathVariable("id") String id) {
        try {
            service.deletePlant(new DeletePlantDTO(id));
            return ResponseEntity.ok("Successfully deleted plant with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/water/{id}")
    public ResponseEntity<?> waterPlant(@PathVariable("id") String id) {
        try {
            Plant watered = service.waterPlant(new WaterPlantDTO(id));
            return ResponseEntity.ok(watered);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> addPlantBulk(@RequestParam("file") MultipartFile file) {
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

                    Plant plant = service.addPlant(new AddPlantDTO(
                            name,
                            species,
                            wateringFrequency,
                            notes,
                            new Date()
                    ));

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
