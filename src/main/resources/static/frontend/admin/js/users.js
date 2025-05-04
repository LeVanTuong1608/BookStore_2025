document.addEventListener('DOMContentLoaded', function () {
  const usersList = document.getElementById('usersList');
  const API_URL = 'http://localhost:8080/api/users';

  let users = [];

  // Hàm lấy dữ liệu người dùng
  async function fetchUsers() {
    try {
      const response = await fetch(API_URL);

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();
      users = data;
      renderUsers();
    } catch (error) {
      console.error('Error fetching users:', error);
      usersList.innerHTML = `<tr><td colspan="6">Không thể tải dữ liệu người dùng. Vui lòng thử lại sau.</td></tr>`;
    }
  }

  // Render danh sách người dùng
  function renderUsers() {
    if (!usersList) {
      console.error('Không tìm thấy phần tử usersList');
      return;
    }

    usersList.innerHTML = '';

    if (!users || users.length === 0) {
      usersList.innerHTML = `<tr><td colspan="6">Không có người dùng nào</td></tr>`;
      return;
    }

    users.forEach((user, index) => {
      const row = document.createElement('tr');
      row.innerHTML = `
        <td>${index + 1}</td>
        <td>${user.email || 'N/A'}</td>
        <td>${user.fullname || 'N/A'}</td>
        <td>${user.address || 'N/A'}</td>
        <td>${user.phonenumber || 'N/A'}</td>
        <td>
          <button class="edit-btn" data-id="${user.userid}">Sửa</button>
          <button class="delete-btn" data-id="${user.userid}">Xóa</button>
        </td>
      `;
      usersList.appendChild(row);
    });

    attachEventListeners();
  }

  // Gắn sự kiện cho các nút
  function attachEventListeners() {
    document.querySelectorAll('.edit-btn').forEach((button) => {
      button.addEventListener('click', handleEdit);
    });

    document.querySelectorAll('.delete-btn').forEach((button) => {
      button.addEventListener('click', handleDelete);
    });
  }

  // Xử lý chỉnh sửa
  async function handleEdit(event) {
    const userId = event.target.dataset.id;
    const user = users.find((u) => u.userid == userId);

    if (!user) return;

    const editModal = document.createElement('div');
    editModal.classList.add('edit-modal');
    editModal.innerHTML = `
      <div class="edit-container">
        <h3>Chỉnh sửa người dùng</h3>
        <label>Email:</label>
        <input type="text" value="${user.email}" disabled>
        <label>Họ tên:</label>
        <input type="text" id="editFullName" value="${user.fullname || ''}">
        <label>Địa chỉ:</label>
        <input type="text" id="editAddress" value="${user.address || ''}">
        <label>Số điện thoại:</label>
        <input type="text" id="editPhoneNumber" value="${
          user.phonenumber || ''
        }">
        <label>Mật khẩu (nếu cần cập nhật):</label>
        <input type="password" id="editPassword" placeholder="Để trống nếu không đổi">
        <div class="button-group">
          <button id="saveEdit">Lưu</button>
          <button id="cancelEdit">Hủy</button>
        </div>
      </div>
    `;

    document.body.appendChild(editModal);

    document.getElementById('cancelEdit').addEventListener('click', () => {
      document.body.removeChild(editModal);
    });

    document.getElementById('saveEdit').addEventListener('click', async () => {
      const fullName = document.getElementById('editFullName').value.trim();
      const address = document.getElementById('editAddress').value.trim();
      const phoneNumber = document
        .getElementById('editPhoneNumber')
        .value.trim();
      const password = document.getElementById('editPassword').value;

      const updatedUser = {
        fullname: fullName,
        address: address,
        phonenumber: phoneNumber,
      };

      if (password) {
        updatedUser.password = password;
      }

      try {
        const response = await fetch(`${API_URL}/${user.userid}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(updatedUser),
        });

        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }

        document.body.removeChild(editModal);
        await fetchUsers();
      } catch (error) {
        console.error('Error updating user:', error);
        alert('Có lỗi khi cập nhật người dùng');
      }
    });
  }

  // Xử lý xóa người dùng
  async function handleDelete(event) {
    if (!confirm('Bạn có chắc chắn muốn xóa người dùng này?')) return;

    const userId = event.target.dataset.id;

    try {
      const response = await fetch(`${API_URL}/${userId}`, {
        method: 'DELETE',
      });

      if (!response.ok) {
        const errorText = await response.text(); // Lấy thông tin lỗi chi tiết
        console.error(`Error: ${errorText}`);
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      await fetchUsers();
    } catch (error) {
      console.error('Error deleting user:', error);
      alert('Có lỗi khi xóa người dùng');
    }
  }

  fetchUsers();
});
