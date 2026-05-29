<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>MENU</title>
	<link rel="stylesheet"
		  href="${pageContext.request.contextPath}/views/Shared/demo.css">
</head>
<body>
<div class="topbar">
	<div class="container topbar-row">
		<div class="topbar-left">
			<div class="top-item">Hỗ trợ</div>
			<div class="top-item">Liên hệ</div>
			<div class="top-item">Kết nối</div>
			<div class="user-popup" id="userPopup">
				<div class="popup-item">👤 Đăng nhập</div>
				<div class="popup-item">📝 Đăng ký</div>
				<hr>
			</div>
		</div>
		<div class="topbar-right">
			<div class="top-item lang-dropdown">
				🌐 Ngôn ngữ
				<div class="lang-menu">
					<div>🇻🇳 Tiếng Việt</div>
					<div>🇺🇸 English</div>

				</div>
			</div>
			<c:if
					test="${sessionScope.account != null 
          && sessionScope.account.role eq 'SELLER'}">
				<a href="${pageContext.request.contextPath}/seller"> Kênh người
					bán </a>
			</c:if>
			<div class="top-item">Thông báo</div>
		</div>

	</div>
</div>
<div class="header">
	<div class="container header-row">

		<a class="logo" href="${pageContext.request.contextPath}/Trangchu">
			<span class="logo-icon">🍛</span> <span class="logo-text">QUANQUE</span>
		</a>

		<form action="${pageContext.request.contextPath}/search" method="get"
			  class="search-box">

			<input type="text" name="keyword" list="foodList"
				   placeholder="Tìm đồ ăn, đồ uống" value="${param.keyword}">

			<button type="submit">🔍</button>
			<datalist id="foodList">
				<c:forEach var="f" items="${foodlist}">
					<option value="${f.name}" />
				</c:forEach>
			</datalist>

		</form>

		<div class="header-actions">
			<a href="${pageContext.request.contextPath}/cart" class="cart-link">
				<div class="circle-btn cart-btn">
					🛒
					<c:if test="${cartCount > 0}">
						<span class="cart-badge">${cartCount}</span>
					</c:if>
				</div>
			</a>
			<div class="user-wrapper" tabindex="0">

				<button type="button" class="user-btn"
						onclick="toggleUserMenu(event)">
					<span class="circle-btn">👤</span>

					<c:if test="${not empty sessionScope.account}">
						<span class="userName">${sessionScope.account.userName}</span>
					</c:if>
				</button>

				<div class="user-menu" id="userMenu">
					<c:if test="${empty sessionScope.account}">
						<a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
						<a href="${pageContext.request.contextPath}/register">Đăng ký</a>
					</c:if>

					<c:if test="${not empty sessionScope.account}">
						<a href="${pageContext.request.contextPath}/profile">Tài
							khoản</a>
						<c:choose>
							<c:when test="${sessionScope.account.role eq 'ADMIN'}">
								<a href="${pageContext.request.contextPath}/admin/dashboard">
									Quản lý
								</a>
							</c:when>

							<c:otherwise>
								<a href="${pageContext.request.contextPath}/orderHistory">
									Đơn hàng
								</a>
							</c:otherwise>
						</c:choose>
						<a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
					</c:if>
				</div>

			</div>

		</div>
	</div>

</div>

</body>
</html>