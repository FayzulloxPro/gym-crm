package dev.fayzullokh.dtos;

import static org.junit.jupiter.api.Assertions.*;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class TraineeDTOForTrainerTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidDTO() {
        // Arrange
        TraineeDTOForTrainer dto = TraineeDTOForTrainer.builder()
                .username("john_doe")
                .firstName("John")
                .lastName("Doe")
                .build();

        // Act & Assert
        Set<ConstraintViolation<TraineeDTOForTrainer>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "There should be no validation violations for a valid DTO");

        // Getter methods can be called without any issues
        assertEquals("john_doe", dto.getUsername());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
    }

    @Test
    void testInvalidDTO() {
        // Arrange
        TraineeDTOForTrainer dto = TraineeDTOForTrainer.builder()
                .username("")  // Blank username, violating the @NotBlank constraint
                .firstName("John")
                .lastName("Doe")
                .build();

        // Act & Assert
        Set<ConstraintViolation<TraineeDTOForTrainer>> violations = validator.validate(dto);

        assertEquals(1, violations.size(), "There should be one validation violation");
    }
}
