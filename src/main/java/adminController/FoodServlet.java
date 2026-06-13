package adminController;

import DAO.FoodDAOimpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.PermissionUtil;

import java.io.IOException;

@WebServlet("/admin/product")
public class FoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FoodServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (PermissionUtil.deny(request, request.getSession(), response, "VIEW_PRODUCT")) {
			return;
		}

		FoodDAOimpl dao = new FoodDAOimpl();

		int page = 1;
		int pageSize = 8;

		String pageParam = request.getParameter("page");

		if (pageParam != null) {
			try {
				page = Integer.parseInt(pageParam);
			} catch (Exception e) {
				page = 1;
			}
		}
		int totalFoods = dao.countFood();
		int totalPages = (int) Math.ceil((double) totalFoods / pageSize);
		if (page < 1) {
			page = 1;
		}
		if (totalPages > 0 && page > totalPages) {
			page = totalPages;
		}
		int startPage = page - 2;
		int endPage = page + 2;
		if (startPage < 1) {
			startPage = 1;
			endPage = Math.min(5, totalPages);
		}
		if (endPage > totalPages) {
			endPage = totalPages;
			startPage = Math.max(1, totalPages - 4);
		}
		request.setAttribute("foods", dao.findByPage(page, pageSize));
		request.setAttribute("currentPage", page);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.getRequestDispatcher("/views/admin/product.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}