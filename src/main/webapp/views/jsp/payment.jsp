<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thanh toán chuyển khoản - SePay QR</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/Shared/payment.css">

</head>
<body>

<jsp:include page="/views/jsp/demo.jsp" />

<div class="payment-wrapper">
    <div class="card payment-info-card">
        <h2>🏦 Thông tin chuyển khoản</h2>
        
        <div class="info-row">
            <span class="info-label">Ngân hàng</span>
            <span class="info-value">${bank}</span>
        </div>
        
        <div class="info-row">
            <span class="info-label">Số tài khoản</span>
            <span class="info-value">
                <span id="accNum">${accountNo}</span>
                <button class="copy-btn" onclick="copyText('accNum', 'Số tài khoản')">Sao chép</button>
            </span>
        </div>
        
        <div class="info-row">
            <span class="info-label">Chủ tài khoản</span>
            <span class="info-value">${accountName}</span>
        </div>
        
        <div class="info-row">
            <span class="info-label">Số tiền</span>
            <span class="info-value amount-highlight">
                <span id="payAmount"><fmt:formatNumber value="${totalAmount}" type="number"/></span> đ
                <button class="copy-btn" onclick="copyTextRaw('${totalAmount}', 'Số tiền')">Sao chép</button>
            </span>
        </div>
        
        <div class="info-row" style="background: rgba(79, 70, 229, 0.05); padding: 14px; border-radius: 12px; border: 1px solid rgba(79, 70, 229, 0.1); margin-top: 10px;">
            <span class="info-label" style="color: var(--primary);">Nội dung chuyển khoản</span>
            <span class="info-value">
                <span id="memoText" class="memo-highlight">${mem}</span>
                <button class="copy-btn" style="background: var(--primary); color:#fff;" onclick="copyText('memoText', 'Nội dung chuyển khoản')">Sao chép</button>
            </span>
        </div>

        <h3 style="margin-top: 30px; font-size: 18px; color: #0F172A; display: flex; align-items: center; gap: 8px;">
            📦 Đơn hàng liên quan
        </h3>
        <div style="max-height: 150px; overflow-y: auto; background: #F8FAFC; border-radius: 12px; padding: 15px; border: 1px solid #E2E8F0;">
            <c:forEach var="o" items="${orders}">
                <div style="display: flex; justify-content: space-between; margin-bottom: 10px; font-size: 14px; border-bottom: 1px solid #EDF2F7; padding-bottom: 8px;">
                    <span>Đơn hàng <b>#${o.orderId}</b></span>
                    <span><fmt:formatNumber value="${o.totalAmount}" type="number"/> đ</span>
                </div>
            </c:forEach>
        </div>
    </div>

    <div class="card qr-card">
        <div class="qr-frame">
            <img class="qr-image" src="${qrUrl}" alt="SePay VietQR Code">
        </div>
        
        <div class="qr-instructions">
            Quét mã QR bằng ứng dụng ngân hàng của bạn.<br>
            Nội dung chuyển khoản và số tiền đã được cài đặt tự động.
        </div>

        <form id="simulateForm" action="${pageContext.request.contextPath}/payment" method="post" class="simulation-container">
            <input type="hidden" name="orderIdsStr" value="${orderIdsStr}">
            <button type="button" class="btn-simulate" onclick="triggerSuccessSimulation()">
                ⚡ Giả lập chuyển khoản thành công
            </button>
        </form>
    </div>
</div>

<jsp:include page="/views/jsp/footer.jsp" />

<div id="toast" class="toast">Đã sao chép!</div>

<div id="successOverlay" class="overlay-success">
    <div class="success-box">
        <div class="checkmark-circle">
            <span class="checkmark">✓</span>
        </div>
        <div class="success-title">Thanh toán thành công!</div>
        <p class="success-desc">Hệ thống SePay đã ghi nhận giao dịch của bạn. Đang chuyển hướng về trang đơn hàng...</p>
    </div>
</div>

<script>
    function copyText(elementId, label) {
        const text = document.getElementById(elementId).textContent;
        navigator.clipboard.writeText(text).then(() => {
            showToast("Đã sao chép " + label + "!");
        });
    }

    function copyTextRaw(text, label) {
        navigator.clipboard.writeText(text).then(() => {
            showToast("Đã sao chép " + label + "!");
        });
    }

    function showToast(message) {
        const toast = document.getElementById("toast");
        toast.textContent = message;
        toast.classList.add("show");
        setTimeout(() => {
            toast.classList.remove("show");
        }, 2500);
    }

    function triggerSuccessSimulation() {
        const overlay = document.getElementById("successOverlay");
        overlay.classList.add("show");
        
        setTimeout(() => {
            document.getElementById("simulateForm").submit();
        }, 2000);
    }
</script>

</body>
</html>
