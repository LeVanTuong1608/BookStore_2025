package com.example.myapp1.repository;

// import com.example.myapp1.model.Author;
import com.example.myapp1.model.Order;
import com.example.myapp1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.*;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // Custom query methods can be added here
    // Trong OrderRepository.java
    // Page<Order> findByUser_Id(Long userId, Pageable pageable);

    Page<Order> findByUser(User user, Pageable pageable);

    Page<Order> findAll(Pageable pageable);

}