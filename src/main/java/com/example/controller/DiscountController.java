package com.example.controller;

import com.example.dto.DiscountDTO;
import com.example.model.Discount;
import com.example.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @GetMapping
    public ResponseEntity<List<Discount>> getAllDiscounts() {
        return ResponseEntity.ok(discountService.getAllDiscounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable int id) {
        Discount discount = discountService.getDiscountById(id);
        if (discount != null) {
            return ResponseEntity.ok(discount);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Discount> createDiscount(@RequestBody DiscountDTO discountDTO) {
        Discount created = discountService.createDiscount(discountDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Discount> updateDiscount(@PathVariable int id, @RequestBody DiscountDTO discountDTO) {
        Discount updated = discountService.updateDiscount(id, discountDTO);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable int id) {
        discountService.deleteDiscount(id);
        return ResponseEntity.noContent().build();
    }
}
