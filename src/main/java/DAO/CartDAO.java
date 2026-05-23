package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.CartItem;
import model.Food;

public class CartDAO {

	/*public ArrayList<CartItem> getCartByUser(int accId) {

	    ArrayList<CartItem> list = new ArrayList<>();

	    String sql = """
	        SELECT cd.DEATILID, cd.QUANTITY,
	               f.ID AS FOOD_ID, f.FNAME, f.PRICE, f.IMAGES, f.RESID
	        FROM CART c
	        JOIN CARTDETAIL cd ON c.ID = cd.CARTID
	        JOIN FOOD f ON cd.FOODID = f.ID
	        WHERE c.ACCID = ?
	    """;

	    try (Connection con = DBConnect.getConnect();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, accId);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {

	            Food food = new Food();
	            food.setId(rs.getInt("FOOD_ID"));
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
	} */

	// lấy cart theo accid
	public int getCartIdByAccount(int accId) {
		String sql = "SELECT ID FROM CART WHERE ACCID = ?";

		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, accId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("ID");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1; // không tồn tại cart
	}
	// lấy cart hoặc tạo cart nếu không có
	public int getOrCreateCart(int accId) {
		String select = "SELECT ID FROM CART WHERE ACCID = ?";
		String insert = "INSERT INTO CART (ACCID) VALUES (?)";

		try (Connection con = DBConnect.getConnect()) {
			PreparedStatement ps = con.prepareStatement(select);
			ps.setInt(1, accId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("ID"); // cartId
			}
// tạo cart mới
			ps = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, accId);
			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	// xóa cart 
	public void clearCartByUser(int accountId) {
		String sql = """
            DELETE cd
            FROM CARTDETAIL cd
            JOIN CART c ON cd.CARTID = c.ID
            WHERE c.ACCID = ?
        """;
		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, accountId);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* ===== LẤY GIỎ HÀNG ===== */

	public void addCartByUser(int accId, int foodId, int quantity) {

		int cartId = getOrCreateCart(accId);
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

		try (Connection con = DBConnect.getConnect();

			 PreparedStatement ps = con.prepareStatement(check)) {
			ps.setInt(1, cartId);
			ps.setInt(2, foodId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int newQty = rs.getInt("QUANTITY") + quantity;
				PreparedStatement ups = con.prepareStatement(update);
				ups.setInt(1, newQty);
				ups.setInt(2, rs.getInt("DEATILID"));
				ups.executeUpdate();
			} else {
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
	// đếm số lượng sp có trong cart
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
	/* ===== REMOVE ITEM ===== */
	public void removeItem(int detailId ) {

		String sql = "DELETE FROM CARTDETAIL WHERE DEATILID = ?";
		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, detailId);

			int rows = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// update quantity
	public void updateQuantity(int detailId, int quantity) {

		if (quantity <= 0) {
			removeItem(detailId);
			return;
		}

		String sql = "UPDATE CARTDETAIL SET QUANTITY = ? WHERE DEATILID = ?";

		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, quantity);
			ps.setInt(2, detailId);

			int rows = ps.executeUpdate();
			System.out.println("UPDATE rows = " + rows);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// lấy danh sách các cartitem trong cart
	public List<CartItem> getCartItems(int cartId) {
		List<CartItem> list = new ArrayList<>();

		String sql = """
	        SELECT cd.DEATILID, cd.QUANTITY,
	               f.ID AS FOOD_ID, f.FNAME, f.PRICE, f.IMAGES, f.RESID
	        FROM CARTDETAIL cd
	        JOIN FOOD f ON cd.FOODID = f.ID
	        WHERE cd.CARTID = ?
	    """;

		try (Connection con = DBConnect.getConnect();
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, cartId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Food food = new Food();
				food.setId(rs.getInt("FOOD_ID"));
				food.setName(rs.getString("FNAME"));
				food.setPrice(rs.getBigDecimal("PRICE"));
				food.setImage(rs.getString("IMAGES"));
				food.setResID(rs.getInt("RESID"));

				CartItem item = new CartItem();
				item.setDetailId(rs.getInt("DEATILID"));
				item.setQuantity(rs.getBigDecimal("QUANTITY"));
				item.setFood(food);

				list.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
		

