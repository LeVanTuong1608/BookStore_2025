document.addEventListener('DOMContentLoaded', function () {
  const orderList = document.getElementById('bookList');
  let orders = [];

  const apiUrl = 'http://localhost:8080/api/orders';

  // Hàm lấy dữ liệu đơn hàng
  async function fetchOrders() {
    try {
      const response = await fetch(apiUrl);
      if (!response.ok) {
        throw new Error('Không thể tải dữ liệu đơn hàng');
      }

      orders = await response.json();
      renderOrders();
    } catch (error) {
      console.error('Lỗi khi lấy đơn hàng:', error);
      alert('Lỗi khi tải danh sách đơn hàng');
    }
  }

  // Hàm hiển thị đơn hàng
  function renderOrders() {
    orderList.innerHTML = '';

    if (orders.length === 0) {
      orderList.innerHTML =
        '<tr><td colspan="6" style="text-align: center;">Không có đơn hàng nào</td></tr>';
      return;
    }

    orders.forEach((order, index) => {
      const row = document.createElement('tr');
      row.innerHTML = `
        <td>${index + 1}</td>
        <td>${order.fullName}</td>
        <td>${new Date(order.orderDate).toLocaleDateString()}</td>
        <td>${order.totalAmount?.toLocaleString() || 0} VND</td>
        <td>
          <select class="order-status" data-id="${order.orderId}">
            <option value="PENDING" ${
              order.status === 'PENDING' ? 'selected' : ''
            }>Chờ xác nhận</option>
            <option value="PROCESSING" ${
              order.status === 'PROCESSING' ? 'selected' : ''
            }>Đang xử lý</option>
            <option value="SHIPPING" ${
              order.status === 'SHIPPING' ? 'selected' : ''
            }>Đang giao</option>
            <option value="COMPLETED" ${
              order.status === 'COMPLETED' ? 'selected' : ''
            }>Đã giao</option>
            <option value="CANCELLED" ${
              order.status === 'CANCELLED' ? 'selected' : ''
            }>Đã hủy</option>
          </select>
        </td>
        <td>
          <button class="detail-btn" data-id="${
            order.orderId
          }">Chi tiết</button>
          <button class="delete-btn" data-id="${order.orderId}">Xóa</button>
        </td>
      `;
      orderList.appendChild(row);
    });

    document.querySelectorAll('.order-status').forEach((select) => {
      select.addEventListener('change', handleStatusChange);
    });

    document.querySelectorAll('.delete-btn').forEach((button) => {
      button.addEventListener('click', handleDelete);
    });

    document.querySelectorAll('.detail-btn').forEach((button) => {
      button.addEventListener('click', showOrderDetails);
    });
  }

  // Hàm xử lý thay đổi trạng thái đơn hàng
  async function handleStatusChange(event) {
    const orderId = event.target.dataset.id;
    const newStatus = event.target.value;

    try {
      const response = await fetch(`${apiUrl}/${orderId}/status`, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ status: newStatus }),
      });

      if (!response.ok) {
        throw new Error('Cập nhật trạng thái thất bại');
      }

      const order = orders.find((o) => o.orderId == orderId);
      if (order) {
        order.status = newStatus;
      }

      alert('Cập nhật trạng thái thành công');
    } catch (error) {
      console.error('Lỗi khi cập nhật trạng thái:', error);
      alert('Lỗi khi cập nhật trạng thái');
      event.target.value = orders.find((o) => o.orderId == orderId).status;
    }
  }

  // Hàm xử lý xóa đơn hàng
  async function handleDelete(event) {
    const orderId = event.target.dataset.id;

    if (!confirm('Bạn có chắc chắn muốn xóa đơn hàng này?')) return;

    try {
      const response = await fetch(`${apiUrl}/${orderId}`, {
        method: 'DELETE',
      });

      if (!response.ok) {
        throw new Error('Xóa đơn hàng thất bại');
      }

      orders = orders.filter((order) => order.orderId != orderId);
      renderOrders();
      alert('Xóa đơn hàng thành công');
    } catch (error) {
      console.error('Lỗi khi xóa đơn hàng:', error);
      alert('Lỗi khi xóa đơn hàng');
    }
  }

  // Hàm hiển thị chi tiết đơn hàng
  async function showOrderDetails(event) {
    const orderId = event.target.dataset.id;

    try {
      const response = await fetch(`${apiUrl}/${orderId}`);
      if (!response.ok) {
        throw new Error('Không thể lấy chi tiết đơn hàng');
      }

      const order = await response.json();
      showDetailsModal(order);
    } catch (error) {
      console.error('Lỗi khi lấy chi tiết đơn hàng:', error);
      alert('Lỗi khi tải chi tiết đơn hàng');
    }
  }

  // Hàm hiển thị modal chi tiết đơn hàng
  function showDetailsModal(order) {
    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.innerHTML = `
      <div class="modal-content">
        <span class="close-btn">&times;</span>
        <h2>Chi tiết đơn hàng #${order.orderId}</h2>
        <p><strong>Người dùng ID:</strong> ${order.userId}</p>
        <p><strong>Ngày đặt:</strong> ${new Date(
          order.orderDate
        ).toLocaleString()}</p>
        <p><strong>Trạng thái:</strong> ${getStatusText(order.status)}</p>
        <p><strong>Tổng tiền:</strong> ${
          order.totalAmount?.toLocaleString() || 0
        } VND</p>
        <p><strong>Mã giảm giá:</strong> ${order.discountId || 'Không có'}</p>
      </div>
    `;

    document.body.appendChild(modal);

    modal.querySelector('.close-btn').addEventListener('click', () => {
      document.body.removeChild(modal);
    });

    modal.addEventListener('click', (e) => {
      if (e.target === modal) {
        document.body.removeChild(modal);
      }
    });
  }

  // Hàm lấy tên trạng thái đơn hàng
  function getStatusText(status) {
    const statusMap = {
      PENDING: 'Chờ xác nhận',
      PROCESSING: 'Đang xử lý',
      SHIPPING: 'Đang giao',
      COMPLETED: 'Đã giao',
      CANCELLED: 'Đã hủy',
    };
    return statusMap[status] || status;
  }

  // Gọi hàm lấy đơn hàng
  fetchOrders();
});
