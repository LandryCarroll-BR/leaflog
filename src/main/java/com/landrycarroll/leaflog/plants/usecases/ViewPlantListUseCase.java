package com.landrycarroll.leaflog.plants.usecases;

import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import com.landrycarroll.leaflog.plants.repositories.PlantRepository;

import java.util.List;

public class ViewPlantListUseCase {
    private final PlantRepository repository;

    public ViewPlantListUseCase(PlantRepository repository) {
        this.repository = repository;
    }

    public List<Plant> execute() {
        return repository.findAll();
    }
}
