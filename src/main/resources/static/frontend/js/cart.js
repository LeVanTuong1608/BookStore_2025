document.addEventListener('DOMContentLoaded', async function () {
  // Lấy phần tử hiển thị giỏ hàng, tổng giá và nút thanh toán
  const cartContainer = document.getElementById('cart-items');
  const totalPriceEl = document.getElementById('total-price');
  const checkoutBtn = document.getElementById('checkout');

  // Dữ liệu giả lập giỏ hàng
  let cartItems = [
    {
      image:
        'https://storage.googleapis.com/a1aa/image/9-iPYTX9izcMRkztWoF6UIekROB_xhdxk3RpDGSNRyg.jpg',
      title: 'Sách excel 2018',
      price: 100000,
      quantity: 1,
    },
    {
      image:
        'https://storage.googleapis.com/a1aa/image/9-iPYTX9izcMRkztWoF6UIekROB_xhdxk3RpDGSNRyg.jpg',
      title: 'Sách excel 2019',
      price: 150000,
      quantity: 2,
    },
    {
      image:
        'https://storage.googleapis.com/a1aa/image/9-iPYTX9izcMRkztWoF6UIekROB_xhdxk3RpDGSNRyg.jpg',
      title: 'Sách excel 2025',
      price: 200000,
      quantity: 2,
    },
  ];

  // Hàm render giỏ hàng lên giao diện
  function renderCart() {
    cartContainer.innerHTML = '';
    let total = 0;

    cartItems.forEach((item, index) => {
      // Tạo một phần tử hiển thị sản phẩm trong giỏ hàng
      const cartItem = document.createElement('div');
      cartItem.classList.add('cart-item');
      cartItem.innerHTML = `
              <img src="${item.image}" alt="${
        item.title
      }" class="cart-item-img">
              <div class="cart-item-info">
                  <p><strong>${item.title}</strong></p>
                  <p>Giá: ${item.price.toLocaleString()} VNĐ</p>
                  <p>Số lượng: <button onclick="changeQuantity(${index}, -1)">-</button>
                  <span>${item.quantity}</span>
                  <button onclick="changeQuantity(${index}, 1)">+</button></p>
                  <p><strong>Tổng:</strong> ${(
                    item.price * item.quantity
                  ).toLocaleString()} VNĐ</p>
                  <button onclick="removeItem(${index})">Xóa</button>
              </div>
          `;
      cartContainer.appendChild(cartItem);
      total += item.price * item.quantity; // Cộng tổng giá trị giỏ hàng
    });

    totalPriceEl.textContent = total.toLocaleString(); // Cập nhật tổng tiền lên giao diện
  }

  // Hàm xóa sản phẩm khỏi giỏ hàng
  window.removeItem = function (index) {
    cartItems.splice(index, 1);
    renderCart(); // Render lại giỏ hàng sau khi xóa
  };

  // Hàm thay đổi số lượng sản phẩm trong giỏ hàng
  window.changeQuantity = function (index, delta) {
    if (cartItems[index].quantity + delta > 0) {
      cartItems[index].quantity += delta;
    } else {
      cartItems.splice(index, 1); // Xóa nếu số lượng = 0
    }
    renderCart(); // Render lại giỏ hàng sau khi cập nhật số lượng
  };

  // Xử lý sự kiện khi nhấn vào nút thanh toán
  checkoutBtn.addEventListener('click', function () {
    window.location.href = 'checkout.html'; // Chuyển đến trang thanh toán
  });

  renderCart(); // Gọi hàm renderCart để hiển thị dữ liệu ban đầu
});
