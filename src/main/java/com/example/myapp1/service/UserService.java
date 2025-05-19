// package com.example.myapp1.service;

// import com.example.myapp1.dto.*;
// import com.example.myapp1.exception.EmailAlreadyExistsException;
// import com.example.myapp1.model.User;
// import com.example.myapp1.repository.UserRepository;

// import jakarta.servlet.http.HttpSession;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;

// @Service
// public class UserService {

//     @Autowired
//     private UserRepository userRepository; // Thao tác với DB

//     @Autowired
//     private PasswordEncoder passwordEncoder; // Mã hóa mật khẩu

//     // ✅ Đăng ký người dùng mới
//     public void register(RegisterRequest request) {
//         // ❗ Kiểm tra email đã tồn tại chưa (phải có existsByEmail trong UserRepository)
//         if (userRepository.existsByEmail(request.getEmail())) {
//             throw new EmailAlreadyExistsException("Email already registered");
//         }

//         // Tạo user mới và mã hóa mật khẩu
//         User user = new User();
//         user.setEmail(request.getEmail());
//         user.setFullname(request.getFullname());
//         user.setAddress(request.getAddress());
//         user.setPhonenumber(request.getPhonenumber());
//         user.setPassword(passwordEncoder.encode(request.getPassword()));

//         userRepository.save(user); // Lưu user vào DB
//     }

//     // ✅ Đăng nhập người dùng, trả về thông tin người dùng và thông báo
//     public LoginResponse login(LoginRequest request, HttpSession session) {
//         User user = validateLogin(request); // Dùng logic xác thực đã có
//         session.setAttribute("user", user); // Lưu thông tin người dùng vào session
//         UserDTO userDTO = convertToDTO(user); // Chuyển đổi sang DTO để trả về
//         return new LoginResponse("Login successful", userDTO); // Trả về thông tin người dùng
//     }

//     // ✅ Tạo user (dùng cho admin thêm)
//     public UserDTO createUser(User user) {
//         user.setPassword(passwordEncoder.encode(user.getPassword())); // Mã hóa mật khẩu
//         User saved = userRepository.save(user); // Lưu user vào DB
//         return convertToDTO(saved); // Trả về DTO của user đã lưu
//     }

//     // ✅ Lấy user theo ID
//     public UserDTO getUserById(Long id) {
//         Optional<User> user = userRepository.findById(id); // Tìm user theo ID
//         return user.map(this::convertToDTO).orElse(null); // Chuyển đổi sang DTO nếu tồn tại
//     }

//     // ✅ Lấy danh sách toàn bộ user
//     public List<UserDTO> getAllUsers() {
//         return userRepository.findAll()
//                 .stream()
//                 .map(this::convertToDTO) // Chuyển đổi tất cả user thành DTO
//                 .collect(Collectors.toList());
//     }

//     // ✅ Xóa user theo ID
//     public void deleteUser(Long id) {
//         if (!userRepository.existsById(id)) { // Kiểm tra xem user có tồn tại không
//             throw new RuntimeException("User not found");
//         }
//         userRepository.deleteById(id); // Xóa user theo ID
//     }

//     // ✅ Cập nhật thông tin user
//     public UserDTO updateUser(Long id, User user) {
//         Optional<User> existingUser = userRepository.findById(id); // Kiểm tra user tồn tại
//         if (existingUser.isPresent()) { // Nếu user tồn tại
//             User updatedUser = existingUser.get();
//             updatedUser.setFullname(user.getFullname());
//             updatedUser.setAddress(user.getAddress());
//             updatedUser.setPhonenumber(user.getPhonenumber());

//             // Nếu có mật khẩu mới, thì mã hóa lại
//             if (user.getPassword() != null && !user.getPassword().isEmpty()) {
//                 updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
//             }

//             User savedUser = userRepository.save(updatedUser); // Lưu thông tin đã cập nhật
//             return convertToDTO(savedUser); // Trả về DTO của user đã cập nhật
//         } else {
//             return null; // Trả về null nếu không tìm thấy user
//         }
//     }

//     // ✅ Trả về entity gốc của user (dùng cho nội bộ hệ thống)
//     public User getUserEntityById(Long id) {
//         return userRepository.findById(id).orElse(null); // Trả về entity user nếu có
//     }

//     // ✅ Chuyển User -> UserDTO (ẩn mật khẩu)
//     private UserDTO convertToDTO(User user) {
//         return new UserDTO(
//                 user.getUserid(),
//                 user.getEmail(),
//                 user.getFullname(),
//                 user.getAddress(),
//                 user.getPhonenumber());
//     }

