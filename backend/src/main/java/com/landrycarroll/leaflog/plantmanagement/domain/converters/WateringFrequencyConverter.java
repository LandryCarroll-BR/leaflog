package com.landrycarroll.leaflog.plantmanagement.domain.converters;

import com.landrycarroll.leaflog.plantmanagement.domain.valueobjects.WateringFrequencyInDays;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA attribute converter for mapping {@link WateringFrequencyInDays} value objects
 * to {@link Integer} database columns and vice versa.
 *
 * <p>This enables the use of a strongly-typed {@code WateringFrequencyInDays} object
 * in the domain model while persisting it as a simple integer (number of days) in the database.</p>
 */
@Converter
public class WateringFrequencyConverter implements AttributeConverter<WateringFrequencyInDays, Integer> {

    /**
     * Converts a {@link WateringFrequencyInDays} value object into an {@link Integer}
     * for storage in the database.
     *
     * @param attribute the {@code WateringFrequencyInDays} object to convert
     * @return the number of days as an {@code Integer}
     */
    @Override
    public Integer convertToDatabaseColumn(WateringFrequencyInDays attribute) {
        return attribute.value();
    }

    /**
     * Converts an {@link Integer} from the database into a {@link WateringFrequencyInDays} value object.
     *
     * @param dbData the number of days retrieved from the database
     * @return a new {@code WateringFrequencyInDays} object wrapping the provided value
     */
    @Override
    public WateringFrequencyInDays convertToEntityAttribute(Integer dbData) {
        return new WateringFrequencyInDays(dbData);
    }
}