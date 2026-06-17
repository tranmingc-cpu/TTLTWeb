<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lịch sử đơn hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/Shared/orderHistory.css">

</head>
<body>
<jsp:include page="/views/jsp/demo.jsp" />
<div class="order-container">
    <h2>📦 Đơn hàng của tôi</h2>

    <c:if test="${empty orders}">
        <p class="empty">Bạn chưa có đơn hàng nào.</p>
    </c:if>

    <c:forEach var="o" items="${orders}">
        <%-- CHỈ HIỂN THỊ KHI TRẠNG THÁI KHÁC 'CANCELLED' --%>
        <c:if test="${o.status != 'CANCELLED'}">
            <div class="order-card">
                <div>
                    <b>Mã đơn:</b> ${o.orderId}
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
                    <b>Trạng thái:</b>
                    <c:choose>
                        <c:when test="${o.status == 'PENDING'}">
                            <span class="status-pending">Chờ xác nhận</span>
                        </c:when>
                        <c:when test="${o.status == 'READY_TO_PICK'}">
                            <span class="status-ready">Đã xác nhận</span>
                        </c:when>
                        <c:when test="${o.status == 'PICKING'}">
                            <span class="status-picking">Shipper đang lấy hàng</span>
                        </c:when>
                        <c:when test="${o.status == 'DELIVERING'}">
                            <span class="status-delivering">Đang giao hàng</span>
                        </c:when>
                        <c:when test="${o.status == 'DELIVERED'}">
                            <span class="status-delivered">Đã giao hàng</span>
                        </c:when>
                        <c:otherwise>
                            ${o.status}
                        </c:otherwise>
                    </c:choose>
                </div>

                <c:if test="${o.status == 'PENDING' || o.status == 'READY_TO_PICK'}">
                    <form action="${pageContext.request.contextPath}/cancelOrder" method="post" class="cancel-order-form">
                        <input type="hidden" name="orderId" value="${o.orderId}">
                        <button type="button" class="btn-cancel btn-cancel-trigger" data-orderid="${o.orderId}">
                            ❌ Hủy đơn hàng
                        </button>
                    </form>
                </c:if>
            </div>
        </c:if>
    </c:forEach>
</div>

<div id="cancelConfirmModal" class="custom-modal">
    <div class="modal-content">
        <div class="modal-icon">⚠️</div>
        <h3>Xác nhận hủy đơn</h3>
        <p>Bạn có chắc chắn muốn hủy đơn hàng <b id="modalOrderIdText">#0</b> không? Hành động này không thể hoàn tác.</p>
        <div class="modal-actions">
            <button type="button" class="modal-btn modal-btn-close" id="btnModalClose">Quay lại</button>
            <button type="button" class="modal-btn modal-btn-confirm" id="btnModalConfirm">Đồng ý hủy</button>
        </div>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        const modal = document.getElementById('cancelConfirmModal');
        const modalText = document.getElementById('modalOrderIdText');
        const btnClose = document.getElementById('btnModalClose');
        const btnConfirm = document.getElementById('btnModalConfirm');
        const cancelButtons = document.querySelectorAll('.btn-cancel-trigger');

        let currentForm = null;

        cancelButtons.forEach(button => {
            button.addEventListener('click', function() {
                const orderId = this.getAttribute('data-orderid');
                currentForm = this.closest('.cancel-order-form');

                modalText.textContent = "#" + orderId;
                modal.classList.add('show');
            });
        });

        btnClose.addEventListener('click', function() {
            modal.classList.remove('show');
            currentForm = null;
        });

        window.addEventListener('click', function(event) {
            if (event.target === modal) {
                modal.classList.remove('show');
                currentForm = null;
            }
        });

        btnConfirm.addEventListener('click', function() {
            if (currentForm) {
                currentForm.submit();
            }
        });
    });
</script>
</body>
</html>