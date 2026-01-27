package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Order;
import model.OrderDetails;

import java.io.IOException;
import java.util.List;

import DAO.OrderDAO;

/**
 * Servlet implementation class OrderTrackingServlet
 */
@WebServlet("/OrderTrackingServlet")
public class OrderTrackingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     private OrderDAO orderDAO = new OrderDAO();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderTrackingServlet() {
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
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int orderId = Integer.parseInt(request.getParameter("orderId"));

       // Order order = orderDAO.getOrdersByIds(orderId);
        List<OrderDetails> details = orderDAO.getOrderDetails(orderId);

       // request.setAttribute("order", order);
        request.setAttribute("details", details);

        request.getRequestDispatcher("/views/jsp/orderTracking.jsp")
               .forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
