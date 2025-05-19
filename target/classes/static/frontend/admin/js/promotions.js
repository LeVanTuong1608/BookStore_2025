document.addEventListener('DOMContentLoaded', () => {
  const DISCOUNTS_URL = 'http://localhost:8080/admin/api/discounts';
  const BOOKS_URL = 'http://localhost:8080/admin/api/books/All';

  const promoForm = document.getElementById('addPromotionForm');
  const promoList = document.getElementById('promotionList');
  const bookSelect = document.getElementById('bookId');
  const cancelBtn = promoForm.querySelector('.cancel-btn');

  // Tạo phần điều khiển phân trang
  const paginationContainer = document.createElement('div');
  paginationContainer.id = 'paginationContainer';
  paginationContainer.style.margin = '20px 0';
  promoList.parentNode.insertBefore(paginationContainer, promoList.nextSibling);

  let editingPromoId = null;
  let currentPage = 0;
  let totalPages = 1;
  const pageSize = 12; // Số lượng item mỗi trang, phù hợp với API

  function handleError(err, msg) {
    console.error(msg, err);
    alert(`Có lỗi: ${msg}`);
  }

  // 1. Load sách vào dropdown
  async function fetchBooks() {
    try {
      const resp = await fetch(BOOKS_URL);
      if (!resp.ok) throw new Error(`HTTP ${resp.status}`);
      const books = await resp.json();

      bookSelect.innerHTML = '<option value="">Chọn sách</option>';
      books.forEach((b) => {
        const option = document.createElement('option');
        option.value = b.bookId;
        option.textContent = b.title;
        bookSelect.appendChild(option);
      });
    } catch (e) {
      handleError(e, 'khi tải danh sách sách');
    }
  }

  // 2. Load và hiển thị khuyến mãi với phân trang
  async function fetchPromotions(page = 0) {
    try {
      const resp = await fetch(
        `${DISCOUNTS_URL}?page=${page}&size=${pageSize}`
      );
      if (!resp.ok) throw new Error(`HTTP ${resp.status}`);

      const data = await resp.json();
      currentPage = data.number;
      totalPages = data.totalPages;

      renderPromotions(data.content);
      updatePaginationControls(data);
    } catch (e) {
      handleError(e, 'khi tải khuyến mãi');
      promoList.innerHTML = `<tr><td colspan="7">Không thể tải dữ liệu</td></tr>`;
    }
  }

  function renderPromotions(promos) {
    promoList.innerHTML = '';

    if (!promos || promos.length === 0) {
      promoList.innerHTML = `<tr><td colspan="7">Không có khuyến mãi</td></tr>`;
      return;
    }

    const startIndex = currentPage * pageSize;

    promos.forEach((promo, index) => {
      const row = document.createElement('tr');
      row.innerHTML = `
        <td>${startIndex + index + 1}</td>
        <td>${promo.discountName || '—'}</td>
        <td>${promo.discountCode || '—'}</td>
        <td>${promo.title || '—'}</td>
        <td>${formatCurrency(promo.minOrderAmount)}</td>
        <td>${formatDate(promo.startDate)} → ${formatDate(promo.endDate)}</td>
        <td>
          <button class="edit-btn" data-id="${promo.discountId}">Sửa</button>
          <button class="delete-btn" data-id="${promo.discountId}">Xóa</button>
        </td>
      `;
      promoList.appendChild(row);
    });

    // Gắn sự kiện cho các nút
    document.querySelectorAll('.edit-btn').forEach((btn) => {
      btn.addEventListener('click', handleEdit);
    });

    document.querySelectorAll('.delete-btn').forEach((btn) => {
      btn.addEventListener('click', handleDelete);
    });
  }

  // Định dạng tiền tệ
  function formatCurrency(amount) {
    return amount
      ? new Intl.NumberFormat('vi-VN', {
          style: 'currency',
          currency: 'VND',
        }).format(amount)
      : '—';
  }

  // Định dạng ngày tháng
  function formatDate(dateString) {
    if (!dateString) return '—';
    const date = new Date(dateString);
    return date.toLocaleDateString('vi-VN');
  }

  // Cập nhật điều khiển phân trang
  function updatePaginationControls(data) {
    paginationContainer.innerHTML = `
      <div style="display: flex; gap: 10px; align-items: center;">
        <button id="firstPage" ${data.first ? 'disabled' : ''}>
          <i class="fas fa-angle-double-left"></i>
        </button>
        <button id="prevPage" ${data.first ? 'disabled' : ''}>
          <i class="fas fa-angle-left"></i>
        </button>
        <span>Trang ${currentPage + 1} / ${totalPages}</span>
        <button id="nextPage" ${data.last ? 'disabled' : ''}>
          <i class="fas fa-angle-right"></i>
        </button>
        <button id="lastPage" ${data.last ? 'disabled' : ''}>
          <i class="fas fa-angle-double-right"></i>
        </button>
        <span>(${data.totalElements} khuyến mãi)</span>
      </div>
    `;

    // Gắn sự kiện cho các nút phân trang
    document
      .getElementById('firstPage')
      ?.addEventListener('click', () => fetchPromotions(0));
    document
      .getElementById('prevPage')
      ?.addEventListener('click', () => fetchPromotions(currentPage - 1));
    document
      .getElementById('nextPage')
      ?.addEventListener('click', () => fetchPromotions(currentPage + 1));
    document
      .getElementById('lastPage')
      ?.addEventListener('click', () => fetchPromotions(totalPages - 1));
  }

  // 3. Xử lý xóa khuyến mãi
  async function handleDelete(event) {
    const discountId = event.target.dataset.id;

    if (!confirm('Bạn có chắc chắn muốn xóa khuyến mãi này?')) return;

    try {
      const response = await fetch(`${DISCOUNTS_URL}/${discountId}`, {
        method: 'DELETE',
      });

      if (!response.ok) throw new Error(`HTTP ${response.status}`);

      // Kiểm tra nếu trang hiện tại trống sau khi xóa
      const checkResponse = await fetch(
        `${DISCOUNTS_URL}?page=${currentPage}&size=${pageSize}`
      );
      const checkData = await checkResponse.json();

      if (checkData.numberOfElements === 0 && currentPage > 0) {
        fetchPromotions(currentPage - 1);
      } else {
        fetchPromotions(currentPage);
      }

      alert('Xóa khuyến mãi thành công!');
    } catch (error) {
      handleError(error, 'khi xóa khuyến mãi');
    }
  }

  // 4. Xử lý chỉnh sửa khuyến mãi
  async function handleEdit(event) {
    const discountId = event.target.dataset.id;

    try {
      // Đảm bảo danh sách sách đã được tải
      await fetchBooks();

      // Lấy thông tin chi tiết khuyến mãi
      const response = await fetch(`${DISCOUNTS_URL}/${discountId}`);
      if (!response.ok) throw new Error(`HTTP ${response.status}`);

      const promo = await response.json();

      // Điền dữ liệu vào form
      promoForm.promoName.value = promo.discountName || '';
      promoForm.promoCode.value = promo.discountCode || '';
      promoForm.bookId.value = promo.bookId || '';
      promoForm.minSpend.value = promo.minOrderAmount || '';
      promoForm.startDate.value = promo.startDate?.split('T')[0] || '';
      promoForm.endDate.value = promo.endDate?.split('T')[0] || '';

      editingPromoId = discountId;
      promoForm.scrollIntoView({ behavior: 'smooth' });
    } catch (error) {
      handleError(error, 'khi lấy thông tin khuyến mãi');
    }
  }

  // 5. Xử lý submit form
  promoForm.addEventListener('submit', async (event) => {
    event.preventDefault();

    // Lấy dữ liệu từ form
    const formData = {
      discountName: promoForm.promoName.value.trim(),
      discountCode: promoForm.promoCode.value.trim(),
      bookId: promoForm.bookId.value,
      minOrderAmount: promoForm.minSpend.value,
      startDate: promoForm.startDate.value,
      endDate: promoForm.endDate.value,
    };

    // Validate dữ liệu
    const errors = [];
    if (!formData.discountName)
      errors.push('Tên khuyến mãi không được để trống');
    if (!formData.discountCode)
      errors.push('Mã khuyến mãi không được để trống');
    if (!formData.bookId) errors.push('Vui lòng chọn sách');
    if (!formData.minOrderAmount || isNaN(formData.minOrderAmount)) {
      errors.push('Mức chi tối thiểu không hợp lệ');
    }
    if (!formData.startDate) errors.push('Ngày bắt đầu không được để trống');
    if (!formData.endDate) errors.push('Ngày kết thúc không được để trống');

    if (formData.startDate && formData.endDate) {
      const startDate = new Date(formData.startDate);
      const endDate = new Date(formData.endDate);

      if (startDate >= endDate) {
        errors.push('Ngày kết thúc phải sau ngày bắt đầu');
      }
    }

    if (errors.length > 0) {
      alert('Lỗi:\n' + errors.join('\n'));
      return;
    }

    // Chuẩn bị dữ liệu gửi đi
    const payload = {
      discountName: formData.discountName,
      discountCode: formData.discountCode,
      bookId: parseInt(formData.bookId),
      minOrderAmount: parseFloat(formData.minOrderAmount),
      startDate: formData.startDate,
      endDate: formData.endDate,
    };

    const url = editingPromoId
      ? `${DISCOUNTS_URL}/${editingPromoId}`
      : DISCOUNTS_URL;
    const method = editingPromoId ? 'PUT' : 'POST';

    try {
      const response = await fetch(url, {
        method,
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
      });

      if (!response.ok) {
        const errorData = await response.json().catch(() => ({}));
        throw new Error(errorData.message || `HTTP ${response.status}`);
      }

      // Xử lý sau khi thành công
      alert(
        editingPromoId
          ? 'Cập nhật khuyến mãi thành công!'
          : 'Thêm khuyến mãi thành công!'
      );

      promoForm.reset();
      editingPromoId = null;

      // Load lại danh sách
      if (method === 'POST') {
        fetchPromotions(totalPages - 1); // Nếu là thêm mới, chuyển đến trang cuối
      } else {
        fetchPromotions(currentPage); // Nếu là cập nhật, giữ nguyên trang
      }
    } catch (error) {
      handleError(error, 'khi lưu khuyến mãi');
    }
  });

  // 6. Xử lý nút hủy
  cancelBtn.addEventListener('click', () => {
    promoForm.reset();
    editingPromoId = null;
  });

  // Khởi chạy
  fetchBooks();
  fetchPromotions();
});
