document.addEventListener('DOMContentLoaded', () => {
  const addBookForm = document.getElementById('addBookForm');

  // Hàm để tải danh sách từ API hoặc dữ liệu mẫu
  function loadSelectOptions(selectId, data) {
    const select = document.getElementById(selectId);
    select.innerHTML = '<option value="">Select...</option>';
    data.forEach((item) => {
      const option = document.createElement('option');
      option.value = item.id;
      option.textContent = item.name;
      select.appendChild(option);
    });
  }

  // Giả lập dữ liệu từ API
  const authors = [
    { id: 1, name: 'Nguyễn Nhật Ánh' },
    { id: 2, name: 'J.K. Rowling' },
  ];
  const categories = [
    { id: 1, name: 'Tiểu thuyết' },
    { id: 2, name: 'Khoa học' },
  ];
  const publishers = [
    { id: 1, name: 'NXB Kim Đồng' },
    { id: 2, name: 'NXB Trẻ' },
  ];

  loadSelectOptions('author', authors);
  loadSelectOptions('category', categories);
  loadSelectOptions('publisher', publishers);

  // Xử lý sự kiện khi gửi form
  addBookForm.addEventListener('submit', (event) => {
    event.preventDefault();

    const bookData = {
      bookId: document.getElementById('bookId').value.trim(),
      bookName: document.getElementById('bookName').value.trim(),
      author: document.getElementById('author').value,
      category: document.getElementById('category').value,
      publisher: document.getElementById('publisher').value,
      publishYear: document.getElementById('publishYear').value,
      pageCount: document.getElementById('pageCount').value,
      size: document.getElementById('size').value.trim(),
      price: parseFloat(document.getElementById('price').value),
      discount: parseFloat(document.getElementById('discount').value),
      description: document.getElementById('description').value.trim(),
      bookImage: document.getElementById('bookImage').files[0],
    };

    if (
      !bookData.bookId ||
      !bookData.bookName ||
      !bookData.author ||
      !bookData.category ||
      !bookData.price
    ) {
      alert('Vui lòng nhập đầy đủ thông tin bắt buộc.');
      return;
    }

    console.log('Dữ liệu sách:', bookData);
    alert('Sách đã được thêm thành công! (Chưa kết nối API)');
  });
});
