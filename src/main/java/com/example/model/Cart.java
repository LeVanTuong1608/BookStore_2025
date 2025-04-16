package com.example.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Liên kết với bảng Users

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book; // Liên kết với bảng Books

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    private Date createdAt;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "status", nullable = false, columnDefinition = "NVARCHAR(50) DEFAULT 'pending'")
    private String status;

    // Constructor mặc định
    public Cart() {
    }

    // Constructor có tham số
    public Cart(User user, Book book, int quantity, String status) {
        this.user = user;
        this.book = book;
        this.quantity = quantity;
        this.status = status;
        this.createdAt = new Date(); // Gán thời gian hiện tại cho createdAt
    }

    // Getter và Setter
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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

    // @Override
    // public String toString() {
    // return "Cart{" +
    // "cartId=" + cartId +
    // ", user=" + user.getFullname() +
    // ", createdAt=" + createdAt +
    // ", quantity=" + quantity +
    // ", status='" + status + '\'' +
    // '}';
    // }
}
