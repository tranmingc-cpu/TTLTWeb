package SellerController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Account.Role;
import model.Order;
import model.Restaurant;

import java.io.IOException;
import java.util.List;

import DAO.FoodDAOimpl;
import DAO.OrderDAO;
import DAO.SellerDAO;

/**
 * Servlet implementation class SellerDashBoardServlet
 */
@WebServlet("/sellerDashBoard")
public class SellerDashBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SellerDashBoardServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Account accSeller = (Account) session.getAttribute("account");

		// check login + role
		if (accSeller == null || accSeller.getRole() != Role.SELLER) {
			response.sendRedirect(request.getContextPath()+"login");
			return;
		}

		// lấy RESID theo ACCID
		SellerDAO rdao = new SellerDAO();
		int resid = rdao.getResIdByAccid(accSeller.getIdAccount());

		// DAO
		FoodDAOimpl foodDAO = new FoodDAOimpl();
		OrderDAO orderDAO = new OrderDAO();

		// dữ liệu dashboard
		int totalFood = foodDAO.countFoodByRestaurant(resid);
		int totalOrder = orderDAO.countFoodByRes(resid);
		double revenue = orderDAO.revenueThisMonth(resid);

		// gửi sang JSP
		request.setAttribute("totalFood", totalFood);
		request.setAttribute("totalOrder", totalOrder);
		request.setAttribute("revenue", revenue);

		request.getRequestDispatcher("/seller/sellerDashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}