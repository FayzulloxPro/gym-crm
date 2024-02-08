package dev.fayzullokh.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TraineeResponseDtoTest {

    @Test
    void testTraineeResponseDto() {
        TraineeResponseDto traineeResponseDto = TraineeResponseDto.builder()
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .build();

        assertEquals("John", traineeResponseDto.getFirstName());
        assertEquals("Doe", traineeResponseDto.getLastName());
        assertTrue(traineeResponseDto.isActive());

        assertNotNull(traineeResponseDto.toString());
    }
}
