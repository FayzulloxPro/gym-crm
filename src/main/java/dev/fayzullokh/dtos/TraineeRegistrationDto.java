package dev.fayzullokh.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TraineeRegistrationDto {

    @NotBlank(message = "First name cannot be blank")
    @Size(max = 255, message = "First name cannot exceed 255 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 255, message = "Last name cannot exceed 255 characters")
    private String lastName;

    @NotBlank(message = "Password cannot be blank")
    @Size(max = 255, message = "Password cannot exceed 255 characters")
    private String password;

    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;

    private String address;
}
