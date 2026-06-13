<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết đơn hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/orderDetail.css">
</head>
<body>

<jsp:include page="/views/jsp/demo.jsp"/>

<div class="admin-container">

    <jsp:include page="/views/admin/sidebar.jsp"/>
    <main class="admin-content">
        <div class="page-header">
            <h2>Chi tiết đơn hàng</h2>

            <a href="${pageContext.request.contextPath}/admin/order"
               class="btn-back">
                ← Quay lại
            </a>
        </div>

        <div class="detail-card">

            <h3>📦 Thông tin đơn hàng</h3>

            <div class="info-row">
                <div class="info-label">Mã đơn hàng:</div>
                <div>${order.orderId}</div>
            </div>

            <div class="info-row">
                <div class="info-label">Mã khách hàng:</div>
                <div>${order.accountId}</div>
            </div>

            <div class="info-row">
                <div class="info-label">Mã nhà hàng:</div>
                <div>${order.resId}</div>
            </div>

            <div class="info-row">
                <div class="info-label">Địa chỉ giao:</div>
                <div>${order.address}</div>
            </div>

            <div class="info-row">
                <div class="info-label">Ngày đặt:</div>
                <div>${order.orderDate}</div>
            </div>

            <div class="info-row">
                <div class="info-label">Tổng tiền:</div>
                <div class="total-money">
                    <fmt:formatNumber value="${order.totalAmount}" type="number" maxFractionDigits="0" /> VNĐ
                </div>
            </div>

            <div class="info-row">
                <div class="info-label">Trạng thái:</div>

                <div>
                    <c:choose>
                        <c:when test="${order.status == 'Đang xử lý'}">
                    <span class="status status-pending">
                            ${order.status}
                    </span>
                        </c:when>

                        <c:when test="${order.status == 'Hoàn thành'}">
                    <span class="status status-done">
                            ${order.status}
                    </span>
                        </c:when>

                        <c:otherwise>
                    <span class="status status-cancel">
                            ${order.status}
                    </span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <hr class="detail-divider">

            <h3>👤 Thông tin khách hàng</h3>

            <div class="info-row">
                <div class="info-label">Mã tài khoản:</div>
                <div>${order.accountId}</div>
            </div>

            <div class="info-row">
                <div class="info-label">Tên khách hàng:</div>
                <div>${customerName}</div>
            </div>

            <div class="info-row">
                <div class="info-label">Số điện thoại:</div>
                <div>${customerPhone}</div>
            </div>

            <div class="info-row">
                <div class="info-label">Email:</div>
                <div>${customerEmail}</div>
            </div>

        </div>

        <c:if test="${not empty detailList}">

            <div class="detail-card">

                <h3>🍔 Danh sách món ăn</h3>

                <table class="order-table">

                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Tên món</th>
                        <th>Số lượng</th>
                        <th>Đơn giá</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="item"
                               items="${detailList}"
                               varStatus="loop">

                        <tr>
                            <td>${loop.index + 1}</td>
                            <td>${item.foodName}</td>
                            <td>${item.quantity}</td>
                            <td>${item.price} VNĐ</td>
                        </tr>

                    </c:forEach>

                    </tbody>

                </table>

            </div>

        </c:if>

    </main>

</div>

</body>
</html>