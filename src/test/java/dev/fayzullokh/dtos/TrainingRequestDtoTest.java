package dev.fayzullokh.dtos;

import static org.junit.jupiter.api.Assertions.*;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.Date;

public class TrainingRequestDtoTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidDTO() {
        // Arrange
        TrainingRequestDto dto = TrainingRequestDto.builder()
                .traineeUsername("john_doe_trainee")
                .trainerUsername("jane_smith_trainer")
                .trainingName("Java Programming")
                .trainingDate(new Date())
                .trainingDuration(60)
                .build();

        // Act & Assert
        Set<ConstraintViolation<TrainingRequestDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "There should be no validation violations for a valid DTO");

        // Getter methods can be called without any issues
        assertEquals("john_doe_trainee", dto.getTraineeUsername());
        assertEquals("jane_smith_trainer", dto.getTrainerUsername());
        assertEquals("Java Programming", dto.getTrainingName());
        assertNotNull(dto.getTrainingDate());
        assertEquals(60, dto.getTrainingDuration());
    }

    @Test
    void testInvalidDTO() {
        // Arrange
        TrainingRequestDto dto = TrainingRequestDto.builder()
                .traineeUsername("")  // Blank trainee username, violating the @NotBlank constraint
                .trainerUsername("jane_smith_trainer")
                .trainingName("Java Programming")
                .trainingDate(null)  // Null training date, violating the @NotNull constraint
                .trainingDuration(60)
                .build();

        // Act & Assert
        Set<ConstraintViolation<TrainingRequestDto>> violations = validator.validate(dto);

        assertEquals(2, violations.size(), "There should be two validation violations");
    }
}
