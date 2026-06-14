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
<jsp:include page="/views/jsp/demo.jsp" />
<div class="checkout-container">

    <h2>💳 XÁC NHẬN THANH TOÁN</h2>

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

        <div class="checkout-summary">

            <c:if test="${coupon != null}">
                <div class="coupon-info">
                    🎉 Mã giảm giá:
                    <b>${coupon.code}</b>
                </div>
            </c:if>

            <p>
                <span>Giá Gốc </span>
                <strong>
                    <fmt:formatNumber value="${subTotal}"
                                      type="number"/> đ
                </strong>
            </p>

            <p>
                <span>Phí ship</span>
                <strong>
                    <fmt:formatNumber value="${shipFee}"
                                      type="number"/> đ
                </strong>
            </p>

            <c:if test="${discount > 0}">
                <p class="discount-row">
                    <span>Giảm giá</span>
                    <strong>
                        -
                        <fmt:formatNumber value="${discount}"
                                          type="number"/> đ
                    </strong>
                </p>
            </c:if>

            <p class="total-row">
                <span>Tổng thanh toán</span>
                <strong>
                    <fmt:formatNumber value="${total}"
                                      type="number"/> đ
                </strong>
            </p>
        </div>

        <div class="address-box">
            <h4>📍 Địa chỉ giao hàng</h4>
            <p>${address}</p>
        </div>

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
<jsp:include page="/views/jsp/footer.jsp" />
</body>
</html>
