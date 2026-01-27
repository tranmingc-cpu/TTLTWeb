package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Category;

public class CategoryDAO {
	// lấy tên theo cate id
	 public String getNameById(int id) {
	        String sql = "SELECT CNAME FROM CATEGORY WHERE ID = ?";
	        try (Connection con = DBConnect.getConnect();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, id);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                return rs.getString("CNAME");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return "";
	    }
	 // lấy all 
	 public List<Category> findAll() {
	        List<Category> list = new ArrayList<>();
	        String sql = "SELECT * FROM CATEGORY";

	        try (Connection con = DBConnect.getConnect();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {
	             Category cate = new Category();
	             rs.getInt(cate.getId());
	             rs.getString(cate.getName());
	             list.add(cate);             
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return list;
	    }
	}

