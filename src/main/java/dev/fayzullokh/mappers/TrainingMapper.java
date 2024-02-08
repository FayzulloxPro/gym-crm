package dev.fayzullokh.mappers;

import dev.fayzullokh.dtos.TrainingResponseDto;
import dev.fayzullokh.entity.Training;
import org.springframework.stereotype.Component;


@Component
public class TrainingMapper {
    public TrainingResponseDto toTrainingResponseDto(Training training) {
        return TrainingResponseDto.builder()
                .trainingName(training.getName())
                .trainingDate(training.getDate())
                .trainingType(training.getTrainingType())
                .trainingDuration(training.getDuration())
                .trainerName(training.getTrainer().getUser().getFirstName())
                .build();
    }
}