
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thông tin đặt hàng</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/views/Shared/order.css">
</head>
<body>

<jsp:include page="${pageContext.request.contextPath}/views/jsp/demo.jsp"/>

<div class="order-container">

    <h2>📦 THÔNG TIN GIAO HÀNG</h2>
    <form action="${pageContext.request.contextPath}/order"
          method="post"
          class="order-form">

        <label>Họ và Tên</label>
        <input type="text"
               name="name"
               value="${profile.fullname}"
               required
               placeholder="Nhập họ và tên">

        <label>Địa chỉ giao hàng</label>
        <input type="text"
               name="address"
               value="${profile.address}"
               required
               placeholder="Nhập địa chỉ giao hàng">

        <label>Số điện thoại</label>
        <input type="tel"
               name="phone"
               value="${profile.phone}"
               required
               placeholder="Nhập số điện thoại"
               pattern="[0-9]{9,11}"
               title="Số điện thoại từ 9–11 chữ số">

        <label>Ghi chú (tuỳ chọn)</label>
        <textarea name="note"
                  placeholder="Ví dụ: Giao giờ hành chính, gọi trước khi giao"></textarea>

        <div class="order-actions">
            <a href="${pageContext.request.contextPath}/cart"
               class="btn-back">← Quay lại giỏ hàng</a>

            <button type="submit" class="btn-next">
                Tiếp tục thanh toán →
            </button>
        </div>

    </form>

</div>

<jsp:include page="${pageContext.request.contextPath}/views/jsp/footer.jsp"/>

</body>
</html>
