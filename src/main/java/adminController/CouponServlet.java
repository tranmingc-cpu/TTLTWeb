package adminController;

import DAO.CouponDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Coupons;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/admin/coupon")
public class CouponServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CouponDAO copDao = new CouponDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "list":
                showList(request, response);
                break;
            case "delete":
                delete(request, response);
                break;
            default:
                showList(request, response);
                break;
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            addCoupon(request, response);
        } else if ( "toggleStatus".equals(action)){
            toggleStatus(request,response);
        }
    }
    private void showList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Coupons> coupons = copDao.getAllCounpons();
        request.setAttribute("coupons", coupons);
        request.getRequestDispatcher("/views/admin/coupon.jsp").forward(request, response);
    }

    private void addCoupon(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String code = request.getParameter("code");
            String discountType = request.getParameter("discountType");
            BigDecimal discountValue = new BigDecimal(request.getParameter("discountValue"));
            BigDecimal minOrderValue = new BigDecimal(request.getParameter("minOrderValue"));

            String maxDiscountStr = request.getParameter("maxDiscountAmount");
            BigDecimal maxDiscountAmount = (maxDiscountStr != null && !maxDiscountStr.isEmpty())
                    ? new BigDecimal(maxDiscountStr) : null;

            int quantity = Integer.parseInt(request.getParameter("quantity"));

            Timestamp startDate = Timestamp.valueOf(request.getParameter("startDate").replace("T", " ") + ":00");
            Timestamp endDate = Timestamp.valueOf(request.getParameter("endDate").replace("T", " ") + ":00");

            Coupons coupon = new Coupons();
            copDao.addCoupon(coupon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/admin/coupon");
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
}
