// Hàm kiểm tra trạng thái đăng nhập
function checkLoginStatus() {
  const user = JSON.parse(localStorage.getItem('user'));
  const userInfo = document.getElementById('user-info');
  const loginBtn = document.getElementById('login-btn');
  const usernameDisplay = document.getElementById('username-display');

  if (user) {
    // Người dùng đã đăng nhập
    loginBtn.style.display = 'none'; // Ẩn nút đăng nhập
    userInfo.style.display = 'block'; // Hiển thị thông tin người dùng
    usernameDisplay.textContent = user.username; // Hiển thị tên người dùng
  } else {
    // Người dùng chưa đăng nhập
    loginBtn.style.display = 'block'; // Hiển thị nút đăng nhập
    userInfo.style.display = 'none'; // Ẩn thông tin người dùng
  }
}

// Hàm đăng nhập (giả lập API)
function login(username, password) {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      if (username === 'admin' && password === '123456') {
        resolve({ username });
      } else {
        reject(new Error('Sai tài khoản hoặc mật khẩu!'));
      }
    }, 1000); // Giả lập thời gian chờ 1 giây
  });
}

// Hàm đăng nhập (gọi API thực tế - dùng khi tích hợp với backend)
/*
async function login(username, password) {
  try {
    const response = await fetch('https://api.example.com/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ username, password }),
    });

    const data = await response.json();

    if (response.ok) {
      return data.user; // Trả về thông tin người dùng từ API
    } else {
      throw new Error(data.message || 'Đăng nhập thất bại!');
    }
  } catch (error) {
    throw new Error('Đã xảy ra lỗi khi đăng nhập. Vui lòng thử lại sau!');
  }
}
*/

// Sự kiện đăng nhập
document
  .getElementById('login-form')
  .addEventListener('submit', async function (event) {
    event.preventDefault();

    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();

    // Kiểm tra dữ liệu đầu vào
    if (!username || !password) {
      alert('Vui lòng nhập đầy đủ tài khoản và mật khẩu!');
      return;
    }

    try {
      // Giả lập gọi API đăng nhập
      const user = await login(username, password);

      // Lưu thông tin người dùng vào localStorage
      localStorage.setItem('user', JSON.stringify(user));

      // Cập nhật giao diện
      checkLoginStatus();

      // Thông báo và chuyển hướng
      alert('Đăng nhập thành công!');
      window.location.href = 'index.html';
    } catch (error) {
      alert(error.message);
    }
  });

// Sự kiện đăng xuất
document.getElementById('logout-btn').addEventListener('click', function () {
  // Xóa thông tin người dùng khỏi localStorage
  localStorage.removeItem('user');

  // Cập nhật giao diện
  checkLoginStatus();

  // Thông báo
  alert('Đăng xuất thành công!');
});

// Kiểm tra trạng thái đăng nhập khi trang được tải
document.addEventListener('DOMContentLoaded', function () {
  checkLoginStatus();
});
