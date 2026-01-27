<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Món ăn của nhà hàng tôi</title>

<link rel="stylesheet"
    href="${pageContext.request.contextPath}/views/seller/sellerFood.css">
</head>

<body>
<jsp:include page="/views/jsp/demo.jsp"/>
<div class="seller-content">

    <!-- ALERT -->
    <c:if test="${not empty sessionScope.success}">
        <div class="alert-success">
            ${sessionScope.success}
        </div>
        <c:remove var="success" scope="session"/>
    </c:if>

    <!-- HEADER -->
    <div class="page-header">
        <h2>🍽️ Món ăn của nhà hàng tôi</h2>
        <a class="btn-add"
           href="${pageContext.request.contextPath}/seller/food/add">
           ➕ Thêm món mới
        </a>
    </div>

    <!-- TABLE -->
    <table class="food-table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Tên món</th>
                <th>Giá</th>
                <th>Ảnh</th>
                <th>Hành động</th>
            </tr>
        </thead>

        <tbody>
            <c:choose>
                <c:when test="${not empty foods}">
                    <c:forEach var="f" items="${foods}">
                        <tr>
                            <td>#${f.id}</td>
                            <td class="food-name">${f.name}</td>
                            <td class="food-price">${f.price} ₫</td>
                            <td>
                                <img src="${pageContext.request.contextPath}/images/${f.image}"
                                     class="food-img">
                            </td>
                            <td>
                                <a class="action-btn btn-edit"
                                   href="${pageContext.request.contextPath}/seller/food/edit?id=${f.id}">
                                   ✏️ Sửa
                                </a>
                                <a class="action-btn btn-delete"
                                   href="${pageContext.request.contextPath}/seller/food/delete?id=${f.id}"
                                   onclick="return confirm('Xóa món này?')">
                                   🗑️ Xóa
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>

                <c:otherwise>
                    <tr>
                        <td colspan="5" class="empty-text">
                            🚫 Chưa có món ăn nào
                        </td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>

</div>
</body>
</html>
