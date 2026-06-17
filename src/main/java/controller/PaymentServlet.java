package controller;

import DAO.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Order;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Account acc = (Account) session.getAttribute("account");
        String orderIdsStr = request.getParameter("orderIds");
        if (orderIdsStr == null || orderIdsStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/orderHistory");
            return;
        }

        String[] orderIdArray = orderIdsStr.split(",");
        List<Order> orders = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        int firstOrderId = 0;

        for (String idStr : orderIdArray) {
            try {
                int orderId = Integer.parseInt(idStr.trim());
                Order o = orderDAO.getOrderById(orderId);
                if (o != null && o.getAccountId() == acc.getIdAccount()) {
                    orders.add(o);
                    totalAmount = totalAmount.add(o.getTotalAmount());
                    if (firstOrderId == 0) {
                        firstOrderId = orderId;
                    }
                }
            } catch (NumberFormatException e) {
            }
        }

        if (orders.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/orderHistory");
            return;
        }

        String bank = "MBBank";
        String accountNo = "0364964685";
        String accountName = "TRAN NHAT MINH ";
        String mem = "DH SỐ " + firstOrderId + " CỦA BẠN ";
        
        String qrUrl = String.format("https://qr.sepay.vn/img?acc=%s&bank=%s&amount=%.0f&mem=%s",
                accountNo, bank, totalAmount, mem);

        request.setAttribute("orders", orders);
        request.setAttribute("totalAmount", totalAmount);
        request.setAttribute("qrUrl", qrUrl);
        request.setAttribute("bank", bank);
        request.setAttribute("accountNo", accountNo);
        request.setAttribute("accountName", accountName);
        request.setAttribute("mem", mem);
        request.setAttribute("orderIdsStr", orderIdsStr);

        request.getRequestDispatcher("/views/jsp/payment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String orderIdsStr = request.getParameter("orderIdsStr");
        if (orderIdsStr != null && !orderIdsStr.trim().isEmpty()) {
            String[] orderIdArray = orderIdsStr.split(",");
            for (String idStr : orderIdArray) {
                try {
                    int orderId = Integer.parseInt(idStr.trim());
                    orderDAO.updateStatus(orderId, "PENDING");
                } catch (NumberFormatException e) {
                }
            }
        }

        response.sendRedirect(request.getContextPath() + "/orderHistory");
    }
}
