<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:choose>
		<c:when test="${not empty food}">
            ${food.name}
        </c:when>
		<c:otherwise>
            Chi tiết sản phẩm
        </c:otherwise>
	</c:choose></title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/views/Shared/product-detail.css">
</head>

<body>

	<div class="page-container">

		<!-- HEADER -->
		<jsp:include page="/views/jsp/demo.jsp" />

		<!-- MAIN CONTENT -->
		<main class="content">
		
<c:if test="${not empty header.referer and not fn:contains(header.referer, '/login')}">
        <a href="${header.referer}" class="btn-back">← Quay lại</a>
</c:if>

			<!--  ERROR -->
			<c:if test="${not empty error}">
				<h2 style="color: red; text-align: center; margin: 40px 0;">
					${error}</h2>
			</c:if>

			<!-- CÓ SẢN PHẨM -->
			<c:if test="${not empty food}">

				<div class="breadcrumb">
					Trang chủ / <b>${food.name}</b>
				</div>

				<div class="food-detail">

					<!-- HÌNH -->
					<div class="food-image">
						<img src="${pageContext.request.contextPath}/images/${food.image}"
							onerror="this.src='${pageContext.request.contextPath}/images/default-food.jpg'"
							alt="${food.name}">
					</div>

					<!-- THÔNG TIN -->
					<div class="food-info">
						<h1>${food.name}</h1>

						<p class="status">
							Tình trạng: <span class="in-stock">Còn hàng</span>
						</p>

						<div class="price">
							<span class="new-price">${food.price} ₫</span> <span
								class="old-price">${food.price + 10000} ₫</span>
						</div>

						<div class="promo-box">
							🔥 <b>Siêu Ưu Đãi</b>
							<ul>
								<li>Nhập mã <b>EGANY</b> giảm 15%
								</li>
								<li>Số lượng có hạn</li>
							</ul>
						</div>

						<!-- ADD TO CART -->

						<form action="${pageContext.request.contextPath}/cart"
							method="get">

							 <input
								type="hidden" name="foodId" value="${food.id}">

							<div class="quantity">
								<input type="number" name="quantity" value="1" min="1">
							</div>

<button type="submit" name="action" value="add" class="btn-cart">
    THÊM VÀO GIỎ
</button>

<button type="submit" name="action" value="buy" class="btn-buy">
    MUA NGAY
</button>

						</form>
					</div>
				</div>
			</c:if>

		</main>

		<!-- FOOTER -->
		<jsp:include page="/views/jsp/footer.jsp" />

	</div>

</body>
</html>
