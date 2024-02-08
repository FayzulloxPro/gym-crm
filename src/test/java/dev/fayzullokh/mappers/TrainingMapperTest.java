package dev.fayzullokh.mappers;

import dev.fayzullokh.dtos.TrainingResponseDto;
import dev.fayzullokh.entity.Trainer;
import dev.fayzullokh.entity.Training;
import dev.fayzullokh.entity.TrainingType;
import dev.fayzullokh.entity.User;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrainingMapperTest {

    @Test
    void testToTrainingResponseDto_ValidTraining_ReturnsTrainingResponseDto() {
        TrainingMapper mapper = new TrainingMapper();
        Training training = new Training();
        training.setName("Java Programming");
        training.setDate(new Date());
        training.setTrainingType(new TrainingType());
        training.setDuration(90);

        Trainer trainer = new Trainer();
        User trainerUser = new User();
        trainerUser.setFirstName("John");
        trainerUser.setLastName("Doe");
        trainer.setUser(trainerUser);

        training.setTrainer(trainer);

        TrainingResponseDto responseDto = mapper.toTrainingResponseDto(training);

        assertEquals(training.getName(), responseDto.getTrainingName());
        assertEquals(training.getDate(), responseDto.getTrainingDate());
        assertEquals(training.getTrainingType(), responseDto.getTrainingType());
        assertEquals(training.getDuration(), responseDto.getTrainingDuration());
        assertEquals(trainerUser.getFirstName(), responseDto.getTrainerName());
    }

}
