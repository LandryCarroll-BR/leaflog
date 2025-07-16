package com.landrycarroll.leaflog.plantmanagement.domain.converters;

import com.landrycarroll.leaflog.plantmanagement.domain.valueobjects.Notes;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA attribute converter for mapping {@link Notes} value objects
 * to {@link String} database columns and vice versa.
 *
 * <p>This allows the {@code Notes} domain type to be persisted as a simple string
 * in the database, while preserving strong typing in the domain model.</p>
 */
@Converter
public class NotesConverter implements AttributeConverter<Notes, String> {

    /**
     * Converts a {@link Notes} value object into a {@link String} for storage in the database.
     *
     * @param attribute the {@code Notes} object to convert
     * @return the {@code String} to store in the database
     */
    @Override
    public String convertToDatabaseColumn(Notes attribute) {
        return attribute.value();
    }

    /**
     * Converts a {@link String} from the database into a {@link Notes} value object.
     *
     * @param dbData the {@code String} retrieved from the database
     * @return a new {@code Notes} object wrapping the provided string
     */
    @Override
    public Notes convertToEntityAttribute(String dbData) {
        return new Notes(dbData);
    }
}