document.addEventListener('DOMContentLoaded', function () {
  const user = JSON.parse(localStorage.getItem('user'));
  const loginLink = document.getElementById('login-link');
  const usernameDisplay = document.getElementById('username-display');
  const authSection = document.getElementById('auth-section');

  if (user) {
    // Hiển thị tên người dùng
    if (user.username && usernameDisplay) {
      usernameDisplay.textContent = user.username;
    }

    // Chuyển "Đăng nhập" thành "Đăng xuất"
    loginLink.textContent = 'Đăng xuất';
    loginLink.href = '/index.html';

    loginLink.addEventListener('click', async function (event) {
      event.preventDefault();

      try {
        const response = await fetch(
          'http://localhost:8080/user/api/auth/logout',
          {
            method: 'GET',
            credentials: 'include',
          }
        );

        if (response.ok) {
          localStorage.removeItem('user');
          window.location.reload();
        } else {
          throw new Error('Đã xảy ra lỗi khi đăng xuất');
        }
      } catch (error) {
        console.error('Lỗi khi gọi API đăng xuất:', error);
        alert('Có lỗi xảy ra khi đăng xuất. Vui lòng thử lại sau!');
      }
    });

    // Thêm liên kết "Thông tin tài khoản"
    const accountLink = document.createElement('li');
    const accountButton = document.createElement('a');
    accountButton.href = '/personal.html';
    accountButton.textContent = 'Thông tin tài khoản';
    accountLink.appendChild(accountButton);

    // Thêm vào trước nút "Đăng xuất"
    authSection.insertBefore(accountLink, loginLink.closest('li'));
  } else {
    loginLink.textContent = 'Đăng nhập';
    loginLink.href = '/login.html';
  }
});
