document.addEventListener('DOMContentLoaded', function () {
  const bookList = document.getElementById('bookList');

  // Dữ liệu mẫu (có thể thay thế bằng API)
  let books = [
    {
      id: 1,
      name: 'Sách A',
      author: 'Tác giả 1',
      price: 100000,
      status: 'Còn hàng',
      image: 'book1.jpg',
    },
    {
      id: 2,
      name: 'Sách B',
      author: 'Tác giả 2',
      price: 120000,
      status: 'Hết hàng',
      image: 'book2.jpg',
    },
  ];

  // Render danh sách sách
  function renderBooks() {
    bookList.innerHTML = '';
    books.forEach((book, index) => {
      const row = document.createElement('tr');
      row.innerHTML = `
                <td><img src="${book.image}" alt="Sách" width="50"></td>
                <td>${book.name}</td>
                <td>${book.author}</td>
                <td>${book.price.toLocaleString()} VND</td>
                <td>${book.status}</td>
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
  function handleEdit(event) {
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
        <label>Tên sách:</label>
        <input type="text" id="editName" value="${book.name}">
        <label>Giá:</label>
        <input type="number" id="editPrice" value="${book.price}">
        <label>Trạng thái:</label>
        <select id="editStatus">
            <option value="Còn hàng" ${
              book.status === 'Còn hàng' ? 'selected' : ''
            }>Còn hàng</option>
            <option value="Hết hàng" ${
              book.status === 'Hết hàng' ? 'selected' : ''
            }>Hết hàng</option>
        </select>
        <button id="saveEdit">Lưu</button>
        <button id="cancelEdit">Hủy</button>
    `;

    // Thêm form và overlay vào body
    document.body.appendChild(overlay);
    document.body.appendChild(editForm);

    // Xử lý khi lưu chỉnh sửa
    document.getElementById('saveEdit').addEventListener('click', function () {
      book.name = document.getElementById('editName').value;
      book.price = parseInt(document.getElementById('editPrice').value);
      book.status = document.getElementById('editStatus').value;
      closeEditForm();
      renderBooks();
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
  function handleDelete(event) {
    const bookId = event.target.dataset.id;
    books = books.filter((book) => book.id != bookId);
    renderBooks();
  }

  // Khởi chạy
  renderBooks();
});
