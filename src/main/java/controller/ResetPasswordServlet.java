package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import DAO.AccountDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import model.Account;

import java.io.IOException;

@WebServlet(name = "ResetPasswordServlet", value = "/reset-password")
public class ResetPasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/views/jsp/reset-password.jsp")
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String otpInput = request.getParameter("otp");
        String newPassword = request.getParameter("password");

        HttpSession session = request.getSession();

        String otpSession = (String) session.getAttribute("otp");
        String email = (String) session.getAttribute("resetEmail");

        if (otpSession == null || email == null) {
            response.sendRedirect("forgot-password");
            return;
        }

        if (!otpSession.equals(otpInput)) {

            request.setAttribute("error", "OTP không đúng!");

            request.getRequestDispatcher("/views/jsp/reset-password.jsp")
                    .forward(request, response);
            return;
        }

        AccountDAO dao = new AccountDAO();

        Account acc = dao.findByEmail(email);

        if (acc != null) {
            dao.updatePassword(acc.getIdAccount(), newPassword);
        }

        session.removeAttribute("otp");
        session.removeAttribute("resetEmail");

        request.setAttribute("success",
                "Đổi mật khẩu thành công!");

        request.getRequestDispatcher("/views/jsp/login.jsp")
                .forward(request, response);

    }
}