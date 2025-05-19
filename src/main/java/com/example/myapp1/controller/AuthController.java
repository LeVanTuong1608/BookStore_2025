
package com.example.myapp1.controller;

import com.example.myapp1.dto.*;
import com.example.myapp1.exception.EmailAlreadyExistsException;
import com.example.myapp1.model.User;
import com.example.myapp1.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    // ✅ API đăng ký
    // @PostMapping("/register")
    // public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    // try {
    // userService.register(request);
    // return ResponseEntity.ok("Đăng ký thành công");
    // } catch (EmailAlreadyExistsException e) {
    // return ResponseEntity.badRequest().body(e.getMessage());
    // }
    // }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            userService.register(request);
            Map<String, Object> res = new HashMap<>();
            res.put("message", "Đăng ký thành công");
            return ResponseEntity.ok(res);
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // ✅ API cập nhật thông tin người dùng
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO, HttpSession session) {
        UserDTO loggedInUser = (UserDTO) session.getAttribute("user");

        // Kiểm tra nếu người dùng chưa đăng nhập
        if (loggedInUser == null) {
            return ResponseEntity.status(401).body("Chưa đăng nhập");
        }

        if (!loggedInUser.getUserid().equals(userDTO.getUserid())) {
            return ResponseEntity.status(403).body("Bạn không có quyền cập nhật thông tin này");
        }

        try {
            // User userToUpdate = new User();
            // userToUpdate.setUserid(userDTO.getUserid());
            // userToUpdate.setFullname(userDTO.getFullname());
            // userToUpdate.setEmail(userDTO.getEmail());
            // userToUpdate.setAddress(userDTO.getAddress());
            // userToUpdate.setPhonenumber(userDTO.getPhonenumber());
            // userToUpdate.setPassword(userDTO.getPassword());
            UserDTO updatedUserDTO = userService.updateUser(userDTO.getUserid(), userDTO);
            // UserDTO updatedUserDTO = userService.updateUser(userDTO.getUserid(),
            // userDTO);

            // Cập nhật session với UserDTO mới
            session.setAttribute("user", updatedUserDTO);
            return ResponseEntity.ok(updatedUserDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ✅ API đăng nhập
    // @PostMapping("/login")
    // public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession
    // session) {
    // try {
    // LoginResponse response = userService.login(request, session);
    // session.setAttribute("user", response.getUser()); // Lưu thông tin người dùng
    // vào session

    // // Tạo đối tượng response chứa thông điệp và role của người dùng
    // Map<String, String> responseMessage = new HashMap<>();
    // if (response.getUser().getRole().equals("admin")) {
    // responseMessage.put("message", "Chào mừng Admin");
    // } else {
    // responseMessage.put("message", "Chào mừng User");
    // }

    // return ResponseEntity.ok(responseMessage); // Trả về đối tượng JSON
    // } catch (RuntimeException e) {
    // return ResponseEntity.status(401).body(e.getMessage());
    // }
    // }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        try {
            LoginResponse response = userService.login(request, session);
            UserDTO user = response.getUser();

            // Lưu thông tin người dùng vào session
            session.setAttribute("user", user);

            // Tạo đối tượng JSON trả về
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("message", user.getRole().equals("admin") ? "Chào mừng Admin" : "Chào mừng User");
            responseData.put("user", user); // chứa userid, email, role, v.v.

            return ResponseEntity.ok(responseData);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

    // API chỉ dành cho admin
    @GetMapping("/admin/dashboard")
    public ResponseEntity<?> getAdminDashboard(HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(401).body("Chưa đăng nhập");
        }

        if (!"admin".equals(user.getRole())) {
            return ResponseEntity.status(403).body("Bạn không có quyền truy cập");
        }

        return ResponseEntity.ok("Trang tổng quan của Admin");
    }

    // ✅ API đăng xuất
    // @GetMapping("/logout")
    // public ResponseEntity<?> logout(HttpSession session) {
    // session.invalidate();
    // return ResponseEntity.ok("Đăng xuất thành công");
    // }
    // ✅ API đăng xuất
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "Đăng xuất thành công"));
    }

    // ✅ API lấy thông tin người dùng hiện tại
    @GetMapping("/me")
    public ResponseEntity<?> currentUser(HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO == null) {
            return ResponseEntity.status(401).body("Chưa đăng nhập");
        }
        return ResponseEntity.ok(userDTO);
    }
}
