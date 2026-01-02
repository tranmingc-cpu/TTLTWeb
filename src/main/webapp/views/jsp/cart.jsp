<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Giỏ hàng</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/views/Shared/cart.css">
    <jsp:include page="/views/jsp/demo.jsp"/>
</head>
<body>

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
        <c:if test="${empty cart}">
            <tr>
                <td colspan="5" style="text-align:center;">Giỏ hàng trống</td>
            </tr>
        </c:if>

        <c:forEach items="${cart}" var="item">
            <tr>
                <td class="food-info">
                    <img src="${pageContext.request.contextPath}/images/${item.food.image}">
                    ${item.food.name}
                </td>

                <td>
                    <fmt:formatNumber value="${item.food.price}" type="number"/> đ
                </td>

                <td>
                    <form action="${pageContext.request.contextPath}/CartServlet" method="get">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="foodId" value="${item.id}">
                        <input type="number" name="quantity" value="${item.quantity}" min="1">
                        <button>Cập nhật</button>
                    </form>
                </td>

                <td class="price">
                    <fmt:formatNumber value="${item.totalPrice}" type="number"/> đ
                </td>

                <td>
                    <a href="${pageContext.request.contextPath}/CartServlet?action=remove&cartDetailId=${item.id}"
                       class="remove">
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
            <fmt:formatNumber value="${total}" type="number"/> đ
        </strong>

        <a href="${pageContext.request.contextPath}/CheckoutServlet" class="btn-buy">
            Mua hàng
        </a>
    </div>
</div>

</body>
</html>
