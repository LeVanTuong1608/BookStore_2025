// package com.example.myapp1.service;

// import com.example.myapp1.dto.DiscountDTO;
// // import com.example.myapp1.dto.BookDTO;
// import com.example.myapp1.model.Discount;
// import com.example.myapp1.repository.DiscountRepository;
// import com.example.myapp1.model.Book;
// import com.example.myapp1.repository.BookRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;

// import java.util.List;
// // import java.util.Optional;
// import java.util.stream.Collectors;

// @Service
// public class DiscountService {

//     @Autowired
//     private DiscountRepository discountRepository;

//     @Autowired
//     private BookRepository bookRepository;

//     // Lấy tất cả các discount
//     public List<DiscountDTO> getAllDiscounts() {
//         return discountRepository.findAll().stream()
//                 .map(this::convertToDTO)
//                 .collect(Collectors.toList());
//     }

//     // Tìm discount theo ID
//     public ResponseEntity<DiscountDTO> getDiscountById(int discountId) {
//         return discountRepository.findById(discountId)
//                 .map(discount -> ResponseEntity.ok(convertToDTO(discount)))
//                 .orElseGet(() -> ResponseEntity.notFound().build());
//     }

//     // Tạo mới discount
//     public DiscountDTO createDiscount(DiscountDTO discountDTO) {
//         Book book = bookRepository.findById(discountDTO.getBookId())
//                 .orElseThrow(() -> new RuntimeException("Book not found"));
//         Discount discount = new Discount(
//                 discountDTO.getDiscountName(),
//                 discountDTO.getDiscountCode(),
//                 book,
//                 discountDTO.getMinOrderAmount(),
//                 discountDTO.getStartDate(),
//                 discountDTO.getEndDate());
//         discount = discountRepository.save(discount);
//         return convertToDTO(discount);
//     }

//     // Cập nhật discount
//     public DiscountDTO updateDiscount(int discountId, DiscountDTO discountDTO) {
//         Discount discount = discountRepository.findById(discountId)
//                 .orElseThrow(() -> new RuntimeException("Discount not found"));
//         Book book = bookRepository.findById(discountDTO.getBookId())
//                 .orElseThrow(() -> new RuntimeException("Book not found"));

//         discount.setDiscountName(discountDTO.getDiscountName());
//         discount.setDiscountCode(discountDTO.getDiscountCode());
//         discount.setBook(book);
//         discount.setMinOrderAmount(discountDTO.getMinOrderAmount());
//         discount.setStartDate(discountDTO.getStartDate());
//         discount.setEndDate(discountDTO.getEndDate());

//         discount = discountRepository.save(discount);
//         return convertToDTO(discount);
//     }

//     // Xóa discount
//     public void deleteDiscount(int discountId) {
//         discountRepository.deleteById(discountId);
//     }

//     public Discount getUserEntityById(int id) {
//         return discountRepository.findById(id).orElse(null);
//     }

//     // Chuyển Discount entity thành DiscountDTO
//     private DiscountDTO convertToDTO(Discount discount) {
//         return new DiscountDTO(
//                 discount.getDiscountId(),
//                 discount.getDiscountName(),
//                 discount.getDiscountCode(),

//                 // discount.getBook().getBookId(), // Lấy bookId từ đối tượng Book
//                 discount.getBook().getBookId(),
//                 discount.getBook().getTitle(),
//                 discount.getMinOrderAmount(),
//                 discount.getStartDate(),
//                 discount.getEndDate());
//     }

//     public Discount getDiscountEntityById(Integer discountId) {
//         return discountRepository.findById(discountId)
//                 .orElseThrow(() -> new RuntimeException("Discount not found with id: " + discountId));
//     }

// }

package com.example.myapp1.service;

import com.example.myapp1.dto.DiscountDTO;
import com.example.myapp1.model.Discount;
import com.example.myapp1.model.Book;
import com.example.myapp1.repository.DiscountRepository;
import com.example.myapp1.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.myapp1.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

// @Service
// public class DiscountService {

//     @Autowired
//     private DiscountRepository discountRepository;

//     @Autowired
//     private BookRepository bookRepository;

//     // Lấy tất cả các discount với phân trang
//     public Page<DiscountDTO> getAllDiscounts(Pageable pageable) {
//         return discountRepository.findAll(pageable)
//                 .map(this::convertToDTO);
//     }

//     // Tìm discount theo ID
//     public ResponseEntity<DiscountDTO> getDiscountById(int discountId) {
//         return discountRepository.findById(discountId)
//                 .map(discount -> ResponseEntity.ok(convertToDTO(discount)))
//                 .orElseGet(() -> ResponseEntity.notFound().build());
//     }

