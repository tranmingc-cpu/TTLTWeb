<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng Nhập</title>
    <link rel="stylesheet" href="/FoodOrderWeb/views/Shared/login.css">
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

        <!-- HIỂN THỊ LỖI ĐĂNG NHẬP -->
        <c:if test="${not empty error}">
            <p style="color:red; text-align:center; margin-bottom:10px;">
                ${error}
            </p>
        </c:if>

        <form action="login" method="post">
            <input type="text" name="username"
                   placeholder="Tên đăng nhập"
                   value="${param.username}"
                   required>
                   </form>
                                      
<div class="password-wrapper">

    <input type="checkbox" id="showPass" hidden>

    <!-- PASSWORD ẨN -->
    <input type="password"
           name="password"
           placeholder="Mật khẩu"
           class="password password-hide"
           required>

    <!-- PASSWORD HIỆN -->
    <input type="text"
           name="password"
           placeholder="Mật khẩu"
           class="password password-show"
           required>

    <label for="showPass" class="eye">👁️</label>

</div>
			<button type="submit">Đăng nhập</button>


		</div>
</body>
</html>
