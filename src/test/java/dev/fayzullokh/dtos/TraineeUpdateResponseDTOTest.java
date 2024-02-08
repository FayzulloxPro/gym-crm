package dev.fayzullokh.dtos;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TraineeUpdateResponseDTOTest {

    @Test
    void testTraineeUpdateResponseDto() {
        TraineeUpdateResponseDto traineeUpdateResponseDto = TraineeUpdateResponseDto.builder()
                .traineeId(1L)
                .address("123 Main St")
                .dateOfBirth(new Date())
                .firstName("John")
                .lastname("Doe")
                .build();

        assertEquals(1L, traineeUpdateResponseDto.getTraineeId());
        assertEquals("123 Main St", traineeUpdateResponseDto.getAddress());
        assertNotNull(traineeUpdateResponseDto.getDateOfBirth());
        assertEquals("John", traineeUpdateResponseDto.getFirstName());
        assertEquals("Doe", traineeUpdateResponseDto.getLastname());
    }
}
