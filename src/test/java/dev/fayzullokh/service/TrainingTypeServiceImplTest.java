package dev.fayzullokh.service;

import dev.fayzullokh.entity.TrainingType;
import dev.fayzullokh.exceptions.NotFoundException;
import dev.fayzullokh.repositories.TrainingTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TrainingTypeServiceImplTest {

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @InjectMocks
    private TrainingTypeServiceImpl trainingTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_Success() throws NotFoundException {
        long trainingTypeId = 1L;
        TrainingType trainingType = new TrainingType();
        when(trainingTypeRepository.findById(anyLong())).thenReturn(Optional.of(trainingType));

        TrainingType result = trainingTypeService.findById(trainingTypeId);

        assertNotNull(result);
        assertEquals(trainingType, result);

        verify(trainingTypeRepository, times(1)).findById(anyLong());
    }

    @Test
    void testFindById_Failure_NotFound() {
        long nonExistentTrainingTypeId = 99L;
        when(trainingTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> trainingTypeService.findById(nonExistentTrainingTypeId));

        verify(trainingTypeRepository, times(1)).findById(anyLong());
    }

}
