package dev.fayzullokh.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DuplicateUsernameExceptionTest {

    @Test
    void testDuplicateUsernameException() {
        String errorMessage = "Username is already taken";

        DuplicateUsernameException exception = new DuplicateUsernameException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }
}
