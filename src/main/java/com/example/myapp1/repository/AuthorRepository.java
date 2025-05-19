package com.example.myapp1.repository;

import com.example.myapp1.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import org.springframework.data.domain.*;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    // You can define custom query methods here if needed
    List<Author> findByAuthorNameContainingIgnoreCase(String name);

    // Tìm tác giả theo tên có phân trang
    Page<Author> findByAuthorNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Author> findAll(Pageable pageable);

}
