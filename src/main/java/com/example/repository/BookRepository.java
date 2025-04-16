package com.example.repository;

import com.example.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Bạn có thể thêm các phương thức tìm kiếm theo yêu cầu
    Book findByTitle(String title);
}
