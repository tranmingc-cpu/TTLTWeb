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
		   href="${pageContext.request.contextPath}/Trangchu">Trang Chủ</a>

		<a class="${param.ID == '1' ? 'active' : ''}"
		   href="${pageContext.request.contextPath}/Trangchu?action=category&ID=1">Món Nước</a>

		<a class="${param.ID == '2' ? 'active' : ''}"
		   href="${pageContext.request.contextPath}/Trangchu?action=category&ID=2">Cơm</a>

		<a class="${param.ID == '3' ? 'active' : ''}"
		   href="${pageContext.request.contextPath}/Trangchu?action=category&ID=3">Đồ Ngọt & Tráng Miệng</a>

		<a class="${param.ID == '4' ? 'active' : ''}"
		   href="${pageContext.request.contextPath}/Trangchu?action=category&ID=4">Bánh Việt Nam</a>

		<a class="${param.ID == '5' ? 'active' : ''}"
		   href="${pageContext.request.contextPath}/Trangchu?action=category&ID=5"> Món Theo Mùa</a>

		<a class="${param.ID == '6' ? 'active' : ''}"
		   href="${pageContext.request.contextPath}/Trangchu?action=category&ID=6">Khác</a>
	</div>

	<!-- SLIDER -->
	<div class="promo-slider">
		<div class="slides" id="slides">

			<div class="slide">
				<div class="overlay">
					<h2>🍜 Giảm 30% món bún</h2>
					<p>Ăn ngon mỗi ngày</p>
				</div>
			</div>

			<div class="slide">
				<div class="overlay">
					<h2>🍛 Cơm chỉ từ 35K</h2>
					<p>No bụng giá mềm</p>
				</div>
			</div>

			<div class="slide">
				<div class="overlay">
					<h2>🔥 Combo hấp dẫn</h2>
					<p>Giảm đến 40%</p>
				</div>
			</div>

		</div>
	</div>

	<main class="page-content">

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
							<img src="${f.image}"
							     onerror="this.src='${pageContext.request.contextPath}/images/default-food.jpg'">
							<h3 class="product-name">${f.name}</h3>
						</a>

						<div class="product-price">${f.price} VND</div>

						<div class="btn-group">
							<a class="btn-cart"
							   href="${pageContext.request.contextPath}/cart?action=add&foodId=${f.id}&quantity=1">
								Giỏ
							</a>

							<a class="btn-buy"
							   href="${pageContext.request.contextPath}/cart?action=add&foodId=${f.id}&quantity=1&buyNow=true">
								Mua
							</a>
						</div>

					</div>

				</c:forEach>

			</div>
		</div>

	</main>

	<jsp:include page="/views/jsp/footer.jsp" />
</div>

<!-- JS SLIDER -->
<script>
	let index = 0;
	const slides = document.getElementById("slides");
	const total = slides.children.length;

	setInterval(() => {
		index++;
		if(index >= total) index = 0;
		slides.style.transform = "translateX(-" + (index * 100) + "%)";
	}, 3000);
</script>

</body>
</html>