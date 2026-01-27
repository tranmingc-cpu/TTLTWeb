<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Giỏ hàng</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/views/Shared/cart.css">
</head>

<body class="d-flex flex-column min-vh-100">

	<!-- HEADER -->
	<jsp:include page="/views/jsp/demo.jsp"/>

	<div class="cart-container">

		<button onclick="history.back()">← Quay lại</button>

		<h2>🛒 Giỏ hàng của bạn</h2>

		<table class="cart-table">
			<thead>
				<tr>
					<th>Món ăn</th>
					<th>Đơn giá</th>
					<th>Số lượng</th>
					<th>Số tiền</th>
					<th>Thao tác</th>
				</tr>
			</thead>

			<tbody>

				<!-- GIỎ HÀNG TRỐNG -->
				<c:if test="${empty cart}">
					<tr>
						<td colspan="5" style="text-align: center;">Giỏ hàng trống</td>
					</tr>
				</c:if>

				<!-- DANH SÁCH SẢN PHẨM -->
				<c:forEach items="${cart}" var="item">
					<tr>

						<!-- THÔNG TIN MÓN -->
						<td class="food-info"><img
							src="${pageContext.request.contextPath}/images/${item.food.image}"
							onerror="this.src='${pageContext.request.contextPath}/images/default-food.jpg'"
							alt="${item.food.name}"> ${item.food.name}</td>

						<!-- ĐƠN GIÁ -->
						<td><fmt:formatNumber value="${item.food.price}"
								type="number" /> đ</td>
						<!-- UPDATE SỐ LƯỢNG -->
						<td>
							<form action="${pageContext.request.contextPath}/cart"
								method="post">
								<input type="hidden" name="action" value="update" />

								<!-- PK của CARTDETAIL -->
								<input type="hidden" name="detailId" value="${item.detailId}" />

								<input type="number" name="quantity" value="${item.quantity}"
									min="1" class="qty-input" onchange="this.form.submit()" />
							</form>

						</td>


						<!-- THÀNH TIỀN -->
						<td class="price"><fmt:formatNumber
								value="${item.totalPrice}" type="number" /> đ</td>

						<!-- REMOVE ITEM -->
						<td>
							<form action="${pageContext.request.contextPath}/cart"
								method="post"
								onsubmit="return confirm('Xóa món này khỏi giỏ hàng?')">

								<input type="hidden" name="action" value="delete" /> <input
									type="hidden" name="detailId" value="${item.detailId}" />

								<button type="submit" class="delete-btn">Xóa</button>
							</form>

						</td>

					</tr>
				</c:forEach>

			</tbody>

		</table>

		<!-- TỔNG TIỀN -->
		<div class="cart-total">
			<div>
				<p>
					Tạm tính: <strong> <fmt:formatNumber value="${subTotal}"
							type="number" /> đ
					</strong>
				</p>
				<p>
					Phí ship: <strong> <fmt:formatNumber value="${shipFee}"
							type="number" /> đ
					</strong>
				</p>
			</div>

			<div class="cart-final">
				<span>Tổng tiền:</span> <strong> <fmt:formatNumber
						value="${total}" type="number" /> đ
				</strong> 
				<a href="${pageContext.request.contextPath}/order" class="btn-buy">
    Mua hàng
</a>


			</div>
		</div>
	</div>

	<!-- THÔNG TIN NHÀ HÀNG -->
	<c:if test="${restaurant != null}">
		<div class="restaurant-section">
			<h3>🍽 Nhà hàng đã nấu món ăn</h3>

			<div class="restaurant-box">
				<div class="restaurant-info">
					<h4>${restaurant.name}</h4>
					<p>📍 ${restaurant.address}</p>
					<p>📞 ${restaurant.phone}</p>
					<p>⭐ ${restaurant.rating}</p>
					<p class="desc">${restaurant.description}</p>
				</div>
			</div>

			<!-- GỢI Ý MÓN KHÁC -->
			<h4 class="suggest-title">🥘 Món khác của nhà hàng</h4>

			<div class="food-suggest-list">
				<c:forEach items="${restaurantFoods}" var="f">
					<div class="food-card">
						<img src="${pageContext.request.contextPath}/images/${f.image}"
							onerror="this.src='${pageContext.request.contextPath}/images/default-food.jpg'">

						<p class="food-name">${f.name}</p>

						<span class="food-price"> <fmt:formatNumber
								value="${f.price}" type="number" /> đ
						</span>

						<!-- ADD -->
						<a
							href="${pageContext.request.contextPath}/cart?action=add&foodId=${f.id}&quantity=1"
							class="btn-add"> + Thêm </a>
					</div>
				</c:forEach>
			</div>
		</div>
		<!-- FOOTER -->
	<jsp:include page="/views/jsp/footer.jsp" />
	</c:if>

</body>
</html>
