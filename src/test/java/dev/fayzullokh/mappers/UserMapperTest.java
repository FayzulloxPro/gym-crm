package dev.fayzullokh.mappers;

import dev.fayzullokh.dtos.TraineeRegistrationDto;
import dev.fayzullokh.dtos.TrainerRegistrationDto;
import dev.fayzullokh.entity.Trainee;
import dev.fayzullokh.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    @Test
    void testToUser_ValidInput_ReturnsUser() {
        UserMapper userMapper = new UserMapper();

        TrainerRegistrationDto dto = new TrainerRegistrationDto("John", "Doe", 1L, "password");
        User user = userMapper.toUser(dto);

        assertEquals(dto.getFirstName(), user.getFirstName());
        assertEquals(dto.getLastName(), user.getLastName());
        assertEquals(dto.getPassword(), user.getPassword());
    }

    @Test
    void testToUser_AnotherValidInput_ReturnsUser() {
        UserMapper userMapper = new UserMapper();

        TrainerRegistrationDto dto = new TrainerRegistrationDto("Alice", "Doe", 1L, "password");
        User user = userMapper.toUser(dto);


        assertEquals(dto.getFirstName(), user.getFirstName());
        assertEquals(dto.getLastName(), user.getLastName());
        assertEquals(dto.getPassword(), user.getPassword());
    }

}
