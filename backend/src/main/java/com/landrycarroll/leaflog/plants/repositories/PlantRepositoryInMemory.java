package com.landrycarroll.leaflog.plants.repositories;

import com.landrycarroll.leaflog.plants.domain.entities.Plant;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PlantRepositoryInMemory implements PlantRepository {
    private final Map<Long, Plant> plants;

    public PlantRepositoryInMemory() {
        plants = new HashMap<>();
    }

    @Override
    public List<Plant> findAll() {
        return new ArrayList<>(plants.values());
    }

    @Override
    public Plant save(Plant plant) {
        plants.put(plant.getId().value(), plant);
        return plant;
    }

    @Override
    public Plant update(Plant plant) {
        plants.put(plant.getId().value(), plant);
        return plant;
    }

    @Override
    public Plant findById(long id) {
        return plants.get(id);
    }

    @Override
    public Plant deleteById(long id) {
        return plants.remove(id);
    }
}
