	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Food Online</title>
<link rel="stylesheet" href="/FoodOrderWeb/views/Shared/chicken.css">
</head>
<body>
 <a class="logo" href="#">
        <div class="food-logo spoon">
            <div class="spoon-head"></div>
            <div class="spoon-handle"></div>
        </div>
        <span>FOOD ONLINE</span>
    </a>

    <!-- SEARCH -->
    <div class="search-box">
        <input type="text" placeholder="Tìm đồ ăn, đồ uống">
        <button>🔍</button>
    </div>

    <!-- RIGHT ICONS -->
    <div class="header-icons">

        <!-- CART -->
        <div class="header-cart">
            🛒
            <span class="cart-count">2</span>
        </div>

        <!-- USER -->
        <div class="header-user">
            <img src="/FoodOrderWeb/images/user.png" alt="User">
        </div>

    </div>

	<div class="nav">
		<a href="#">Trang Chủ</a> 
		<a href="#">Tất Cả</a> 
		<a class="active" href ="#"> Chicken</a>
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

	

</body>
</html>