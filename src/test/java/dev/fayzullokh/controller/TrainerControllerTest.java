package dev.fayzullokh.controller;

import dev.fayzullokh.dtos.*;
import dev.fayzullokh.entity.Trainer;
import dev.fayzullokh.entity.TrainingType;
import dev.fayzullokh.entity.User;
import dev.fayzullokh.exceptions.NotFoundException;
import dev.fayzullokh.mappers.TrainerMapper;
import dev.fayzullokh.mappers.UserMapper;
import dev.fayzullokh.service.TrainerService;
import dev.fayzullokh.service.TrainingTypeService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class TrainerControllerTest {

    @Mock
    private TrainerService trainerService;

    @Mock
    private TrainingTypeService trainingTypeService;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private TrainerMapper trainerMapper;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private TrainerController trainerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createTrainer_ValidTrainerRegistrationDto_CreatedSuccessfully() throws NotFoundException {
        TrainerRegistrationDto trainerRegistrationDto = new TrainerRegistrationDto("John", "Doe", 1L);

        when(userService.getUserById(anyLong())).thenReturn(new User());
        when(trainingTypeService.findById(anyLong())).thenReturn(new TrainingType());
        when(trainerService.createTrainer(any(Trainer.class))).thenReturn(new Trainer());
        when(trainerMapper.toCreatedResponseDTO(any(Trainer.class))).thenReturn(new CreatedResponseDTO());

        ResponseEntity<CreatedResponseDTO> responseEntity = trainerController.createTrainer(trainerRegistrationDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }


    @Test
    void resetPassword_ValidChangePasswordRequest_PasswordResetSuccessfully() throws NotFoundException {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("username", "oldPassword", "newPassword");

        when(userService.changePassword(any(ChangePasswordRequest.class))).thenReturn("Password reset successfully");

        ResponseEntity<String> responseEntity = trainerController.resetPassword(changePasswordRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Password reset successfully", responseEntity.getBody());
    }

    @Test
    void resetPassword_InvalidChangePasswordRequest_ThrowsNotFoundException() throws NotFoundException {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("username", "oldPassword", "newPassword");

        when(userService.changePassword(any(ChangePasswordRequest.class))).thenThrow(new NotFoundException("User not found"));

        assertThrows(NotFoundException.class, () -> trainerController.resetPassword(changePasswordRequest));
    }

    @Test
    void selectTrainer_ValidUsername_TrainerFetchedSuccessfully() throws NotFoundException {
        String username = "john_doe";

        when(trainerService.getTrainerByUsername(anyString())).thenReturn(new Trainer());
        when(trainerMapper.toTrainerResponseDto(any(Trainer.class))).thenReturn(new TrainerResponseDto());

        ResponseEntity<TrainerResponseDto> responseEntity = trainerController.selectTrainer(username);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void selectTrainer_InvalidUsername_ThrowsNotFoundException() throws NotFoundException {
        String username = "john_doe";

        when(trainerService.getTrainerByUsername(anyString())).thenThrow(new NotFoundException("Trainer not found"));

        assertThrows(NotFoundException.class, () -> trainerController.selectTrainer(username));
    }

    @Test
    void updateTrainer_ValidIdAndTrainer_TrainerUpdatedSuccessfully() throws NotFoundException {
        Long id = 1L;
        TrainerUpdateDto trainerUpdateDto = new TrainerUpdateDto();

        when(trainerService.updateTrainer(any(TrainerUpdateDto.class), anyLong())).thenReturn(new Trainer());
        when(trainerMapper.toTrainerResponseDto(any(Trainer.class))).thenReturn(new TrainerResponseDto());

        ResponseEntity<TrainerResponseDto> responseEntity = trainerController.updateTrainer(id, trainerUpdateDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void updateTrainer_InvalidId_ThrowsNotFoundException() throws NotFoundException {
        Long id = 1L;
        TrainerUpdateDto trainerUpdateDto = new TrainerUpdateDto();

        when(trainerService.updateTrainer(any(TrainerUpdateDto.class), anyLong())).thenThrow(new NotFoundException("Trainer not found"));

        assertThrows(NotFoundException.class, () -> trainerController.updateTrainer(id, trainerUpdateDto));
    }

    @Test
    void getTrainerTrainings_ValidUsernameAndDatesAndTraineeName_TrainingsFetchedSuccessfully() throws NotFoundException {
        String username = "john_doe";
        LocalDate periodFrom = LocalDate.now().minusDays(7);
        LocalDate periodTo = LocalDate.now();
        String traineeName = "trainee";

        when(trainerService.getTrainerTrainings(anyString(), any(LocalDate.class), any(LocalDate.class), anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<List<TrainingResponseDto>> responseEntity = trainerController.getTrainerTrainings(username, periodFrom, periodTo, traineeName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getTrainerTrainings_InvalidUsername_ThrowsNotFoundException() throws NotFoundException {
        String username = "john_doe";
        LocalDate periodFrom = LocalDate.now().minusDays(7);
        LocalDate periodTo = LocalDate.now();
        String traineeName = "trainee";

        when(trainerService.getTrainerTrainings(anyString(), any(LocalDate.class), any(LocalDate.class), anyString())).thenThrow(new NotFoundException("Trainer not found"));

        assertThrows(NotFoundException.class, () -> trainerController.getTrainerTrainings(username, periodFrom, periodTo, traineeName));
    }

    @Test
    void activateDeactivateTrainee_ValidUsernameAndIsActive_TraineeActivatedOrDeactivatedSuccessfully() throws NotFoundException {
        String username = "john_doe";
        boolean isActive = true;

        ResponseEntity<Void> responseEntity = trainerController.activateDeactivateTrainee(username, isActive);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void activateDeactivateTrainee_InvalidUsername_ThrowsNotFoundException() throws NotFoundException {
        String username = "john_doe";
        boolean isActive = true;
        doThrow(new NotFoundException("Trainer not found")).when(trainerService).activateDeactivateTrainee(anyString(), anyBoolean());
        assertThrows(NotFoundException.class, () -> trainerController.activateDeactivateTrainee(username, isActive));
    }

}
