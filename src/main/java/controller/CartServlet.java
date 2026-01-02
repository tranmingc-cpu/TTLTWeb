package controller;
import model.Cart;

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

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		   Object uidObj = session.getAttribute("userId");
	        if (uidObj == null) {
	            response.sendRedirect(request.getContextPath() + "/views/jsp/login.jsp");
	            return;
	        }
	        int userId = (int) uidObj;
		CartDAO  cartD= new CartDAO();
		ArrayList<CartItem> cart = cartD.getCart(userId);
		String action = request.getParameter("action");
		if(action!=null) {

		switch(action) {
		case"add":
			int foodID = Integer.parseInt(request.getParameter("foodId"));
			int quantity = Integer.parseInt(request.getParameter("quantity"));
		    try {
				cartD.addToCart(userId, foodID, quantity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "update" :
			int cartDetailId = Integer.parseInt(request.getParameter("cartID"));
			int quantity1 = Integer.parseInt(request.getParameter("quantity"));
			cartD.updateQuantity(cartDetailId, quantity1);
			break;
		case "remove" :
			int id = Integer.parseInt(request.getParameter("cartDetailId"));
			cartD.removeItem(userId, id);
			break;
			
		
		}
		}
		  ArrayList<CartItem> cart1 = cartD.getCart(userId);

	        /* ===== SHIPPING ===== */
	        String ship = request.getParameter("ship");
	        if(ship==null) ship ="normal";
	        int shipFee = 20000; // mặc định

	        if ("save".equals(ship)) shipFee = 10000;
	        if ("fast".equals(ship)) shipFee = 30000;

	        int subTotal = 0; 
	        for (CartItem item : cart1) {
	            subTotal += item.getTotalPrice() ;
	        }

	        int total = subTotal + shipFee;

	        /* ===== SET ATTRIBUTE ===== */
	        request.setAttribute("cart", cart1);
	        request.setAttribute("subTotal", subTotal);
	        request.setAttribute("shipFee", shipFee);
	        request.setAttribute("total", total);
	        request.setAttribute("ship", ship);

	        request.getRequestDispatcher("/views/jsp/cart.jsp").forward(request, response);
	    }

		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
