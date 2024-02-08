package dev.fayzullokh.mappers;

import dev.fayzullokh.dtos.*;
import dev.fayzullokh.entity.Trainee;
import dev.fayzullokh.entity.Trainer;
import dev.fayzullokh.entity.User;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TraineeMapperTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testToTrainee_ValidDto_ReturnsTrainee() {
        // Arrange
        TraineeMapper mapper = new TraineeMapper();
        TraineeRegistrationDto dto = TraineeRegistrationDto.builder()
                .dateOfBirth(new Date())
                .address("123 Main St")
                .build();
        User user = new User();

        // Act
        Trainee trainee = mapper.toTrainee(dto, user);

        // Assert
        assertNotNull(trainee);
        assertEquals(dto.getDateOfBirth(), trainee.getDateOfBirth());
        assertEquals(user, trainee.getUser());
        assertEquals(dto.getAddress(), trainee.getAddress());
    }

    @Test
    void testToTraineeResponseDto_ValidTrainee_ReturnsTraineeResponseDto() {
        // Arrange
        TraineeMapper mapper = new TraineeMapper();
        Trainee trainee = new Trainee();
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setActive(true);
        trainee.setUser(user);
        trainee.setAddress("123 Main St");
        trainee.setDateOfBirth(new Date());
        List<Trainer> trainers = new ArrayList<>();
        Trainer trainer = new Trainer();
        User trainerUser = new User();
        trainerUser.setFirstName("Trainer");
        trainerUser.setLastName("One");
        trainerUser.setUsername("trainer1");
        trainer.setUser(trainerUser);
        trainers.add(trainer);
        trainee.setTrainers(trainers);

        // Act
        TraineeResponseDto responseDto = mapper.toTraineeResponseDto(trainee);

        // Assert
        assertNotNull(responseDto);
        assertEquals(user.getFirstName(), responseDto.getFirstName());
        assertEquals(user.getLastName(), responseDto.getLastName());
        assertTrue(responseDto.isActive());
        assertEquals(trainee.getAddress(), responseDto.getAddress());
        assertEquals(trainee.getDateOfBirth(), responseDto.getDateOfBirth());
        assertFalse(responseDto.getTrainers().isEmpty());
        assertEquals(trainers.size(), responseDto.getTrainers().size());
        assertEquals("Trainer", responseDto.getTrainers().get(0).getTrainerFirstName());
        assertEquals("One", responseDto.getTrainers().get(0).getTrainerLastName());
        assertEquals("trainer1", responseDto.getTrainers().get(0).getTrainerUsername());
        assertNull(responseDto.getTrainers().get(0).getTrainerSpecialization());
    }

    // Similar tests for other methods in TraineeMapper
    // ...

    @Test
    void testToCreatedResponseDTO_ValidTrainee_ReturnsCreatedResponseDTO() {
        // Arrange
        TraineeMapper mapper = new TraineeMapper();
        Trainee trainee = new Trainee();
        User user = new User();
        user.setUsername("john_doe");
        user.setPassword("secretpassword");
        trainee.setUser(user);

        // Act
        CreatedResponseDTO createdResponseDTO = mapper.toCreatedResponseDTO(trainee);

        // Assert
        assertNotNull(createdResponseDTO);
        assertEquals(user.getUsername(), createdResponseDTO.getUsername());
        assertEquals(user.getPassword(), createdResponseDTO.getPassword());
    }
}
