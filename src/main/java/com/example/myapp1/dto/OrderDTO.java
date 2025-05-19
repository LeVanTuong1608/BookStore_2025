
package com.example.myapp1.dto;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDTO {
    private int orderId;
    private Long userId;
    private String fullName;
    private Date orderDate;
    private BigDecimal totalAmount;
    private String status;
    private Integer discountId;
    // private String fullName;
    // private Long userId; // Changed from int to Long
    // private Date orderDate;
    // private BigDecimal totalAmount;
    // private String status;
    // private Integer discountId;

    // Static inner class for StatusRequest
    public static class StatusRequest {
        private String status;

        public StatusRequest() {
        }

        public StatusRequest(String status) {
            this.status = status;
        }

        // Getters and Setters
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    // Constructors
    public OrderDTO() {
    }

    public OrderDTO(int orderId, Long userId, String fullName,
            Date orderDate, BigDecimal totalAmount,
            String status, Integer discountId) {
        this.orderId = orderId;
        this.userId = userId;
        this.fullName = fullName;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.discountId = discountId;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int orderId;
        private Long userId;
        private String fullName;
        private Date orderDate;
        private BigDecimal totalAmount;
        private String status;
        private Integer discountId;

        public Builder orderId(int orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder orderDate(Date orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder totalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder discountId(Integer discountId) {
            this.discountId = discountId;
            return this;
        }
        // Other builder methods...

        public OrderDTO build() {
            return new OrderDTO(orderId, userId, fullName, orderDate,
                    totalAmount, status, discountId);
        }
    }

    // Getters and Setters
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

    // Other getters and setters...

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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