package DAO;

import model.Review;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    public List<Review> getReviewsByFoodId(int foodId) {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT r.*, a.username FROM REVIEWS r JOIN ACCOUNT a ON r.ACCOUNTID = a.ID WHERE r.FOODID = ? ORDER BY r.CREATEDAT DESC";

        try (Connection conn = DBConnect.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, foodId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Review r = new Review();
                r.setId(rs.getInt("ID"));
                r.setAccountId(rs.getInt("ACCOUNTID"));
                r.setFoodId(rs.getInt("FOODID"));
                r.setRating(rs.getInt("RATING"));
                r.setComment(rs.getString("COMMENT"));
                r.setImageUrl(rs.getString("IMAGEURL"));
                r.setCreatedAt(rs.getTimestamp("CREATEDAT"));
                r.setUsername(rs.getString("username"));
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addReview(int accountId, int foodId, int rating, String comment, String imageUrl) {
        String sql = "INSERT INTO REVIEWS (ACCOUNTID, FOODID, RATING, COMMENT, IMAGEURL) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnect.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            ps.setInt(2, foodId);
            ps.setInt(3, rating);
            ps.setString(4, comment);
            ps.setString(5, imageUrl);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}