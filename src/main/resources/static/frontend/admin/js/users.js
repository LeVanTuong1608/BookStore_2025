document.addEventListener('DOMContentLoaded', function () {
  const usersList = document.getElementById('usersList');

  let users = [
    {
      id: 1,
      username: 'userA',
      password: '123456',
      orders: 5,
      totalPaid: 500000,
    },
    {
      id: 2,
      username: 'userB',
      password: 'abcdef',
      orders: 2,
      totalPaid: 200000,
    },
  ];

  function renderUsers() {
    usersList.innerHTML = '';
    users.forEach((user, index) => {
      const row = document.createElement('tr');
      row.innerHTML = `
                <td>${index + 1}</td>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td>${user.orders}</td>
                <td>${user.totalPaid.toLocaleString()} VND</td>
                <td>
                    <button class="edit-btn" data-id="${user.id}">Sửa</button>
                    <button class="delete-btn" data-id="${user.id}">Xóa</button>
                </td>
            `;
      usersList.appendChild(row);
    });

    document.querySelectorAll('.edit-btn').forEach((button) => {
      button.addEventListener('click', handleEdit);
    });

    document.querySelectorAll('.delete-btn').forEach((button) => {
      button.addEventListener('click', handleDelete);
    });
  }

  function handleEdit(event) {
    const userId = event.target.dataset.id;
    const user = users.find((u) => u.id == userId);

    if (!user) return;

    const editModal = document.createElement('div');
    editModal.classList.add('edit-modal');
    editModal.innerHTML = `
            <div class="edit-container">
                <h3>Chỉnh sửa người dùng</h3>
                <label>Tài khoản:</label>
                <input type="text" id="editUsername" value="${user.username}" disabled>
                <label>Mật khẩu:</label>
                <input type="text" id="editPassword" value="${user.password}">
                <label>Số lượng hàng đã mua:</label>
                <input type="number" id="editOrders" value="${user.orders}">
                <label>Tổng số tiền đã thanh toán:</label>
                <input type="number" id="editTotalPaid" value="${user.totalPaid}">
                <button id="saveEdit">Lưu</button>
                <button id="cancelEdit">Hủy</button>
            </div>
        `;

    document.body.appendChild(editModal);

    document.getElementById('saveEdit').addEventListener('click', function () {
      user.password = document.getElementById('editPassword').value;
      user.orders = parseInt(document.getElementById('editOrders').value);
      user.totalPaid = parseInt(document.getElementById('editTotalPaid').value);
      document.body.removeChild(editModal);
      renderUsers();
    });

    document
      .getElementById('cancelEdit')
      .addEventListener('click', function () {
        document.body.removeChild(editModal);
      });
  }

  function handleDelete(event) {
    const userId = event.target.dataset.id;
    users = users.filter((user) => user.id != userId);
    renderUsers();
  }

  renderUsers();
});
