package adminController;

import DAO.CouponDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Coupons;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;

@WebServlet("/admin/coupon/add")
public class AddCouponServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CouponDAO couponDAO;

    @Override
    public void init() throws ServletException {
        couponDAO = new CouponDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/admin/add-coupon.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            String code = request.getParameter("code");
            String discountType = request.getParameter("discountType");

            BigDecimal discountValue = new BigDecimal(request.getParameter("discountValue"));
            BigDecimal minOrderValue = new BigDecimal(request.getParameter("minOrderValue"));

            String maxDiscountStr = request.getParameter("maxDiscountAmount");
            BigDecimal maxDiscountAmount = (maxDiscountStr != null && !maxDiscountStr.trim().isEmpty())
                    ? new BigDecimal(maxDiscountStr)
                    : BigDecimal.ZERO;

            int quantity = Integer.parseInt(request.getParameter("quantity"));

            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");

            Timestamp startDate = Timestamp.valueOf(startDateStr.replace("T", " ") + ":00");
            Timestamp endDate = Timestamp.valueOf(endDateStr.replace("T", " ") + ":00");

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

            boolean isSuccess = couponDAO.addCoupon(coupon);
            System.out.println("INSERT SUCCESS = " + isSuccess);

            if (isSuccess) {
                request.getSession().setAttribute("successMessage", "Thêm mã giảm giá thành công!");
                response.sendRedirect(request.getContextPath() + "/admin/coupon");
            } else {
                request.setAttribute("errorMessage", "Không thể thêm mã giảm giá vào hệ thống!");
                request.getRequestDispatcher("/views/admin/add-coupon.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Dữ liệu nhập vào không hợp lệ hoặc sai định dạng ngày tháng!");
            request.getRequestDispatcher("/views/admin/add-coupon.jsp").forward(request, response);
        }
    }
}