package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.CartItem;
import model.Food;

public class CartDAO {

    /* ===== ĐẢM BẢO USER CÓ CART ===== */
    public int createCartIfNotExists(int userId) {
        String check = "SELECT ID FROM CART WHERE USERID = ?";
        String insert = "INSERT INTO CART(ID, USERID) VALUES (?, ?)";

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
        return -1;
    }
    
public int getCartId(int userID) {
	String sql = "SELECT ID FROM CART WHERE USERID = ? ";
	 try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("ID");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
	
}
    /* ===== LẤY GIỎ HÀNG ===== */
    public ArrayList<CartItem> getCartItem(int cartId) {
        ArrayList<CartItem> list = new ArrayList<>();

        String sql = """
                SELECT cd.DEATILID, cd.QUANTITY,
                f.ID, f.FNAME, f.PRICE
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
                food.setName(rs.getString("FNAME"));
                food.setPrice(rs.getDouble("PRICE"));

                CartItem item = new CartItem();
                item.setDetailId(rs.getInt("DEATILID"));
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
    public void addToCart(int cartId, int foodId, int quantity) {

    	String check = """
                SELECT DEATILID, QUANTITY
                FROM CARTDETAIL
                WHERE ID = ? AND FOODID = ?
            """;

            String update = """
                UPDATE CARTDETAIL
                SET QUANTITY = ?
                WHERE DEATILID = ?
            """;

            String insert = """
                INSERT INTO CARTDETAIL (DEATILID, ID, FOODID, QUANTITY)
                VALUES (?, ?, ?, ?)
            """;

        	 try (Connection con = DBConnect.getConnect();
                     PreparedStatement ps = con.prepareStatement(check)) {

                    ps.setInt(1, cartId);
                    ps.setInt(2, foodId);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        int detailId = rs.getInt("DEATILID");
                        int newQty = rs.getInt("QUANTITY") + quantity;

                        try (PreparedStatement ups = con.prepareStatement(update)) {
                            ups.setInt(1, newQty);
                            ups.setInt(2, detailId);
                            ups.executeUpdate();
                        }

                    } else {
                        int detailId = (int) (System.currentTimeMillis() % 1_000_000);

                        try (PreparedStatement ins = con.prepareStatement(insert)) {
                            ins.setInt(1, detailId);
                            ins.setInt(2, cartId);
                            ins.setInt(3, foodId);
                            ins.setInt(4, quantity);
                            ins.executeUpdate();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    


    /* ===== UPDATE QUANTITY ===== */
    public void updateQuantity(int detailId, int quantity) {
    	 String sql = "UPDATE CARTDETAIL SET QUANTITY = ? WHERE DEATILID = ?";
        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, detailId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ===== REMOVE ITEM ===== */
    public void removeItem(int detailId) {
    	  String sql = "DELETE FROM CARTDETAIL WHERE DEATILID = ?";

        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, detailId);
    
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int countCartItems(int cartId) {
        String sql = "SELECT SUM(QUANTITY) FROM CARTDETAIL WHERE ID = ?";
        try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
