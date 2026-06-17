package adminController;

import DAO.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Order;
import service.GhnService;
import util.PermissionUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/order")
public class AdminOrderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (PermissionUtil.deny(request, request.getSession(), response, "VIEW_ORDER")) {
			return;
		}
		OrderDAO orderDAO = new OrderDAO();
		String action = request.getParameter("action");
		if ("view".equals(action)) {
			Order order = orderDAO.getOrderById(Integer.parseInt(request.getParameter("id")));
			request.setAttribute("order", order);
			request.getRequestDispatcher("/views/admin/orderDetail.jsp").forward(request, response);
			return;
		}

		int totalOrders = orderDAO.countOrder();
		List<Order> orderList = orderDAO.getAllOrders();
		request.setAttribute("orderList", orderList);

		request.setAttribute("totalOrders",
				totalOrders
		);

		request.getRequestDispatcher("/views/admin/order-list.jsp"
		).forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if ("confirm".equals(action)) {
			int orderId = Integer.parseInt(request.getParameter("id"));
			OrderDAO orderDAO = new OrderDAO();
			try {
				Order order = orderDAO.getOrderById(orderId);
				if (order.getGhnOrderCode() == null || order.getGhnOrderCode().isBlank()) {
					GhnService ghnService = new GhnService();
					String ghnCode = ghnService.createOrder(order);
					orderDAO.updateGHNCode(orderId, ghnCode);
					orderDAO.updateStatus(orderId, "READY_TO_PICK");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			response.sendRedirect(request.getContextPath() + "/admin/order"
			);
			return;
		}
		doGet(request, response);
	}
}