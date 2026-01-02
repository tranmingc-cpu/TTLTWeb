<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<html>
<head>
    <title>Food List Test</title>

    
</head>
<body>

<h2>DANH SÁCH FOOD</h2>

<c:if test="${empty foodlist}">
    <p>❌ Không có dữ liệu</p>
</c:if>

<table border="1" cellpadding="10">
    <tr>
        <th>ID</th>
        <th>Tên</th>
        <th>Giá</th>
        <th>Hình</th>
    </tr>

    <c:forEach items="${foodlist}" var="f">
        <tr>
            <td>${f.id}</td>
            <td>${f.name}</td>
            <td>${f.price}</td>
            <td>
                <img src="images/${f.image}" width="80"/>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
