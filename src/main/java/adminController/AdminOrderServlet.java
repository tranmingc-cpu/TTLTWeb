package adminController;

import DAO.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Order;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/order")
public class AdminOrderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		OrderDAO orderDAO = new OrderDAO();

		String action = request.getParameter("action");

		// Trang chi tiết đơn hàng
		if (action != null && action.equals("view")) {

			int id = Integer.parseInt(request.getParameter("id"));

			Order order = orderDAO.getOrderById(id);

			request.setAttribute("order", order);


			request.getRequestDispatcher("/views/admin/orderDetail.jsp").forward(request, response);
			return;
		}

		// Trang danh sách đơn hàng
		int totalOrders = orderDAO.countOrder();
		List<Order> orderList = orderDAO.getAllOrders();

		request.setAttribute("orderList", orderList);
		request.setAttribute("totalOrders", totalOrders);

		request.getRequestDispatcher("/views/admin/order-list.jsp").forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}