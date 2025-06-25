package com.landrycarroll.leaflog.plants.domain.valueobjects;

import com.landrycarroll.leaflog.plants.exceptions.DomainValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotesTest {

    @Test
    public void shouldCreateNotes() {
        Notes notes = new Notes("notes");
        assertEquals("notes", notes.value());
    }

    @Test
    public void shouldThrowExceptionWhenExceedsMaximum() {
        int MAXIMUM_LENGTH = 500;

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < MAXIMUM_LENGTH; i++) {
            stringBuilder.append(i);
        }

        stringBuilder.append("test");

        DomainValidationException ex = assertThrows(DomainValidationException.class, () -> new Notes(stringBuilder.toString()));

        assertEquals("Notes must be less than " + MAXIMUM_LENGTH + " characters.", ex.getMessage());
    }

}
