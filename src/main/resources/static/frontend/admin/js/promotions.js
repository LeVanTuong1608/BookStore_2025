document.addEventListener('DOMContentLoaded', function () {
  const promoForm = document.getElementById('addPromotionForm');
  const promoList = document.getElementById('promotionList');
  const bookSelect = document.getElementById('bookId');
  const overlay = document.createElement('div');
  document.body.appendChild(overlay);
  overlay.classList.add('overlay');

  const DISCOUNTS_URL = 'http://localhost:8080/api/discounts';
  const BOOKS_URL = 'http://localhost:8080/api/books';

  let editingPromoId = null;

  function handleError(error, message) {
    console.error(`${message}:`, error);
    alert(`Có lỗi xảy ra: ${message}`);
  }

  // 1. Tải danh sách sách
  async function fetchBooks() {
    try {
      const response = await fetch(BOOKS_URL);
      if (!response.ok) throw new Error(`Lỗi HTTP! status: ${response.status}`);
      const books = await response.json();
      renderBookOptions(books);
    } catch (error) {
      handleError(error, 'khi tải danh sách sách');
    }
  }

  function renderBookOptions(books) {
    bookSelect.innerHTML = '<option value="">-- Chọn sách --</option>';
    books.forEach((book) => {
      const option = document.createElement('option');
      option.value = book.id;
      option.textContent = book.title;
      bookSelect.appendChild(option);
    });
  }

  // 2. Tải danh sách khuyến mãi
  async function fetchPromotions() {
    try {
      const response = await fetch(DISCOUNTS_URL);
      if (!response.ok) throw new Error(`Lỗi HTTP! status: ${response.status}`);
      const data = await response.json();
      renderPromotions(data);
    } catch (error) {
      handleError(error, 'khi tải khuyến mãi');
    }
  }

  function renderPromotions(promotions) {
    promoList.innerHTML = '';

    if (promotions.length === 0) {
      promoList.innerHTML =
        '<tr><td colspan="8">Không có khuyến mãi nào</td></tr>';
      return;
    }

    promotions.forEach((promo, index) => {
      const row = document.createElement('tr');
      row.innerHTML = `
        <td>${index + 1}</td>
        <td>${promo.discountName || 'N/A'}</td>
        <td>${promo.discountCode || 'N/A'}</td>
        <td>${promo.title || 'N/A'}</td>
        <td>${
          promo.minOrderAmount
            ? `${parseFloat(promo.minOrderAmount).toLocaleString()} VND`
            : 'N/A'
        }</td>
        <td>${promo.startDate || 'N/A'} - ${promo.endDate || 'N/A'}</td>
        <td>
          <button class="edit-btn" data-id="${promo.discountId}">Sửa</button>
          <button class="delete-btn" data-id="${promo.discountId}">Xóa</button>
        </td>
      `;
      promoList.appendChild(row);
    });

    document
      .querySelectorAll('.edit-btn')
      .forEach((btn) => btn.addEventListener('click', handleEdit));
    document
      .querySelectorAll('.delete-btn')
      .forEach((btn) => btn.addEventListener('click', handleDelete));
  }

  async function handleEdit(event) {
    const promoId = event.target.dataset.id;
    if (!promoId) return;

    try {
      const response = await fetch(`${DISCOUNTS_URL}/${promoId}`);
      if (!response.ok) throw new Error(`Lỗi HTTP! status: ${response.status}`);
      const promo = await response.json();
      editingPromoId = promoId;

      document.getElementById('promoName').value = promo.discountName || '';
      document.getElementById('promoCode').value = promo.discountCode || '';
      document.getElementById('bookId').value = promo.book?.id || '';
      document.getElementById('minSpend').value = promo.minOrderAmount || '';
      document.getElementById('startDate').value = promo.startDate || '';
      document.getElementById('endDate').value = promo.endDate || '';

      promoForm.classList.add('show-form');
      overlay.classList.add('show-overlay');
    } catch (error) {
      handleError(error, 'khi tải thông tin khuyến mãi');
    }
  }

  async function handleDelete(event) {
    const promoId = event.target.dataset.id;
    if (!promoId) return;

    const confirmation = confirm('Bạn có chắc chắn muốn xóa khuyến mãi này?');
    if (!confirmation) return;

    try {
      const response = await fetch(`${DISCOUNTS_URL}/${promoId}`, {
        method: 'DELETE',
      });
      if (!response.ok) throw new Error(`Lỗi HTTP! status: ${response.status}`);
      fetchPromotions();
    } catch (error) {
      handleError(error, 'khi xóa khuyến mãi');
    }
  }

  // Xử lý thêm / sửa
  promoForm.addEventListener('submit', async function (event) {
    event.preventDefault();

    try {
      const discountName = document.getElementById('promoName').value.trim();
      const discountCode = document.getElementById('promoCode').value.trim();
      const bookId = parseInt(document.getElementById('bookId').value);
      const minOrderAmount = parseFloat(
        document.getElementById('minSpend').value
      );
      const startDate = document.getElementById('startDate').value;
      const endDate = document.getElementById('endDate').value;

      if (
        !discountName ||
        discountCode ||
        isNaN(bookId) ||
        isNaN(minOrderAmount) ||
        !startDate ||
        !endDate
      ) {
        alert('Vui lòng điền đầy đủ thông tin');
        return;
      }

      const promoData = {
        discountName,
        discountCode,
        book: { id: bookId },
        minOrderAmount,
        startDate,
        endDate,
      };

      const url = editingPromoId
        ? `${DISCOUNTS_URL}/${editingPromoId}`
        : DISCOUNTS_URL;
      const method = editingPromoId ? 'PUT' : 'POST';

      const response = await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(promoData),
      });

      if (!response.ok) throw new Error(`Lỗi HTTP! status: ${response.status}`);

      promoForm.reset();
      promoForm.classList.remove('show-form');
      overlay.classList.remove('show-overlay');
      editingPromoId = null;

      fetchPromotions();
    } catch (error) {
      handleError(error, 'khi lưu khuyến mãi');
    }
  });

  overlay.addEventListener('click', () => {
    promoForm.classList.remove('show-form');
    overlay.classList.remove('show-overlay');
    editingPromoId = null;
  });

  // Khởi chạy
  fetchBooks();
  fetchPromotions();
});
