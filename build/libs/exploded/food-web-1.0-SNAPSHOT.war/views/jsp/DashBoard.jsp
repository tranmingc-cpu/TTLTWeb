<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Doanh Số </title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/views/Shared/dashboard.css">
</head>
<body>
<jsp:include page="${pageContext.request.contextPath}/views/jsp/demo.jsp"></jsp:include>
<div class="stats">
    <div class="stat green">
        <p>Total Revenue</p>
        <h2>${totalRevenue} đ</h2>
    </div>

    <div class="stat red">
        <p>Total Orders</p>
        <h2>${totalOrders}</h2>
    </div>

    <div class="stat blue">
        <p>Customers</p>
        <h2>${totalCustomers}</h2>
    </div>

    <div class="stat orange">
        <p>Today Revenue</p>
        <h2>${todayRevenue} đ</h2>
    </div>
</div>

<div class="charts">

    <div class="box">
        <h3>Revenue This Week</h3>

        <div class="bar-chart">
            <div style="--h:40%">Mon</div>
            <div style="--h:65%">Tue</div>
            <div style="--h:30%">Wed</div>
            <div style="--h:55%">Thu</div>
            <div style="--h:80%">Fri</div>
            <div style="--h:60%">Sat</div>
            <div style="--h:90%">Sun</div>
        </div>
    </div>

    <div class="box small">
        <h3>Order Completion</h3>

        <div class="circle-chart" style="--percent:75">
            <span>75%</span>
        </div>
    </div>

</div>

</body>
</html>