package adminController;

import DAO.CategoryDAO;
import DAO.FoodDAOimpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Food;

import java.io.IOException;
import java.nio.file.Paths;

@WebServlet("/admin/food/edit")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024
)
public class EditFoodServlet extends HttpServlet {

    FoodDAOimpl foodDAO = new FoodDAOimpl();
    CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Food food = foodDAO.getFoodbyFoodId(id);

        if (food == null) {
            response.sendRedirect(request.getContextPath() + "/admin/food");
            return;
        }

        request.setAttribute("food", food);
        request.setAttribute("categories", categoryDAO.findAll());

        request.getRequestDispatcher("/views/admin/editFood.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("ID RAW = " + request.getParameter("id"));

        request.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        // String description = request.getParameter("description");

        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        // int resId = Integer.parseInt(request.getParameter("resId"));

        String image = request.getParameter("oldImage");

        Part filePart = request.getPart("image");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName())
                    .getFileName().toString();
            String uploadDir = "D:/upload/food/";
            filePart.write(uploadDir + fileName);
            image = fileName;
        }

        Food food = new Food();
        food.setId(id);
        food.setName(name);
        food.setPrice(price);
        food.setCATEGORYId(categoryId);
        food.setImage(image);

        foodDAO.update(food);

        response.sendRedirect(request.getContextPath() + "/admin/food");
    }
}