package com.example.repository;

import com.example.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    // Có thể thêm các phương thức tìm kiếm theo yêu cầu, ví dụ:
    // List<Cart> findByUser(User user);
}
