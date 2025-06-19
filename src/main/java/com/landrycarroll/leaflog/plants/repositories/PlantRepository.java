package com.landrycarroll.leaflog.plants.repositories;

import com.landrycarroll.leaflog.plants.domain.entities.Plant;

import java.util.List;

public interface PlantRepository {
    List<Plant> findAll();

    Plant save(Plant plant);

    Plant update(Plant plant);

    Plant findById(long id);

    Plant deleteById(long id);
}
