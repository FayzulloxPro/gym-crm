package dev.fayzullokh.service;

import dev.fayzullokh.dtos.TraineeUpdateDTO;
import dev.fayzullokh.dtos.TrainerResponseDto;
import dev.fayzullokh.dtos.TrainingResponseDto;
import dev.fayzullokh.entity.Trainee;
import dev.fayzullokh.entity.Trainer;
import dev.fayzullokh.exceptions.DuplicateUsernameException;
import dev.fayzullokh.exceptions.NotFoundException;
import dev.fayzullokh.exceptions.UnknownException;

import java.time.LocalDate;
import java.util.List;

public interface TraineeService {
    Trainee createTrainee(Trainee trainee) throws UnknownException, DuplicateUsernameException;

    Trainee getTraineeById(Long id) throws NotFoundException;

    List<Trainee> findAllTrainees();

    Trainee updateTrainee(TraineeUpdateDTO trainee, Long id) throws NotFoundException;

    void deleteTrainee(Long id) throws NotFoundException;

    Trainee getTraineeByUsername(String username) throws NotFoundException;

    void deleteTraineeByUsername(String username) throws NotFoundException;

    List<Trainer> updateTrainers(String username, List<String> trainerUsernames) throws NotFoundException;

    List<TrainingResponseDto> getTraineeTrainings(String username, LocalDate periodFrom, LocalDate periodTo, String trainerName, Long trainingTypeId) throws NotFoundException;

    void activateDeactivateTrainee(String username, Boolean isActive) throws NotFoundException;
}
