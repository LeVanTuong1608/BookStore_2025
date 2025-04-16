package com.example.service;

import com.example.dto.AuthorDTO;
import com.example.model.Author;
import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> getAllAuthors();

    Optional<Author> getAuthorById(Long id);

    Author createAuthor(AuthorDTO authorDTO);

    Author updateAuthor(Long id, AuthorDTO authorDTO);

    void deleteAuthor(Long id);
}
