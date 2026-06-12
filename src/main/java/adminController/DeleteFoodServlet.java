package adminController;

import DAO.FoodDAOimpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.PermissionUtil;

import java.io.IOException;

@WebServlet("/admin/food/delete")
public class DeleteFoodServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    FoodDAOimpl foodDAO = new FoodDAOimpl();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        if (PermissionUtil.deny(
                request,
                request.getSession(),
                response,
                "DELETE_PRODUCT")) {
            return;
        }

        String idRaw = request.getParameter("id");

        if (idRaw == null || idRaw.isEmpty()) {
            response.sendRedirect(
                    request.getContextPath() + "/admin/product");
            return;
        }

        int id = Integer.parseInt(idRaw);

        foodDAO.deleteFood(id);

        response.sendRedirect(
                request.getContextPath() + "/admin/product");
    }
}