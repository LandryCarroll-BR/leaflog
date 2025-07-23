package com.landrycarroll.leaflog.plants.plantmanagement.services;

import com.landrycarroll.leaflog.plantmanagement.domain.entities.Plant;
import com.landrycarroll.leaflog.plantmanagement.domain.valueobjects.*;
import com.landrycarroll.leaflog.plantmanagement.dtos.PlantDto;
import com.landrycarroll.leaflog.plantmanagement.exceptions.PlantException;
import com.landrycarroll.leaflog.plantmanagement.repositories.PlantRepository;
import com.landrycarroll.leaflog.plantmanagement.services.PlantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


class PlantServiceTest {

    private PlantRepository repository;
    private PlantService service;

    @BeforeEach
    void setup() {
        repository = mock(PlantRepository.class);
        service = new PlantService(repository);
    }

    @Test
    void findAll_shouldReturnAllPlants() {
        List<Plant> expected = List.of(new Plant(), new Plant());
        when(repository.findAll()).thenReturn(expected);

        List<Plant> result = service.findAll();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void findById_whenExists_shouldReturnPlant() {
        UUID id = UUID.randomUUID();
        Plant plant = new Plant();
        when(repository.findById(id)).thenReturn(Optional.of(plant));

        Plant result = service.findById(id);

        assertThat(result).isEqualTo(plant);
    }

    @Test
    void findById_whenNotFound_shouldThrowException() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(PlantException.PlantNotFound.class)
                .hasMessageContaining(id.toString());
    }

    @Test
    void createPlant_shouldSaveAndReturnNewPlant() {
        Date now = new Date();
        PlantDto.Request request = new PlantDto.Request("Aloe", "Succulent", now, 7, "Sunny");
        Plant mockPlant = PlantDto.createPlantFromDto(request);
        when(repository.save(any(Plant.class))).thenReturn(mockPlant);

        Plant result = service.createPlant(request);

        assertThat(result.getPlantName().value()).isEqualTo("Aloe");
        verify(repository).save(any(Plant.class));
    }

    @Nested
    class UpdatePlantTests {
        UUID id = UUID.randomUUID();
        Date now = new Date();
        PlantDto.Request request = new PlantDto.Request("Updated", "NewSpecies", now, 5, "Updated note");

        @Test
        void whenExists_shouldUpdateAndReturn() {
            Plant existing = PlantDto.createPlantFromDto(
                    new PlantDto.Request("Old", "OldSpecies", now, 3, "Old note"));
            when(repository.findById(id)).thenReturn(Optional.of(existing));
            when(repository.save(any(Plant.class))).thenAnswer(inv -> inv.getArgument(0));

            Plant updated = service.updatePlant(id, request);

            assertThat(updated.getPlantName().value()).isEqualTo("Updated");
            assertThat(updated.getSpecies().value()).isEqualTo("NewSpecies");
            assertThat(updated.getWateringFrequency().value()).isEqualTo(5);
            assertThat(updated.getNotes().value()).isEqualTo("Updated note");
        }

        @Test
        void whenNotExists_shouldCreateNewPlant() {
            when(repository.findById(id)).thenReturn(Optional.empty());
            when(repository.save(any(Plant.class))).thenAnswer(inv -> inv.getArgument(0));

            Plant created = service.updatePlant(id, request);

            assertThat(created.getPlantName().value()).isEqualTo("Updated");
        }
    }

    @Test
    void deletePlant_shouldCallRepositoryAndReturnTrue() {
        UUID id = UUID.randomUUID();

        boolean result = service.deletePlant(id);

        assertThat(result).isTrue();
        verify(repository).deleteById(id);
    }

    @Test
    void markAsWatered_shouldUpdateLastWateredAndReturnPlant() {
        UUID id = UUID.randomUUID();
        Plant plant = new Plant();
        plant.setPlantName(new PlantName("Cactus"));
        when(repository.findById(id)).thenReturn(Optional.of(plant));
        when(repository.save(any(Plant.class))).thenReturn(plant);

        Plant result = service.markAsWatered(id);

        assertThat(result.getPlantName().value()).isEqualTo("Cactus");
        verify(repository).save(plant);
    }

    @Nested
    class BulkCreateTests {

        @Test
        void bulkCreate_shouldImportValidLinesAndReportErrors() {
            String content = """
                    Aloe - Succulent - 5 - Needs sun
                    BadLineWithoutEnoughFields
                    Fern - Shade - NotANumber - Mist often
                    Bamboo - Grass - 14 - Grows fast
                    """;

            MockMultipartFile file = new MockMultipartFile("plants.txt", "plants.txt", "text/plain", content.getBytes(StandardCharsets.UTF_8));

            when(repository.save(any(Plant.class))).thenAnswer(inv -> inv.getArgument(0));

            Map<String, Object> result = service.bulkCreate(file);

            List<?> plants = (List<?>) result.get("plants");
            List<?> errors = (List<?>) result.get("errors");

            assertThat(plants).hasSize(2); // Aloe and Bamboo
            assertThat(errors).hasSize(2); // BadLine and NotANumber
            assertThat(errors.get(0).toString()).contains("BadLineWithoutEnoughFields");
            assertThat(errors.get(1).toString()).contains("For input string: \"NotANumber\"");
        }

        @Test
        void bulkCreate_whenIOException_shouldThrowRuntimeException() {
            MultipartFile file = mock(MultipartFile.class);
            try {
                when(file.getInputStream()).thenThrow(new RuntimeException("fail"));
            } catch (Exception ignored) {
            }

            assertThatThrownBy(() -> service.bulkCreate(file))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("Failed to process bulk file");
        }
    }
}
