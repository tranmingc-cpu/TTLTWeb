package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Food;
import java.util.stream.Collectors;

import java.io.IOException;
import java.util.List;

import DAO.FoodDAOimpl;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String keyword = request.getParameter("keyword");
		String categoryRaw = request.getParameter("categoryId");
		String minRaw = request.getParameter("minPrice");
		String maxRaw = request.getParameter("maxPrice");

		Integer categoryId = (categoryRaw != null && !categoryRaw.isEmpty()) ? Integer.parseInt(categoryRaw) : null;
		Double minPrice = (minRaw != null && !minRaw.isEmpty()) ? Double.parseDouble(minRaw) : null;
		Double maxPrice = (maxRaw != null && !maxRaw.isEmpty()) ? Double.parseDouble(maxRaw) : null;
		FoodDAOimpl foodao = new FoodDAOimpl();

		List<Food> result = foodao.searchAdvanced(keyword, categoryId, minPrice, maxPrice);
		// gợi ý 5 tên liên quan
		List<Food> suggest = foodao.findByName(keyword)
				.stream()
				.limit(5)
				.collect(Collectors.toList());
		request.setAttribute("foodlist", result);

		request.setAttribute("suggestList", suggest);
		request.getRequestDispatcher("/views/jsp/Trangchu.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}