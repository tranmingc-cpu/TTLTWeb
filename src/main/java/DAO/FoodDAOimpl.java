package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Food;

public class FoodDAOimpl implements FoodDAO {
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
	                return f;
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	@Override
	public void insert(Food food) {
		  String sql = "INSERT INTO Food(name, price, description, image) VALUES (?,?,?,?)";
	        try (Connection con = DBConnect.getConnect();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, food.getName());
	            ps.setDouble(2, food.getPrice());
	            ps.setString(3, food.getDescription());
	            ps.setString(4, food.getImage());
	            ps.setInt(5, food.getId());
	            ps.executeUpdate();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
		

	@Override
	public void update(Food food) {
		 String sql = "UPDATE Food SET name=?, price=?, description=?, image=? WHERE id=?";
	        try (Connection con = DBConnect.getConnect();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, food.getName());
	            ps.setDouble(2, food.getPrice());
	            ps.setString(3, food.getDescription());
	            ps.setString(4, food.getImage());
	            ps.setInt(5, food.getId());
	            ps.executeUpdate();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}

	@Override
	public void delete(int id) {
		  String sql = "DELETE FROM Food WHERE id=?";
	        try (Connection con = DBConnect.getConnect();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, id);
	            ps.executeUpdate();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}

	@Override
	public List<Food> findByName(String name) {
		List<Food> list = new ArrayList<>();
        String sql = "SELECT * FROM Food WHERE FNAME LIKE ?";
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


	@Override
	public List<Food> findByCategory(String category) {
		 List<Food> list = new ArrayList<>();
	        String sql = "SELECT * FROM Food WHERE category = ?";
	        try (Connection con = DBConnect.getConnect();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, category);
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

	@Override
	public List<Food> findALL() {
		  List<Food> list = new ArrayList<>();
	        String sql = "SELECT * FROM Food";
	        try (Connection con = DBConnect.getConnect();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

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
	@Override
	public List<Food> findLimit(int limit) {
		List<Food> list = new ArrayList<>();
	    String sql = "SELECT TOP (?) * FROM Food";

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
	}
	

