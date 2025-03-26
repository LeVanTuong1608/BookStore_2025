document.addEventListener('DOMContentLoaded', function () {
  const promoForm = document.getElementById('addPromotionForm');
  const promoList = document.getElementById('promotionList');
  const overlay = document.createElement('div');
  document.body.appendChild(overlay);
  overlay.classList.add('overlay');

  let editingPromoId = null;

  let promotions = [
    {
      id: 1,
      name: 'Giảm 10%',
      code: 'SALE10',
      book: 'Sách A',
      minSpend: 100000,
      discount: 10000,
      startDate: '2024-04-01',
      endDate: '2024-04-10',
    },
  ];

  function renderPromotions() {
    promoList.innerHTML = '';
    promotions.forEach((promo, index) => {
      const row = document.createElement('tr');
      row.innerHTML = `
              <td>${index + 1}</td>
              <td>${promo.name}</td>
              <td>${promo.code}</td>
              <td>${promo.book}</td>
              <td>${promo.minSpend.toLocaleString()} VND</td>
              <td>${promo.discount.toLocaleString()} VND</td>
              <td>${promo.startDate} - ${promo.endDate}</td>
              <td>
                  <button class="delete-btn" data-id="${promo.id}">Xóa</button>
              </td>
          `;
      promoList.appendChild(row);
    });

    document
      .querySelectorAll('.edit-btn')
      .forEach((btn) => btn.addEventListener('click', handleEdit));
    document
      .querySelectorAll('.delete-btn')
      .forEach((btn) => btn.addEventListener('click', handleDelete));
  }

  function handleEdit(event) {
    const promoId = event.target.dataset.id;
    const promo = promotions.find((p) => p.id == promoId);

    if (promo) {
      editingPromoId = promoId;
      document.getElementById('promoName').value = promo.name;
      document.getElementById('promoCode').value = promo.code;
      document.getElementById('bookId').value = promo.book;
      document.getElementById('discountAmount').value = promo.discount;
      document.getElementById('minSpend').value = promo.minSpend;
      document.getElementById('startDate').value = promo.startDate;
      document.getElementById('endDate').value = promo.endDate;

      promoForm.classList.add('show-form');
      overlay.classList.add('show-overlay');
    }
  }

  function handleDelete(event) {
    const promoId = event.target.dataset.id;
    promotions = promotions.filter((p) => p.id != promoId);
    renderPromotions();
  }

  promoForm.addEventListener('submit', function (event) {
    event.preventDefault();
    const name = document.getElementById('promoName').value;
    const code = document.getElementById('promoCode').value;
    const book = document.getElementById('bookId').value;
    const discount = parseInt(
      document.getElementById('discountAmount').value,
      10
    );
    const minSpend = parseInt(document.getElementById('minSpend').value, 10);
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;

    if (editingPromoId) {
      const promoIndex = promotions.findIndex((p) => p.id == editingPromoId);
      if (promoIndex !== -1) {
        promotions[promoIndex] = {
          id: editingPromoId,
          name,
          code,
          book,
          discount,
          minSpend,
          startDate,
          endDate,
        };
      }
      editingPromoId = null;
    } else {
      promotions.push({
        id: promotions.length + 1,
        name,
        code,
        book,
        discount,
        minSpend,
        startDate,
        endDate,
      });
    }

    promoForm.reset();
    promoForm.classList.remove('show-form');
    overlay.classList.remove('show-overlay');
    renderPromotions();
  });

  overlay.addEventListener('click', () => {
    promoForm.classList.remove('show-form');
    overlay.classList.remove('show-overlay');
  });

  renderPromotions();
});
