package dev.fayzullokh.mappers;

import dev.fayzullokh.dtos.*;
import dev.fayzullokh.entity.Trainee;
import dev.fayzullokh.entity.Trainer;
import dev.fayzullokh.entity.TrainingType;
import dev.fayzullokh.entity.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrainerMapper {

    public Trainer toTrainer(@Valid TrainerRegistrationDto dto, User user, TrainingType trainingType) {
        Trainer trainer = new Trainer();
        trainer.setTrainingType(trainingType);
        trainer.setUser(user);
        return trainer;
    }

    public TrainerResponseDto toTrainerResponseDto(Trainer trainer) {
        User user = trainer.getUser();
        List<Trainee> trainees = trainer.getTrainees();
        List<TraineeDTOForTrainer> traineeDTOForTrainers = new ArrayList<>();
        trainees.forEach(trainee -> {
            User traineeUser = trainee.getUser();
            traineeDTOForTrainers.add(TraineeDTOForTrainer.builder()
                    .firstName(traineeUser.getFirstName())
                    .lastName(traineeUser.getLastName())
                    .username(traineeUser.getUsername())
                    .build());
        });
        return TrainerResponseDto.builder()
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .trainingType(trainer.getTrainingType())
                .isActive(user.isActive())
                .trainees(traineeDTOForTrainers)
                .build();
    }

    public CreatedResponseDTO toCreatedResponseDTO(Trainer createdTrainer) {
        User user = createdTrainer.getUser();
        return CreatedResponseDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    public List<TrainerResponseForNotAssigned> toTrainerResponseDtoForNotAssigned(List<Trainer> trainers) {
        List<TrainerResponseForNotAssigned> trainerResponseForNotAssigneds = new ArrayList<>();
        trainers.forEach(trainer -> {
            User user = trainer.getUser();
            trainerResponseForNotAssigneds.add(TrainerResponseForNotAssigned.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .username(user.getUsername())
                    .trainingType(trainer.getTrainingType())
                    .build());
        });
        return trainerResponseForNotAssigneds;
    }
}