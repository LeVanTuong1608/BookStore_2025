document.addEventListener('DOMContentLoaded', () => {
  const authorList = document.getElementById('authorList');
  const apiUrl = 'http://localhost:8080/api/authors';
  const addAuthorForm = document.getElementById('addPromotionForm'); // Select the add author form

  // Lấy danh sách tác giả
  async function fetchAuthors() {
    try {
      const response = await fetch(apiUrl);
      if (!response.ok) throw new Error('Không thể tải dữ liệu tác giả');
      const authors = await response.json();
      renderAuthors(authors);
    } catch (error) {
      console.error(error);
      alert('Lỗi khi lấy danh sách tác giả.');
    }
  }

  // Hiển thị danh sách tác giả
  function renderAuthors(authors) {
    authorList.innerHTML = '';
    authors.forEach((author, index) => {
      const row = document.createElement('tr');
      row.innerHTML = `
        <td>${index + 1}</td>
        <td>${author.authorName}</td>
        <td>${author.dateOfBirth}</td> <!-- Hiển thị ngày sinh -->
        <td>
          <button class="delete-btn" data-id="${
            author.authorId
          }">Xóa</button> <!-- Sử dụng authorId để xóa -->
        </td>
      `;
      authorList.appendChild(row);
    });

    // Gắn sự kiện xóa
    document.querySelectorAll('.delete-btn').forEach((button) => {
      button.addEventListener('click', handleDelete);
    });
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
      fetchAuthors();
    } catch (error) {
      console.error(error);
      alert('Lỗi khi xóa tác giả. Có thể tác giả vẫn còn sách liên quan.');
    }
  }

  // Xử lý thêm tác giả mới
  addAuthorForm.addEventListener('submit', async (event) => {
    event.preventDefault(); // Ngừng gửi form mặc định

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

      const createdAuthor = await response.json(); // Nhận tác giả đã tạo từ phản hồi API
      alert('Tác giả đã được thêm thành công');
      fetchAuthors(); // Cập nhật lại danh sách tác giả
      addAuthorForm.reset(); // Làm trống form
    } catch (error) {
      console.error(error);
      alert('Lỗi khi thêm tác giả');
    }
  });

  // Tải danh sách khi trang load
  fetchAuthors();
});
