package dev.fayzullokh.mappers;

import dev.fayzullokh.dtos.*;
import dev.fayzullokh.entity.Trainee;
import dev.fayzullokh.entity.Trainer;
import dev.fayzullokh.entity.User;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TraineeMapper {

    public Trainee toTrainee(@Valid TraineeRegistrationDto dto, User user) {
        Trainee trainee = new Trainee();
        trainee.setDateOfBirth(dto.getDateOfBirth());
        trainee.setUser(user);
        trainee.setAddress(dto.getAddress());
        return trainee;
    }

    public TraineeResponseDto toTraineeResponseDto(Trainee trainee) {
        User user = trainee.getUser();
        List<Trainer> trainers = trainee.getTrainers();
        List<TrainerDTOForTrainee> trainerDTOForTrainees = new ArrayList<>();
        trainers.forEach(trainer -> {
            User trainerUser = trainer.getUser();
            trainerDTOForTrainees.add(TrainerDTOForTrainee.builder()
                            .trainerFirstName(trainerUser.getFirstName())
                            .trainerLastName(trainerUser.getLastName())
                            .trainerSpecialization(trainer.getTrainingType())
                            .trainerUsername(trainerUser.getUsername())
                            .build());
        });
        return TraineeResponseDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isActive(user.isActive())
                .address(trainee.getAddress())
                .dateOfBirth(trainee.getDateOfBirth())
                .trainers(trainerDTOForTrainees)
                .build();
    }

    public TraineeUpdateResponseDto toTraineeUpdateResponseDto(Trainee trainee) {
        User user = trainee.getUser();
        return TraineeUpdateResponseDto.builder()
                .traineeId(trainee.getId())
                .address(trainee.getAddress())
                .dateOfBirth(trainee.getDateOfBirth())
                .lastname(user.getLastName())
                .firstName(user.getFirstName())
                .build();
    }

    public CreatedResponseDTO toCreatedResponseDTO(@Nonnull Trainee trainee) {
        User user = trainee.getUser();
        return CreatedResponseDTO.builder()
                .username(user.getUsername())
                .build();
    }

    public List<TrainerResponseForNotAssigned> toTraineeResponseForNotAssigned(List<Trainer> updatedTrainers) {
        List<TrainerResponseForNotAssigned> trainerResponseForNotAssigned = new ArrayList<>();
        updatedTrainers.forEach(trainer -> {
            User user = trainer.getUser();
            trainerResponseForNotAssigned.add(TrainerResponseForNotAssigned.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .trainingType(trainer.getTrainingType())
                    .username(user.getUsername())
                    .build());
        });
        return trainerResponseForNotAssigned;
    }
}
