package com.example.myapp1.dto;

import java.math.BigDecimal;
import java.util.Date;

public class DiscountDTO {

    private int discountId;
    private String discountName;
    private String discountCode;
    private String title;
    private Long bookId; // Chuyển kiểu từ int sang Long
    private BigDecimal minOrderAmount;
    private Date startDate;
    private Date endDate;

    // Constructors
    public DiscountDTO() {
    }

    public DiscountDTO(int discountId, String discountName, String discountCode, Long bookId, String title,
            BigDecimal minOrderAmount, Date startDate, Date endDate) {
        this.discountId = discountId;
        this.discountName = discountName;
        this.discountCode = discountCode;
        this.bookId = bookId;
        this.title = title;
        this.minOrderAmount = minOrderAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public String getTitle() {
        return title;
    }

    public void setTile(String title) {
        this.title = title;
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

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
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
