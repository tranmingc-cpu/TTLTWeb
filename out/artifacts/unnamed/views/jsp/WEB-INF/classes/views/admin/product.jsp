<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tổng số sản phẩm </title>
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

        <h2>Danh sách món ăn</h2>

        <table class="food-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Ảnh</th>
                <th>Tên món</th>
            </tr>
            </thead>

            <tbody>
            <c:choose>
                <c:when test="${not empty foods}">
                    <c:forEach var="f" items="${foods}">
                        <tr>
                            <td>${f.id}</td>

                            <td>
                                <img class="food-img"
                                     src="${pageContext.request.contextPath}/image/${f.image}"
                                     alt="${f.name}">
                            </td>

                            <td>${f.name}</td>
                        </tr>
                    </c:forEach>
                </c:when>

                <c:otherwise>
                    <tr>
                        <td colspan="3">Không có món ăn nào</td>
                    </tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>

    </main>
</div>
</body>
</html>