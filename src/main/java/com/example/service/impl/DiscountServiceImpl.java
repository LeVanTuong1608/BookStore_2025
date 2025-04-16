package com.example.service.impl;

import com.example.dto.DiscountDTO;
import com.example.model.Discount;
import com.example.repository.DiscountRepository;
import com.example.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    @Override
    public Discount getDiscountById(int id) {
        return discountRepository.findById(id).orElse(null);
    }

    @Override
    public Discount createDiscount(DiscountDTO discountDTO) {
        Discount discount = new Discount();
        discount.setDiscountName(discountDTO.getDiscountName());
        discount.setDiscountCode(discountDTO.getDiscountCode());
        discount.setBook(new Book(discountDTO.getBookId())); // Giả sử có Book với ID
        discount.setMinOrderAmount(discountDTO.getMinOrderAmount());
        discount.setStartDate(discountDTO.getStartDate());
        discount.setEndDate(discountDTO.getEndDate());
        return discountRepository.save(discount);
    }

    @Override
    public Discount updateDiscount(int id, DiscountDTO discountDTO) {
        return discountRepository.findById(id).map(discount -> {
            discount.setDiscountName(discountDTO.getDiscountName());
            discount.setDiscountCode(discountDTO.getDiscountCode());
            discount.setBook(new Book(discountDTO.getBookId())); // Giả sử có Book với ID
            discount.setMinOrderAmount(discountDTO.getMinOrderAmount());
            discount.setStartDate(discountDTO.getStartDate());
            discount.setEndDate(discountDTO.getEndDate());
            return discountRepository.save(discount);
        }).orElse(null);
    }

    @Override
    public void deleteDiscount(int id) {
        discountRepository.deleteById(id);
    }
}
