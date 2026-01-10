package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Account.Role;

/**
 * Servlet implementation class SellerFilter
 */
@WebServlet("/restaurant/*")
	public class SellerFilter implements Filter {
	    @Override
	    public void doFilter(ServletRequest request, ServletResponse response,
	                         FilterChain chain)
	            throws IOException, ServletException {

	        HttpServletRequest req = (HttpServletRequest) request;
	        HttpServletResponse res = (HttpServletResponse) response;

	        HttpSession session = req.getSession(false);

	        if (session == null || session.getAttribute("account") == null) {
	            res.sendRedirect(req.getContextPath() + "/login");
	            return;
	        }

	        Account acc = (Account) session.getAttribute("account");

	        if (acc.getRole()==Role.SELLER) {
	            res.sendRedirect(req.getContextPath() + "/Trangchu");
	            return;
	        }

	        chain.doFilter(request, response);
	    }
	}