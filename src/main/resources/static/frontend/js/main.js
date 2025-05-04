document.getElementById('menu-toggle').addEventListener('click', function () {
  document.querySelector('nav ul').classList.toggle('active');
});

document.addEventListener('DOMContentLoaded', function () {
  const user = JSON.parse(localStorage.getItem('user'));
  const loginLink = document.querySelector('nav ul li:last-child a');

  if (user) {
    loginLink.textContent = 'Đăng xuất';
    loginLink.addEventListener('click', function (event) {
      event.preventDefault();
      localStorage.removeItem('user');
      window.location.reload();
    });
  }
});
