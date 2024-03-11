package dev.fayzullokh.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TrainerRegistrationDto {

    @NotBlank(message = "First name cannot be blank")
    @Size(max = 255, message = "First name cannot exceed 255 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 255, message = "Last name cannot exceed 255 characters")
    private String lastName;

    @NotNull(message = "Training type ID cannot be null")
    private Long trainingTypeId;

    @NotBlank(message = "Password cannot be blank")
    @Size(max = 255, message = "Password cannot exceed 255 characters")
    private String password;

}

