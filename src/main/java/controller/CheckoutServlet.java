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
import java.math.BigDecimal;
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
		BigDecimal subTotal = BigDecimal.ZERO;

		for (CartItem item : cart) {
			subTotal = subTotal.add(item.getTotalPrice()
			);
		}

		BigDecimal shipFee = new BigDecimal("20000");

		BigDecimal total = subTotal.add(shipFee);


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

		String address = (String) session.getAttribute("orderAddress");
		String note = (String) session.getAttribute("orderNote");

		// 1. KIỂM TRA PROFILE TRÁNH LỖI NULL POINTER EXCEPTION
		User profile = new UserDAO().getProfileByAccId(acc.getIdAccount());
		String email = "";

		if (profile != null) {
			email = profile.getEmail();
			if (address == null || address.trim().isEmpty()) {
				address = profile.getAddress();
			}
		}

		// Dự phòng nếu địa chỉ vẫn trống rỗng
		if (address == null || address.trim().isEmpty()) {
			address = "Địa chỉ chưa cập nhật";
		}

		Map<Integer, List<CartItem>> cartByRes = new HashMap<>();
		for (CartItem item : cart) {
			cartByRes
					.computeIfAbsent(item.getFood().getResID(), k -> new ArrayList<>())
					.add(item);
		}

		List<Integer> orderIds = new ArrayList<>();

		for (var entry : cartByRes.entrySet()) {

			BigDecimal shipFee = new BigDecimal("20000");

			BigDecimal total = entry.getValue().stream()
					.map(CartItem::getTotalPrice)
					.reduce(BigDecimal.ZERO, BigDecimal::add)
					.add(shipFee);
			int orderId = orderDAO.createOrder(
					acc.getIdAccount(),
					entry.getKey(),
					total,
					address
			);

			// Kiểm tra đề phòng tạo order thất bại
			if (orderId <= 0) {
				System.out.println("❌ Không thể tạo đơn hàng cho nhà hàng ID: " + entry.getKey());
				continue;
			}

			for (CartItem item : entry.getValue()) {
				orderDAO.insertOrderDetail(
						orderId,
						item.getFood().getId(),
						item.getQuantity(),
						item.getFood().getPrice()
				);
			}

			// 2. CHỈ GỬI MAIL KHI CÓ EMAIL HỢP LỆ
			if (email != null && !email.trim().isEmpty()) {
				try {
					String subject = "Xác nhận đơn hàng #" + orderId;
					String content = """
                       Cảm ơn bạn đã đặt hàng ❤️

                       Mã đơn hàng: #%d
                       Tổng tiền: %.0f đ
                       Địa chỉ giao hàng: %s

                       Đơn hàng đang được xử lý.
                       """.formatted(orderId, total, address);

					EmailUtil.send(email, subject, content);
				} catch (Exception e) {
					System.out.println("❌ Lỗi gửi mail cho đơn hàng #" + orderId + ": " + e.getMessage());
				}
			} else {
				System.out.println("⚠️ Tài khoản không có email, bỏ qua bước gửi mail xác nhận.");
			}

			orderIds.add(orderId);
		}

		session.removeAttribute("orderAddress");
		session.removeAttribute("orderNote");
		cartDAO.clearCartByUser(acc.getIdAccount());
		session.setAttribute("orderIds", orderIds);

		response.sendRedirect(request.getContextPath() + "/orderSuccess");
	}
	}
