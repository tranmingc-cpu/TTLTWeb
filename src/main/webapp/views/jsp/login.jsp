<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng Nhập</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/views/Shared/login.css">
</head>
<body>

<div class="page">

    <div class="header-title">
        <h1>
            FOOD<span>ORDER</span>
            <small>ONLINE</small>
        </h1>
    </div>

    <div class="login-box">
        <h2>Đăng nhập</h2>

        <!-- HIỂN THỊ LỖI -->
        <c:if test="${not empty error}">
            <p style="color:red; text-align:center;">
                ${error}
            </p>
        </c:if>

        <!-- FORM PHẢI BAO TRÙM HẾT -->
        <form action="${pageContext.request.contextPath}/login" method="post">

            <input type="text"
                   name="username"
                   placeholder="Tên đăng nhập"
                   value="${param.username}"
                   required>

            <div class="password-wrapper">
                <input type="checkbox" id="showPass" hidden>

                <!-- PASSWORD ẨN -->
                <input type="password"
                       name="password"
                       placeholder="Mật khẩu"
                       class="password password-hide"
                       required>

                <!-- PASSWORD HIỆN (KHÔNG name) -->
                <input type="text"
                       placeholder="Mật khẩu"
                       class="password password-show">

                <label for="showPass" class="eye">👁️</label>
            </div>

            <button type="submit">Đăng nhập</button>
            <div class="login-extra">
    <span>Chưa có tài khoản?</span>
    <a href="register">Đăng ký</a>
    <span class="separator">|</span>
    <a href="forgot-password">Quên mật khẩu?</a>
</div>
            	
        </form>

    </div>
</div>

</body>
</html>
