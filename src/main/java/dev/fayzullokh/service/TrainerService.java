package dev.fayzullokh.service;

import dev.fayzullokh.dtos.TrainerUpdateDto;
import dev.fayzullokh.dtos.TrainingResponseDto;
import dev.fayzullokh.entity.Trainee;
import dev.fayzullokh.entity.Trainer;
import dev.fayzullokh.exceptions.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface TrainerService {
    Trainer createTrainer(Trainer trainer);

    Trainer getTrainerById(Long id) throws NotFoundException;

    List<Trainer> findAllTrainers();

    Trainer updateTrainer(TrainerUpdateDto dto, Long id) throws NotFoundException;

    Trainer getTrainerByUsername(String username) throws NotFoundException;

    List<Trainer> getUnassignedTrainers(Trainee trainee);

    List<Trainer> getTrainersByUsernames(List<String> trainerUsernames);

    List<TrainingResponseDto> getTrainerTrainings(String username, LocalDate periodFrom, LocalDate periodTo, String traineeName) throws NotFoundException;

    void activateDeactivateTrainee(String username, Boolean isActive) throws NotFoundException;


    // If you decide to uncomment the deleteTrainer method in the future:
    // void deleteTrainer(Long id);
}
