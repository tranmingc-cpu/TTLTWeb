package controller;

import DAO.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Order;
import service.GhnService;
import java.io.IOException;

@WebServlet("/cancelOrder")
public class CancelOrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        Account account = (Account) request.getSession().getAttribute("account");
        OrderDAO orderDAO = new OrderDAO();
        try {
            Order order = orderDAO.getOrderById(orderId);
            if(order == null || order.getAccountId() != account.getIdAccount()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            if("PENDING".equalsIgnoreCase(order.getStatus())
                    || "READY_TO_PICK".equalsIgnoreCase(order.getStatus())) {

                if(order.getGhnOrderCode() != null && !order.getGhnOrderCode().isBlank()) {

                    GhnService ghnService = new GhnService();
                    ghnService.cancelOrder(order.getGhnOrderCode());
                }

                orderDAO.updateStatus(orderId, "CANCELLED");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/orderHistory");
        request.getSession().setAttribute("successMessage", "Đơn hàng đã được hủy thành công!");
    }
}