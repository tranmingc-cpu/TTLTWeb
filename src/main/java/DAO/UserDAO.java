package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UserDAO {

	private static final String GET_BY_ACC_ID = """
        SELECT  u.ID,
                u.ACCID,
                u.FULLNAME,
                u.ADDRESS,
                u.NUMBER,
                a.EMAIL
        FROM USERProfile u
        INNER JOIN Account a
        ON u.ACCID = a.ID
        WHERE u.ACCID = ?
        """;

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
				u.setAddress(rs.getString("ADDRESS"));
				u.setNumber(rs.getString("NUMBER"));
				u.setEmail(rs.getString("EMAIL"));

				return u;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void insertProfile(User u) {

		String sql = """
            INSERT INTO USERProfile
            (ACCID, FULLNAME, ADDRESS, NUMBER)
            VALUES (?, ?, ?, ?)
            """;

		try (Connection conn = DBConnect.getConnect();
		     PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, u.getAccid());
			ps.setString(2, u.getFullname());
			ps.setString(3, u.getAddress());
			ps.setString(4, u.getNumber());

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void updateProfile(User u) {

		String sql = """
            UPDATE USERProfile
            SET FULLNAME = ?,
                ADDRESS = ?,
                NUMBER = ?
            WHERE ACCID = ?
            """;

		try (Connection conn = DBConnect.getConnect();
		     PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, u.getFullname());
			ps.setString(2, u.getAddress());
			ps.setString(3, u.getNumber());
			ps.setInt(4, u.getAccid());

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public User getUserById(int id) {

		String sql = """
            SELECT  u.ID,
                    u.ACCID,
                    u.FULLNAME,
                    u.ADDRESS,
                    u.NUMBER,
                    a.EMAIL
            FROM USERProfile u
            INNER JOIN Account a
            ON u.ACCID = a.ID
            WHERE u.ID = ?
            """;

		try (Connection conn = DBConnect.getConnect();
		     PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				User u = new User();

				u.setId(rs.getInt("ID"));
				u.setAccid(rs.getInt("ACCID"));
				u.setFullname(rs.getString("FULLNAME"));
				u.setAddress(rs.getString("ADDRESS"));
				u.setNumber(rs.getString("NUMBER"));
				u.setEmail(rs.getString("EMAIL"));

				return u;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}