package dev.fayzullokh.mappers;

import dev.fayzullokh.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    @Test
    void testToUser_ValidInput_ReturnsUser() {
        UserMapper userMapper = new UserMapper();
        String firstName = "John";
        String lastName = "Doe";

        User user = userMapper.toUser(firstName, lastName);

        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
    }

    @Test
    void testToUser_AnotherValidInput_ReturnsUser() {
        UserMapper userMapper = new UserMapper();
        String firstName = "Alice";
        String lastName = "Smith";

        User user = userMapper.toUser(firstName, lastName);

        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
    }

}
