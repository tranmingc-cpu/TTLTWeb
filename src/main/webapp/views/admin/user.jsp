<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib uri="jakarta.tags.core" prefix="c"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <title>Quản Lý User</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/views/admin/user.css">
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
    <!-- MAIN CONTENT -->
<main class="admin-content">

    <h2>Quản lý tài khoản</h2>

    <table class="user-table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Tên tài khoản</th>
                <th>Role</th>
            </tr>
        </thead>

        <tbody>
        <c:choose>
            <c:when test="${not empty accounts}">
                <c:forEach var="acc" items="${accounts}">
                    <tr>
                        <td>${acc.id}</td>
                        <td>${acc.username}</td>
                        <td>${acc.role}</td>

                 
                    </tr>
                </c:forEach>
            </c:when>

            <c:otherwise>
                <tr>
                    <td colspan="5">Không có tài khoản nào</td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>

</main>
    

</body>
</html>