package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;

import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

/**
 * Servlet implementation class cartFilter
 */
@WebServlet("/cartFilter")
public class cartFilter implements Filter {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public cartFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doFilter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpServletRequest req = (HttpServletRequest) request;
	        HttpSession session = req.getSession(false);

	        int cartCount = 0;

	        if (session != null) {
	            Cart cart = (Cart) session.getAttribute("cart");
	            if (cart != null) {
	                cartCount = cart.getTotailQuantity();
	            }
	        }

	        request.setAttribute("cartCount", cartCount);

	       // chain.doFilter(request, response);
	    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */


	@Override
	public boolean isLoggable(LogRecord record) {
		// TODO Auto-generated method stub
		return false;
	}

}