//     // ✅ Xác thực thông tin đăng nhập
//     // private User validateLogin(LoginRequest request) {
//     // User user = userRepository.findByEmail(request.getEmail()); // Tìm user theo
//     // email
//     // if (user == null || !user.getPassword().equals(request.getPassword())) { //
//     // Kiểm tra email và mật khẩu
//     // throw new RuntimeException("Invalid email or password");
//     // }
//     // return user; // Trả về user nếu thông tin hợp lệ
//     // }
//     private User validateLogin(LoginRequest request) {
//         Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
//         if (userOptional.isEmpty()) {
//             throw new RuntimeException("Invalid email or password"); // Không tìm thấy user với email
//         }

//         User user = userOptional.get(); // Lấy đối tượng User từ Optional (vì đã kiểm tra isEmpty)

//         // So sánh mật khẩu đã mã hóa trong DB với mật khẩu người dùng nhập
//         if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//             throw new RuntimeException("Invalid email or password"); // Mật khẩu không khớp
//         }

//         return user; // Trả về user nếu thông tin đăng nhập hợp lệ
//     }
// }

// package com.example.myapp1.service;

// import com.example.myapp1.dto.*;
// import com.example.myapp1.exception.EmailAlreadyExistsException;
// import com.example.myapp1.model.User;
// import com.example.myapp1.repository.UserRepository;

// import jakarta.servlet.http.HttpSession;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.stereotype.Service;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;

// @Service
// public class UserService {

//     @Autowired
//     private UserRepository userRepository;

//     // ✅ Đăng ký người dùng mới (KHÔNG mã hóa mật khẩu)
//     public void register(RegisterRequest request) {
//         if (userRepository.existsByEmail(request.getEmail())) {
//             throw new EmailAlreadyExistsException("Email already registered");
//         }

//         User user = new User();
//         user.setEmail(request.getEmail());
//         user.setFullname(request.getFullname());
//         user.setAddress(request.getAddress());
//         user.setPhonenumber(request.getPhonenumber());
//         user.setPassword(request.getPassword()); // Lưu thẳng mật khẩu
//         user.setRole("customer");

//         userRepository.save(user);
//     }

//     // ✅ Đăng nhập người dùng (KHÔNG mã hóa mật khẩu)
//     public LoginResponse login(LoginRequest request, HttpSession session) {
//         User user = validateLogin(request);
//         session.setAttribute("user", user);
//         UserDTO userDTO = convertToDTO(user);
//         return new LoginResponse("Login successful", userDTO);
//     }

//     public Page<UserDTO> getAllUsersPaginated(int page, int size) {
//         Pageable pageable = PageRequest.of(page, size);
//         Page<User> userPage = userRepository.findAll(pageable);
//         return userPage.map(this::convertToDTO); // chuyển sang DTO
//     }

//     // ✅ Tạo user (dành cho admin thêm, KHÔNG mã hóa mật khẩu)
//     public UserDTO createUser(User user) {
//         // Không mã hóa mật khẩu nữa
//         User saved = userRepository.save(user);
//         return convertToDTO(saved);
//     }

//     // public UserDTO getUserById(Long id) {
//     // Optional<User> user = userRepository.findById(id);
//     // return user.map(this::convertToDTO).orElse(null);
//     // }

//     public List<UserDTO> getAllUsers() {
//         return userRepository.findAll()
//                 .stream()
//                 .map(this::convertToDTO)
//                 .collect(Collectors.toList());
//     }

//     public void deleteUser(Long id) {
//         if (!userRepository.existsById(id)) {
//             throw new RuntimeException("User not found");
//         }
//         userRepository.deleteById(id);
//     }

//     public UserDTO updateUser(Long id, User user) {
//         Optional<User> existingUser = userRepository.findById(id);
//         if (existingUser.isPresent()) {
//             User updatedUser = existingUser.get();
//             updatedUser.setFullname(user.getFullname());
//             updatedUser.setAddress(user.getAddress());
//             updatedUser.setPhonenumber(user.getPhonenumber());
//             // updatedUser.setPassword(user.getPassword());

//             // Nếu có mật khẩu mới, cập nhật luôn (không mã hóa)
//             if (user.getPassword() != null && !user.getPassword().isEmpty()) {
//                 updatedUser.setPassword(user.getPassword());
//             }

//             User savedUser = userRepository.save(updatedUser);
//             return convertToDTO(savedUser);
//         } else {
//             return null;
//         }
//     }

//     public User getUserEntityById(Long id) {
//         return userRepository.findById(id).orElse(null);
//     }

