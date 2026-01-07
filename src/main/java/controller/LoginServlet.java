package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Account;
import model.Cart;

import java.io.IOException;

import DAO.AccountDAO;
import DAO.CartDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("views/jsp/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        AccountDAO accountDAO = new AccountDAO();
        CartDAO cartDAO = new CartDAO();

        Account acc = accountDAO.login(username, password);

        if (acc != null) {
            // ===== ĐĂNG NHẬP THÀNH CÔNG =====
            HttpSession session = request.getSession();

            // Lưu user
            session.setAttribute("account", acc);
            session.setAttribute("userId", acc.getIdAccount());
            session.setAttribute("user", acc.getUserName());
            session.setAttribute("role", acc.getRole());

            // ===== LẤY / TẠO CART THEO USER =====
            int cart = cartDAO.getCartId(acc.getIdAccount());

            if (cart == 0) {
               int cart1 = cartDAO.createCartIfNotExists(acc.getIdAccount());
               session.setAttribute("cartId", cart1);
            }

            session.setAttribute("cart", cart);

            // ===== ĐIỀU HƯỚNG =====
            if ("ADMIN".equals(acc.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/Trangchu");
            }

        } else {
            request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu");
            request.getRequestDispatcher("/views/jsp/login.jsp").forward(request, response);
        }
    }
}
