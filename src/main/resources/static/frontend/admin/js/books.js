document.addEventListener('DOMContentLoaded', function () {
  const bookList = document.getElementById('bookList');
  let books = []; // Biến lưu trữ dữ liệu sách từ API

  // URL của API
  const apiUrl = 'http://localhost:8080/api/books';

  // Hàm lấy danh sách sách từ API
  async function fetchBooks() {
    try {
      const response = await fetch(apiUrl);
      if (!response.ok) {
        throw new Error('Không thể tải dữ liệu Sách');
      }
      books = await response.json();
      renderBooks(); // Gọi renderBooks để hiển thị sách
    } catch (error) {
      console.error(error);
      alert('Lỗi khi lấy danh sách Sách.');
    }
  }

  // Render danh sách sách
  function renderBooks() {
    bookList.innerHTML = ''; // Xóa danh sách cũ
    books.forEach((book) => {
      const row = document.createElement('tr');
      row.innerHTML = `
                <td><img src="${
                  book.imageUrl || 'https://via.placeholder.com/50'
                }" alt="Sách" width="50"></td>
                <td>${book.title}</td>
                <td>${book.authorName}</td>
                <td>${
                  book.price ? book.price.toLocaleString() + ' VND' : 'N/A'
                }</td>
                <td>${book.publisher || 'N/A'}</td>
                <td>${book.category || 'N/A'}</td>
                <td>
                    <button class="edit-btn" data-id="${book.id}">Sửa</button>
                    <button class="delete-btn" data-id="${book.id}">Xóa</button>
                </td>
            `;
      bookList.appendChild(row);
    });

    // Gắn sự kiện cho nút Sửa và Xóa
    document.querySelectorAll('.edit-btn').forEach((button) => {
      button.addEventListener('click', handleEdit);
    });

    document.querySelectorAll('.delete-btn').forEach((button) => {
      button.addEventListener('click', handleDelete);
    });
  }

  // Xử lý khi nhấn nút "Sửa"
  async function handleEdit(event) {
    const bookId = event.target.dataset.id;
    const book = books.find((b) => b.id == bookId);
    if (!book) return;

    // Tạo overlay nền mờ
    const overlay = document.createElement('div');
    overlay.classList.add('overlay');

    // Tạo form chỉnh sửa
    const editForm = document.createElement('div');
    editForm.classList.add('edit-form');
    editForm.innerHTML = `
        <h3>Chỉnh sửa sách</h3>
        <label>Tiêu đề:</label>
        <input type="text" id="editTitle" value="${book.title || ''}">
        
        <label>Tác giả:</label>
        <input type="text" id="editAuthor" value="${book.authorName || ''}">
        
        <label>Giá (VND):</label>
        <input type="number" id="editPrice" value="${book.price || ''}">
        
        <label>Nhà xuất bản:</label>
        <input type="text" id="editPublisher" value="${book.publisher || ''}">
        
        <label>Thể loại:</label>
        <input type="text" id="editCategory" value="${book.category || ''}">
        
        <label>URL hình ảnh:</label>
        <input type="text" id="editImageUrl" value="${book.imageUrl || ''}">
        
        <div class="form-actions">
            <button id="saveEdit">Lưu</button>
            <button id="cancelEdit">Hủy</button>
        </div>
    `;

    // Thêm form và overlay vào body
    document.body.appendChild(overlay);
    document.body.appendChild(editForm);

    // Xử lý khi lưu chỉnh sửa
    document
      .getElementById('saveEdit')
      .addEventListener('click', async function () {
        try {
          const updatedBook = {
            ...book,
            title: document.getElementById('editTitle').value,
            authorName: document.getElementById('editAuthor').value,
            price: parseInt(document.getElementById('editPrice').value),
            publisher: document.getElementById('editPublisher').value,
            category: document.getElementById('editCategory').value,
            imageUrl: document.getElementById('editImageUrl').value,
          };

          const response = await fetch(`${apiUrl}/${bookId}`, {
            method: 'PUT',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(updatedBook),
          });

          if (!response.ok) {
            throw new Error('Cập nhật thất bại');
          }

          // Cập nhật danh sách local
          const index = books.findIndex((b) => b.id == bookId);
          if (index !== -1) {
            books[index] = updatedBook;
          }

          closeEditForm();
          renderBooks();
        } catch (error) {
          console.error(error);
          alert('Lỗi khi cập nhật sách');
        }
      });

    // Hủy chỉnh sửa
    document
      .getElementById('cancelEdit')
      .addEventListener('click', closeEditForm);

    // Đóng form khi bấm vào overlay
    overlay.addEventListener('click', closeEditForm);

    function closeEditForm() {
      document.body.removeChild(editForm);
      document.body.removeChild(overlay);
    }
  }

  // Xử lý khi nhấn nút "Xóa"
  async function handleDelete(event) {
    const bookId = event.target.dataset.id;

    if (!confirm('Bạn có chắc chắn muốn xóa sách này?')) {
      return;
    }

    try {
      const response = await fetch(`${apiUrl}/${bookId}`, {
        method: 'DELETE',
      });

      if (!response.ok) {
        throw new Error('Xóa thất bại');
      }

      // Cập nhật danh sách local
      books = books.filter((book) => book.id != bookId);
      renderBooks();
    } catch (error) {
      console.error(error);
      alert('Lỗi khi xóa sách');
    }
  }

  // Khởi chạy
  fetchBooks(); // Lấy dữ liệu từ API khi trang được tải
});
