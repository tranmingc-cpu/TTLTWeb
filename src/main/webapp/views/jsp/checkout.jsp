<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thanh toán</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/Shared/checkout.css">
</head>
<body>
<jsp:include page="/views/jsp/demo.jsp" />
<div class="checkout-container">

    <h2>💳 XÁC NHẬN THANH TOÁN</h2>

    <div class="checkout-box">

        <table class="cart-table">
            <thead>
            <tr>
                <th>Sản phẩm</th>
                <th>SL</th>
                <th>Giá</th>
                <th>Tạm tính</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${cart}">
                <tr>
                    <td>${item.food.name}</td>
                    <td>${item.quantity}</td>
                    <td>
                        <fmt:formatNumber value="${item.food.price}" groupingUsed="true"/> ₫
                    </td>
                    <td>
                        <fmt:formatNumber value="${item.totalPrice}" groupingUsed="true"/> ₫
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="checkout-summary">

            <c:if test="${coupon != null}">
                <div class="coupon-info">
                    🎉 Mã giảm giá:
                    <b>${coupon.code}</b>
                </div>
            </c:if>

            <p>
                <span>Giá Gốc </span>
                <strong id="sub-total-val" data-value="${subTotal}">
                    <fmt:formatNumber value="${subTotal}" type="number"/> đ
                </strong>
            </p>

            <p>
                <span>Phí ship</span>
                <strong id="ship-fee-val">Đang tính...</strong>
            </p>

            <c:if test="${discount > 0}">
                <p class="discount-row">
                    <span>Giảm giá</span>
                    <strong id="discount-val" data-value="${discount}">
                        - <fmt:formatNumber value="${discount}" type="number"/> đ
                    </strong>
                </p>
            </c:if>

            <p class="total-row">
                <span>Tổng thanh toán</span>
                <strong id="total-val">
                    <fmt:formatNumber value="${total}" type="number"/> đ
                </strong>
            </p>
        </div>

        <form action="${pageContext.request.contextPath}/checkout" method="post" class="checkout-action" style="display: block;">

            <div class="address-box" style="text-align: left; margin-bottom: 20px;">
                <h4>📍 Thông tin giao hàng</h4>
                <p style="font-size: 15px; color: #333; line-height: 1.6; margin: 0;">
                    <b>Người nhận:</b> ${orderName} <br>
                    <b>Số điện thoại:</b> ${orderPhone} <br>
                    <b>Địa chỉ:</b> ${orderDetailAddress}
                </p>

                <input type="hidden" name="name" value="${orderName}">
                <input type="hidden" name="phone" value="${orderPhone}">
                <input type="hidden" id="provinceId" name="provinceId" value="${orderProvinceId}">
                <input type="hidden" id="districtId" name="districtId" value="${orderDistrictId}">
                <input type="hidden" id="wardCode" name="wardCode" value="${orderWardCode}">
                <input type="hidden" id="detailAddress" name="detailAddress" value="${orderDetailAddress}">
                <input type="hidden" id="hiddenShipFee" name="shipFee" value="0">
            </div>

            <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 20px;">
                <a href="${pageContext.request.contextPath}/order" class="btn-back">← Quay lại chỉnh sửa</a>
                <button type="submit" class="btn-confirm">✅ Xác nhận thanh toán</button>
            </div>
        </form>
    </div>
</div>
<jsp:include page="/views/jsp/footer.jsp" />

<script>
    document.addEventListener("DOMContentLoaded", function() {
        const districtId = document.getElementById("districtId").value;
        const wardCode = document.getElementById("wardCode").value;

        if(!districtId || !wardCode) {
            document.getElementById("ship-fee-val").textContent = "0 đ";
            return;
        }

        fetch(contextPath + "/api/ghn/calculate-fee?toDistrictId=" + districtId + "&toWardCode=" + wardCode + "&insuranceValue=" + subTotal, {
            method: "POST"
        })
            .then(res => res.json())
            .then(res => {
                let fee = 0;

                if (res.shippingFee !== undefined) {
                    fee = parseFloat(res.shippingFee);
                } else if (res.data && res.data.shippingFee !== undefined) {
                    fee = parseFloat(res.data.shippingFee);
                } else if (res.success && typeof res.data === 'number') {
                    fee = parseFloat(res.data);
                }

                if(fee >= 0) {
                    document.getElementById("ship-fee-val").textContent = fee.toLocaleString('vi-VN') + " đ";
                    document.getElementById("hiddenShipFee").value = fee;

                    const finalTotal = subTotal + fee - discount;
                    document.getElementById("total-val").textContent = finalTotal.toLocaleString('vi-VN') + " đ";
                } else {
                    document.getElementById("ship-fee-val").textContent = "0 đ";
                }
            })
            .catch(err => {
                console.error("Lỗi tính phí ship:", err);
                document.getElementById("ship-fee-val").textContent = "Lỗi tính phí";
            });
    });
</script>
</body>
</html>