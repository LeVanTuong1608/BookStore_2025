package com.example.myapp1.service;

import com.example.myapp1.dto.AuthorDTO;
import com.example.myapp1.model.Author;
import com.example.myapp1.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    // Convert Author entity to AuthorDTO
    private AuthorDTO convertToDTO(Author author) {
        return new AuthorDTO(author.getAuthorId(), author.getAuthorName(), author.getDateOfBirth());
    }

    // Get all authors
    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Get author by ID
    public AuthorDTO getAuthorById(long authorId) {
        Optional<Author> author = authorRepository.findById(authorId);
        return author.map(this::convertToDTO).orElse(null);
    }

    // Create a new author
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = new Author(authorDTO.getAuthorName(), authorDTO.getDateOfBirth());
        author = authorRepository.save(author);
        return convertToDTO(author);
    }

    // Update an existing author
    public AuthorDTO updateAuthor(long authorId, AuthorDTO authorDTO) {
        Optional<Author> authorOptional = authorRepository.findById(authorId);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            author.setAuthorName(authorDTO.getAuthorName());
            author.setDateOfBirth(authorDTO.getDateOfBirth());
            author = authorRepository.save(author);
            return convertToDTO(author);
        }
        return null;
    }

    // Delete an author
    public void deleteAuthor(long authorId) {
        authorRepository.deleteById(authorId);
    }
}
