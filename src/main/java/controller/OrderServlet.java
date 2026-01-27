package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.CartItem;
import model.Order;
import model.OrderDetails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DAO.CartDAO;
import DAO.OrderDAO;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/order")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderDAO orderDAO = new OrderDAO();
    private CartDAO cartDAO = new CartDAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	
    	 HttpSession session = request.getSession(false);
    	  Account acc = (Account) session.getAttribute("account");
         if (session == null ||acc== null) {
             response.sendRedirect(request.getContextPath() + "/login");
             return;
         }
       
         CartDAO cartDAO = new CartDAO();
         int cartId = cartDAO.getOrCreateCart(acc.getIdAccount());
         List<CartItem> cart = cartDAO.getCartItems(cartId);

         if (cart.isEmpty()) {
             response.sendRedirect(request.getContextPath() + "/cart");
             return;
         }
         
         String fullname = request.getParameter("fullname");
         String phone    = request.getParameter("phone");
         String address  = request.getParameter("address");
         request.setAttribute("cart", cart);

         request.getRequestDispatcher("/views/jsp/order.jsp")
            .forward(request, response);
       

     }
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	 @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException { 
		 doGet(request, response);
	 }
	 }

	   /*     HttpSession session = request.getSession(false);
	        if (session == null || session.getAttribute("account") == null) {
	            response.sendRedirect(request.getContextPath() + "/login");
	            return;
	        }

	        Account acc = (Account) session.getAttribute("account");
	        int accountId = acc.getIdAccount();

	        CartDAO cartDAO = new CartDAO();
	        OrderDAO orderDAO = new OrderDAO();

	        // 1. Lấy cart
	        ArrayList<CartItem> cart = cartDAO.getCartByUser(accountId);

	        if (cart.isEmpty()) {
	            response.sendRedirect(request.getContextPath() + "/cart?error=empty");
	            return;
	        }

	        // 2. Tính total
	        double total = 0;
	        for (CartItem item : cart) {
	            total += item.getTotalPrice();
	        }

	        // 3. Lấy địa chỉ giao hàng
	        String address = request.getParameter("address");

	        // 4. Tạo order
	        int orderId = orderDAO.createOrder(accountId, total);

	        // 5. Tạo order detail
	        for (CartItem item : cart) {
	            orderDAO.insertOrderDetail(
	                    orderId,
	                    item.getFood().getId(),
	                    item.getQuantity(),
	                    item.getFood().getPrice()
	            );
	        }

	        // 6. Clear cart
	        cartDAO.clearCartByUser(accountId);

		 HttpSession session = request.getSession(false);
		    if (session == null || session.getAttribute("account") == null) {
		        response.sendRedirect(request.getContextPath() + "/login");
		        return;
		    }

		    Account acc = (Account) session.getAttribute("account");
		    int accountId = acc.getIdAccount();

		    CartDAO cartDAO = new CartDAO();
		    OrderDAO orderDAO = new OrderDAO();

		    List<CartItem> cart = cartDAO.getCartByUser(accountId);
		    if (cart.isEmpty()) {
		        response.sendRedirect(request.getContextPath() + "/cart");
		        return;
		    }

		    // ===== 1. GROUP CART THEO RESID =====
		    Map<Integer, List<CartItem>> cartByRes = new HashMap<>();

		    for (CartItem item : cart) {
		        int resId = item.getFood().getResID();
		        cartByRes
		            .computeIfAbsent(resId, k -> new ArrayList<>())
		            .add(item);
		    }

		    // ===== 2. TẠO ORDER CHO MỖI NHÀ HÀNG =====
		    List<Integer> createdOrderIds = new ArrayList<>();

		    for (Map.Entry<Integer, List<CartItem>> entry : cartByRes.entrySet()) {

		        int resId = entry.getKey();
		        List<CartItem> items = entry.getValue();

		        double total = 0;
		        for (CartItem item : items) {
		            total += item.getTotalPrice();
		        }

		        // 👉 tạo order
		        int orderId = orderDAO.createOrder(
		                accountId,
		                resId,
		                total,
		                request.getParameter("address")
		        );

		        //  tạo order detail
		        for (CartItem item : items) {
		            orderDAO.insertOrderDetail(
		                    orderId,
		                    item.getFood().getId(),
		                    item.getQuantity(),
		                    item.getFood().getPrice()
		            );
		        }

		        createdOrderIds.add(orderId);
		    }

		    // ===== 3. CLEAR CART =====
		    cartDAO.clearCartByUser(accountId);

		    // ===== 4. LƯU SESSION (nếu cần) =====
		    session.setAttribute("orderId", createdOrderIds);

		    response.sendRedirect(
		    	    request.getContextPath() + "/views/jsp/checkout.jsp"
		    	);
		}	
		    }*/
	 
	