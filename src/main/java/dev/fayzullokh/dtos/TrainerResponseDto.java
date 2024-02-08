package dev.fayzullokh.dtos;

import dev.fayzullokh.entity.Trainee;
import dev.fayzullokh.entity.TrainingType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainerResponseDto {
    private String firstName;
    private String lastName;

    private TrainingType trainingType;

    private boolean isActive;

    private List<TraineeDTOForTrainer> trainees;

}
