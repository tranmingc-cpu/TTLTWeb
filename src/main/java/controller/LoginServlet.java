package controller;

import DAO.AdminPermissionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Account;
import model.Account.Role;

import java.io.IOException;

import model.AdminPermission;
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
        AdminPermissionDAO permissionDAO = new AdminPermissionDAO();

        Account acc = accountDAO.login(username, password);

        // Login thất bại
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

        // Nếu là ADMIN thì load quyền
        if (acc.getRole() == Role.ADMIN|| acc.getRole()== Role.SUPER_ADMIN) {

            AdminPermission permission = permissionDAO.getByAccountId(acc.getIdAccount());

            // Trường hợp chưa có bản ghi phân quyền
            if (permission == null) {
                permissionDAO.insert(acc.getIdAccount());
                permission = permissionDAO.getByAccountId(acc.getIdAccount());
            }

            session.setAttribute("permission", permission);
        }

        cartDAO.getOrCreateCart(acc.getIdAccount());

        String redirect = (String) session.getAttribute("redirectAfterLogin");

        if (redirect != null) {
            session.removeAttribute("redirectAfterLogin");
            response.sendRedirect(request.getContextPath() + redirect);
            return;
        }

        if (acc.getRole() == Role.ADMIN || acc.getRole() == Role.SUPER_ADMIN) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/Trangchu");
    }
}