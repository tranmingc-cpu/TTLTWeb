package util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.AdminPermission;

import java.io.IOException;

public class PermissionUtil {

    public static boolean hasPermission(HttpSession session, String permissionType) {
        Account acc = (Account)session.getAttribute("account");
        if(acc !=null && acc.getRole()== Account.Role.SUPER_ADMIN){
            return true;
        }
        AdminPermission p = (AdminPermission) session.getAttribute("permission");
        if (p == null) {
            return false;
        }
        switch (permissionType) {

            case "VIEW_USER":
                return p.isViewUser();
            case "ADD_USER":
                return p.isAddUser();
            case "EDIT_USER":
                return p.isEditUser();
            case "DELETE_USER":
                return p.isDeleteUser();

            case "VIEW_ORDER":
                return p.isViewOrder();
            case "ADD_ORDER":
                return p.isAddOrder();
            case "EDIT_ORDER":
                return p.isEditOrder();
            case "DELETE_ORDER":
                return p.isDeleteOrder();

            case "VIEW_PRODUCT":
                return p.isViewProduct();
            case "ADD_PRODUCT":
                return p.isAddProduct();
            case "EDIT_PRODUCT":
                return p.isEditProduct();
            case "DELETE_PRODUCT":
                return p.isDeleteProduct();

            case "VIEW_COUPON":
                return p.isViewCoupon();
            case "ADD_COUPON":
                return p.isAddCoupon();
            case "EDIT_COUPON":
                return p.isEditCoupon();
            case "DELETE_COUPON":
                return p.isDeleteCoupon();

            case "VIEW_BANNER":
                return p.isViewBanner();
                case"ADD_BANNER":
                    return p.isAddBanner();
            case"EDIT_BANNER" :
                return p.isEditBanner();
            case "DELETE_BANNER":
                return p.isDeleteBanner();
            default:
        }

        return false;
    }

    public static boolean deny(HttpServletRequest request, HttpSession session, HttpServletResponse response, String permission)
            throws ServletException, IOException {
        if (!hasPermission(session, permission)) {
            request.setAttribute("errorMessage", "Bạn không có quyền truy cập chức năng này!");
            request.getRequestDispatcher("/views/admin/access-denied.jsp").forward(request, response);
            return true;
        }
        return false;
    }
}