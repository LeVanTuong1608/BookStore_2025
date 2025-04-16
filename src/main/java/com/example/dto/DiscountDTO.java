package com.example.dto;

import java.math.BigDecimal;
import java.util.Date;

public class DiscountDTO {
    private int discountId;
    private String discountName;
    private String discountCode;
    private int bookId;
    private BigDecimal minOrderAmount;
    private Date startDate;
    private Date endDate;

    // Constructor, Getters, Setters
    public DiscountDTO() {
    }

    public DiscountDTO(int discountId, String discountName, String discountCode, int bookId, BigDecimal minOrderAmount,
            Date startDate, Date endDate) {
        this.discountId = discountId;
        this.discountName = discountName;
        this.discountCode = discountCode;
        this.bookId = bookId;
        this.minOrderAmount = minOrderAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public BigDecimal getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(BigDecimal minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
