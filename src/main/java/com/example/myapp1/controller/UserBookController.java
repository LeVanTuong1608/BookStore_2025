// package com.example.myapp1.controller;

// import com.example.myapp1.dto.BookDTO;
// import com.example.myapp1.service.BookService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/user/api/books")
// public class UserBookController {

//     @Autowired
//     private BookService bookService;

//     // Lấy danh sách tất cả sách (cho người dùng)
//     @GetMapping
//     public List<BookDTO> getAllBooksForUser() {
//         return bookService.getAllBooks();
//     }

//     // Tìm kiếm sách theo tiêu đề (cho người dùng)
//     @GetMapping("/search")
//     public List<BookDTO> searchBooks(@RequestParam("title") String title) {
//         return bookService.searchBooksByTitle(title);
//     }

//     // Xem chi tiết sách theo ID (cho người dùng)
//     @GetMapping("/{id}")
//     public BookDTO getBookDetails(@PathVariable long id) {
//         return bookService.getBookDTOById(id);
//     }
// }
package com.example.myapp1.controller;

import com.example.myapp1.dto.BookDTO;
// import com.example.myapp1.dto.BookDTO;
// import com.example.myapp1.model.User;
import com.example.myapp1.service.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;
// import java.util.List;

@RestController
@RequestMapping("/user/api/books")
public class UserBookController {

    @Autowired
    private BookService bookService;

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    // Lấy danh sách tất cả sách (chỉ cho user đã đăng nhập)
    @GetMapping
    public ResponseEntity<?> getAllBooksForUser(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size, HttpSession session) {
        if (!isLoggedIn(session)) {
            return ResponseEntity.status(401).body("Bạn cần đăng nhập để xem danh sách sách.");
        }
        Page<BookDTO> bookPage = bookService.getBooksWithPagination(page, size);
        return ResponseEntity.ok(bookPage);
        // return ResponseEntity.ok(bookService.getBooksWithPagination(page, size));
        // return ResponseEntity.ok(bookService.getAllBooks());
    }

    // Tìm kiếm sách theo tiêu đề (chỉ cho user đã đăng nhập)
    // @GetMapping("/search")
    // public ResponseEntity<?> searchBooks(@RequestParam("title") String title,
    // HttpSession session) {
    // if (!isLoggedIn(session)) {
    // return ResponseEntity.status(401).body("Bạn cần đăng nhập để tìm kiếm
    // sách.");
    // }

    // return ResponseEntity.ok(bookService.searchBooksByTitle(title));
    // }

    // // Xem chi tiết sách theo ID (chỉ cho user đã đăng nhập)
    // @GetMapping("/{id}")
    // public ResponseEntity<?> getBookDetails(@PathVariable long id, HttpSession
    // session) {
    // if (!isLoggedIn(session)) {
    // return ResponseEntity.status(401).body("Bạn cần đăng nhập để xem chi tiết
    // sách.");
    // }
    // return ResponseEntity.ok(bookService.getBookDTOById(id));
    // }
    // Tìm kiếm sách với phân trang
    @GetMapping("/search")
    public ResponseEntity<?> searchBooks(
            @RequestParam("title") String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            HttpSession session) {

        if (!isLoggedIn(session)) {
            return ResponseEntity.status(401).body("Bạn cần đăng nhập để tìm kiếm sách.");
        }

        Page<BookDTO> searchResult = bookService.searchBooksByTitleWithPagination(title, page, size);
        return ResponseEntity.ok(searchResult);
    }

    // Xem chi tiết sách
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookDetails(
            @PathVariable long id,
            HttpSession session) {

        if (!isLoggedIn(session)) {
            return ResponseEntity.status(401).body("Bạn cần đăng nhập để xem chi tiết sách.");
        }

        BookDTO bookDetails = bookService.getBookDTOById(id);
        if (bookDetails == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookDetails);
    }
}
