package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Order;
import model.User;
import util.EmailUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import DAO.OrderDAO;
import DAO.UserDAO;

@WebServlet("/orderSuccess")
public class OrderSuccessServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        Object sessionOrderIds = session.getAttribute("orderIds");
        List<Integer> orderIds = new ArrayList<>();

        if (sessionOrderIds instanceof List<?>) {
            for (Object obj : (List<?>) sessionOrderIds) {
                if (obj instanceof Integer) {
                    orderIds.add((Integer) obj);
                }
            }
        }

        if (orderIds.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orders = orderDAO.getOrdersByIds(orderIds);

        request.setAttribute("orders", orders);

        session.removeAttribute("orderIds");

        request.getRequestDispatcher("/views/jsp/orderSuccess.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}