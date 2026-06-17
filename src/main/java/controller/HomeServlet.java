package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Food;

import java.io.IOException;
import java.math.BigDecimal;
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
    private static final int PAGE_SIZE = 10 ;
    private int getPage(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            return 1;
        }
    }
    private List<Food> paginate(List<Food> list, int page) {
        int start = (page - 1) * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, list.size());

        if (start >= list.size()) {
            start = 0;
            end = Math.min(PAGE_SIZE, list.size());
        }

        return list.subList(start, end);
    }

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
                int page = getPage(request);
                List<Food> allFoods = dao.findLimit(10);
                int totalPages = (int) Math.ceil((double) allFoods.size() / PAGE_SIZE);
                List<Food> foodlist = paginate(allFoods, page);
                request.setAttribute("foodlist", foodlist);
                request.setAttribute("title", "Món ăn nổi bật");
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.getRequestDispatcher("/views/jsp/Trangchu.jsp").forward(request, response);

                break;
            }
            case "all": {
                int page = getPage(request);
                List<Food> allFoods = dao.findALL();
                int totalPages = (int) Math.ceil((double) allFoods.size() / PAGE_SIZE);
                List<Food> foodlist = paginate(allFoods, page);
                request.setAttribute("foodlist", foodlist);
                request.setAttribute("title", "Tất cả món ăn");
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.getRequestDispatcher("/views/jsp/Trangchu.jsp").forward(request, response);

                break;
            }
            case "detail": {
                int id = Integer.parseInt(request.getParameter("ID"));
                Food food = dao.infomation(id);
                request.setAttribute("food", food);
                request.setAttribute("title", "Chi tiết");
                request.getRequestDispatcher("/views/jsp/Trangchu.jsp")
                        .forward(request, response);
                break;
            }

            case "search": {
                String keyword = request.getParameter("keyword");
                List<Food> foodlist = dao.findByName(keyword);
                request.setAttribute("foodlist", foodlist);
                request.setAttribute("title", "Kết quả tìm kiếm");
                request.getRequestDispatcher("/views/jsp/Trangchu.jsp")
                        .forward(request, response);
                break;
            }

            case "category": {

                int category = Integer.parseInt(request.getParameter("ID"));
                int page = getPage(request);
                List<Food> allFoods = dao.findByCategory(category);
                int totalPages = (int) Math.ceil((double) allFoods.size() / PAGE_SIZE);
                List<Food> foodlist = paginate(allFoods, page);
                CategoryDAO cdao = new CategoryDAO();
                String categoryName = cdao.getNameById(category);
                request.setAttribute("foodlist", foodlist);
                request.setAttribute("title", categoryName);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);

                request.getRequestDispatcher("/views/jsp/Trangchu.jsp").forward(request, response);

                break;
            }
            default: response.sendRedirect("Trangchu");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("insert".equals(action)) {

            Food f = new Food();
            f.setName(request.getParameter("FNAME"));
            f.setPrice(new BigDecimal(request.getParameter("PRICE")));
            f.setDescription(request.getParameter("DESCRIPTIONS"));
            f.setImage(request.getParameter("IMAGES"));
            f.setId(Integer.parseInt(request.getParameter("RESID")));

            dao.insertFood(f);
            response.sendRedirect("product-detail?action=list");
        }


        else if ("update".equals(action)) {

            Food f = new Food();
            f.setName(request.getParameter("FNAME"));
            f.setPrice(new BigDecimal(request.getParameter("PRICE")));
            f.setDescription(request.getParameter("DESCRIPTIONS"));
            f.setImage(request.getParameter("IMAGES"));

            dao.update(f);
            response.sendRedirect("product-detail?action=list");
        }

        else if ("delete".equals(action)) {

            int id = Integer.parseInt(request.getParameter("ID"));
            int resid = Integer.parseInt(request.getParameter("RESID"));
            dao.delete(id, resid);
            response.sendRedirect("product-detail?action=list");
        }
    }

}