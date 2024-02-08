package dev.fayzullokh.dtos;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TraineeUpdateDTOTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testValidTraineeUpdateDTO() {
        TraineeUpdateDTO traineeUpdateDTO = TraineeUpdateDTO.builder()
                .address("123 Main St")
                .dateOfBirth(new Date())
                .lastname("Doe")
                .firstname("John")
                .username("johndoe")
                .isActive(true)
                .build();

        var violations = validator.validate(traineeUpdateDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidTraineeUpdateDTO() {
        TraineeUpdateDTO traineeUpdateDTO = TraineeUpdateDTO.builder().build();

        var violations = validator.validate(traineeUpdateDTO);

        assertFalse(violations.isEmpty());

        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("address")));
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("dateOfBirth")));
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("lastname")));
    }
}
