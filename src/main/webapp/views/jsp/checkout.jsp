<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<jsp:include page="/views/jsp/demo.jsp"/>

<div class="checkout-container">

    <h2>💳 XÁC NHẬN THANH TOÁN</h2>

    <!-- DANH SÁCH SẢN PHẨM -->
    <div class="checkout-box">

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
                            <fmt:formatNumber value="${item.food.price}" groupingUsed="true"/> ₫
                        </td>
                        <td>
                            <fmt:formatNumber value="${item.totalPrice}" groupingUsed="true"/> ₫
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- TỔNG TIỀN -->
        <div class="summary">
            <div>
                <span>Tạm tính:</span>
                <b><fmt:formatNumber value="${subTotal}" groupingUsed="true"/> ₫</b>
            </div>
            <div>
                <span>Phí ship:</span>
                <b><fmt:formatNumber value="${shipFee}" groupingUsed="true"/> ₫</b>
            </div>
            <div class="total">
                <span>TỔNG:</span>
                <b><fmt:formatNumber value="${total}" groupingUsed="true"/> ₫</b>
            </div>
        </div>

        <!-- ĐỊA CHỈ -->
        <div class="address-box">
            <h4>📍 Địa chỉ giao hàng</h4>
            <p>${address}</p>
        </div>

        <!-- ACTION -->
        <form action="${pageContext.request.contextPath}/checkout"
              method="post"
              class="checkout-action">

            <a href="${pageContext.request.contextPath}/order"
               class="btn-back">← Quay lại</a>

            <button type="submit" class="btn-confirm">
                ✅ Xác nhận thanh toán
            </button>
        </form>

    </div>
</div>

<!-- FOOTER -->
<jsp:include page="/views/jsp/footer.jsp"/>

</body>
</html>
