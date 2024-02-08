package dev.fayzullokh.dtos;

import dev.fayzullokh.entity.Trainer;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TraineeResponseDto {


    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String address;
    private boolean isActive;

    private List<TrainerDTOForTrainee> trainers;

}
