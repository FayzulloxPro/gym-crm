package dev.fayzullokh.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TraineeUpdateDTO {
    @NotBlank(message = "Username cannot be blank")
    @Size(max = 255, min = 2, message = "Username cannot exceed 255 characters and can not be shorter than 2 characters")
    private String username;

    @NotBlank(message = "First name cannot be blank")
    @Size(max = 255, message = "first name cannot exceed 255 characters")
    private String firstname;
    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 255, message = "Last name cannot exceed 255 characters")
    private String lastname;
    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;
    @NotBlank(message = "Address cannot be blank")
    private String address;

    private boolean isActive;

}
