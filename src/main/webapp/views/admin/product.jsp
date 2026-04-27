<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý món ăn</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/order-list.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/product.css">
</head>

<body>

<jsp:include page="/views/jsp/demo.jsp"/>

<div class="admin-container">

    <div class="admin-header">
        <h1>ADMIN PANEL</h1>
    </div>

    <jsp:include page="/views/admin/sidebar.jsp"/>

    <div class="main-content">

        <c:if test="${not empty sessionScope.success}">
            <div class="alert-success">
                    ${sessionScope.success}
            </div>
            <c:remove var="success" scope="session"/>
        </c:if>

        <div class="page-header">
            <h2>🍽️ Món ăn của nhà hàng</h2>
            <a class="btn-add"
               href="${pageContext.request.contextPath}/admin/food/add">
                ➕ Thêm món mới
            </a>
        </div>

        <div class="table-wrapper">
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

                                <td>${f.name}</td>

                                <td>
                                    <fmt:formatNumber value="${f.price}" type="number"/> ₫
                                </td>

                                <td>
                                    <div class="img-box">
                                        <img src="${f.image}" class="food-img" alt="${f.name}">
                                    </div>
                                </td>

                                <td>
                                    <a class="action-btn btn-edit"
                                       href="${pageContext.request.contextPath}/admin/food/edit?id=${f.id}">
                                        ✏️ Sửa
                                    </a>

                                    <a class="action-btn btn-delete"
                                       href="${pageContext.request.contextPath}/admin/food/delete?id=${f.id}"
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

        <div class="pagination">

            <c:set var="blockSize" value="6"/>
            <c:set var="startPage" value="${((currentPage-1)/blockSize)*blockSize + 1}"/>
            <c:set var="endPage" value="${startPage + blockSize - 1}"/>

            <c:if test="${endPage > totalPages}">
                <c:set var="endPage" value="${totalPages}"/>
            </c:if>

            <c:if test="${startPage > 1}">
                <a href="${pageContext.request.contextPath}/admin/product?page=${startPage - 1}">
                    «
                </a>
            </c:if>
            <c:forEach begin="${startPage}" end="${endPage}" var="i">
                <a href="${pageContext.request.contextPath}/admin/product?page=${i}"
                   class="${i == currentPage ? 'active' : ''}">
                        ${i}
                </a>
            </c:forEach>
            <c:if test="${endPage < totalPages}">
                <a href="${pageContext.request.contextPath}/admin/product?page=${endPage + 1}">
                    »
                </a>
            </c:if>

        </div>

    </div>
</div>

</body>
</html>