package com.example.dto;

import java.util.Date;

public class CartDTO {
    private int cartId;
    private int userId;
    private int bookId;
    private Date createdAt;
    private int quantity;
    private String status;

    // Constructor, Getters, Setters
    public CartDTO() {
    }

    public CartDTO(int cartId, int userId, int bookId, Date createdAt, int quantity, String status) {
        this.cartId = cartId;
        this.userId = userId;
        this.bookId = bookId;
        this.createdAt = createdAt;
        this.quantity = quantity;
        this.status = status;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
