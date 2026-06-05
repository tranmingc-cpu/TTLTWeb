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
import util.PasswordUtils;
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
        // LOGIN THẤT BẠI
        if (acc == null) {
            request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu");
            request.getRequestDispatcher("/views/jsp/login.jsp")
                    .forward(request, response);
            return;
        }
        // Tài khoản bị khóa
        if (acc.getStatus() == 0) {
            request.setAttribute("error", "Tài khoản của bạn đã bị khóa!");
            request.getRequestDispatcher("/views/jsp/login.jsp")
                    .forward(request, response);
            return;
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("account", acc);
        cartDAO.getOrCreateCart(acc.getIdAccount());
        String redirect =
                (String) session.getAttribute("redirectAfterLogin");
        if (redirect != null) {
            session.removeAttribute("redirectAfterLogin");
            response.sendRedirect(
                    request.getContextPath() + redirect);
            return;
        }
        if (acc.getRole() == Role.ADMIN) {
            response.sendRedirect(
                    request.getContextPath() + "/admin/dashboard");
            return;
        }
        if (acc.getRole() == Role.SELLER) {
            response.sendRedirect(
                    request.getContextPath() + "/seller/food");
            return;
        }
        if (acc.getRole() == Role.USER) {
            response.sendRedirect(
                    request.getContextPath() + "/Trangchu");
            return;
        }
    }
}