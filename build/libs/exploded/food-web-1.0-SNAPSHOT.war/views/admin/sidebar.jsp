<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/views/admin/sidebar.css">
</head>
<body>

    <div class="admin-header">
        <h1>ADMIN PANEL</h1>
    </div>

    <aside class="admin-sidebar">
        <a href="${pageContext.request.contextPath}/admin/dashboard">🏠 Dashboard</a>
        <a href="${pageContext.request.contextPath}/admin/product">🍔 Quản lý món ăn</a>
        <a href="${pageContext.request.contextPath}/admin/order">📦 Quản lý đơn hàng</a>
        <a href="${pageContext.request.contextPath}/admin/user">👤 Quản lý user</a>
        <a href="${pageContext.request.contextPath}/admin/coupon">🎟️ Quản lý Coupon</a>
        <c:if test="${sessionScope.account.role == 'SUPER_ADMIN'}">
            <li>
                <a href="${pageContext.request.contextPath}/admin/permission">Phân quyền Admin</a>
            </li>
        </c:if>
    </aside>

</body>
</html>
