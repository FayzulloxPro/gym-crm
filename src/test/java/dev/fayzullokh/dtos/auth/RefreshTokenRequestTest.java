package dev.fayzullokh.dtos.auth;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RefreshTokenRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidRefreshTokenRequest() {
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest("validRefreshToken");

        Set<ConstraintViolation<RefreshTokenRequest>> violations = validator.validate(refreshTokenRequest);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testBlankRefreshToken() {
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest("");

        Set<ConstraintViolation<RefreshTokenRequest>> violations = validator.validate(refreshTokenRequest);

        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void testNullRefreshToken() {
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(null);

        Set<ConstraintViolation<RefreshTokenRequest>> violations = validator.validate(refreshTokenRequest);

        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }
}
