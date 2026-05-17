package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Account;
import model.Account.Role;
import util.PasswordUtils;

public class AccountDAO {
    // lấy login và pass trong dtb và kiểm tra role của nó
    public Account login(String username, String pass) {
        String hashedPassword = PasswordUtils.toMD5(pass);
        String sql = "SELECT * FROM ACCOUNT WHERE USERNAME = ? AND PASS = ?";

        try (
                Connection con = DBConnect.getConnect();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, username);
            ps.setString(2, hashedPassword);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Account acc = new Account();
                acc.setIdAccount(rs.getInt("ID"));
                acc.setUserName(rs.getString("USERNAME"));
                acc.setPassword(rs.getString("PASS"));

                String role = rs.getString("ROLES");
                acc.setRole(Role.valueOf(role.trim().toUpperCase()));

                return acc;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // đăng kí
    public void register(Account acc) {
        String sql = """
            INSERT INTO ACCOUNT (USERNAME, PASS, ROLES, EMAIL)
            VALUES (?, ?, ?, ?)
            """;

        try (
                Connection c = DBConnect.getConnect();
                PreparedStatement ps = c.prepareStatement(sql)
        ) {

            ps.setString(1, acc.getUserName());
            ps.setString(2, acc.getPassword());
            ps.setString(3, acc.getRole().name());
            ps.setString(4, acc.getEmail());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // kiểm tra acc có tồn tại chưa
    public boolean checkExitAccount(String username) {
        String sql = "SELECT 1 FROM ACCOUNT WHERE USERNAME = ?";

        try (
                Connection con = DBConnect.getConnect();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // đếm số user
    public int countUser() {
        String sql = "SELECT COUNT(*) FROM ACCOUNT";

        try (
                Connection con = DBConnect.getConnect();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Account getAccountById(int accId) {
        String sql = "SELECT * FROM ACCOUNT WHERE ID = ?";

        try (
                Connection con = DBConnect.getConnect();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, accId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Account acc = new Account();
                acc.setIdAccount(rs.getInt("ID"));
                acc.setUserName(rs.getString("USERNAME"));
                acc.setPassword(rs.getString("PASS"));
                acc.setRole(Role.valueOf(
                        rs.getString("ROLES").trim().toUpperCase()
                ));
                return acc;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // cập nhập mk mới
    public void updatePassword(int accId, String newPassword) {
        String sql = "UPDATE ACCOUNT SET PASS = ? WHERE ID = ?";

        try (
                Connection conn = DBConnect.getConnect();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, newPassword);
            ps.setInt(2, accId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Account findByEmail(String email) {
        String sql = "SELECT * FROM ACCOUNT WHERE EMAIL = ?";

        try (
                Connection con = DBConnect.getConnect();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Account acc = new Account();
                acc.setIdAccount(rs.getInt("ID"));
                acc.setUserName(rs.getString("USERNAME"));
                acc.setPassword(rs.getString("PASS"));
                acc.setEmail(rs.getString("EMAIL"));

                acc.setRole(Role.valueOf(
                        rs.getString("ROLES").trim().toUpperCase()
                ));

                return acc;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public int insertGoogle(Account acc) {
        String sql = "INSERT INTO ACCOUNT (USERNAME, PASS, ROLES, EMAIL) VALUES (?, ?, ?, ?)";

        try (
                Connection con = DBConnect.getConnect();
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {

            ps.setString(1, acc.getUserName()); // có thể null
            ps.setString(2, ""); // không dùng password
            ps.setString(3, acc.getRole().name());
            ps.setString(4, acc.getEmail());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // trả về ID
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}