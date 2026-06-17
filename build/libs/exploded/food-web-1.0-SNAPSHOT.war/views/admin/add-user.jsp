<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thêm tài khoản<</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/add-user.css">

</head>
<body>
<jsp:include page="/views/jsp/demo.jsp"/>

<div class="admin-container">
    <jsp:include page="/views/admin/sidebar.jsp"/>
<div class = "admin-content">
    <main class="admin-content">
        <div class="card-form"> <h2>Thêm tài khoản</h2>
            <c:if test="${not empty error}">
                <div id="toast-error">
                     ${error}
                </div>
            </c:if>
        <c:if test="${not empty sessionScope.successMessage}">
        <div id="toast-success">
                ${sessionScope.successMessage}
        </div>
            <c:remove var="successMessage" scope="session"/>
        </c:if>
            <form action="${pageContext.request.contextPath}/admin/user/add" method="post">
                <div class="form-group">
                    <label>Tên đăng nhập</label>
                    <input type="text" name="username" placeholder="Nhập username..." required>
                </div>

                <div class="form-group">
                    <label>Mật khẩu</label>
                    <input type="password" name="password" placeholder="Nhập mật khẩu..." required>
                </div>

                <div class="form-group">
                    <label>Vai trò</label>
                    <select name="role">
                        <option value="user">User</option>
                        <option value="seller">Seller</option>
                        <option value="admin">Admin</option>
                    </select>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn-submit">Thêm người dùng</button>
                    <a href="${pageContext.request.contextPath}/admin/user" class="btn-back">← Quay lại</a>
                </div>
            </form>
        </div>
    </main>
</div>
<script>
    window.onload = function () {
        const toast = document.getElementById("toast-error");
        if (toast) {
            setTimeout(() => {
                toast.style.transition = "0.5s";
                toast.style.opacity = "0";
                setTimeout(() => {
                    toast.remove();
                }, 500);
            }, 3000);
        }
    }
</script>
</div>
</body>
</html>
