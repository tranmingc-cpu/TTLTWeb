package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import DAO.AccountDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import model.Account;
import util.EmailUtil;

import java.util.Random;

import java.io.IOException;

@WebServlet(name = "ForgotPasswordServlet", value = "/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/views/jsp/forgot-password.jsp")
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");

        AccountDAO dao = new AccountDAO();
        Account acc = dao.findByEmail(email);

        if (acc == null) {
            request.setAttribute("error", "Email không tồn tại!");
            request.getRequestDispatcher("/views/jsp/forgot-password.jsp")
                    .forward(request, response);
            return;
        }

        // tạo OTP
        String otp = String.format("%06d",
                new Random().nextInt(999999));

        HttpSession session = request.getSession();

        session.setAttribute("otp", otp);
        session.setAttribute("resetEmail", email);

        // gửi mail
        String subject = "Mã OTP đặt lại mật khẩu";

        String content =
                "<h3>Mã OTP của bạn là: " + otp + "</h3>";

        EmailUtil.send(email, subject, content);

        response.sendRedirect("reset-password");

    }
}