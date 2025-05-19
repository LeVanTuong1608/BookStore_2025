
// package com.example.myapp1.service;

// import com.example.myapp1.dto.CartDTO;
// import com.example.myapp1.model.Book;
// import com.example.myapp1.model.Cart;
// import com.example.myapp1.model.User;
// import com.example.myapp1.repository.BookRepository;
// import com.example.myapp1.repository.CartRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.text.SimpleDateFormat;
// import java.util.Date;
// import java.util.List;
// import java.util.stream.Collectors;

// // @Service
// // public class CartService {

// //     @Autowired
// //     private CartRepository cartRepository;

// //     @Autowired
// //     private BookRepository bookRepository;

// //     public CartDTO addToCart(User user, CartDTO cartDTO) {
// //         Long bookId = (long) cartDTO.getBookId();
// //         Book book = bookRepository.findById(bookId)
// //                 .orElseThrow(() -> new RuntimeException("Không tìm thấy sách"));

// //         Cart cart = new Cart(user, book, cartDTO.getQuantity(), "pending");
// //         cart.setCreatedAt(new Date());
// //         cart = cartRepository.save(cart);

// //         return convertToDTO(cart);
// //     }

// //     public List<CartDTO> getPendingCartDTOs(User user) {
// //         List<Cart> carts = cartRepository.findByUser(user);
// //         return carts.stream()
// //                 .filter(cart -> "pending".equalsIgnoreCase(cart.getStatus()))
// //                 .map(this::convertToDTO)
// //                 .collect(Collectors.toList());
// //     }

// //     public void clearCart(User user) {
// //         cartRepository.deleteByUser(user);
// //     }

// //     public void deleteCartItem(int cartId, User user) {
// //         Cart cart = cartRepository.findById(cartId)
// //                 .orElseThrow(() -> new RuntimeException("Không tìm thấy mục trong giỏ hàng"));

// //         if (!Long.valueOf(cart.getUser().getUserid()).equals(user.getUserid())) {
// //             throw new RuntimeException("Không có quyền xóa mục này");
// //         }

// //         cartRepository.delete(cart);
// //     }

// //     public void updateCartStatusToPaid(User user) {
// //         List<Cart> carts = cartRepository.findByUserAndStatus(user, "pending");
// //         for (Cart cart : carts) {
// //             cart.setStatus("paid");
// //         }
// //         cartRepository.saveAll(carts);
// //     }

// //     private CartDTO convertToDTO(Cart cart) {
// //         CartDTO dto = new CartDTO();
// //         dto.setCartId(Long.valueOf(cart.getCartId()));
// //         dto.setUserId(cart.getUser().getUserid());
// //         dto.setBookId(cart.getBook().getBookId());
// //         dto.setQuantity(cart.getQuantity());
// //         dto.setStatus(cart.getStatus());

// //         if (cart.getCreatedAt() != null) {
// //             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
// //             dto.setCreatedAt(sdf.format(cart.getCreatedAt()));
// //         }

// //         return dto;
// //     }
// // }
// @Service
// public class CartService {

//     @Autowired
//     private CartRepository cartRepository;

//     @Autowired
//     private BookRepository bookRepository;

//     public CartDTO addToCart(User user, CartDTO cartDTO) {
//         Long bookId = (long) cartDTO.getBookId();
//         // Xử lý lỗi không tìm thấy sách với exception tùy chỉnh
//         // Book book = bookRepository.findById(bookId)
//         // .orElseThrow(() -> new BookNotFoundException("Không tìm thấy sách"));
//         Book book = bookRepository.findById(bookId)
//                 .orElseThrow(() -> new RuntimeException("Không tìm thấy sách"));

//         Cart cart = new Cart(user, book, cartDTO.getQuantity(), "pending");
//         cart.setCreatedAt(new Date());
//         cart = cartRepository.save(cart);

//         return convertToDTO(cart);
//     }

