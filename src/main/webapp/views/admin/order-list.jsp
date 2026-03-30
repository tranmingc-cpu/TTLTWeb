<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Tổng số đơn hàng</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/views/admin/order-list.css">
</head>
<body>
<jsp:include page="/views/jsp/demo.jsp"/>
<div class="admin-container">

    <header class="admin-header">
        <h1>ADMIN PANEL</h1>
        <div class="admin-user">
            Xin chào <b>${adminName}</b> |
            <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
        </div>
    </header>

    <aside class="admin-sidebar">
        <a href="${pageContext.request.contextPath}/admin/dashboard">🏠 Dashboard</a>
        <a href="${pageContext.request.contextPath}/admin/product">🍔 Quản lý món ăn</a>
        <a href="${pageContext.request.contextPath}/admin/order">📦 Quản lý đơn hàng</a>
        <a href="${pageContext.request.contextPath}/admin/user">👤 Quản lý user</a>
    </aside>

    <main class="admin-content">

        <div class="page-header">
            <h2>Tổng số đơn hàng</h2>
            <a href="${pageContext.request.contextPath}/admin/dashboard"
               class="btn-back">← Quay lại</a>
        </div>

        <div class="card single">
            <p>📦 Tổng số order hiện tại</p>
            <p style="font-size:36px;font-weight:bold;">
                ${totalOrders}
            </p>
        </div>

    </main>

</div>

</body>
</html>
