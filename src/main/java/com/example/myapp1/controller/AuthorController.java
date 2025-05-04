package com.example.myapp1.controller;

import com.example.myapp1.dto.AuthorDTO;
import com.example.myapp1.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    // Get all authors
    @GetMapping
    public List<AuthorDTO> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    // Get author by ID
    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable long authorId) {
        AuthorDTO authorDTO = authorService.getAuthorById(authorId);
        if (authorDTO != null) {
            return ResponseEntity.ok(authorDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new author
    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
        AuthorDTO createdAuthor = authorService.createAuthor(authorDTO);
        return ResponseEntity.status(201).body(createdAuthor);
    }

    // Update an existing author
    @PutMapping("/{authorId}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable long authorId, @RequestBody AuthorDTO authorDTO) {
        AuthorDTO updatedAuthor = authorService.updateAuthor(authorId, authorDTO);
        if (updatedAuthor != null) {
            return ResponseEntity.ok(updatedAuthor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an author
    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable long authorId) {
        authorService.deleteAuthor(authorId);
        return ResponseEntity.noContent().build();
    }
}
