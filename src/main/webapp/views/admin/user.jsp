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
        <c:if test="${not empty sessionScope.successMessage}">
            <div class="success-message">
                    ${sessionScope.successMessage}
            </div>
            <c:remove var="successMessage" scope="session"/>
        </c:if>
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
                                <a class="action-btn"
                                   style="background:#f59e0b;color:white;"
                                   href="javascript:void(0);"
                                   onclick="showChangePass(${acc.idAccount})">
                                    🔑 Đổi pass
                                </a>
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
    <div id="changePassModal"
         style="display:none;position:fixed;top:0;left:0;width:100%;
     height:100%;background:rgba(0,0,0,0.5);z-index:9999;">

        <div style="background:white;width:400px;padding:20px;
         margin:120px auto;border-radius:10px;">

            <h3>Đổi mật khẩu</h3>

            <form action="${pageContext.request.contextPath}/admin/user/change-password"
                  method="post">

                <input type="hidden" id="userId" name="id">

                <label>Mật khẩu mới:</label><br>
                <input type="password"
                       name="newPassword"
                       required
                       style="width:100%;padding:8px;margin-top:8px;margin-bottom:15px;">

                <button type="submit" class="btn-add">
                    Lưu
                </button>

                <button type="button"
                        onclick="closeChangePass()"
                        style="margin-left:10px;">
                    Hủy
                </button>

            </form>
        </div>
    </div>

    <script>
        function showChangePass(id) {
            document.getElementById("userId").value = id;
            document.getElementById("changePassModal").style.display = "block";
        }

        function closeChangePass() {
            document.getElementById("changePassModal").style.display = "none";
        }
    </script>
    <jsp:include page="/views/jsp/notification.jsp"/>
</body>
</html>