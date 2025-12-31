<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Food Online</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/views/Shared/index.css">
</head>
<body>
<jsp:include page="/views/jsp/demo.jsp"></jsp:include>
<!-- PROMO BANNER -->
<!--  <div class="promo-banner">
    <div class="promo-left">
        <span class="promo-badge">🔥 HOT DEAL</span>
        <h2>Giảm <span>30%</span> cho Chicken</h2>
        <p>Áp dụng hôm nay • Giao nhanh trong 30 phút</p>
        <a href="category?id=1" class="promo-btn">Đặt ngay</a>
    </div>

    <div class="promo-right">
        🍗
    </div>
</div>
-->
	<div class="nav">
		<a class="active" href="#">Trang Chủ</a> 
		<a href="#">Tất Cả</a> 
		<a href ="#"> Chicken</a>
			 <a href="#">Pizza/Burger</a> 
			<a href="#">Drink</a>
			<a href="#"> Đồ Chay </a>		
			<a href="#">Set Menu</a>
	</div>

<div class="section">
    <div class="section-title">CHICKEN</div>

    <div class="products">

        <div class="product-card">
            <img src="/FoodOrderWeb/images/chicken1.jpg">
            <div class="product-name">Đùi gà chiên mật ong</div>
            <div class="product-price">35.000VND</div>
            <div class="product-note">#1205-Hasky</div>
            <div class="btn-group">
                <button class="btn-cart">THÊM VÀO GIỎ</button>
                <button class="btn-buy">MUA NGAY</button>
            </div>
        </div>

        <div class="product-card">
            <img src="/FoodOrderWeb/images/chicken2.jpg">
            <div class="product-name">Gà BBQ 1pc</div>
            <div class="product-price">69.000VND</div>
            <div class="product-note">#12345-Hasky</div>
            <div class="btn-group">
                <button class="btn-cart">THÊM VÀO GIỎ</button>
                <button class="btn-buy">MUA NGAY</button>
            </div>
        </div>

        <div class="product-card">
            <img src="/FoodOrderWeb/images/default-food.jpg">
            <div class="product-name">Cánh gà quay nướng</div>
            <div class="product-price">40.000VND</div>
            <div class="product-note">#12355-Hasky</div>
            <div class="btn-group">
                <button class="btn-cart">THÊM VÀO GIỎ</button>
                <button class="btn-buy">MUA NGAY</button>
            </div>
        </div>

        <div class="product-card">
            <img src="/FoodOrderWeb/images/default-food.jpg">
            <div class="product-name">FULL set gà nướng</div>
            <div class="product-price">240.000VND</div>
            <div class="product-note">#13345-Hasky</div>
            <div class="btn-group">
                <button class="btn-cart">THÊM VÀO GIỎ</button>
                <button class="btn-buy">MUA NGAY</button>
            </div>
        </div>

        <div class="product-card">
            <img src="/FoodOrderWeb/images/default-food.jpg">
            <div class="product-name">FULL set BBQ gà</div>
            <div class="product-price">540.000VND</div>
            <div class="product-note">#12445-Hasky</div>
            <div class="btn-group">
                <button class="btn-cart">THÊM VÀO GIỎ</button>
                <button class="btn-buy">MUA NGAY</button>
            </div>
        </div>

    </div>
</div>
			<div class="section">
    <div class="section-title">PIZZA</div>

    <div class="products">

        <div class="product-card">
            <img src="/FoodOrderWeb/images/chicken1.jpg">
            <div class="product-name">Pizza mật ong</div>
            <div class="product-price">35.000VND</div>
            <div class="product-note">#1205-Orinaga</div>
            <div class="btn-group">
                <button class="btn-cart">THÊM VÀO GIỎ</button>
                <button class="btn-buy">MUA NGAY</button>
            </div>
        </div>

        <div class="product-card">
            <img src="/FoodOrderWeb/images/chicken2.jpg">
            <div class="product-name">Pizza BBQ </div>
            <div class="product-price">69.000VND</div>
            <div class="product-note">#12345-Orinaga</div>
            <div class="btn-group">
                <button class="btn-cart">THÊM VÀO GIỎ</button>
                <button class="btn-buy">MUA NGAY</button>
            </div>
        </div>

        <div class="product-card">
            <img src="/FoodOrderWeb/images/default-food.jpg">
            <div class="product-name">Pizza quay nướng</div>
            <div class="product-price">40.000VND</div>
            <div class="product-note">#12355-Orinaga</div>
            <div class="btn-group">
                <button class="btn-cart">THÊM VÀO GIỎ</button>
                <button class="btn-buy">MUA NGAY</button>
            </div>
        </div>

        <div class="product-card">
            <img src="/FoodOrderWeb/images/default-food.jpg">
            <div class="product-name">FULL set Pizza</div>
            <div class="product-price">240.000VND</div>
            <div class="product-note">#13345-Orinaga</div>
            <div class="btn-group">
                <button class="btn-cart">THÊM VÀO GIỎ</button>
                <button class="btn-buy">MUA NGAY</button>
            </div>
        </div>

        <div class="product-card">
            <img src="/FoodOrderWeb/images/default-food.jpg">
            <div class="product-name">FULL set BBQ Pizza</div>
            <div class="product-price">540.000VND</div>
            <div class="product-note">#12445-Orinaga</div>
            <div class="btn-group">
                <button class="btn-cart">THÊM VÀO GIỎ</button>
                <button class="btn-buy">MUA NGAY</button>
            </div>
        </div>

    </div>
</div>
			
						<div class="view-all">
							<a href="#">Xem tất cả</a>
						</div>
						
	<jsp:include page="/views/jsp/footer.jsp"></jsp:include>					
			
</body>
</html>