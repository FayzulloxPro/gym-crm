package dev.fayzullokh.service;

import dev.fayzullokh.dtos.TrainingRequestDto;
import dev.fayzullokh.entity.TrainingType;
import dev.fayzullokh.exceptions.NotFoundException;

import java.util.List;

public interface TrainingService {
    void addTraining(TrainingRequestDto trainingRequestDto) throws NotFoundException;

    List<TrainingType> getAllTrainingTypes();
}
