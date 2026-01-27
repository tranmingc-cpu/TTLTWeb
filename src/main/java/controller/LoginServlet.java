package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Account;
import model.Account.Role;

import java.io.IOException;

import DAO.AccountDAO;
import DAO.CartDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/views/jsp/login.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        AccountDAO accountDAO = new AccountDAO();
        CartDAO cartDAO = new CartDAO();

        Account acc = accountDAO.login(username, password);

        if (acc != null) {

            HttpSession session = request.getSession(true);

            // ===== LƯU USER INFO =====
            session.setAttribute("account", acc);

            // ===== ĐẢM BẢO USER CÓ CART =====
            cartDAO.getOrCreateCart(acc.getIdAccount());

            // ===== REDIRECT VỀ TRANG TRƯỚC ĐÓ =====
            String redirect = (String) session.getAttribute("redirectAfterLogin");

            if (redirect != null) {
                response.sendRedirect(request.getContextPath() + redirect);
                session.removeAttribute("redirectAfterLogin");
                return;
            }


            // FALLBACK, ktra role
            if (acc.getRole()==Role.ADMIN) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/Trangchu");
            }

        } else {
            request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu");
            request.getRequestDispatcher("/views/jsp/login.jsp")
                   .forward(request, response);
        }
}
}
