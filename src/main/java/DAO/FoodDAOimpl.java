package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Food;

public class FoodDAOimpl implements FoodDAO {
	// lấy thông tin của food đã tồn tại theo id
	public Food infomation (int id ) {
		String sql = "SELECT * FROM Food WHERE id = ?";
		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Food f = new Food();
				f.setId(rs.getInt("ID"));
				f.setName(rs.getString("FNAME"));
				f.setPrice(rs.getDouble("PRICE"));
				f.setDescription(rs.getString("DESCRIPTIONS"));
				f.setImage(rs.getString("IMAGES"));
				f.setResID(rs.getInt("RESID"));
				return f;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	// thêm , sửa food
	@Override
	public void insert(Food food) {
		String sql = "INSERT INTO Food(ID,FNAME, PRICE, DESCRIPTIONS, CATEGORYID,IMAGES,RESID) VALUES (?,?,?,?,?,?,?)";
		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, food.getId());
			ps.setString(2, food.getName());
			ps.setDouble(3, food.getPrice());
			ps.setString(4, food.getDescription());
			ps.setInt(5, food.getCATEGORYId());
			ps.setString(6, food.getImage());
			ps.setInt(7, food.getResID());

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// cập nhập danh sách food
	@Override
	public void updateBySeller(Food food , int accId) {
		String sql = "UPDATE FOOD SET FNAME=?, PRICE=?, DESCRIPTIONS=?, IMAGES=? WHERE ID=? "
				+ "AND RESID = (SELECT RESID FROM RESTAURENT WHERE ACCID=?)";
		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, food.getName());
			ps.setDouble(2, food.getPrice());
			ps.setString(3, food.getDescription());
			ps.setString(4, food.getImage());
			ps.setInt(5, food.getId());
			ps.setInt(6,food.getId());
			ps.setInt(7, accId);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void update(Food food) {
		String sql = "UPDATE FOOD SET FNAME=?, PRICE=?, DESCRIPTIONS=?, IMAGES=? WHERE ID=? "
				+ "AND RESID =?";
		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, food.getName());
			ps.setDouble(2, food.getPrice());
			ps.setString(3, food.getDescription());
			ps.setString(4, food.getImage());
			ps.setInt(5, food.getId());
			ps.setInt(6,food.getId());
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// xóa food
	@Override
	public void delete(int id, int accId) {
		String sql = "DELETE FROM Food WHERE ID=? AND RESID = (SELECT RESID FROM RESTAURENT WHERE ACCID=?)";
		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, id);
			ps.setInt(2,accId);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// lấy food theo tên
	@Override
	public List<Food> findByName(String name) {
		List<Food> list = new ArrayList<>();
		String sql = "SELECT * FROM Food WHERE FNAME LIKE ?";// lấy tên
		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, "%" + name + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Food f = new Food();
				f.setId(rs.getInt("ID"));
				f.setName(rs.getString("FNAME"));
				f.setPrice(rs.getDouble("PRICE"));
				f.setDescription(rs.getString("DESCRIPTIONS"));
				f.setImage(rs.getString("IMAGES"));
				list.add(f);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// tìm food theo tên category
	@Override
	public List<Food> findByCategory(int category) {
		List<Food> list = new ArrayList<>();
		String sql = "SELECT * FROM FOOD WHERE CATEGORYID =?"; // lấy food theo category
		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, category);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Food f = new Food();
				f.setId(rs.getInt("ID"));
				f.setName(rs.getString("FNAME"));
				f.setPrice(rs.getDouble("PRICE"));
				f.setDescription(rs.getString("DESCRIPTIONS"));
				f.setImage(rs.getString("IMAGES"));
				f.setCATEGORYId(rs.getInt("CATEGORYID"));
				list.add(f);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	// tìm tất cả food
	@Override
	public List<Food> findALL() {
		List<Food> list = new ArrayList<>();
		String sql = "SELECT ID, FNAME, IMAGES, PRICE FROM FOOD";
		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Food f = new Food();
				f.setId(rs.getInt("ID"));
				f.setName(rs.getString("FNAME"));
				f.setImage(rs.getString("IMAGES"));
				f.setPrice(rs.getDouble("PRICE"));
				list.add(f);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	// lấy 1 số trong danh sách food để hiển thị
	@Override
	public List<Food> findLimit(int limit) {
		List<Food> list = new ArrayList<>();
		String sql = "SELECT TOP (?) * FROM Food"; // sql lấy 1 số

		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, limit);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Food f = new Food();
				f.setId(rs.getInt("ID"));
				f.setName(rs.getString("FNAME"));
				f.setPrice(rs.getDouble("PRICE"));
				f.setDescription(rs.getString("DESCRIPTIONS"));
				f.setImage(rs.getString("IMAGES"));
				list.add(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public int  countFood() {
		String sql = "SELECT COUNT(*) FROM FOOD";
		try (
				Connection con = DBConnect.getConnect();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
		) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	// lấy danh sách đồ ăn của nhà hàng đó 
	public List<Food> getFoodsByRestaurant(int resid) {
		List<Food> list = new ArrayList<>();
		String sql = "SELECT * FROM Food WHERE RESID = ?";
		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, resid);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Food f = new Food();
				f.setId(rs.getInt("ID"));
				f.setName(rs.getString("FNAME"));
				f.setPrice(rs.getDouble("PRICE"));
				f.setImage(rs.getString("IMAGES"));
				f.setDescription(rs.getString("DESCRIPTIONS"));
				f.setResID(rs.getInt("RESID"));
				f.setCATEGORYId(rs.getInt("CATEGORYID"));
				list.add(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	// hàm đếm food theo mã nhà hàng
	public int countFoodByRestaurant(int resId) {
		String sql = "SELECT COUNT(*) FROM FOOD WHERE RESID = ?";
		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, resId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) return rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	// hàm lấy food theo mã nhà hàng
	public int getResIdByFoodId(int foodId) {

		String sql = "SELECT RESID FROM FOOD WHERE ID = ?";

		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, foodId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("RESID");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}
	// lấy tất cả món ăn của nhà hàng
	public List<Food> getFoodBySeller (int accId){
		List<Food> list = new ArrayList<>();
		String sql = """
	            SELECT f.*
	            FROM FOOD f
	            JOIN RESTAURENT r ON f.RESID = r.RESID
	            WHERE r.ACCID = ?
	        """;
		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, accId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Food f = new Food();
				f.setId(rs.getInt("ID"));
				f.setName(rs.getString("FNAME"));
				f.setPrice(rs.getDouble("PRICE"));
				f.setDescription(rs.getString("DESCRIPTIONS"));
				f.setCATEGORYId(rs.getInt("CATEGORYID"));
				f.setImage(rs.getString("IMAGES"));
				f.setResID(rs.getInt("RESID"));
				list.add(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public Food getFoodbyFoodId(int id) {

		String sql = "SELECT * FROM food WHERE ID = ?";
		Food food = null;

		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				food = new Food();
				food.setId(rs.getInt("ID"));
				food.setName(rs.getString("FNAME"));
				food.setPrice(rs.getDouble("PRICE"));
				food.setImage(rs.getString("IMAGES"));
				food.setResID(rs.getInt("RESID"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return food;
	}
	@Override
	public void deleteFood(int id) {
		String sql = "DELETE FROM Food WHERE ID = ?";

		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, id);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

