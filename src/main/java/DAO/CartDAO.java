package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.CartItem;
import model.Food;

public class CartDAO {
   
    /* ===== ĐẢM BẢO USER CÓ CART =====
    public int createCartIfNotExists(int userId) {
        String check = "SELECT ID FROM CART WHERE USERID = ?";
        String insert = "INSERT INTO CART(ID, USERID) VALUES (?, ?)";

        try (Connection con = DBConnect.getConnect()) {
            PreparedStatement ps = con.prepareStatement(check);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                PreparedStatement ps2 = con.prepareStatement(insert);
                ps2.setInt(1, userId); 

                ps2.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userId;
    } */
	public ArrayList<CartItem> getCartByUser(int userId) {

	    ArrayList<CartItem> list = new ArrayList<>();

	    String sql = """
	        SELECT cd.DEATILID, cd.QUANTITY,
	               f.ID, f.FNAME, f.PRICE, f.IMAGES, f.RESID
	        FROM CART c
	        JOIN CARTDETAIL cd ON c.ID = cd.DEATILID
	        JOIN FOOD f ON cd.FOODID = f.ID
	        WHERE c.ACCID = ?
	    """;

	    try (Connection con = DBConnect.getConnect();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, userId);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {

	            Food food = new Food();
	            food.setId(rs.getInt("ID"));
	            food.setName(rs.getString("FNAME"));
	            food.setPrice(rs.getDouble("PRICE"));
	            food.setImage(rs.getString("IMAGES"));
	            food.setResID(rs.getInt("RESID"));

	            CartItem item = new CartItem();
	            item.setDetailId(rs.getInt("DEATILID"));
	            item.setQuantity(rs.getInt("QUANTITY"));
	            item.setFood(food);

	            list.add(item);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}

    public int getOrCreateCart(int userId) {
        String select = "SELECT ID FROM CART WHERE USERID = ?";
        String insert = "INSERT INTO CART(USERID) VALUES (?)";

        try (Connection con = DBConnect.getConnect()) {

            PreparedStatement ps = con.prepareStatement(select);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("ID");

            PreparedStatement ins = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            ins.setInt(1, userId);
            ins.executeUpdate();

            rs = ins.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

/*public int getCartId(int cartId) {
	String sql = "SELECT ID FROM CART WHERE USERID = ? ";
	 try (Connection con = DBConnect.getConnect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("ID");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
	
}
    /* ===== LẤY GIỎ HÀNG ===== */
    
    public void addCartByUser(int userId, int foodId, int quantity) {

        // 1 Lấy hoặc tạo cart cho user
        int cartId = getOrCreateCart(userId);
        if (cartId == -1) return;

        String check = """
            SELECT DEATILID, QUANTITY
            FROM CARTDETAIL
            WHERE CARTID = ? AND FOODID = ?
        """;

        String update = """
            UPDATE CARTDETAIL
            SET QUANTITY = ?
            WHERE DEATILID = ?
        """;

        String insert = """
            INSERT INTO CARTDETAIL (CARTID, FOODID, QUANTITY)
            VALUES (?, ?, ?)
        """;

        try (Connection con = DBConnect.getConnect()) {

            PreparedStatement ps = con.prepareStatement(check);
            ps.setInt(1, cartId);
            ps.setInt(2, foodId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // 2Đã có sản phẩm → tăng số lượng
                int newQty = rs.getInt("QUANTITY") + quantity;

                PreparedStatement ups = con.prepareStatement(update);
                ups.setInt(1, newQty);
                ups.setInt(2, rs.getInt("DEATILID"));
                ups.executeUpdate();

            } else {
                // 3Chưa có → thêm mới
                PreparedStatement ins = con.prepareStatement(insert);
                ins.setInt(1, cartId);
                ins.setInt(2, foodId);
                ins.setInt(3, quantity);
                ins.executeUpdate();
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
        String sql = "SELECT SUM(QUANTITY) FROM CARTDETAIL WHERE CARTID = ?";
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
