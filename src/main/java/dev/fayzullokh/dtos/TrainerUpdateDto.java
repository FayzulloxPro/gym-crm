package dev.fayzullokh.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerUpdateDto {

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 255, message = "Username cannot exceed 255 characters")
    private String username;

    @NotBlank(message = "First name cannot be blank")
    @Size(max = 255, message = "First name cannot exceed 255 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 255, message = "Last name cannot exceed 255 characters")
    private String lastname;

    @NotNull(message = "Specialization ID cannot be null")
    @Positive(message = "Specialization ID must be a positive number")
    private Long trainingTypeId;

    @NotNull(message = "is active field cannot be null")
    private Boolean isActive;
}
