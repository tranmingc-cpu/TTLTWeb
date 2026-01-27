package SellerController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Food;
import model.Restaurant;
import model.Account;
import model.Category;
import DAO.CategoryDAO;
import DAO.FoodDAOimpl;
import DAO.SellerDAO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/seller/food/add")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 5,
    maxRequestSize = 1024 * 1024 * 10
)
public class AddFoodServlet extends HttpServlet {

    private FoodDAOimpl foodDAO = new FoodDAOimpl();

    // ===== SHOW FORM =====
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	CategoryDAO categoryDAO = new CategoryDAO();
    	List<Category> categories = categoryDAO.findAll();

    	request.setAttribute("categories", categories);
    	request.getRequestDispatcher("/views/seller/addFood.jsp").forward(request, response);

     
    }
  @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
	request.getParameterMap().forEach((k, v) -> {
	    System.out.println(k + " = " + String.join(",", v));
	});
        request.setCharacterEncoding("UTF-8");

        // ===== ACCOUNT =====
        Account acc = (Account) request.getSession().getAttribute("account");
        if (acc == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // ===== RESTAURANT =====
        SellerDAO sellerDAO = new SellerDAO();
        Restaurant restaurant = sellerDAO.getByAccountId(acc.getIdAccount());
        if (restaurant == null) {
            response.sendRedirect(request.getContextPath() + "/seller/food");
            return;
        }

        int restaurantId = restaurant.getResId();

        // ===== FORM DATA =====
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        String description = request.getParameter("description");
        String idRaw = request.getParameter("id");
        String categoryRaw = request.getParameter("categoryId");

        if (idRaw == null || idRaw.isEmpty()
                || categoryRaw == null || categoryRaw.isEmpty()) {
               System.out.println("raw"+categoryRaw);
               response.sendRedirect(request.getContextPath() + "/seller/food/add");
               return;

        }

        int foodId = Integer.parseInt(idRaw);
        System.out.println("food id"+foodId);
        int categoryId = Integer.parseInt(categoryRaw);
System.out.println("category id"+categoryId);
        // ===== UPLOAD IMAGE =====
        Part filePart = request.getPart("image");
        String fileName = null;

        if (filePart != null && filePart.getSize() > 0) {
            fileName = Paths.get(filePart.getSubmittedFileName())
                            .getFileName().toString();

            String uploadPath = "D:/upload/food/";
            File dir = new File(uploadPath);
            if (!dir.exists()) dir.mkdirs();

            filePart.write(uploadPath + fileName);
        }
      
     
        // ===== SET FOOD =====
        Food food = new Food();
        food.setId(foodId);
        food.setName(name);
        food.setPrice(price);
        food.setDescription(description);
        food.setImage(fileName);
        food.setResID(restaurantId);
        food.setCATEGORYId(categoryId); 

        // ===== INSERT =====
        foodDAO.insert(food);

        // ===== BACK TO LIST =====
        response.sendRedirect(request.getContextPath() + "/seller/food");
    }
}