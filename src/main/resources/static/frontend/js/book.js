document.addEventListener('DOMContentLoaded', function () {
  const booksContainer = document.getElementById('books-container');
  const itemsToShow = 12;

  let products = [
    {
      id: 1,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 10,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
      createdAt: 1709870000000,
    },
    {
      id: 2,
      title: 'Làm gì - John Gray',
      price: 160000,
      discount: 20,
      image:
        'https://storage.googleapis.com/a1aa/image/Ua50ayq3EjGPiiS5UKj7Qs_inuZFrtEGqMBIvbTaLvM.jpg',
      createdAt: 1709966400000,
    },
    {
      id: 3,
      title: 'Sách mẫu',
      price: 200000,
      discount: 20,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
      createdAt: 1710643200000,
    },
    {
      id: 4,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 10,
      image:
        'https://storage.googleapis.com/a1aa/image/Ua50ayq3EjGPiiS5UKj7Qs_inuZFrtEGqMBIvbTaLvM.jpg',
      createdAt: 1709870000000,
    },
    {
      id: 5,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 10,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
      createdAt: 1709870000000,
    },
    {
      id: 6,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 10,
      image:
        'https://res.cloudinary.com/dbynglvwk/image/upload/v1653963583/bookstore/hpfjebw8i5cygplpl5zi.jpg',
      createdAt: 1709870000000,
    },
    {
      id: 7,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 10,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
      createdAt: 1709870000000,
    },
    {
      id: 8,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 10,
      image:
        'https://res.cloudinary.com/dbynglvwk/image/upload/v1653963583/bookstore/hpfjebw8i5cygplpl5zi.jpg',
      createdAt: 1709870000000,
    },
    {
      id: 9,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 10,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
      createdAt: 1709870000000,
    },
    {
      id: 10,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 10,
      image:
        'https://res.cloudinary.com/dbynglvwk/image/upload/v1653963583/bookstore/hpfjebw8i5cygplpl5zi.jpg',
      createdAt: 1709870000000,
    },
    {
      id: 11,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 10,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
      createdAt: 1709870000000,
    },
    {
      id: 12,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 10,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
      createdAt: 1709870000000,
    },
    {
      id: 13,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 10,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
      createdAt: 1709870000000,
    },
    {
      id: 14,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 10,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
      createdAt: 1709870000000,
    },
    {
      id: 15,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 10,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
      createdAt: 1709870000000,
    },
    {
      id: 16,
      title: 'Thiên Tài Bên Trái',
      price: 899999,
      discount: 10,
      image:
        'https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1630313960i/50908574.jpg',
      createdAt: 1709870000000,
    },
  ];

  function renderProducts() {
    booksContainer.innerHTML = '';

    let latestProducts = products
      .sort((a, b) => b.createdAt - a.createdAt)
      .slice(0, itemsToShow);

    const fragment = document.createDocumentFragment();

    latestProducts.forEach((product) => {
      let priceAfterDiscount = product.price * (1 - product.discount / 100);

      const productItem = document.createElement('div');
      productItem.classList.add('product-item');
      productItem.innerHTML = `
        <a href="book-detail.html?id=${product.id}" class="product-link">
          <img src="${product.image}" alt="${product.title}">
          <h4>${product.title}</h4>
          <p class="price">
            ${priceAfterDiscount.toLocaleString()} đ
            ${
              product.discount > 0 && priceAfterDiscount !== product.price
                ? `<span class="original-price">${product.price.toLocaleString()} đ</span>`
                : ''
            }
          </p>
        </a>
      `;
      fragment.appendChild(productItem);
    });

    booksContainer.appendChild(fragment);
  }

  renderProducts();
});
