<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Giỏ hàng</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/views/Shared/cart.css">
          <jsp:include page="/views/jsp/demo.jsp"></jsp:include>
</head>
<body>

<!-- ===== CART ===== -->
<div class="cart-container">
    <h2>🛒 Giỏ hàng của bạn</h2>

    <table class="cart-table">
        <thead>
        <tr>
            <th>Món ăn</th>
            <th>Đơn giá</th>
            <th>Số lượng</th>
            <th>Số tiền</th>
            <th>Thao tác</th>
        </tr>
        </thead>

        <tbody>
        <c:if test="${empty sessionScope.cart}">
            <tr>
                <td colspan="5" style="text-align:center;">Giỏ hàng trống</td>
            </tr>
        </c:if>

        <c:forEach items="${sessionScope.cart}" var="item">
            <tr>
                <td class="food-info">
                    <img src="${pageContext.request.contextPath}/images/${item.food.image}">
                    ${item.food.name}
                </td>

                <td>
                    <fmt:formatNumber value="${item.food.price}" type="number"/> đ
                </td>

                <td>
                    <form action="updateCart" method="post">
                        <input type="hidden" name="id" value="${item.food.id}">
                        <button name="action" value="minus">-</button>
                        ${item.quantity}
                        <button name="action" value="plus">+</button>
                    </form>
                </td>

                <td class="price">
                    <fmt:formatNumber
                        value="${item.food.price * item.quantity}"
                        type="number"/> đ
                </td>

                <td>
                    <a href="removeFromCart?id=${item.food.id}" class="remove">
                        Xóa
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="cart-total">
        <span>Tổng tiền:</span>
        <strong>
            <fmt:formatNumber value="${sessionScope.total}" type="number"/> đ
        </strong>
        <a href="checkout.jsp" class="btn-buy">Mua hàng</a>
    </div>
</div>

</body>
</html>
