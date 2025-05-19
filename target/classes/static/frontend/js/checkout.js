document.addEventListener('DOMContentLoaded', async function () {
  const qrCodeContainer = document.getElementById('qr-code');
  const totalPriceElem = document.getElementById('checkout-total-price');
  const bookInfoContainer = document.getElementById('book-info');
  const confirmPaymentBtn = document.getElementById('confirm-payment');
  const closeBtn = document.getElementById('close-checkout');

  let cartItems = [];

  async function fetchBookDetail(bookId) {
    const response = await fetch(
      `http://localhost:8080/user/api/books/${bookId}`
    );
    if (!response.ok)
      throw new Error(`Lỗi lấy thông tin sách với id ${bookId}`);
    return response.json();
  }

  async function fetchCart() {
    try {
      const response = await fetch('http://localhost:8080/user/api/carts', {
        credentials: 'include',
      });

      if (response.status === 401) {
        alert('Bạn cần đăng nhập để thanh toán!');
        window.location.href = 'login.html';
        return;
      }

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();
      const items = data.content;

      const detailedCartItems = await Promise.all(
        items.map(async (item) => {
          const book = await fetchBookDetail(item.bookId);
          return {
            cartId: item.cartId,
            bookId: item.bookId,
            quantity: item.quantity,
            title: book.title,
            price: book.price,
            image: book.imageUrl,
          };
        })
      );

      cartItems = detailedCartItems;
      renderCart();
    } catch (error) {
      console.error('Lỗi khi lấy giỏ hàng:', error);
      alert('Không thể tải giỏ hàng.');
      window.location.href = 'index.html';
    }
  }

  function renderCart() {
    bookInfoContainer.innerHTML = '';
    let total = 0;

    cartItems.forEach((item) => {
      const itemTotal = item.price * item.quantity;
      total += itemTotal;

      bookInfoContainer.innerHTML += `
        <div class="checkout-item">
          <p><strong>${item.title}</strong></p>
          <p>Số lượng: ${item.quantity}</p>
          <p>Giá: ${item.price.toLocaleString()} đ</p>
          <p><strong>Tổng: ${itemTotal.toLocaleString()} đ</strong></p>
          <hr>
        </div>
      `;
    });

    totalPriceElem.textContent = `${total.toLocaleString()} đ`;
    generateQRCode(total);
  }

  function generateQRCode(amount) {
    const qrImagePath = '../frontend/assets/images/thanh-toan-hoa-don.jpg';
    qrCodeContainer.innerHTML = `
      <img src="${qrImagePath}" alt="QR Code" />
      <p>Vui lòng quét mã để thanh toán ${amount.toLocaleString()} đ</p>
    `;
  }

  confirmPaymentBtn.addEventListener('click', async function () {
    try {
      // 1. Tính tổng tiền từ giỏ hàng
      const totalAmount = cartItems.reduce((sum, item) => {
        return sum + item.price * item.quantity;
      }, 0);

      // 2. Tạo đối tượng đơn hàng
      const orderData = {
        // orderDate: new Date().toISOString(), // thời gian hiện tại
        totalAmount: totalAmount,
        status: 'pending',
        discountId: null,
      };

      // 3. Gọi API tạo đơn hàng
      const createOrderResponse = await fetch(
        'http://localhost:8080/admin/api/orders',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          credentials: 'include',
          body: JSON.stringify(orderData),
        }
      );

      if (createOrderResponse.status === 401) {
        alert('Bạn cần đăng nhập để tạo đơn hàng!');
        window.location.href = 'login.html';
        return;
      }

      if (!createOrderResponse.ok) {
        const errorData = await createOrderResponse.json();
        alert(`Lỗi tạo đơn hàng: ${errorData.message || 'Có lỗi xảy ra!'}`);
        return;
      }

      const response = await fetch(
        'http://localhost:8080/user/api/carts/checkout',
        {
          method: 'PUT', // ✅ Dùng PUT thay vì POST
          credentials: 'include',
        }
      );

      if (response.status === 401) {
        alert('Bạn cần đăng nhập để thanh toán!');
        window.location.href = 'login.html';
        return;
      }

      if (!response.ok) {
        const errorData = await response.json();
        alert(`Lỗi thanh toán: ${errorData.message || 'Có lỗi xảy ra!'}`);
        return;
      }

      alert('Thanh toán thành công!');
      window.location.href = 'index.html';
    } catch (error) {
      console.error('Lỗi khi thanh toán:', error);
      alert('Không thể thực hiện thanh toán lúc này.');
    }
  });

  closeBtn.addEventListener('click', function () {
    window.location.href = 'index.html';
  });

  fetchCart();
});
