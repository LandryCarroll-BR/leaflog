package com.landrycarroll.leaflog.plantmanagement.domain.converters;

import com.landrycarroll.leaflog.plantmanagement.domain.valueobjects.PlantSpecies;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA attribute converter for mapping {@link PlantSpecies} value objects
 * to {@link String} columns in the database and vice versa.
 *
 * <p>This converter enables strong typing in the domain model while persisting
 * plant species as simple strings in the database.</p>
 */
@Converter
public class PlantSpeciesConverter implements AttributeConverter<PlantSpecies, String> {

    /**
     * Converts a {@link PlantSpecies} value object into a {@link String} for database storage.
     *
     * @param attribute the {@code PlantSpecies} object to convert
     * @return the {@code String} representation to store in the database
     */
    @Override
    public String convertToDatabaseColumn(PlantSpecies attribute) {
        return attribute.value();
    }

    /**
     * Converts a {@link String} from the database into a {@link PlantSpecies} value object.
     *
     * @param dbData the {@code String} retrieved from the database
     * @return a new {@code PlantSpecies} object wrapping the provided string
     */
    @Override
    public PlantSpecies convertToEntityAttribute(String dbData) {
        return new PlantSpecies(dbData);
    }
}