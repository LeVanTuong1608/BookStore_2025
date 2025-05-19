
package com.example.myapp1.controller;

import com.example.myapp1.dto.AuthorDTO;
import com.example.myapp1.dto.UserDTO;
// import com.example.myapp1.dto.UserDTO;
import com.example.myapp1.model.User;
import com.example.myapp1.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import jakarta.servlet.http.HttpSession;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    // Hàm tiện ích kiểm tra đã đăng nhập và có vai trò admin chưa
    private boolean isAdmin(HttpSession session) {
        // User user = (User) session.getAttribute("user");
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO == null) {
            System.out.println("Không có thông tin người dùng trong session.");
            return false;
        }
        if ("admin".equalsIgnoreCase(userDTO.getRole())) {
            System.out.println("Người dùng có quyền admin.");
            return true;
        } else {
            System.out.println("Người dùng không có quyền admin.");
            // System.out.println("User role: " + user.getRole());
            return false;
        }
        // return user != null && "admin".equalsIgnoreCase(user.getRole());
    }

    // Get all authors
    @GetMapping
    public ResponseEntity<?> getAllAuthors(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpSession session) {
        // User user = (User) session.getAttribute("user");

        // if (user != null) {
        // System.out.println("User role: " + user.getRole()); // Log thông tin role
        // } else {
        // System.out.println("User is not logged in.");
        // }
        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body("Bạn không có quyền truy cập.");
        }
        // Page<AuthorDTO> authorsPage = authorService.getAuthorsWithPagination(page,
        // size);
        // return ResponseEntity.ok(authorsPage);
        try {
            Page<AuthorDTO> authorsPage = authorService.getAuthorsWithPagination(page, size);
            return ResponseEntity.ok(authorsPage);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi hệ thống, vui lòng thử lại sau.");
        }
        // List<AuthorDTO> authors = authorService.getAllAuthors();
        // List<AuthorDTO> authors = authorService.getAuthorsWithPagination(page, size);
        // return ResponseEntity.ok(authors);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAuthorsWithoutPagination(HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body("Bạn không có quyền truy cập.");
        }
        try {
            List<AuthorDTO> authors = authorService.getAllAuthors();
            return ResponseEntity.ok(authors);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi hệ thống, vui lòng thử lại sau.");
        }
    }

    // Get author by ID
    @GetMapping("/{authorId}")
    public ResponseEntity<?> getAuthorById(@PathVariable long authorId,
            HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body("Bạn không có quyền truy cập.");
        }
        // AuthorDTO authorDTO = authorService.getAuthorById(authorId);
        // if (authorDTO != null) {
        // return ResponseEntity.ok(authorDTO);
        // } else {
        // return ResponseEntity.notFound().build();
        // }
        try {
            AuthorDTO authorDTO = authorService.getAuthorById(authorId);
            if (authorDTO != null) {
                return ResponseEntity.ok(authorDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi hệ thống, vui lòng thử lại sau.");
        }
    }

    // Create a new author
    @PostMapping
    public ResponseEntity<?> createAuthor(@RequestBody AuthorDTO authorDTO,
            HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body("Bạn không có quyền tạo tác giả.");
        }
        try {
            AuthorDTO createdAuthor = authorService.createAuthor(authorDTO);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdAuthor.getAuthorId())
                    .toUri();
            return ResponseEntity.created(location).body(createdAuthor);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi hệ thống, vui lòng thử lại sau.");
        }
        // AuthorDTO createdAuthor = authorService.createAuthor(authorDTO);
        // return ResponseEntity.status(201).body(createdAuthor);
    }

    // Update an existing author
    @PutMapping("/{authorId}")
    public ResponseEntity<?> updateAuthor(@PathVariable long authorId,
            @RequestBody AuthorDTO authorDTO,
            HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body("Bạn không có quyền cập nhật tác giả.");
        }
        // AuthorDTO updatedAuthor = authorService.updateAuthor(authorId, authorDTO);
        // if (updatedAuthor != null) {
        // return ResponseEntity.ok(updatedAuthor);
        // } else {
        // return ResponseEntity.notFound().build();
        // }
        try {
            AuthorDTO updatedAuthor = authorService.updateAuthor(authorId, authorDTO);
            if (updatedAuthor != null) {
                return ResponseEntity.ok(updatedAuthor);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi hệ thống, vui lòng thử lại sau.");
        }
    }

    // Delete an author
    @DeleteMapping("/{authorId}")
    public ResponseEntity<?> deleteAuthor(@PathVariable long authorId,
            HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body("Bạn không có quyền xóa tác giả.");
        }
        try {
            AuthorDTO author = authorService.getAuthorById(authorId);
            if (author != null) {
                authorService.deleteAuthor(authorId);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi hệ thống, vui lòng thử lại sau.");
        }
        // AuthorDTO author = authorService.getAuthorById(authorId);
        // if (author != null) {
        // authorService.deleteAuthor(authorId);
        // return ResponseEntity.noContent().build();
        // } else {
        // return ResponseEntity.notFound().build();
        // }
    }
}
