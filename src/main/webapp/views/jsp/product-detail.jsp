<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${food.name}</title>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/views/Shared/product-detail.css">
</head>
<body>
<div class="container">

    <div class="breadcrumb">
        Trang chủ / Rau - Củ - Quả / <b>${food.name}</b>
    </div>

    <div class="food-detail">

        <!-- HÌNH -->
        <div class="food-image">
            <img src="${pageContext.request.contextPath}/images/${food.image}" alt="">
        </div>

        <!-- THÔNG TIN -->
        <div class="food-info">
            <h1>${food.name}</h1>

            <p class="status">
                Tình trạng:
                <span class="in-stock">Còn hàng</span>
            </p>

            <div class="price">
                <span class="new-price">${food.price}₫</span>
                <span class="old-price">${food.price + 10000}₫</span>
            </div>

            <div class="promo-box">
                🔥 <b>Siêu Ưu Đãi</b>
                <ul>
                    <li>Nhập mã <b>EGANY</b> giảm 15%</li>
                    <li>Số lượng có hạn</li>
                </ul>
            </div>

            <!-- FORM ADD TO CART -->
            <form action="CartServlet" method="post">
                <input type="hidden" name="foodId" value="${food.id}">

                <div class="quantity">
                    <button type="button">-</button>
                    <input type="number" name="qty" value="1" min="1">
                    <button type="button">+</button>
                </div>

                <button class="add-cart">
                    THÊM VÀO GIỎ HÀNG
                </button>
            </form>

        </div>
    </div>

</div>
</body>
</html>