package adminController;

import DAO.BannerDAO;
import DAO.FoodDAOimpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Banner;
import util.PermissionUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/banner")
public class AdminBannerServlet extends HttpServlet {
    private BannerDAO bannerDAO;
    private FoodDAOimpl foodDAO;
    @Override
    public void init() throws ServletException {
        this.bannerDAO = new BannerDAO();
        this.foodDAO = new FoodDAOimpl();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
                if (PermissionUtil.deny(request, request.getSession(), response, "VIEW_BANNER")) {
                    return;
                }
                showList(request, response);
                break;

            case "add":
                if (PermissionUtil.deny(request, request.getSession(), response, "ADD_BANNER")) {
                    return;
                }
                request.setAttribute("foods", foodDAO.getDisountFood());
                request.getRequestDispatcher("/admin/banner-add.jsp").forward(request, response);
                break;

            case "edit":
                if (PermissionUtil.deny(request, request.getSession(), response, "EDIT_BANNER")) {
                    return;
                }
                showEdit(request, response);
                break;

            case "delete":
                if (PermissionUtil.deny(request, request.getSession(), response, "DELETE_BANNER")) {
                    return;
                }
                delete(request, response);
                break;

            case "toggle":
                if (PermissionUtil.deny(request, request.getSession(), response, "EDIT_BANNER")) {
                    return;
                }
                toggleStatus(request, response);
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/admin/banner?action=list");
                break;
        }
    }


    private void showList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Banner> banners = bannerDAO.getAllBanners();
        if (banners == null) {
            banners = new ArrayList<>();
            request.setAttribute("errorMessage", "⚠️ Có lỗi xảy ra khi kết nối cơ sở dữ liệu. Vui lòng thử lại sau!");
        }
        request.setAttribute("banners", banners);
        request.getRequestDispatcher("/admin/banner.jsp").forward(request, response);
    }

    private void showEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int editId = Integer.parseInt(request.getParameter("id"));
            Banner existingBanner = bannerDAO.getBannerById(editId);
            request.setAttribute("banner", existingBanner);
            request.setAttribute("foods", foodDAO.getDisountFood());
            request.getRequestDispatcher("/admin/banner-edit.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/banner?action=list");
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int deleteId = Integer.parseInt(request.getParameter("id"));
            bannerDAO.deleteBanner(deleteId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/admin/banner?action=list");
    }

    private void toggleStatus(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int toggleId = Integer.parseInt(request.getParameter("id"));
            boolean currentStatus = Boolean.parseBoolean(request.getParameter("status"));
            bannerDAO.toggleStatus(toggleId, currentStatus);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/admin/banner?action=list");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("ID");

        if (idStr == null || idStr.isEmpty()) {
            if (PermissionUtil.deny(request, request.getSession(), response, "ADD_BANNER")) return;
        } else {
            if (PermissionUtil.deny(request, request.getSession(), response, "EDIT_BANNER")) return;
        }

        String title = request.getParameter("TITLE");
        String images = request.getParameter("IMAGES");
        int foodId = Integer.parseInt(request.getParameter("FOODID"));
        boolean status = request.getParameter("STATUS") != null;

        Banner banner = new Banner();
        banner.setTitle(title);
        banner.setImages(images);
        banner.setFoodId(foodId);
        banner.setStatus(status);

        if (idStr == null || idStr.isEmpty()) {
            bannerDAO.insertBanner(banner);
        } else {
            banner.setId(Integer.parseInt(idStr));
            bannerDAO.updateBanner(banner);
        }

        response.sendRedirect(request.getContextPath() + "/admin/banner?action=list");
    }
}
