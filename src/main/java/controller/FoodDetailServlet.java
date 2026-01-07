package controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Food;

import java.io.IOException;
import java.util.List;

import DAO.FoodDAOimpl;

/**
 * Servlet implementation class FoodDetailServlet
 */
@WebServlet("/product")
public class FoodDetailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
private FoodDAOimpl dao = new FoodDAOimpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FoodDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(action==null) {
			action ="list";
		}
		switch(action) {
		case "list":
			  List<Food> list = dao.findALL();
              request.setAttribute("foodlist", list);
              request.getRequestDispatcher("views/jsp/food-list.jsp")
                      .forward(request, response);
              break;
		case "detail":
            int id = Integer.parseInt(request.getParameter("id"));
            Food food = dao.infomation(id);
            request.setAttribute("food", food);
            request.getRequestDispatcher("views/jsp/food-detail.jsp")
                    .forward(request, response);
            break;

       
        // TÌM THEO TÊN
      
        case "search":
            String keyword = request.getParameter("keyword");
            List<Food> result = dao.findByName(keyword);
            request.setAttribute("foodlist", result);
            request.getRequestDispatcher("views/jsp/food-list.jsp")
                    .forward(request, response);
            break;

     
        // TÌM THEO CATEGORY
      
        case "category":
            String category = request.getParameter("category");
            List<Food> byCate = dao.findByCategory(category);
            request.setAttribute("foodlist", byCate);
            request.getRequestDispatcher("views/jsp/food-detail.jsp")
                    .forward(request, response);
            break;

        default:
            response.sendRedirect("product-detail?action=list");
    }
		}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

      
        // THÊM MÓN
        
        if ("insert".equals(action)) {

            Food f = new Food();
            f.setName(request.getParameter("name"));
            f.setPrice(Double.parseDouble(request.getParameter("price")));
            f.setDescription(request.getParameter("description"));
            f.setImage(request.getParameter("image"));

            dao.insert(f);
            response.sendRedirect("product-detail?action=list");
        }

      
        // CẬP NHẬT
      
        else if ("update".equals(action)) {

            Food f = new Food();
            f.setId(Integer.parseInt(request.getParameter("id")));
            f.setName(request.getParameter("name"));
            f.setPrice(Double.parseDouble(request.getParameter("price")));
            f.setDescription(request.getParameter("description"));
            f.setImage(request.getParameter("image"));

            dao.update(f);
            response.sendRedirect("product-detail?action=list");
        }

      
        // XÓA
    
        else if ("delete".equals(action)) {

            int id = Integer.parseInt(request.getParameter("id"));
            dao.delete(id);
            response.sendRedirect("product-detail?action=list");
        }
    }

}
