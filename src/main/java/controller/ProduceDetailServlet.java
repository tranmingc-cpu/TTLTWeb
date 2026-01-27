package controller;

import model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import DAO.FoodDAOimpl;

/**
 * Servlet implementation class ProduceDetailServlet
 */
@WebServlet("/product-detail")
public class ProduceDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FoodDAOimpl dao = new FoodDAOimpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProduceDetailServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");

		String idR = request.getParameter("id");

		if (idR == null) {
			request.setAttribute("error", "Không tìm thấy sản phẩm");
			request.getRequestDispatcher("/views/jsp/product-detail.jsp").forward(request, response);
			return;
		}
      // check id food
		int id;
		try {
			id = Integer.parseInt(idR);
		} catch (NumberFormatException e) {
			request.setAttribute("error", "ID sản phẩm không hợp lệ");
			request.getRequestDispatcher("/views/jsp/product-detail.jsp").forward(request, response);
			return;
		}
		Food food = dao.infomation(id);

		if (food == null) {
			request.setAttribute("error", "Sản phẩm không tồn tại");
		} else {
			request.setAttribute("food", food);
		}

		request.getRequestDispatcher("/views/jsp/product-detail.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

	}

}