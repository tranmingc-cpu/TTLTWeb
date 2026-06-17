<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý mã giảm giá</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/coupon.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/Shared/notification.css">

</head>
<body>

<jsp:include page="/views/jsp/demo.jsp"/>

<div class="admin-container">

    <jsp:include page="/views/admin/sidebar.jsp"/>

    <div class="main-content">
        <c:if test="${not empty sessionScope.success}">
            <div class="alert-success">
                    ${sessionScope.success}
            </div>
            <c:remove var="success" scope="session"/>
        </c:if>

        <div class="page-header">
            <h2>🎫 Quản lý mã giảm giá (Coupon)</h2>
            <a class="btn-add" href="${pageContext.request.contextPath}/views/admin/add-coupon.jsp" style="text-decoration: none; padding: 10px 16px; display: inline-block;">
                ➕ Thêm mã mới
            </a>
        </div>

        <div class="table-wrapper">
            <table class="food-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Mã số</th>
                    <th>Loại giảm</th>
                    <th>Giá trị giảm</th>
                    <th>Đơn tối thiểu</th>
                    <th>Giảm tối đa</th>
                    <th>Số lượng</th>
                    <th>Đã dùng</th>
                    <th>Thời gian áp dụng</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty coupons}">
                        <c:forEach var="c" items="${coupons}">
                            <tr>
                                <td>#${c.id}</td>
                                <td><strong>${c.code}</strong></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${c.discountType == 'fixed'}">Cố định</c:when>
                                        <c:otherwise>Phần trăm</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <fmt:formatNumber value="${c.discountValue}" type="number"/>${c.discountType == 'fixed' ? ' ₫' : ' %'}
                                </td>
                                <td>
                                    <fmt:formatNumber value="${c.minOrderValue}" type="number"/> ₫
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty c.maxDiscountAmount}">
                                            <fmt:formatNumber value="${c.maxDiscountAmount}" type="number"/> ₫
                                        </c:when>
                                        <c:otherwise>--</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${c.quantity}</td>
                                <td>${c.usedCount}</td>
                                <td style="font-size: 12px; max-width: 150px;">
                                    Từ: <fmt:formatDate value="${c.startDate}" pattern="dd/MM/yyyy HH:mm"/><br>
                                    Đến: <fmt:formatDate value="${c.endDate}" pattern="dd/MM/yyyy HH:mm"/>
                                <td>
                                    <form action="${pageContext.request.contextPath}/admin/coupon"
                                          method="post"
                                          style="display:inline;">

                                        <input type="hidden"
                                               name="action"
                                               value="toggleStatus">

                                        <input type="hidden"
                                               name="id"
                                               value="${c.id}">

                                        <label class="switch">
                                            <input type="checkbox"
                                                   onchange="this.form.submit()"
                                                   <c:if test="${c.status}">checked</c:if>>
                                            <span class="slider round"></span>
                                        </label>

                                    </form>
                                    <br>
                                    <span class="${c.status ? 'status-on' : 'status-off'}">
                                            ${c.status ? 'Bật' : 'Tắt'}
                                    </span>
                                </td>

                                <td>
                                    <a class="action-btn btn-delete btn-delete-trigger"
                                       href="javascript:void(0);"
                                       data-url="${pageContext.request.contextPath}/admin/coupon?action=delete&id=${c.id}"
                                       data-message="Bạn có chắc chắn muốn xóa mã giảm giá này không?">
                                        🗑️ Xóa
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="11" class="empty-text">
                                🚫 Chưa có mã giảm giá nào </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="/views/jsp/notification.jsp"/>
</body>
</html>