package DAO;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.*;
public class AccountDAO {
public Account login(String username , String pass) {
	String sql = "SELECT * FROM ACCOUNT Where USERNAME = ? AND PASS= ? ";
	try {
		Connection con = DBConnect.getConnect();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, username);
		ps.setString(2, pass);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			Account acc = new Account();
			acc.setIdAccount(rs.getInt("ID"));
            acc.setUserName(rs.getString("USERNAME"));
            acc.setPassword(rs.getString("PASS")); 
            acc.setEmail((rs.getString("EMAIL")));
            acc.setAddress(rs.getString("ADDRESS"));
            acc.setNumber(rs.getInt("NUMBER"));
            acc.setRole(rs.getString("ROLES"));
            return acc;
			
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
		ps.setString(6, acc.getRole());
		ps.setString(7, acc.getAddress());
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
public int countUser() {
	String sql = "SELECT COUNT(*) FROM ACCOUNT";
	try (Connection con = DBConnect.getConnect();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()){
		if(rs.next()) return rs.getInt(1);
			
	} catch (Exception e) {
		e.printStackTrace();
	}
	return 0;
}

}
