package DAO;
import model.Coupons;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CouponDAO {
    public boolean addCoupon(Coupons coupon) {

        String sql = "INSERT INTO COUPONS (CODE, DISCOUNTTYPE, DISCOUNTVALUE, MINORDERVALUE, "
                + "MAXDISCOUNTAMOUNT, QUANTITY, STARTDATE, ENDDATE, STATUS, USEDCOUNT) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnect.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, coupon.getCode());
            ps.setString(2, coupon.getDiscountType());
            ps.setBigDecimal(3, coupon.getDiscountValue());
            ps.setBigDecimal(4, coupon.getMinOrderValue());
            ps.setBigDecimal(5, coupon.getMaxDiscountAmount());
            ps.setInt(6, coupon.getQuantity());
            ps.setTimestamp(7, coupon.getStartDate());
            ps.setTimestamp(8, coupon.getEndDate());
            ps.setBoolean(9, coupon.isStatus());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCoupon(int id) {
        String sql = "DELETE FROM COUPONS WHERE ID = ?";

        try (Connection conn = DBConnect.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
  // check coupon hợp lệ
    public Coupons validateCoupon(String code) {
        String sql = "SELECT * FROM COUPONS WHERE CODE = ? AND STATUS = 1 "
                + "AND QUANTITY > 0 AND CURRENT_TIMESTAMP BETWEEN STARTDATE AND ENDDATE";
        try (Connection conn = DBConnect.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Coupons cop = new Coupons();
                    cop.setId(rs.getInt("ID"));
                    cop.setCode(rs.getString("CODE"));
                    cop.setDiscountType(rs.getString("DISCOUNTTYPE"));
                    cop.setDiscountValue(rs.getBigDecimal("DISCOUNTVALUE"));
                    cop.setMinOrderValue(rs.getBigDecimal("MINORDERVALUE"));
                    cop.setMaxDiscountAmount(rs.getBigDecimal("MAXDISCOUNTAMOUNT"));
                    cop.setQuantity(rs.getInt("QUANTITY"));
                    cop.setStartDate(rs.getTimestamp("STARTDATE"));
                    cop.setEndDate(rs.getTimestamp("ENDDATE"));
                    cop.setStatus(rs.getBoolean("STATUS"));
                    return cop;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
 // đếm số lượng coupons đã sử dụng
    public boolean useCoupon(int id) {
        String sql = "UPDATE COUPONS SET QUANTITY = QUANTITY - 1, USEDCOUNT = USEDCOUNT + 1, "
                + "STATUS = CASE WHEN QUANTITY - 1 <= 0 THEN 0 ELSE STATUS END "
                + "WHERE ID = ? AND QUANTITY > 0 AND STATUS = 1";
        try (Connection conn = DBConnect.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
// lấy tất cả coupons
    public List<Coupons> getAllCounpons() {
        List<Coupons> list = new ArrayList<>();
        String sql = "SELECT * FROM COUPONS ORDER BY ID DESC";

        try (Connection conn = DBConnect.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Coupons c = new Coupons();
                c.setId(rs.getInt("ID"));
                c.setCode(rs.getString("CODE"));
                c.setDiscountType(rs.getString("DISCOUNTTYPE"));
                c.setDiscountValue(rs.getBigDecimal("DISCOUNTVALUE"));
                c.setMinOrderValue(rs.getBigDecimal("MINORDERVALUE"));
                c.setMaxDiscountAmount(rs.getBigDecimal("MAXDISCOUNTAMOUNT"));
                c.setQuantity(rs.getInt("QUANTITY"));
                c.setStartDate(rs.getTimestamp("STARTDATE"));
                c.setEndDate(rs.getTimestamp("ENDDATE"));
                c.setStatus(rs.getBoolean("STATUS"));
                c.setUsedCount(rs.getInt("USEDCOUNT"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("đã lấy dtb "  + list );
        }
        return list;
    }
    public Coupons getCouponById(int id) {
        String sql = "SELECT * FROM COUPONS WHERE ID = ?";

        try (Connection conn = DBConnect.getConnect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Coupons c = new Coupons();
                    c.setId(rs.getInt("ID"));
                    c.setCode(rs.getString("CODE"));
                    c.setDiscountType(rs.getString("DISCOUNTTYPE"));
                    c.setDiscountValue(rs.getBigDecimal("DISCOUNTVALUE"));
                    c.setMinOrderValue(rs.getBigDecimal("MINORDERVALUE"));
                    c.setMaxDiscountAmount(rs.getBigDecimal("MAXDISCOUNTAMOUNT"));
                    c.setQuantity(rs.getInt("QUANTITY"));
                    c.setStartDate(rs.getTimestamp("STARTDATE"));
                    c.setEndDate(rs.getTimestamp("ENDDATE"));
                    c.setStatus(rs.getBoolean("STATUS"));
                    c.setUsedCount(rs.getInt("USEDCOUNT"));
                    return c;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}



