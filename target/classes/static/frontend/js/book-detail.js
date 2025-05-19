// document.addEventListener('DOMContentLoaded', function () {
//   const urlParams = new URLSearchParams(window.location.search);
//   const bookId = parseInt(urlParams.get('id'));

//   // Gọi API để lấy thông tin sách chi tiết
//   fetch(`http://localhost:8080/user/api/books/${bookId}`, {
//     method: 'GET',
//     credentials: 'include', // Đảm bảo cookie (session) được gửi
//   })
//     .then((response) => {
//       if (response.status === 401) {
//         alert('Bạn cần đăng nhập để xem chi tiết sách.');
//         window.location.href = '../frontend/login.html'; // Chuyển hướng tới trang đăng nhập
//         return;
//       }
//       if (!response.ok) {
//         throw new Error('Không tìm thấy sách!');
//       }
//       return response.json();
//     })
//     .then((book) => {
//       // Hiển thị thông tin sách
//       document.getElementById('book-image').src = book.imageUrl;
//       document.getElementById('book-title').textContent = book.title;
//       document.getElementById('book-author').textContent = book.authorName;
//       document.getElementById('book-category').textContent = book.category;
//       document.getElementById('book-year').textContent = book.publicationYear;
//       document.getElementById('book-description').textContent =
//         book.description;
//       document.getElementById(
//         'book-price'
//       ).textContent = `${book.price.toLocaleString()} đ`;

//       // Nếu có giá gốc, hiển thị (nếu cần)
//       const originalPriceElem = document.getElementById('book-original-price');
//       if (book.discount && book.discount > 0) {
//         originalPriceElem.textContent = `${(
//           book.price /
//           (1 - book.discount / 100)
//         ).toLocaleString()} đ`;
//       } else {
//         originalPriceElem.style.display = 'none'; // Nếu không có giảm giá, ẩn giá gốc
//       }
//     })
//     .catch((error) => {
//       console.error(error);
//       document.getElementById('book-detail-container').innerHTML =
//         '<p>Không thể tải chi tiết sách.</p>';
//     });
// });

document.addEventListener('DOMContentLoaded', function () {
  const urlParams = new URLSearchParams(window.location.search);
  const bookId = parseInt(urlParams.get('id'));

  // Gọi API để lấy thông tin chi tiết sách
  fetch(`http://localhost:8080/user/api/books/${bookId}`, {
    method: 'GET',
    credentials: 'include',
  })
    .then((response) => {
      if (response.status === 401) {
        alert('Bạn cần đăng nhập để xem chi tiết sách.');
        window.location.href = '../frontend/login.html';
        return;
      }
      if (!response.ok) {
        throw new Error('Không tìm thấy sách!');
      }
      return response.json();
    })
    .then((book) => {
      // Hiển thị thông tin sách
      document.getElementById('book-image').src = book.imageUrl;
      document.getElementById('book-title').textContent = book.title;
      document.getElementById('book-author').textContent = book.authorName;
      document.getElementById('book-category').textContent = book.category;
      document.getElementById('book-year').textContent = book.publicationYear;
      document.getElementById('book-description').textContent =
        book.description;
      document.getElementById(
        'book-price'
      ).textContent = `${book.price.toLocaleString()} đ`;

      const originalPriceElem = document.getElementById('book-original-price');
      if (book.discount && book.discount > 0) {
        const originalPrice = book.price / (1 - book.discount / 100);
        originalPriceElem.textContent = `${originalPrice.toLocaleString()} đ`;
      } else {
        originalPriceElem.style.display = 'none';
      }
    })
    .catch((error) => {
      console.error(error);
      document.getElementById('book-detail-container').innerHTML =
        '<p>Không thể tải chi tiết sách.</p>';
    });

  // Sự kiện: Thêm vào giỏ hàng
  document.getElementById('add-to-cart').addEventListener('click', () => {
    const quantity = parseInt(document.getElementById('quantity').value);
    fetch('http://localhost:8080/user/api/carts/add', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
      body: JSON.stringify({
        bookId: bookId,
        quantity: quantity,
      }),
    })
      .then((response) => {
        if (response.status === 401) {
          alert('Bạn cần đăng nhập để thêm vào giỏ hàng.');
          window.location.href = '../frontend/login.html';
          return;
        }
        if (!response.ok) {
          throw new Error('Không thể thêm vào giỏ hàng.');
        }
        return response.json();
      })
      .then(() => {
        alert('Đã thêm sách vào giỏ hàng!');
      })
      .catch((error) => {
        console.error(error);
        alert('Có lỗi xảy ra khi thêm vào giỏ hàng.');
      });
  });

  // Sự kiện: Mua ngay
  document.getElementById('buy-now').addEventListener('click', () => {
    const quantity = parseInt(document.getElementById('quantity').value);
    fetch('http://localhost:8080/user/api/carts/add', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
      body: JSON.stringify({
        bookId: bookId,
        quantity: quantity,
      }),
    })
      .then((response) => {
        if (response.status === 401) {
          alert('Bạn cần đăng nhập để mua sách.');
          window.location.href = '../frontend/login.html';
          return;
        }
        if (!response.ok) {
          throw new Error('Không thể thêm vào giỏ hàng.');
        }
        return response.json();
      })
      .then(() => {
        window.location.href = '../frontend/cart.html'; // chuyển sang giỏ hàng
      })
      .catch((error) => {
        console.error(error);
        alert('Có lỗi xảy ra khi mua sách!');
      });
  });

  // Sự kiện: Quay lại trang sản phẩm
  document.getElementById('back-to-products').addEventListener('click', () => {
    window.location.href = '../frontend/index.html';
  });
});
