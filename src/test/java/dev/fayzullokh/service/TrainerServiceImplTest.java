package dev.fayzullokh.service;

import dev.fayzullokh.dtos.TrainerUpdateDto;
import dev.fayzullokh.dtos.TrainingResponseDto;
import dev.fayzullokh.entity.*;
import dev.fayzullokh.exceptions.NotFoundException;
import dev.fayzullokh.mappers.TrainingMapper;
import dev.fayzullokh.repositories.TrainerRepository;
import dev.fayzullokh.repositories.TrainingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TrainerServiceImplTest {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private TrainingTypeServiceImpl trainingTypeService;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TrainingMapper trainingMapper;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testDeleteTrainer_Success() throws NotFoundException {
        Trainer trainer = new Trainer();
        when(trainerRepository.findById(anyLong())).thenReturn(Optional.of(trainer));

        assertDoesNotThrow(() -> trainerService.deleteTrainer(1L));

        verify(trainerRepository, times(1)).findById(anyLong());
        verify(trainerRepository, times(1)).delete(any(Trainer.class));
    }

    @Test
    void testDeleteTrainer_Failure() {
        when(trainerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> trainerService.deleteTrainer(1L));
        verify(trainerRepository, times(1)).findById(anyLong());
        verify(trainerRepository, times(0)).delete(any(Trainer.class));
    }

    @Test
    void testGetTrainerByUsername_Success() throws NotFoundException {
        Trainer trainer = new Trainer();
        when(trainerRepository.findByUser_Username(anyString())).thenReturn(Optional.of(trainer));

        Trainer result = trainerService.getTrainerByUsername("testUsername");

        assertNotNull(result);
        assertEquals(trainer, result);
        verify(trainerRepository, times(1)).findByUser_Username(anyString());
    }

    @Test
    void testGetTrainerByUsername_Failure() {
        when(trainerRepository.findByUser_Username(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> trainerService.getTrainerByUsername("nonexistentUsername"));
        verify(trainerRepository, times(1)).findByUser_Username(anyString());
    }

    /*@Test
    void testUpdateTrainer_Success() throws NotFoundException {
        TrainerUpdateDto trainerUpdateDto = new TrainerUpdateDto();
        trainerUpdateDto.setUsername("testUsername");
        trainerUpdateDto.setFirstName("John");
        trainerUpdateDto.setLastname("Doe");
        trainerUpdateDto.setIsActive(true);
        trainerUpdateDto.setTrainingTypeId(1L);

        Trainer trainer = new Trainer();
        trainer.setUser(new User());
        when(trainerRepository.findById(anyLong())).thenReturn(Optional.of(trainer));
        when(trainingTypeService.findById(anyLong())).thenReturn(new TrainingType());
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);

        Trainer result = trainerService.updateTrainer(trainerUpdateDto, 1L);

        assertNotNull(result);
        verify(trainerRepository, times(1)).findById(anyLong());
        verify(trainingTypeService, times(1)).findById(anyLong());
        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }*/

    @Test
    void testUpdateTrainer_Failure() throws NotFoundException {
        TrainerUpdateDto trainerUpdateDto = new TrainerUpdateDto();
        when(trainerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> trainerService.updateTrainer(trainerUpdateDto, 1L));
        verify(trainerRepository, times(1)).findById(anyLong());
        verify(trainingTypeService, times(0)).findById(anyLong());
        verify(trainerRepository, times(0)).save(any(Trainer.class));
    }

    @Test
    void testGetUnassignedTrainers_Success() {
        Trainee trainee = new Trainee();
        trainee.setTrainers(new ArrayList<>());
        when(trainerRepository.findUnassignedTrainers(anyList())).thenReturn(new ArrayList<>());

        List<Trainer> result = trainerService.getUnassignedTrainers(trainee);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(trainerRepository, times(1)).findUnassignedTrainers(anyList());
    }

    /*@Test
    void testGetUnassignedTrainers_Failure() {
        when(trainerRepository.findUnassignedTrainers(anyList())).thenReturn(null);

        assertThrows(NullPointerException.class, () -> trainerService.getUnassignedTrainers(null));
        verify(trainerRepository, times(1)).findUnassignedTrainers(anyList());
    }*/

    @Test
    void testGetTrainersByUsernames_Success() {
        List<String> trainerUsernames = List.of("username1", "username2");
        when(trainerRepository.findByUser_UsernameIn(anyList())).thenReturn(new ArrayList<>());

        List<Trainer> result = trainerService.getTrainersByUsernames(trainerUsernames);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(trainerRepository, times(1)).findByUser_UsernameIn(anyList());
    }

    /*@Test
    void testGetTrainersByUsernames_Failure() {
        when(trainerRepository.findByUser_UsernameIn(anyList())).thenReturn(null);

        assertThrows(NullPointerException.class, () -> trainerService.getTrainersByUsernames(null));
        verify(trainerRepository, times(1)).findByUser_UsernameIn(anyList());
    }*/

    /*@Test
    void testGetTrainerTrainings_Success() throws NotFoundException {
        Trainer trainer = new Trainer();
        when(trainerRepository.findByUser_Username(anyString())).thenReturn(Optional.of(trainer));
        when(trainingRepository.findTrainingsForTrainer(anyLong(), any(LocalDate.class), any(LocalDate.class), anyString())).thenReturn(new ArrayList<>());

        List<TrainingResponseDto> result = trainerService.getTrainerTrainings("testUsername", LocalDate.now(), LocalDate.now(), "traineeName");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(trainerRepository, times(1)).findByUser_Username(anyString());
        verify(trainingRepository, times(1)).findTrainingsForTrainer(anyLong(), any(LocalDate.class), any(LocalDate.class), anyString());
        verify(trainingMapper, times(0)).toTrainingResponseDto(any(Training.class));
    }*/

    @Test
    void testGetTrainerTrainings_Failure() {
        when(trainerRepository.findByUser_Username(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                trainerService.getTrainerTrainings("nonexistentUsername", LocalDate.now(), LocalDate.now(), "traineeName"));
        verify(trainerRepository, times(1)).findByUser_Username(anyString());
        verify(trainingRepository, times(0)).findTrainingsForTrainer(anyLong(), any(LocalDate.class), any(LocalDate.class), anyString());
        verify(trainingMapper, times(0)).toTrainingResponseDto(any(Training.class));
    }

    /*@Test
    void testActivateDeactivateTrainee_Success() throws NotFoundException {
        Trainer trainer = new Trainer();
        when(trainerRepository.findByUser_Username(anyString())).thenReturn(Optional.of(trainer));
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);

        assertDoesNotThrow(() -> trainerService.activateDeactivateTrainee("testUsername", true));

        verify(trainerRepository, times(1)).findByUser_Username(anyString());
        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }*/

    @Test
    void testActivateDeactivateTrainee_Failure() {
        when(trainerRepository.findByUser_Username(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> trainerService.activateDeactivateTrainee("nonexistentUsername", true));
        verify(trainerRepository, times(1)).findByUser_Username(anyString());
        verify(trainerRepository, times(0)).save(any(Trainer.class));
    }
}
