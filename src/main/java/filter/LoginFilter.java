package filter;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String contextPath = request.getContextPath();
        String uri = request.getRequestURI().substring(contextPath.length());

        boolean isPublic =
                uri.equals("/login") ||
                uri.equals("/register") ||
                uri.equals("/product-detail") ||
                uri.equals("/Trangchu") ||
                uri.startsWith("/views/") ||
                uri.startsWith("/images/") ||
                uri.startsWith("/css/") ;

        HttpSession session = request.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("account") != null);

        if (!loggedIn && !isPublic) {

            String query = request.getQueryString();
            String fullUrl = uri + (query != null ? "?" + query : "");

            request.getSession(true)
                   .setAttribute("redirectAfterLogin", fullUrl);

            response.sendRedirect(contextPath + "/login");
            return;
        }

        chain.doFilter(req, res);
    }
}
