package com.example.myapp1.dto;

import java.math.BigDecimal;
// import java.time.LocalDateTime;
import java.util.Date;

public class OrderDTO {
    private int orderId;
    private String fullName;
    private Long userId; // Changed from int to Long
    private Date orderDate;
    private BigDecimal totalAmount;
    private String status;
    private Integer discountId;

    // Constructors
    public OrderDTO() {
    }

    public OrderDTO(int orderId, Long userId, String fullName, Date orderDate, BigDecimal totalAmount, String status,
            Integer discountId) {
        this.orderId = orderId;
        this.userId = userId;
        this.fullName = fullName;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.discountId = discountId;
    }

    // Getters and

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }
}