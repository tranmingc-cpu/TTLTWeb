package adminController;

import DAO.AdminPermissionDAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Account;
import model.AdminPermission;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet("/admin/permission/save-all")
public class SaveAllPermissionServlet extends HttpServlet {

    private final AdminPermissionDAO permissionDAO = new AdminPermissionDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Account currentUser = (Account) request.getSession().getAttribute("account");

        if (currentUser == null || currentUser.getRole() != Account.Role.SUPER_ADMIN) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        StringBuilder json = new StringBuilder();

        BufferedReader reader = request.getReader();
        String line;

        while ((line = reader.readLine()) != null) {json.append(line);
        }

        Gson gson = new Gson();

        Type listType = new TypeToken<List<AdminPermission>>() {}.getType();

        List<AdminPermission> permissions = gson.fromJson(json.toString(), listType);

        if (permissions != null) {
            for (AdminPermission p : permissions) {

                permissionDAO.saveOrUpdate(p);
            }
        }
        response.setContentType("text/plain");
        response.getWriter().write("success");
    }
}