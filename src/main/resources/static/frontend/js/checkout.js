document.addEventListener('DOMContentLoaded', async function () {
  const user = JSON.parse(localStorage.getItem('user'));
  if (!user) {
    alert('Bạn cần đăng nhập để thanh toán!');
    window.location.href = 'login.html';
    return;
  }

  const urlParams = new URLSearchParams(window.location.search);
  const bookId = urlParams.get('id');
  const qrCodeContainer = document.getElementById('qr-code');
  const totalPriceElem = document.getElementById('checkout-total-price');
  let totalPrice = 0;

  // Dữ liệu giả lập (không cần API thật)
  const books = [
    { id: 1, title: 'Thiên Tài Bên Trái', price: 899999 },
    { id: 2, title: 'Làm gì - John Gray', price: 160000 },
  ];
  const cart = [
    { id: 1, title: 'Thiên Tài Bên Trái', price: 899999, quantity: 1 },
    { id: 2, title: 'Làm gì - John Gray', price: 160000, quantity: 2 },
  ];

  if (bookId) {
    const book = books.find((b) => b.id == bookId);
    if (book) {
      document.getElementById('checkout-book-title').textContent = book.title;
      document.getElementById(
        'checkout-book-price'
      ).textContent = `${book.price.toLocaleString()} đ`;
      totalPrice = book.price;
    }
  } else {
    if (cart.length === 0) {
      alert('Giỏ hàng trống!');
      window.location.href = 'index.html';
      return;
    }
    let cartHtml = '';
    cart.forEach((item) => {
      totalPrice += item.price * item.quantity;
      cartHtml += `<p><strong>${item.title}</strong>: ${
        item.quantity
      } x ${item.price.toLocaleString()} đ</p>`;
    });
    document.getElementById('book-info').innerHTML = cartHtml;
  }

  totalPriceElem.textContent = `${totalPrice.toLocaleString()} đ`;
  generateQRCode(totalPrice);

  function generateQRCode(amount) {
    const qrData = `../frontend/assets/images/thanh-toan-hoa-don.jpg`;
    qrCodeContainer.innerHTML = `
        <img src="${qrData}" alt="QR Code">
        <p>Quét mã để thanh toán ${amount.toLocaleString()} đ</p>`;
  }

  document
    .getElementById('confirm-payment')
    .addEventListener('click', function () {
      alert('Thanh toán thành công!');
      window.location.href = 'index.html';
    });

  document
    .getElementById('close-checkout')
    .addEventListener('click', function () {
      window.location.href = 'index.html';
    });
});
