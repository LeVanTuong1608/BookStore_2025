package com.example.service;

import com.example.model.Book;
import com.example.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(int id) {
        return bookRepository.findById(id);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(int id, Book updatedBook) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setImageUrl(updatedBook.getImageUrl());
            book.setCategory(updatedBook.getCategory());
            book.setPrice(updatedBook.getPrice());
            book.setAuthorId(updatedBook.getAuthorId());
            book.setDescription(updatedBook.getDescription());
            book.setPublisher(updatedBook.getPublisher());
            book.setPublicationYear(updatedBook.getPublicationYear());
            book.setDimensions(updatedBook.getDimensions());
            return bookRepository.save(book);
        }).orElse(null);
    }

    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }
}
