package dev.fayzullokh.dtos.auth;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void createTokenRequest_ValidData_Success() {
        TokenRequest tokenRequest = new TokenRequest("validUsername", "validPassword");

        Set<?> violations = validator.validate(tokenRequest);

        assertEquals(0, violations.size());
    }

}
