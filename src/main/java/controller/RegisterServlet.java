package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Account.Role;

import java.io.IOException;

import DAO.AccountDAO;
import util.PasswordUtils;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/views/jsp/register.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String numberStr = request.getParameter("phone");
        String roleParam = request.getParameter("role");
        Role role;
        try {
            role = Role.valueOf(roleParam.toUpperCase());
        } catch (Exception e) {
            role = Role.USER;
        }

        // 1️⃣ Validate
        if (username == null || email == null || password == null ||
                username.isEmpty() || email.isEmpty() || password.isEmpty()) {

            request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin!");
            request.getRequestDispatcher("/views/jsp/register.jsp")
                    .forward(request, response);
            return;
        }


        int number = 0;
        try {
            number = Integer.parseInt(numberStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Số điện thoại không hợp lệ!");
            request.getRequestDispatcher("/views/jsp/register.jsp")
                    .forward(request, response);
            return;
        }

        // Validate password mạnh
        String passwordPattern =
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";

        if (!password.matches(passwordPattern)) {
            request.setAttribute("error",
                    "Mật khẩu phải có ít nhất 8 ký tự, gồm chữ hoa, chữ thường, số và ký tự đặc biệt!");

            request.getRequestDispatcher("/views/jsp/register.jsp")
                    .forward(request, response);
            return;
        }

        // 2️⃣ Tạo account
        Account acc = new Account();
        acc.setUserName(username);
        String hashedPassword = PasswordUtils.toMD5(password);
        acc.setPassword(hashedPassword);
        acc.setEmail(email);
        acc.setRole(role);

        // 3️⃣ Gọi DAO
        AccountDAO dao = new AccountDAO();

        if (dao.checkExitAccount(username)) {
            request.setAttribute("error", "Username đã tồn tại!");
            request.getRequestDispatcher("/views/jsp/register.jsp")
                    .forward(request, response);
            return;
        }

        dao.register(acc);

        //  Thành công → về login (DÙNG REDIRECT + SESSION)
        request.setAttribute("success", "Đăng ký thành công!");
        request.getRequestDispatcher("/views/jsp/register.jsp")
                .forward(request, response);
    }

}

