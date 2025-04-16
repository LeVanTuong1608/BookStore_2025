package com.example.dto;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDTO {
    private int orderId;
    private int userId;
    private Date orderDate;
    private BigDecimal totalAmount;
    private String status;
    private int discountId;

    // Constructor, Getters, Setters
    public OrderDTO() {
    }

    public OrderDTO(int orderId, int userId, Date orderDate, BigDecimal totalAmount, String status, int discountId) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.discountId = discountId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }
}
