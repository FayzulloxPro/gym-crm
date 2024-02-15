package dev.fayzullokh.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreatedResponseDTO {

    @NotBlank(message = "Username cannot be blank")
    private String username;

}
