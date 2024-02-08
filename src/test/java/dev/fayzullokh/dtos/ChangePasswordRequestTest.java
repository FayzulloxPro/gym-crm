package dev.fayzullokh.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChangePasswordRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidChangePasswordRequest() {
        ChangePasswordRequest request = ChangePasswordRequest.builder()
                .oldPassword("oldPass123")
                .newPassword("newPass456")
                .username("username")
                .build();

        Set<ConstraintViolation<ChangePasswordRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty(), "There should be no validation violations");
    }

    @Test
    public void testInvalidChangePasswordRequest() {
        ChangePasswordRequest request = ChangePasswordRequest.builder()
                .oldPassword("")
                .newPassword("sh")
                .username("username")
                .build();

        Set<ConstraintViolation<ChangePasswordRequest>> violations = validator.validate(request);

        assertEquals(2, violations.size(), "There should be two validation violations");
    }
}
