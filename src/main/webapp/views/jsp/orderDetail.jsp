<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết đơn hàng</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/views/Shared/orderDetail.css">
</head>
<body>
<div class="order-detail-page">

    <h2>Chi tiết đơn hàng #${orderId}</h2>

    <table class="order-detail-table">
        <thead>
        <tr>
            <th>Món ăn</th>
            <th>Số lượng</th>
            <th>Đơn giá</th>
            <th>Tạm tính</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="d" items="${details}">
            <tr>
                <td>${d.foodName}</td>
                <td>${d.quantity}</td>
                <td>
                    <fmt:formatNumber value="${d.price}" type="number"/> đ
                </td>
                <td>
                    <fmt:formatNumber value="${d.total}" type="number"/> đ
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a class="btn-back"
       href="${pageContext.request.contextPath}/orderHistory">
        ← Quay lại lịch sử đơn hàng
    </a>
</div>
</body>
</html>