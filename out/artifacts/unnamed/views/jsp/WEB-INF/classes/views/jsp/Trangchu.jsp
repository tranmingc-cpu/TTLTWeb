<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<!DOCTYPE html>
<html lang="vi">
<head>
	<meta charset="UTF-8">
	<title>Food Online</title>
	<link rel="stylesheet"
		  href="${pageContext.request.contextPath}/views/Shared/index.css">
</head>

<body>

<div class="page-container">

	<jsp:include page="/views/jsp/demo.jsp" />

	<div class="nav">
		<a class="${empty param.action ? 'active' : ''}"
		   href="${pageContext.request.contextPath}/Trangchu.jsp">Trang Chủ</a>

		<a class="${param.ID == '1' ? 'active' : ''}"
		   href="${pageContext.request.contextPath}/Trangchu?action=category&ID=1">Món Nước</a>

		<a class="${param.ID == '2' ? 'active' : ''}"
		   href="${pageContext.request.contextPath}/Trangchu?action=category&ID=2">Cơm</a>

		<a class="${param.ID == '3' ? 'active' : ''}"
		   href="${pageContext.request.contextPath}/Trangchu?action=category&ID=3">Đồ Ngọt & Tráng Miệng</a>

		<a class="${param.ID == '4' ? 'active' : ''}"
		   href="${pageContext.request.contextPath}/Trangchu?action=category&ID=4">Bánh Việt Nam</a>

		<a class="${param.ID == '5' ? 'active' : ''}"
		   href="${pageContext.request.contextPath}/Trangchu?action=category&ID=5">Món Theo Mùa</a>
		<a class="${param.ID == '6' ? 'active' : ''}"
		   href="${pageContext.request.contextPath}/Trangchu?action=category&ID=6">Khác</a>
	</div>

	<div class="promo-slider">
		<div class="slides">
			<div class="slide" onclick="location.href='${pageContext.request.contextPath}/Trangchu?action=category&ID=1'">
				<div class="overlay">
					<h2>🍜 Giảm 30% món bún</h2>
					<p>Ăn ngon mỗi ngày</p>
				</div>
			</div>
			<div class="slide" onclick="location.href='${pageContext.request.contextPath}/Trangchu?action=category&ID=2'">
				<div class="overlay">
					<h2>🍛 Cơm chỉ từ 35K</h2>
					<p>No bụng giá mềm</p>
				</div>
			</div>
			<div class="slide" onclick="location.href='${pageContext.request.contextPath}/Trangchu?action=all'">
				<div class="overlay">
					<h2>🔥 Combo hấp dẫn</h2>
					<p>Giảm đến 40%</p>
				</div>
			</div>
		</div>
	</div>
	<main class="page-content">

		<div class="filter-bar">

			<form action="${pageContext.request.contextPath}/search" method="get">

				<!-- giữ keyword -->
				<input type="hidden" name="keyword" value="${param.keyword}">

				<!-- category -->
				<select name="categoryId">
					<option value="">Danh mục</option>
					<option value="1">Món nước</option>
					<option value="2">Cơm</option>
				</select>

				<!-- giá -->
				<input type="number" name="minPrice" placeholder="Từ">
				<input type="number" name="maxPrice" placeholder="Đến">

				<button>Lọc</button>

			</form>

		</div>

		<div class="section">
			<div class="section-title">${title}</div>

			<div class="products">

				<c:if test="${empty foodlist}">
					<p>❌ Không có dữ liệu</p>
				</c:if>

				<c:forEach var="f" items="${foodlist}">

					<div class="product-card">

						<a class="product-link"
						   href="${pageContext.request.contextPath}/product-detail?id=${f.id}">
							<img src="${pageContext.request.contextPath}/images/${f.image}"
								 onerror="this.src='${pageContext.request.contextPath}/images/default-food.jpg'">
							<h3 class="product-name">${f.name}</h3>
						</a>

						<div class="product-price">${f.price} VND</div>
						<div class="product-note">#${f.id}-Hasky</div>

						<div class="btn-group">
							<a class="btn-cart"
							   href="${pageContext.request.contextPath}/cart?action=add&foodId=${f.id}&quantity=1">
								THÊM VÀO GIỎ
							</a>

							<a class="btn-buy"
							   href="${pageContext.request.contextPath}/cart?action=add&foodId=${f.id}&quantity=1&buyNow=true">
								MUA NGAY
							</a>
						</div>

					</div>
				</c:forEach>

			</div>
		</div>

		<c:if test="${title eq 'Món ăn nổi bật'}">
			<div class="view-all">
				<a href="${pageContext.request.contextPath}/Trangchu?action=all">
					Xem tất cả
				</a>
			</div>
		</c:if>

	</main>

	<jsp:include page="/views/jsp/footer.jsp" />

</div>

</body>
</html>