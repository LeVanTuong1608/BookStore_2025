
package com.example.myapp1.controller;

import com.example.myapp1.dto.CartDTO;
import com.example.myapp1.dto.UserDTO;
import com.example.myapp1.model.Cart;
import com.example.myapp1.model.User;
import com.example.myapp1.service.CartService;
import com.example.myapp1.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/api/carts")
@CrossOrigin(origins = "*") // Cho phép gọi từ frontend
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    // ✅ Lấy thực thể User từ session
    // private User getUserFromSession(HttpSession session) {
    // Object sessionUser = session.getAttribute("user");
    // if (sessionUser == null)
    // return null;

    // if (sessionUser instanceof User) {
    // return (User) sessionUser;
    // } else if (sessionUser instanceof UserDTO) {
    // return userService.getUserById(((UserDTO) sessionUser).getUserid());

    // // return userService.getUserById(((UserDTO) sessionUser).getUserid());
    // } else {
    // return null;
    // }
    // }

    private User getUserFromSession(HttpSession session) {
        Object sessionUser = session.getAttribute("user");
        if (sessionUser instanceof User user) {
            return user;
        }
        if (sessionUser instanceof UserDTO userDTO) {
            return userService.getUserEntityById(userDTO.getUserid()).orElse(null);
        }
        return null;
    }

    // ✅ Thêm sách vào giỏ hàng
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartDTO cartDTO, HttpSession session,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        User user = getUserFromSession(session);
        if (user == null)
            return ResponseEntity.status(401).body("Bạn chưa đăng nhập");

        // try {
        // CartDTO cart = cartService.addToCart(user, cartDTO);
        // return ResponseEntity.ok(cart);
        // } catch (RuntimeException e) {
        // return ResponseEntity.badRequest().body(e.getMessage());
        // }
        try {
            // Kiểm tra nếu sản phẩm đã có trong giỏ hàng
            // List<Cart> existingCartItems = cartService.getCartItems(user);
            Page<CartDTO> cartPage = cartService.getCartItems(user, page, size);
            CartDTO existingCart = cartPage.getContent().stream()
                    .filter(cart -> cart.getBookId() == cartDTO.getBookId()
                            && "pending".equalsIgnoreCase(cart.getStatus()))
                    .findFirst().orElse(null);

            // Cart existingCart = cartService.getCartItems(user).stream()
            // .filter(cart -> cart.getBook().getBookId() == cartDTO.getBookId()
            // && "pending".equalsIgnoreCase(cart.getStatus()))
            // .findFirst().orElse(null);
            if (existingCart != null) {
                return ResponseEntity.status(400).body("Sản phẩm đã có trong giỏ hàng");
            }

            CartDTO cart = cartService.addToCart(user, cartDTO);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Lấy danh sách giỏ hàng chưa thanh toán
    // @GetMapping
    // public ResponseEntity<?> getCartItems(HttpSession session) {
    // User user = getUserFromSession(session);
    // if (user == null)
    // return ResponseEntity.status(401).body("Bạn chưa đăng nhập");

    // List<Cart> carts = cartService.getCartItems(user).stream()
    // .filter(cart -> "pending".equalsIgnoreCase(cart.getStatus()))
    // .collect(Collectors.toList());
    // return ResponseEntity.ok(carts);
    // }
    @GetMapping
    public ResponseEntity<?> getCartItems(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            HttpSession session) {
        User user = getUserFromSession(session);
        if (user == null)
            return ResponseEntity.status(401).body("Bạn chưa đăng nhập");

        // Tạo pageable từ các tham số page và size
        // Pageable pageable = PageRequest.of(page, size);

        // Lấy danh sách giỏ hàng với phân trang
        // Page<Cart> cartPage = cartService.getCartItems(user, pageable).stream()
        // .filter(cart -> "pending".equalsIgnoreCase(cart.getStatus()))
        // .collect(Collectors.toList());
        // Lấy danh sách giỏ hàng của người dùng với phân trang
        Page<CartDTO> cartDTOPage = cartService.getCartItems(user, page, size);
        return ResponseEntity.ok(cartDTOPage);
    }

    // ✅ Xóa một mục trong giỏ hàng
    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable int cartId, HttpSession session) {
        User user = getUserFromSession(session);
        if (user == null)
            return ResponseEntity.status(401).body("Bạn chưa đăng nhập");

        try {
            cartService.deleteCartItem(cartId, user);
            return ResponseEntity.ok("Đã xóa sản phẩm khỏi giỏ hàng");
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    // ✅ Xóa toàn bộ giỏ hàng
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(HttpSession session) {
        User user = getUserFromSession(session);
        if (user == null)
            return ResponseEntity.status(401).body("Bạn chưa đăng nhập");

        cartService.clearCart(user);
        return ResponseEntity.ok("Đã xóa toàn bộ giỏ hàng");
    }

    // ✅ Thanh toán giỏ hàng
    @PutMapping("/checkout")
    public ResponseEntity<?> checkout(HttpSession session) {
        User user = getUserFromSession(session);
        if (user == null)
            return ResponseEntity.status(401).body("Bạn chưa đăng nhập");

        cartService.updateCartStatusToPaid(user);
        return ResponseEntity.ok("Giỏ hàng đã được thanh toán thành công");
    }

    // // ✅ Lấy thông tin người dùng hiện tại
    // @GetMapping("/me")
    // public ResponseEntity<?> currentUser(HttpSession session) {
    // Object sessionUser = session.getAttribute("user");
    // if (sessionUser == null)
    // return ResponseEntity.status(401).body("Chưa đăng nhập");

    // if (sessionUser instanceof UserDTO) {
    // return ResponseEntity.ok(sessionUser);
    // } else if (sessionUser instanceof User) {
    // return ResponseEntity.ok(new UserDTO((User) sessionUser));
    // } else {
    // return ResponseEntity.status(500).body("Dữ liệu session không hợp lệ");
    // }
    // }
    @GetMapping("/me")
    public ResponseEntity<?> currentUser(HttpSession session) {
        // Lấy thông tin người dùng trong session
        Object sessionUser = session.getAttribute("user");

        // Kiểm tra nếu không có người dùng trong session
        if (sessionUser == null) {
            return ResponseEntity.status(401).body("Chưa đăng nhập");
        }

        // Kiểm tra kiểu dữ liệu trong session và trả về kết quả phù hợp
        if (sessionUser instanceof UserDTO) {
            return ResponseEntity.ok(sessionUser); // Trả về UserDTO nếu đã có trong session
        } else if (sessionUser instanceof User) {
            // Chuyển đổi User thành UserDTO và trả về
            UserDTO userDTO = new UserDTO(
                    ((User) sessionUser).getUserid(),
                    ((User) sessionUser).getEmail(),
                    ((User) sessionUser).getPassword(),
                    ((User) sessionUser).getFullname(),
                    ((User) sessionUser).getAddress(),
                    ((User) sessionUser).getPhonenumber(),
                    ((User) sessionUser).getRole());
            // ((User) sessionUser).getRole());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(500).body("Dữ liệu session không hợp lệ");
        }
    }

    // ✅ (Tùy chọn) Cập nhật trạng thái giỏ hàng sang đã thanh toán
    @PutMapping("/update-status-to-paid")
    public ResponseEntity<?> updateCartStatusToPaid(HttpSession session) {
        return checkout(session);
    }
}
