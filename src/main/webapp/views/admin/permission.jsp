<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý phân quyền</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/permission.css">
</head>
<body>

<jsp:include page="/views/jsp/demo.jsp"/>
<div class="admin-container">
    <jsp:include page="/views/admin/sidebar.jsp"/>

    <div class="main-content">

            <h2>Quản lý phân quyền Admin</h2>

            <table class="permission-table">

                <thead>
                <tr>
                    <th>ID</th>
                    <th>Tài khoản</th>

                    <th> User Manager</th>
                    <th> Order Manager </th>
                    <th> Product Manager</th>
                    <th> Coupon Manager</th>

                    <th>Thao tác</th>
                </tr>
                </thead>

                <tbody>

                <c:forEach items="${admins}" var="admin">

                    <c:if test="${admin.role.toString() eq 'ADMIN'}">

                        <c:set var="p" value="${permissionMap[admin.idAccount]}" />

                        <tr>

                            <form method="post"
                                  action="${pageContext.request.contextPath}/admin/permission">

                                <input type="hidden"
                                       name="accountId"
                                       value="${admin.idAccount}" />

                                <td>${admin.idAccount}</td>

                                <td>
                                    <strong>${admin.userName}</strong>
                                </td>

                                <td class="permission-cell">

                                    <label>
                                        <input type="checkbox"
                                               name="viewUser"
                                            ${p.viewUser ? 'checked' : ''}>
                                        View
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="addUser"
                                            ${p.addUser ? 'checked' : ''}>
                                        Add
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="editUser"
                                            ${p.editUser ? 'checked' : ''}>
                                        Edit
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="deleteUser"
                                            ${p.deleteUser ? 'checked' : ''}>
                                        Delete
                                    </label>

                                </td>

                                <td class="permission-cell">

                                    <label>
                                        <input type="checkbox"
                                               name="viewOrder"
                                            ${p.viewOrder ? 'checked' : ''}>
                                        View
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="addOrder"
                                            ${p.addOrder ? 'checked' : ''}>
                                        Add
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="editOrder"
                                            ${p.editOrder ? 'checked' : ''}>
                                        Edit
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="deleteOrder"
                                            ${p.deleteOrder ? 'checked' : ''}>
                                        Delete
                                    </label>

                                </td>

                                <td class="permission-cell">

                                    <label>
                                        <input type="checkbox"
                                               name="viewProduct"
                                            ${p.viewProduct ? 'checked' : ''}>
                                        View
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="addProduct"
                                            ${p.addProduct ? 'checked' : ''}>
                                        Add
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="editProduct"
                                            ${p.editProduct ? 'checked' : ''}>
                                        Edit
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="deleteProduct"
                                            ${p.deleteProduct ? 'checked' : ''}>
                                        Delete
                                    </label>

                                </td>

                                <td class="permission-cell">

                                    <label>
                                        <input type="checkbox"
                                               name="viewCoupon"
                                            ${p.viewCoupon ? 'checked' : ''}>
                                        View
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="addCoupon"
                                            ${p.addCoupon ? 'checked' : ''}>
                                        Add
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="editCoupon"
                                            ${p.editCoupon ? 'checked' : ''}>
                                        Edit
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="deleteCoupon"
                                            ${p.deleteCoupon ? 'checked' : ''}>
                                        Delete
                                    </label>

                                </td>

                                <td>
                                    <button type="submit" class="action-btn btn-edit">
                                        Lưu quyền
                                    </button>
                                </td>

                            </form>

                        </tr>

                    </c:if>

                </c:forEach>

                <c:if test="${empty admins}">
                    <tr>
                        <td colspan="7">Không có tài khoản Admin</td>
                    </tr>
                </c:if>

                </tbody>
            </table>
    </div>

</div>

</body>
</html>