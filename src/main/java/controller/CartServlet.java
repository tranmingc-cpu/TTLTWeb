package controller;

import model.Account;
import model.CartItem;
import model.Coupons;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import DAO.CartDAO;
import DAO.FoodDAOimpl;
import DAO.SellerDAO;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        CartDAO cartDAO = new CartDAO();
        Account acc = (Account) session.getAttribute("account");

        String action = request.getParameter("action");

        if ("add".equals(action)) {

            String foodIdRaw = request.getParameter("foodId");
            String qtyRaw = request.getParameter("quantity");

            if (foodIdRaw != null && qtyRaw != null) {

                int foodId = Integer.parseInt(foodIdRaw);
                int quantity = Integer.parseInt(qtyRaw);

                cartDAO.addCartByUser(
                        acc.getIdAccount(),
                        foodId,
                        quantity
                );
            }

            if ("true".equals(request.getParameter("buyNow"))) {
                response.sendRedirect(request.getContextPath() + "/order");
            } else {
                response.sendRedirect(request.getContextPath() + "/cart");
            }

            return;
        }
        if ("update".equals(action)) {

            String detailIdRaw = request.getParameter("detailId");
            String qtyRaw = request.getParameter("quantity");

            if (detailIdRaw == null || qtyRaw == null) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            int detailId = Integer.parseInt(detailIdRaw);
            int quantity = Integer.parseInt(qtyRaw);

            if (quantity < 1) {
                cartDAO.removeItem(detailId);
            } else {
                cartDAO.updateQuantity(detailId, quantity);
            }

            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }
        if ("delete".equals(action)) {

            String detailIdRaw = request.getParameter("detailId");

            if (detailIdRaw != null) {

                int detailId = Integer.parseInt(detailIdRaw);
                cartDAO.removeItem(detailId);
            }

            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }


        int cartId = cartDAO.getCartIdByAccount(acc.getIdAccount());

        ArrayList<CartItem> cart =
                new ArrayList<>(cartDAO.getCartItems(cartId));

        BigDecimal subTotal = BigDecimal.ZERO;

        for (CartItem item : cart) {

            if (item.getTotalPrice() != null) {
                subTotal = subTotal.add(item.getTotalPrice());
            }
        }

        BigDecimal shipFee = BigDecimal.ZERO;
        BigDecimal total = subTotal.add(shipFee);
        BigDecimal discount = BigDecimal.ZERO;

        Coupons coupon = (Coupons) session.getAttribute("coupon");

        if (coupon != null) {
            if (total.compareTo(coupon.getMinOrderValue()) >= 0) {

                if ("percentage".equalsIgnoreCase(
                        coupon.getDiscountType())) {

                    discount = total.multiply(coupon.getDiscountValue()).divide(BigDecimal.valueOf(100));
                    if (coupon.getMaxDiscountAmount() != null &&
                            discount.compareTo(coupon.getMaxDiscountAmount()) > 0) {
                        discount = coupon.getMaxDiscountAmount();
                    }

                } else if ("fixed".equalsIgnoreCase(
                        coupon.getDiscountType())) {
                    discount = coupon.getDiscountValue();
                }
                total = total.subtract(discount);
                if (total.compareTo(BigDecimal.ZERO) < 0) {
                    total = BigDecimal.ZERO;
                }

            } else {

                request.setAttribute("couponError", "Đơn hàng chưa đạt tối thiểu " + coupon.getMinOrderValue()
                );
            }
        }

        if (!cart.isEmpty()) {
            int restaurantId = cart.get(0).getFood().getResID();

            SellerDAO rDAO = new SellerDAO();
            FoodDAOimpl fDAO = new FoodDAOimpl();

            request.setAttribute("restaurant", rDAO.getRestaurantById(restaurantId));

            request.setAttribute("restaurantFoods", fDAO.getFoodsByRestaurant(restaurantId));
        }

        request.setAttribute("cart", cart);
        request.setAttribute("subTotal", subTotal);
        request.setAttribute("shipFee", shipFee);
        request.setAttribute("discount", discount);
        request.setAttribute("total", total);

        request.setAttribute("coupon", session.getAttribute("coupon")
        );

        request.getRequestDispatcher("/views/jsp/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}