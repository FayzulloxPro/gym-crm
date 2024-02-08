package dev.fayzullokh.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TraineeRegistrationDtoTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidTraineeRegistrationDto() {
        TraineeRegistrationDto dto = TraineeRegistrationDto.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(new Date())
                .address("123 Main St")
                .build();

        Set<ConstraintViolation<TraineeRegistrationDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "There should be no validation violations");
    }

    @Test
    public void testInvalidTraineeRegistrationDto() {
        TraineeRegistrationDto dto = TraineeRegistrationDto.builder()
                .firstName("")
                .lastName("")
                .dateOfBirth(new Date())
                .address("address")
                .build();

        Set<ConstraintViolation<TraineeRegistrationDto>> violations = validator.validate(dto);

        assertEquals(2, violations.size(), "There should be two validation violations");
    }
}