//     public List<CartDTO> getPendingCartDTOs(User user) {
//         List<Cart> carts = cartRepository.findByUser(user);
//         return carts.stream()
//                 .filter(cart -> "pending".equalsIgnoreCase(cart.getStatus()))
//                 .map(this::convertToDTO)
//                 .collect(Collectors.toList());
//     }

//     public void clearCart(User user) {
//         cartRepository.deleteByUser(user);
//     }

//     // Phương thức lấy các mục giỏ hàng của người dùng
//     public List<Cart> getCartItems(User user) {
//         return cartRepository.findByUser(user); // Hoặc tùy thuộc vào cách bạn lưu trữ giỏ hàng
//     }

//     public void deleteCartItem(int cartId, User user) {
//         Cart cart = cartRepository.findById(cartId)
//                 .orElseThrow(() -> new RuntimeException("Không tìm thấy mục trong giỏ hàng"));

//         if (!Long.valueOf(cart.getUser().getUserid()).equals(user.getUserid())) {
//             throw new RuntimeException("Không có quyền xóa mục này");
//         }

//         cartRepository.delete(cart);
//     }

//     public void updateCartStatusToPaid(User user) {
//         List<Cart> carts = cartRepository.findByUserAndStatus(user, "pending");
//         for (Cart cart : carts) {
//             cart.setStatus("paid");
//         }
//         cartRepository.saveAll(carts);
//     }

//     private CartDTO convertToDTO(Cart cart) {
//         CartDTO dto = new CartDTO();
//         dto.setCartId(Long.valueOf(cart.getCartId()));
//         dto.setUserId(cart.getUser().getUserid());
//         dto.setBookId(cart.getBook().getBookId());
//         dto.setQuantity(cart.getQuantity());
//         dto.setStatus(cart.getStatus());

//         if (cart.getCreatedAt() != null) {
//             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//             dto.setCreatedAt(sdf.format(cart.getCreatedAt()));
//         }

//         return dto;
//     }
// }

package com.example.myapp1.service;

import com.example.myapp1.dto.CartDTO;
import com.example.myapp1.model.Book;
import com.example.myapp1.model.Cart;
import com.example.myapp1.model.User;
import com.example.myapp1.repository.BookRepository;
import com.example.myapp1.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;

    public CartDTO addToCart(User user, CartDTO cartDTO) {
        Long bookId = (long) cartDTO.getBookId();
        // Xử lý lỗi không tìm thấy sách với exception tùy chỉnh
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách"));

        Cart cart = new Cart(user, book, cartDTO.getQuantity(), "pending");
        cart.setCreatedAt(new Date());
        cart = cartRepository.save(cart);

        return convertToDTO(cart);
    }

    public Page<CartDTO> getCartItems(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cart> cartPage = cartRepository.findByUserAndStatus(user, "pending", pageable);
        return cartPage.map(this::convertToDTO);
    }

    public List<CartDTO> getPendingCartDTOs(User user) {
        List<Cart> carts = cartRepository.findByUser(user);
        return carts.stream()
                .filter(cart -> "pending".equalsIgnoreCase(cart.getStatus()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void clearCart(User user) {
        cartRepository.deleteByUser(user);
    }

    public void deleteCartItem(int cartId, User user) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mục trong giỏ hàng"));

        if (!Long.valueOf(cart.getUser().getUserid()).equals(user.getUserid())) {
            throw new RuntimeException("Không có quyền xóa mục này");
        }

        cartRepository.delete(cart);
    }

    public void updateCartStatusToPaid(User user) {
        List<Cart> carts = cartRepository.findByUserAndStatus(user, "pending");
        for (Cart cart : carts) {
            cart.setStatus("paid");
        }
        cartRepository.saveAll(carts);
    }

    private CartDTO convertToDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setCartId(Long.valueOf(cart.getCartId()));
        dto.setUserId(cart.getUser().getUserid());
        dto.setBookId(cart.getBook().getBookId());
        dto.setQuantity(cart.getQuantity());
        dto.setStatus(cart.getStatus());

        if (cart.getCreatedAt() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dto.setCreatedAt(sdf.format(cart.getCreatedAt()));
        }

        return dto;
    }
}
