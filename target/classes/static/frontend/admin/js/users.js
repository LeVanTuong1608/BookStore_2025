document.addEventListener('DOMContentLoaded', function () {
  const usersList = document.getElementById('usersList');
  const API_URL = 'http://localhost:8080/admin/api/users';

  // Tạo phần điều khiển phân trang
  const paginationContainer = document.createElement('div');
  paginationContainer.id = 'paginationContainer';
  paginationContainer.style.margin = '20px 0';
  usersList.parentNode.insertBefore(paginationContainer, usersList.nextSibling);

  let currentPage = 0;
  let totalPages = 1;
  const pageSize = 12; // Số lượng item mỗi trang

  function handleError(err, msg) {
    console.error(msg, err);
    alert(`Có lỗi: ${msg}`);
  }

  // Hàm lấy dữ liệu người dùng có phân trang
  async function fetchUsers(page = 0) {
    try {
      const response = await fetch(`${API_URL}?page=${page}&size=${pageSize}`);

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();
      currentPage = data.number;
      totalPages = data.totalPages;

      renderUsers(data.content);
      updatePaginationControls(data);
    } catch (error) {
      console.error('Error fetching users:', error);
      usersList.innerHTML = `<tr><td colspan="6">Không thể tải dữ liệu người dùng. Vui lòng thử lại sau.</td></tr>`;
    }
  }

  // Render danh sách người dùng
  function renderUsers(users) {
    if (!usersList) {
      console.error('Không tìm thấy phần tử usersList');
      return;
    }

    usersList.innerHTML = '';

    if (!users || users.length === 0) {
      usersList.innerHTML = `<tr><td colspan="6">Không có người dùng nào</td></tr>`;
      return;
    }

    const startIndex = currentPage * pageSize;

    users.forEach((user, index) => {
      const row = document.createElement('tr');
      row.innerHTML = `
        <td>${startIndex + index + 1}</td>
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
        <span>(${data.totalElements} người dùng)</span>
      </div>
    `;

    // Gắn sự kiện cho các nút phân trang
    document
      .getElementById('firstPage')
      ?.addEventListener('click', () => fetchUsers(0));
    document
      .getElementById('prevPage')
      ?.addEventListener('click', () => fetchUsers(currentPage - 1));
    document
      .getElementById('nextPage')
      ?.addEventListener('click', () => fetchUsers(currentPage + 1));
    document
      .getElementById('lastPage')
      ?.addEventListener('click', () => fetchUsers(totalPages - 1));
  }

  // Gắn sự kiện cho các nút sửa và xóa
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
    try {
      const response = await fetch(`${API_URL}/${userId}`);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const user = await response.json();
      if (!user) return;

      const editModal = document.createElement('div');
      editModal.classList.add('edit-modal');
      editModal.innerHTML = `
        <div class="edit-container">
          <h3>Chỉnh sửa người dùng</h3>
          <label>Họ tên:</label>
          <input type="text" id="editFullName" value="${user.fullname || ''}">
          <label>Địa chỉ:</label>
          <input type="text" id="editAddress" value="${user.address || ''}">
          <label>Số điện thoại:</label>
          <input type="text" id="editPhoneNumber" value="${
            user.phonenumber || ''
          }">
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

      document
        .getElementById('saveEdit')
        .addEventListener('click', async () => {
          const fullName = document.getElementById('editFullName').value.trim();
          const address = document.getElementById('editAddress').value.trim();
          const phoneNumber = document
            .getElementById('editPhoneNumber')
            .value.trim();
          // const password = document.getElementById('editPassword').value;

          const updatedUser = {
            // userId: user.userid,
            // email: user.email,
            fullname: fullName,
            address: address,
            phonenumber: phoneNumber
            // role: user.role
          };

          // if (password) {
          //   updatedUser.password = password;
          // }

          try {
            const updateResponse = await fetch(`${API_URL}/${userId}`, {
              method: 'PUT',
              headers: {
                'Content-Type': 'application/json',
              },
              body: JSON.stringify(updatedUser),
            });

            if (!updateResponse.ok) {
              throw new Error(`HTTP error! status: ${updateResponse.status}`);
            }

            document.body.removeChild(editModal);
            fetchUsers(currentPage); // Load lại trang hiện tại
          } catch (error) {
            console.error('Error updating user:', error);
            alert('Có lỗi khi cập nhật người dùng');
          }
        });
    } catch (error) {
      console.error('Error fetching user details for edit:', error);
      alert('Có lỗi khi tải thông tin người dùng để chỉnh sửa');
    }
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

      // Kiểm tra nếu trang hiện tại trống sau khi xóa
      const checkResponse = await fetch(
        `${API_URL}?page=${currentPage}&size=${pageSize}`
      );
      const checkData = await checkResponse.json();

      if (checkData.numberOfElements === 0 && currentPage > 0) {
        fetchUsers(currentPage - 1);
      } else {
        fetchUsers(currentPage);
      }
    } catch (error) {
      console.error('Error deleting user:', error);
      alert('Có lỗi khi xóa người dùng');
    }
  }

  // Khởi chạy
  fetchUsers();
});
