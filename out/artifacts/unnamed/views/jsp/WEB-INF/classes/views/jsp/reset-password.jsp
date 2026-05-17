<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đặt lại mật khẩu</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/views/Shared/login.css">

    <style>

        .message-error{
            color:red;
            text-align:center;
            margin-bottom:12px;
        }

        .message-success{
            color:green;
            text-align:center;
            margin-bottom:12px;
        }

        .login-extra {
            margin-top: 15px;
            text-align: center;
        }

        .login-extra a {
            color: #e53935;
            text-decoration: none;
        }

    </style>
</head>
<body>

<div class="page">

    <div class="header-title">
        <h1>
            <span>WELCOM TO </span>
            <small>QUANQUE</small>
        </h1>
    </div>

    <div class="login-box">

        <h2>Đặt lại mật khẩu</h2>

        <c:if test="${not empty error}">
            <p class="message-error">${error}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/reset-password"
              method="post">

            <input type="text"
                   name="otp"
                   placeholder="Nhập mã OTP"
                   required>

            <input type="password"
                   name="password"
                   placeholder="Mật khẩu mới"
                   required>

            <button type="submit">
                Đổi mật khẩu
            </button>

        </form>

        <div class="login-extra">
            <a href="${pageContext.request.contextPath}/login">
                ← Quay lại đăng nhập
            </a>
        </div>

    </div>

</div>

</body>
</html>