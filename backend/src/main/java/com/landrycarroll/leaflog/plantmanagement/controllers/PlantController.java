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

/**
 * {@code PlantController} is a REST controller that manages HTTP requests
 * related to {@link Plant} resources. It exposes endpoints to retrieve,
 * create, update, delete, and bulk upload plant data.
 * <p>
 * This controller delegates the business logic to {@link PlantService}
 * and maps domain entities to HATEOAS-enriched models using {@link PlantModelAssembler}.
 * </p>
 */
@RestController
@RequestMapping("/api/plants")
public class PlantController {

    private final PlantService plantService;
    private final PlantModelAssembler assembler;

    /**
     * Constructs a {@code PlantController} with the provided service.
     *
     * @param plantService the service layer handling plant operations
     */
    public PlantController(PlantService plantService) {
        this.plantService = plantService;
        this.assembler = new PlantModelAssembler();
    }

    /**
     * Retrieves all plants in the system.
     *
     * @return a {@link ResponseEntity} containing the list of all plants
     */
    @GetMapping
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(plantService.findAll());
    }

    /**
     * Retrieves a specific plant by its ID.
     *
     * @param id the UUID of the plant to retrieve
     * @return a {@link ResponseEntity} containing the plant model
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> plant(@PathVariable UUID id) {
        return ResponseEntity.ok(assembler.toModel(plantService.findById(id)));
    }

    /**
     * Creates a new plant entry using the provided request data.
     *
     * @param request the plant data sent by the client
     * @return a {@link ResponseEntity} with the created plant model and location header
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody PlantDto.Request request) {
        Plant saved = plantService.createPlant(request);
        return ResponseEntity.created(linkTo(methodOn(PlantController.class).plant(saved.getId())).toUri())
                .body(assembler.toModel(saved));
    }

    /**
     * Updates an existing plant identified by ID with new data.
     *
     * @param id      the UUID of the plant to update
     * @param request the updated plant data
     * @return a {@link ResponseEntity} with the updated plant model
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody PlantDto.Request request) {
        return ResponseEntity.ok(assembler.toModel(plantService.updatePlant(id, request)));
    }

    /**
     * Deletes a plant by its ID.
     *
     * @param id the UUID string of the plant to delete
     * @return a {@link ResponseEntity} with a confirmation message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        plantService.deletePlant(UUID.fromString(id));
        return ResponseEntity.ok("Successfully deleted plant with id " + id);
    }

    /**
     * Marks a plant as watered by updating its last watered timestamp.
     *
     * @param id the UUID string of the plant to mark as watered
     * @return a {@link ResponseEntity} with the updated plant model
     */
    @PutMapping("/water/{id}")
    public ResponseEntity<?> water(@PathVariable String id) {
        return ResponseEntity.ok(assembler.toModel(plantService.markAsWatered(UUID.fromString(id))));
    }

    /**
     * Bulk creates plant records from a CSV or text file uploaded via multipart form.
     *
     * @param file the uploaded file containing plant data
     * @return a {@link ResponseEntity} with either the list of created plants or validation errors
     */
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