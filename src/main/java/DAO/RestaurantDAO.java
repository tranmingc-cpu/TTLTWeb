package DAO;

import java.sql.*;
import model.Restaurant;

public class RestaurantDAO {

    //  LẤY NHÀ HÀNG THEO RESID
    public Restaurant getRestaurantById(int resid) {
        String sql = "SELECT * FROM RESTAURENT WHERE RESID = ?";

        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, resid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Restaurant r = new Restaurant();
                r.setId(rs.getInt("RESID"));
                r.setName(rs.getString("RNAME"));
                r.setAddress(rs.getString("ADDRES"));
                r.setPhone(rs.getString("PHONE"));
                r.setEmail(rs.getString("EMAIL"));
                r.setDescription(rs.getString("DECRIPTION"));
                r.setRating(rs.getDouble("RATING"));
                return r;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //  CẬP NHẬT THÔNG TIN NHÀ HÀNG
    public void updateRestaurant(Restaurant r) {
        String sql = """
            UPDATE RESTAURENT
            SET RNAME = ?, ADDRES = ?, PHONE = ?, EMAIL = ?, DECRIPTION = ?, RATING = ?
            WHERE RESID = ?
        """;

        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, r.getName());
            ps.setString(2, r.getAddress());
            ps.setString(3, r.getPhone());
            ps.setString(4, r.getEmail());
            ps.setString(5, r.getDescription());
            ps.setDouble(6, r.getRating());
         ps.setInt(7, r.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
