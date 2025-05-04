package com.example.myapp1.service;

import com.example.myapp1.dto.UserDTO;
import com.example.myapp1.model.User;
import com.example.myapp1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getUserid(),
                user.getEmail(),
                user.getFullname(),
                user.getAddress(),
                user.getPhonenumber());
    }

    public UserDTO createUser(User user) {
        User saved = userRepository.save(user);
        return convertToDTO(saved);
    }

    public UserDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::convertToDTO).orElse(null);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id); // Xóa người dùng nếu tồn tại
        } else {
            throw new RuntimeException("User not found"); // Nếu không tìm thấy, ném lỗi
        }
    }

    public UserDTO updateUser(Long id, User user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setFullname(user.getFullname());
            updatedUser.setAddress(user.getAddress());
            updatedUser.setPhonenumber(user.getPhonenumber());

            // Nếu có mật khẩu mới, có thể cập nhật thêm
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                updatedUser.setPassword(user.getPassword());
            }

            User savedUser = userRepository.save(updatedUser);
            return convertToDTO(savedUser);
        } else {
            return null; // Không tìm thấy người dùng để cập nhật
        }
    }

    public User getUserEntityById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByEmail(String email);
    }

}
