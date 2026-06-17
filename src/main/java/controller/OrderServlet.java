package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.getRequestDispatcher("/views/jsp/order.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String provinceId = request.getParameter("provinceId");
        String districtId = request.getParameter("districtId");
        String wardCode = request.getParameter("wardCode");
        String detailAddress = request.getParameter("detailAddress");
        String note = request.getParameter("note");
        String shippingFee = request.getParameter("shippingFee");
        session.setAttribute("shippingFee", shippingFee);

        session.setAttribute("name", name);
        session.setAttribute("phone", phone);
        session.setAttribute("orderProvinceId", provinceId);
        session.setAttribute("orderDistrictId", districtId);
        session.setAttribute("orderWardCode", wardCode);
        session.setAttribute("orderDetailAddress", detailAddress);
        session.setAttribute("orderNote", note);

        response.sendRedirect(request.getContextPath() + "/checkout");
    }
}