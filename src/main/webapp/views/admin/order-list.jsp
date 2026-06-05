<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Tổng số đơn hàng</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/views/admin/order-list.css">
</head>
<body>
<jsp:include page="/views/jsp/demo.jsp"/>

<div class="admin-container">
    <div class="admin-header">
        <h1>ADMIN PANEL</h1>
    </div>

    <jsp:include page="/views/admin/sidebar.jsp"/>

    <main class="admin-content">

        <div class="page-header">
            <h2>Tổng số đơn hàng</h2>
            <a href="${pageContext.request.contextPath}/admin/dashboard"
               class="btn-back">← Quay lại</a>
        </div>

        <div class="card single">
            <p>📦 Tổng số order hiện tại</p>
            <p style="font-size:36px;font-weight:bold;">
                ${totalOrders}
            </p>
        </div>

        <h3 class="section-title">Chi tiết tất cả đơn hàng</h3>

        <table class="order-table">
            <thead>
            <tr>
                <th>#</th>
                <th>Mã đơn</th>
                <th>Khách hàng</th>
                <th>Nhà hàng </th>
                <th>Địa chỉ</th>
                <th>Ngày đặt</th>
                <th>Tổng tiền</th>
                <th>Trạng thái</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orderList}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td>${order.orderId}</td>
                    <td>${order.accountId}</td>
                    <td>${order.resId}</td>
                    <td>${order.address}</td>
                    <td>${order.orderDate}</td>
                    <td>${order.totalAmount} VNĐ</td>
                    <td>
                        <c:choose>
                            <c:when test="${order.status == 'Đang xử lý'}">
                                <span class="status status-pending">${order.status}</span>
                            </c:when>
                            <c:when test="${order.status == 'Hoàn thành'}">
                                <span class="status status-done">${order.status}</span>
                            </c:when>
                            <c:otherwise>
                                <span class="status status-canceled">${order.status}</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/order-detail?id=${order.orderId}"
                           class="btn-view">🔍 Xem</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </main>
</div>

</body>
</html>