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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // web gọi get thì hiện form nhập thông tin

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.getRequestDispatcher("/views/jsp/order.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        // nguoi dung submit form thì lưu tt vào session
        // LẤY THÔNG TIN USER NHẬP
        String address = request.getParameter("address");
        String note = request.getParameter("note");

        // LƯU SESSION
        session.setAttribute("orderAddress", address);
        session.setAttribute("orderNote", note);

        response.sendRedirect(request.getContextPath() + "/checkout");
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
	 
	