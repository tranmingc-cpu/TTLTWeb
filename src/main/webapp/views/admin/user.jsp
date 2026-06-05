<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản Lý User</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/user.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/Shared/notification.css">

</head>
<body>

<jsp:include page="/views/jsp/demo.jsp"/>

<div class="admin-container">
    <div class="admin-header">
        <h1>ADMIN PANEL</h1>
    </div>

    <jsp:include page="/views/admin/sidebar.jsp"/>

    <div class="main-content">
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
                                    <c:choose>
                                        <c:when test="${acc.status == 1}">
                                            <a class="action-btn btn-lock"
                                               href="${pageContext.request.contextPath}/admin/user?action=lock&id=${acc.idAccount}">
                                                🔒 Khóa
                                            </a>
                                        </c:when>

                                        <c:otherwise>
                                            <a class="action-btn btn-unlock"
                                               href="${pageContext.request.contextPath}/admin/user?action=unlock&id=${acc.idAccount}">
                                                🔓 Mở
                                            </a>
                                        </c:otherwise>
                                    </c:choose>

                                    <a class="action-btn btn-delete btn-delete-trigger"
                                       href="javascript:void(0);"
                                       data-url="${pageContext.request.contextPath}/admin/user?action=delete&id=${acc.idAccount}"
                                       data-message="Bạn có chắc chắn muốn xóa tài khoản này không?">
                                        🗑️ Xóa
                                    </a>
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
    <jsp:include page="/views/jsp/notification.jsp"/>

</body>
</html>