<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm mã giảm giá mới</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/coupon.css">
</head>
<body>

<jsp:include page="/views/jsp/demo.jsp"/>

<div class="admin-container">

    <jsp:include page="/views/admin/sidebar.jsp"/>

    <div class="main-content">
        <div class="page-header">
            <h2>➕ Tạo mã giảm giá mới</h2>
        </div>

        <div class="table-wrapper">
            <form action="${pageContext.request.contextPath}/admin/coupon/add" method="POST">
                <div class="form-container">
                    <div class="form-group">
                        <label>Mã code :</label>
                        <input type="text" name="code" required>
                    </div>
                    <div class="form-group">
                        <label>Loại giảm giá:</label>
                        <select name="discountType" required>
                            <option value="fixed">Giảm số tiền cố định (fixed)</option>
                            <option value="percentage">Giảm theo phần trăm (percentage)</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Giá trị giảm:</label>
                        <input type="number" step="0.01" name="discountValue" required>
                    </div>
                    <div class="form-group">
                        <label>Đơn tối thiểu:</label>
                        <input type="number" step="0.01" name="minOrderValue" value="0" required>
                    </div>
                    <div class="form-group">
                        <label>Giảm tối đa (Nếu chọn %):</label>
                        <input type="number" step="0.01" name="maxDiscountAmount">
                    </div>
                    <div class="form-group">
                        <label>Số lượng phát hành:</label>
                        <input type="number" name="quantity" required>
                    </div>
                    <div class="form-group">
                        <label>Ngày bắt đầu:</label>
                        <input type="datetime-local" name="startDate" required>
                    </div>
                    <div class="form-group">
                        <label>Ngày hết hạn:</label>
                        <input type="datetime-local" name="endDate" required>
                    </div>
                </div>

                <div class="btn-group">
                    <button type="submit" class="btn-submit">Lưu mã giảm giá</button>
                    <a href="${pageContext.request.contextPath}/admin/coupon" class="btn-cancel">Hủy bỏ</a>
                </div>
            </form>
        </div>
    </div>