package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Food;

import java.io.IOException;
import java.util.List;

import DAO.CategoryDAO;
import DAO.FoodDAOimpl;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/Trangchu")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FoodDAOimpl dao = new FoodDAOimpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 String action = request.getParameter("action");
         if (action == null) action = "list";

         switch (action) {
         case "list" :{
        	 List<Food> foodlist = dao.findLimit(10);
        	 request.setAttribute("foodlist", foodlist);
        	   request.setAttribute("title", "Món ăn nổi bật");
        	 request.getRequestDispatcher("/views/jsp/Trangchu.jsp").forward(request, response);
        	 break;

             }
         case "all": { // XEM TẤT CẢ
        	    List<Food> foodlist = dao.findALL();
        	    request.setAttribute("foodlist", foodlist);
        	    request.setAttribute("title", "Tất cả món ăn");
        	    request.getRequestDispatcher("/views/jsp/Trangchu.jsp")
        	           .forward(request, response);
        	    break;
        	}
             // ================= CHI TIẾT =================
             case "detail": {
                 int id = Integer.parseInt(request.getParameter("ID"));
                 Food food = dao.infomation(id);
                 request.setAttribute("food", food);
                 request.setAttribute("title", "Chi tiết"); 
                 request.getRequestDispatcher("/views/jsp/Trangchu.jsp")
                         .forward(request, response);
                 break;
             }

             // ================= SEARCH =================
             case "search": {
                 String keyword = request.getParameter("keyword");
                 List<Food> foodlist = dao.findByName(keyword);
                 request.setAttribute("foodlist", foodlist);
                 request.setAttribute("title", "Kết quả tìm kiếm");
                 request.getRequestDispatcher("/views/jsp/Trangchu.jsp")
                         .forward(request, response);
                 break;
             }

             // ================= CATEGORY =================
             case "category": {
                 int category = Integer.parseInt(request.getParameter("ID"));
                 List<Food> foodlist = dao.findByCategory(category);
                 CategoryDAO cdao = new CategoryDAO();
                 String categoryName = cdao.getNameById(category);
                 request.setAttribute("foodlist", foodlist);
                 request.setAttribute("title", categoryName); 
                 request.getRequestDispatcher("/views/jsp/Trangchu.jsp")
                         .forward(request, response);
                 
                 break;
             }

             default:
                 response.sendRedirect("Trangchu");
         }
     }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

        // THÊM MÓN
        
        if ("insert".equals(action)) {

            Food f = new Food();
            f.setName(request.getParameter("FNAME"));
            f.setPrice(Double.parseDouble(request.getParameter("PRICE")));
            f.setDescription(request.getParameter("DESCRIPTIONS"));
            f.setImage(request.getParameter("IMAGES"));
            f.setId(Integer.parseInt(request.getParameter("RESID")));

            dao.insert(f);
            response.sendRedirect("product-detail?action=list");
        }
        // CẬP NHẬT
      
        else if ("update".equals(action)) {

            Food f = new Food();
            f.setId(Integer.parseInt(request.getParameter("ID")));
            f.setName(request.getParameter("FNAME"));
            f.setPrice(Double.parseDouble(request.getParameter("PRICE")));
            f.setDescription(request.getParameter("DESCRIPTIONS"));
            f.setImage(request.getParameter("IMAGES"));

            dao.update(f);
            response.sendRedirect("product-detail?action=list");
        }
        // XÓA
    
        else if ("delete".equals(action)) {

            int id = Integer.parseInt(request.getParameter("ID"));
            int resid = Integer.parseInt(request.getParameter("RESID"));
			dao.delete(id, resid);
            response.sendRedirect("product-detail?action=list");
        }
    }

}
