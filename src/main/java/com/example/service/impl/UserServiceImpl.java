package com.example.service.impl;

import com.example.dto.UserDTO;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setFullname(userDTO.getFullname());
        user.setAddress(userDTO.getAddress());
        user.setPhonenumber(userDTO.getPhonenumber());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(long id, UserDTO userDTO) {
        return userRepository.findById(id).map(user -> {
            user.setEmail(userDTO.getEmail());
            user.setFullname(userDTO.getFullname());
            user.setAddress(userDTO.getAddress());
            user.setPhonenumber(userDTO.getPhonenumber());
            return userRepository.save(user);
        }).orElse(null);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
