<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thanh toán</title>

<link rel="stylesheet"
    href="${pageContext.request.contextPath}/views/Shared/checkout.css">
</head>
<body>

<!-- HEADER -->
<jsp:include page="/views/jsp/demo.jsp" />

<div class="checkout-container">

    <h2>XÁC NHẬN THANH TOÁN</h2>

    <div class="checkout-box">

        <!-- CART LIST -->
        <table class="cart-table">
            <thead>
                <tr>
                    <th>Sản phẩm</th>
                    <th>SL</th>
                    <th>Giá</th>
                    <th>Tạm tính</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${cart}">
                    <tr>
                        <td>${item.food.name}</td>
                        <td>${item.quantity}</td>
                        <td>
                            <fmt:formatNumber value="${item.food.price}"
                                groupingUsed="true"/> ₫
                        </td>
                        <td>
                            <fmt:formatNumber value="${item.totalPrice}"
                                groupingUsed="true"/> ₫
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- TOTAL -->
        <div class="summary">
            <p>
                Tạm tính:
                <b>
                    <fmt:formatNumber value="${subTotal}" groupingUsed="true"/> ₫
                </b>
            </p>

            <p>
                Phí ship:
                <b>
                    <fmt:formatNumber value="${shipFee}" groupingUsed="true"/> ₫
                </b>
            </p>

            <p class="total">
                TỔNG:
                <b style="color:red">
                    <fmt:formatNumber value="${total}" groupingUsed="true"/> ₫
                </b>
            </p>
        </div>

        <!-- FORM THANH TOÁN -->
        <form action="${pageContext.request.contextPath}/checkout"
              method="post"
              class="checkout-form">

            <!-- GỬI TOTAL -->
            <input type="hidden" name="total" value="${total}" />

            <h3>Phương thức thanh toán</h3>

            <label class="payment-option">
                <input type="radio" name="paymentMethod"
                       value="COD" checked>
                Thanh toán khi nhận hàng (COD)
            </label>

            <label class="payment-option">
                <input type="radio" name="paymentMethod"
                       value="VNPAY">
                Ví điện tử / VNPay
            </label>

            <label class="payment-option">
                <input type="radio" name="paymentMethod"
                       value="BANK">
                Chuyển khoản ngân hàng
            </label>

            <button type="submit" class="btn-order">
                ✅ XÁC NHẬN THANH TOÁN
            </button>
        </form>

    </div>
</div>

<!-- FOOTER -->
<jsp:include page="/views/jsp/footer.jsp" />

</body>
</html>
