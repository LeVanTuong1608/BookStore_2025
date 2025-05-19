document.addEventListener('DOMContentLoaded', () => {
  const authorList = document.getElementById('authorList');
  const apiUrl = 'http://localhost:8080/admin/api/authors';
  const addAuthorForm = document.getElementById('addPromotionForm');
  const paginationContainer = document.createElement('div');
  paginationContainer.id = 'paginationContainer';
  paginationContainer.style.margin = '20px 0';
  authorList.parentNode.insertBefore(
    paginationContainer,
    authorList.nextSibling
  );

  let currentPage = 0;
  let totalPages = 1;
  const pageSize = 12; // Số lượng item mỗi trang

  // Lấy danh sách tác giả
  async function fetchAuthors(page = 0) {
    try {
      const response = await fetch(
        `${apiUrl}?page=${page}&size=${pageSize}&sort=authorId,asc`
      );
      if (!response.ok) throw new Error('Không thể tải dữ liệu tác giả');
      const data = await response.json();

      // Cập nhật thông tin phân trang từ API response
      totalPages = data.totalPages;
      currentPage = data.number;

      renderAuthors(data.content);
      updatePaginationControls(data);
    } catch (error) {
      console.error(error);
      alert('Lỗi khi lấy danh sách tác giả.');
      authorList.innerHTML = `<tr><td colspan="4">Không thể tải dữ liệu tác giả.</td></tr>`;
    }
  }

  // Hiển thị danh sách tác giả
  function renderAuthors(authors) {
    authorList.innerHTML = '';
    if (!authors || authors.length === 0) {
      authorList.innerHTML = `<tr><td colspan="4">Không có tác giả.</td></tr>`;
      return;
    }

    const startIndex = currentPage * pageSize; // Tính toán chỉ số bắt đầu

    authors.forEach((author, index) => {
      const row = document.createElement('tr');
      row.innerHTML = `
        <td>${startIndex + index + 1}</td>
        <td>${author.authorName}</td>
        <td>${formatDate(author.dateOfBirth)}</td>
        <td>
          <button class="delete-btn" data-id="${author.authorId}">Xóa</button>
        </td>
      `;
      authorList.appendChild(row);
    });

    // Gắn sự kiện xóa
    document.querySelectorAll('.delete-btn').forEach((button) => {
      button.addEventListener('click', handleDelete);
    });
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
        <span>(${data.totalElements} tác giả)</span>
      </div>
    `;

    // Gắn sự kiện cho các nút phân trang
    document
      .getElementById('firstPage')
      ?.addEventListener('click', () => fetchAuthors(0));
    document
      .getElementById('prevPage')
      ?.addEventListener('click', () => fetchAuthors(currentPage - 1));
    document
      .getElementById('nextPage')
      ?.addEventListener('click', () => fetchAuthors(currentPage + 1));
    document
      .getElementById('lastPage')
      ?.addEventListener('click', () => fetchAuthors(totalPages - 1));
  }

  // Xử lý xóa tác giả
  async function handleDelete(event) {
    const authorId = event.target.dataset.id;

    if (!confirm('Bạn có chắc chắn muốn xóa tác giả này không?')) return;

    try {
      const response = await fetch(`${apiUrl}/${authorId}`, {
        method: 'DELETE',
      });
      if (!response.ok) throw new Error('Xóa thất bại');

      alert('Đã xóa tác giả thành công');

      // Kiểm tra nếu trang hiện tại trống sau khi xóa thì quay lại trang trước
      const responseCheck = await fetch(
        `${apiUrl}?page=${currentPage}&size=${pageSize}`
      );
      const dataCheck = await responseCheck.json();

      if (dataCheck.numberOfElements === 0 && currentPage > 0) {
        fetchAuthors(currentPage - 1);
      } else {
        fetchAuthors(currentPage);
      }
    } catch (error) {
      console.error(error);
      alert('Lỗi khi xóa tác giả. Có thể tác giả vẫn còn sách liên quan.');
    }
  }

  // Xử lý thêm tác giả mới
  addAuthorForm.addEventListener('submit', async (event) => {
    event.preventDefault();

    const authorName = document.getElementById('promoName').value;
    const dateOfBirth = document.getElementById('startDate').value;

    if (!authorName || !dateOfBirth) {
      alert('Vui lòng điền đầy đủ thông tin');
      return;
    }

    try {
      const response = await fetch(apiUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          authorName,
          dateOfBirth,
        }),
      });
      if (!response.ok) throw new Error('Không thể thêm tác giả mới');

      alert('Tác giả đã được thêm thành công');
      // Sau khi thêm, chuyển đến trang cuối cùng để xem item mới
      fetchAuthors(totalPages - 1);
      addAuthorForm.reset();
    } catch (error) {
      console.error(error);
      alert('Lỗi khi thêm tác giả');
    }
  });

  // Tải danh sách khi trang load
  fetchAuthors();
});
