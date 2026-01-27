<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xác nhận đơn hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/Shared/order.css">
</head>
<body>

<jsp:include page="/views/jsp/demo.jsp"/>

<div class="checkout-container">

    <h2 class="page-title">🧾 XÁC NHẬN ĐƠN HÀNG</h2>

    <form action="${pageContext.request.contextPath}/checkout" method="post">

        <!-- ===== THÔNG TIN KHÁCH HÀNG ===== -->
        <div class="box">
            <h3>👤 Thông tin khách hàng</h3>

            <div class="form-group">
                <label>Họ và tên</label>
                <input type="text" name="fullname"
                       value="${profile.fullname}" required>
            </div>

            <div class="form-group">
                <label>Số điện thoại</label>
                <input type="tel" name="phone"
                       value="${profile.phone}" required>
            </div>

            <div class="form-group">
                <label>📍 Địa chỉ giao hàng</label>
                <input type="text" name="address"
                       value="${profile.address}" required>
            </div>
        </div>

        <!-- ===== DANH SÁCH MÓN ===== -->
        <div class="box">
            <h3>🍽️ Thông tin đơn hàng</h3>

            <table class="order-table">
                <thead>
                <tr>
                    <th>Nhà hàng</th>
                    <th>Món ăn</th>
                    <th>Số lượng</th>
                    <th>Giá</th>
                    <th>Thành tiền</th>
                </tr>
                </thead>

                <tbody>
                <c:set var="total" value="0"/>

                <c:forEach var="item" items="${cart}">
                    <tr>
                        <td>${item.food.resID}</td>
                        <td>${item.food.name}</td>
                        <td>${item.quantity}</td>
                        <td>
                            <fmt:formatNumber value="${item.food.price}" type="number"/> đ
                        </td>
                        <td>
                            <fmt:formatNumber value="${item.totalPrice}" type="number"/> đ
                        </td>
                    </tr>
                    <c:set var="total" value="${total + item.totalPrice}"/>
                </c:forEach>
                </tbody>

                <tfoot>
                <tr>
                    <td colspan="4" class="total-label">TỔNG CỘNG</td>
                    <td class="total-price">
                        <fmt:formatNumber value="${total}" type="number"/> đ
                    </td>
                </tr>
                </tfoot>
            </table>
        </div>

        <input type="hidden" name="total" value="${total}">

        <!-- ===== BUTTON ===== -->
        <div class="btn-area">
            <button type="submit" class="btn-confirm">
                ✅ Xác nhận đặt hàng
            </button>
        </div>

    </form>
</div>

<jsp:include page="/views/jsp/footer.jsp"/>

</body>
</html>
