package com.landrycarroll.leaflog.plantmanagement.assemblers;


import com.landrycarroll.leaflog.plantmanagement.controllers.PlantController;
import com.landrycarroll.leaflog.plantmanagement.domain.entities.Plant;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Assembler class that converts {@link Plant} domain objects into HATEOAS-compliant {@link EntityModel} representations.
 *
 * <p>This allows the API to include hypermedia links with each plant resource, enabling better discoverability
 * and navigability for clients following HATEOAS principles.</p>
 */
@Component
public class PlantModelAssembler implements RepresentationModelAssembler<Plant, EntityModel<Plant>> {

    /**
     * Converts a {@link Plant} object into an {@link EntityModel} with self and collection links.
     *
     * <p>Includes:
     * <ul>
     *     <li>A {@code self} link pointing to the plant's own resource endpoint</li>
     *     <li>A {@code plants} link pointing to the collection of all plants</li>
     * </ul>
     * </p>
     *
     * @param plant the {@link Plant} domain object to convert
     * @return an {@link EntityModel} containing the plant and relevant hypermedia links
     */
    @Override
    public EntityModel<Plant> toModel(Plant plant) {
        return EntityModel.of(plant,
                linkTo(methodOn(PlantController.class).plant(plant.getId())).withSelfRel(),
                linkTo(methodOn(PlantController.class).all()).withRel("plants"));
    }
}
