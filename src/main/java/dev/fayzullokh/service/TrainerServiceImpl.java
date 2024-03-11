package dev.fayzullokh.service;

import dev.fayzullokh.dtos.TrainerUpdateDto;
import dev.fayzullokh.dtos.TrainingResponseDto;
import dev.fayzullokh.entity.*;
import dev.fayzullokh.exceptions.DuplicateUsernameException;
import dev.fayzullokh.exceptions.NotFoundException;
import dev.fayzullokh.exceptions.UnknownException;
import dev.fayzullokh.mappers.TrainingMapper;
import dev.fayzullokh.repositories.TrainerRepository;
import dev.fayzullokh.repositories.TrainingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainerServiceImpl implements TrainerService {
    @Autowired
    private TrainerRepository trainerRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerServiceImpl.class);
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private TrainingTypeServiceImpl trainingTypeService;
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private TrainingMapper trainingMapper;

    @Autowired
    public TrainerServiceImpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public Trainer createTrainer(Trainer trainer) throws UnknownException, DuplicateUsernameException {
        LOGGER.debug("TrainerServiceImpl: createTrainer({})", trainer);
        User user = trainer.getUser();
        user = userService.createUser(user);
        trainer.setUser(user);
        return trainerRepository.save(trainer);
    }

    public Trainer getTrainerById(Long id) throws NotFoundException {
        LOGGER.debug("TrainerServiceImpl: getTrainerById({})", id);
        return trainerRepository.findById(id).orElseThrow(() -> new NotFoundException("Trainer not found with id: " + id + ""));
    }

    public List<Trainer> findAllTrainers() {
        LOGGER.debug("TrainerServiceImpl: findAllTrainers()");
        return trainerRepository.findAll();
    }

    public Trainer updateTrainer(TrainerUpdateDto dto, Long id) throws NotFoundException {
        LOGGER.info("TrainerServiceImpl: updateTrainer({}) with id: {}", dto, id);
        Trainer trainerById = getTrainerById(id);
        TrainingType trainingType = trainingTypeService.findById(dto.getTrainingTypeId());
        trainerById.setTrainingType(trainingType);
        User user = trainerById.getUser();
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastname());
        user.setActive(dto.getIsActive());
        trainerById.setUser(user);
        LOGGER.info("TrainerServiceImpl: updateTrainer({}) with id: {}", dto, id);
        return trainerRepository.save(trainerById);
    }//

    @Override
    public Trainer getTrainerByUsername(String username) throws NotFoundException {
        LOGGER.debug("TrainerServiceImpl: getTrainerByUsername({})", username);
        Optional<Trainer> byUserUsername = trainerRepository.findByUser_Username(username);
        return byUserUsername.orElseThrow(() -> new NotFoundException("Trainer not found with username: " + username + ""));
    }

    @Override
    public List<Trainer> getUnassignedTrainers(Trainee trainee) {
        LOGGER.debug("TrainerServiceImpl: getUnassignedTrainers({})", trainee);
        return trainerRepository.findUnassignedTrainers(trainee.getTrainers());
    }//

    @Override
    public List<Trainer> getTrainersByUsernames(List<String> trainerUsernames) {
        trainerUsernames.forEach(username -> LOGGER.debug("TrainerServiceImpl: getTrainersByUsernames({})", username));
        return trainerRepository.findByUser_UsernameIn(trainerUsernames);
    }//

    @Override
    public List<TrainingResponseDto> getTrainerTrainings(String username, LocalDate periodFrom, LocalDate periodTo, String traineeName) throws NotFoundException {
        Trainer trainer = trainerRepository.findByUser_Username(username)
                .orElseThrow(() -> new NotFoundException("Trainer not found with username: " + username));

        List<Training> trainings = trainingRepository.findTrainingsForTrainer(trainer.getId(), periodFrom, periodTo, traineeName);

        return trainings.stream()
                .map(trainingMapper::toTrainingResponseDto)
                .collect(Collectors.toList());
    }//

    @Override
    public void activateDeactivateTrainee(String username, Boolean isActive) throws NotFoundException {
        Trainer trainer = getTrainerByUsername(username);
        trainer.getUser().setActive(isActive);
        trainerRepository.save(trainer);
    }//

    public void deleteTrainer(Long id) throws NotFoundException {
        LOGGER.debug("TrainerServiceImpl: deleteTrainer({})", id);
        trainerRepository.delete(getTrainerById(id));
    }//
}
