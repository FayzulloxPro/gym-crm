package dev.fayzullokh.controller;

import dev.fayzullokh.controller.TrainingController;
import dev.fayzullokh.dtos.TrainingRequestDto;
import dev.fayzullokh.entity.TrainingType;
import dev.fayzullokh.exceptions.NotFoundException;
import dev.fayzullokh.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TrainingControllerTest {

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private TrainingController trainingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void addTraining_ValidTrainingRequestDto_ReturnsOk() throws NotFoundException {
        // Arrange
        TrainingRequestDto trainingRequestDto = new TrainingRequestDto(/* fill with valid data */);

        // Act
        ResponseEntity<Void> response = trainingController.addTraining(trainingRequestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(trainingService, times(1)).addTraining(trainingRequestDto);
    }

    @Test
    void addTraining_InvalidTrainingRequestDto_ThrowsNotFoundException() throws NotFoundException {
        // Arrange
        TrainingRequestDto trainingRequestDto = new TrainingRequestDto(/* fill with invalid data */);
        doThrow(new NotFoundException("Training not found")).when(trainingService).addTraining(trainingRequestDto);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> trainingController.addTraining(trainingRequestDto));
        verify(trainingService, times(1)).addTraining(trainingRequestDto);
    }

    @Test
    void getTrainingTypes_ReturnsListOfTrainingTypes() {
        // Arrange
        List<TrainingType> expectedTrainingTypes = Collections.singletonList(new TrainingType(/* fill with valid data */));
        when(trainingService.getAllTrainingTypes()).thenReturn(expectedTrainingTypes);

        // Act
        ResponseEntity<List<TrainingType>> response = trainingController.getTrainingTypes();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTrainingTypes, response.getBody());
        verify(trainingService, times(1)).getAllTrainingTypes();
    }
}
