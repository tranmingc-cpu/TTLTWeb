package DAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.CartItem;
import model.Order;
public class OrderDAO {
	
public OrderDAO() {
	}
public int createOrder (Order o) {
	String sql = "INSERT INTO ORDER(USERID";
	try  {
		Connection con = DBConnect.getConnect();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, o.getAccountId());
		ps.setInt(2, o.getOrderid());
		ps.setDouble(3, o.getTotalAmount());
		ps.setDate(4, (Date) o.getOrderDate());
		ps.setString(5, o.getStatus());
		ps.executeQuery();
	
 ResultSet rs = ps.getGeneratedKeys();
 if(rs.next()) return rs.getInt(1);
 
	} catch (Exception e) {}
	return -1;
	
}
public void insertOrderDetail (int orderId,CartItem item) {
	String sql = "";
	try {
		Connection con = DBConnect.getConnect();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, orderId);
	ps.setInt(2, item.getFood().getId());
	ps.setInt(3, item.getQuantity());
	ps.setDouble(4, item.getFood().getPrice());;
	ps.executeUpdate();
	} catch (Exception e) {
	}
}
public int countOrder () {;

	String sql = "SELECT COUNT(*) FROM ORDER";
	
	try (Connection con = DBConnect.getConnect();
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();){
		if(rs.next()) return rs.getInt(1);
		
	} catch (Exception e) {}
		// TODO: handle exception
		return 0;
	}

public int countUser() {
	String sql ="SELECT COUNT(*) FROM USER";
	try (Connection con = DBConnect.getConnect();
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery()){
		if(rs.next()) return rs.getInt(1);
		
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	return 0;
	
}
public double totalRevenue () {
	String sql = " SELECT SUM(TOTAL) AS TOTALREV FROM ORDER";
	try (Connection con = DBConnect.getConnect();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()){
		if(rs.next()) return rs.getDouble(1);
	} catch (Exception e) {
		// TODO: handle exception
	}
	return 0;
}
}