//     // Tạo mới discount
//     public DiscountDTO createDiscount(DiscountDTO discountDTO) {
//         Book book = bookRepository.findById(discountDTO.getBookId())
//                 .orElseThrow(() -> new RuntimeException("Book not found"));
//         Discount discount = new Discount(
//                 discountDTO.getDiscountName(),
//                 discountDTO.getDiscountCode(),
//                 book,
//                 discountDTO.getMinOrderAmount(),
//                 discountDTO.getStartDate(),
//                 discountDTO.getEndDate());
//         discount = discountRepository.save(discount);
//         return convertToDTO(discount);
//     }

//     // Cập nhật discount
//     public DiscountDTO updateDiscount(int discountId, DiscountDTO discountDTO) {
//         Discount discount = discountRepository.findById(discountId)
//                 .orElseThrow(() -> new RuntimeException("Discount not found"));
//         Book book = bookRepository.findById(discountDTO.getBookId())
//                 .orElseThrow(() -> new RuntimeException("Book not found"));

//         discount.setDiscountName(discountDTO.getDiscountName());
//         discount.setDiscountCode(discountDTO.getDiscountCode());
//         discount.setBook(book);
//         discount.setMinOrderAmount(discountDTO.getMinOrderAmount());
//         discount.setStartDate(discountDTO.getStartDate());
//         discount.setEndDate(discountDTO.getEndDate());

//         discount = discountRepository.save(discount);
//         return convertToDTO(discount);
//     }

//     // Xóa discount
//     public void deleteDiscount(int discountId) {
//         discountRepository.deleteById(discountId);
//     }

//     public Discount getDiscountEntityById(Integer discountId) {
//         return discountRepository.findById(discountId)
//                 .orElseThrow(() -> new RuntimeException("Discount not found with id: " + discountId));
//     }

//     // Chuyển Discount entity thành DiscountDTO
//     private DiscountDTO convertToDTO(Discount discount) {
//         return new DiscountDTO(
//                 discount.getDiscountId(),
//                 discount.getDiscountName(),
//                 discount.getDiscountCode(),
//                 discount.getBook().getBookId(),
//                 discount.getBook().getTitle(),
//                 discount.getMinOrderAmount(),
//                 discount.getStartDate(),
//                 discount.getEndDate());
//     }

// }

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final BookRepository bookRepository;

    @Autowired
    public DiscountService(DiscountRepository discountRepository, BookRepository bookRepository) {
        this.discountRepository = discountRepository;
        this.bookRepository = bookRepository;
    }

    public Page<DiscountDTO> getAllDiscounts(Pageable pageable) {
        return discountRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    public DiscountDTO getDiscountById(int discountId) {
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new NotFoundException("Discount not found with id: " + discountId));
        return convertToDTO(discount);
    }

    public DiscountDTO createDiscount(DiscountDTO discountDTO) {
        Book book = bookRepository.findById(discountDTO.getBookId())
                .orElseThrow(() -> new NotFoundException("Book not found with id: " + discountDTO.getBookId()));
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

    public DiscountDTO updateDiscount(int discountId, DiscountDTO discountDTO) {
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new NotFoundException("Discount not found with id: " + discountId));
        Book book = bookRepository.findById(discountDTO.getBookId())
                .orElseThrow(() -> new NotFoundException("Book not found with id: " + discountDTO.getBookId()));

        discount.setDiscountName(discountDTO.getDiscountName());
        discount.setDiscountCode(discountDTO.getDiscountCode());
        discount.setBook(book);
        discount.setMinOrderAmount(discountDTO.getMinOrderAmount());
        discount.setStartDate(discountDTO.getStartDate());
        discount.setEndDate(discountDTO.getEndDate());

        discount = discountRepository.save(discount);
        return convertToDTO(discount);
    }

    public void deleteDiscount(int discountId) {
        if (!discountRepository.existsById(discountId)) {
            throw new NotFoundException("Discount not found with id: " + discountId);
        }
        discountRepository.deleteById(discountId);
    }

    public Discount getDiscountEntityById(Integer discountId) {
        return discountRepository.findById(discountId)
                .orElseThrow(() -> new NotFoundException("Discount not found with id: " + discountId));
    }

    private DiscountDTO convertToDTO(Discount discount) {
        return new DiscountDTO(
                discount.getDiscountId(),
                discount.getDiscountName(),
                discount.getDiscountCode(),
                discount.getBook().getBookId(),
                discount.getBook().getTitle(),
                discount.getMinOrderAmount(),
                discount.getStartDate(),
                discount.getEndDate());
    }
}
