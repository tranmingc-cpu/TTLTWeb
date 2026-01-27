package SellerController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Order;
import model.Restaurant;

import java.io.IOException;
import java.util.List;

import DAO.FoodDAOimpl;
import DAO.OrderDAO;
import DAO.SellerDAO;

/**
 * Servlet implementation class SellerDashBoardServlet
 */
@WebServlet("/SellerDashBoardServlet")
public class SellerDashBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SellerDashBoardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Account acc = (Account)
	                request.getSession().getAttribute("account");

	        SellerDAO rDao = new SellerDAO();
	        OrderDAO oDao = new OrderDAO();
	        FoodDAOimpl fDao = new FoodDAOimpl();

	        // 1. Restaurant của seller
	        Restaurant r =
	                rDao.getByAccountId(acc.getIdAccount());

	        // 2. Thống kê
	        int totalFoods =
	                fDao.countFoodByRestaurant(r.getResId());

	        int todayOrders =
	                oDao.countTodayOrders(r.getResId());

	        int todayRevenue =
	                oDao.sumTodayRevenue(r.getResId());

	        // 3. Đơn hàng gần đây
	        List<Order> recentOrders =
	                oDao.getRecentOrdersByRestaurant(r.getResId());

	        // set attribute
	        request.setAttribute("restaurant", r);
	        request.setAttribute("totalFoods", totalFoods);
	        request.setAttribute("todayOrders", todayOrders);
	        request.setAttribute("todayRevenue", todayRevenue);
	        request.setAttribute("recentOrders", recentOrders);

	        request.getRequestDispatcher(
	            "/views/jsp/seller/dashboard.jsp"
	        ).forward(request, response);
	    }
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
