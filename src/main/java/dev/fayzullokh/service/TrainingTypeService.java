package dev.fayzullokh.service;

import dev.fayzullokh.entity.TrainingType;
import dev.fayzullokh.exceptions.NotFoundException;

public interface TrainingTypeService {
    TrainingType findById(Long trainingTypeId) throws NotFoundException;
}
