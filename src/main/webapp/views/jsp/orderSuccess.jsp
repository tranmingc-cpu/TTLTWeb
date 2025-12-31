<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thông báo đặt hàng </title>
    <link rel="stylesheet" href="/FoodOrderWeb/views/Shared/orderSuccess.css">
    <jsp:include page="/views/jsp/demo.jsp"></jsp:include>
    
</head>
<body>
<div class="success-box">
    <h2>🎉 Đặt hàng thành công!</h2>

    <p>Cảm ơn bạn đã đặt đồ ăn tại hệ thống.</p>
    <p>Đơn hàng của bạn đang được xử lý.</p>

    <div class="order-info">
        <p>Mã đơn hàng: <b>${sessionScope.lastOrderId}</b></p>
        <p>Tổng tiền: <b>${sessionScope.lastTotal} VNĐ</b></p>
    </div>

    <a href="home">Tiếp tục mua hàng</a><br>
    <a href="order-history">Xem đơn hàng của tôi</a>
</div>
</body>
</html>