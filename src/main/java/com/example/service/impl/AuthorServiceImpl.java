package com.example.service.impl;

import com.example.dto.AuthorDTO;
import com.example.model.Author;
import com.example.repository.AuthorRepository;
import com.example.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Author createAuthor(AuthorDTO authorDTO) {
        // Tạo mới đối tượng Author từ AuthorDTO
        Author author = new Author(authorDTO.getAuthorName(), authorDTO.getDateOfBirth());
        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(Long id, AuthorDTO authorDTO) {
        // Cập nhật đối tượng Author
        return authorRepository.findById(id).map(author -> {
            author.setAuthorName(authorDTO.getAuthorName());
            author.setDateOfBirth(authorDTO.getDateOfBirth());
            return authorRepository.save(author);
        }).orElse(null); // Trả về null nếu không tìm thấy tác giả
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
