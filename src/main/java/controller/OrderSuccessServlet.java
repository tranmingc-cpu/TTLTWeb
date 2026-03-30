package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Order;
import model.User;
import util.EmailUtil;

import java.io.IOException;
import java.util.List;

import DAO.OrderDAO;
import DAO.UserDAO;

/**
 * Servlet implementation class OrderSuccessServlet
 */
@WebServlet("/orderSuccess")
public class OrderSuccessServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        List<Integer> orderIds =
            (List<Integer>) session.getAttribute("orderIds");

        if (orderIds == null || orderIds.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        OrderDAO orderDAO = new OrderDAO();
        List<Order> orders = orderDAO.getOrdersByIds(orderIds);

        request.setAttribute("orders", orders);

        session.removeAttribute("orderIds");

        request.getRequestDispatcher("/views/jsp/orderSuccess.jsp")
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
