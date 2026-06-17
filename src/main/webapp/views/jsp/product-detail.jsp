<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title><c:choose><c:when test="${not empty food}">${food.name}</c:when><c:otherwise>Chi tiết sản phẩm</c:otherwise></c:choose></title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/views/Shared/product-detail.css">
</head>
<body>
<div class="page-container">
	<jsp:include page="/views/jsp/demo.jsp" />
	<main class="content">
		<c:if test="${not empty error}">
			<h2 style="color: red; text-align: center; margin: 40px 0;">${error}</h2>
		</c:if>
		<c:if test="${not empty food}">
			<div class="breadcrumb">
				<c:if test="${not empty header.referer}">
					<a href="${header.referer}" class="back-link">← Quay lại</a>
					<span class="separator">/</span>
				</c:if>
				<a href="${pageContext.request.contextPath}/Trangchu">Trang chủ</a>
				<span class="separator">/</span>
				<span class="current">${food.name}</span>
			</div>
			<div class="food-detail">
				<div class="food-image">
					<img src="${food.image}" onerror="this.src='${pageContext.request.contextPath}/images/default-food.jpg'" alt="${food.name}">
				</div>
				<div class="food-info">
					<h1>${food.name}</h1>
					<p class="status">
						Tình trạng:
						<c:choose>
							<c:when test="${food.quantity > 0}"><span class="in-stock">Còn hàng</span></c:when>
							<c:otherwise><span class="out-stock">Hết hàng</span></c:otherwise>
						</c:choose>
					</p>
					<p class="stock">Số lượng còn: <b>${food.quantity}</b></p>
					<div class="product-price">
						<span class="new-price"><fmt:formatNumber value="${newPrice}" type="number"/> đ</span>
						<c:if test="${food.discount > 0}">
							<span class="old-price"><fmt:formatNumber value="${food.price}" type="number"/> đ</span>
							<span class="discount-badge">-${food.discount}%</span>
						</c:if>
					</div>
					<div class="description">
						<h3>Mô tả món ăn</h3>
						<p>${food.description}</p>
					</div>
					<div class="promo-box">
						🔥 <b>Siêu Ưu Đãi</b>
						<ul>
							<li>Nhập mã <b>EGANY</b> giảm 15%</li>
							<li>Số lượng có hạn</li>
						</ul>
					</div>
					<form action="${pageContext.request.contextPath}/cart" method="post">
						<input type="hidden" name="foodId" value="${food.id}">
						<div class="quantity">
							<label for="quantityInput">Số lượng:</label>
							<input type="number" id="quantityInput" name="quantity" value="1" min="1" max="${food.quantity}">
						</div>
						<c:choose>
							<c:when test="${food.quantity > 0}">
								<div class="action-buttons">
									<button type="submit" name="action" value="add" class="btn-cart">THÊM VÀO GIỎ</button>
									<button type="submit" name="action" value="buy" class="btn-buy">MUA NGAY</button>
								</div>
							</c:when>
							<c:otherwise>
								<button class="btn-cart" disabled>HẾT HÀNG</button>
							</c:otherwise>
						</c:choose>
					</form>
				</div>
			</div>
		</c:if>

		<section class="review-section">
			<div class="review-form">
				<h3>✍️ Viết đánh giá của bạn</h3>
				<c:choose>
					<c:when test="${not empty account}">
						<form action="${pageContext.request.contextPath}/add-review" method="post" enctype="multipart/form-data">
							<input type="hidden" name="foodId" value="${food.id}">
							<div class="rating-select">
								<label>Đánh giá số sao:</label>
								<select name="rating">
									<option value="5">⭐⭐⭐⭐⭐ (5/5)</option>
									<option value="4">⭐⭐⭐⭐ (4/5)</option>
									<option value="3">⭐⭐⭐ (3/5)</option>
									<option value="2">⭐⭐ (2/5)</option>
									<option value="1">⭐ (1/5)</option>
								</select>
							</div>
							<textarea name="comment" placeholder="Món ăn này có ngon không? Hãy chia sẻ cảm nhận của bạn nhé..." required></textarea>
							<div style="margin-bottom: 15px;">
								<label style="font-weight: 600; display: block; margin-bottom: 5px;">📸 Đính kèm ảnh thực tế:</label>
								<input type="file" name="reviewImage" accept="image/*">
							</div>
							<button type="submit" class="btn-submit-review">Gửi đánh giá</button>
						</form>
					</c:when>
					<c:otherwise>
						<p>Vui lòng <a href="${pageContext.request.contextPath}/login" style="color: #ff5722; font-weight: bold; text-decoration: none;">Đăng nhập</a> để gửi bình luận đánh giá.</p>
					</c:otherwise>
				</c:choose>
			</div>
			<hr style="margin: 30px 0; border: 0; border-top: 1px solid #e5e7eb;">
			<div class="review-list">
				<h3>💬 Đánh giá từ khách hàng (${fn:length(reviews)})</h3>
				<c:choose>
					<c:when test="${empty reviews}">
						<p style="color: #666; font-style: italic;">Chưa có đánh giá nào cho món ăn này. Hãy là người đầu tiên review!</p>
					</c:when>
					<c:otherwise>
						<c:forEach items="${reviews}" var="r">
							<div class="review-item">
								<div class="review-header">
									<span class="review-user">👤 ${r.username}</span>
									<span class="review-stars">
                               <c:forEach begin="1" end="${r.rating}">★</c:forEach><c:forEach begin="${r.rating + 1}" end="5">☆</c:forEach>
                            </span>
								</div>
								<p class="review-comment">${r.comment}</p>
								<c:if test="${not empty r.imageUrl}">
									<div class="review-attached-image">
										<img src="${pageContext.request.contextPath}/${r.imageUrl}" alt="Ảnh review thực tế">
									</div>
								</c:if>
								<span class="review-date"><fmt:formatDate value="${r.createdAt}" pattern="dd/MM/yyyy HH:mm"/></span>
							</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
		</section>

		<c:if test="${not empty restaurantFoods}">
			<section class="related-products unique-restaurant-section">
				<h2>Món ăn cùng nhà hàng</h2>
				<div class="related-list">
					<c:forEach items="${restaurantFoods}" var="item">
						<div class="related-item">
							<a href="${pageContext.request.contextPath}/product-detail?id=${item.id}">
								<img src="${item.image}" alt="${item.name}" width="150">
								<h4>${item.name}</h4>
								<p><fmt:formatNumber value="${item.price}" type="number"/> ₫</p>
							</a>
						</div>
					</c:forEach>
				</div>

				<c:if test="${resTotalPages > 1}">
					<div class="pagination">
						<c:if test="${resCurrentPage > 1}">
							<a href="${pageContext.request.contextPath}/product-detail?id=${food.id}&resPage=${resCurrentPage-1}">←</a>
						</c:if>
						<c:forEach begin="1" end="${resTotalPages}" var="i">
							<a href="${pageContext.request.contextPath}/product-detail?id=${food.id}&resPage=${i}" class="${i==resCurrentPage?'active':''}">${i}</a>
						</c:forEach>
						<c:if test="${resCurrentPage < resTotalPages}">
							<a href="${pageContext.request.contextPath}/product-detail?id=${food.id}&resPage=${resCurrentPage+1}">→</a>
						</c:if>
					</div>
				</c:if>
			</section>
		</c:if>

		<c:if test="${not empty relatedFoods}">
			<section class="related-products">
				<h2>Sản phẩm liên quan</h2>
				<div class="related-list">
					<c:forEach items="${relatedFoods}" var="item">
						<div class="related-item">
							<a href="${pageContext.request.contextPath}/product-detail?id=${item.id}">
								<img src="${item.image}" alt="${item.name}" width="150">
								<h4>${item.name}</h4>
								<p><fmt:formatNumber value="${item.price}" type="number"/> ₫</p>
							</a>
						</div>
					</c:forEach>
				</div>
			</section>
			<c:if test="${totalPages > 1}">
				<div class="pagination">
					<c:if test="${currentPage > 1}">
						<a href="${pageContext.request.contextPath}/product-detail?id=${food.id}&page=${currentPage-1}">←</a>
					</c:if>
					<c:forEach begin="1" end="${totalPages}" var="i">
						<a href="${pageContext.request.contextPath}/product-detail?id=${food.id}&page=${i}" class="${i==currentPage?'active':''}">${i}</a>
					</c:forEach>
					<c:if test="${currentPage < totalPages}">
						<a href="${pageContext.request.contextPath}/product-detail?id=${food.id}&page=${currentPage+1}">→</a>
					</c:if>
				</div>
			</c:if>
		</c:if>
	</main>
	<jsp:include page="/views/jsp/footer.jsp" />
</div>
</body>
</html>