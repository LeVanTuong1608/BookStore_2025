document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('addPromotionForm');
  const authorList = document.getElementById('authorList');

  // Dữ liệu mẫu tác giả (có thể thay bằng API sau này)
  let authors = [
    { name: 'Nguyễn Nhật Ánh', bookCount: 10, status: 'Hoạt động' },
    { name: 'J.K. Rowling', bookCount: 7, status: 'Hoạt động' },
  ];

  // Hiển thị danh sách tác giả
  function renderAuthors() {
    authorList.innerHTML = '';
    authors.forEach((author, index) => {
      const row = document.createElement('tr');
      row.innerHTML = `
                <td>${index + 1}</td>
                <td>${author.name}</td>
                <td>${author.bookCount}</td>
                <td>${author.status}</td>
            `;
      authorList.appendChild(row);
    });
  }

  // Xử lý thêm tác giả mới
  form.addEventListener('submit', (event) => {
    event.preventDefault();
    const authorName = document.getElementById('promoName').value.trim();
    const birthDate = document.getElementById('startDate').value;

    if (!authorName || !birthDate) {
      alert('Vui lòng nhập đầy đủ thông tin.');
      return;
    }

    authors.push({ name: authorName, bookCount: 0, status: 'Hoạt động' });
    renderAuthors();
    form.reset();
    alert('Thêm tác giả thành công!');
  });

  // Xử lý nút Hủy
  document.querySelector('.cancel-btn').addEventListener('click', () => {
    form.reset();
  });

  renderAuthors();
});
