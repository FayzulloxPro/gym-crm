package dev.fayzullokh.service;

import dev.fayzullokh.entity.TrainingType;
import dev.fayzullokh.exceptions.NotFoundException;
import dev.fayzullokh.repositories.TrainingTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
public class TrainingTypeServiceImpl implements TrainingTypeService {
    private final TrainingTypeRepository trainingTypeRepository;
    @Override
    public TrainingType findById(Long trainingTypeId) throws NotFoundException {
        return trainingTypeRepository.findById(trainingTypeId).orElseThrow(()->
                new NotFoundException("Training type not found by id: "+trainingTypeId));
    }
}
