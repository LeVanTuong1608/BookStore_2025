package com.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Liên kết với bảng Users

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart; // Liên kết với bảng Carts

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "qr_code", nullable = false)
    private String qrCode;

    @Column(name = "status", nullable = false, columnDefinition = "NVARCHAR(50) DEFAULT 'pending'")
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "payment_date", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    private Date paymentDate;

    // Constructor mặc định
    public Payment() {
    }

    // Constructor có tham số
    public Payment(User user, Cart cart, BigDecimal amount, String qrCode, String status, Date paymentDate) {
        this.user = user;
        this.cart = cart;
        this.amount = amount;
        this.qrCode = qrCode;
        this.status = status;
        this.paymentDate = paymentDate;
    }

    // Getter và Setter
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    // @Override
    // public String toString() {
    // return "Payment{" +
    // "paymentId=" + paymentId +
    // ", user=" + user.getFullname() +
    // ", cart=" + cart.getCartId() +
    // ", amount=" + amount +
    // ", status='" + status + '\'' +
    // ", paymentDate=" + paymentDate +
    // '}';
    // }
}
