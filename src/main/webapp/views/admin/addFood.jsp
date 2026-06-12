<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm món ăn</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/views/admin/addFood.css">
</head>
<body>
<jsp:include page="/views/jsp/demo.jsp"/>

<div class="admin-container">
    <jsp:include page="/views/admin/sidebar.jsp"/>
<div class="form-page">
    <div class="seller-content">
        <h2>➕ Thêm món ăn mới</h2>

        <c:if test="${not empty error}">
            <p style="color:red">${error}</p>
        </c:if>

        <form class="food-form"
              action="${pageContext.request.contextPath}/admin/food/add"
              method="post"
              enctype="multipart/form-data">
            <div class="form-group">
                <label>Tên món ăn</label>
                <input type="text" name="name" required>
            </div>

            <div class="form-group">
                <label>Nhà hàng</label>
                <select name="restaurantId" required>
                    <option value="">-- Chọn nhà hàng --</option>
                    <c:forEach var="r" items="${restaurants}">
                        <option value="${r.resId}">${r.name}</option>
                    </c:forEach>
                </select>
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
                <label>Hình ảnh món ăn (Tối đa 2MB):</label>
                <input type="file" id="fileImage" name="image" accept="image/*" onchange="checkFileSize(this)" required />
                <span id="error-message" style="color: red; display: none; font-weight: bold; margin-top: 5px;"></span>
            </div>
            <script>
                function checkFileSize(input) {
                    const maxBytes = 1024 * 1024 * 2;
                    const file = input.files[0];
                    const errorSpan = document.getElementById("error-message");

                    if (file) {
                        if (file.size > maxBytes) {
                            errorSpan.textContent = "❌ File ảnh quá lớn! Vui lòng chọn file dưới 2MB.";
                            errorSpan.style.display = "block";
                            input.value = "";
                        } else {
                            errorSpan.style.display = "none";
                        }
                    }
                }
            </script>
 <div class="form-group">
     <label>Số lượng</label>
     <input type="number" name="quantity" placeholder="Nhập số lượng..." />
 </div>
            <div class="form-group">
                <label>Mô tả</label>
                <textarea name="description" rows="4" required></textarea>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn-add">💾 Lưu món</button>
                <a href="${pageContext.request.contextPath}/admin/food"
                   class="btn-cancel">❌ Hủy</a>
            </div>

        </form>

    </div>
</div>
</div>
</body>
</html>
