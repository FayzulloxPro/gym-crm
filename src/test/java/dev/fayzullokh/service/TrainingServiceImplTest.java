package dev.fayzullokh.service;

import dev.fayzullokh.dtos.TrainingRequestDto;
import dev.fayzullokh.entity.Trainee;
import dev.fayzullokh.entity.Trainer;
import dev.fayzullokh.entity.Training;
import dev.fayzullokh.entity.TrainingType;
import dev.fayzullokh.exceptions.NotFoundException;
import dev.fayzullokh.repositories.TrainingRepository;
import dev.fayzullokh.repositories.TrainingTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TrainingServiceImplTest {

    @Mock
    private TraineeServiceImpl traineeService;

    @Mock
    private TrainerServiceImpl trainerService;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTraining_Success() throws NotFoundException {
        TrainingRequestDto trainingRequestDto = new TrainingRequestDto();
        trainingRequestDto.setTraineeUsername("traineeUsername");
        trainingRequestDto.setTrainerUsername("trainerUsername");
        trainingRequestDto.setTrainingName("TrainingName");
        trainingRequestDto.setTrainingDate(new Date());
        trainingRequestDto.setTrainingDuration(60);

        Trainee trainee = new Trainee();
        when(traineeService.getTraineeByUsername(anyString())).thenReturn(trainee);

        Trainer trainer = new Trainer();
        when(trainerService.getTrainerByUsername(anyString())).thenReturn(trainer);

        when(trainingRepository.save(any(Training.class))).thenReturn(new Training());

        assertDoesNotThrow(() -> trainingService.addTraining(trainingRequestDto));

        verify(traineeService, times(1)).getTraineeByUsername(anyString());
        verify(trainerService, times(1)).getTrainerByUsername(anyString());
        verify(trainingRepository, times(1)).save(any(Training.class));
    }

    @Test
    void testAddTraining_Failure_TraineeNotFound() throws NotFoundException {
        TrainingRequestDto trainingRequestDto = new TrainingRequestDto();
        trainingRequestDto.setTraineeUsername("nonexistentTrainee");
        trainingRequestDto.setTrainerUsername("trainerUsername");

        when(traineeService.getTraineeByUsername(anyString())).thenThrow(new NotFoundException("Trainee not found"));

        assertThrows(NotFoundException.class, () -> trainingService.addTraining(trainingRequestDto));

        verify(traineeService, times(1)).getTraineeByUsername(anyString());
        verify(trainerService, times(0)).getTrainerByUsername(anyString());
        verify(trainingRepository, times(0)).save(any(Training.class));
    }


    @Test
    void testGetAllTrainingTypes_Success() {
        List<TrainingType> trainingTypes = new ArrayList<>();
        when(trainingTypeRepository.findAll()).thenReturn(trainingTypes);

        List<TrainingType> result = trainingService.getAllTrainingTypes();

        assertNotNull(result);
        assertEquals(trainingTypes, result);

        verify(trainingTypeRepository, times(1)).findAll();
    }

}
