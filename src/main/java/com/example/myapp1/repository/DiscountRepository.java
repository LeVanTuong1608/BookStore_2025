package com.example.myapp1.repository;

import com.example.myapp1.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    // Bạn có thể thêm các phương thức tìm kiếm theo discountCode, bookId nếu cần
    Discount findByDiscountCode(String discountCode);
}
