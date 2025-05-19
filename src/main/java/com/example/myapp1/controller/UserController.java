
// package com.example.myapp1.controller;

// import com.example.myapp1.dto.UserDTO;
// import com.example.myapp1.model.User;
// import com.example.myapp1.service.UserService;
// import jakarta.servlet.http.HttpSession;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// // import java.util.List;

// @RestController
// @RequestMapping("/admin/api/users")
// public class UserController {

//     @Autowired
//     private UserService userService;

//     // Hàm tiện ích kiểm tra đã đăng nhập và có vai trò admin chưa
//     private boolean isAdmin(HttpSession session) {
//         // User user = (User) session.getAttribute("user");
//         UserDTO userDTO = (UserDTO) session.getAttribute("user");
//         if (userDTO == null) {
//             System.out.println("Không có thông tin người dùng trong session.");
//             return false;
//         }
//         if ("admin".equalsIgnoreCase(userDTO.getRole())) {
//             System.out.println("Người dùng có quyền admin.");
//             return true;
//         } else {
//             System.out.println("Người dùng không có quyền admin.");
//             // System.out.println("User role: " + user.getRole());
//             return false;
//         }
//         // return user != null && "admin".equalsIgnoreCase(user.getRole());
//     }

//     // Tạo mới người dùng - chỉ admin mới được phép
//     @PostMapping
//     public ResponseEntity<?> createUser(@RequestBody User user, HttpSession session) {
//         if (!isAdmin(session)) {
//             return ResponseEntity.status(403).body("Bạn không có quyền truy cập.");
//         }
//         return ResponseEntity.ok(userService.createUser(user));
//     }

//     // Lấy tất cả người dùng - chỉ admin mới được phép
//     @GetMapping
//     public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "0") int page,
//             @RequestParam(defaultValue = "10") int size, HttpSession session) {
//         if (!isAdmin(session)) {
//             return ResponseEntity.status(403).body("Bạn không có quyền truy cập.");

//         }

//         return ResponseEntity.ok(userService.getAllUsersPaginated(page, size));

//         // return ResponseEntity.ok(userService.getAllUsers());
//     }

//     // Cập nhật thông tin người dùng - chỉ admin mới được phép
//     @PutMapping("/{id}")
//     public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user, HttpSession session) {
//         if (!isAdmin(session)) {
//             return ResponseEntity.status(403).body("Bạn không có quyền truy cập.");
//         }
//         UserDTO updatedUser = userService.updateUser(id, user);
//         return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
//     }

//     // Xóa người dùng - chỉ admin mới được phép
//     @DeleteMapping("/{id}")
//     public ResponseEntity<?> deleteUser(@PathVariable Long id, HttpSession session) {
//         if (!isAdmin(session)) {
//             return ResponseEntity.status(403).body("Bạn không có quyền truy cập.");
//         }

//         try {
//             userService.deleteUser(id);
//             return ResponseEntity.noContent().build(); // Xóa thành công
//         } catch (IllegalArgumentException e) {
//             return ResponseEntity.status(404).body("Người dùng không tồn tại."); // Người dùng không tồn tại (404)
//         } catch (RuntimeException e) {
//             return ResponseEntity.status(500).body("Lỗi khi xóa."); // Lỗi khi xóa (500)
//         }
//     }
// }

package com.example.myapp1.controller;

import com.example.myapp1.dto.UserDTO;
import com.example.myapp1.exception.UserNotFoundException;
import com.example.myapp1.model.User;
import com.example.myapp1.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api/users")
public class UserController {

    private static final String ADMIN_ROLE = "admin";
    private static final String ACCESS_DENIED = "Bạn không có quyền thực hiện thao tác này";
    private static final String USER_NOT_FOUND = "Không tìm thấy người dùng";
    private static final String SYSTEM_ERROR = "Lỗi hệ thống, vui lòng thử lại sau";
    private static final String INVALID_DATA = "Dữ liệu không hợp lệ";

    @Autowired
    private UserService userService;

    private boolean isAdmin(HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        return userDTO != null && ADMIN_ROLE.equalsIgnoreCase(userDTO.getRole());
    }

    // @PostMapping
    // public ResponseEntity<?> createUser(@RequestBody User user, HttpSession
    // session) {
    // if (!isAdmin(session)) {
    // return ResponseEntity.status(403).body(ACCESS_DENIED);
    // }
    // try {
    // UserDTO createdUser = userService.createUser(user);
    // return ResponseEntity.ok(createdUser);
    // } catch (IllegalArgumentException e) {
    // return ResponseEntity.badRequest().body(INVALID_DATA + ": " +
    // e.getMessage());
    // } catch (Exception e) {
    // return ResponseEntity.internalServerError().body(SYSTEM_ERROR);
    // }
    // }

    @GetMapping
    public ResponseEntity<?> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            HttpSession session) {

        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body(ACCESS_DENIED);
        }

        try {
            Page<UserDTO> usersPage = userService.getAllUsersPaginated(page, size);
            return ResponseEntity.ok(usersPage);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(SYSTEM_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body(ACCESS_DENIED);
        }

        try {
            UserDTO userDTO = userService.getUserById(id);
            return ResponseEntity.ok(userDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(USER_NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(SYSTEM_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody UserDTO userDTO,
            HttpSession session) {

        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body(ACCESS_DENIED);
        }

        try {
            UserDTO updatedUser = userService.updateUser(id, userDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(USER_NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(SYSTEM_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body(ACCESS_DENIED);
        }

        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(USER_NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(SYSTEM_ERROR);
        }
    }
}