<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thông báo đặt hàng</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/views/Shared/orderSuccess.css">


</head>

<body>
<jsp:include page="/views/jsp/demo.jsp" />
	<!-- Nếu không có order thì về trang chủ -->
	<c:if test="${empty orders}">
		<c:redirect url="Trangchu" />
	</c:if>

	<div class="success-box">
		<h2>🎉 Đặt hàng thành công!</h2>

		<p>Cảm ơn bạn đã đặt đồ ăn tại hệ thống.</p>
		<p>Đơn hàng của bạn đang được xử lý.</p>

		<hr>

		<!-- HIỂN THỊ DANH SÁCH ORDER -->
<!-- HIỂN THỊ DANH SÁCH ORDER -->
<c:forEach var="o" items="${orders}">
    <div class="order-info">
        <p>Mã đơn hàng: <b>#${o.orderId}</b></p>

        <p>Nhà hàng (RESID): <b>${o.resId}</b></p>

        <p>Ngày đặt:
            <b>
                <fmt:formatDate value="${o.orderDate}" pattern="dd/MM/yyyy HH:mm"/>
            </b>
        </p>

        <p>Tổng tiền:
            <b style="color:red">
                <fmt:formatNumber value="${o.totalAmount}"
                                  type="number"
                                  groupingUsed="true"/> VNĐ
            </b>
        </p>

        <p>Địa chỉ: <b>${o.address}</b></p>

        <p>Trạng thái: <b>${o.status}</b></p>
    </div>
    <hr>
</c:forEach>



		<a href="${pageContext.request.contextPath}/Trangchu"> Tiếp tục
			mua hàng </a><br> <a
			href="${pageContext.request.contextPath}/orderHistory"> Xem đơn
			hàng của tôi </a>
	</div>

</body>
</html>
