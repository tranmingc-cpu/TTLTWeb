<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết đơn hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/Shared/order-detail.css">
    
</head>
<body>
<jsp:include page="/views/jsp/demo.jsp"></jsp:include>
<<div class="cart-container">

    <h2 class="cart-title">🛒 Giỏ hàng của bạn</h2>

    <!-- CART TABLE -->
    <div class="cart-table">
        <div class="cart-header">
            <span>Sản phẩm</span>
            <span>Giá</span>
            <span>Số lượng</span>
            <span>Tổng</span>
            <span>Xóa</span>
        </div>

        <!-- ITEM -->
        <div class="cart-item">
            <div class="cart-product">
                <img src="images/pizza.jpg">
                <div>
                    <h4>Pizza Phô Mai</h4>
                    <p>Size lớn</p>
                </div>
            </div>

            <span class="price">120.000đ</span>

            <div class="quantity">
                <button>-</button>
                <input type="text" value="1">
                <button>+</button>
            </div>

            <span class="total">120.000đ</span>

            <button class="remove">✖</button>
        </div>

    </div>
<div class="shipping">
    <h4>🚚 Đơn vị vận chuyển</h4>

    <label class="shipping-option">
        <input type="radio" name="ship" checked>
        <span>
            <strong>Giao hàng nhanh</strong><br>
            <small>2 – 4 giờ</small>
        </span>
        <span class="ship-price">20.000đ</span>
    </label>

    <label class="shipping-option">
        <input type="radio" name="ship">
        <span>
            <strong>Giao hàng tiết kiệm</strong><br>
            <small>Trong ngày</small>
        </span>
        <span class="ship-price">10.000đ</span>
    </label>

    <label class="shipping-option">
        <input type="radio" name="ship">
        <span>
            <strong>Giao hàng hỏa tốc</strong><br>
            <small>30 – 60 phút</small>
        </span>
        <span class="ship-price">30.000đ</span>
    </label>
</div>

    <!-- SUMMARY -->
    <div class="cart-summary">
        <div class="summary-row">
            <span>Tạm tính</span>
            <span>120.000đ</span>
        </div>
        <div class="summary-row">
            <span>Phí giao hàng</span>
            <span>20.000đ</span>
        </div>
        <div class="summary-row total">
            <span>Tổng cộng</span>
            <span>140.000đ</span>
        </div>

        <button class="checkout-btn">Thanh toán</button>
        <a href="index.jsp" class="back-shop">← Tiếp tục mua hàng</a>
    </div>

</div>

	<jsp:include page="/views/jsp/footer.jsp"></jsp:include>	
</body>
</html>
    