<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quên mật khẩu</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/views/Shared/login.css">

    <style>

        .login-extra {
            margin-top: 15px;
            font-size: 13px;
            text-align: center;
        }

        .login-extra a {
            color: #e53935;
            text-decoration: none;
        }

        .login-extra a:hover {
            text-decoration: underline;
        }

        .message-error{
            color:red;
            text-align:center;
            margin-bottom:12px;
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

        <h2>Quên mật khẩu</h2>

        <c:if test="${not empty error}">
            <p class="message-error">
                    ${error}
            </p>
        </c:if>

        <form action="${pageContext.request.contextPath}/forgot-password"
              method="post">

            <input type="email"
                   name="email"
                   placeholder="Nhập email của bạn"
                   required>

            <button type="submit">
                Gửi mã OTP
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