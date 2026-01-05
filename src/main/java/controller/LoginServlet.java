package controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

import java.io.IOException;

import DAO.AccountDAO;
import DAO.CartDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
       
	private static final long serialVersionUID = 1L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
request.getRequestDispatcher("views/jsp/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String username = request.getParameter("username");
	        String password = request.getParameter("password");

	        AccountDAO dao = new AccountDAO();
	        Account acc = dao.login(username, password);

	        if (acc != null) {
	            // Đăng nhập thành công
	            HttpSession session = request.getSession();
	            session.setAttribute("userId", acc.getIdAccount()); 
	            session.setAttribute("user", username);
	            session.setAttribute("account", acc);
	            session.setAttribute("role", acc.getRole());

	            
	           
           if("ADMIN".equals(acc.getRole())) {
        	   response.sendRedirect(request.getContextPath()+"/admin/dashboard");
           } else {
        	   response.sendRedirect(request.getContextPath()+"/Trangchu");
           }
	     
		
	        } else {
	        	request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu");
	        	request.getRequestDispatcher("/views/jsp/login.jsp").forward(request, response);
	        }
	}
	}

