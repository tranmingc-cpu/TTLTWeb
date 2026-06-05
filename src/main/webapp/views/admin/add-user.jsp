
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thêm tài khoản<</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/add-user.css">

</head>
<body>
<jsp:include page="/views/jsp/demo.jsp"/>

<div class="admin-container">
    <div class="admin-header">
        <h1>ADMIN PANEL</h1>
    </div>

    <jsp:include page="/views/admin/sidebar.jsp"/>

    <main class="admin-content">
        <div class="card-form"> <h2>Thêm tài khoản</h2>

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
</body>
</html>
