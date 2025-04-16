package com.example.service.impl;

import com.example.dto.CartDTO;
import com.example.model.Cart;
import com.example.repository.CartRepository;
import com.example.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public Cart getCartById(int id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Override
    public Cart addCart(CartDTO cartDTO) {
        Cart cart = new Cart();
        cart.setUser(new User(cartDTO.getUserId())); // Giả sử có User với ID
        cart.setBook(new Book(cartDTO.getBookId())); // Giả sử có Book với ID
        cart.setQuantity(cartDTO.getQuantity());
        cart.setStatus(cartDTO.getStatus());
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateCart(int id, CartDTO cartDTO) {
        return cartRepository.findById(id).map(cart -> {
            cart.setQuantity(cartDTO.getQuantity());
            cart.setStatus(cartDTO.getStatus());
            return cartRepository.save(cart);
        }).orElse(null);
    }

    @Override
    public void deleteCart(int id) {
        cartRepository.deleteById(id);
    }
}
