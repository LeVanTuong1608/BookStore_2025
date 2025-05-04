package com.example.myapp1.controller;

import com.example.myapp1.dto.DiscountDTO;
import com.example.myapp1.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// import java.util.Optional;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    // Lấy tất cả các discount
    @GetMapping
    public ResponseEntity<List<DiscountDTO>> getAllDiscounts() {
        List<DiscountDTO> discounts = discountService.getAllDiscounts();
        return ResponseEntity.ok(discounts);
    }

    // Lấy discount theo ID
    // @GetMapping("/{id}")
    // public ResponseEntity<DiscountDTO> getDiscountById(@PathVariable int id) {
    // Optional<DiscountDTO> discount = discountService.getDiscountById(id);
    // return discount.map(ResponseEntity::ok)
    // .orElseGet(() -> ResponseEntity.notFound().build());
    // }
    @GetMapping("/{id}")
    public ResponseEntity<DiscountDTO> getDiscountById(@PathVariable int id) {
        return discountService.getDiscountById(id);
    }

    // Tạo mới discount
    @PostMapping
    public ResponseEntity<DiscountDTO> createDiscount(@RequestBody DiscountDTO discountDTO) {
        DiscountDTO createdDiscount = discountService.createDiscount(discountDTO);
        return new ResponseEntity<>(createdDiscount, HttpStatus.CREATED);
    }

    // Cập nhật discount
    @PutMapping("/{id}")
    public ResponseEntity<DiscountDTO> updateDiscount(@PathVariable int id, @RequestBody DiscountDTO discountDTO) {
        DiscountDTO updatedDiscount = discountService.updateDiscount(id, discountDTO);
        return ResponseEntity.ok(updatedDiscount);
    }

    // Xóa discount
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable int id) {
        discountService.deleteDiscount(id);
        return ResponseEntity.noContent().build();
    }
}
