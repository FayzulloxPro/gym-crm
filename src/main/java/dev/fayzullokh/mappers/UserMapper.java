package dev.fayzullokh.mappers;

import dev.fayzullokh.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(String firstName, String lastName) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }
}
