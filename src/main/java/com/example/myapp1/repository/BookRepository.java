package com.example.myapp1.repository;

import com.example.myapp1.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần
}
