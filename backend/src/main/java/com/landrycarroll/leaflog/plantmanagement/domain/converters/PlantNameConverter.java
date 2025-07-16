package com.landrycarroll.leaflog.plantmanagement.domain.converters;

import com.landrycarroll.leaflog.plantmanagement.domain.valueobjects.PlantName;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA attribute converter for mapping {@link PlantName} value objects
 * to {@link String} database columns and vice versa.
 *
 * <p>This converter enables the use of the domain-specific {@code PlantName}
 * class while persisting the name as a simple string in the database.</p>
 */
@Converter
public class PlantNameConverter implements AttributeConverter<PlantName, String> {

    /**
     * Converts a {@link PlantName} value object into a {@link String} for database storage.
     *
     * @param attribute the {@code PlantName} object to convert
     * @return the {@code String} to store in the database
     */
    @Override
    public String convertToDatabaseColumn(PlantName attribute) {
        return attribute.value();
    }

    /**
     * Converts a {@link String} from the database into a {@link PlantName} value object.
     *
     * @param dbData the {@code String} retrieved from the database
     * @return a new {@code PlantName} object wrapping the provided string
     */
    @Override
    public PlantName convertToEntityAttribute(String dbData) {
        return new PlantName(dbData);
    }
}