package DAO;

import model.Banner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BannerDAO {
    public List<Banner> getAllBanners() {
        List<Banner> list = new ArrayList<>();
        String sql = "SELECT * FROM BANNER";
        try (Connection conn = DBConnect.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Banner b = new Banner(
                        rs.getInt("ID"),
                        rs.getString("TITLE"),
                        rs.getString("IMAGES"),
                        rs.getInt("FOODID"),
                        rs.getBoolean("STATUS")
                );
                list.add(b);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    public Banner getBannerById(int id) {
        String sql = "SELECT * FROM BANNER WHERE ID = ?";
        try (Connection conn = DBConnect.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Banner(
                            rs.getInt("ID"),
                            rs.getString("TITLE"),
                            rs.getString("IMAGES"),
                            rs.getInt("FOODID"),
                            rs.getBoolean("STATUS")
                    );
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
    public void updateBanner(Banner banner) {
        String sql = "UPDATE BANNER SET TITLE = ?, IMAGES = ?, FOODID = ?, STATUS = ? WHERE ID = ?";
        try (Connection conn = DBConnect.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, banner.getTitle());
            ps.setString(2, banner.getImages());
            ps.setInt(3, banner.getFoodId());
            ps.setBoolean(4, banner.isStatus());
            ps.setInt(5, banner.getId());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
    public void deleteBanner(int id) {
        String sql = "DELETE FROM BANNER WHERE ID = ?";
        try (Connection conn = DBConnect.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
    public void toggleStatus(int id, boolean currentStatus) {
        String sql = "UPDATE BANNER SET STATUS = ? WHERE ID = ?";
        try (Connection conn = DBConnect.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, !currentStatus);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public boolean insertBanner(Banner banner) {
        String sql = "INSERT INTO BANNER (TITLE, IMAGES, FOODID, STATUS) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnect.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, banner.getTitle());
            ps.setString(2, banner.getImages());
            ps.setInt(3, banner.getFoodId());
            ps.setBoolean(4, banner.isStatus());

            int rowAffected = ps.executeUpdate();
            return rowAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
