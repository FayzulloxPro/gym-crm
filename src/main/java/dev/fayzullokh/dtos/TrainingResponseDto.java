package dev.fayzullokh.dtos;

import dev.fayzullokh.entity.TrainingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingResponseDto {

    @NotBlank(message = "Training name cannot be null or blank")
    private String trainingName;

    @NotNull(message = "Training date cannot be null")
    private Date trainingDate;

    @NotNull(message = "Trainee username cannot be null")
    private TrainingType trainingType;

    @Size(min = 1, message = "Training duration cannot be negative or zero")
    private int trainingDuration;

    @NotBlank(message = "Trainee name cannot be blank")
    private String trainerName;
}
