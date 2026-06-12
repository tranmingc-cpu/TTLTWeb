package adminController;

import DAO.CouponDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Coupons;
import util.PermissionUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/admin/coupon")
public class CouponServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CouponDAO copDao = new CouponDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {

            case "list":
                if (PermissionUtil.deny(request,request.getSession(),
                        response, "VIEW_COUPON"))
                    return;

                showList(request, response);
                break;

            case "add":
                if (PermissionUtil.deny(request,request.getSession(),
                        response, "ADD_COUPON"))
                    return;

                request.getRequestDispatcher("/views/admin/add-coupon.jsp").forward(request, response);
                break;

            case "edit":
                if (PermissionUtil.deny(request,request.getSession(),
                        response, "EDIT_COUPON"))
                    return;

                showEdit(request, response);
                break;

            case "delete":
                if (PermissionUtil.deny(request,request.getSession(),
                        response, "DELETE_COUPON"))
                    return;

                delete(request, response);
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/admin/coupon");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            if (PermissionUtil.deny(request,request.getSession(), response, "ADD_COUPON")) return;

            addCoupon(request, response);

        } else if ("toggleStatus".equals(action)) {

            if (PermissionUtil.deny(request,request.getSession(), response, "EDIT_COUPON"))
                return;

            toggleStatus(request, response);

        } else if ("edit".equals(action)) {

            if (PermissionUtil.deny(request,request.getSession(), response, "EDIT_COUPON"))
                return;

            updateCoupon(request, response);
        }
    }

    private void showList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Coupons> coupons = copDao.getAllCounpons();
        request.setAttribute("coupons", coupons);
        request.getRequestDispatcher("/views/admin/coupon.jsp").forward(request, response);
    }

    private void addCoupon(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {

            String code = request.getParameter("code");
            String discountType = request.getParameter("discountType");
            BigDecimal discountValue = new BigDecimal(request.getParameter("discountValue"));
            BigDecimal minOrderValue = new BigDecimal(request.getParameter("minOrderValue"));
            String maxDiscountStr = request.getParameter("maxDiscountAmount");
            BigDecimal maxDiscountAmount = (maxDiscountStr != null && !maxDiscountStr.trim().isEmpty()) ? new BigDecimal(maxDiscountStr) : BigDecimal.ZERO;
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            Timestamp startDate = Timestamp.valueOf(request.getParameter("startDate").replace("T", " ") + ":00");
            Timestamp endDate = Timestamp.valueOf(request.getParameter("endDate").replace("T", " ") + ":00");
            Coupons coupon = new Coupons();

            coupon.setCode(code);
            coupon.setDiscountType(discountType);
            coupon.setDiscountValue(discountValue);
            coupon.setMinOrderValue(minOrderValue);
            coupon.setMaxDiscountAmount(maxDiscountAmount);
            coupon.setQuantity(quantity);
            coupon.setStartDate(startDate);
            coupon.setEndDate(endDate);
            coupon.setStatus(true);

            if (copDao.addCoupon(coupon)) {
                request.getSession().setAttribute("successMessage", "Thêm mã giảm giá thành công!");

                response.sendRedirect(request.getContextPath()
                        + "/admin/coupon");
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("errorMessage", "Không thể thêm mã giảm giá!");

        request.getRequestDispatcher("/views/admin/add-coupon.jsp").forward(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            copDao.deleteCoupon(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/admin/coupon");
    }
    private void toggleStatus( HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        copDao.toggleStatus(id);
        response.sendRedirect(request.getContextPath() + "/admin/coupon");
    }
    private void showEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Coupons coupon = copDao.getCouponById(id);
        request.setAttribute("coupon", coupon);
        request.getRequestDispatcher("/views/admin/edit-coupon.jsp").forward(request, response);
    }
    private void updateCoupon(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int id = Integer.parseInt(request.getParameter("id"));
            String code = request.getParameter("code");
            String discountType = request.getParameter("discountType");
            BigDecimal discountValue = new BigDecimal(request.getParameter("discountValue"));
            BigDecimal minOrderValue = new BigDecimal(request.getParameter("minOrderValue"));
            String maxDiscountStr = request.getParameter("maxDiscountAmount");
            BigDecimal maxDiscountAmount = (maxDiscountStr != null && !maxDiscountStr.trim().isEmpty()) ? new BigDecimal(maxDiscountStr) : BigDecimal.ZERO;

            int quantity = Integer.parseInt(request.getParameter("quantity"));

            Timestamp startDate = Timestamp.valueOf(request.getParameter("startDate").replace("T", " ") + ":00");

            Timestamp endDate = Timestamp.valueOf(request.getParameter("endDate").replace("T", " ") + ":00");

            Coupons coupon = new Coupons();

            coupon.setId(id);
            coupon.setCode(code);
            coupon.setDiscountType(discountType);
            coupon.setDiscountValue(discountValue);
            coupon.setMinOrderValue(minOrderValue);
            coupon.setMaxDiscountAmount(maxDiscountAmount);
            coupon.setQuantity(quantity);
            coupon.setStartDate(startDate);
            coupon.setEndDate(endDate);

            copDao.updateCoupon(coupon);

            response.sendRedirect(request.getContextPath() + "/admin/coupon");

        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute("errorMessage", "Cập nhật thất bại!");

            request.getRequestDispatcher("/views/admin/edit-coupon.jsp").forward(request, response);
        }
    }
}
