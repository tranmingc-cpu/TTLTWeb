<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>


<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/views/admin/dashboard.css">
</head>
<body>
    <jsp:include page="/views/jsp/demo.jsp"/>
<div class="admin-container">
    <header class="admin-header">
        <h1>ADMIN PANEL</h1>
    </header>

    <aside class="admin-sidebar">
        <a href="${pageContext.request.contextPath}/admin/dashboard">🏠 Dashboard</a>
        <a href="${pageContext.request.contextPath}/admin/product">🍔 Quản lý món ăn</a>
        <a href="${pageContext.request.contextPath}/admin/order">📦 Quản lý đơn hàng</a>
        <a href="${pageContext.request.contextPath}/admin/user">👤 Quản lý user</a>
    </aside>

    <main class="admin-content">
        <h2>Thống kê nhanh</h2>
        <div class="stats">
            <div class="stat-box">
                👤 Users<br>
                <b>${totalUsers}</b>
            </div>

            <div class="stat-box">
                🍔 Món ăn<br>
                <b>${totalFoods}</b>
            </div>

            <div class="stat-box">
                📦 Đơn hàng<br>
                <b>${totalOrders}</b>
            </div>

            <div class="stat-box">
                💰 Doanh thu<br>
                <b>
                    <fmt:formatNumber value="${totalRevenue}" type="number"/> ₫
                </b>
            </div>
        </div>
        <div class="card">
            <h3>Biểu đồ doanh thu </h3>
            <canvas id="revenueChart"></canvas>
        </div>
    </main>

</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<canvas id="revenueChart"></canvas>

<script>
    fetch("<%=request.getContextPath()%>/admin/revenue-by-month")
        .then(res => res.json())
        .then(data => {

            const months = [...new Set(data.map(item => item.month))];
            const stores = [...new Set(data.map(item => item.resid))];

            const datasets = stores.map(storeId => {
                return {
                    label: "Cửa hàng " + storeId,
                    data: months.map(month => {
                        const item = data.find(d => d.month === month && d.resid === storeId);
                        return item ? item.revenue : 0;
                    })
                };
            });

            const labels = months.map(m => "Tháng " + m);

            new Chart(document.getElementById('revenueChart'), {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: datasets
                }
            });

        });
</script></body>
</html>
