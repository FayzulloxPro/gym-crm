package dev.fayzullokh.mappers;

import dev.fayzullokh.dtos.TraineeRegistrationDto;
import dev.fayzullokh.dtos.TrainerRegistrationDto;
import dev.fayzullokh.entity.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(TraineeRegistrationDto dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());
        return user;
    }
    public User toUser(TrainerRegistrationDto dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());
        return user;
    }
}
