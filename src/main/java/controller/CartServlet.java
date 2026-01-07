package controller;

import model.CartItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

import DAO.CartDAO;
import DAO.FoodDAOimpl;
import DAO.RestaurantDAO;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /* ===== CHECK LOGIN ===== */
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        CartDAO cartDAO = new CartDAO();
        cartDAO.createCartIfNotExists(userId);
        int cartId = cartDAO.getCartId(userId);

        /* ===== ACTION ===== */
        String action = request.getParameter("action");

        if ("add".equals(action)) {

            String foodId = request.getParameter("id");
       
            String qRaw = request.getParameter("quantity");
            if (qRaw != null && qRaw !=null) {
                int foodid = Integer.parseInt(foodId);
                int quantity = Integer.parseInt(qRaw);
              cartDAO.addToCart(cartId, foodid, quantity) ; 
            }
            response.sendRedirect(request.getContextPath()+"/cart");
return;
        }
         if ("update".equals(action)) {

           int detailId = Integer.parseInt(request.getParameter("detailId"));
           int qty = Integer.parseInt(request.getParameter("quantity"));
           cartDAO.updateQuantity(detailId, qty);

        }  if ("remove".equals(action)) {

         int detailId = Integer.parseInt(request.getParameter("detailId"));
         
            cartDAO.removeItem(detailId);
        }

     

        /* ===== LOAD CART ===== */
        ArrayList<CartItem> cart = cartDAO.getCartItem(cartId);

        int subTotal = 0;
        for (CartItem item : cart) {
            subTotal += item.getTotalPrice();
        }


        int shipFee = cart.isEmpty() ? 0 : 20000;
        int total = subTotal + shipFee;

        /* ===== RESTAURANT & SUGGEST ===== */
        if (!cart.isEmpty()) {
            int restaurantId = cart.get(0).getFood().getResID();
            RestaurantDAO rDAO = new RestaurantDAO();
            FoodDAOimpl fDAO = new FoodDAOimpl();

            request.setAttribute("restaurant",
                    rDAO.getRestaurantById(restaurantId));
            request.setAttribute("restaurantFoods",
                    fDAO.getFoodsByRestaurant(restaurantId, 6));
        }

        request.setAttribute("cart", cart);
        request.setAttribute("subTotal", subTotal);
        request.setAttribute("shipFee", shipFee);
        request.setAttribute("total", total);

        request.getRequestDispatcher("/views/jsp/cart.jsp")
               .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}