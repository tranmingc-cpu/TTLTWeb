package SellerController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Food;
import model.Restaurant;

import java.io.IOException;
import java.util.List;

import DAO.FoodDAOimpl;
import DAO.SellerDAO;

/**
 * Servlet implementation class SellerServlet
 */
@WebServlet("/SellerServlet")
public class SellerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SellerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 Account acc = (Account)
	                request.getSession().getAttribute("account");

	        SellerDAO sDao= new SellerDAO();
	        Restaurant r = sDao.getByAccountId(acc.getIdAccount());

	        FoodDAOimpl fDao = new FoodDAOimpl();
	        List<Food> foods = fDao.getFoodsByRestaurant(r.getResId());

	        request.setAttribute("foods", foods);
	        request.getRequestDispatcher(
	            "/views/jsp/sellerfood.jsp"
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
