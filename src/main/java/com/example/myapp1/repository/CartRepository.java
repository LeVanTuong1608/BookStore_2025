package com.example.myapp1.repository;

import com.example.myapp1.model.Book;
// import com.example.myapp1.dto.BestSellingBookDTO;
import com.example.myapp1.model.Cart;
import com.example.myapp1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.*;

import java.util.*;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUser(User user);

    List<Cart> findByUserAndStatus(User user, String status);

    Optional<Cart> findByUserAndBookAndStatus(User user, Book book, String status);

    Page<Cart> findByUser(User user, Pageable pageable); // Tìm giỏ hàng của người dùng với phân trang

    void deleteByUser(User user);

    // Phân trang giỏ hàng theo user và status
    Page<Cart> findByUserAndStatus(User user, String status, Pageable pageable);
    // @Query("SELECT new com.example.myapp1.dto.BestSellingBookDTO(b.id, b.title,
    // SUM(c.quantity), SUM(c.quantity * b.price)) FROM Cart c JOIN c.book b GROUP
    // BY b.id ORDER BY SUM(c.quantity) DESC")
    // Page<BestSellingBookDTO> findTop10BestSellingBooks(PageRequest pageRequest);

    // @Query("SELECT new com.example.myapp1.dto.BestSellingBookDTO(b.id, b.title,
    // SUM(c.quantity), SUM(c.quantity * b.price)) FROM Cart c JOIN c.book b WHERE
    // c.date BETWEEN :startDate AND :endDate GROUP BY b.id ORDER BY SUM(c.quantity)
    // DESC")
    // Page<BestSellingBookDTO> findBestSellingBooksByDateRange(@Param("startDate")
    // Date startDate,
    // @Param("endDate") Date endDate, PageRequest pageRequest);

    // @Query("SELECT c.book AS book, SUM(c.quantity) AS totalQuantity " +
    // "FROM Cart c " +
    // "WHERE c.status = 'paid' " +
    // "GROUP BY c.book " +
    // "ORDER BY totalQuantity DESC")
    // List<Object[]> findTopSellingBooks(Pageable pageable);
    // @Query("SELECT new com.example.myapp1.dto.BestSellingBookDTO(b.id, b.title,
    // SUM(c.quantity)) " +
    // "FROM Cart c JOIN c.book b " +
    // "WHERE c.status = 'paid' " +
    // "GROUP BY b.id, b.title " +
    // "ORDER BY SUM(c.quantity) DESC")
    // Page<BestSellingBookDTO> findTopSellingBooks(Pageable pageable);

}
