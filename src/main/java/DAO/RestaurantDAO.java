package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Restaurant;

public class RestaurantDAO {
public Restaurant getRestaurant(int id , String name, String address , String email, String number ,String decription , int rating ) {
	String sql = "SELECT * FROM RESTAURENT WHERE RESID = ? AND RNAME = ? AND ADDRES = ? AND EMAIL = ? AND PHONE = ? AND RATING = ? AND DECRIPTION = ? ";
	try {
		Connection con = DBConnect.getConnect() ;
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1,id);
		ps.setString(2, name);
		ps.setString(3, address);
		ps.setString(4, email);
		ps.setString(5, number);
		ps.setString(6,decription);
		ps.setLong(7, rating);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			return new Restaurant();
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return null;
}

public Restaurant getRestaurantById(int restaurantId) {
	  String sql = "SELECT * FROM RESTAURANT WHERE ID = ?";
   

      try (Connection con = DBConnect.getConnect();
           PreparedStatement ps = con.prepareStatement(sql)) {

          ps.setInt(1, restaurantId);
          ResultSet rs = ps.executeQuery();

          if (rs.next()) {
        	  Restaurant r = new Restaurant();
              r.setId(rs.getInt("ID"));
              r.setName(rs.getString("NAME"));
              r.setAddress(rs.getString("ADDRESS"));
              r.setPhone(rs.getString("PHONE"));
              r.setEmail(rs.getString("EMAIL"));
              r.setDescription(rs.getString("DESCRIPTION"));
              r.setRating(rs.getDouble("RATING"));
              return r;
          }

      } catch (Exception e) {
          e.printStackTrace();
      }
      return null;
  }
	 
}

