package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.User;

import java.io.IOException;

import DAO.AccountDAO;
import DAO.UserDAO;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        // ktra account
        Object obj = session.getAttribute("account");
        if (!(obj instanceof Account)) {
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Account acc = (Account) obj;

        UserDAO userDAO = new UserDAO();
        User profile = userDAO.getProfileByAccId(acc.getIdAccount());

        if (profile == null) {
            request.setAttribute("error", "Chưa có thông tin hồ sơ người dùng");
        } else {
            request.setAttribute("profile", profile);
        }

        request.getRequestDispatcher("/views/jsp/profile.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || !(session.getAttribute("account") instanceof Account)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Account acc = (Account) session.getAttribute("account");

        request.setCharacterEncoding("UTF-8");

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String number = request.getParameter("number");
        String address = request.getParameter("address");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        AccountDAO accountDAO = new AccountDAO();

        User profile = userDAO.getProfileByAccId(acc.getIdAccount());

        // Chưa có profile thì tạo mới
        if (profile == null) {

            profile = new User();
            profile.setAccid(acc.getIdAccount());
            profile.setFullname(fullName);
            profile.setNumber(number);
            profile.setAddress(address);

            userDAO.insertProfile(profile);

        }
        // Đã có thì cập nhật
        else {

            profile.setFullname(fullName);
            profile.setNumber(number);
            profile.setAddress(address);

            userDAO.updateProfile(profile);
        }

        // Email lưu ở bảng Account
        accountDAO.updateEmail(acc.getIdAccount(), email);

        // Đổi mật khẩu nếu có nhập
        if (password != null && !password.trim().isEmpty()) {

            accountDAO.updatePassword(
                    acc.getIdAccount(),
                    util.PasswordUtils.toMD5(password)
            );

            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/profile");
    }
}