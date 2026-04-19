 <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng Nhập</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/views/Shared/login.css">
    <style>
    /* ===== SOCIAL ===== */
    .social-login {
    display: flex;
    justify-content: center;
    gap: 12px;
    margin-top: 12px;
    }

    .social-btn {
    width: 38px;
    height: 38px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    }

    .social-btn img {
    width: 20px;
    height: 20px;
    }

    .google {
    background: #fff;
    border: 1px solid #ddd;
    }

    /* ===== EXTRA ===== */
    .login-extra {
    margin-top: 15px;
    font-size: 13px;
    }

    .login-extra a {
    color: #e53935;
    text-decoration: none;
    }

    .login-extra a:hover {
    text-decoration: underline;
    }

    .separator {
    margin: 0 6px;
    color: #aaa;
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
        <h2>Đăng nhập</h2>

        <c:if test="${not empty error}">
            <p style="color:red; text-align:center;">
                    ${error}
            </p>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">

            <input type="text"
                   name="username"
                   placeholder="Tên đăng nhập"
                   value="${param.username}"
                   required>

            <div class="password-wrapper">
                <input type="password"
                       id="password"
                       name="password"
                       placeholder="Mật khẩu"
                       required>

                <span class="eye" onclick="togglePassword()">👁️</span>
            </div>

            <button type="submit">Đăng nhập</button>
            <div class="social-login">
                <a href="${pageContext.request.contextPath}/login-google" class="social-btn google">
                    <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/google/google-original.svg"/>
                </a>
            </div>
            <div class="login-extra">
                <span>Chưa có tài khoản?</span>
                <a href="register">Đăng ký</a>
                <span class="separator">|</span>
                <a href="forgot-password">Quên mật khẩu?</a>
            </div>

        </form>

    </div>
</div>

<script>
    function togglePassword() {
        const pass = document.getElementById("password");

        if (pass.type === "password") {
            pass.type = "text";
        } else {
            pass.type = "password";
        }
    }
</script>
</body>
</html>
