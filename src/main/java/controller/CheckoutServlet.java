package controller;

import DAO.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.CartItem;
import model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DAO.CartDAO;
import DAO.OrderDAO;
import util.EmailUtil;
/**
 * Servlet implementation class CheckoutServlet
 */
@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CartDAO cartDAO = new CartDAO();
    private OrderDAO orderDAO = new OrderDAO();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    HttpSession session = request.getSession(false);
	    if (session == null || session.getAttribute("account") == null) {
	        response.sendRedirect(request.getContextPath() + "/login");
	        return;
	    }
// ktra accId và cartid
	    Account acc = (Account) session.getAttribute("account");

	    int cartId = cartDAO.getCartIdByAccount(acc.getIdAccount());
	    List<CartItem> cart = cartDAO.getCartItems(cartId);

	    if (cart.isEmpty()) {
	        response.sendRedirect(request.getContextPath() + "/cart");
	        return;
	    }
          // tính total
	    double subTotal = 0;
	    for (CartItem item : cart) {
	        subTotal += item.getTotalPrice();
	    }

	    int shipFee = 20000;
	    double total = subTotal + shipFee;

	    //  LẤY TỪ ORDER SERVLET
	    String address = (String) session.getAttribute("orderAddress");
	    String note = (String) session.getAttribute("orderNote");

	    request.setAttribute("cart", cart);
	    request.setAttribute("subTotal", subTotal);
	    request.setAttribute("shipFee", shipFee);
	    request.setAttribute("total", total);
	    request.setAttribute("address", address);
	    request.setAttribute("note", note);

	    request.getRequestDispatcher("/views/jsp/checkout.jsp")
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

		    Account acc = (Account) session.getAttribute("account");

		    int cartId = cartDAO.getCartIdByAccount(acc.getIdAccount());
		    List<CartItem> cart = cartDAO.getCartItems(cartId);

		    if (cart == null || cart.isEmpty()) {
		        response.sendRedirect(request.getContextPath() + "/cart");
		        return;
		    }

		    // LẤY THÔNG TIN TỪ SESSION (ORDER)
		    String address = (String) session.getAttribute("orderAddress");
		    String note = (String) session.getAttribute("orderNote");

		    Map<Integer, List<CartItem>> cartByRes = new HashMap<>();
		    User profile = new UserDAO().getProfileByAccId(acc.getIdAccount());
		    String email = profile.getEmail();
               // tạo order với tt vừa lưu 
		    for (CartItem item : cart) {
		        cartByRes
		            .computeIfAbsent(item.getFood().getResID(), k -> new ArrayList<>())
		            .add(item);
		    }

		    List<Integer> orderIds = new ArrayList<>();

		    for (var entry : cartByRes.entrySet()) {

		        double shipFee = 20000;
		        double total = entry.getValue().stream()
		                .mapToDouble(CartItem::getTotalPrice)
		                .sum() + shipFee;

		        int orderId = orderDAO.createOrder( // tao order 
		                acc.getIdAccount(),
		                entry.getKey(),
		                total,
		                address
		        );

		        for (CartItem item : entry.getValue()) {
		            orderDAO.insertOrderDetail(
		                    orderId,
		                    item.getFood().getId(),
		                    item.getQuantity(),
		                    item.getFood().getPrice()
		            );
		        }

		        // GỬI MAIL Ở ĐÂY
		        String subject = "Xác nhận đơn hàng #" + orderId;
		        String content = """
		            Cảm ơn bạn đã đặt hàng ❤️

		            Mã đơn hàng: #%d
		            Tổng tiền: %.0f đ
		            Địa chỉ giao hàng: %s

		            Đơn hàng đang được xử lý.
		            """.formatted(orderId, total, address);

		        EmailUtil.send(email, subject, content);

		        orderIds.add(orderId);
		    }

		    session.removeAttribute("orderAddress");
		    session.removeAttribute("orderNote");
		    cartDAO.clearCartByUser(acc.getIdAccount());
		    session.setAttribute("orderIds", orderIds);
            
		    response.sendRedirect(request.getContextPath() + "/orderSuccess");
		}
	}
      /*  HttpSession session = request.getSession(false);
     

        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        Account acc = (Account) session.getAttribute("account");

            int cart0 = cartDAO.getCartIdByAccount(acc.getIdAccount());
            List<CartItem> cart = cartDAO.getCartItems(cart0);
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }
        // lấy thông tin user  
        User profile = new UserDAO().getProfileByAccId(acc.getIdAccount());
        String address = profile.getAddress();
        Map<Integer, List<CartItem>> cartByRes = new HashMap<>();

        for (CartItem item : cart) {
            cartByRes
              .computeIfAbsent(item.getFood().getResID(), k -> new ArrayList<>())
              .add(item);
        }
 // tạo order và thêm vào order deatail
        List<Integer> orderIds = new ArrayList<>();

        for (var entry : cartByRes.entrySet()) {

			double shipFee = 20000;
			double total = entry.getValue().stream().mapToDouble(CartItem::getTotalPrice).sum() + shipFee;

			int orderId = orderDAO.createOrder(acc.getIdAccount(), entry.getKey(), total, address);
			if (orderId <= 0) {
			    System.out.println("❌ CREATE ORDER FAILED for RESID = " + entry.getKey());
			    continue; // KHÔNG insert order detail
			}
            for (CartItem item : entry.getValue()) {
                orderDAO.insertOrderDetail( // insert orderdetail
                        orderId,
                        item.getFood().getId(),
                        item.getQuantity(),
                        item.getFood().getPrice()
                );
            }
// gửi mail sau khi tạo order và inser vào orderdetail
            String subject = "Xác nhận đơn hàng #" + orderId;
            String content = """
                Cảm ơn bạn đã đặt hàng ❤️

                Mã đơn hàng: #%d
                Tổng tiền: %.0f đ
                Địa chỉ giao hàng: %s

                Đơn hàng đang được xử lý.
                """.formatted(orderId, total, address);
            System.out.println(">>> BEFORE SEND MAIL");
            EmailUtil.send(profile.getEmail(), subject, content);
            System.out.println(">>> AFTER SEND MAIL");
            orderIds.add(orderId);
        }

        cartDAO.clearCartByUser(acc.getIdAccount());
        session.setAttribute("orderIds", orderIds);

        response.sendRedirect(request.getContextPath() + "/orderSuccess");
    }

} */