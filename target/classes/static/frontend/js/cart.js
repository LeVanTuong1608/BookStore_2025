document.addEventListener('DOMContentLoaded', async function () {
  const cartContainer = document.getElementById('cart-items');
  const totalPriceEl = document.getElementById('total-price');
  const checkoutBtn = document.getElementById('checkout');

  let cartItems = [];

  async function fetchBookDetail(bookId) {
    const response = await fetch(
      `http://localhost:8080/user/api/books/${bookId}`
    );
    if (!response.ok) throw new Error(`Lỗi lấy sách với id ${bookId}`);
    return response.json();
  }

  async function fetchCart() {
    try {
      const response = await fetch('http://localhost:8080/user/api/carts');
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const data = await response.json();

      const detailedCartItems = await Promise.all(
        data.content.map(async (item) => {
          const bookDetail = await fetchBookDetail(item.bookId);
          return {
            id: item.cartId,
            quantity: item.quantity,
            title: bookDetail.title,
            image: bookDetail.imageUrl,
            price: bookDetail.price,
            authorName: bookDetail.authorName,
          };
        })
      );

      cartItems = detailedCartItems;
      renderCart();
    } catch (error) {
      console.error('Lỗi khi lấy giỏ hàng:', error);
      cartContainer.innerHTML = '<p>Không thể tải giỏ hàng.</p>';
      totalPriceEl.textContent = '0';
    }
  }

  function renderCart() {
    cartContainer.innerHTML = '';
    let total = 0;

    cartItems.forEach((item) => {
      const cartItem = document.createElement('div');
      cartItem.classList.add('cart-item');
      cartItem.innerHTML = `
        <img src="${item.image}" alt="${item.title}" class="cart-item-img">
        <div class="cart-item-info">
          <p><strong>${item.title}</strong></p>
          <p>Giá: ${item.price ? item.price.toLocaleString() : 'N/A'} VNĐ</p>
          <p>Số lượng: 
            <button onclick="changeQuantity('${item.id}', -1)">-</button>
            <span>${item.quantity}</span>
            <button onclick="changeQuantity('${item.id}', 1)">+</button>
          </p>
          <p><strong>Tổng:</strong> ${(
            item.price * item.quantity
          ).toLocaleString()} VNĐ</p>
          <button onclick="removeItem('${item.id}')">Xóa</button>
        </div>
      `;
      cartContainer.appendChild(cartItem);
      total += item.price * item.quantity;
    });

    totalPriceEl.textContent = total.toLocaleString();
  }

  window.removeItem = async function (itemId) {
    try {
      const response = await fetch(`/user/api/carts/${itemId}`, {
        method: 'DELETE',
      });
      if (!response.ok)
        throw new Error(`HTTP error! status: ${response.status}`);

      cartItems = cartItems.filter((item) => item.id !== itemId);
      renderCart();
    } catch (error) {
      console.error('Lỗi khi xóa sản phẩm:', error);
      alert('Không thể xóa sản phẩm khỏi giỏ hàng.');
    }
  };

  window.changeQuantity = async function (itemId, delta) {
    const itemIndex = cartItems.findIndex((item) => item.id === itemId);
    if (itemIndex === -1) return;

    const newQuantity = cartItems[itemIndex].quantity + delta;
    if (newQuantity > 0) {
      try {
        const response = await fetch(`/user/api/carts/${itemId}`, {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ quantity: newQuantity }),
        });
        if (!response.ok)
          throw new Error(`HTTP error! status: ${response.status}`);

        const updatedItem = await response.json();

        // Giữ nguyên thông tin sách, chỉ cập nhật số lượng
        cartItems[itemIndex] = {
          ...cartItems[itemIndex],
          quantity: updatedItem.quantity,
        };

        renderCart();
      } catch (error) {
        console.error('Lỗi khi cập nhật số lượng:', error);
        alert('Không thể cập nhật số lượng sản phẩm.');
      }
    } else {
      removeItem(itemId);
    }
  };

  checkoutBtn.addEventListener('click', async function () {
    window.location.href = 'checkout.html'; // Đổi thành URL trang thanh toán bạn muốn

    // try {
    //   const response = await fetch('/user/api/checkout', {
    //     method: 'POST',
    //     headers: { 'Content-Type': 'application/json' },
    //     body: JSON.stringify({ items: cartItems }),
    //   });

    //   if (response.ok) {
    //     const checkoutData = await response.json();
    //     console.log('Thanh toán thành công:', checkoutData);
    //     cartItems = [];
    //     renderCart();
    //     window.location.href = 'order-success.html';
    //   } else {
    //     const errorData = await response.json();
    //     console.error('Lỗi thanh toán:', errorData);
    //     alert(
    //       `Lỗi thanh toán: ${
    //         errorData.message || 'Có lỗi xảy ra trong quá trình thanh toán.'
    //       }`
    //     );
    //   }
    // } catch (error) {
    //   console.error('Lỗi khi gọi API thanh toán:', error);
    //   alert('Không thể thực hiện thanh toán vào lúc này.');
    // }
  });

  fetchCart();
});
