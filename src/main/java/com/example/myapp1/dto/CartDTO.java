// package com.example.myapp1.dto;

// import org.antlr.v4.runtime.misc.NotNull;
// // import jakarta.validation.constraints.Min;

// public class CartDTO {
//     private int bookId;
//     private int quantity;
//     // @NotNull(message = "Book ID is required")
//     // private Integer bookId;

//     // @Min(value = 1, message = "Quantity must be at least 1")
//     // private int quantity;
//     // Constructors
//     public CartDTO() {
//     }

//     public CartDTO(int bookId, int quantity) {
//         this.bookId = bookId;
//         this.quantity = quantity;
//     }

//     // Getters and setters
//     public int getBookId() {
//         return bookId;
//     }

//     public void setBookId(int bookId) {
//         this.bookId = bookId;
//     }

//     public int getQuantity() {
//         return quantity;
//     }

//     public void setQuantity(int quantity) {
//         this.quantity = quantity;
//     }
// }

package com.example.myapp1.dto;

// import jakarta.validation.constraints.Min;
// import jakarta.validation.constraints.NotNull;

public class CartDTO {
    // @NotNull(message = "Book ID is required")
    private Long bookId;

    // @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    private Long cartId;
    private Long userId;
    private String status;
    private String createdAt;

    // Constructors
    public CartDTO() {
    }

    public CartDTO(Long bookId, int quantity, Long cartId, Long userId, String status, String createdAt) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.cartId = cartId;
        this.userId = userId;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // Builder pattern implementation
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long bookId;
        private int quantity;
        private Long cartId;
        private Long userId;
        private String status;
        private String createdAt;

        public Builder bookId(Long bookId) {
            this.bookId = bookId;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder cartId(Long cartId) {
            this.cartId = cartId;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CartDTO build() {
            return new CartDTO(bookId, quantity, cartId, userId, status, createdAt);
        }
    }
}
