package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Account;
import model.User;

public class UserDAO {
	private static final String GET_BY_ACC_ID =
			"SELECT * FROM USERProfile WHERE ACCID = ?";
	// lấy thông tin theo accid
	public User getProfileByAccId(int accId) {
		try (Connection conn = DBConnect.getConnect();
			 PreparedStatement ps = conn.prepareStatement(GET_BY_ACC_ID)) {

			ps.setInt(1, accId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("ID"));
				u.setAccid(rs.getInt("ACCID"));
				u.setFullname(rs.getString("FULLNAME"));
				u.setAddress(rs.getString("ADDRES"));
				u.setNumber(rs.getString("NUMBER"));
				u.setEmail(rs.getString("EMAIL"));
				return u;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	// insert thong tin
	public void insertProfile(User u) {
		String sql = """
	            INSERT INTO USERProfile (ID, ACCID, FULLNAME, ADDRES, NUMBER, EMAIL)
	            VALUES (?, ?, ?, ?, ?, ?)
	        """;

		try (Connection conn = DBConnect.getConnect();
			 PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, u.getId());      // hoặc auto increment
			ps.setInt(2, u.getAccid());
			ps.setString(3, u.getFullname());
			ps.setString(4, u.getAddress());
			ps.setString(5, u.getNumber());
			ps.setString(6, u.getEmail());
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// update thong tin
	public void updateProfile(User u) {
		String sql = """
	            UPDATE USERProfile
	            SET FULLNAME = ?, ADDRES = ?, NUMBER = ?, EMAIL = ?
	            WHERE ACCID = ?
	        """;

		try (Connection conn = DBConnect.getConnect();
			 PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, u.getFullname());
			ps.setString(2, u.getAddress());
			ps.setString(3, u.getNumber());
			ps.setString(4, u.getEmail());
			ps.setInt(5, u.getAccid());
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void insertUser(Account acc){
		String sql ="INSERT INO ACCOUNT(USERNAME, PASS,ROLE, STATUS";
		try (Connection conn = DBConnect.getConnect();
		     PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, acc.getUserName());
			ps.setString(2, acc.getPassword());
			ps.setString(3, acc.getRole().name());
			ps.setString(4, acc.getRole().name());
			ps.executeUpdate();
		} catch (Exception e) {
e.printStackTrace();
		}


		}


		}
