package adminController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import DAO.AccountDAO;
import DAO.UserDAO;
import model.Account;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/admin/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
String action = request.getParameter("action");
int id;
AccountDAO dao = new AccountDAO();
if("lock".equals(action)){
	id = Integer.parseInt(request.getParameter("id"));
	Account target = dao.getAccountById(id);
	if(!"admin".equals((target.getRole()))){
		dao.updateStatus(id,0);
	}
	response.sendRedirect("user");
}

		request.setAttribute("accounts", new AccountDAO().getAllAccount());
		request.getRequestDispatcher("/views/admin/user.jsp")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Account acc = new Account();
		acc.setUserName(username);
		acc.setPassword(password);
		acc.setStatus(1);

		new AccountDAO().insertUser(acc);

		response.sendRedirect("user");
	}
}