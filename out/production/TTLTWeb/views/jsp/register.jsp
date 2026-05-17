<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/views/Shared/register.css">
</head>
<body>
<div class="page">
    <div class="header-title">
        <h1>
            <span>WELCOM TO </span>
            <small>QUANQUE</small>
        </h1>
    </div>


    <div class="register-container">

        <h2>Đăng ký tài khoản</h2>

        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>

        <c:if test="${not empty success}">
            <p class="success">${success}</p>
        </c:if>

        <form action="register" method="post">
            <input type="text" name="username"
                   placeholder="Tên đăng nhập"
                   value="${param.username}" required>

            <input type="email" name="email"
                   placeholder="Email"
                   value="${param.email}" required>

            <input type="password" name="password"
                   placeholder="Mật khẩu" required>

            <input type="text" name="phone"
                   placeholder="Số điện thoại"
                   value="${param.phone}" required>

            <input type="text" name="address"
                   placeholder="Địa chỉ"
                   value="${param.address}">

            <button type="submit">Đăng ký</button>
        </form>
        <c:if test="${not empty sessionScope.success}">
            <p class="success">${sessionScope.success}</p>
            <c:remove var="success" scope="session"/>
        </c:if>
        <p style="margin-top: 14px;">
            Đã có tài khoản?
            <a href="login">Đăng nhập</a>
        </p>

    </div>
</div>

</body>
</html>
