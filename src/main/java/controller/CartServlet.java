package controller;

import model.Account;

import model.CartItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;

import DAO.CartDAO;
import DAO.FoodDAOimpl;
import DAO.SellerDAO;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /* ===== CHECK LOGIN ===== */
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        CartDAO cartDAO = new CartDAO();
        Account acc = (Account) session.getAttribute("account");

        String action = request.getParameter("action");

        /* ================= ADD ================= */
        if ("add".equals(action)) {

            String foodIdRaw = request.getParameter("foodId");
            String qtyRaw = request.getParameter("quantity");

            if (foodIdRaw != null && qtyRaw != null) {
                int foodId = Integer.parseInt(foodIdRaw);
                int quantity = Integer.parseInt(qtyRaw);
                cartDAO.addCartByUser(acc.getIdAccount(), foodId, quantity);
            }

            // BUY NOW → CHECKOUT
            if ("true".equals(request.getParameter("buyNow"))) {
                response.sendRedirect(request.getContextPath() + "/order");
            } else {
                response.sendRedirect(request.getContextPath() + "/cart");
            }
            return;
        }

        /* ================= UPDATE ================= */
        if ("update".equals(action)) {

            String detailIdRaw = request.getParameter("detailId");
            String qtyRaw = request.getParameter("quantity");

            if (detailIdRaw == null || qtyRaw == null) {
                response.sendRedirect("cart");
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
        /* ================= REMOVE ================= */
        if ("delete".equals(action)) {

            String detailIdRaw = request.getParameter("detailId");

            if (detailIdRaw != null) {
                int detailId = Integer.parseInt(detailIdRaw);
                cartDAO.removeItem(detailId);
            }

            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        /* ================= LOAD CART ================= */
        int cart0 = cartDAO.getCartIdByAccount(acc.getIdAccount());
        ArrayList<CartItem> cart =
                new ArrayList<>(cartDAO.getCartItems(cart0));
        int subTotal = 0;
        for (CartItem item : cart) {
            subTotal += item.getTotalPrice();
        }

        int total = subTotal ;

        /* ===== RESTAURANT & SUGGEST ===== */
        if (!cart.isEmpty()) {
            int restaurantId = cart.get(0).getFood().getResID();

            SellerDAO rDAO = new SellerDAO();
            FoodDAOimpl fDAO = new FoodDAOimpl();

            request.setAttribute("restaurant",
                    rDAO.getRestaurantById(restaurantId));
            request.setAttribute("restaurantFoods",
                    fDAO.getFoodsByRestaurant(restaurantId));
        }

        request.setAttribute("cart", cart);
        request.setAttribute("subTotal", subTotal);
        request.setAttribute("total", total);

        request.getRequestDispatcher("/views/jsp/cart.jsp")
                .forward(request, response);

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}