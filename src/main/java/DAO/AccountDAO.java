package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import model.Account;
import model.Account.Role;

public class AccountDAO {
    public Account login(String username, String pass) {
        String sql = "SELECT * FROM ACCOUNT WHERE USERNAME = ? AND PASS = ?";

        try (
                Connection con = DBConnect.getConnect();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, username);
            ps.setString(2, pass);

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

    public void register(Account acc) {
        String sql = """
            INSERT INTO ACCOUNT (ID, USERNAME, PASS, ROLES)
            VALUES (?, ?, ?, ?)
        """;

        try (
                Connection c = DBConnect.getConnect();
                PreparedStatement ps = c.prepareStatement(sql)
        ) {

            ps.setInt(1, acc.getIdAccount());
            ps.setString(2, acc.getUserName());
            ps.setString(3, acc.getPassword());
            ps.setString(4, acc.getRole().name());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public List<Account> getAllAccount() {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM ACCOUNT";
        try {
            Connection conn = DBConnect.getConnect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account acc = new Account();
                acc.setIdAccount(rs.getInt("ID"));
                acc.setUserName(rs.getString("USERNAME"));
                acc.setRole(Role.valueOf(
                        rs.getString("ROLES").trim().toUpperCase()
                ));
                list.add(acc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}


