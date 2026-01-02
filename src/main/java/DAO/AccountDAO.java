package DAO;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.*;
public class AccountDAO {
public Account login(String username , String pass) {
	String sql = "SELECT * FROM ACCOUNT Where username = ? AND password= ? ";
	try {
		Connection con = DBConnect.getConnect();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, username);
		ps.setString(2, pass);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			return new Account();
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return null;
}
public void register(Account acc) {
	String sql ="INSERT INTO ACCOUNT VALUES (?,?,?,?,?)";
	try (Connection c = DBConnect.getConnect();
			PreparedStatement ps = c.prepareStatement(sql)){
		ps.setString(1,acc.getUserName());
		ps.setString(2,acc.getEmail());
		ps.setString(3,acc.getPassword());
		ps.setInt(4,acc.getIdAccount());
		ps.setInt(5,acc.getNumber());
		ps.executeUpdate();
			
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
}
public boolean checkExitAccount(String username) {
	String sql ="SELECT 1 FROM ACCOUNT WHERE username = ?";
	try(Connection con = DBConnect.getConnect();
			PreparedStatement ps = con.prepareStatement(sql)) {
		ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		
	} catch (Exception e) {
e.printStackTrace();	}
	return false;
}
}