//     public User getUserById(Long userId) {
//         return userRepository.findById(userId)
//                 .orElseThrow(() -> new RuntimeException("User not found"));
//     }

//     private UserDTO convertToDTO(User user) {
//         return new UserDTO(
//                 user.getUserid(),
//                 user.getEmail(),
//                 user.getPassword(),
//                 user.getFullname(),
//                 user.getAddress(),
//                 user.getPhonenumber(),
//                 user.getRole());
//     }

//     // ✅ So sánh mật khẩu thẳng, không mã hóa
//     private User validateLogin(LoginRequest request) {
//         Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
//         if (userOptional.isEmpty()) {
//             throw new RuntimeException("Invalid email or password");
//         }

//         User user = userOptional.get();

//         if (!user.getPassword().equals(request.getPassword())) {
//             throw new RuntimeException("Invalid email or password");
//         }

//         return user;
//     }
// }

package com.example.myapp1.service;

import com.example.myapp1.dto.*;
import com.example.myapp1.exception.EmailAlreadyExistsException;
import com.example.myapp1.exception.UserNotFoundException;
import com.example.myapp1.model.User;
import com.example.myapp1.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private static final String DEFAULT_ROLE = "customer";
    private static final String INVALID_CREDENTIALS = "Email hoặc mật khẩu không chính xác";
    private static final String USER_NOT_FOUND = "Không tìm thấy người dùng";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Đăng ký người dùng mới (có mã hóa mật khẩu)
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email đã được đăng ký");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFullname(request.getFullname());
        user.setAddress(request.getAddress());
        user.setPhonenumber(request.getPhonenumber());
        user.setPassword((request.getPassword()));
        user.setRole(DEFAULT_ROLE);

        userRepository.save(user);
    }

    // Đăng nhập người dùng
    public LoginResponse login(LoginRequest request, HttpSession session) {
        User user = validateLogin(request);
        session.setAttribute("user", convertToDTO(user));
        return new LoginResponse("Đăng nhập thành công", convertToDTO(user));
    }

    // Lấy danh sách người dùng có phân trang
    public Page<UserDTO> getAllUsersPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).map(this::convertToDTO);
    }

    // Tạo người dùng mới (cho admin)
    // public UserDTO createUser(UserCreateRequest request) {
    // if (userRepository.existsByEmail(request.getEmail())) {
    // throw new EmailAlreadyExistsException("Email đã được đăng ký");
    // }

    // User user = new User();
    // user.setEmail(request.getEmail());
    // user.setFullname(request.getFullname());
    // user.setAddress(request.getAddress());
    // user.setPhonenumber(request.getPhonenumber());
    // user.setPassword(passwordEncoder.encode(request.getPassword()));
    // user.setRole(request.getRole());

    // User savedUser = userRepository.save(user);
    // return convertToDTO(savedUser);
    // }

    // Lấy thông tin người dùng theo ID
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    // Cập nhật thông tin người dùng
    // public UserDTO updateUser(Long id, @RequestBody RegisterRequest request) {
    // User user = userRepository.findById(id)
    // .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

    // user.setFullname(request.getFullname());
    // user.setAddress(request.getAddress());
    // user.setPhonenumber(request.getPhonenumber());

    // if (request.getPassword() != null && !request.getPassword().isEmpty()) {
    // user.setPassword(passwordEncoder.encode(request.getPassword()));
    // }

    // User updatedUser = userRepository.save(user);
    // return convertToDTO(updatedUser);
    // }
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        user.setFullname(userDTO.getFullname());
        user.setAddress(userDTO.getAddress());
        user.setPhonenumber(userDTO.getPhonenumber());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            // user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            // userDTO.setPassword(user.getPassword());
            user.setPassword(userDTO.getPassword());
        }

        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    // Xóa người dùng
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    // Lấy entity user (dùng nội bộ)
    public Optional<User> getUserEntityById(Long id) {
        return userRepository.findById(id);
    }

    // Chuyển đổi User sang UserDTO
    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .userId(user.getUserid())
                .email(user.getEmail())
                .fullName(user.getFullname())
                .address(user.getAddress())
                .phonenumber(user.getPhonenumber())
                .role(user.getRole())
                .build();
    }

    // Xác thực thông tin đăng nhập
    private User validateLogin(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException(INVALID_CREDENTIALS));

        // if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        // throw new RuntimeException(INVALID_CREDENTIALS);
        // }
        if (!request.getPassword().equals(user.getPassword())) {
            throw new RuntimeException(INVALID_CREDENTIALS);
        }

        return user;
    }
}