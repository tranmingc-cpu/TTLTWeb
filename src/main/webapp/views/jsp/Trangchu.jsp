<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
    
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Food Online</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/views/Shared/index.css">
</head>
<body>
<div class ="page-container">
<jsp:include page="/views/jsp/demo.jsp"></jsp:include>
<div class="nav">
	<a class="active" href="${pageContext.request.contextPath}/food">Trang Chủ</a>
	<a href="${pageContext.request.contextPath}/food">Tất Cả</a>
	<a href="#">Chicken</a>
	<a href="#">Pizza/Burger</a>
	<a href="#">Drink</a>
	<a href="#">Đồ Chay</a>
	<a href="#">Set Menu</a>
</div>
 <div class="page-content">
<div class="section">
	<div class="section-title">"${title}"</div>

	<div class="products">

		<c:if test="${empty foodlist}">
			<p>❌ Không có dữ liệu</p>
		</c:if>

	<c:forEach var="f" items="${foodlist}">
    <a class="product-link"
       href="${pageContext.request.contextPath}/food?action=detail&id=${f.id}">
       
        <div class="product-card">

            <img src="${pageContext.request.contextPath}/images/${f.image}"
                 onerror="this.src='${pageContext.request.contextPath}/images/default-food.jpg'">

            <div class="product-name">${f.name}</div>

            <div class="product-price">${f.price} VND</div>

            <div class="product-note">#${f.id}-Hasky</div>

            <div class="btn-group">
                <span class="btn-cart">THÊM VÀO GIỎ</span>
                <span class="btn-buy">MUA NGAY</span>
            </div>

        </div>
    </a>
</c:forEach>
	</div>
</div>

<c:if test="${title == 'Món ăn nổi bật'}">
    <div class="view-all">
        <a href="${pageContext.request.contextPath}/Trangchu?action=all">
            Xem tất cả
        </a>
    </div>
</c:if>

</div>
<jsp:include page="/views/jsp/footer.jsp"/>
</div>
</body>
</html>