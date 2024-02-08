package dev.fayzullokh.dtos;


import dev.fayzullokh.entity.TrainingType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerDTOForTrainee {
    @NotBlank(message = "Trainer username is required")
    private String trainerUsername;
    @NotBlank(message = "Trainer first name is required")
    private String trainerFirstName;

    @NotBlank(message = "Trainer last name is required")
    private String trainerLastName;

    @NotBlank(message = "Trainer email is required")
    private TrainingType trainerSpecialization;
}
