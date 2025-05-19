package com.example.myapp1.repository;

// import com.example.myapp1.dto.BestSellingBookDTO;
// import com.example.myapp1.dto.RevenueStatsDTO;
import com.example.myapp1.model.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
// import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
// import org.springframework.data.domain.Page;

import java.util.*;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Integer> {

  // @Query(value = """
  // SELECT DATE(p.payment_date) as period, SUM(p.amount)
  // FROM payments p
  // WHERE p.status = 'success'
  // AND YEARWEEK(p.payment_date, 1) = YEARWEEK(CURDATE(), 1)
  // GROUP BY DATE(p.payment_date)
  // ORDER BY DATE(p.payment_date)
  // """, nativeQuery = true)
  // List<Object[]> getWeeklyRevenueByDay();

  // @Query(value = """
  // SELECT
  // CONCAT(MIN(DATE(p.payment_date)), ' ~ ', MAX(DATE(p.payment_date))) as
  // period,
  // SUM(p.amount)
  // FROM payments p
  // WHERE p.status = 'success'
  // AND MONTH(p.payment_date) = MONTH(CURDATE())
  // AND YEAR(p.payment_date) = YEAR(CURDATE())
  // GROUP BY FLOOR((DAY(p.payment_date) - 1) / 5)
  // ORDER BY MIN(DATE(p.payment_date))
  // """, nativeQuery = true)
  // List<Object[]> getMonthlyRevenueGroupedBy5Days();

  // @Query(value = """
  // SELECT DATE_FORMAT(p.payment_date, '%Y-%m') as period, SUM(p.amount)
  // FROM payments p
  // WHERE p.status = 'success'
  // AND YEAR(p.payment_date) = YEAR(CURDATE())
  // GROUP BY MONTH(p.payment_date)
  // ORDER BY MONTH(p.payment_date)
  // """, nativeQuery = true)
  // List<Object[]> getYearlyRevenueByMonth();

  // Phương thức mới sử dụng DTO
  // @Query("SELECT NEW com.example.myapp1.dto.BestSellingBookDTO(" +
  // "b.bookId, b.title, SUM(c.quantity), SUM(b.price * c.quantity)) " +
  // "FROM Cart c JOIN c.book b " +
  // "WHERE c.status = 'paid' " +
  // "GROUP BY b.bookId, b.title " +
  // "ORDER BY SUM(c.quantity) DESC")
  // List<BestSellingBookDTO> findBestSellingBooks();

  // Phiên bản có lọc theo khoảng thời gian
  // @Query("SELECT NEW com.example.myapp1.dto.BestSellingBookDTO(" +
  // "b.bookId, b.title, SUM(c.quantity), SUM(b.price * c.quantity)) " +
  // "FROM Cart c JOIN c.book b " +
  // "WHERE c.status = 'paid' AND c.createdAt BETWEEN :startDate AND :endDate " +
  // "GROUP BY b.bookId, b.title " +
  // "ORDER BY SUM(c.quantity) DESC")
  // List<BestSellingBookDTO> findBestSellingBooksByDateRange(
  // @Param("startDate") Date startDate,
  // @Param("endDate") Date endDate);

  // Phiên bản phân trang
  // @Query("SELECT NEW com.example.myapp1.dto.BestSellingBookDTO(" +
  // "b.bookId, b.title, SUM(c.quantity), SUM(b.price * c.quantity)) " +
  // "FROM Cart c JOIN c.book b " +
  // "WHERE c.status = 'paid' " +
  // "GROUP BY b.bookId, b.title " +
  // "ORDER BY SUM(c.quantity) DESC")
  // Page<BestSellingBookDTO> findBestSellingBooks(Pageable pageable);

  // @Query("SELECT b.id, b.title, SUM(p.quantity), SUM(p.totalPrice) " +
  // "FROM Payment p " +
  // "JOIN p.book b " +
  // "GROUP BY b.id, b.title " +
  // "ORDER BY SUM(p.quantity) DESC")
  // List<Object[]> getBestSellingBooksWithoutTimeFilter();
  // @Query("SELECT b.id, b.title, SUM(c.quantity), SUM(c.totalPrice) " +
  // "FROM Cart c " +
  // "JOIN c.book b " +
  // "WHERE c.status = 'paid' " +
  // "GROUP BY b.id, b.title " +
  // "ORDER BY SUM(c.quantity) DESC")
  // List<Object[]> getBestSellingBooksFromPaidCarts();

  // @Query("SELECT NEW com.example.myapp1.dto.RevenueStatsDTO(" +
  // "FUNCTION('DATE_FORMAT', p.paymentDate, :pattern), SUM(p.amount)) " +
  // "FROM Payment p " +
  // "WHERE p.status = 'success' " +
  // "GROUP BY FUNCTION('DATE_FORMAT', p.paymentDate, :pattern) " +
  // "ORDER BY FUNCTION('DATE_FORMAT', p.paymentDate, :pattern) ASC")
  // List<RevenueStatsDTO> getRevenueGroupedByPeriod(@Param("pattern") String
  // pattern);

  // @Query("SELECT NEW com.example.myapp1.dto.BestSellingBookDTO(" +
  // "b.bookId, b.title, SUM(c.quantity), SUM(b.price * c.quantity)) " +
  // "FROM Cart c JOIN c.book b " +
  // "WHERE c.status = 'paid' " +
  // "GROUP BY b.bookId, b.title " +
  // "ORDER BY SUM(c.quantity) DESC")
  // List<BestSellingBookDTO> findBestSellingBooks();

  // @Query("SELECT NEW com.example.myapp1.dto.BestSellingBookDTO(" +
  // "b.bookId, b.title, SUM(c.quantity), SUM(b.price * c.quantity)) " +
  // "FROM Cart c JOIN c.book b " +
  // "WHERE c.status = 'paid' " +
  // "GROUP BY b.bookId, b.title " +
  // "ORDER BY SUM(c.quantity) DESC")
  // Page<BestSellingBookDTO> findBestSellingBooks(Pageable pageable);

}
