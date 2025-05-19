document.addEventListener('DOMContentLoaded', function () {
  const booksContainer = document.getElementById('books-container');
  const paginationContainer = document.getElementById('pagination');
  const sortSelect = document.getElementById('sort');
  const filterCheckboxes = document.querySelectorAll(
    '.filter-category input[type="checkbox"]'
  );
  const searchInput = document.getElementById('search-input');
  const searchBtn = document.getElementById('search-btn');
  const itemsPerPage = 16;
  let currentPage = 1;
  let currentSearchTerm = '';
  let selectedCategories = [];
  let currentSort = '';
  let totalPages = 1;

  async function fetchBooks() {
    if (!booksContainer || !paginationContainer) {
      console.error('Không tìm thấy phần tử DOM cần thiết.');
      return;
    }

    try {
      let apiUrl = `http://localhost:8080/user/api/books?page=${
        currentPage - 1
      }&size=${itemsPerPage}`;

      if (currentSearchTerm) {
        apiUrl += `&title=${encodeURIComponent(currentSearchTerm)}`;
      }

      if (selectedCategories.length > 0) {
        apiUrl += `&category=${selectedCategories.join(',')}`;
      }

      if (currentSort === 'price-asc') {
        apiUrl += `&sort=price,asc`;
      } else if (currentSort === 'price-desc') {
        apiUrl += `&sort=price,desc`;
      }

      const response = await fetch(apiUrl);

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();
      console.log('Dữ liệu API trả về:', data);

      // Xử lý trường hợp API trả về mảng hoặc trả về đối tượng có content
      let books = [];
      if (Array.isArray(data)) {
        books = data;
        totalPages = 1; // giả định nếu API trả về mảng không phân trang
      } else if (data && Array.isArray(data.content)) {
        books = data.content;
        totalPages = data.totalPages || 1;
      } else {
        showErrorMessage(
          'Không có dữ liệu sách hoặc dữ liệu không đúng định dạng.'
        );
        return;
      }

      renderProducts(books);
      renderPagination();
    } catch (error) {
      console.error('Error fetching books:', error);
      showErrorMessage('Không thể tải danh sách sách. Vui lòng thử lại sau.');
    }
  }

  function renderProducts(books) {
    booksContainer.innerHTML = '';

    if (books.length === 0) {
      booksContainer.innerHTML =
        '<p class="no-results">Không tìm thấy sách phù hợp.</p>';
      return;
    }

    const fragment = document.createDocumentFragment();

    books.forEach((book) => {
      const productElement = document.createElement('div');
      productElement.className = 'product-item';
      productElement.innerHTML = `
        <a href="book-detail.html?id=${book.bookId}" class="product-link">
          <img src="${book.imageUrl || '../frontend/images/default-book.jpg'}" 
               alt="${book.title}" 
               onerror="this.src='../frontend/images/default-book.jpg'">
          <h4>${book.title}</h4>
          <p class="author">${book.authorName || ''}</p>
          <p class="price">${formatPrice(book.price)} đ</p>
          ${
            book.discount > 0
              ? `<span class="discount-badge">-${book.discount}%</span>`
              : ''
          }
        </a>
      `;
      fragment.appendChild(productElement);
    });

    booksContainer.appendChild(fragment);
  }

  function renderPagination() {
    paginationContainer.innerHTML = '';

    if (totalPages <= 1) return;

    if (currentPage > 1) {
      paginationContainer.appendChild(createPaginationButton('<<', 1));
      paginationContainer.appendChild(
        createPaginationButton('<', currentPage - 1)
      );
    }

    const startPage = Math.max(1, currentPage - 2);
    const endPage = Math.min(totalPages, currentPage + 2);

    for (let i = startPage; i <= endPage; i++) {
      const btn = createPaginationButton(i, i);
      if (i === currentPage) btn.classList.add('active');
      paginationContainer.appendChild(btn);
    }

    if (currentPage < totalPages) {
      paginationContainer.appendChild(
        createPaginationButton('>', currentPage + 1)
      );
      paginationContainer.appendChild(createPaginationButton('>>', totalPages));
    }
  }

  function createPaginationButton(text, page) {
    const btn = document.createElement('button');
    btn.className = 'page-btn';
    btn.textContent = text;
    btn.dataset.page = page;
    btn.addEventListener('click', function () {
      currentPage = parseInt(this.dataset.page);
      fetchBooks();
      window.scrollTo({ top: 0, behavior: 'smooth' });
    });
    return btn;
  }

  function formatPrice(price) {
    if (!price && price !== 0) return '';
    return new Intl.NumberFormat('vi-VN').format(price);
  }

  function showErrorMessage(message) {
    booksContainer.innerHTML = `<p class="error-message">${message}</p>`;
    paginationContainer.innerHTML = '';
  }

  sortSelect?.addEventListener('change', function () {
    currentSort = this.value;
    currentPage = 1;
    fetchBooks();
  });

  filterCheckboxes.forEach((checkbox) => {
    checkbox.addEventListener('change', function () {
      selectedCategories = Array.from(filterCheckboxes)
        .filter((cb) => cb.checked)
        .map((cb) => cb.value);
      currentPage = 1;
      fetchBooks();
    });
  });

  searchBtn?.addEventListener('click', function () {
    currentSearchTerm = searchInput.value.trim();
    currentPage = 1;
    fetchBooks();
  });

  searchInput?.addEventListener('keypress', function (e) {
    if (e.key === 'Enter') {
      currentSearchTerm = searchInput.value.trim();
      currentPage = 1;
      fetchBooks();
    }
  });

  fetchBooks();
});
