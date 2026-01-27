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
		<!-- 🔥 WRAPPER -->

		<!-- HEADER -->
		<jsp:include page="/views/jsp/demo.jsp" />

		<!-- NAV -->
		<div class="nav">
 <a class="${empty param.action ? 'active' : ''}"
       href="${pageContext.request.contextPath}/Trangchu">
        Trang Chủ
    </a>

    <a class="${param.ID == '1' ? 'active' : ''}"
       href="${pageContext.request.contextPath}/Trangchu?action=category&ID=1">
        Chicken
    </a>

    <a class="${param.ID == '2' ? 'active' : ''}"
       href="${pageContext.request.contextPath}/Trangchu?action=category&ID=2">
        Pizza
    </a>

    <a class="${param.ID == '3' ? 'active' : ''}"
       href="${pageContext.request.contextPath}/Trangchu?action=category&ID=3">
        Snack
    </a>

    <a class="${param.ID == '4' ? 'active' : ''}"
       href="${pageContext.request.contextPath}/Trangchu?action=category&ID=4">
        Drink
    </a>

    <a class="${param.ID == '6' ? 'active' : ''}"
       href="${pageContext.request.contextPath}/Trangchu?action=category&ID=6">
        Burger
    </a>

</div>


		<!-- MAIN CONTENT -->
		<main class="page-content">
			<!-- 🔥 -->

			<div class="section">
				<div class="section-title">${title}</div>

				<div class="products">

					<c:if test="${empty foodlist}">
						<p>❌ Không có dữ liệu</p>
					</c:if>

					<c:forEach var="f" items="${foodlist}">

						<div class="product-card">

							<!-- CLICK VÀO ĐÂY MỚI VÀO CHI TIẾT -->
					    <!-- VÙNG CLICK CHI TIẾT -->
    <a class="product-link"
       href="${pageContext.request.contextPath}/product-detail?id=${f.id}">
        <img src="${pageContext.request.contextPath}/images/${f.image}"
             onerror="this.src='${pageContext.request.contextPath}/images/default-food.jpg'">
        <h3 class="product-name">${f.name}</h3>
    </a>
							<div class="product-price">${f.price}VND</div>
							<div class="product-note">#${f.id}-Hasky</div>

							<div class="btn-group">

								<!-- ADD CART -->
								<a class="btn-cart"
									href="${pageContext.request.contextPath}/cart?action=add&foodId=${f.id}&quantity=1">
									THÊM VÀO GIỎ </a>

								<!-- BUY NOW -->
								<a class="btn-buy"
									href="${pageContext.request.contextPath}/cart?action=add&foodId=${f.id}&quantity=1&buyNow=true">
									MUA NGAY </a>

							</div>

						</div>

					</c:forEach>

				</div>
			</div>

			<c:if test="${title eq 'Món ăn nổi bật'}">
				<div class="view-all">
					<a href="${pageContext.request.contextPath}/Trangchu?action=all">
						Xem tất cả </a>
				</div>
			</c:if>

		</main>

		<!-- FOOTER -->
		<jsp:include page="/views/jsp/footer.jsp" />

	</div>

</body>
</html>
