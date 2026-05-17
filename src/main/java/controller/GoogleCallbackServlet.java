package controller;

import DAO.AccountDAO;
import DAO.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Account;
import model.User;

import java.io.IOException;

@WebServlet(name = "GoogleCallbackServlet", value = "/GoogleCallbackServlet")
public class GoogleCallbackServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String email = req.getParameter("email");
        String name = req.getParameter("name");

        // ⚠️ tạm fake (vì bạn chưa gọi API Google thật)

        AccountDAO accDAO = new AccountDAO();
        UserDAO userDAO = new UserDAO();

        Account acc = accDAO.findByEmail(email);

        if (acc == null) {

            acc = new Account();
            acc.setEmail(email);
            acc.setRole(Account.Role.USER);

            int accId = accDAO.insertGoogle(acc);
            acc.setIdAccount(accId);

            // tạo profile
            User u = new User();
            u.setId(accId); // dùng chung ID
            u.setAccid(accId);
            u.setFullname(name);
            u.setEmail(email);

            userDAO.insertProfile(u);
        }

        req.getSession().setAttribute("account", acc);
        resp.sendRedirect("Trangchu");
    }
}