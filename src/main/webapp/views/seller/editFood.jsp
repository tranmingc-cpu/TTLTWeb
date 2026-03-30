<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa món ăn</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/views/seller/editFood.css">
</head>

<body>
<jsp:include page="/views/jsp/demo.jsp"/>

<div class="container">
    <h2>🍽️ Chỉnh sửa món ăn</h2>

    <form action="${pageContext.request.contextPath}/seller/food/edit"
          method="post"
          enctype="multipart/form-data"
          class="edit-form">


        <input type="hidden" name="id" value="${food.id}">

        <input type="hidden" name="oldImage" value="${food.image}">

        <div class="form-group">
            <label>Tên món ăn</label>
            <input type="text" name="name" value="${food.name}" required>
        </div>
        <div class="form-group">
            <label>Danh mục</label>
            <select name="categoryId" required>
                <c:forEach var="c" items="${categories}">
                    <option value="${c.id}"
                        ${c.id == food.CATEGORYId ? "selected" : ""}>
                            ${c.name}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>Giá (VNĐ)</label>
            <input type="number" step="0.01" name="price"
                   value="${food.price}" required>
        </div>

        <div class="form-group">
            <label>Ảnh hiện tại</label>
            <img src="${pageContext.request.contextPath}/image/${food.image}"
                 class="preview-img">
        </div>

        <div class="form-group">
            <label>Chọn ảnh mới (nếu muốn đổi)</label>
            <input type="file" name="image" accept="image/*"
                   onchange="previewImage(event)">
        </div>

        <div class="form-actions">
            <button type="submit" class="btn-save">💾 Cập nhật</button>
            <a href="${pageContext.request.contextPath}/seller/food"
               class="btn-cancel">↩ Quay lại</a>
        </div>
    </form>

</div>

<script>
    function previewImage(event) {
        const img = document.querySelector(".preview-img");
        img.src = URL.createObjectURL(event.target.files[0]);
    }
</script>

</body>
</html>
