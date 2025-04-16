package com.example.service;

import com.example.dto.UserDTO;
import com.example.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(long id);

    User getUserByEmail(String email);

    User createUser(UserDTO userDTO);

    User updateUser(long id, UserDTO userDTO);

    void deleteUser(long id);
}
