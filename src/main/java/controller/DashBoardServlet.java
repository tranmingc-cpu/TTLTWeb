package controller;
import DAO.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.CartItem;
import model.Order;

import java.io.IOException;
import java.net.ResponseCache;
import java.sql.ResultSet;
import java.util.List;

/**
 * Servlet implementation class DashBoardServlet
 */
@WebServlet("/DashBoardServlet")
public class DashBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashBoardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderDAO dao = new OrderDAO();
		request.setAttribute("totalOrder", dao.countOrder());
		request.setAttribute("totalUser", dao.countOrder());
		request.setAttribute("totalRevenue", dao.totalRevenue());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		//
		Account acc = (Account) session.getAttribute("account");
		if(acc == null) {
			response.sendRedirect("login.jsp");
			return; }
			List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
			if(cart ==null|| cart.isEmpty()) {
				response.sendRedirect("cart.jsp");
				return;
			}
			//
			double total =0;
			for (CartItem cartItem : cart) {
				total = cartItem.getTotalPrice();
			}
			Order or = new Order();
			
			// 
			Order od = new Order();
			od.setAccountId(acc.getIdAccount());
			od.setOrderDate( new java.sql.Date(System.currentTimeMillis()));
			od.setTotalAmount(total);
			od.setStatus("PENDING");
			OrderDAO dao = new OrderDAO();
			int orderID = dao.createOrder(od);
			
			for (CartItem cartItem : cart) {
				dao.insertOrderDetail(orderID, cartItem);
			}
			session.removeAttribute("cart");
			
			response.sendRedirect("orderSuccess.jsp");
			
		}

	}



