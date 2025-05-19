
package com.example.myapp1.controller;

import com.example.myapp1.model.Book;
import com.example.myapp1.dto.BookDTO;
import com.example.myapp1.dto.UserDTO;
import com.example.myapp1.service.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/api/books")
public class BookController {

    // Các hằng số thông báo
    private static final String ADMIN_ROLE = "admin";
    private static final String ACCESS_DENIED = "Bạn không có quyền thực hiện thao tác này";
    private static final String BOOK_NOT_FOUND = "Không tìm thấy sách";
    private static final String SYSTEM_ERROR = "Lỗi hệ thống, vui lòng thử lại sau";
    private static final String INVALID_DATA = "Dữ liệu không hợp lệ";
    private static final String SEARCH_REQUIRE_LOGIN = "Bạn cần đăng nhập để tìm kiếm sách";

    @Autowired
    private BookService bookService;

    // Kiểm tra quyền admin
    private boolean isAdmin(HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        return userDTO != null && ADMIN_ROLE.equalsIgnoreCase(userDTO.getRole());
    }

    // Lấy tất cả sách có phân trang
    @GetMapping
    public ResponseEntity<?> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            HttpSession session) {

        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body(ACCESS_DENIED);
        }

        try {
            Page<BookDTO> booksPage = bookService.getBooksWithPagination(page, size);
            return ResponseEntity.ok(booksPage);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(SYSTEM_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body(ACCESS_DENIED);
        }

        try {
            BookDTO book = bookService.getBookById(id);
            return ResponseEntity.ok(book);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(BOOK_NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(SYSTEM_ERROR);
        }
    }

    @GetMapping("/All")
    public ResponseEntity<?> getAllsBooks(HttpSession session) {

        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body(ACCESS_DENIED);
        }

        try {
            List<BookDTO> books = bookService.getAllBooks();
            // Page<BookDTO> booksPage = bookService.getBooksWithPagination(page, size);
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(SYSTEM_ERROR);
        }
    }

    // Tìm kiếm sách theo tiêu đề

    // Tạo sách mới
    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody Book book, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body(ACCESS_DENIED);
        }

        try {
            Book createdBook = bookService.createBook(book);
            return ResponseEntity.ok(createdBook);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(INVALID_DATA + ": " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(SYSTEM_ERROR);
        }
    }

    // Cập nhật sách
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(
            @PathVariable Long id,
            @RequestBody Book book,
            HttpSession session) {

        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body(ACCESS_DENIED);
        }

        try {
            Book updatedBook = bookService.updateBook(id, book);
            return ResponseEntity.ok(updatedBook);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(BOOK_NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(SYSTEM_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBooks(
            @RequestParam String keyword,
            HttpSession session) {

        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body(ACCESS_DENIED);
        }

        try {
            List<BookDTO> results = bookService.searchBooksByTitle(keyword);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(SYSTEM_ERROR);
        }
    }

    // Xóa sách
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body(ACCESS_DENIED);
        }

        try {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(BOOK_NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(SYSTEM_ERROR);
        }
    }
}