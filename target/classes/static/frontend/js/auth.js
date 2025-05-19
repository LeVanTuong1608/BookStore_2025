// Gọi API đăng nhập sử dụng session
async function login(username, password) {
  try {
    const response = await fetch('http://localhost:8080/user/api/auth/login', {
      method: 'POST',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email: username, // dùng email làm username
        password: password,
      }),
    });

    const textResponse = await response.text();
    console.log('Response from API:', textResponse);

    try {
      const data = JSON.parse(textResponse);

      if (response.ok) {
        alert('Đăng nhập thành công!');
        window.location.href = '../frontend/index.html';
      } else {
        throw new Error(data.message || 'Đăng nhập thất bại!');
      }
    } catch (jsonError) {
      console.error('Lỗi parse JSON:', jsonError);
      alert('Lỗi phản hồi từ máy chủ. Vui lòng thử lại sau!');
    }
  } catch (error) {
    console.error('Lỗi đăng nhập:', error);
    alert('Đã xảy ra lỗi khi đăng nhập. Vui lòng thử lại sau!');
  }
}

// Kiểm tra trạng thái đăng nhập
async function checkLoginStatus() {
  const loginBtn = document.getElementById('login-btn');
  const userInfo = document.getElementById('user-info');
  const usernameDisplay = document.getElementById('username-display');

  try {
    const response = await fetch('http://localhost:8080/user/api/auth/me', {
      method: 'GET',
      credentials: 'include',
    });

    if (!response.ok) throw new Error('Chưa đăng nhập');

    const user = await response.json();

    loginBtn.style.display = 'none';
    userInfo.style.display = 'flex';
    usernameDisplay.textContent = user.username || user.email;
  } catch (error) {
    loginBtn.style.display = 'inline-block';
    userInfo.style.display = 'none';
  }
}

// Xử lý đăng nhập khi submit form
document.addEventListener('DOMContentLoaded', function () {
  checkLoginStatus();

  const loginForm = document.getElementById('login-form');
  if (loginForm) {
    loginForm.addEventListener('submit', async function (event) {
      event.preventDefault();
      const username = document.getElementById('username').value.trim();
      const password = document.getElementById('password').value.trim();

      if (!username || !password) {
        alert('Vui lòng nhập đầy đủ tài khoản và mật khẩu!');
        return;
      }

      await login(username, password);
    });
  }

  // Đăng xuất
  const logoutBtn = document.getElementById('logout-btn');
  if (logoutBtn) {
    logoutBtn.addEventListener('click', async function () {
      try {
        await fetch('http://localhost:8080/user/api/auth/logout', {
          method: 'GET',
          credentials: 'include',
        });

        alert('Đăng xuất thành công!');
        checkLoginStatus();
      } catch (error) {
        alert('Lỗi khi đăng xuất!');
      }
    });
  }
});
