package controller;
import DAO.AccountDAO;

import DAO.FoodDAOimpl;
import DAO.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import java.io.IOException;


/**
 * Servlet implementation class DashBoardServlet
 */
@WebServlet("/admin/Dashbora")
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
		OrderDAO ordao = new OrderDAO();
		AccountDAO accdao = new AccountDAO();
		FoodDAOimpl foodao = new FoodDAOimpl();
		
		  HttpSession session = request.getSession();
	        Account acc = (Account) session.getAttribute("account");
	        if (acc != null) {
	            request.setAttribute("adminName", acc.getUserName());
	        }
	        request.setAttribute("totalOrder", ordao.countOrder());
		request.setAttribute("totalUser", accdao.countUser());
		request.setAttribute("totalRevenue", ordao.totalRevenue());
		request.setAttribute("totalFood", foodao.countFood());
		request.getRequestDispatcher("/views/admin/dashboard.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	}

	}



