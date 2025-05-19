document.addEventListener('DOMContentLoaded', function () {
  const bookList = document.getElementById('bookList');
  const API_URL = 'http://localhost:8080/admin/api/books';

  // Tạo phần điều khiển phân trang
  const paginationContainer = document.createElement('div');
  paginationContainer.id = 'paginationContainer';
  paginationContainer.style.margin = '20px 0';
  bookList.parentNode.insertBefore(paginationContainer, bookList.nextSibling);

  let currentPage = 0;
  let totalPages = 1;
  const pageSize = 12; // Số lượng item mỗi trang

  // Thêm credentials để gửi session cookie
  const fetchOptions = {
    credentials: 'include',
    headers: {
      'Content-Type': 'application/json',
    },
  };

  function handleError(err, msg) {
    console.error(msg, err);
    alert(`Có lỗi: ${msg}`);
  }

  // Hàm lấy dữ liệu sách có phân trang
  async function fetchBooks(page = 0) {
    try {
      bookList.innerHTML = '<tr><td colspan="7">Đang tải...</td></tr>';

      const response = await fetch(
        `${API_URL}?page=${page}&size=${pageSize}`,
        fetchOptions
      );

      if (response.status === 403) {
        alert(
          'Bạn không có quyền truy cập. Vui lòng đăng nhập với tài khoản admin.'
        );
        window.location.href = '../login.html';
        return;
      }

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();
      currentPage = data.number;
      totalPages = data.totalPages;

      renderBooks(data.content);
      updatePaginationControls(data);
    } catch (error) {
      console.error('Error fetching books:', error);
      bookList.innerHTML = `<tr><td colspan="7">Không thể tải dữ liệu sách. Vui lòng thử lại sau.</td></tr>`;
    }
  }

  // Render danh sách sách
  function renderBooks(books) {
    if (!bookList) {
      console.error('Không tìm thấy phần tử bookList');
      return;
    }

    bookList.innerHTML = '';

    if (!books || books.length === 0) {
      bookList.innerHTML = `<tr><td colspan="7">Không có sách nào</td></tr>`;
      return;
    }

    const startIndex = currentPage * pageSize;

    books.forEach((book, index) => {
      const row = document.createElement('tr');
      row.innerHTML = `
        <td><img src="${book.imageUrl || '--'}" 
                 alt="${book.title || 'Sách'}" 
                 width="50" 
                 onerror="this.src='--'"></td>
        <td>${escapeHtml(book.title)}</td>
        <td>${escapeHtml(book.authorName)}</td>
        <td>${book.price ? book.price.toLocaleString() + ' VND' : 'N/A'}</td>
        <td>${escapeHtml(book.publisher)}</td>
        <td>${escapeHtml(book.category)}</td>
        <td>
          <button class="edit-btn" data-id="${book.bookId}">Sửa</button>
          <button class="delete-btn" data-id="${book.bookId}">Xóa</button>
        </td>
      `;
      bookList.appendChild(row);
    });

    attachEventListeners();
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
        <span>(${data.totalElements} sách)</span>
      </div>
    `;

    // Gắn sự kiện cho các nút phân trang
    document
      .getElementById('firstPage')
      ?.addEventListener('click', () => fetchBooks(0));
    document
      .getElementById('prevPage')
      ?.addEventListener('click', () => fetchBooks(currentPage - 1));
    document
      .getElementById('nextPage')
      ?.addEventListener('click', () => fetchBooks(currentPage + 1));
    document
      .getElementById('lastPage')
      ?.addEventListener('click', () => fetchBooks(totalPages - 1));
  }

  // Gắn sự kiện cho các nút sửa và xóa
  function attachEventListeners() {
    document.querySelectorAll('.edit-btn').forEach((button) => {
      button.addEventListener('click', handleEdit);
    });

    document.querySelectorAll('.delete-btn').forEach((button) => {
      button.addEventListener('click', handleDelete);
    });
  }

  // Xử lý chỉnh sửa
  async function handleEdit(event) {
    const bookId = event.target.dataset.id;
    try {
      const response = await fetch(`${API_URL}/${bookId}`, fetchOptions);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const book = await response.json();
      if (!book) return;

      // Tạo modal với giao diện đẹp hơn
      const editModal = document.createElement('div');
      editModal.classList.add('edit-modal');
      editModal.innerHTML = `
            <div class="modal-overlay"></div>
            <div class="modal-container">
                <div class="modal-header">
                    <h3>Chỉnh sửa thông tin sách</h3>
                    <button class="close-btn">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="editTitle">Tiêu đề:</label>
                        <input type="text" id="editTitle" value="${escapeHtml(
                          book.title || ''
                        )}">
                    </div>
                    <div class="form-group">
                        <label for="editPrice">Giá (VND):</label>
                        <input type="number" id="editPrice" value="${
                          book.price || ''
                        }">
                    </div>
                    <div class="form-group">
                        <label for="editPublisher">Nhà xuất bản:</label>
                        <input type="text" id="editPublisher" value="${escapeHtml(
                          book.publisher || ''
                        )}">
                    </div>
                    <div class="form-group">
                        <label for="editCategory">Thể loại:</label>
                        <input type="text" id="editCategory" value="${escapeHtml(
                          book.category || ''
                        )}">
                    </div>
                    <div class="form-group">
                        <label for="editImageUrl">URL hình ảnh:</label>
                        <input type="text" id="editImageUrl" value="${escapeHtml(
                          book.imageUrl || ''
                        )}">
                        <div class="image-preview">
                            <img src="${
                              book.imageUrl || '#'
                            }" id="imagePreview" onerror="this.style.display='none'">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" id="cancelEdit">Hủy bỏ</button>
                    <button class="btn btn-primary" id="saveEdit">Lưu thay đổi</button>
                </div>
            </div>
        `;

      document.body.appendChild(editModal);
      document.body.style.overflow = 'hidden'; // Ngăn scroll khi modal mở

      // Xử lý sự kiện đóng modal
      const closeModal = () => {
        document.body.removeChild(editModal);
        document.body.style.overflow = '';
      };

      editModal
        .querySelector('.close-btn')
        .addEventListener('click', closeModal);
      editModal
        .querySelector('.modal-overlay')
        .addEventListener('click', closeModal);
      document
        .getElementById('cancelEdit')
        .addEventListener('click', closeModal);

      // Xem trước ảnh khi thay đổi URL
      document
        .getElementById('editImageUrl')
        .addEventListener('input', function () {
          const preview = document.getElementById('imagePreview');
          preview.src = this.value;
          preview.style.display = this.value ? 'block' : 'none';
        });

      // Xử lý lưu thay đổi
      document
        .getElementById('saveEdit')
        .addEventListener('click', async () => {
          try {
            const updatedBook = {
              title: document.getElementById('editTitle').value.trim(),
              // authorName: document.getElementById('editAuthor').value.trim(),
              price: parseInt(document.getElementById('editPrice').value) || 0,
              publisher: document.getElementById('editPublisher').value.trim(),
              category: document.getElementById('editCategory').value.trim(),
              imageUrl: document.getElementById('editImageUrl').value.trim(),
            };

            // Validate dữ liệu
            if (!updatedBook.title) {
              alert('Vui lòng nhập tiêu đề sách');
              return;
            }

            // if (!updatedBook.authorName) {
            //   alert('Vui lòng nhập tên tác giả');
            //   return;
            // }

            const updateResponse = await fetch(`${API_URL}/${bookId}`, {
              ...fetchOptions,
              method: 'PUT',
              body: JSON.stringify(updatedBook),
            });

            if (!updateResponse.ok) {
              const errorData = await updateResponse.json();
              throw new Error(
                errorData.message ||
                  `Lỗi HTTP! status: ${updateResponse.status}`
              );
            }

            closeModal();
            fetchBooks(currentPage);

            // Hiệu ứng thông báo thành công
            showNotification('Cập nhật sách thành công!', 'success');
          } catch (error) {
            console.error('Lỗi khi cập nhật sách:', error);
            showNotification(`Lỗi: ${error.message}`, 'error');
          }
        });
    } catch (error) {
      console.error('Lỗi khi tải thông tin sách:', error);
      showNotification('Không thể tải thông tin sách để chỉnh sửa', 'error');
    }
  }

  // Xử lý xóa sách
  async function handleDelete(event) {
    if (!confirm('Bạn có chắc chắn muốn xóa sách này?')) return;

    const bookId = event.target.dataset.id;

    try {
      const response = await fetch(`${API_URL}/${bookId}`, {
        ...fetchOptions,
        method: 'DELETE',
      });

      if (!response.ok) {
        const errorText = await response.text();
        console.error(`Error: ${errorText}`);
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      // Kiểm tra nếu trang hiện tại trống sau khi xóa
      const checkResponse = await fetch(
        `${API_URL}?page=${currentPage}&size=${pageSize}`,
        fetchOptions
      );
      const checkData = await checkResponse.json();

      if (checkData.numberOfElements === 0 && currentPage > 0) {
        fetchBooks(currentPage - 1);
      } else {
        fetchBooks(currentPage);
      }
    } catch (error) {
      console.error('Error deleting book:', error);
      alert('Có lỗi khi xóa sách');
    }
  }

  // Hàm phòng chống XSS
  function escapeHtml(unsafe) {
    return unsafe
      ? unsafe
          .toString()
          .replace(/&/g, '&amp;')
          .replace(/</g, '&lt;')
          .replace(/>/g, '&gt;')
          .replace(/"/g, '&quot;')
          .replace(/'/g, '&#039;')
      : 'N/A';
  }

  // Khởi chạy
  fetchBooks();
});
