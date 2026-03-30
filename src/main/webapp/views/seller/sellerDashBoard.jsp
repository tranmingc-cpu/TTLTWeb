<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Seller Dashboard</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/seller/seller-dashboard.css">
</head>
<body>
<div class="dashboard-container">

    <h1>SELLER DASHBOARD</h1>

    <div class="card-container">

        <div class="card">
            <h3>Tổng món ăn</h3>
            <p class="number">${totalFood}</p>
        </div>

        <div class="card">
            <h3>Tổng đơn hàng</h3>
            <p class="number">${totalOrder}</p>
        </div>

        <div class="card revenue">
            <h3>Doanh thu tháng này</h3>
            <p class="number">
                ${revenue} <span>VND</span>
            </p>
        </div>

    </div>

</div>

</body>
</html>