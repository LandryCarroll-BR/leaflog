package com.landrycarroll.leaflog.plantmanagement.controllers;

import com.landrycarroll.leaflog.plantmanagement.assemblers.PlantModelAssembler;
import com.landrycarroll.leaflog.plantmanagement.domain.entities.Plant;
import com.landrycarroll.leaflog.plantmanagement.dtos.PlantDto;
import com.landrycarroll.leaflog.plantmanagement.services.PlantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import java.util.*;

@RestController
@RequestMapping("/api/plants")
public class PlantController {

    private final PlantService plantService;
    private final PlantModelAssembler assembler;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
        this.assembler = new PlantModelAssembler();
    }

    @GetMapping
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(plantService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> plant(@PathVariable UUID id) {
        return ResponseEntity.ok(assembler.toModel(plantService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PlantDto.Request request) {
        Plant saved = plantService.createPlant(request);
        return ResponseEntity.created(linkTo(methodOn(PlantController.class).plant(saved.getId())).toUri())
                .body(assembler.toModel(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody PlantDto.Request request) {
        return ResponseEntity.ok(assembler.toModel(plantService.updatePlant(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        plantService.deletePlant(UUID.fromString(id));
        return ResponseEntity.ok("Successfully deleted plant with id " + id);
    }

    @PutMapping("/water/{id}")
    public ResponseEntity<?> water(@PathVariable String id) {
        return ResponseEntity.ok(assembler.toModel(plantService.markAsWatered(UUID.fromString(id))));
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> createBulk(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = plantService.bulkCreate(file);
        List<String> errors = (List<String>) result.get("errors");

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.ok(result.get("plants"));
    }
}