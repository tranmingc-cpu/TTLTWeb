<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thêm món ăn</title>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/views/seller/addFood.css">
</head>

<body>
<jsp:include page="/views/jsp/demo.jsp"/>

<div class="form-page">
<div class="seller-content">

    <h2>➕ Thêm món ăn mới</h2>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

    <form class="food-form"
          action="${pageContext.request.contextPath}/seller/food/add"
          method="post"
          enctype="multipart/form-data">

        <div class="form-group">
            <label>ID món ăn</label>
            <input type="number" name="id" required>
        </div>

        <div class="form-group">
            <label>Tên món ăn</label>
            <input type="text" name="name" required>
        </div>

        <div class="form-group">
            <label>Danh mục</label>
            <select name="categoryId" required>
                <option value="">-- Chọn danh mục --</option>
                <c:forEach var="c" items="${categories}">
                    <option value="${c.id}">${c.name}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label>Giá (VNĐ)</label>
            <input type="number" name="price" required>
        </div>

        <div class="form-group">
            <label>Ảnh món ăn</label>
            <input type="file" name="image" accept="image/*" required>
        </div>

        <div class="form-group">
            <label>Mô tả</label>
            <textarea name="description" rows="4" required></textarea>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn-add">💾 Lưu món</button>
            <a href="${pageContext.request.contextPath}/seller/food"
               class="btn-cancel">❌ Hủy</a>
        </div>

    </form>

</div>
</div>
</body>
</html>
