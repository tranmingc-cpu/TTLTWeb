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
        <div class="save-all-container">
            <button type="button"
                    id="saveAllBtn"
                    class="action-btn btn-save-all">
                Lưu tất cả
            </button>
        </div>
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
                <form method="post" action="${pageContext.request.contextPath}/admin/permission" class="permission-form" data-accountid="${admin.idAccount}">
                    <input type="hidden" name="accountId" value="${admin.idAccount}" />
                    <tr class="permission-row"
                        data-accountid="${admin.idAccount}">
                                <td>${admin.idAccount}</td>

                                <td>
                                    <strong>${admin.userName}</strong>
                                </td>

                                <td class="permission-cell">

                                    <label>
                                        <input type="checkbox"
                                               name="viewUser"
                                               data-accountid="${admin.idAccount}"
                                        ${p.viewUser ? 'checked' : ''}>
                                        View
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="addUser"
                                               data-accountid="${admin.idAccount}"
                                        ${p.addUser ? 'checked' : ''}>
                                        Add
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="editUser"
                                               data-accountid="${admin.idAccount}"
                                        ${p.editUser ? 'checked' : ''}>
                                        Edit
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="deleteUser"
                                               data-accountid="${admin.idAccount}"
                                        ${p.deleteUser ? 'checked' : ''}>
                                        Delete
                                    </label>

                                </td>

                                <td class="permission-cell">

                                    <label>
                                        <input type="checkbox"
                                               name="viewOrder"
                                               data-accountid="${admin.idAccount}"
                                        ${p.viewOrder ? 'checked' : ''}>
                                        View
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="addOrder"
                                               data-accountid="${admin.idAccount}"
                                        ${p.addOrder ? 'checked' : ''}>
                                        Add
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="editOrder"
                                               data-accountid="${admin.idAccount}"
                                        ${p.editOrder ? 'checked' : ''}>
                                        Edit
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="deleteOrder"
                                               data-accountid="${admin.idAccount}"
                                        ${p.deleteOrder ? 'checked' : ''}>
                                        Delete
                                    </label>

                                </td>

                                <td class="permission-cell">

                                    <label>
                                        <input type="checkbox"
                                               name="viewProduct"
                                               data-accountid="${admin.idAccount}"
                                        ${p.viewProduct ? 'checked' : ''}>
                                        View
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="addProduct"
                                               data-accountid="${admin.idAccount}"
                                        ${p.addProduct ? 'checked' : ''}>
                                        Add
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="editProduct"
                                               data-accountid="${admin.idAccount}"
                                        ${p.editProduct ? 'checked' : ''}>
                                        Edit
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="deleteProduct"
                                               data-accountid="${admin.idAccount}"
                                        ${p.deleteProduct ? 'checked' : ''}>
                                        Delete
                                    </label>

                                </td>

                                <td class="permission-cell">

                                    <label>
                                        <input type="checkbox"
                                               name="viewCoupon"
                                               data-accountid="${admin.idAccount}"
                                        ${p.viewCoupon ? 'checked' : ''}>
                                        View
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="addCoupon"
                                               data-accountid="${admin.idAccount}"
                                        ${p.addCoupon ? 'checked' : ''}>
                                        Add
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="editCoupon"
                                               data-accountid="${admin.idAccount}"
                                        ${p.editCoupon ? 'checked' : ''}>
                                        Edit
                                    </label>

                                    <label>
                                        <input type="checkbox"
                                               name="deleteCoupon"
                                               data-accountid="${admin.idAccount}"
                                        ${p.deleteCoupon ? 'checked' : ''}>
                                        Delete
                                    </label>

                                </td>

                                <td>
                                    <button type="submit" class="action-btn btn-edit">
                                        Lưu quyền
                                    </button>
                                </td>
                        </tr>
                </form>

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
<script>

    document.getElementById("saveAllBtn")
        .addEventListener("click", function () {
            const rows = document.querySelectorAll(".permission-row");
            const permissions = [];
            rows.forEach(row => {
                permissions.push({
                    accountId: parseInt(row.dataset.accountid),
                    viewUser: row.querySelector('[name="viewUser"]')?.checked || false,
                    addUser: row.querySelector('[name="addUser"]')?.checked || false,
                    editUser: row.querySelector('[name="editUser"]')?.checked || false,
                    deleteUser: row.querySelector('[name="deleteUser"]')?.checked || false,
                    viewOrder: row.querySelector('[name="viewOrder"]')?.checked || false,
                    addOrder: row.querySelector('[name="addOrder"]')?.checked || false,
                    editOrder: row.querySelector('[name="editOrder"]')?.checked || false,
                    deleteOrder: row.querySelector('[name="deleteOrder"]')?.checked || false,
                    viewProduct: row.querySelector('[name="viewProduct"]')?.checked || false,
                    addProduct: row.querySelector('[name="addProduct"]')?.checked || false,
                    editProduct: row.querySelector('[name="editProduct"]')?.checked || false,
                    deleteProduct: row.querySelector('[name="deleteProduct"]')?.checked || false,
                    viewCoupon: row.querySelector('[name="viewCoupon"]')?.checked || false,
                    addCoupon: row.querySelector('[name="addCoupon"]')?.checked || false,
                    editCoupon: row.querySelector('[name="editCoupon"]')?.checked || false,
                    deleteCoupon: row.querySelector('[name="deleteCoupon"]')?.checked || false
                });

            });

            console.log(permissions);

            fetch(
                "${pageContext.request.contextPath}/admin/permission/save-all",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(permissions)
                }
            )
                .then(response => response.text())
                .then(data => {
                    alert("Lưu tất cả thành công");
                    location.reload();

                })
                .catch(error => {
                    console.error(error);
                    alert("Có lỗi xảy ra");

                });

        });

</script>
</body>
</html>