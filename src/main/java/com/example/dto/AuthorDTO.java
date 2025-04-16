package com.example.dto;

import java.time.LocalDate;

public class AuthorDTO {

    private String authorName;
    private LocalDate dateOfBirth;

    public AuthorDTO(String authorName, LocalDate dateOfBirth) {
        this.authorName = authorName;
        this.dateOfBirth = dateOfBirth;
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
