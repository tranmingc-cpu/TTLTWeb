package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;

import java.io.IOException;

import DAO.AccountDAO;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // CHỈ hiển thị trang register
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
        String numberStr = request.getParameter("number");

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

        // 2️⃣ Tạo account
        Account acc = new Account();
        acc.setUserName(username);
        acc.setPassword(password); // đồ án ok, nâng cao thì hash
        acc.setEmail(email);
        acc.setNumber(number);
        acc.setAddress(address);
        acc.setRole(false);

        // 3️⃣ Gọi DAO
        AccountDAO dao = new AccountDAO();

        if (dao.checkExitAccount(username)) {
            request.setAttribute("error", "Username đã tồn tại!");
            request.getRequestDispatcher("/views/jsp/register.jsp")
                   .forward(request, response);
            return;
        }

        dao.register(acc);

        //  Thành công → về login
        request.setAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
        request.getRequestDispatcher("/views/jsp/login.jsp")
               .forward(request, response);
    }
}


