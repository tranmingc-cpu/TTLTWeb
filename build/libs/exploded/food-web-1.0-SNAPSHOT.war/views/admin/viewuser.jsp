<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thông tin người dùng</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/views/admin/viewuser.css">
</head>
<body>

<jsp:include page="/views/jsp/demo.jsp"/>

<div class="admin-container">

    <jsp:include page="/views/admin/sidebar.jsp"/>

    <div class="main-content">
        <main class="admin-content">

            <h2>Thông tin người dùng</h2>

            <table class="user-table">
                <tr>
                    <th style="width:250px;">ID User</th>
                    <td>${user.id}</td>
                </tr>

                <tr>
                    <th>ID Tài khoản</th>
                    <td>${user.accid}</td>
                </tr>

                <tr>
                    <th>Họ và tên</th>
                    <td>${user.fullname}</td>
                </tr>

                <tr>
                    <th>Email</th>
                    <td>${user.email}</td>
                </tr>

                <tr>
                    <th>Số điện thoại</th>
                    <td>${user.number}</td>
                </tr>

                <tr>
                    <th>Địa chỉ</th>
                    <td>${user.address}</td>
                </tr>
            </table>

            <div style="margin-top:25px;">
                <a href="${pageContext.request.contextPath}/admin/user">
                    <button class="btn-add">
                        ← Quay lại
                    </button>
                </a>
            </div>

        </main>
    </div>

</div>

</body>
</html>