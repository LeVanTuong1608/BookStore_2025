// ===== Khi trang load: gọi API lấy thông tin user =====
document.addEventListener('DOMContentLoaded', async function () {
  try {
    const response = await fetch('http://localhost:8080/user/api/auth/me', {
      method: 'GET',
      credentials: 'include', // để gửi cookie session
    });

    if (!response.ok) {
      throw new Error(
        'Không thể lấy thông tin người dùng. Vui lòng đăng nhập lại.'
      );
    }

    const user = await response.json();

    // Lưu userId vào localStorage để dùng khi cập nhật
    localStorage.setItem('userId', user.userid);

    // Gán dữ liệu vào form
    document.getElementById('fullname').value = user.fullname || '';
    document.getElementById('email').value = user.email || '';
    document.getElementById('phone').value = user.phonenumber || '';
    document.getElementById('address').value = user.address || '';
    document.getElementById('password').value = ''; // Mật khẩu luôn để trống khi load
  } catch (error) {
    console.error('Lỗi khi lấy thông tin người dùng:', error);
    showAlert('error', error.message);
  }
});

// ===== Khi người dùng submit form cập nhật thông tin =====
document
  .getElementById('account-form')
  .addEventListener('submit', async function (event) {
    event.preventDefault();

    const submitButton = document.querySelector(
      '#account-form button[type="submit"]'
    );
    const originalButtonText = submitButton.textContent;
    submitButton.disabled = true;
    submitButton.innerHTML =
      '<i class="fa fa-spinner fa-spin"></i> Đang cập nhật...';

    try {
      const userId = localStorage.getItem('userId');

      if (!userId || isNaN(userId)) {
        throw new Error(
          'Không tìm thấy thông tin người dùng. Vui lòng đăng nhập lại.'
        );
      }

      const fullname = document.getElementById('fullname').value.trim();
      const email = document.getElementById('email').value.trim();
      const phone = document.getElementById('phone').value.trim();
      const address = document.getElementById('address').value.trim();
      const password = document.getElementById('password').value.trim();

      // Kiểm tra dữ liệu đầu vào
      if (!fullname || !email || !phone) {
        throw new Error(
          'Vui lòng điền đầy đủ thông tin bắt buộc (Họ tên, Email, Số điện thoại)'
        );
      }

      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(email)) {
        throw new Error('Email không hợp lệ');
      }

      const userData = {
        userid: parseInt(userId),
        fullname: fullname,
        email: email,
        phonenumber: phone,
        address: address || null,
      };

      // Chỉ thêm password vào dữ liệu gửi đi nếu người dùng có nhập
      if (password) {
        userData.password = password;
      }

      const response = await fetch(
        'http://localhost:8080/user/api/auth/update',
        {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          credentials: 'include', // gửi session cookie
          body: JSON.stringify(userData),
        }
      );

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(
          errorData.message || `Lỗi từ server: ${response.status}`
        );
      }

      const data = await response.json();
      showAlert('success', 'Cập nhật thông tin thành công!');

      // Cập nhật lại dữ liệu trên form nếu có thay đổi từ server
      document.getElementById('fullname').value = data.fullname || fullname;
      document.getElementById('email').value = data.email || email;
      document.getElementById('phone').value = data.phonenumber || phone;
      document.getElementById('address').value = data.address || address;
      // Xóa mật khẩu trong form sau khi cập nhật
      document.getElementById('password').value = '';
    } catch (error) {
      console.error('Lỗi khi cập nhật:', error);
      showAlert(
        'error',
        error.message || 'Có lỗi xảy ra khi cập nhật thông tin'
      );
    } finally {
      submitButton.disabled = false;
      submitButton.textContent = originalButtonText;
    }
  });

// ===== Hàm hiển thị thông báo dạng toast =====
function showAlert(type, message) {
  const alertBox = document.createElement('div');
  alertBox.className = `alert alert-${type}`;
  alertBox.textContent = message;
  alertBox.style.position = 'fixed';
  alertBox.style.top = '20px';
  alertBox.style.right = '20px';
  alertBox.style.padding = '15px';
  alertBox.style.borderRadius = '5px';
  alertBox.style.color = type === 'error' ? '#721c24' : '#155724';
  alertBox.style.backgroundColor = type === 'error' ? '#f8d7da' : '#d4edda';
  alertBox.style.border =
    type === 'error' ? '1px solid #f5c6cb' : '1px solid #c3e6cb';
  alertBox.style.zIndex = '1000';

  document.body.appendChild(alertBox);

  setTimeout(() => {
    alertBox.style.transition = 'opacity 0.5s';
    alertBox.style.opacity = '0';
    setTimeout(() => alertBox.remove(), 500);
  }, 5000);
}
