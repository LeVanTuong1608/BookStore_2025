package com.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Liên kết với bảng Users

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    private Date orderDate;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "status", nullable = false, columnDefinition = "NVARCHAR(50) DEFAULT 'pending'")
    private String status;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount; // Liên kết với bảng Discounts (có thể NULL)

    // Constructor mặc định
    public Order() {
    }

    // Constructor với tham số
    public Order(User user, Date orderDate, BigDecimal totalAmount, String status, Discount discount) {
        this.user = user;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.discount = discount;
    }

    // Getter và Setter
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    // @Override
    // public String toString() {
    // return "Order{" +
    // "orderId=" + orderId +
    // ", user=" + user.getFullname() +
    // ", orderDate=" + orderDate +
    // ", totalAmount=" + totalAmount +
    // ", status='" + status + '\'' +
    // ", discount=" + (discount != null ? discount.getDiscountName() : "None") +
    // '}';
    // }
}
