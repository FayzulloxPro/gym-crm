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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/trainees")
public class TraineeController {

    @Autowired
    private TraineeService traineeService;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TraineeMapper traineeMapper;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private TrainerServiceImpl trainerService;
    @Autowired
    private TrainerMapper trainerMapper;


    @Operation(summary = "This API is used to create a new trainee",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Trainee created", content = @Content(schema = @Schema(implementation = CreatedResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = AppErrorDTO.class)))
            }
    )
    @PostMapping
    public ResponseEntity<CreatedResponseDTO> createTrainee(@RequestBody @Valid TraineeRegistrationDto dto) throws UnknownException, DuplicateUsernameException {
        log.info("Received request to create trainee with DTO: {}", dto);
        User user = userMapper.toUser(dto);
        Trainee trainee = traineeMapper.toTrainee(dto, user);
        Trainee traineeServiceTrainee = traineeService.createTrainee(trainee);
        CreatedResponseDTO createdTrainee = traineeMapper.toCreatedResponseDTO(traineeServiceTrainee);
        log.info("Trainee created successfully: {}", createdTrainee);
        return new ResponseEntity<>(createdTrainee, HttpStatus.CREATED);
    }

    @Operation(summary = "This API is used to reset password for trainee",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Password reset", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = AppErrorDTO.class)))
            }
    )
    @PutMapping("/password/reset")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ChangePasswordRequest request) throws NotFoundException {
        log.info("Received request to reset password for trainer with: {}", request);
        String response = userService.changePassword(request);
        log.info("Password reset successful for trainer with: {}", request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "This API is to get a trainee by username",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Trainee fetched", content = @Content(schema = @Schema(implementation = TraineeResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = AppErrorDTO.class)))
            }
    )
    @GetMapping("/{username}")
    public ResponseEntity<TraineeResponseDto> selectTrainer(@PathVariable @NotBlank String username) throws NotFoundException {
        log.info("Received request to fetch trainee by username: {}", username);
        Trainee trainee = traineeService.getTraineeByUsername(username);
        TraineeResponseDto traineeResponseDto = traineeMapper.toTraineeResponseDto(trainee);
        log.info("Fetched trainee successfully: {}", traineeResponseDto);
        return new ResponseEntity<>(traineeResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "This API is used to update a trainee",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Trainee updated", content = @Content(schema = @Schema(implementation = TraineeResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = AppErrorDTO.class)))
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<TraineeResponseDto> updateTrainee(@RequestBody @Valid TraineeUpdateDTO updateDTO, @PathVariable Long id) throws NotFoundException {
        log.info("Received request to update trainee with DTO: {}", updateDTO);
        Trainee updatedTrainee = traineeService.updateTrainee(updateDTO, id);
        TraineeResponseDto traineeResponseDto = traineeMapper.toTraineeResponseDto(updatedTrainee);
        log.info("Trainee updated successfully: {}", traineeResponseDto);
        return new ResponseEntity<>(traineeResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "This API is used to delete a trainee",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Trainee deleted", content = @Content(schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = AppErrorDTO.class)))
            }
    )
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteTrainee(@PathVariable @NotBlank String username) throws NotFoundException {
        log.info("Received request to delete trainee by username: {}", username);
        traineeService.deleteTraineeByUsername(username);
        log.info("Trainee deleted successfully for username: {}", username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "This API is used to get all trainees",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Trainees fetched", content = @Content(schema = @Schema(implementation = TraineeResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = AppErrorDTO.class)))
            }
    )
    @GetMapping("/{username}/trainers/unassigned")
    public ResponseEntity<List<TrainerResponseForNotAssigned>> getUnassignedTrainers(@PathVariable @NotBlank String username) throws NotFoundException {
        log.info("Received request to get unassigned trainers for trainee with username: {}", username);

        Trainee trainee = traineeService.getTraineeByUsername(username);

        List<Trainer> unassignedTrainers = trainerService.getUnassignedTrainers(trainee);
        List<TrainerResponseForNotAssigned> trainerResponseDtos = trainerMapper.toTrainerResponseDtoForNotAssigned(unassignedTrainers);
        log.info("Fetched {} unassigned trainers for trainee with username: {}", trainerResponseDtos.size(), username);
        return new ResponseEntity<>(trainerResponseDtos, HttpStatus.OK);
    }

    @Operation(summary = "This API is used to update trainers for trainee",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Trainers updated", content = @Content(schema = @Schema(implementation = TrainerResponseForNotAssigned.class))),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = AppErrorDTO.class)))
            }
    )
    @PutMapping("/{username}/trainers")
    public ResponseEntity<List<TrainerResponseForNotAssigned>> updateTrainers(@RequestBody List<String> trainerUsernames, @PathVariable @NotBlank String username) throws NotFoundException {
        log.info("Received request to update trainers for trainee with username: {}", username);
        List<Trainer> updatedTrainers = traineeService.updateTrainers(username, trainerUsernames);
        List<TrainerResponseForNotAssigned> trainerResponseForNotAssignedList = traineeMapper.toTraineeResponseForNotAssigned(updatedTrainers);
        log.info("Updated trainers successfully for trainee with username: {}", username);
        return new ResponseEntity<>(trainerResponseForNotAssignedList, HttpStatus.OK);
    }

    @Operation(summary = "This API is used to get all trainings for trainee",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Trainings fetched", content = @Content(schema = @Schema(implementation = TrainingResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = AppErrorDTO.class)))
            }
    )
    @GetMapping("/{username}/trainings")
    public ResponseEntity<List<TrainingResponseDto>> getTraineeTrainings(
            @PathVariable @NotBlank String username,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodTo,
            @RequestParam(required = false) String trainerName,
            @RequestParam(required = false) Long trainingTypeId) throws NotFoundException {

        List<TrainingResponseDto> trainings = traineeService.getTraineeTrainings(username, periodFrom, periodTo, trainerName, trainingTypeId);

        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @Operation(summary = "This API is used to activate/deactivate a trainee",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Trainee activated/deactivated", content = @Content(schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = AppErrorDTO.class)))
            }
    )
    @PatchMapping("/{username}")
    public ResponseEntity<Void> activateDeactivateTrainee(
            @PathVariable @NotBlank String username,
            @RequestParam @NotNull Boolean isActive) throws NotFoundException {
        traineeService.activateDeactivateTrainee(username, isActive);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
