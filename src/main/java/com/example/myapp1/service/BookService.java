package com.example.myapp1.service;

import com.example.myapp1.model.Author;
import com.example.myapp1.model.Book;
import com.example.myapp1.dto.AuthorDTO;
import com.example.myapp1.dto.BookDTO;
import com.example.myapp1.repository.AuthorRepository;
import com.example.myapp1.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private AuthorRepository authorRepository;

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

    // public Optional<Book> getBookById(long id) {
    // return bookRepository.findById(id);
    // }
    // public BookDTO getBookById(long id) {
    // Optional<Book> optionalBook = bookRepository.findById(id);
    // if (optionalBook.isPresent()) {
    // Book book = optionalBook.get();
    // return convertToDTO(book); // sử dụng hàm convertToDTO ở dưới
    // }
    // throw new IllegalArgumentException("Book not found");
    // }

    public BookDTO getBookById(long id) {
        return bookRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
    }

    // Phương thức phân trang trả về Page<BookDTO>
    public Page<BookDTO> getBooksWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("bookId").ascending());
        Page<Book> bookPage = bookRepository.findAll(pageable);

        return bookPage.map(this::convertToDTO);
    }

    // Phương thức tìm kiếm với phân trang
    public Page<BookDTO> searchBooksByTitleWithPagination(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("bookId").ascending());
        Page<Book> bookPage = bookRepository.findByTitleContainingIgnoreCase(title, pageable);

        return bookPage.map(this::convertToDTO);
    }

    // Phương thức chuyển đổi từ Book sang BookDTO
    // private BookDTO convertToBookDTO(Book book) {
    // BookDTO dto = new BookDTO();
    // // dto.setBookId(book.getBookId());
    // dto.setTitle(book.getTitle());
    // dto.setImageUrl(book.getImageUrl());
    // dto.setCategory(book.getCategory());
    // dto.setPrice(book.getPrice());
    // dto.setAuthorName(book.getAuthor().getAuthorName());
    // dto.setDescription(book.getDescription());
    // dto.setPublisher(book.getPublisher());
    // dto.setPublicationYear(book.getPublicationYear());
    // dto.setDimensions(book.getDimensions());
    // // dto.setAuthorId(book.getAuthor().getAuthorId()); // Nếu bạn muốn thêm
    // authorId trong DTO
    // return dto;
    // }

    //
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    private BookDTO convertToDTO(Book book) {
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
    }

    // public Map<String, Object> getBooksPage(int page, int size) {
    // Pageable pageable = PageRequest.of(page, size,
    // Sort.by("bookId").ascending());
    // Page<Book> bookPage = bookRepository.findAll(pageable);

    // List<BookDTO> bookDTOs = bookPage.getContent().stream().map(book -> {
    // BookDTO dto = new BookDTO();
    // dto.setBookId(book.getBookId());
    // dto.setTitle(book.getTitle());
    // dto.setImageUrl(book.getImageUrl());
    // dto.setCategory(book.getCategory());
    // dto.setPrice(book.getPrice());
    // dto.setAuthorName(book.getAuthor().getAuthorName());
    // dto.setDescription(book.getDescription());
    // dto.setPublisher(book.getPublisher());
    // dto.setPublicationYear(book.getPublicationYear());
    // dto.setDimensions(book.getDimensions());
    // return dto;
    // }).collect(Collectors.toList());

    // Map<String, Object> response = new HashMap<>();
    // response.put("books", bookDTOs);
    // response.put("currentPage", bookPage.getNumber());
    // response.put("totalItems", bookPage.getTotalElements());
    // response.put("totalPages", bookPage.getTotalPages());

    // return response;
    // }

    // public Book createBook(BookDTO dto) {
    // // Lấy author từ DB bằng authorId
    // Author author = authorRepository.findById(Long.valueOf(dto.getAuthorId()))
    // .orElseThrow(() -> new RuntimeException("Author not found"));

    // // Tạo đối tượng Book từ dữ liệu trong DTO
    // Book book = new Book(
    // dto.getTitle(),
    // dto.getImageUrl(),
    // dto.getCategory(),
    // dto.getPrice(),
    // author, // gán object Author
    // dto.getDescription(),
    // dto.getPublisher(),
    // dto.getPublicationYear(),
    // dto.getDimensions());

    // return bookRepository.save(book);
    // }

    public Book updateBook(long id, Book bookDetails) {
        Optional<Book> bookData = bookRepository.findById(id);
        if (bookData.isPresent()) {
            Book book = bookData.get();
            book.setTitle(bookDetails.getTitle());
            book.setImageUrl(bookDetails.getImageUrl());
            book.setCategory(bookDetails.getCategory());
            book.setPrice(bookDetails.getPrice());
            // book.setAuthor(bookDetails.getAuthor());
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

    public List<BookDTO> searchBooksByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
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

    public BookDTO getBookDTOById(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
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
        }
        return null;
    }

}
