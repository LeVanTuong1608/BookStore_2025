package com.example.service;

import com.example.dto.BookDTO;
import com.example.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    Book getBookById(long id);

    Book getBookByTitle(String title);

    Book createBook(BookDTO bookDTO);

    Book updateBook(long id, BookDTO bookDTO);

    void deleteBook(long id);
}
