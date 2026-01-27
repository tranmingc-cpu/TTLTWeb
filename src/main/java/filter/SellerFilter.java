package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Account.Role;

/**
 * Servlet implementation class SellerFilter
 */
@WebFilter("/seller/*")
public class SellerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        Account acc = null;

        if (session != null) {
            acc = (Account) session.getAttribute("account");
        }

        // Chưa login hoặc không phải SELLER chặn
        if (acc == null || acc.getRole() != Role.SELLER) {
            res.sendRedirect(req.getContextPath() + "/Trangchu");
            return; 
        }
        //  Đúng SELLER  cho đi tiếp
        chain.doFilter(request, response);
    }
}
