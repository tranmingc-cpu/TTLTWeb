<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thông tin cá nhân</title>


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/views/Shared/profile.css">

</head>
<body>

	<!-- HEADER -->
	<jsp:include page="/views/jsp/demo.jsp" />
   <div class="Account"> TÀI KHOẢN </div>
	<div class="profile-wrapper">

		<div class="profile-card">

			<!-- AVATAR -->
			<div class="profile-avatar">
				<img src="${pageContext.request.contextPath}/images/user.png"
					alt="Avatar">
				<h3>${sessionScope.account.userName}</h3>
			</div>

			<!-- FORM -->
			<form action="${pageContext.request.contextPath}/profile"
				method="post" class="profile-form">

				<input type="hidden" name="idAccount"
					value="${sessionScope.account.idAccount}">

				<div class="form-group">
					<label>Tên đăng nhập</label> <input type="text"
						value="${sessionScope.account.userName}" disabled>
				</div>
				<div class="form-group">
					<label>Họ và Tên</label> <input type="text" name="fullName"
						value="${profile.fullname}">
				</div>

				<div class="form-group">
					<label>Email</label> <input type="email" name="email"
						value="${profile.email}">
				</div>

				<div class="form-group">
					<label>Số điện thoại</label> <input type="text" name="number"
						value="${profile.number}">
				</div>

				<div class="form-group">
					<label>Địa chỉ</label> <input type="text" name="address"
						value="${profile.address}">
				</div>

				<div class="form-group">
					<label>Mật khẩu mới</label> <input type="password" name="password"
						placeholder="Để trống nếu không đổi">
				</div>

				<button type="submit" class="btn-save">💾 Lưu thay đổi</button>

			</form>
		</div>

	</div>
<jsp:include page="/views/jsp/footer.jsp" />
</body>
</html>
