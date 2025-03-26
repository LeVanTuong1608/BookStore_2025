document.addEventListener('DOMContentLoaded', function () {
  const ctxRevenue = document.getElementById('revenueChart')?.getContext('2d');
  const ctxBestSeller = document
    .getElementById('bestSellerChart')
    ?.getContext('2d');

  if (!ctxRevenue || !ctxBestSeller) {
    console.error('Không tìm thấy phần tử canvas');
    return;
  }

  // 🔹 Dữ liệu Doanh Thu theo thời gian
  const revenueData = {
    all: [
      10000000, 12000000, 8000000, 15000000, 20000000, 18000000, 22000000,
      24000000, 19000000, 21000000, 23000000, 25000000,
    ], // 12 tháng

    year: [
      9000000, 11000000, 9500000, 14000000, 17000000, 16000000, 18000000,
      20000000, 17500000, 19500000, 21500000, 23500000,
    ], // 12 tháng

    month: [7000000, 9000000, 7500000, 13000000, 16000000, 15000000, 17000000], // 7 ngày

    week: [2000000, 4000000, 3000000, 5000000, 7000000, 6000000, 8000000], // 7 ngày
  };

  // 🔹 Nhãn thời gian tương ứng
  const labelsData = {
    all: [
      'Tháng 1',
      'Tháng 2',
      'Tháng 3',
      'Tháng 4',
      'Tháng 5',
      'Tháng 6',
      'Tháng 7',
      'Tháng 8',
      'Tháng 9',
      'Tháng 10',
      'Tháng 11',
      'Tháng 12',
    ],

    year: [
      'Tháng 1',
      'Tháng 2',
      'Tháng 3',
      'Tháng 4',
      'Tháng 5',
      'Tháng 6',
      'Tháng 7',
      'Tháng 8',
      'Tháng 9',
      'Tháng 10',
      'Tháng 11',
      'Tháng 12',
    ],

    month: [
      'Ngày 1',
      'Ngày 5',
      'Ngày 10',
      'Ngày 15',
      'Ngày 20',
      'Ngày 25',
      'Ngày 30',
    ], // 7 ngày

    week: [
      'Thứ Hai',
      'Thứ Ba',
      'Thứ Tư',
      'Thứ Năm',
      'Thứ Sáu',
      'Thứ Bảy',
      'Chủ Nhật',
    ], // 7 ngày
  };

  // 🔹 Khởi tạo biểu đồ Doanh Thu
  let revenueChart = new Chart(ctxRevenue, {
    type: 'line',
    data: {
      labels: labelsData.all, // Mặc định: 12 tháng
      datasets: [
        {
          label: 'Doanh thu (VNĐ)',
          data: revenueData.all,
          backgroundColor: 'rgba(54, 162, 235, 0.5)',
          borderColor: 'rgba(54, 162, 235, 1)',
          borderWidth: 2,
          fill: true,
        },
      ],
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        y: { beginAtZero: true },
      },
    },
  });

  // 🔹 Cập nhật biểu đồ khi thay đổi khoảng thời gian
  document.getElementById('timeRange')?.addEventListener('change', function () {
    const selectedRange = this.value;
    revenueChart.data.labels = labelsData[selectedRange] || labelsData.all; // Cập nhật labels
    revenueChart.data.datasets[0].data =
      revenueData[selectedRange] || revenueData.all; // Cập nhật data
    revenueChart.update(); // Cập nhật biểu đồ
  });

  // 🔹 Biểu đồ Sách Bán Chạy
  new Chart(ctxBestSeller, {
    type: 'bar',
    data: {
      labels: ['Sách A', 'Sách B', 'Sách C', 'Sách D', 'Sách E'],
      datasets: [
        {
          label: 'Số lượng bán',
          data: [50, 75, 40, 90, 60],
          backgroundColor: ['red', 'blue', 'green', 'orange', 'purple'],
          borderWidth: 1,
        },
      ],
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        y: { beginAtZero: true },
      },
    },
  });
});
