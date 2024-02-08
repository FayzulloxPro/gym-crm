package dev.fayzullokh.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TraineeUpdateResponseDto {
    private Long traineeId;

    private String address;
    private Date dateOfBirth;

    private String firstName;
    private String lastname;
}
