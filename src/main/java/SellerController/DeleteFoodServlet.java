package SellerController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import DAO.FoodDAOimpl;

import java.io.IOException;

@WebServlet("/seller/food/delete")
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