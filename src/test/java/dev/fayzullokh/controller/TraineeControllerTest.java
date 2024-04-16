package dev.fayzullokh.controller;

import dev.fayzullokh.dtos.*;
import dev.fayzullokh.entity.Trainee;
import dev.fayzullokh.entity.Trainer;
import dev.fayzullokh.entity.User;
import dev.fayzullokh.exceptions.DuplicateUsernameException;
import dev.fayzullokh.exceptions.NotFoundException;
import dev.fayzullokh.exceptions.UnknownException;
import dev.fayzullokh.mappers.TraineeMapper;
import dev.fayzullokh.mappers.TrainerMapper;
import dev.fayzullokh.mappers.UserMapper;
import dev.fayzullokh.service.TraineeService;
import dev.fayzullokh.service.TrainerServiceImpl;
import dev.fayzullokh.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TraineeControllerTest {

    @Mock
    private TraineeService traineeService;

    @Mock
    private TraineeMapper traineeMapper;

    @Mock
    private UserServiceImpl userService;
    @Mock
    private UserMapper userMapper;

    @Mock
    private TrainerServiceImpl trainerService;

    @Mock
    private TrainerMapper trainerMapper;

    @InjectMocks
    private TraineeController traineeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createTrainee_ValidTraineeRegistrationDto_CreatedSuccessfully() throws UnknownException, DuplicateUsernameException {
        TraineeRegistrationDto traineeRegistrationDto = new TraineeRegistrationDto("John", "Doe", "password", new Date(), "address");

        when(userMapper.toUser(traineeRegistrationDto)).thenReturn(new User());
        when(traineeMapper.toTraineeResponseDto(any(Trainee.class))).thenReturn(new TraineeResponseDto());
        when(traineeService.createTrainee(any(Trainee.class))).thenReturn(new Trainee());

        ResponseEntity<CreatedResponseDTO> responseEntity = traineeController.createTrainee(traineeRegistrationDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }

    /*@Test
    void createTrainee_InvalidTraineeRegistrationDto_ReturnsBadRequest() {
        TraineeRegistrationDto traineeRegistrationDto = new TraineeRegistrationDto(*//* set invalid values *//*);
        when(userMapper.toUser(traineeRegistrationDto.getFirstName(), traineeRegistrationDto.getLastName())).thenReturn(new User());
        ResponseEntity<CreatedResponseDTO> responseEntity = traineeController.createTrainee(traineeRegistrationDto);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }*/

    @Test
    void resetPassword_ValidChangePasswordRequest_PasswordResetSuccessfully() throws NotFoundException {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(/* set valid values */);
        when(userService.changePassword(changePasswordRequest)).thenReturn("New password set successfully");

        ResponseEntity<String> responseEntity = traineeController.resetPassword(changePasswordRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("New password set successfully", responseEntity.getBody());
    }

    @Test
    void resetPassword_InvalidChangePasswordRequest_ThrowsNotFoundException() throws NotFoundException {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(/* set invalid values */);
        when(userService.changePassword(changePasswordRequest)).thenThrow(new NotFoundException("Error message"));

        assertThrows(NotFoundException.class, () -> traineeController.resetPassword(changePasswordRequest));
    }

    @Test
    void selectTrainer_ValidUsername_TraineeFetchedSuccessfully() throws NotFoundException {
        String username = "testUsername";
        Trainee trainee = new Trainee(/* set valid values */);
        TraineeResponseDto expectedResponse = new TraineeResponseDto(/* set expected values */);

        when(traineeService.getTraineeByUsername(username)).thenReturn(trainee);
        when(traineeMapper.toTraineeResponseDto(trainee)).thenReturn(expectedResponse);

        ResponseEntity<TraineeResponseDto> responseEntity = traineeController.selectTrainer(username);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void selectTrainer_InvalidUsername_ThrowsNotFoundException() throws NotFoundException {
        String username = "nonExistingUsername";
        when(traineeService.getTraineeByUsername(username)).thenThrow(new NotFoundException("Error message"));

        assertThrows(NotFoundException.class, () -> traineeController.selectTrainer(username));
    }

    @Test
    void updateTrainee_ValidTraineeUpdateDTOAndId_TraineeUpdatedSuccessfully() throws NotFoundException {
        Long id = 1L;
        TraineeUpdateDTO traineeUpdateDTO = new TraineeUpdateDTO(/* set valid values */);
        Trainee updatedTrainee = new Trainee(/* set updated values */);
        TraineeResponseDto expectedResponse = new TraineeResponseDto(/* set expected values */);

        when(traineeService.updateTrainee(traineeUpdateDTO, id)).thenReturn(updatedTrainee);
        when(traineeMapper.toTraineeResponseDto(updatedTrainee)).thenReturn(expectedResponse);

        ResponseEntity<TraineeResponseDto> responseEntity = traineeController.updateTrainee(traineeUpdateDTO, id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void updateTrainee_InvalidTraineeUpdateDTOOrId_ThrowsNotFoundException() throws NotFoundException {
        Long id = 1L;
        TraineeUpdateDTO traineeUpdateDTO = new TraineeUpdateDTO();
        when(traineeService.updateTrainee(traineeUpdateDTO, id)).thenThrow(new NotFoundException("Error message"));

        assertThrows(NotFoundException.class, () -> traineeController.updateTrainee(traineeUpdateDTO, id));
    }

    @Test
    void deleteTrainee_ValidUsername_TraineeDeletedSuccessfully() throws NotFoundException {
        String username = "testUsername";

        ResponseEntity<Void> responseEntity = traineeController.deleteTrainee(username);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(traineeService, times(1)).deleteTraineeByUsername(username);
    }

    @Test
    void deleteTrainee_InvalidUsername_ThrowsNotFoundException() throws NotFoundException {
        String username = "nonExistingUsername";
        doThrow(new NotFoundException("Error message")).when(traineeService).deleteTraineeByUsername(username);

        assertThrows(NotFoundException.class, () -> traineeController.deleteTrainee(username));
    }

    @Test
    void getUnassignedTrainers_ValidUsername_UnassignedTrainersFetchedSuccessfully() throws NotFoundException {
        String username = "testUsername";
        Trainee trainee = new Trainee();
        Trainer trainer = new Trainer();
        List<Trainer> unassignedTrainers = Collections.singletonList(trainer);
        List<TrainerResponseForNotAssigned> expectedResponse = Collections.singletonList(new TrainerResponseForNotAssigned(/* set expected values */));

        when(traineeService.getTraineeByUsername(username)).thenReturn(trainee);
        when(trainerService.getUnassignedTrainers(trainee)).thenReturn(unassignedTrainers);
        when(trainerMapper.toTrainerResponseDtoForNotAssigned(unassignedTrainers)).thenReturn(expectedResponse);

        ResponseEntity<List<TrainerResponseForNotAssigned>> responseEntity = traineeController.getUnassignedTrainers(username);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void getUnassignedTrainers_InvalidUsername_ThrowsNotFoundException() throws NotFoundException {
        String username = "nonExistingUsername";
        when(traineeService.getTraineeByUsername(username)).thenThrow(new NotFoundException("Error message"));

        assertThrows(NotFoundException.class, () -> traineeController.getUnassignedTrainers(username));
    }

    @Test
    void updateTrainers_ValidTrainerUsernamesAndUsername_TrainersUpdatedSuccessfully() throws NotFoundException {
        String username = "testUsername";
        List<String> trainerUsernames = Collections.singletonList("trainerUsername");
        Trainer trainer = new Trainer();
        List<Trainer> updatedTrainers = Collections.singletonList(trainer);
        List<TrainerResponseForNotAssigned> expectedResponse = Collections.singletonList(new TrainerResponseForNotAssigned(/* set expected values */));

        when(traineeService.updateTrainers(username, trainerUsernames)).thenReturn(updatedTrainers);
        when(traineeMapper.toTraineeResponseForNotAssigned(updatedTrainers)).thenReturn(expectedResponse);

        ResponseEntity<List<TrainerResponseForNotAssigned>> responseEntity = traineeController.updateTrainers(trainerUsernames, username);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void updateTrainers_InvalidTrainerUsernamesOrUsername_ThrowsNotFoundException() throws NotFoundException {
        String username = "nonExistingUsername";
        List<String> trainerUsernames = Collections.singletonList("trainerUsername");
        when(traineeService.updateTrainers(username, trainerUsernames)).thenThrow(new NotFoundException("Error message"));

        assertThrows(NotFoundException.class, () -> traineeController.updateTrainers(trainerUsernames, username));
    }

    @Test
    void getTraineeTrainings_ValidUsernameAndOptionalParameters_TrainingsFetchedSuccessfully() throws NotFoundException {
        String username = "testUsername";
        LocalDate periodFrom = LocalDate.now().minusDays(7);
        LocalDate periodTo = LocalDate.now();
        String trainerName = "trainerName";
        Long trainingTypeId = 1L;
        List<TrainingResponseDto> expectedResponse = Collections.singletonList(new TrainingResponseDto(/* set expected values */));

        when(traineeService.getTraineeTrainings(username, periodFrom, periodTo, trainerName, trainingTypeId)).thenReturn(expectedResponse);

        ResponseEntity<List<TrainingResponseDto>> responseEntity = traineeController.getTraineeTrainings(username, periodFrom, periodTo, trainerName, trainingTypeId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void activateDeactivateTrainee_ValidUsernameAndIsActive_TraineeActivatedOrDeactivatedSuccessfully() throws NotFoundException {
        String username = "testUsername";
        Boolean isActive = true;

        ResponseEntity<Void> responseEntity = traineeController.activateDeactivateTrainee(username, isActive);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(traineeService, times(1)).activateDeactivateTrainee(username, isActive);
    }

    @Test
    void activateDeactivateTrainee_InvalidUsername_ThrowsNotFoundException() throws NotFoundException {
        String username = "nonExistingUsername";
        Boolean isActive = true;
        doThrow(new NotFoundException("Error message")).when(traineeService).activateDeactivateTrainee(username, isActive);

        assertThrows(NotFoundException.class, () -> traineeController.activateDeactivateTrainee(username, isActive));

    }
}
