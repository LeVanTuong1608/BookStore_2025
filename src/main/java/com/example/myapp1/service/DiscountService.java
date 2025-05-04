package com.example.myapp1.service;

import com.example.myapp1.dto.DiscountDTO;
// import com.example.myapp1.dto.BookDTO;
import com.example.myapp1.model.Discount;
import com.example.myapp1.repository.DiscountRepository;
import com.example.myapp1.model.Book;
import com.example.myapp1.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
// import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private BookRepository bookRepository;

    // Lấy tất cả các discount
    public List<DiscountDTO> getAllDiscounts() {
        return discountRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Tìm discount theo ID
    public ResponseEntity<DiscountDTO> getDiscountById(int discountId) {
        return discountRepository.findById(discountId)
                .map(discount -> ResponseEntity.ok(convertToDTO(discount)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Tạo mới discount
    public DiscountDTO createDiscount(DiscountDTO discountDTO) {
        Book book = bookRepository.findById(discountDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        Discount discount = new Discount(
                discountDTO.getDiscountName(),
                discountDTO.getDiscountCode(),
                book,
                discountDTO.getMinOrderAmount(),
                discountDTO.getStartDate(),
                discountDTO.getEndDate());
        discount = discountRepository.save(discount);
        return convertToDTO(discount);
    }

    // Cập nhật discount
    public DiscountDTO updateDiscount(int discountId, DiscountDTO discountDTO) {
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new RuntimeException("Discount not found"));
        Book book = bookRepository.findById(discountDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        discount.setDiscountName(discountDTO.getDiscountName());
        discount.setDiscountCode(discountDTO.getDiscountCode());
        discount.setBook(book);
        discount.setMinOrderAmount(discountDTO.getMinOrderAmount());
        discount.setStartDate(discountDTO.getStartDate());
        discount.setEndDate(discountDTO.getEndDate());

        discount = discountRepository.save(discount);
        return convertToDTO(discount);
    }

    // Xóa discount
    public void deleteDiscount(int discountId) {
        discountRepository.deleteById(discountId);
    }

    public Discount getUserEntityById(int id) {
        return discountRepository.findById(id).orElse(null);
    }

    // Chuyển Discount entity thành DiscountDTO
    private DiscountDTO convertToDTO(Discount discount) {
        return new DiscountDTO(
                discount.getDiscountId(),
                discount.getDiscountName(),
                discount.getDiscountCode(),

                // discount.getBook().getBookId(), // Lấy bookId từ đối tượng Book
                discount.getBook().getBookId(),
                discount.getBook().getTitle(),
                discount.getMinOrderAmount(),
                discount.getStartDate(),
                discount.getEndDate());
    }

    public Discount getDiscountEntityById(Integer discountId) {
        return discountRepository.findById(discountId)
                .orElseThrow(() -> new RuntimeException("Discount not found with id: " + discountId));
    }

}
