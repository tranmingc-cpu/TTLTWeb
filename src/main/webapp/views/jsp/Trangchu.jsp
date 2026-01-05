<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Food Online</title>
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/views/Shared/index.css">
</head>

<body>
  <jsp:include page="/views/jsp/demo.jsp"/>
<div class="page-container">
    <!-- ===== NAV ===== -->
<div class="nav">

    <a class="${empty param.action ? 'active' : ''}"
       href="${pageContext.request.contextPath}/Trangchu">
        Trang Chủ
    </a>

    <a class="${param.category == 'Chicken' ? 'active' : ''}"
       href="${pageContext.request.contextPath}/Trangchu?action=category&category=Chicken">
        Chicken
    </a>

    <a class="${param.category == 'Pizza' ? 'active' : ''}"
       href="${pageContext.request.contextPath}/Trangchu?action=category&category=Pizza">
        Pizza/Burger
    </a>

    <a class="${param.category == 'Snack' ? 'active' : ''}"
       href="${pageContext.request.contextPath}/Trangchu?action=category&category=Snack">
        Snack
    </a>

    <a class="${param.category == 'Drink' ? 'active' : ''}"
       href="${pageContext.request.contextPath}/Trangchu?action=category&category=Drink">
        Drink
    </a>

</div>

    <div class="page-content">
        <div class="section">
            <!-- ✅ FIX TITLE -->
            <div class="section-title">${title}</div>

            <div class="products">

                <c:if test="${empty foodlist}">
                    <p>❌ Không có dữ liệu</p>
                </c:if>

                <c:forEach var="f" items="${foodlist}">
                    <!-- CLICK VÀO CẢ Ô -->
                    <a class="product-link"
                       href="${pageContext.request.contextPath}/product-detail?id=${f.id}">

                        <div class="product-card">

                            <img src="${pageContext.request.contextPath}/images/${f.image}"
                                 onerror="this.src='${pageContext.request.contextPath}/images/default-food.jpg'">

                            <div class="product-name">${f.name}</div>

                            <div class="product-price">${f.price} VND</div>

                            <div class="product-note">#${f.id}-Hasky</div>

                            <div class="btn-group">
                                <span class="btn-cart">THÊM VÀO GIỎ</span>
                                <span class="btn-buy">MUA NGAY</span>
                            </div>

                        </div>
                    </a>
                </c:forEach>

            </div>
        </div>

        <!-- ===== XEM TẤT CẢ CHỈ Ở TRANG CHỦ ===== -->
        <c:if test="${title eq 'Món ăn nổi bật'}">
            <div class="view-all">
                <a href="${pageContext.request.contextPath}/Trangchu?action=all">
                    Xem tất cả
                </a>
            </div>
        </c:if>

    </div>

   

</div>
 <jsp:include page="/views/jsp/footer.jsp"/>
</body>
</html>
