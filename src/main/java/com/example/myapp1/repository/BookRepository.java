package com.example.myapp1.repository;

// import com.example.myapp1.dto.BestSellingBookDTO;
import com.example.myapp1.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;
import org.springframework.data.domain.*;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần

    // Tìm sách theo tên (tương đối, không phân biệt hoa thường)
    List<Book> findByTitleContainingIgnoreCase(String title);

    Page<Book> findAllByOrderByTitleAsc(Pageable pageable);

    Page<Book> findAll(Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

}
