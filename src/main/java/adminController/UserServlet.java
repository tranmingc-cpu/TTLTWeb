package adminController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

import model.Account.Role;
import DAO.AccountDAO;
import DAO.UserDAO;
import model.Account;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/admin/user/*")
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
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path = request.getPathInfo();
		AccountDAO dao = new AccountDAO();

		// Trang thêm user
		if (path != null && path.equals("/add-page")) {
			request.getRequestDispatcher("/views/admin/add-user.jsp")
					.forward(request, response);
			return;
		}

		String action = request.getParameter("action");

		if (action != null) {
			int id = Integer.parseInt(request.getParameter("id"));
			Account target = dao.getAccountById(id);

			if (target != null && target.getRole() != Role.ADMIN) {

				if ("delete".equals(action)) {
					dao.deleteUser(id);

				} else if ("lock".equals(action)) {
					dao.updateStatus(id, 0);

				} else if ("unlock".equals(action)) {
					dao.updateStatus(id, 1);
				}
			}

			response.sendRedirect(request.getContextPath() + "/admin/user");
			return;
		}

		request.setAttribute("accounts", dao.getAllAccount());
		request.getRequestDispatcher("/views/admin/user.jsp")
				.forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getPathInfo();
		if (path != null && path.equals("/add")) {

			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String roleStr = request.getParameter("role").toUpperCase();
			Role roles = Role.valueOf(roleStr);

			Account acc = new Account();
			acc.setUserName(username);
			acc.setPassword(password);
			acc.setRole(roles);
			acc.setStatus(1);
			new AccountDAO().insertUser(acc);

			response.sendRedirect(request.getContextPath() + "/admin/user");
		}
	}
}