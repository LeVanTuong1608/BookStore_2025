package com.example.myapp1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;

    @Column(nullable = false)
    private String title;

    private String imageUrl;
    private String category;
    private double price;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author; // Liên kết với bảng Authors

    private String description;
    private String publisher;
    private int publicationYear;
    private String dimensions;

    // Constructors
    public Book() {
    }

    public Book(String title, String imageUrl, String category, double price, Author author, String description,
            String publisher, int publicationYear, String dimensions) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.category = category;
        this.price = price;
        this.author = author;
        this.description = description;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.dimensions = dimensions;
    }

    // ✅ Constructor cho trường hợp chỉ có ID
    public Book(long bookId) {
        this.bookId = bookId;
    }

    // Getters và Setters
    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }
}
