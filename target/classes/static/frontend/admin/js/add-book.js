document.addEventListener('DOMContentLoaded', () => {
  const addBookForm = document.getElementById('addBookForm');

  // Load danh sách tác giả
  async function loadSelectOptions(selectId, apiUrl) {
    try {
      const res = await fetch(apiUrl);
      const data = await res.json();
      const select = document.getElementById(selectId);
      select.innerHTML = '<option value="">Select...</option>';
      data.forEach((item) => {
        const option = document.createElement('option');
        option.value = JSON.stringify(item);
        option.textContent = item.authorName;
        select.appendChild(option);
      });
    } catch (e) {
      console.error('Lỗi tải tác giả:', e);
    }
  }

  loadSelectOptions('author', 'http://localhost:8080/admin/api/authors/all');

  addBookForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    // Lấy dữ liệu từ form
    const title = document.getElementById('bookName').value.trim();
    const authorRaw = document.getElementById('author').value;
    const category = document.getElementById('category').value.trim();
    const price = parseFloat(document.getElementById('price').value);
    const description = document.getElementById('description').value.trim();
    const publisher = document.getElementById('publisher').value.trim();
    const publicationYear =
      parseInt(document.getElementById('publishYear')?.value) || null;
    const dimensions = document.getElementById('size').value.trim();
    const imageFile = document.getElementById('bookImage').files[0];

    if (!title || !authorRaw || !category || isNaN(price) || !dimensions) {
      alert('Vui lòng nhập đầy đủ thông tin bắt buộc.');
      return;
    }

    let imageUrl = '';

    if (imageFile) {
      const imageForm = new FormData();
      imageForm.append('file', imageFile);
      try {
        const uploadRes = await fetch(
          'http://localhost:8080/api/images/upload',
          {
            method: 'POST',
            body: imageForm,
          }
        );
        if (!uploadRes.ok) throw new Error('Upload ảnh thất bại');

        const uploadData = await uploadRes.json();
        imageUrl = uploadData.secure_url?.trim();

        if (!imageUrl) {
          alert('Upload ảnh thành công nhưng không nhận được URL.');
          return;
        }
      } catch (error) {
        console.error('Lỗi upload ảnh:', error);
        alert('Không thể upload ảnh.');
        return;
      }
    }

    const authorObj = JSON.parse(authorRaw);

    const bookData = {
      title,
      imageUrl,
      category,
      price,
      author: {
        authorId: authorObj.authorId,
        authorName: authorObj.authorName,
        dateOfBirth: authorObj.dateOfBirth || null,
      },
      description,
      publisher,
      publicationYear,
      dimensions,
    };

    try {
      const bookRes = await fetch('http://localhost:8080/admin/api/books', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(bookData),
      });

      if (!bookRes.ok) throw new Error('Không thể thêm sách');

      const result = await bookRes.json();
      alert('Thêm sách thành công!');
      addBookForm.reset();
    } catch (error) {
      console.error('Lỗi khi thêm sách:', error);
      alert('Có lỗi xảy ra khi thêm sách.');
    }
  });
});
