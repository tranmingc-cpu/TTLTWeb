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
        	 request.getRequestDispatcher("views/jsp/Trangchu.jsp").forward(request, response);
        	 break;
             // ================= TRANG CHỦ =================
        // case "list": {
            //   List<Food> gaList = dao.findByCategory("Chicken");
           //    List<Food> burgerList = dao.findByCategory("burger");
            //   List<Food> pizList = dao.findByCategory("pizza");
             //  List<Food> drinkList = dao.findByCategory("drink");
            //   List<Food>snackList = dao.findByCategory("snack");
               
            //   request.setAttribute("gaList", gaList);
             //  request.setAttribute("burgerList", burgerList);
             //  request.setAttribute("pizList", pizList);
             //  request.setAttribute("drinkList", drinkList);
             //  request.setAttribute("snackList", snackList);
             
        //   request.getRequestDispatcher("views/jsp/Trangchu.jsp").forward(request, response);
        //   break;
                
             }
         case "all": { // XEM TẤT CẢ
        	    List<Food> foodlist = dao.findALL();
        	    request.setAttribute("foodlist", foodlist);
        	    request.setAttribute("title", "Tất cả món ăn");
        	    request.getRequestDispatcher("views/jsp/Trangchu.jsp")
        	           .forward(request, response);
        	    break;
        	}
             // ================= CHI TIẾT =================
             case "detail": {
                 int id = Integer.parseInt(request.getParameter("id"));
                 Food food = dao.infomation(id);
                 request.setAttribute("food", food);
                 request.getRequestDispatcher("views/jsp/Trangchu.jsp")
                         .forward(request, response);
                 break;
             }

             // ================= SEARCH =================
             case "search": {
                 String keyword = request.getParameter("keyword");
                 List<Food> result = dao.findByName(keyword);
                 request.setAttribute("foodlist", result);
                 request.getRequestDispatcher("views/jsp/Trangchu.jsp")
                         .forward(request, response);
                 break;
             }

             // ================= CATEGORY =================
             case "category": {
                 String category = request.getParameter("category");
                 List<Food> foodlist = dao.findByCategory(category);
                 request.setAttribute("foodlist", foodlist);
                 request.setAttribute("title", category);
                 request.getRequestDispatcher("views/jsp/Trangchu.jsp")
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
