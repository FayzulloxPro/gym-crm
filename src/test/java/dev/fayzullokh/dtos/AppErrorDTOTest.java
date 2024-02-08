package dev.fayzullokh.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppErrorDTOTest {

    @Test
    void testConstructorWithBody() {
        String errorPath = "/api/error";
        String errorMessage = "An error occurred";
        Object errorBody = "Detailed error information";
        Integer errorCode = 500;

        AppErrorDTO appErrorDTO = new AppErrorDTO(errorPath, errorMessage, errorBody, errorCode);

        assertEquals(errorPath, appErrorDTO.getErrorPath());
        assertEquals(errorMessage, appErrorDTO.getErrorMessage());
        assertEquals(errorBody, appErrorDTO.getErrorBody());
        assertEquals(errorCode, appErrorDTO.getErrorCode());
        assertNotNull(appErrorDTO.getTimestamp());
    }

    @Test
    void testConstructorWithoutBody() {
        String errorPath = "/api/error";
        String errorMessage = "An error occurred";
        Integer errorCode = 500;

        AppErrorDTO appErrorDTO = new AppErrorDTO(errorPath, errorMessage, errorCode);

        assertEquals(errorPath, appErrorDTO.getErrorPath());
        assertEquals(errorMessage, appErrorDTO.getErrorMessage());
        assertNull(appErrorDTO.getErrorBody());
        assertEquals(errorCode, appErrorDTO.getErrorCode());
        assertNotNull(appErrorDTO.getTimestamp());
    }


}
