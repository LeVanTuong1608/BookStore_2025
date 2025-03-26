document.addEventListener('DOMContentLoaded', function () {
  // Xử lý toggle menu
  const menuToggle = document.getElementById('menu-toggle');
  const navMenu = document.querySelector('nav ul');

  if (menuToggle && navMenu) {
    menuToggle.addEventListener('click', function () {
      navMenu.classList.toggle('active');
    });
  }

  // Xử lý đăng nhập / đăng xuất
  const user = JSON.parse(localStorage.getItem('user'));
  const loginLink = document.querySelector('nav ul li:last-child a');

  if (loginLink) {
    if (user) {
      loginLink.textContent = 'Đăng xuất';
      loginLink.addEventListener('click', function (event) {
        event.preventDefault();
        localStorage.removeItem('user');
        window.location.reload();
      });
    } else {
      loginLink.textContent = 'Đăng nhập';
    }
  }
});
