package dev.fayzullokh.mappers;

import dev.fayzullokh.dtos.*;
import dev.fayzullokh.entity.Trainee;
import dev.fayzullokh.entity.Trainer;
import dev.fayzullokh.entity.TrainingType;
import dev.fayzullokh.entity.User;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrainerMapperTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testToTrainer_ValidDto_ReturnsTrainer() {
        // Arrange
        TrainerMapper mapper = new TrainerMapper();
        TrainerRegistrationDto dto = TrainerRegistrationDto.builder().build();
        User user = new User();
        TrainingType trainingType = new TrainingType();

        // Act
        Trainer trainer = mapper.toTrainer(dto, user, trainingType);

        // Assert
        assertNotNull(trainer);
        assertEquals(trainingType, trainer.getTrainingType());
        assertEquals(user, trainer.getUser());
    }

    @Test
    void testToTrainerResponseDto_ValidTrainer_ReturnsTrainerResponseDto() {
        // Arrange
        TrainerMapper mapper = new TrainerMapper();
        Trainer trainer = new Trainer();
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setActive(true);
        trainer.setUser(user);
        trainer.setTrainingType(new TrainingType());
        List<Trainee> trainees = new ArrayList<>();
        Trainee trainee = new Trainee();
        User traineeUser = new User();
        traineeUser.setFirstName("Trainee");
        traineeUser.setLastName("One");
        traineeUser.setUsername("trainee1");
        trainee.setUser(traineeUser);
        trainees.add(trainee);
        trainer.setTrainees(trainees);

        // Act
        TrainerResponseDto responseDto = mapper.toTrainerResponseDto(trainer);

        // Assert
        assertNotNull(responseDto);
        assertEquals(user.getFirstName(), responseDto.getFirstName());
        assertEquals(user.getLastName(), responseDto.getLastName());
        assertTrue(responseDto.isActive());
        assertEquals(trainer.getTrainingType(), responseDto.getTrainingType());
        assertFalse(responseDto.getTrainees().isEmpty());
        assertEquals(trainees.size(), responseDto.getTrainees().size());
        assertEquals("Trainee", responseDto.getTrainees().get(0).getFirstName());
        assertEquals("One", responseDto.getTrainees().get(0).getLastName());
        assertEquals("trainee1", responseDto.getTrainees().get(0).getUsername());
    }

    // Similar tests for other methods in TrainerMapper
    // ...

    @Test
    void testToCreatedResponseDTO_ValidTrainer_ReturnsCreatedResponseDTO() {
        // Arrange
        TrainerMapper mapper = new TrainerMapper();
        Trainer createdTrainer = new Trainer();
        User user = new User();
        user.setUsername("john_doe");
        user.setPassword("secretpassword");
        createdTrainer.setUser(user);

        // Act
        CreatedResponseDTO createdResponseDTO = mapper.toCreatedResponseDTO(createdTrainer);

        // Assert
        assertNotNull(createdResponseDTO);
        assertEquals(user.getUsername(), createdResponseDTO.getUsername());
        assertEquals(user.getPassword(), createdResponseDTO.getPassword());
    }

    @Test
    void testToTrainerResponseDtoForNotAssigned_ValidTrainers_ReturnsTrainerResponseDtoForNotAssigned() {
        // Arrange
        TrainerMapper mapper = new TrainerMapper();
        List<Trainer> trainers = new ArrayList<>();
        Trainer trainer = new Trainer();
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("john_doe");
        trainer.setUser(user);
        trainer.setTrainingType(new TrainingType());
        trainers.add(trainer);

        // Act
        List<TrainerResponseForNotAssigned> response = mapper.toTrainerResponseDtoForNotAssigned(trainers);

        // Assert
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(trainers.size(), response.size());
        assertEquals("John", response.get(0).getFirstName());
        assertEquals("Doe", response.get(0).getLastName());
        assertEquals("john_doe", response.get(0).getUsername());
        assertEquals(trainer.getTrainingType(), response.get(0).getTrainingType());
    }
}
