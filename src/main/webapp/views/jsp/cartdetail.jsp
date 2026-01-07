<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cart Detail</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/views/Shared/cart-detail.css">
</head>
<body>
<div class ="page-wrapper">
<jsp:include page="/views/jsp/demo.jsp"></jsp:include>
<main class ="main-content">
<div class="cart-container">
<h2>🛒 Giỏ hàng</h2>

<c:if test="${empty cart}">
    <p>Giỏ hàng trống</p>
</c:if>

<c:if test="${not empty cart}">
<table>
<tr>
    <th>Món</th>
    <th>Giá</th>
    <th>SL</th>
    <th>Tổng</th>
</tr>

<c:forEach items="${cart}" var="i">
<tr>
    <td>${i.foodName}</td>
    <td>${i.price} đ</td>
    <td>${i.quantity}</td>
    <td>${i.totalPrice} đ</td>
</tr>
</c:forEach>
</table>

<!-- ===== CHỌN ĐƠN VỊ VẬN CHUYỂN ===== -->
<form action="CartServlet" method="get" class="ship-form">
    <h3>🚚 Đơn vị vận chuyển</h3>

    <label>
        <input type="radio" name="ship" value="save"
            <c:if test="${ship=='save'}">checked</c:if>>
        Tiết kiệm (10.000đ)
    </label><br>

    <label>
        <input type="radio" name="ship" value="normal"
            <c:if test="${ship=='normal'}">checked</c:if>>
        Tiêu chuẩn (20.000đ)
    </label><br>

    <label>
        <input type="radio" name="ship" value="fast"
            <c:if test="${ship=='fast'}">checked</c:if>>
        Nhanh (30.000đ)
    </label><br>

    <button type="submit" class="btn-ship">Cập nhật phí ship</button>
</form>
</form>

<!-- ===== TỔNG TIỀN ===== -->
<div class="summary">
    <p>Tạm tính: <b>${subTotal} đ</b></p>
    <p>Phí ship: <b>${shipFee} đ</b></p>
    <p class="total">Tổng thanh toán: <b>${total} đ</b></p>
</div>

<form action="CheckoutServlet" method="post">
    <input type="hidden" name="shipFee" value="${shipFee}">
    <input type="hidden" name="total" value="${total}">
    <button class="btn-checkout">💳 Thanh toán</button>
</form>

</c:if>
</div>
	</main>					
<jsp:include page="/views/jsp/footer.jsp"></jsp:include>
</div>
</body>
</html>