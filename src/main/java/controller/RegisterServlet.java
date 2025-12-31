package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;

import java.io.IOException;

import DAO.AccountDAO;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String address = request.getParameter("address");
		String numberStr =request.getParameter("number");
	if(username ==null|| password==null||email==null||
			username.isEmpty()||password.isEmpty()||email.isEmpty()) {
		request.setAttribute("error","vui lòng nhập đầy đủ thông tin!");
		request.getRequestDispatcher("register.jsp").forward(request, response);
		request.getRequestDispatcher("/views/jsp/register.jsp").forward(request, response);
		
	}
		int number = Integer.parseInt(numberStr);
		Account acc = new Account();
		acc.setUserName(username);
		 acc.setPassword(password);
	        acc.setEmail(email);
	        acc.setNumber(number);
	        acc.setAddress(address);
	        acc.setRole(false); 
	        
	        AccountDAO dao = new AccountDAO();
	        dao.register(acc);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
