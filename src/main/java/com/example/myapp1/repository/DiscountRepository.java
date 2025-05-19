package com.example.myapp1.repository;

import com.example.myapp1.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.*;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    // Bạn có thể thêm các phương thức tìm kiếm theo discountCode, bookId nếu cần
    // Discount findByDiscountCode(String discountCode);

    // Optional<Discount> getDiscountEntityById(Integer discountId);
    // discount = discountRepository.save(discount);
    // Optional<Discount> findByDiscountCode(String discountCode);

    boolean existsByDiscountCode(String discountCode);

    // Phân trang tất cả mã giảm giá
    Page<Discount> findAll(Pageable pageable);

    // Phân trang theo trạng thái đang hoạt động
    // Page<Discount> findByActiveTrue(Pageable pageable);

}
