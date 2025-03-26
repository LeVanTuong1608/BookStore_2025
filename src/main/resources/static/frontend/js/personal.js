document
  .getElementById('account-form')
  .addEventListener('submit', function (event) {
    event.preventDefault();
    const fullname = document.getElementById('fullname').value;
    const email = document.getElementById('email').value;
    const phone = document.getElementById('phone').value;
    const address = document.getElementById('address').value;

    const userData = { fullname, email, phone, address };

    fetch('https://your-api.com/api/user', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userData),
    })
      .then((response) => response.json())
      .then((data) => {
        alert('Thông tin tài khoản đã được cập nhật thành công!');
      })
      .catch((error) => {
        console.error('Lỗi cập nhật thông tin:', error);
        alert('Cập nhật thông tin thất bại!');
      });
  });
