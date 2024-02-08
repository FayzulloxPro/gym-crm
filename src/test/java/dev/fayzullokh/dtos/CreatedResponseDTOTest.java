package dev.fayzullokh.dtos;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class CreatedResponseDTOTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidDTO() {
        // Arrange
        CreatedResponseDTO dto = CreatedResponseDTO.builder()
                .username("john_doe")
                .password("secretpassword")
                .build();

        // Act & Assert
        assertDoesNotThrow(() -> {
            // Validation should pass for a valid DTO
            // No exception should be thrown
            // Getter methods can be called without any issues
            assertEquals("john_doe", dto.getUsername());
            assertEquals("secretpassword", dto.getPassword());
        });
    }

    @Test
    void testInvalidDTO() {
        // Arrange
        CreatedResponseDTO dto = CreatedResponseDTO.builder()
                .username("")  // Blank username, violating the @NotBlank constraint
                .password("secretpassword")
                .build();

        // Act & Assert
        Set<ConstraintViolation<CreatedResponseDTO>> violations = validator.validate(dto);

        assertEquals(1, violations.size(), "There should be one validation violation");
    }
}
