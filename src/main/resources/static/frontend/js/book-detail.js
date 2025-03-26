document.addEventListener('DOMContentLoaded', function () {
  const urlParams = new URLSearchParams(window.location.search);
  const bookId = parseInt(urlParams.get('id'));

  // TODO: Sau này sẽ thay thế bằng API call
  const books = [
    {
      id: 1,
      title: 'Thiên Tài Bên Trái',
      author: 'Takuya Sato',
      year: 2021,
      category: 'văn học',
      description:
        'Cuốn sách khám phá tư duy sáng tạo và chiến lược thành công.',
      price: 899999,
      discount: 10,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
    },
    {
      id: 2,
      title: 'Làm gì - John Gray',
      author: 'John Gray',
      category: 'kinh tế',
      year: 2019,
      description: 'Hướng dẫn giúp bạn tìm ra con đường phù hợp với bản thân.',
      price: 160000,
      discount: 5,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
    },
    // Thêm sách khác nếu cần
  ];

  // Tìm sách theo ID
  const book = books.find((b) => b.id === bookId);

  if (book) {
    document.getElementById('book-image').src = book.image;
    document.getElementById('book-title').textContent = book.title;
    document.getElementById('book-author').textContent = book.author;
    document.getElementById('book-year').textContent = book.year;
    document.getElementById('book-category').textContent = book.category;
    document.getElementById('book-description').textContent = book.description;
    document.getElementById('book-price').textContent = `${(
      book.price *
      (1 - book.discount / 100)
    ).toLocaleString()} đ`;

    const originalPriceElem = document.getElementById('book-original-price');
    if (book.discount > 0) {
      originalPriceElem.textContent = `${book.price.toLocaleString()} đ`;
    } else {
      originalPriceElem.style.display = 'none';
    }
  } else {
    document.getElementById('book-detail-container').innerHTML =
      '<p>Sách không tồn tại!</p>';
    return;
  }

  // Lấy số lượng từ input
  function getQuantity() {
    return parseInt(document.getElementById('quantity').value) || 1;
  }

  // Thêm sản phẩm vào giỏ hàng
  function addToCart(book, quantity) {
    let cart = JSON.parse(localStorage.getItem('cart')) || []; // Lấy giỏ hàng từ localStorage
    const existingItem = cart.find((item) => item.id === book.id);

    if (existingItem) {
      existingItem.quantity += quantity; // Cập nhật số lượng nếu sản phẩm đã tồn tại
    } else {
      cart.push({ ...book, quantity }); // Thêm sản phẩm mới vào giỏ hàng
    }

    localStorage.setItem('cart', JSON.stringify(cart)); // Lưu giỏ hàng vào localStorage
  }

  // Xử lý sự kiện "Thêm vào giỏ hàng"
  document.getElementById('add-to-cart').addEventListener('click', function () {
    let quantity = getQuantity();
    addToCart(book, quantity);
    alert(`Đã thêm ${quantity} cuốn "${book.title}" vào giỏ hàng!`);
  });

  // Xử lý sự kiện "Mua ngay"
  document.getElementById('buy-now').addEventListener('click', function () {
    let quantity = getQuantity();
    addToCart(book, quantity); // Thêm sản phẩm vào giỏ hàng trước
    window.location.href = '../frontend/checkout.html'; // Sau đó chuyển hướng đến trang thanh toán
  });

  // Xử lý sự kiện "Quay lại trang sản phẩm"
  document
    .getElementById('back-to-products')
    .addEventListener('click', function () {
      window.location.href = '../frontend/products.html'; // Cập nhật đúng đường dẫn trang danh sách sách
    });
});
