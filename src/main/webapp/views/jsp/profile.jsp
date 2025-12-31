<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thông tin cá nhân</title>
    <link rel="stylesheet"
	href="${pageContext.request.contextPath}/FoodOrderWeb/views/Shared/profile.css">
    <jsp:include page="/views/jsp/demo.jsp"></jsp:include>
    
</head>
<body>

<div class="profile-container">
    <h2>Thông tin cá nhân</h2>

    <!-- AVATAR -->
    <div class="profile-avatar">
        <img src="images/user.png" alt="Avatar">
    </div>

    <!-- FORM UPDATE -->
    <form action="updateProfile" method="post" class="profile-form">
        <input type="hidden" name="idAccount" value="${sessionScope.account.idAccount}">

        <div class="form-group">
            <label>Tên đăng nhập</label>
            <input type="text" value="${sessionScope.account.userName}" disabled>
        </div>

        <div class="form-group">
            <label>Email</label>
            <input type="email" name="email" value="${sessionScope.account.email}">
        </div>

        <div class="form-group">
            <label>Số điện thoại</label>
            <input type="text" name="number" value="${sessionScope.account.number}">
        </div>

        <div class="form-group">
            <label>Địa chỉ</label>
            <input type="text" name="address" value="${sessionScope.account.address}">
        </div>

        <div class="form-group">
            <label>Mật khẩu mới</label>
            <input type="password" name="password" placeholder="Để trống nếu không đổi">
        </div>

        <button type="submit" class="btn-save">Lưu thay đổi</button>
    </form>
</div>

</body>
</html>
