<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lịch sử đơn hàng</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/views/Shared/orderHistory.css">
</head>
<body>
<jsp:include page="/views/jsp/demo.jsp" />

<div class="order-container">
    <h2>📦 Đơn hàng của tôi</h2>

    <c:if test="${empty orders}">
        <p class="empty">Bạn chưa có đơn hàng nào.</p>
    </c:if>

    <c:forEach var="o" items="${orders}">
        <div class="order-card">
            <div>
                <b>Mã đơn:</b>${o.orderId}
            </div>
            <div>
                <b>Ngày đặt:</b>
                <fmt:formatDate value="${o.orderDate}" pattern="dd/MM/yyyy HH:mm"/>
            </div>
            <div>
                <div class="address">
                    <b>Địa chỉ giao hàng:</b> ${o.address}
                </div>
                <b>Tổng tiền:</b>
                <fmt:formatNumber value="${o.totalAmount}" type="number"/> đ
            </div>
            <div class="status">
                <b>Trạng thái:</b> ${o.status}
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>