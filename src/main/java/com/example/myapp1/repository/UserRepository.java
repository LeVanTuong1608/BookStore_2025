package com.example.myapp1.repository;

import com.example.myapp1.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // ======== Tìm kiếm ========
    Optional<User> findByEmail(String email);

    Optional<User> findByPhonenumber(String phonenumber);

    List<User> findByFullname(String fullname);

    List<User> findByFullnameContaining(String keyword);

    Optional<User> findByEmailAndPassword(String email, String password); // Không nên dùng nếu mật khẩu đã mã hóa

    // ======== Kiểm tra ========
    boolean existsByEmail(String email);

    // ======== Xóa ========
    void deleteByEmail(String email);

    // ======== Đếm ========
    long countByEmail(String email);

    // ======== Truy vấn tùy chỉnh ========
    @Query("SELECT u FROM User u WHERE u.fullname = :name AND u.email = :email")
    Optional<User> findByNameAndEmail(@Param("name") String fullname, @Param("email") String email);

    @Query("SELECT u FROM User u ORDER BY u.fullname ASC")
    List<User> findAllSortedByName();

    // ======== Phân trang ========
    Page<User> findAll(Pageable pageable);

    Page<User> findByFullnameContaining(String keyword, Pageable pageable);
}
