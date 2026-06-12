package adminController;

import DAO.CategoryDAO;
import DAO.FoodDAOimpl;
import DAO.SellerDAO;
import Upload.CloudinaryConfig;
import com.cloudinary.Cloudinary;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Account;
import model.Category;
import model.Food;
import model.Restaurant;
import util.PermissionUtil;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/food/add")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10
)
public class AddFoodServlet extends HttpServlet {

    private FoodDAOimpl foodDAO = new FoodDAOimpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (PermissionUtil.deny(request, request.getSession(), response, "ADD_PRODUCT")) {
            return;
        }
        String searchRes = request.getParameter("searchRes");
        String searchCat = request.getParameter("searchCat");
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> categories = categoryDAO.findAll();
        if (searchCat != null && !searchCat.isBlank()) {
            String keyword = searchCat.toLowerCase().trim();
            categories = categories.stream()
                    .filter(c -> c.getName().toLowerCase().contains(keyword))
                    .collect(Collectors.toList()); // Đổi chỗ này
        }
        SellerDAO sellerDAO = new SellerDAO();
        List<Restaurant> restaurants = sellerDAO.getAll();
        if (searchRes != null && !searchRes.isBlank()) {
            String keyword = searchRes.toLowerCase().trim();
            restaurants = restaurants.stream()
                    .filter(r -> r.getName().toLowerCase().contains(keyword))
                    .collect(Collectors.toList()); // Đổi chỗ này
        }

        request.setAttribute("categories", categories);
        request.setAttribute("restaurants", restaurants);
        request.setAttribute("searchRes", searchRes);
        request.setAttribute("searchCat", searchCat);

        request.getRequestDispatcher("/views/admin/addFood.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (PermissionUtil.deny(request, request.getSession(), response, "ADD_PRODUCT")) {
            return;
        }
        request.setCharacterEncoding("UTF-8");
        Account acc = (Account) request.getSession().getAttribute("account");
        if (acc == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String resIdRaw = request.getParameter("restaurantId");
        String categoryRaw = request.getParameter("categoryId");
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        String description = request.getParameter("description");
        String quantityRaw = request.getParameter("quantity");

        if (resIdRaw == null || resIdRaw.isEmpty()
                || categoryRaw == null || categoryRaw.isEmpty()
                || priceStr == null || priceStr.isEmpty()
                || quantityRaw == null || quantityRaw.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/food/add");
            return;
        }

        int restaurantId = Integer.parseInt(resIdRaw);
        int categoryId   = Integer.parseInt(categoryRaw);
        BigDecimal price = new BigDecimal(priceStr);
        BigDecimal quantity  = new BigDecimal(quantityRaw);

        Part filePart = request.getPart("image");
        String imageUrl = null;

        if (filePart != null && filePart.getSize() > 0) {
            try (InputStream is = filePart.getInputStream()) {

                Map<String, Object> options = new HashMap<>();
                options.put("folder", "food");

                Cloudinary cloudinary = CloudinaryConfig.getInstance();
                Map uploadResult = cloudinary.uploader().upload(is.readAllBytes(), options);
                imageUrl = (String) uploadResult.get("secure_url");
                System.out.println(" Upload Cloudinary success: " + imageUrl);

            } catch (Exception e) {
                e.printStackTrace();
                imageUrl = null;
            }
        }

        Food food = new Food();
        food.setName(name);
        food.setPrice(price);
        food.setDescription(description);
        food.setImage(imageUrl);
        food.setResID(restaurantId);
        food.setCATEGORYId(categoryId);
        food.setQuantity(quantity);

        foodDAO.insertFood(food);

        response.sendRedirect(request.getContextPath() + "/admin/product");
    }
}