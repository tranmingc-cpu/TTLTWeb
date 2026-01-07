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


    <!-- HEADER -->
    <header class="admin-header">
        <h1>ADMIN PANEL</h1>
        <div class="admin-user">
            Xin chào <b>${adminName}</b> |
            <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
        </div>
    </header>

    <!-- SIDEBAR -->
    <aside class="admin-sidebar">
        <a href="${pageContext.request.contextPath}/admin/dashboard">🏠 Dashboard</a>
        <a href="${pageContext.request.contextPath}/admin/product">🍔 Quản lý món ăn</a>
        <a href="${pageContext.request.contextPath}/admin/order">📦 Quản lý đơn hàng</a>
        <a href="${pageContext.request.contextPath}/admin/user">👤 Quản lý user</a>
    </aside>

    <!-- CONTENT -->
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

    </main>

</div>

</body>
</html>
