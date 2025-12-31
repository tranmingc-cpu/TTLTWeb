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
	<!-- TOP BAR -->
	<div class="topbar">
		<div class="container topbar-row">
			<div class="topbar-left">
				<div class="top-item">Hỗ trợ</div>
				<div class="top-item">Liên hệ</div>
				<div class="top-item">Kết nối</div>
				<!-- USER POPUP -->
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
				<div class="top-item">Kênh người bán</div>
				<div class="top-item">Thông báo</div>
			</div>

		</div>
	</div>

	<!-- MAIN HEADER -->
	<div class="header">
		<div class="container header-row">

			<a class="logo" href="#"> <span class="logo-icon">🍗</span> <span
				class="logo-text">FOOD ONLINE</span>
			</a>
			<div class="search-box">
				<input type="text" placeholder="Tìm đồ ăn, đồ uống">
				<button>🔍</button>
			</div>
 <div class="header-actions">
    <!-- USER -->
 
    <div class="user-wrapper" tabindex="0">
        <div class="circle-btn">U</div>

        <!-- CHỈ ĐƯỢC ĐỂ LINK Ở ĐÂY -->
  
        <div class="user-menu">
    <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
    <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
</div>
        
        </div>
    </div>

    <div class="circle-btn cart-btn">
        🛒
        <c:if test="${cartCount > 0}">
            <span class="cart-badge">${cartCount}</span>
        </c:if>
    </div>

</div>
  


	</div>
	</div>

</body>
</html>