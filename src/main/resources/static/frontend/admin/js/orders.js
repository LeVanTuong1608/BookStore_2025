document.addEventListener('DOMContentLoaded', function () {
  const orderList = document.getElementById('bookList');

  // Dữ liệu đơn hàng mẫu (thay thế bằng API sau này)
  let orders = [
    {
      id: 1,
      info: 'Nguyễn Văn A - 3 sách',
      date: '2025-03-22',
      total: 300000,
      status: 'Chờ xác nhận',
    },
    {
      id: 2,
      info: 'Trần Thị B - 1 sách',
      date: '2025-03-21',
      total: 120000,
      status: 'Đã giao',
    },
  ];

  // Render danh sách đơn hàng
  function renderOrders() {
    orderList.innerHTML = '';
    orders.forEach((order, index) => {
      const row = document.createElement('tr');
      row.innerHTML = `
                <td>${index + 1}</td>
                <td>${order.info}</td>
                <td>${order.date}</td>
                <td>${order.total.toLocaleString()} VND</td>
                <td>
                    <select class="order-status" data-id="${order.id}">
                        <option value="Chờ xác nhận" ${
                          order.status === 'Chờ xác nhận' ? 'selected' : ''
                        }>Chờ xác nhận</option>
                        <option value="Đang giao" ${
                          order.status === 'Đang giao' ? 'selected' : ''
                        }>Đang giao</option>
                        <option value="Đã giao" ${
                          order.status === 'Đã giao' ? 'selected' : ''
                        }>Đã giao</option>
                        <option value="Đã hủy" ${
                          order.status === 'Đã hủy' ? 'selected' : ''
                        }>Đã hủy</option>
                    </select>
                </td>
                <td>
                    <button class="delete-btn" data-id="${
                      order.id
                    }">Xóa</button>
                </td>
            `;
      orderList.appendChild(row);
    });

    // Gán sự kiện thay đổi trạng thái đơn hàng
    document.querySelectorAll('.order-status').forEach((select) => {
      select.addEventListener('change', handleStatusChange);
    });

    // Gán sự kiện xóa đơn hàng
    document.querySelectorAll('.delete-btn').forEach((button) => {
      button.addEventListener('click', handleDelete);
    });
  }

  // Xử lý thay đổi trạng thái đơn hàng
  function handleStatusChange(event) {
    const orderId = event.target.dataset.id;
    const newStatus = event.target.value;
    const order = orders.find((o) => o.id == orderId);
    if (order) {
      order.status = newStatus;
    }
  }

  // Xử lý khi nhấn nút "Xóa"
  function handleDelete(event) {
    const orderId = event.target.dataset.id;
    orders = orders.filter((order) => order.id != orderId);
    renderOrders();
  }

  // Khởi chạy
  renderOrders();
});
