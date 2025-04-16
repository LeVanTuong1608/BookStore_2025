package com.example.controller;

import com.example.dto.AuthorDTO;
import com.example.model.Author;
import com.example.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO dto) {
        Author created = authorService.createAuthor(dto);
        AuthorDTO response = convertToDTO(created);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO dto) {
        Author updated = authorService.updateAuthor(id, dto);
        if (updated != null) {
            AuthorDTO response = convertToDTO(updated);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    private AuthorDTO convertToDTO(Author author) {
        return new AuthorDTO(author.getAuthorName(), author.getDateOfBirth());
    }
}
