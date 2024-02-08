package dev.fayzullokh.dtos;

import dev.fayzullokh.entity.TrainingType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrainerResponseDtoTest {

    @Test
    void testTrainerResponseDto_Success() {
        TrainingType trainingType = TrainingType.builder()
                .id(1L)
                .name("Java Development")
                .build();

        TrainerResponseDto trainerResponseDto = TrainerResponseDto.builder()
                .firstName("John")
                .lastName("Doe")
                .trainingType(trainingType)
                .isActive(true)
                .build();

        assertNotNull(trainerResponseDto);
        assertEquals("John", trainerResponseDto.getFirstName());
        assertEquals("Doe", trainerResponseDto.getLastName());
        assertEquals(trainingType, trainerResponseDto.getTrainingType());
        assertTrue(trainerResponseDto.isActive());
    }
}
