package com.example.service;

import com.example.dto.DiscountDTO;
import com.example.model.Discount;

import java.util.List;

public interface DiscountService {
    List<Discount> getAllDiscounts();

    Discount getDiscountById(int id);

    Discount createDiscount(DiscountDTO discountDTO);

    Discount updateDiscount(int id, DiscountDTO discountDTO);

    void deleteDiscount(int id);
}
