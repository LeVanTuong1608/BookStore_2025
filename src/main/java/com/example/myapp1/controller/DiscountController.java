
package com.example.myapp1.controller;

import com.example.myapp1.dto.DiscountDTO;
import com.example.myapp1.dto.UserDTO;
import com.example.myapp1.model.User;
import com.example.myapp1.service.DiscountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.List;

@RestController
@RequestMapping("/admin/api/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

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

    // Lấy tất cả các discount
    @GetMapping
    public ResponseEntity<?> getAllDiscounts(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body("Bạn không có quyền truy cập.");
        }
        // List<DiscountDTO> discounts = discountService.getAllDiscounts();
        Pageable pageable = PageRequest.of(page, size);
        Page<DiscountDTO> discounts = discountService.getAllDiscounts(pageable);
        return ResponseEntity.ok(discounts);
    }

    // Lấy discount theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getDiscountById(@PathVariable int id, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body("Bạn không có quyền truy cập.");
        }
        try {
            DiscountDTO discount = discountService.getDiscountById(id);
            return ResponseEntity.ok(discount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy mã giảm giá với id: " + id);
        }
        // return discountService.getDiscountById(id);
    }

    // Tạo mới discount
    @PostMapping
    public ResponseEntity<?> createDiscount(@RequestBody DiscountDTO discountDTO,
            HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body("Bạn không có quyền tạo mã giảm giá.");
        }
        DiscountDTO createdDiscount = discountService.createDiscount(discountDTO);
        return new ResponseEntity<>(createdDiscount, HttpStatus.CREATED);
    }

    // Cập nhật discount
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDiscount(@PathVariable int id, @RequestBody DiscountDTO discountDTO,
            HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body("Bạn không có quyền cập nhật mã giảm giá.");
        }
        try {
            DiscountDTO updatedDiscount = discountService.updateDiscount(id, discountDTO);
            return ResponseEntity.ok(updatedDiscount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy mã giảm giá với id: " + id);
        }
        // DiscountDTO updatedDiscount = discountService.updateDiscount(id,
        // discountDTO);
        // return ResponseEntity.ok(updatedDiscount);
    }

    // Xóa discount
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDiscount(@PathVariable int id, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(403).body("Bạn không có quyền xóa mã giảm giá.");
        }
        try {
            discountService.deleteDiscount(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy mã giảm giá với id: " + id);
        }

        // discountService.deleteDiscount(id);
        // return ResponseEntity.noContent().build();
    }
}
