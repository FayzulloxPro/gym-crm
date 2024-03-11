package dev.fayzullokh.service;

import dev.fayzullokh.dtos.TraineeUpdateDTO;
import dev.fayzullokh.dtos.TrainingResponseDto;
import dev.fayzullokh.entity.Trainee;
import dev.fayzullokh.entity.Trainer;
import dev.fayzullokh.entity.Training;
import dev.fayzullokh.entity.User;
import dev.fayzullokh.exceptions.DuplicateUsernameException;
import dev.fayzullokh.exceptions.NotFoundException;
import dev.fayzullokh.exceptions.UnknownException;
import dev.fayzullokh.mappers.TrainingMapper;
import dev.fayzullokh.repositories.TraineeRepository;
import dev.fayzullokh.repositories.TrainingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {

    private final TraineeRepository traineeRepository;
    private final UserServiceImpl userService;
    private final TrainerServiceImpl trainerService;


    public Trainee createTrainee(Trainee trainee) throws UnknownException, DuplicateUsernameException {
        log.debug("Trainee: createTrainee({})", trainee);
        User user = trainee.getUser();
        User savedUser = userService.createUser(user);
        trainee.setUser(savedUser);
        return traineeRepository.save(trainee);
    }


    public Trainee getTraineeById(Long id) throws NotFoundException {
        log.debug("TraineeServiceImpl: getTraineeById({})", id);
        return traineeRepository.findById(id).orElseThrow(()-> new NotFoundException("Trainee not found by id: " + id));
    }

    public List<Trainee> findAllTrainees() {
        log.debug("TraineeServiceImpl: findAllTrainees()");
        return traineeRepository.findAll();
    }

    public Trainee updateTrainee(TraineeUpdateDTO trainee, Long id) throws NotFoundException {
        log.debug("TraineeServiceImpl: updateTrainee({}), id {}", trainee, id);
        Trainee traineeToUpdate = getTraineeById(id);
        User user = traineeToUpdate.getUser();
        user.setUsername(trainee.getUsername());
        user.setFirstName(trainee.getFirstname());
        user.setLastName(trainee.getLastname());
        user.setActive(trainee.isActive());
        traineeToUpdate.setDateOfBirth(trainee.getDateOfBirth());
        traineeToUpdate.setAddress(trainee.getAddress());

        return traineeRepository.save(traineeToUpdate);
    }

    public void deleteTrainee(Long id) throws NotFoundException {
        log.debug("TraineeServiceImpl: deleteTrainee({})", id);
        traineeRepository.delete(getTraineeById(id));
    }

    @Override
    public Trainee getTraineeByUsername(String username) throws NotFoundException {
        return traineeRepository.findByUser_Username(username).orElseThrow(() -> new NotFoundException("Trainee not found by username: " + username));
    }

    @Override
    public void deleteTraineeByUsername(String username) throws NotFoundException {
        Trainee trainee = traineeRepository.findByUser_Username(username).orElseThrow(() -> new NotFoundException("Trainee not found by username: " + username));
        traineeRepository.delete(trainee);
    }

    @Override
    public List<Trainer> updateTrainers(String username, List<String> trainerUsernames) throws NotFoundException {
        List<Trainer> trainers = trainerService.getTrainersByUsernames(trainerUsernames);
        Trainee byUserUsername = traineeRepository.findByUser_Username(username).orElseThrow(() -> new NotFoundException("Trainee not found by username: " + username));
        byUserUsername.setTrainers(trainers);
        traineeRepository.save(byUserUsername);
        return trainers;
    }

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private TrainingMapper trainingMapper; // You need to create TrainingMapper

    @Override
    public List<TrainingResponseDto> getTraineeTrainings(String username, LocalDate periodFrom, LocalDate periodTo, String trainerName, Long trainingTypeId) throws NotFoundException {
        Trainee trainee = traineeRepository.findByUser_Username(username)
                .orElseThrow(() -> new NotFoundException("Trainee not found with username: " + username));

        List<Training> trainings = trainingRepository.findTrainingsForTrainee(trainee.getId(), periodFrom, periodTo, trainerName, trainingTypeId);

        return trainings.stream()
                .map(trainingMapper::toTrainingResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void activateDeactivateTrainee(String username, Boolean isActive) throws NotFoundException {
        Trainee trainee = getTraineeByUsername(username);
        trainee.getUser().setActive(isActive);
        traineeRepository.save(trainee);
    }
}
