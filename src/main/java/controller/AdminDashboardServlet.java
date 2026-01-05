package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

import java.io.IOException;

import DAO.AccountDAO;
import DAO.OrderDAO;

/**
 * Servlet implementation class AdminDashboardServlet
 */
@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminDashboardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OrderDAO orderDao = new OrderDAO();
        AccountDAO accDao = new AccountDAO();
        HttpSession session = request.getSession();
        // Lấy account từ session (đã được AdminFilter kiểm tra)
        Account acc = (Account) session.getAttribute("account");
        if (acc != null) {
            request.setAttribute("adminName", acc.getUserName());
        }

        request.setAttribute("totalUser", accDao.countUser());
        request.setAttribute("totalOrder", orderDao.countOrder());
        request.setAttribute("totalRevenue", orderDao.totalRevenue());
        

        request.getRequestDispatcher("/views/admin/dashboard.jsp")
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
