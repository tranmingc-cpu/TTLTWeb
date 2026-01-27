package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Order;

import java.io.IOException;
import java.util.List;

import DAO.OrderDAO;

/**
 * Servlet implementation class OrderSuccessServlet
 */
@WebServlet("/orderSuccess")
public class OrderSuccessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderDAO orderdao = new OrderDAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderSuccessServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("orderIds") == null) {
            response.sendRedirect(request.getContextPath() + "/Trangchu");
            System.out.println("test odder");
            return;
        }

        // lấy order id
        List<Integer> orderIds = (List<Integer>) session.getAttribute("orderIds");

        List<Order> orders = orderdao.getOrdersByIds(orderIds);

        request.setAttribute("orders", orders);

        // xóa để Fkhông tạo lại đơn
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
