package dev.fayzullokh.service;

import dev.fayzullokh.dtos.ChangePasswordRequest;
import dev.fayzullokh.entity.Trainer;
import dev.fayzullokh.entity.User;
import dev.fayzullokh.exceptions.NotFoundException;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUserById(Long id) throws NotFoundException;

    List<User> findAllUsers();

    User updateUser(User user);

    String changePassword(ChangePasswordRequest request) throws NotFoundException;

    String changeActivation(boolean status) throws NotFoundException;
}
