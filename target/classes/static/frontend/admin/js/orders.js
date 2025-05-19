document.addEventListener('DOMContentLoaded', function () {
  const orderList = document.getElementById('bookList');
  const API_URL = 'http://localhost:8080/admin/api/orders';

  // Tạo phần điều khiển phân trang
  const paginationContainer = document.createElement('div');
  paginationContainer.id = 'paginationContainer';
  paginationContainer.style.margin = '20px 0';
  orderList.parentNode.insertBefore(paginationContainer, orderList.nextSibling);

  let currentPage = 0;
  let totalPages = 1;
  const pageSize = 12; // Số lượng đơn hàng mỗi trang

  function handleError(err, msg) {
    console.error(msg, err);
    alert(`Có lỗi: ${msg}`);
  }

  // Hàm lấy dữ liệu đơn hàng có phân trang
  async function fetchOrders(page = 0) {
    try {
      orderList.innerHTML =
        '<tr><td colspan="6" style="text-align:center">Đang tải...</td></tr>';

      const response = await fetch(`${API_URL}?page=${page}&size=${pageSize}`);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();
      currentPage = data.number;
      totalPages = data.totalPages;

      renderOrders(data.content);
      updatePaginationControls(data);
    } catch (e) {
      handleError(e, 'khi tải danh sách đơn hàng');
      orderList.innerHTML = `<tr><td colspan="6" style="text-align:center">Không tải được đơn hàng</td></tr>`;
    }
  }

  // Render danh sách đơn hàng
  function renderOrders(orders) {
    orderList.innerHTML = '';

    if (!orders || orders.length === 0) {
      orderList.innerHTML = `<tr><td colspan="6" style="text-align:center">Không có đơn hàng</td></tr>`;
      return;
    }

    const startIndex = currentPage * pageSize;

    orders.forEach((order, idx) => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${startIndex + idx + 1}</td>
        <td>
          <strong>${order.fullName}</strong><br>
          ID: ${order.orderId}<br>
          Mã giảm giá: ${order.discountId || 'Không có'}
        </td>
        <td>${new Date(order.orderDate).toLocaleDateString()}</td>
        <td>${order.totalAmount?.toLocaleString() || 0} VND</td>
        <td>
          <select class="order-status" data-id="${order.orderId}">
            <option value="pending" ${
              order.status === 'pending' ? 'selected' : ''
            }>Chờ xác nhận</option>
            <option value="shipped" ${
              order.status === 'shipped' ? 'selected' : ''
            }>Đã giao</option>
            <option value="delivered" ${
              order.status === 'delivered' ? 'selected' : ''
            }>Đã hoàn thành</option>
            <option value="cancelled" ${
              order.status === 'cancelled' ? 'selected' : ''
            }>Đã hủy</option>
          </select>
        </td>
        <td>
          <button class="detail-btn" data-id="${
            order.orderId
          }">Chi tiết</button>
          <button class="delete-btn" data-id="${order.orderId}">Xóa</button>
        </td>`;
      orderList.appendChild(tr);
    });

    // Gắn sự kiện
    document.querySelectorAll('.order-status').forEach((sel) => {
      sel.addEventListener('change', handleStatusChange);
    });
    document.querySelectorAll('.delete-btn').forEach((btn) => {
      btn.addEventListener('click', handleDelete);
    });
    document.querySelectorAll('.detail-btn').forEach((btn) => {
      btn.addEventListener('click', showOrderDetails);
    });
  }

  // Cập nhật điều khiển phân trang
  function updatePaginationControls(data) {
    paginationContainer.innerHTML = `
      <div style="display: flex; gap: 10px; align-items: center;">
        <button id="firstPage" ${data.first ? 'disabled' : ''}>
          <i class="fas fa-angle-double-left"></i>
        </button>
        <button id="prevPage" ${data.first ? 'disabled' : ''}>
          <i class="fas fa-angle-left"></i>
        </button>
        <span>Trang ${currentPage + 1} / ${totalPages}</span>
        <button id="nextPage" ${data.last ? 'disabled' : ''}>
          <i class="fas fa-angle-right"></i>
        </button>
        <button id="lastPage" ${data.last ? 'disabled' : ''}>
          <i class="fas fa-angle-double-right"></i>
        </button>
        <span>(${data.totalElements} đơn hàng)</span>
      </div>
    `;

    // Gắn sự kiện cho các nút phân trang
    document
      .getElementById('firstPage')
      ?.addEventListener('click', () => fetchOrders(0));
    document
      .getElementById('prevPage')
      ?.addEventListener('click', () => fetchOrders(currentPage - 1));
    document
      .getElementById('nextPage')
      ?.addEventListener('click', () => fetchOrders(currentPage + 1));
    document
      .getElementById('lastPage')
      ?.addEventListener('click', () => fetchOrders(totalPages - 1));
  }

  // Các hàm xử lý khác giữ nguyên
  async function handleStatusChange(e) {
    const sel = e.target;
    const orderId = sel.dataset.id;
    const newStatus = sel.value;

    try {
      const resp = await fetch(`${API_URL}/${orderId}/status`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ status: newStatus }),
      });

      if (!resp.ok) throw new Error(`HTTP ${resp.status}`);

      // Load lại trang hiện tại để cập nhật dữ liệu
      fetchOrders(currentPage);
      alert('Cập nhật trạng thái thành công');
    } catch (err) {
      handleError(err, 'khi cập nhật trạng thái');
      // Rollback giá trị select
      const order = data.content.find((o) => o.orderId == orderId);
      if (order) sel.value = order.status;
    }
  }

  async function handleDelete(e) {
    const orderId = e.target.dataset.id;
    if (!confirm('Bạn có chắc chắn muốn xóa đơn hàng này?')) return;

    try {
      const resp = await fetch(`${API_URL}/${orderId}`, { method: 'DELETE' });
      if (!resp.ok) throw new Error(`HTTP ${resp.status}`);

      // Kiểm tra nếu trang hiện tại trống sau khi xóa
      const checkResp = await fetch(
        `${API_URL}?page=${currentPage}&size=${pageSize}`
      );
      const checkData = await checkResp.json();

      if (checkData.numberOfElements === 0 && currentPage > 0) {
        fetchOrders(currentPage - 1);
      } else {
        fetchOrders(currentPage);
      }

      alert('Xóa đơn hàng thành công');
    } catch (err) {
      handleError(err, 'khi xóa đơn hàng');
    }
  }

  async function showOrderDetails(e) {
    const orderId = e.target.dataset.id;
    try {
      const resp = await fetch(`${API_URL}/${orderId}`);
      if (!resp.ok) throw new Error(`HTTP ${resp.status}`);
      const order = await resp.json();
      showDetailsModal(order);
    } catch (err) {
      handleError(err, 'khi tải chi tiết');
    }
  }

  function showDetailsModal(order) {
    const modal = document.createElement('div');
    modal.className = 'modal';
    modal.innerHTML = `
      <div class="modal-content">
        <span class="close-btn">&times;</span>
        <h2>Đơn hàng #${order.orderId}</h2>
        <p><strong>Người đặt:</strong> ${order.fullName}</p>
        <p><strong>Ngày đặt:</strong> ${new Date(
          order.orderDate
        ).toLocaleString()}</p>
        <p><strong>Trạng thái:</strong> ${getStatusText(order.status)}</p>
        <p><strong>Tổng tiền:</strong> ${
          order.totalAmount?.toLocaleString() || 0
        } VND</p>
        <p><strong>Mã giảm giá:</strong> ${order.discountId || 'Không có'}</p>
      </div>`;
    document.body.appendChild(modal);

    modal.querySelector('.close-btn').addEventListener('click', () => {
      document.body.removeChild(modal);
    });

    modal.addEventListener('click', (e) => {
      if (e.target === modal) document.body.removeChild(modal);
    });
  }

  function getStatusText(status) {
    const statusMap = {
      pending: 'Chờ xác nhận',
      shipped: 'Đã giao',
      delivered: 'Đã hoàn thành',
      cancelled: 'Đã hủy',
    };
    return statusMap[status] || status;
  }

  // Khởi chạy
  fetchOrders();
});
