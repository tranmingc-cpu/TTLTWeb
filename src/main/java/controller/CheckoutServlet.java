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

import java.io.IOException;
import java.util.List;

import DAO.OrderDAO;

import java.util.Date;

/**
 * Servlet implementation class CheckoutServlet
 */
@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	HttpSession session = request.getSession();
	// check dang nhap
	Account acc = (Account) session.getAttribute("acc");
	if(acc==null) {
		response.sendRedirect("login");
		return;
	}
	//lay cart tu session
	// chat bao them vao de biết session.getattribute tra ve mot list<cartitem>
	@SuppressWarnings("unchecked")
	List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
	   if (cart == null || cart.isEmpty()) {
           request.setAttribute("error", "Giỏ hàng trống, không thể thanh toán!");
           request.getRequestDispatcher("cart.jsp").forward(request, response);
           return;
       }
	// toal 
	double total =0;
	for (CartItem cartItem : cart) {
		total+= cartItem.getTotalPrice();
	}
	Order order = new Order();
	order.setAccountId(acc.getIdAccount());
	order.setOrderDate(new Date());
	order.setTotalAmount(total);
	order.setStatus("NEW");
	
	OrderDAO dao = new OrderDAO();
	int orderID = dao.createOrder(order);
	for (CartItem cartItem : cart) {
		dao.insertOrderDetail(orderID, cartItem);
		
		session.removeAttribute("cart");
		response.sendRedirect("orderSuccess.jsp");
	}
	
	}

}
