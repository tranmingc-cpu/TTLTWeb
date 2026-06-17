package controller;

import model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

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
	 * response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String idR = request.getParameter("id");

		if (idR == null) {
			request.setAttribute("error", "Không tìm thấy sản phẩm");
			request.getRequestDispatcher("/views/jsp/product-detail.jsp").forward(request, response);
			return;
		}
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

			BigDecimal oldPrice = food.getPrice();
			int discount = food.getDiscount();
			BigDecimal newPrice = oldPrice;
			if (discount > 0) {
				BigDecimal discountPercent = new BigDecimal(discount);
				BigDecimal oneHundred = new BigDecimal(100);
				newPrice = oldPrice.subtract(oldPrice.multiply(new java.math.BigDecimal(discount)).divide(new java.math.BigDecimal(100), 2, java.math.RoundingMode.HALF_UP));			}
               System.out.println(" old price"+ oldPrice);
			System.out.println(" old price"+ oldPrice);
			System.out.println(" discount "+ food.getDiscount());

			request.setAttribute("oldPrice", oldPrice);
			request.setAttribute("discount", discount);
			request.setAttribute("newPrice", newPrice);

			List<Food> relatedFoods = dao.findByCategory(food.getCATEGORYId());
			relatedFoods.removeIf(f -> f.getId() == food.getId());

			int page = 1;

			String pageStr = request.getParameter("page");

			if (pageStr != null) {
				try {
					page = Integer.parseInt(pageStr);
				} catch (Exception e) {
					page = 1;
				}
			}

			int pageSize = 10;
			int totalItems = relatedFoods.size();
			int totalPages = (int) Math.ceil((double) totalItems / pageSize);
			if(page < 1){
				page = 1;
			}

			if(page > totalPages && totalPages > 0){
				page = totalPages;
			}

			int start = (page - 1) * pageSize;

			int end = Math.min(start + pageSize, totalItems);

			List<Food> pageFoods = relatedFoods.subList(start, end);

			request.setAttribute("relatedFoods", pageFoods);
			request.setAttribute("currentPage", page);
			request.setAttribute("totalPages", totalPages);
		}

		request.getRequestDispatcher("/views/jsp/product-detail.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 * response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}
}