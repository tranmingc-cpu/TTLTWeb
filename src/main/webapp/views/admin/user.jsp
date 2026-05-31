<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản Lý User</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/user.css">
</head>
<body>

<jsp:include page="/views/jsp/demo.jsp"/>

<div class="admin-container">

    <header class="admin-header">
        <h1>ADMIN PANEL</h1>
        <div class="admin-user">
            Xin chào, <span>${sessionScope.account.userName}</span>
            <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
        </div>
    </header>

    <jsp:include page="/views/admin/sidebar.jsp"/>

    <main class="admin-content">
        <h2>Quản lý tài khoản</h2>

        <div style="margin-bottom: 20px;">
            <a href="${pageContext.request.contextPath}/admin/user/add-page" style="text-decoration: none;">
                <button type="button" class="btn-add">➕ Thêm tài khoản</button>
            </a>
        </div>

        <table class="user-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Tên tài khoản</th>
                <th>Role</th>
                <th>Trạng thái</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${not empty accounts}">
                    <c:forEach var="acc" items="${accounts}">
                        <tr>
                            <td>${acc.idAccount}</td>
                            <td><strong>${acc.userName}</strong></td>
                            <td><span class="role-badge">${acc.role}</span></td>
                            <td>
                                <c:choose>
                                    <c:when test="${acc.status == 1}">
                                        <span style="color: #10b981; font-weight: 600;">● Hoạt động</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: #ef4444; font-weight: 600;">● Đã khóa</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:if test="${acc.role.toString() != 'admin'}">
                                    <c:choose>
                                        <c:when test="${acc.status == 1}">
                                            <form action="${pageContext.request.contextPath}/admin/user/status" method="post">
                                                <input type="hidden" name="id" value="${acc.idAccount}">
                                                <input type="hidden" name="status" value="0">
                                                <button type="submit">🔒 Khóa</button>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <form action="${pageContext.request.contextPath}/admin/user/status" method="post">
                                                <input type="hidden" name="id" value="${acc.idAccount}">
                                                <input type="hidden" name="status" value="1">
                                                <button type="submit">🔓 Mở</button>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>

                                    <form action="${pageContext.request.contextPath}/admin/user/delete" method="post">
                                        <input type="hidden" name="id" value="${acc.idAccount}">
                                        <button type="submit" onclick="return confirm('Bạn chắc chắn muốn xóa user này?')">🗑 Xóa</button>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="5" class="no-data">Không có tài khoản nào trong hệ thống</td>
                    </tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </main>

</div>

</body>
</html>