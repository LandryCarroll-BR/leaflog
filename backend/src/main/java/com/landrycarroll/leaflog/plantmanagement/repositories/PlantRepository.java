package com.landrycarroll.leaflog.plantmanagement.repositories;

import com.landrycarroll.leaflog.plantmanagement.domain.entities.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for managing {@link Plant} entities.
 *
 * <p>Extends {@link JpaRepository} to provide basic CRUD operations and
 * query capabilities for {@code Plant} objects, identified by a {@link UUID}.</p>
 *
 * <p>Spring Data JPA will automatically generate the implementation at runtime.</p>
 */
@Repository
public interface PlantRepository extends JpaRepository<Plant, UUID> {
}
