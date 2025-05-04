package com.example.myapp1.repository;

import com.example.myapp1.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    // You can define custom query methods here if needed
}
