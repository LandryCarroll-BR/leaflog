package com.landrycarroll.leaflog.plantmanagement.domain.converters;

import com.landrycarroll.leaflog.plantmanagement.domain.valueobjects.LastWateredDate;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Date;

/**
 * JPA attribute converter for mapping {@link LastWateredDate} value objects
 * to {@link java.util.Date} database columns and vice versa.
 *
 * <p>This allows the use of a strongly-typed domain-specific {@code LastWateredDate}
 * in your entity while persisting it as a standard {@code Date} in the database.</p>
 */
@Converter
public class LastWateredDateConverter implements AttributeConverter<LastWateredDate, Date> {

    /**
     * Converts a {@link LastWateredDate} value object into a {@link Date} for database storage.
     *
     * @param attribute the {@code LastWateredDate} object to convert
     * @return the {@code Date} to be stored in the database
     */
    @Override
    public Date convertToDatabaseColumn(LastWateredDate attribute) {
        return attribute.value();
    }


    /**
     * Converts a {@link Date} from the database into a {@link LastWateredDate} value object.
     *
     * @param dbData the {@code Date} retrieved from the database
     * @return a {@code LastWateredDate} wrapping the retrieved {@code Date}
     */
    @Override
    public LastWateredDate convertToEntityAttribute(Date dbData) {
        return new LastWateredDate(dbData);
    }
}