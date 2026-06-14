<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

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
	<style>

		.breadcrumb{
			display:flex;
			align-items:center;
			gap:12px;
			margin-bottom:25px;
			font-size:15px;
		}

		.back-link{
			background:#ff5722;
			color:white;
			text-decoration:none;
			padding:8px 16px;
			border-radius:8px;
			font-weight:600;
		}

		.back-link:hover{
			background:#e64a19;
		}

		.food-detail{
			display:flex;
			gap:60px;
			background:white;
			padding:40px;
			border-radius:20px;
			box-shadow:0 8px 30px rgba(0,0,0,.08);
		}

		.food-image img{
			width:100%;
			max-width:520px;
			border-radius:18px;
		}

		.food-info h1{
			font-size:42px;
			margin-bottom:20px;
		}

		.quantity{
			display:flex;
			align-items:center;
			gap:12px;
			margin-bottom:20px;
		}

		.quantity label{
			font-weight:600;
		}

		.quantity input{
			width:90px;
			height:42px;
			text-align:center;
			border:1px solid #ccc;
			border-radius:8px;
		}

		.action-buttons{
			display:flex;
			gap:15px;
			margin-top:20px;
		}

		.action-buttons button{
			flex:1;
			height:52px;
			border:none;
			border-radius:12px;
			color:white;
			font-weight:700;
			cursor:pointer;
		}

		.btn-cart{
			background:#dc2626;
		}

		.btn-buy{
			background:#f97316;
		}

		@media(max-width:900px){
			.food-detail{
				flex-direction:column;
			}

			.action-buttons{
				flex-direction:column;
			}
		}
		.related-products{
			margin-top:40px;
			background:#fff;
			padding:30px;
			border-radius:20px;
			box-shadow:0 8px 30px rgba(0,0,0,.08);
		}

		.related-products h2{
			margin-bottom:25px;
			font-size:28px;
			color:#1f2937;
		}

		.related-list{
			display:grid;
			grid-template-columns:repeat(auto-fill,minmax(220px,1fr));
			gap:20px;
		}

		.related-item{
			background:#fff;
			border:1px solid #e5e7eb;
			border-radius:16px;
			overflow:hidden;
			transition:.3s;
		}

		.related-item:hover{
			transform:translateY(-5px);
			box-shadow:0 10px 25px rgba(0,0,0,.12);
		}

		.related-item a{
			text-decoration:none;
			color:inherit;
			display:block;
		}

		.related-item img{
			width:100%;
			height:180px;
			object-fit:cover;
		}

		.related-item h4{
			padding:12px 15px 6px;
			font-size:17px;
			color:#111827;

			overflow:hidden;
			text-overflow:ellipsis;
			white-space:nowrap;
		}

		.related-item p{
			padding:0 15px 15px;
			font-size:18px;
			font-weight:700;
			color:#e63946;
		}

		/* Mobile */

		@media(max-width:768px) {

			.related-products {
				padding: 20px;
			}

			.related-products h2 {
				font-size: 22px;
			}

			.related-list {
				grid-template-columns:repeat(2, 1fr);
				gap: 15px;
			}

			.related-item img {
				height: 140px;
			}

			.related-item h4 {
				font-size: 15px;
			}

			.related-item p {
				font-size: 16px;
			}
		}

	</style>
</head>

<body>

<div class="page-container">

	<jsp:include page="/views/jsp/demo.jsp" />
	<main class="content">

		<c:if test="${not empty error}">
			<h2 style="color: red; text-align: center; margin: 40px 0;">
					${error}</h2>
		</c:if>

		<c:if test="${not empty food}">

			<div class="breadcrumb">
				<c:if test="${not empty header.referer}">
					<a href="${header.referer}" class="back-link">
						← Quay lại
					</a>
					<span class="separator">/</span>
				</c:if>

				<a href="${pageContext.request.contextPath}/Trangchu">
					Trang chủ
				</a>

				<span class="separator">/</span>

				<span class="current">${food.name}</span>
			</div>

			<div class="food-detail">

				<div class="food-image">
					<img src="${food.image}"
					     onerror="this.src='${pageContext.request.contextPath}/images/default-food.jpg'"
					     alt="${food.name}">
				</div>

				<div class="food-info">
					<h1>${food.name}</h1>

					<p class="status">
						Tình trạng:
						<c:choose>
							<c:when test="${food.quantity > 0}">
								<span class="in-stock">Còn hàng</span>
							</c:when>

							<c:otherwise>
								<span class="out-stock">Hết hàng</span>
							</c:otherwise>
						</c:choose>
					</p>
					<p class="stock">
						Số lượng còn:
						<b>${food.quantity}</b>
					</p>
					<div class="price">
						<span class="new-price">
    <fmt:formatNumber value="${food.price}" type="number"/> ₫
</span>
						<span class="old-price">
    <fmt:formatNumber value="${food.price * 1.2}" type="number" maxFractionDigits="0"/> ₫
</span>
					</div>
					<div class="description">
						<h3>Mô tả món ăn</h3>
						<p>${food.description}</p>
					</div>
					<div class="promo-box">
						🔥 <b>Siêu Ưu Đãi</b>
						<ul>
							<li>Nhập mã <b>EGANY</b> giảm 15%
							</li>
							<li>Số lượng có hạn</li>
						</ul>
					</div>


					<form action="${pageContext.request.contextPath}/cart"
						  method="post">

						<input type="hidden" name="foodId" value="${food.id}">

						<div class="quantity">
							<label for="quantityInput">Số lượng:</label>
							<input type="number" id="quantityInput" name="quantity" value="1" min="1"  max="${food.quantity}">
						</div>
						<c:choose>

							<c:when test="${food.quantity > 0}">
						<div class="action-buttons">
								<button type="submit" name="action" value="add" class="btn-cart">
									THÊM VÀO GIỎ
								</button>

								<button type="submit" name="action" value="buy" class="btn-buy">
									MUA NGAY
								</button>
						</div>
							</c:when>

							<c:otherwise>
								<button class="btn-cart" disabled>
									HẾT HÀNG
								</button>
							</c:otherwise>

						</c:choose>

					</form>
				</div>
			</div>
		</c:if>
		<c:if test="${not empty relatedFoods}">
			<section class="related-products">
				<h2>Sản phẩm liên quan</h2>
				<div class="related-list">
					<c:forEach items="${relatedFoods}" var="item">
						<div class="related-item">
							<a href="${pageContext.request.contextPath}/product-detail?id=${item.id}">
								<img src="${item.image}"
									 alt="${item.name}"
									 width="150">
								<h4>${item.name}</h4>
								<p>
									<fmt:formatNumber value="${item.price}" type="number"/> ₫
								</p>
							</a>
						</div>
					</c:forEach>
				</div>
			</section>
		</c:if>
	</main>

	<jsp:include page="/views/jsp/footer.jsp" />
</div>

</body>
</html>
