package dev.fayzullokh.service;

import dev.fayzullokh.dtos.NewTrainingRequest;
import dev.fayzullokh.dtos.TrainingRequestDto;
import dev.fayzullokh.entity.Trainee;
import dev.fayzullokh.entity.Trainer;
import dev.fayzullokh.entity.Training;
import dev.fayzullokh.entity.TrainingType;
import dev.fayzullokh.enums.ActionType;
import dev.fayzullokh.exceptions.NotFoundException;
import dev.fayzullokh.repositories.TrainingRepository;
import dev.fayzullokh.repositories.TrainingTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {
    private final TraineeServiceImpl traineeService;
    private final TrainerServiceImpl trainerService;
    private final TrainingRepository trainingRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final RestTemplate restTemplate;

    @Override
    public void addTraining(TrainingRequestDto trainingRequestDto) throws NotFoundException {
        Trainee trainee = traineeService.getTraineeByUsername(trainingRequestDto.getTraineeUsername());
        Trainer trainer = trainerService.getTrainerByUsername(trainingRequestDto.getTrainerUsername());

        Training training = new Training();
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setName(trainingRequestDto.getTrainingName());
        training.setDate(trainingRequestDto.getTrainingDate());
        training.setDuration(trainingRequestDto.getTrainingDuration());
        trainingRepository.save(training);

        //todo call secondary microservice
        NewTrainingRequest newTrainingRequest = NewTrainingRequest.builder()
                .trainerId(trainer.getId())
                .firstName(trainer.getUser().getFirstName())
                .lastName(trainer.getUser().getLastName())
                .username(trainer.getUser().getUsername())
                .isActive(trainer.getUser().isActive())
                .duration(trainingRequestDto.getTrainingDuration())
                .startDate(trainingRequestDto.getTrainingDate())
                .actionType(ActionType.ADD)
                .build();
        restTemplate.postForObject("http://localhost:8088/modify", newTrainingRequest, Void.class);

    }

    @Override
    public List<TrainingType> getAllTrainingTypes() {
        return trainingTypeRepository.findAll();
    }
}
