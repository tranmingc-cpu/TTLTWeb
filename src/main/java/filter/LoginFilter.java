package filter;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

@WebFilter(urlPatterns = { "/cart", "/profile", "/order", "/checkout" })
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        //  LẤY SESSION (KHÔNG TẠO MỚI)
        HttpSession session = request.getSession(false);

        boolean loggedIn = (session != null && session.getAttribute("account") != null);

        if (!loggedIn) {
            //  LƯU URL TRƯỚC KHI LOGIN (CÓ CONTEXT PATH)
            String contextPath = request.getContextPath();
            String uri = request.getRequestURI();      // /FoodWeb/cart
            String query = request.getQueryString();   // check id food

            String fullUrl = uri + (query != null ? "?" + query : "");

            //  TẠO SESSION ĐỂ LƯU REDIRECT
            request.getSession(true)
                   .setAttribute("redirectAfterLogin", fullUrl);

            response.sendRedirect(contextPath + "/login");
            return;
        }

        //  ĐÃ LOGIN → CHO ĐI TIẾP
        chain.doFilter(req, res);
    }
}
