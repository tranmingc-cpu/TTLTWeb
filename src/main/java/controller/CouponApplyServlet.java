package controller;

import DAO.CartDAO;
import DAO.CouponDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.CartItem;
import model.Coupons;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/coupon")
public class CouponApplyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CouponDAO couponDAO = new CouponDAO();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String code = request.getParameter("couponCode");

        HttpSession session = request.getSession();

        if (code == null || code.trim().isEmpty()) {

            session.removeAttribute("coupon");
            session.setAttribute("couponMessage", "Vui lòng nhập mã giảm giá.");

            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }
        Coupons coupon = couponDAO.getCouponByCode(code.trim());

        if (coupon == null) {

            session.removeAttribute("coupon");

            session.setAttribute("couponMessage", "Mã giảm giá không hợp lệ hoặc đã hết hạn!");

        } else {

            Account acc = (Account) session.getAttribute("account");
            CartDAO cartDAO = new CartDAO();

            int cartId = cartDAO.getCartIdByAccount(acc.getIdAccount());
            List<CartItem> cart =cartDAO.getCartItems(cartId);
            BigDecimal subTotal =BigDecimal.ZERO;

            for (CartItem item : cart) {
                subTotal = subTotal.add(item.getTotalPrice());
            }

            BigDecimal shipFee = new BigDecimal("0");

            BigDecimal total = subTotal.add(shipFee);

            if (total.compareTo(
                    coupon.getMinOrderValue()) < 0) {
                session.removeAttribute("coupon");
                session.setAttribute("couponMessage", "Đơn hàng chưa đạt tối thiểu " + coupon.getMinOrderValue());

            } else {
                session.setAttribute("coupon", coupon);
                session.setAttribute("couponMessage", "Áp dụng mã giảm giá thành công!");
            }
        }
        response.sendRedirect(request.getContextPath() + "/cart");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}