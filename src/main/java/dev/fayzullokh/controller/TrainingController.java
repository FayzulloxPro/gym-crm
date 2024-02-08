package dev.fayzullokh.controller;

import dev.fayzullokh.dtos.TrainingRequestDto;
import dev.fayzullokh.entity.TrainingType;
import dev.fayzullokh.exceptions.NotFoundException;
import dev.fayzullokh.service.TrainingService;
import dev.fayzullokh.service.TrainingTypeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainings")
@RequiredArgsConstructor
@Slf4j
public class TrainingController {


    private final TrainingService trainingTypeService;

    @Operation(summary = "This API is used for adding a new training type",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Training type added"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            }
    )
    @PostMapping
    public ResponseEntity<Void> addTraining(@RequestBody @Valid TrainingRequestDto trainingRequestDto) throws NotFoundException {
        log.info("Received request to add training with DTO: {}", trainingRequestDto);
        trainingTypeService.addTraining(trainingRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "This API is used for getting all training types",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Training types retrieved"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            }
    )
    @GetMapping("/trainings")
    public ResponseEntity<List<TrainingType>> getTrainingTypes() {
        List<TrainingType> trainingTypes = trainingTypeService.getAllTrainingTypes();
        return new ResponseEntity<>(trainingTypes, HttpStatus.OK);
    }
}