package adminController;

import DAO.AccountDAO;
import DAO.AdminPermissionDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Account;
import model.AdminPermission;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/permission")
public class AdminPermissionServlet extends HttpServlet {

    private AccountDAO accountDAO = new AccountDAO();
    private AdminPermissionDAO permissionDAO = new AdminPermissionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Account currentUser = (Account) request.getSession().getAttribute("account");

        if (currentUser == null || currentUser.getRole() != Account.Role.SUPER_ADMIN) {

            response.sendRedirect(request.getContextPath() + "/Trangchu");
            return;
        }
        List<Account> admins = accountDAO.getAllAdmins();
        HttpSession session = request.getSession();

        Map<Integer, AdminPermission> permissionMap = new HashMap<>();
        Map<Integer, AdminPermission> tempPermissions = (Map<Integer, AdminPermission>) session.getAttribute("tempPermissions");


        for (Account admin : admins) {

            AdminPermission permission = null;

            if (tempPermissions != null && tempPermissions.containsKey(admin.getIdAccount())) {

                permission = tempPermissions.get(admin.getIdAccount());

            } else {

                permission = permissionDAO.getPermissionByAccountId(admin.getIdAccount());

                if (permission == null) {
                    permission = new AdminPermission();
                    permission.setAccountId(admin.getIdAccount());
                }
            }

            permissionMap.put(admin.getIdAccount(), permission);
        }
        request.setAttribute("admins", admins);
        request.setAttribute("permissionMap", permissionMap);

        request.getRequestDispatcher("/views/admin/permission.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        Account currentUser = (Account) request.getSession().getAttribute("account");

        if (currentUser == null || currentUser.getRole() != Account.Role.SUPER_ADMIN) {

            response.sendRedirect(request.getContextPath() + "/Trangchu");
            return;
        }

        int accountId = Integer.parseInt(request.getParameter("accountId"));

        AdminPermission p = new AdminPermission();

        p.setAccountId(accountId);

        p.setViewUser(request.getParameter("viewUser") != null);
        p.setAddUser(request.getParameter("addUser") != null);
        p.setEditUser(request.getParameter("editUser") != null);
        p.setDeleteUser(request.getParameter("deleteUser") != null);

        p.setViewOrder(request.getParameter("viewOrder") != null);
        p.setAddOrder(request.getParameter("addOrder") != null);
        p.setEditOrder(request.getParameter("editOrder") != null);
        p.setDeleteOrder(request.getParameter("deleteOrder") != null);

        p.setViewProduct(request.getParameter("viewProduct") != null);
        p.setAddProduct(request.getParameter("addProduct") != null);
        p.setEditProduct(request.getParameter("editProduct") != null);
        p.setDeleteProduct(request.getParameter("deleteProduct") != null);

        p.setViewCoupon(request.getParameter("viewCoupon") != null);
        p.setAddCoupon(request.getParameter("addCoupon") != null);
        p.setEditCoupon(request.getParameter("editCoupon") != null);
        p.setDeleteCoupon(request.getParameter("deleteCoupon") != null);

        HttpSession session = request.getSession();

        Map<Integer, AdminPermission> tempPermissions = (Map<Integer, AdminPermission>) session.getAttribute("tempPermissions");

        if (tempPermissions == null) {
            tempPermissions = new HashMap<>();
        }

        tempPermissions.put(accountId, p);

        session.setAttribute("tempPermissions", tempPermissions
        );

        response.sendRedirect(request.getContextPath() + "/admin/permission");
    }
}