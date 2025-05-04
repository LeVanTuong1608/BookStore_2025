package com.example.myapp1.service;

import com.example.myapp1.model.Book;
import com.example.myapp1.dto.BookDTO;
import com.example.myapp1.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(book -> {
            BookDTO dto = new BookDTO();
            dto.setBookId(book.getBookId());
            dto.setTitle(book.getTitle());
            dto.setImageUrl(book.getImageUrl());
            dto.setCategory(book.getCategory());
            dto.setPrice(book.getPrice());
            dto.setAuthorName(book.getAuthor().getAuthorName());
            dto.setDescription(book.getDescription());
            dto.setPublisher(book.getPublisher());
            dto.setPublicationYear(book.getPublicationYear());
            dto.setDimensions(book.getDimensions());
            return dto;
        }).collect(Collectors.toList());
    }

    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(long id, Book bookDetails) {
        Optional<Book> bookData = bookRepository.findById(id);
        if (bookData.isPresent()) {
            Book book = bookData.get();
            book.setTitle(bookDetails.getTitle());
            book.setImageUrl(bookDetails.getImageUrl());
            book.setCategory(bookDetails.getCategory());
            book.setPrice(bookDetails.getPrice());
            book.setAuthor(bookDetails.getAuthor());
            book.setDescription(bookDetails.getDescription());
            book.setPublisher(bookDetails.getPublisher());
            book.setPublicationYear(bookDetails.getPublicationYear());
            book.setDimensions(bookDetails.getDimensions());
            return bookRepository.save(book);
        }
        return null;
    }

    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }
}
