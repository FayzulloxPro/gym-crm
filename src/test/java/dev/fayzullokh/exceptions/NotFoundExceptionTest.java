package dev.fayzullokh.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotFoundExceptionTest {

    @Test
    void testConstructor() {
        String errorMessage = "Username is already taken";
        NotFoundException notFoundException = new NotFoundException(errorMessage);

        assertEquals(errorMessage, notFoundException.getMessage());
    }
}
