document.addEventListener('DOMContentLoaded', function () {
  const booksContainer = document.getElementById('books-container');
  const paginationContainer = document.getElementById('pagination');
  const sortSelect = document.getElementById('sort');
  const filterCheckboxes = document.querySelectorAll(
    '.filter-category input[type="checkbox"]'
  );
  const searchInput = document.getElementById('search-input');
  const searchBtn = document.getElementById('search-btn');
  const itemsPerPage = 12;
  let currentPage = 1;

  // TODO: Sau này thay thế danh sách sản phẩm này bằng API call
  let products = [
    {
      id: 1,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 0,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
      category: 'văn học', // Thể loại từ API
    },
    {
      id: 2,
      title: 'Làm gì - John Gray',
      price: 160000,
      discount: 0,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
      category: 'tâm lý - kỹ năng sống', // Thể loại từ API
    },
    {
      id: 4,
      title: 'Làm gì - John Gray',
      price: 160000,
      discount: 0,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
    },
    {
      id: 6,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 0,
      image:
        'https://storage.googleapis.com/a1aa/image/Ua50ayq3EjGPiiS5UKj7Qs_inuZFrtEGqMBIvbTaLvM.jpg',
    },
    {
      id: 7,
      title: 'Làm gì - John Gray',
      price: 160000,
      discount: 0,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
    },
    {
      id: 8,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 0,
      image:
        'https://storage.googleapis.com/a1aa/image/Ua50ayq3EjGPiiS5UKj7Qs_inuZFrtEGqMBIvbTaLvM.jpg',
    },
    {
      id: 9,
      title: 'Làm gì - John Gray',
      price: 160000,
      discount: 0,
      image:
        'https://res.cloudinary.com/dbynglvwk/image/upload/v1653963583/bookstore/hpfjebw8i5cygplpl5zi.jpg',
    },
    {
      id: 10,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 0,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
    },
    {
      id: 11,
      title: 'Làm gì - John Gray',
      price: 160000,
      discount: 0,
      image:
        'https://res.cloudinary.com/dbynglvwk/image/upload/v1653963583/bookstore/hpfjebw8i5cygplpl5zi.jpg',
    },
    {
      id: 12,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 0,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
    },
    {
      id: 13,
      title: 'Làm gì - John Gray',
      price: 160000,
      discount: 0,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
    },
    {
      id: 14,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 0,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
    },
    {
      id: 15,
      title: 'Làm gì - John Gray',
      price: 160000,
      discount: 0,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
    },
    {
      id: 16,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 0,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
    },
    {
      id: 17,
      title: 'Làm gì - John Gray',
      price: 160000,
      discount: 0,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
    },
    {
      id: 18,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 0,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
    },
    {
      id: 19,
      title: 'Làm gì - John Gray',
      price: 160000,
      discount: 0,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
    },

    // Thêm các sản phẩm khác với thuộc tính category tương ứng...
  ];

  // Hàm hiển thị sản phẩm
  function renderProducts() {
    booksContainer.innerHTML = '';
    let filteredProducts = filterProducts(); // Lọc sản phẩm theo thể loại
    let searchedProducts = searchProducts(filteredProducts); // Tìm kiếm sản phẩm
    let sortedProducts = sortProducts(searchedProducts); // Sắp xếp sản phẩm
    let paginatedProducts = paginateProducts(sortedProducts); // Phân trang sản phẩm

    // Hiển thị sản phẩm
    paginatedProducts.forEach((product) => {
      let priceAfterDiscount = product.price * (1 - product.discount / 100);
      let productElement = document.createElement('div');
      productElement.classList.add('product-item');
      productElement.innerHTML = `
            <img src="${product.image}" alt="${product.title}">
            <h4>${product.title}</h4>
            <p class="price">${priceAfterDiscount.toLocaleString()} đ <span class="original-price">${
        product.discount > 0 ? product.price.toLocaleString() + ' đ' : ''
      }</span></p>
          `;

      // Khi nhấn vào sản phẩm, chuyển đến trang chi tiết sách
      productElement.addEventListener('click', () => {
        window.location.href = `book-detail.html?id=${product.id}`;
      });

      booksContainer.appendChild(productElement);
    });

    // Hiển thị phân trang
    renderPagination(searchedProducts);
  }

  // Hàm lọc sản phẩm theo thể loại
  function filterProducts() {
    let selectedCategories = Array.from(filterCheckboxes)
      .filter((checkbox) => checkbox.checked)
      .map((checkbox) => checkbox.value);

    if (selectedCategories.length === 0) {
      return products; // Nếu không có thể loại nào được chọn, hiển thị tất cả sản phẩm
    }

    return products.filter((product) =>
      selectedCategories.includes(product.category)
    );
  }

  // Hàm tìm kiếm sản phẩm
  function searchProducts(filteredProducts) {
    const searchTerm = searchInput.value.trim().toLowerCase();
    if (!searchTerm) {
      return filteredProducts; // Nếu không có từ khóa tìm kiếm, trả về danh sách đã lọc
    }
    return filteredProducts.filter((product) =>
      product.title.toLowerCase().includes(searchTerm)
    );
  }

  // Hàm sắp xếp sản phẩm
  function sortProducts(filteredProducts) {
    let sortBy = sortSelect.value;
    if (sortBy === 'price-asc') {
      return filteredProducts.slice().sort((a, b) => a.price - b.price);
    } else if (sortBy === 'price-desc') {
      return filteredProducts.slice().sort((a, b) => b.price - a.price);
    }
    return filteredProducts; // Mặc định: sắp xếp theo mới nhất
  }

  // Hàm phân trang sản phẩm
  function paginateProducts(sortedProducts) {
    let start = (currentPage - 1) * itemsPerPage;
    let end = start + itemsPerPage;
    return sortedProducts.slice(start, end);
  }

  // Hàm hiển thị phân trang
  function renderPagination(filteredProducts) {
    let totalPages = Math.ceil(filteredProducts.length / itemsPerPage);
    paginationContainer.innerHTML = '';

    for (let i = 1; i <= totalPages; i++) {
      let btn = document.createElement('button');
      btn.classList.add('page-btn');
      btn.dataset.page = i;
      btn.textContent = i;
      btn.addEventListener('click', function () {
        currentPage = parseInt(this.dataset.page);
        renderProducts();
      });
      paginationContainer.appendChild(btn);
    }
  }

  // Sự kiện thay đổi sắp xếp
  sortSelect.addEventListener('change', function () {
    currentPage = 1; // Reset về trang đầu tiên khi thay đổi sắp xếp
    renderProducts();
  });

  // Sự kiện thay đổi checkbox lọc thể loại
  filterCheckboxes.forEach((checkbox) => {
    checkbox.addEventListener('change', function () {
      currentPage = 1; // Reset về trang đầu tiên khi thay đổi lọc
      renderProducts();
    });
  });

  // Sự kiện tìm kiếm
  searchBtn.addEventListener('click', function () {
    currentPage = 1; // Reset về trang đầu tiên khi tìm kiếm
    renderProducts();
  });

  // Sự kiện nhấn Enter trong ô tìm kiếm
  searchInput.addEventListener('keypress', function (e) {
    if (e.key === 'Enter') {
      currentPage = 1; // Reset về trang đầu tiên khi tìm kiếm
      renderProducts();
    }
  });

  // Hiển thị sản phẩm ban đầu
  renderProducts();
});
