document.addEventListener('DOMContentLoaded', function () {
  var ctxRevenue = document.getElementById('revenueChart').getContext('2d');
  var revenueChart = new Chart(ctxRevenue, {
    type: 'bar',
    data: { labels: [], datasets: [{ label: 'Doanh thu', data: [] }] },
    options: { responsive: true },
  });

  var ctxBestSeller = document
    .getElementById('bestSellerChart')
    .getContext('2d');
  var bestSellerChart = new Chart(ctxBestSeller, {
    type: 'pie',
    data: {
      labels: [],
      datasets: [
        { data: [], backgroundColor: ['#FF6384', '#FFCD56', '#36A2EB'] },
      ],
    },
    options: { responsive: true },
  });

  // Hàm fetch dữ liệu từ API
  async function fetchRevenueData(timeRange) {
    try {
      let response = await fetch(
        `http://localhost:3000/api/revenue?time=${timeRange}`
      );
      let data = await response.json();
      updateRevenueChart(data);
    } catch (error) {
      console.error('Lỗi khi lấy dữ liệu doanh thu:', error);
    }
  }

  async function fetchBestSellerData() {
    try {
      let response = await fetch('http://localhost:3000/api/bestsellers');
      let data = await response.json();
      updateBestSellerChart(data);
    } catch (error) {
      console.error('Lỗi khi lấy dữ liệu sách bán chạy:', error);
    }
  }

  // Cập nhật biểu đồ doanh thu
  function updateRevenueChart(data) {
    revenueChart.data.labels = data.labels;
    revenueChart.data.datasets[0].data = data.values;
    revenueChart.update();
  }

  // Cập nhật biểu đồ sách bán chạy
  function updateBestSellerChart(data) {
    bestSellerChart.data.labels = data.labels;
    bestSellerChart.data.datasets[0].data = data.values;
    bestSellerChart.update();
  }

  // Gọi API khi tải trang
  fetchRevenueData('all');
  fetchBestSellerData();

  // Lắng nghe sự kiện thay đổi thời gian
  document.getElementById('timeRange').addEventListener('change', function () {
    fetchRevenueData(this.value);
  });
});
