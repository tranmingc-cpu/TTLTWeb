package adminController;

import DAO.BannerDAO;
import DAO.FoodDAOimpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Banner;

import java.io.IOException;

@WebServlet("/admin/banner")
public class AdminBannerServlet extends HttpServlet {
    private BannerDAO bannerDAO;
    private FoodDAOimpl foodDAO;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
                request.setAttribute("banners", bannerDAO.getAllBanners());
                request.getRequestDispatcher("/admin/banner-list.jsp").forward(request, response);
                response.sendRedirect(request.getContextPath() + "/admin/banner?action=list");
                break;
        }

        }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect(request.getContextPath() + "/admin/banner?action=list");
    }
}


