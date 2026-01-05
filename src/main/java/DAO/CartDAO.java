package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.CartItem;
import model.Food;

public class CartDAO {

    /* ===== ĐẢM BẢO USER CÓ CART ===== */
    public void createCartIfNotExists(int userId) {
        String check = "SELECT ID FROM CART WHERE USERID = ?";
        String insert = "INSERT INTO CART(ID, USERID) VALUES(?, ?)";

        try (Connection con = DBConnect.getConnect()) {
            PreparedStatement ps = con.prepareStatement(check);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                PreparedStatement ps2 = con.prepareStatement(insert);
                ps2.setInt(1, userId); // CART.ID = USERID
                ps2.setInt(2, userId);
                ps2.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ===== LẤY GIỎ HÀNG ===== */
    public ArrayList<CartItem> getCart(int cartId) {
        ArrayList<CartItem> list = new ArrayList<>();

        String sql = """
            SELECT f.ID, f.NAME, f.PRICE, cd.QUANTITY
            FROM CARTDETAIL cd
            JOIN FOOD f ON cd.FOODID = f.ID
            WHERE cd.ID = ?
        """;

        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Food food = new Food();
                food.setId(rs.getInt("ID"));
                food.setName(rs.getString("NAME"));
                food.setPrice(rs.getDouble("PRICE"));

                CartItem item = new CartItem();
                item.setFood(food);
                item.setQuantity(rs.getInt("QUANTITY"));

                list.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /* ===== ADD TO CART ===== */
    public void addToCart(int userId, int foodId, int quantity) {

        String findCartSql = "SELECT ID FROM CART WHERE USERID = ?";
        String checkItemSql =
            "SELECT ID, QUANTITY FROM CARTDETAIL " +
            "WHERE CARTID = ? AND FOODID = ?";
        String updateSql =
            "UPDATE CARTDETAIL SET QUANTITY = ? WHERE ID = ?";
        String insertSql =
            "INSERT INTO CARTDETAIL (CARTID, FOODID, QUANTITY) VALUES (?, ?, ?)";

        try (Connection con = DBConnect.getConnect()) {

            /* ===== LẤY CART ID ===== */
            int cartId;
            try (PreparedStatement ps = con.prepareStatement(findCartSql)) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                rs.next();
                cartId = rs.getInt("ID");
            }

            /* ===== CHECK FOOD ===== */
            try (PreparedStatement ps = con.prepareStatement(checkItemSql)) {
                ps.setInt(1, cartId);
                ps.setInt(2, foodId);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int detailId = rs.getInt("ID");
                    int newQty = rs.getInt("QUANTITY") + quantity;

                    try (PreparedStatement ups = con.prepareStatement(updateSql)) {
                        ups.setInt(1, newQty);
                        ups.setInt(2, detailId);
                        ups.executeUpdate();
                    }

                } else {
                    try (PreparedStatement ins = con.prepareStatement(insertSql)) {
                        ins.setInt(1, cartId);
                        ins.setInt(2, foodId);
                        ins.setInt(3, quantity);
                        ins.executeUpdate();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /* ===== UPDATE QUANTITY ===== */
    public void updateQuantity(int cartId, int foodId, int quantity) {
        String sql = """
            UPDATE CARTDETAIL
            SET QUANTITY = ?
            WHERE ID = ? AND FOODID = ?
        """;

        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, cartId);
            ps.setInt(3, foodId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ===== REMOVE ITEM ===== */
    public void removeItem(int cartId, int foodId) {
        String sql = """
            DELETE FROM CARTDETAIL
            WHERE ID = ? AND FOODID = ?
        """;

        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ps.setInt(2, foodId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
