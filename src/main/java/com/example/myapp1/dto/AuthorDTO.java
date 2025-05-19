package com.example.myapp1.dto;

import java.time.LocalDate;

public class AuthorDTO {
    private long authorId;
    private String authorName;
    private LocalDate dateOfBirth;

    // Constructors, Getters and Setters
    public AuthorDTO() {
    }

    public AuthorDTO(long authorId, String authorName, LocalDate dateOfBirth) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.dateOfBirth = dateOfBirth;
    }

    public static class Builder {
        private long authorId;
        private String authorName;
        private LocalDate dateOfBirth;

        public Builder authorId(long authorId) {
            this.authorId = authorId;
            return this;
        }

        public Builder authorName(String authorName) {
            this.authorName = authorName;
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public AuthorDTO build() {
            return new AuthorDTO(authorId, authorName, dateOfBirth);
        }
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
