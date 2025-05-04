document.addEventListener('DOMContentLoaded', () => {
  const addBookForm = document.getElementById('addBookForm');

  // Hàm để tải danh sách tác giả từ API
  function loadSelectOptions(selectId, apiUrl) {
    console.log('Loading data from API...');
    fetch(apiUrl)
      .then((response) => response.json())
      .then((data) => {
        console.log('Data loaded:', data);
        const select = document.getElementById(selectId);
        select.innerHTML = '<option value="">Chọn tác giả...</option>'; // Add a default option
        data.forEach((item) => {
          const option = document.createElement('option');
          option.value = item.id; // Use the author's ID
          option.textContent = item.name; // Display the author's name
          select.appendChild(option);
        });
      })
      .catch((error) => console.error('Error loading data:', error));
  }

  // Tải dữ liệu từ API cho tác giả
  loadSelectOptions('author', '/api/authors'); // Make sure '/api/authors' is the correct API endpoint

  // Xử lý sự kiện khi gửi form
  addBookForm.addEventListener('submit', (event) => {
    event.preventDefault();

    const bookData = {
      title: document.getElementById('bookName').value.trim(),
      authorName: document.getElementById('author').value, // Get the selected author name (should be ID)
      category: document.getElementById('category').value.trim(),
      price: parseFloat(document.getElementById('price').value),
      description: document.getElementById('description').value.trim(),
      publisher: document.getElementById('publisher').value.trim(),
      publicationYear: document.getElementById('publishYear').value.trim(),
      dimensions: document.getElementById('size').value.trim(),
      bookImage: document.getElementById('bookImage').files[0], // Handle the file (image)
    };

    // Kiểm tra dữ liệu
    if (
      !bookData.title ||
      !bookData.authorName || // Ensure the author is selected (use authorId in your backend)
      !bookData.category ||
      !bookData.price ||
      !bookData.dimensions
    ) {
      alert('Vui lòng nhập đầy đủ thông tin bắt buộc.');
      return;
    }

    console.log('Dữ liệu sách:', bookData);

    // Gửi dữ liệu lên API (POST request)
    const formData = new FormData();
    formData.append('title', bookData.title);
    formData.append('bookImage', bookData.bookImage);
    formData.append('category', bookData.category);
    formData.append('price', bookData.price);
    formData.append('author_name', bookData.authorName); // Send the author's name
    formData.append('description', bookData.description);
    formData.append('publisher', bookData.publisher);
    formData.append('publication_year', bookData.publicationYear);
    formData.append('dimensions', bookData.dimensions);

    // Post the form data to the backend API
    fetch('/api/books', {
      method: 'POST',
      body: formData,
    })
      .then((response) => response.json())
      .then((data) => {
        console.log('Sách đã được thêm:', data);
        alert('Sách đã được thêm thành công!');
        // Reset the form after successful submission (optional)
        addBookForm.reset();
      })
      .catch((error) => {
        console.error('Có lỗi xảy ra khi thêm sách:', error);
        alert('Có lỗi xảy ra, vui lòng thử lại.');
      });
  });
});
