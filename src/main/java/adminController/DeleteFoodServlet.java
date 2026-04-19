package adminController;

import DAO.FoodDAOimpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/food/delete")
public class DeleteFoodServlet extends HttpServlet {

    FoodDAOimpl foodDAO = new FoodDAOimpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idRaw = request.getParameter("id");

        if (idRaw == null || idRaw.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/seller/food");
            return;
        }

        int id = Integer.parseInt(idRaw);

        foodDAO.deleteFood(id);

        response.sendRedirect(request.getContextPath() + "/seller/food");
    }
}