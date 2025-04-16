package com.example.service.impl;

import com.example.dto.BookDTO;
import com.example.model.Book;
import com.example.repository.BookRepository;
import com.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public Book createBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setImageUrl(bookDTO.getImageUrl());
        book.setCategory(bookDTO.getCategory());
        book.setPrice(bookDTO.getPrice());
        book.setAuthorId(bookDTO.getAuthorId());
        book.setDescription(bookDTO.getDescription());
        book.setPublisher(bookDTO.getPublisher());
        book.setPublicationYear(bookDTO.getPublicationYear());
        book.setDimensions(bookDTO.getDimensions());
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(long id, BookDTO bookDTO) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(bookDTO.getTitle());
            book.setImageUrl(bookDTO.getImageUrl());
            book.setCategory(bookDTO.getCategory());
            book.setPrice(bookDTO.getPrice());
            book.setAuthorId(bookDTO.getAuthorId());
            book.setDescription(bookDTO.getDescription());
            book.setPublisher(bookDTO.getPublisher());
            book.setPublicationYear(bookDTO.getPublicationYear());
            book.setDimensions(bookDTO.getDimensions());
            return bookRepository.save(book);
        }).orElse(null);
    }

    @Override
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }
}
