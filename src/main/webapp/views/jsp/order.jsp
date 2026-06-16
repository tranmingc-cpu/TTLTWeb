<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thông tin đặt hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/Shared/order.css">
    <style>
        .order-form select {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
        }
    </style>
</head>
<body>

<jsp:include page="/views/jsp/demo.jsp" />
<div class="order-container">

    <h2>📦 THÔNG TIN GIAO HÀNG</h2>
    <form action="${pageContext.request.contextPath}/order" method="post" class="order-form">

        <label>Họ và Tên</label>
        <input type="text" name="name" value="${profile.fullname}" required placeholder="Nhập họ và tên">

        <label>Tỉnh / Thành phố</label>
        <select id="province" name="provinceId" required>
            <option value="">-- Chọn Tỉnh/Thành phố --</option>
        </select>

        <label>Quận / Huyện</label>
        <select id="district" name="districtId" required disabled>
            <option value="">-- Chọn Quận/Huyện --</option>
        </select>

        <label>Phường / Xã</label>
        <select id="ward" name="wardCode" required disabled>
            <option value="">-- Chọn Phường/Xã --</option>
        </select>

        <label>Số nhà, tên đường cụ thể</label>
        <input type="text" id="detailAddress" name="detailAddress" value="${profile.address}" required placeholder="Ví dụ: 123 Đường ABC">

        <label>Số điện thoại</label>
        <input type="tel" name="phone" value="${profile.phone}" required placeholder="Nhập số điện thoại" pattern="[0-9]{9,11}" title="Số điện thoại từ 9–11 chữ số">

        <label>Ghi chú (tuỳ chọn)</label>
        <textarea name="note" placeholder="Ví dụ: Giao giờ hành chính, gọi trước khi giao"></textarea>

        <div class="order-actions">
            <a href="${pageContext.request.contextPath}/cart" class="btn-back">← Quay lại giỏ hàng</a>
            <button type="submit" class="btn-next">Tiếp tục thanh toán →</button>
        </div>

    </form>

</div>

<jsp:include page="/views/jsp/footer.jsp" />

<script>
    const contextPath = "${pageContext.request.contextPath}";

    document.addEventListener("DOMContentLoaded", function() {
        fetch(contextPath + "/api/ghn/provinces")
            .then(res => res.json())
            .then(res => {
                const provinceSelect = document.getElementById("province");
                let data = res.data ? res.data : res;
                if (Array.isArray(data)) {
                    data.forEach(p => {
                        if (p.ProvinceName.includes("Test") || p.ProvinceName.includes("02")) return;
                        let opt = document.createElement("option");
                        opt.value = p.ProvinceID;
                        opt.textContent = p.ProvinceName;
                        provinceSelect.appendChild(opt);
                    });
                }
            }).catch(err => console.error("Lỗi tải tỉnh:", err));
    });

    document.getElementById("province").addEventListener("change", function() {
        const provinceId = this.value;
        const districtSelect = document.getElementById("district");
        const wardSelect = document.getElementById("ward");

        districtSelect.innerHTML = '<option value="">-- Chọn Quận/Huyện --</option>';
        districtSelect.disabled = true;
        wardSelect.innerHTML = '<option value="">-- Chọn Phường/Xã --</option>';
        wardSelect.disabled = true;

        if(!provinceId) return;

        fetch(contextPath + "/api/ghn/districts?provinceId=" + provinceId)
            .then(res => res.json())
            .then(res => {
                let data = res.data ? res.data : res;
                if (Array.isArray(data)) {
                    data.forEach(d => {
                        if (d.DistrictName.includes("Test")) return;
                        let opt = document.createElement("option");
                        opt.value = d.DistrictID;
                        opt.textContent = d.DistrictName;
                        districtSelect.appendChild(opt);
                    });
                    districtSelect.disabled = false;
                }
            }).catch(err => console.error("Lỗi tải huyện:", err));
    });

    document.getElementById("district").addEventListener("change", function() {
        const districtId = this.value;
        const wardSelect = document.getElementById("ward");

        wardSelect.innerHTML = '<option value="">-- Chọn Phường/Xã --</option>';
        wardSelect.disabled = true;

        if(!districtId) return;

        fetch(contextPath + "/api/ghn/wards?districtId=" + districtId)
            .then(res => res.json())
            .then(res => {
                let data = res.data ? res.data : res;
                if (Array.isArray(data)) {
                    data.forEach(w => {
                        if (w.WardName.includes("Test")) return;
                        let opt = document.createElement("option");
                        opt.value = w.WardCode;
                        opt.textContent = w.WardName;
                        wardSelect.appendChild(opt);
                    });
                    wardSelect.disabled = false;
                }
            }).catch(err => console.error("Lỗi tải xã:", err));
    });

    document.querySelector(".order-form").addEventListener("submit", function(e) {
        const province = document.getElementById("province");
        const district = document.getElementById("district");
        const ward = document.getElementById("ward");
        const detailInput = document.getElementById("detailAddress");

        if(province.value && district.value && ward.value && detailInput.value) {
            const pText = province.options[province.selectedIndex].text;
            const dText = district.options[district.selectedIndex].text;
            const wText = ward.options[ward.selectedIndex].text;

            const fullAddress = detailInput.value + ", " + wText + ", " + dText + ", " + pText;

            let hiddenFullAddress = document.getElementById("fullAddressInput");
            if(!hiddenFullAddress) {
                hiddenFullAddress = document.createElement("input");
                hiddenFullAddress.type = "hidden";
                hiddenFullAddress.id = "fullAddressInput";
                hiddenFullAddress.name = "detailAddress";
                this.appendChild(hiddenFullAddress);
            }
            hiddenFullAddress.value = fullAddress;
            detailInput.disabled = true;
        }
    });
</script>
</body>
</html>