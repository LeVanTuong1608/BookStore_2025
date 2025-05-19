// package com.example.myapp1.dto;

// public class BookDTO {
//     private long bookId;
//     private String title;
//     private String imageUrl;
//     private String category;
//     private double price;
//     // private int authorId;
//     private String authorName;
//     private String description;
//     private String publisher;
//     private int publicationYear;
//     private String dimensions;

//     // public BookDTO() {

//     // }

//     // public BookDTO(String title, String imageUrl, String category, double price, int authorId,
//     //         String description, String publisher, int publicationYear, String dimensions) {
//     //     this.title = title;
//     //     this.imageUrl = imageUrl;
//     //     this.category = category;
//     //     this.price = price;
//     //     this.authorId = authorId;
//     //     this.description = description;
//     //     this.publisher = publisher;
//     //     this.publicationYear = publicationYear;
//     //     this.dimensions = dimensions;
//     // }
//     // Getters and Setters

//     public long getBookId() {
//         return bookId;
//     }

//     public void setBookId(long bookId) {
//         this.bookId = bookId;
//     }

//     public String getTitle() {
//         return title;
//     }

//     public void setTitle(String title) {
//         this.title = title;
//     }

//     public String getImageUrl() {
//         return imageUrl;
//     }

//     public void setImageUrl(String imageUrl) {
//         this.imageUrl = imageUrl;
//     }

//     public String getCategory() {
//         return category;
//     }

//     public void setCategory(String category) {
//         this.category = category;
//     }

//     public double getPrice() {
//         return price;
//     }

//     public void setPrice(double price) {
//         this.price = price;
//     }

//     // public int getAuthorId() {
//     //     return authorId;
//     // }

//     // public void setAuthorId(int authorId) {
//     //     this.authorId = authorId;
//     // }

//     public String getAuthorName() {
//         return authorName;
//     }

//     public void setAuthorName(String authorName) {
//         this.authorName = authorName;
//     }

//     public String getDescription() {
//         return description;
//     }

//     public void setDescription(String description) {
//         this.description = description;
//     }

//     public String getPublisher() {
//         return publisher;
//     }

//     public void setPublisher(String publisher) {
//         this.publisher = publisher;
//     }

//     public int getPublicationYear() {
//         return publicationYear;
//     }

//     public void setPublicationYear(int publicationYear) {
//         this.publicationYear = publicationYear;
//     }

//     public String getDimensions() {
//         return dimensions;
//     }

//     public void setDimensions(String dimensions) {
//         this.dimensions = dimensions;
//     }
// }

package com.example.myapp1.dto;

public class BookDTO {
    private long bookId;
    private String title;
    private String imageUrl;
    private String category;
    private double price;
    private String authorName;
    private String description;
    private String publisher;
    private int publicationYear;
    private String dimensions;

    // No-args constructor
    public BookDTO() {
    }

    // All-args constructor
    public BookDTO(long bookId, String title, String imageUrl, String category,
            double price, String authorName, String description,
            String publisher, int publicationYear, String dimensions) {
        this.bookId = bookId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.category = category;
        this.price = price;
        this.authorName = authorName;
        this.description = description;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.dimensions = dimensions;
    }

    // Builder pattern implementation
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long bookId;
        private String title;
        private String imageUrl;
        private String category;
        private double price;
        private String authorName;
        private String description;
        private String publisher;
        private int publicationYear;
        private String dimensions;

        public Builder bookId(long bookId) {
            this.bookId = bookId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder authorName(String authorName) {
            this.authorName = authorName;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder publicationYear(int publicationYear) {
            this.publicationYear = publicationYear;
            return this;
        }

        public Builder dimensions(String dimensions) {
            this.dimensions = dimensions;
            return this;
        }

        public BookDTO build() {
            return new BookDTO(bookId, title, imageUrl, category, price,
                    authorName, description, publisher,
                    publicationYear, dimensions);
        }
    }

    // Getters and Setters
    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    // toString() for better logging
    @Override
    public String toString() {
        return "BookDTO{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", authorName='" + authorName + '\'' +
                '}';
    }
}