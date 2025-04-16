package com.example.service;

import com.example.dto.CartDTO;
import com.example.model.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getAllCarts();

    Cart getCartById(int id);

    Cart addCart(CartDTO cartDTO);

    Cart updateCart(int id, CartDTO cartDTO);

    void deleteCart(int id);
}
