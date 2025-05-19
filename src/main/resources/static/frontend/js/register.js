// Hàm gọi API đăng ký
async function register(fullname, password, email, address, phonenumber) {
  try {
    const response = await fetch(
      'http://localhost:8080/user/api/auth/register',
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          email,
          fullname,
          password,
          address,
          phonenumber,
        }),
      }
    );

    const data = await response.json();

    console.log('Response Status:', response.status);
    console.log('Response from server:', data);

    if (response.ok) {
      alert(data.message || 'Đăng ký thành công');
      window.location.href = 'login.html';
    } else {
      alert(
        data.error || data.message || 'Đã xảy ra lỗi trong quá trình đăng ký'
      );
    }
  } catch (error) {
    console.error('Lỗi khi gửi yêu cầu:', error.message);
    alert('Đã xảy ra lỗi khi đăng ký. Vui lòng thử lại sau!');
  }
}

// Xử lý submit form đăng ký
document
  .getElementById('register-form')
  .addEventListener('submit', async function (event) {
    event.preventDefault();

    const email = document.getElementById('new-email').value.trim();
    const fullname = document.getElementById('new-fullname').value.trim(); // Đổi id đúng
    const password = document.getElementById('new-password').value.trim();
    const address = document.getElementById('address').value.trim();
    const phonenumber = document.getElementById('phonenumber').value.trim();

    if (!fullname || !password || !email || !address || !phonenumber) {
      alert('Vui lòng nhập đầy đủ thông tin!');
      return;
    }

    await register(fullname, password, email, address, phonenumber);
  });
