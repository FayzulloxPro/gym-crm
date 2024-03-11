package dev.fayzullokh.service;

import dev.fayzullokh.dtos.TraineeUpdateDTO;
import dev.fayzullokh.dtos.TrainingResponseDto;
import dev.fayzullokh.entity.Trainee;
import dev.fayzullokh.entity.Trainer;
import dev.fayzullokh.entity.Training;
import dev.fayzullokh.entity.User;
import dev.fayzullokh.exceptions.DuplicateUsernameException;
import dev.fayzullokh.exceptions.NotFoundException;
import dev.fayzullokh.exceptions.UnknownException;
import dev.fayzullokh.mappers.TrainingMapper;
import dev.fayzullokh.repositories.TraineeRepository;
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

class TraineeServiceImplTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private TrainerServiceImpl trainerService;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TrainingMapper trainingMapper;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @BeforeEach
    void setUp() {
        trainingRepository = mock(TrainingRepository.class);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTrainee_Success() throws UnknownException, DuplicateUsernameException {
        Trainee trainee = new Trainee();
        User user = new User();
        trainee.setUser(user);
        when(userService.createUser(any(User.class))).thenReturn(user);
        when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);

        Trainee result = traineeService.createTrainee(trainee);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        verify(userService, times(1)).createUser(any(User.class));
        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }

    /*@Test
    void testCreateTrainee_Failure() {
        Trainee trainee = new Trainee();
        User user = new User();
        trainee.setUser(user);
        when(userService.createUser(any(User.class))).thenReturn(null);

        assertThrows(NullPointerException.class, () -> traineeService.createTrainee(trainee));
        verify(userService, times(1)).createUser(any(User.class));
        verify(traineeRepository, times(0)).save(any(Trainee.class));
    }*/

    @Test
    void testGetTraineeById_Success() throws NotFoundException {
        Trainee trainee = new Trainee();
        when(traineeRepository.findById(anyLong())).thenReturn(Optional.of(trainee));

        Trainee result = traineeService.getTraineeById(1L);

        assertNotNull(result);
        assertEquals(trainee, result);
        verify(traineeRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetTraineeById_Failure() {
        when(traineeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> traineeService.getTraineeById(1L));
        verify(traineeRepository, times(1)).findById(anyLong());
    }

    @Test
    void testFindAllTrainees_Success() {
        List<Trainee> trainees = new ArrayList<>();
        when(traineeRepository.findAll()).thenReturn(trainees);

        List<Trainee> result = traineeService.findAllTrainees();

        assertNotNull(result);
        assertEquals(trainees, result);
        verify(traineeRepository, times(1)).findAll();
    }

   /* @Test
    void testFindAllTrainees_Failure() {
        when(traineeRepository.findAll()).thenReturn(null);

        assertThrows(NullPointerException.class, () -> traineeService.findAllTrainees());
        verify(traineeRepository, times(1)).findAll();
    }*/

    @Test
    void testUpdateTrainee_Success() throws NotFoundException {
        TraineeUpdateDTO traineeUpdateDTO = new TraineeUpdateDTO();
        traineeUpdateDTO.setUsername("newUsername");
        traineeUpdateDTO.setFirstname("newFirstName");
        traineeUpdateDTO.setLastname("newLastName");
        traineeUpdateDTO.setActive(true);

        Trainee existingTrainee = new Trainee();
        existingTrainee.setId(1L);
        existingTrainee.setUser(new User());
        when(traineeRepository.findById(anyLong())).thenReturn(Optional.of(existingTrainee));
        when(traineeRepository.save(any(Trainee.class))).thenReturn(existingTrainee);

        Trainee result = traineeService.updateTrainee(traineeUpdateDTO, 1L);

        assertNotNull(result);
        assertEquals(traineeUpdateDTO.getUsername(), result.getUser().getUsername());
        assertEquals(traineeUpdateDTO.getFirstname(), result.getUser().getFirstName());
        assertEquals(traineeUpdateDTO.getLastname(), result.getUser().getLastName());
        assertEquals(traineeUpdateDTO.isActive(), result.getUser().isActive());
        verify(traineeRepository, times(1)).findById(anyLong());
        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }

    @Test
    void testUpdateTrainee_Failure() {
        TraineeUpdateDTO traineeUpdateDTO = new TraineeUpdateDTO();

        when(traineeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> traineeService.updateTrainee(traineeUpdateDTO, 1L));
        verify(traineeRepository, times(1)).findById(anyLong());
        verify(traineeRepository, times(0)).save(any(Trainee.class));
    }

    @Test
    void testDeleteTrainee_Success() throws NotFoundException {
        Trainee trainee = new Trainee();
        when(traineeRepository.findById(anyLong())).thenReturn(Optional.of(trainee));

        assertDoesNotThrow(() -> traineeService.deleteTrainee(1L));

        verify(traineeRepository, times(1)).findById(anyLong());
        verify(traineeRepository, times(1)).delete(any(Trainee.class));
    }

    @Test
    void testDeleteTrainee_Failure() {
        when(traineeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> traineeService.deleteTrainee(1L));
        verify(traineeRepository, times(1)).findById(anyLong());
        verify(traineeRepository, times(0)).delete(any(Trainee.class));
    }

    @Test
    void testGetTraineeByUsername_Success() throws NotFoundException {
        Trainee trainee = new Trainee();
        when(traineeRepository.findByUser_Username(anyString())).thenReturn(Optional.of(trainee));

        Trainee result = traineeService.getTraineeByUsername("testUsername");

        assertNotNull(result);
        assertEquals(trainee, result);
        verify(traineeRepository, times(1)).findByUser_Username(anyString());
    }

    @Test
    void testGetTraineeByUsername_Failure() {
        when(traineeRepository.findByUser_Username(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> traineeService.getTraineeByUsername("nonexistentUsername"));
        verify(traineeRepository, times(1)).findByUser_Username(anyString());
    }

    @Test
    void testDeleteTraineeByUsername_Success() throws NotFoundException {
        Trainee trainee = new Trainee();
        when(traineeRepository.findByUser_Username(anyString())).thenReturn(Optional.of(trainee));

        assertDoesNotThrow(() -> traineeService.deleteTraineeByUsername("testUsername"));

        verify(traineeRepository, times(1)).findByUser_Username(anyString());
        verify(traineeRepository, times(1)).delete(any(Trainee.class));
    }

    @Test
    void testDeleteTraineeByUsername_Failure() {
        when(traineeRepository.findByUser_Username(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> traineeService.deleteTraineeByUsername("nonexistentUsername"));
        verify(traineeRepository, times(1)).findByUser_Username(anyString());
        verify(traineeRepository, times(0)).delete(any(Trainee.class));
    }

    @Test
    void testUpdateTrainers_Success() throws NotFoundException {
        List<String> trainerUsernames = List.of("trainer1", "trainer2");
        Trainee trainee = new Trainee();
        when(traineeRepository.findByUser_Username(anyString())).thenReturn(Optional.of(trainee));
        when(trainerService.getTrainersByUsernames(anyList())).thenReturn(List.of(new Trainer(), new Trainer()));
        when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);

        List<Trainer> result = traineeService.updateTrainers("testUsername", trainerUsernames);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(traineeRepository, times(1)).findByUser_Username(anyString());
        verify(trainerService, times(1)).getTrainersByUsernames(anyList());
        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }

    /*@Test
    void testUpdateTrainers_Failure() {
        List<String> trainerUsernames = List.of("trainer1", "trainer2");
        when(traineeRepository.findByUser_Username(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> traineeService.updateTrainers("nonexistentUsername", trainerUsernames));
        verify(traineeRepository, times(1)).findByUser_Username(anyString());
        verify(trainerService, times(0)).getTrainersByUsernames(anyList());
        verify(traineeRepository, times(0)).save(any(Trainee.class));
    }*/

    /*@Test
    void testGetTraineeTrainings_Success() throws NotFoundException {
        Trainee trainee = new Trainee();
        when(traineeRepository.findByUser_Username(anyString())).thenReturn(Optional.of(trainee));
        when(trainingRepository.findTrainingsForTrainee(anyLong(), any(LocalDate.class), any(LocalDate.class), anyString(), anyLong()))
                .thenReturn(List.of(new Training()));
        when(trainingMapper.toTrainingResponseDto(any(Training.class))).thenReturn(new TrainingResponseDto());

        List<TrainingResponseDto> result = traineeService.getTraineeTrainings("testUsername", LocalDate.now(), LocalDate.now(), "trainerName", 1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(traineeRepository, times(1)).findByUser_Username(anyString());
        verify(trainingRepository, times(1)).findTrainingsForTrainee(anyLong(), any(LocalDate.class), any(LocalDate.class), anyString(), anyLong());
        verify(trainingMapper, times(1)).toTrainingResponseDto(any(Training.class));
    }*/

    @Test
    void testGetTraineeTrainings_Failure() {
        when(traineeRepository.findByUser_Username(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                traineeService.getTraineeTrainings("nonexistentUsername", LocalDate.now(), LocalDate.now(), "trainerName", 1L));
        verify(traineeRepository, times(1)).findByUser_Username(anyString());
        verify(trainingRepository, times(0)).findTrainingsForTrainee(anyLong(), any(LocalDate.class), any(LocalDate.class), anyString(), anyLong());
        verify(trainingMapper, times(0)).toTrainingResponseDto(any(Training.class));
    }

    /*@Test
    void testActivateDeactivateTrainee_Success() throws NotFoundException {
        Trainee trainee = new Trainee();
        when(traineeRepository.findByUser_Username(anyString())).thenReturn(Optional.of(trainee));
        when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);

        assertDoesNotThrow(() -> traineeService.activateDeactivateTrainee("testUsername", true));

        verify(traineeRepository, times(1)).findByUser_Username(anyString());
        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }*/

    @Test
    void testActivateDeactivateTrainee_Failure() {
        when(traineeRepository.findByUser_Username(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> traineeService.activateDeactivateTrainee("nonexistentUsername", true));
        verify(traineeRepository, times(1)).findByUser_Username(anyString());
        verify(traineeRepository, times(0)).save(any(Trainee.class));
    }
}
