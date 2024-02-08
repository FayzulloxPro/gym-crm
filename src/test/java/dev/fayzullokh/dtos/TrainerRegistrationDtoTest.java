package dev.fayzullokh.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TrainerRegistrationDtoTest {

    @Test
    void testTrainerRegistrationDto_Success() {
        TrainerRegistrationDto trainerRegistrationDto = TrainerRegistrationDto.builder()
                .firstName("John")
                .lastName("Doe")
                .trainingTypeId(1L)
                .build();

        assertNotNull(trainerRegistrationDto);
        assertEquals("John", trainerRegistrationDto.getFirstName());
        assertEquals("Doe", trainerRegistrationDto.getLastName());
        assertEquals(1L, trainerRegistrationDto.getTrainingTypeId());
    }
}
