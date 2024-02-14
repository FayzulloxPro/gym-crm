package dev.fayzullokh.controller;

import dev.fayzullokh.dtos.*;
import dev.fayzullokh.entity.Trainee;
import dev.fayzullokh.entity.Trainer;
import dev.fayzullokh.entity.TrainingType;
import dev.fayzullokh.entity.User;
import dev.fayzullokh.exceptions.NotFoundException;
import dev.fayzullokh.mappers.TrainerMapper;
import dev.fayzullokh.mappers.UserMapper;
import dev.fayzullokh.service.TrainerService;
import dev.fayzullokh.service.TrainingTypeService;
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

@Slf4j
@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TrainerMapper trainerMapper;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private TrainingTypeService trainingTypeService;


    @Operation(summary = "This API is used for registering a new trainer",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Trainer registered", content = @Content(schema = @Schema(implementation = CreatedResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = AppErrorDTO.class)))
            }
    )
    @PostMapping
    public ResponseEntity<CreatedResponseDTO> createTrainer(@RequestBody @Valid TrainerRegistrationDto dto) throws NotFoundException {
        log.info("Received request to register trainer with DTO: {}", dto);
        User user = userMapper.toUser(dto);
        TrainingType trainingType = trainingTypeService.findById(dto.getTrainingTypeId());
        Trainer trainer=new Trainer();
        trainer.setUser(user);
        trainer.setTrainingType(trainingType);
        Trainer createdTrainer = trainerService.createTrainer(trainer);
        CreatedResponseDTO responseDTO = trainerMapper.toCreatedResponseDTO(createdTrainer);
        log.info("Trainer registration successful for DTO: {}", dto);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "This API is used for resetting the password of a trainer",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Password reset", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = AppErrorDTO.class)))
            }
    )
    @PutMapping("/password/reset")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ChangePasswordRequest request) throws NotFoundException {
        log.info("Received request to reset password for trainer with: {}", request);
        String response = userService.changePassword(request);
        log.info("Password reset successful for trainer with: {}", request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "This API is used for fetching a trainer by username",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Trainer fetched", content = @Content(schema = @Schema(implementation = TrainerResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Trainer not found", content = @Content(schema = @Schema(implementation = AppErrorDTO.class)))
            }
    )
    @GetMapping("/{username}")
    public ResponseEntity<TrainerResponseDto> selectTrainer(@PathVariable @NotBlank String username) throws NotFoundException {
        log.info("Received request to fetch trainee by username: {}", username);
        Trainer trainer = trainerService.getTrainerByUsername(username);
        TrainerResponseDto trainerResponseDto = trainerMapper.toTrainerResponseDto(trainer);
        return new ResponseEntity<>(trainerResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "This API is used for updating a trainer",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Trainer updated", content = @Content(schema = @Schema(implementation = TrainerResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Trainer not found", content = @Content(schema = @Schema(implementation = AppErrorDTO.class)))
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<TrainerResponseDto> updateTrainer(@PathVariable Long id, @RequestBody TrainerUpdateDto trainer) throws NotFoundException {
        Trainer updatedTrainer = trainerService.updateTrainer(trainer, id);
        TrainerResponseDto trainerResponseDto = trainerMapper.toTrainerResponseDto(updatedTrainer);
        return new ResponseEntity<>(trainerResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "This API is used for fetching all trainers relateo to a training type",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Trainers fetched", content = @Content(schema = @Schema(implementation = TrainerResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Trainers not found", content = @Content(schema = @Schema(implementation = AppErrorDTO.class)))
            }
    )
    @GetMapping("/{username}/trainings")
    public ResponseEntity<List<TrainingResponseDto>> getTrainerTrainings(
            @PathVariable @NotBlank String username,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodTo,
            @RequestParam(required = false) String traineeName) throws NotFoundException {

        List<TrainingResponseDto> trainings = trainerService.getTrainerTrainings(username, periodFrom, periodTo, traineeName);

        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @Operation(summary = "This API is used for activating/deactivating a trainee",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Trainee activated/deactivated", content = @Content(schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content(schema = @Schema(implementation = AppErrorDTO.class)))
            }
    )
    @PatchMapping("/{username}")
    public ResponseEntity<Void> activateDeactivateTrainee(
            @PathVariable @NotBlank String username,
            @RequestParam @NotNull Boolean isActive) throws NotFoundException {
        trainerService.activateDeactivateTrainee(username, isActive);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
