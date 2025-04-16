package com.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int discountId;

    @Column(name = "discount_name", nullable = false)
    private String discountName;

    @Column(name = "discount_code", nullable = false, unique = true)
    private String discountCode;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book; // Liên kết với bảng Books

    @Column(name = "min_order_amount", nullable = false, columnDefinition = "DECIMAL(10, 2) DEFAULT 0")
    private BigDecimal minOrderAmount;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    // Constructor mặc định
    public Discount() {
    }

    // Constructor có tham số
    public Discount(String discountName, String discountCode, Book book, BigDecimal minOrderAmount, Date startDate,
            Date endDate) {
        this.discountName = discountName;
        this.discountCode = discountCode;
        this.book = book;
        this.minOrderAmount = minOrderAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getter và Setter
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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

    // @Override
    // public String toString() {
    // return "Discount{" +
    // "discountId=" + discountId +
    // ", discountName='" + discountName + '\'' +
    // ", discountCode='" + discountCode + '\'' +
    // ", minOrderAmount=" + minOrderAmount +
    // ", startDate=" + startDate +
    // ", endDate=" + endDate +
    // '}';
    // }
}
