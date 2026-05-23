package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Food;
import model.Restaurant;

public class SellerDAO {
	public Restaurant getRestaurantById(int resid) {
		String sql = "SELECT * FROM RESTAURANT WHERE RESID = ?";

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
				r.setDescription(rs.getString("DESCRIPTION"));
				r.setRating(rs.getBigDecimal("RATING"));
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
	            UPDATE RESTAURANT
	            SET RNAME = ?, ADDRES = ?, PHONE = ?, EMAIL = ?, DESCRIPTION = ?, RATING = ?
	            WHERE RESID = ?
	        """;

		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, r.getName());
			ps.setString(2, r.getAddress());
			ps.setString(3, r.getPhone());
			ps.setString(4, r.getEmail());
			ps.setString(5, r.getDescription());
			ps.setBigDecimal(6, r.getRating());
			ps.setInt(7, r.getResId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Restaurant getByAccountId(int accId) {
		String sql = "SELECT * FROM RESTAURANT WHERE ACCID = ?";

		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, accId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Restaurant r = new Restaurant();
				r.setId(rs.getInt("RESID"));
				r.setAccid(rs.getInt("ACCID"));
				r.setName(rs.getString("RNAME"));
				r.setAddress(rs.getString("ADDRES"));
				r.setPhone(rs.getString("PHONE"));
				r.setEmail(rs.getString("EMAIL"));
				r.setDescription(rs.getString("DESCRIPTION"));

				return r;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public List<Food> getFoodsByRestaurant(int resId) {
		String sql = "SELECT * FROM FOOD WHERE RESID = ?";

		List<Food> list = new ArrayList<>();

		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, resId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Food f = new Food();
				f.setId(rs.getInt("ID"));
				f.setName(rs.getString("FNAME"));
				f.setPrice(rs.getBigDecimal("PRICE"));
				f.setImage(rs.getString("IMAGES"));
				f.setDescription(rs.getString("DESCRIPTIONS"));
				list.add(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public void addFood(Food f, int resId) {
		String sql = """
	            INSERT INTO FOOD (RESID, FNAME, PRICE, IMAGES, DESCRIPTIONS)
	            VALUES (?, ?, ?, ?, ?)
	        """;

		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, resId);
			ps.setString(2, f.getName());
			ps.setBigDecimal(3, f.getPrice());
			ps.setString(4, f.getImage());
			ps.setString(5, f.getDescription());

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateFood(Food f, int resId) {
		String sql = """
	            UPDATE FOOD
	            SET FNAME = ?, PRICE = ?, IMAGES = ?, DESCRIPTIONS = ?
	            WHERE ID = ? AND RESID = ?
	        """;

		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, f.getName());
			ps.setBigDecimal(2, f.getPrice());
			ps.setString(3, f.getImage());
			ps.setString(4, f.getDescription());
			ps.setInt(5, f.getId());
			ps.setInt(6, resId);

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void deleteFood(int foodId, int resId) {
		String sql = "DELETE FROM FOOD WHERE ID = ? AND RESID = ?";

		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, foodId);
			ps.setInt(2, resId);

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// lay resid thoe accid
	public int getResIdByAccid (int accId) {
		String sql = "SELECT RESID FROM RESTAURANT WHERE ACCID=?";

		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, accId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("RESID");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	public List<Restaurant> getAll() {
		List<Restaurant> list = new ArrayList<>();
		String sql = "SELECT * FROM RESTAURANT";
		try (Connection con = DBConnect.getConnect();
		     PreparedStatement ps = con.prepareStatement(sql);
		     ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Restaurant r = new Restaurant();
				r.setId(rs.getInt("RESID"));
				r.setName(rs.getString("RNAME"));
				list.add(r);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}

